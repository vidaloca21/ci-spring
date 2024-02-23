package com.cafe.bbs.app.article.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;

public interface ArticleService {

	/**
	 * 사용자 검색 결과로 표출될 게시글의 목록을 불러온다
	 * @param searchArticleVO 사용자 검색 정보
	 * @return 게시글 목록
	 */
	public List<ArticleVO> getAllArticle(SearchArticleVO searchArticleVO);
	
	/**
	 * 사용자 검색 결과로 표출될 게시글의 수를 가져온다
	 * @param searchArticleVO 사용자 검색 정보
	 * @return 게시글의 수
	 */
	public int getArticleCount(SearchArticleVO searchArticleVO);
	
	/**
	 * 게시글 단건의 정보를 조회한다
	 * @param articleId 게시글ID
	 * @param isIncrease 조회수를 증가시킬지 여부
	 * @return 게시글 정보
	 */
	public ArticleVO getOneArticleByArticleId(String articleId, boolean isIncrease);
	
	/**
	 * 새로운 게시글 기본 정보를 DB에 등록한다
	 * @param articleVO 게시글 정보
	 * @param attachFiles 게시글에 첨부된 파일 목록
	 * @return DB에 insert 성공 여부
	 */
	
	public boolean createNewArticle(ArticleVO articleVO, List<MultipartFile> attachFiles);
	
	/**
	 * 기존 게시글 정보를 DB에서 수정한다
	 * @param articleVO 수정할 게시글 정보
	 * @param attachFiles 게시글에 첨부된 파일 목록
	 * @param deleteFiles 기존 게시글에서 삭제할 파일 목록
	 * @return DB update 성공 여부
	 */
	public boolean modifyArticle(ArticleVO articleVO, List<MultipartFile> attachFiles, List<String> deleteFiles);
	
	/**
	 * 게시글 단건을 비공개 처리한다
	 * @param articleId 비공개 처리할 게시글ID
	 * @return DB에 update 성공 여부
	 */
	public boolean deleteOneArticle(String articleId);
	
	/**
	 * 사용자가 입력한 게시글의 비밀번호와 DB에 저장된 게시글의 비밀번호를 비교한다
	 * @param articleVO 사용자 입력 게시글 정보(ID, 비밀번호)
	 * @return 비밀번호 일치 여부
	 */
	public boolean confirmPassword(ArticleVO articleVO);
	
	/**
	 * 현재 게시글의 이전글과 다음글의 정보를 가져온다
	 * @param articleVO 현재 게시글 정보 
	 * @return 이전글과 다음글의 게시글 정보(ID, 제목
	 */
	public NextArticleVO getBesideArticle(ArticleVO articleVO);
	
	
	/**
	 * article.web.ArticleTest.java 전용
	 * Pagination 테스트용 Dummy 게시글 upload 위해
	 * MultipartFile 없이 게시글을 DB에 insert method overloading
	 * @param articleVO
	 * @return DB insert 성공 여부
	 */
	public boolean createNewArticle(ArticleVO articleVO);
}
