package com.cafe.bbs.app.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.dao.ArticleDAO;
import com.cafe.bbs.app.article.vo.ArticleVO;
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
		if (searchArticleVO == null) {
			return articleDAO.getAllArticle();
		} else {
			return articleDAO.searchArticle(searchArticleVO);
		}
	}
	
	@Override
	public int getAllArticleCount() {
		return articleDAO.getAllArticleCount();
	}
	
	@Override
	public ArticleVO getOneArticleByArticleId(String articleId) {
		articleDAO.increaseViewCount(articleId);
		ArticleVO articleVO = articleDAO.getOneArticleByArticleId(articleId);
		return articleVO;
	}
	
	@Transactional
	@Override
	public boolean createNewArticle(ArticleVO articleVO, List<MultipartFile> attachFiles) {
		String salt = sha.generateSalt();
		String password = articleVO.getArticlePassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		articleVO.setArticlePassword(encryptedPassword);
		articleVO.setArticleSalt(salt);
		articleDAO.createNewArticleInfo(articleVO);
		for (MultipartFile file: attachFiles) {
			StoredFile storedFile = fileHandler.storeFile(file);
			if (storedFile == null) {
				break;
			}
			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setArticleId(articleVO.getArticleId());
			attachmentVO.setOriginFilename(storedFile.getFileName());
			attachmentVO.setUuidFilename(storedFile.getRealFileName());
			attachmentDAO.storeNewFile(attachmentVO);
		}
		return articleDAO.createNewArticle(articleVO) >0;
	}
	
	@Transactional
	@Override
	public boolean modifyArticle(ArticleVO articleVO) {
		boolean isSuccess = articleDAO.modifyArticleInfo(articleVO) >0 && articleDAO.modifyArticle(articleVO) >0;  
		return isSuccess;
	}
	
	@Transactional
	@Override
	public boolean deleteOneArticle(String articleId) {
		return articleDAO.deleteOneArticle(articleId) >0;
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
}
