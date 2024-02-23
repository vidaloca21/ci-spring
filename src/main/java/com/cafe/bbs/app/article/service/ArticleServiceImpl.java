package com.cafe.bbs.app.article.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.dao.ArticleDAO;
import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;
import com.cafe.bbs.app.attachment.dao.AttachmentDAO;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.beans.FileHandler;
import com.cafe.bbs.beans.FileHandler.StoredFile;
import com.cafe.bbs.beans.SHA;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private AttachmentDAO attachmentDAO;
	@Autowired
	private FileHandler fileHandler;
	@Autowired
	private SHA sha;
	
	@Override
	public List<ArticleVO> getAllArticle(SearchArticleVO searchArticleVO) {
		List<ArticleVO> articleList = articleDAO.searchArticle(searchArticleVO);
		return articleList;
	}
	
	@Override
	public int getArticleCount(SearchArticleVO searchArticleVO) {
		return articleDAO.getArticleCount(searchArticleVO);
	}
	
	@Transactional
	@Override
	public ArticleVO getOneArticleByArticleId(String articleId, boolean isIncrease) {
		if (isIncrease) {
			articleDAO.increaseViewCount(articleId);
		}
		ArticleVO articleVO = articleDAO.getOneArticleByArticleId(articleId);
		return articleVO;
	}
	
	@Transactional
	@Override
	public boolean createNewArticle(ArticleVO articleVO, List<MultipartFile> attachFiles) {
		// 사용자가 입력한 비밀번호 암호화
		String salt = sha.generateSalt();
		String password = articleVO.getArticlePassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		articleVO.setArticlePassword(encryptedPassword);
		articleVO.setArticleSalt(salt);
		
		// 게시글의 기본 정보 DB에 저장(ARTICLE_MASTER)
		boolean isArticleInfoSuccess = articleDAO.createNewArticleInfo(articleVO) >0;
		
		// 사용자가 첨부한 파일의 개수와 DB에 실제 저장된 파일의 개수 비교
		int fileCnt = attachFiles.size();
		int successCnt = 0;
		for (MultipartFile file: attachFiles) {
			StoredFile storedFile = fileHandler.storeFile(file);
			if (storedFile == null) {
				fileCnt = 0;
				break;
			}
			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setArticleId(articleVO.getArticleId());
			attachmentVO.setOriginFilename(storedFile.getFileName());
			attachmentVO.setUuidFilename(storedFile.getRealFileName());
			successCnt += attachmentDAO.storeNewFile(attachmentVO);
		}
		boolean isFileSuccess = fileCnt == successCnt;
		
		// 게시글 정보 DB에 저장(ARTICLE)
		boolean isArticleSuccess = articleDAO.createNewArticle(articleVO) >0;
		
		// 게시글 기본 정보 저장, 게시글 정보 저장, 파일 저장 성공 여부 반환
		return isArticleInfoSuccess && isArticleSuccess && isFileSuccess;
	}
	
	@Transactional
	@Override
	public boolean modifyArticle(ArticleVO articleVO, List<MultipartFile> attachFiles, List<String> deleteFiles) {
		// 사용자가 삭제 요청한 게시글 첨부파일 DB삭제 수행
		int deleteFileCnt = 0;
		int deleteCnt = 0;
		if (deleteFiles != null) {
			deleteFileCnt = deleteFiles.size();
			for (String attachmentId: deleteFiles) {
				AttachmentVO attachmentVO = attachmentDAO.getOneAttachment(attachmentId);
				File originfile = fileHandler.getStoredFile(attachmentVO.getUuidFilename());
				if (originfile.exists() && originfile.isFile()) {
					attachmentDAO.deleteAttachment(attachmentVO.getAttachmentId());
					originfile.delete();
					deleteCnt++;
				}
			}
		}
		boolean isFileDeleteSuccess = deleteFileCnt == deleteCnt;
		
		// 사용자가 등록 요청한 게시글 첨부파일 DB저장 수행
		int fileCnt = attachFiles.size();
		int successCnt = 0;
		for (MultipartFile file: attachFiles) {
			StoredFile storedFile = fileHandler.storeFile(file);
			if (storedFile == null) {
				fileCnt = 0;
				break;
			}
			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setArticleId(articleVO.getArticleId());
			attachmentVO.setOriginFilename(storedFile.getFileName());
			attachmentVO.setUuidFilename(storedFile.getRealFileName());
			successCnt += attachmentDAO.storeNewFile(attachmentVO);
		}
		boolean isFileUploadSuccess = fileCnt == successCnt;

		// 게시글 기본 정보와 게시글 정보 DB에 수정
		boolean isArticleInfoSuccess = articleDAO.modifyArticleInfo(articleVO) >0;
		boolean isArticleSuccess = articleDAO.modifyArticle(articleVO) >0;
		
		// 게시글 기본 정보 및 정보 UPDATE 성공 여부, 파일 삭제 성공 여부, 파일 저장 성공 여부 반환 
		return isArticleInfoSuccess && isArticleSuccess && isFileDeleteSuccess && isFileUploadSuccess;
	}
	
	@Transactional
	@Override
	public boolean deleteOneArticle(String articleId) {
		// 게시글에 첨부된 파일 목록에 대해 삭제 수행
		List<AttachmentVO> fileList = attachmentDAO.getAllFilesByArticleId(articleId);
		int deleteFileCnt = fileList.size();
		int deleteCnt = 0;
		if (fileList.size() >0) {
			for (AttachmentVO attachmentVO: fileList) {
				File originfile = fileHandler.getStoredFile(attachmentVO.getUuidFilename());
				if (originfile.exists() && originfile.isFile()) {
					attachmentDAO.deleteAttachment(attachmentVO.getAttachmentId());
					originfile.delete();
					deleteCnt++;
				}
			}
		}
		boolean isFileDeleteSuccess = deleteFileCnt == deleteCnt;
		
		// 게시글 정보를 DB에서 비공개 처리함
		boolean isArticleDeleteSuccess = articleDAO.deleteOneArticle(articleId) >0;
		
		// 파일 삭제 성공 여부, 비공개 처리 성공 여부 반환
		return isArticleDeleteSuccess && isFileDeleteSuccess;
	}
	
	@Override
	public boolean confirmPassword(ArticleVO articleVO) {
		String articleId = articleVO.getArticleId();
		ArticleVO originArticle = articleDAO.getArticlePassword(articleId);
		String originPassword = originArticle.getArticlePassword();
		String salt = originArticle.getArticleSalt();
		String userPassword = articleVO.getArticlePassword();
		String encryptedPassword = sha.getEncrypt(userPassword, salt);
		return originPassword.equals(encryptedPassword);
	}
	
	@Override
	public NextArticleVO getBesideArticle(ArticleVO articleVO) {
		return articleDAO.getBesideArticle(articleVO);
	}
	
	
	@Transactional
	@Override
	public boolean createNewArticle(ArticleVO articleVO) {
		// 사용자가 입력한 비밀번호 암호화
		String salt = sha.generateSalt();
		String password = articleVO.getArticlePassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		articleVO.setArticlePassword(encryptedPassword);
		articleVO.setArticleSalt(salt);
		
		// 게시글의 기본 정보 DB에 저장(ARTICLE_MASTER)
		boolean isArticleInfoSuccess = articleDAO.createNewArticleInfo(articleVO) >0;
		
		// 게시글 정보 DB에 저장(ARTICLE)
		boolean isArticleSuccess = articleDAO.createNewArticle(articleVO) >0;
		
		// 게시글 기본 정보 저장, 게시글 정보 저장, 파일 저장 성공 여부 반환
		return isArticleInfoSuccess && isArticleSuccess;
	}
}
