package com.cafe.bbs.app.article.dao;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;

public interface ArticleDAO {
	
	/**
	 * DB에 저장된 모든 게시글의 목록을 불러온다
	 * @return 게시글 목록
	 */
	public List<ArticleVO> getAllArticle(); 
	
	/**
	 * 사용자 검색 결과로 표출될 게시글의 목록을 불러온다
	 * @param searchArticleVO 사용자 검색 정보
	 * @return 게시글 목록
	 */
	public List<ArticleVO> searchArticle(SearchArticleVO searchArticleVO);
	
	/**
	 * 사용자 검색 결과로 표출될 게시글의 수를 가져온다
	 * @param searchArticleVO 사용자 검색 정보
	 * @return 게시글의 수
	 */
	public int getArticleCount(SearchArticleVO searchArticleVO);
	
	/**
	 * 게시글 단건의 정보를 조회한다
	 * @param articleId 게시글ID
	 * @return 게시글 정보
	 */
	public ArticleVO getOneArticleByArticleId(String articleId);
	
	/**
	 * DB에 저장된 게시글 단건의 조회수를 증가시킨다
	 * @param articleId 게시글ID
	 * @return DB에 update된 게시글의 수
	 */
	public int increaseViewCount(String articleId);
	
	/**
	 * 새로운 게시글 기본 정보를 DB에 등록한다
	 * @param articleVO 게시글 정보
	 * @return DB에 insert된 게시글의 수
	 */
	public int createNewArticleInfo(ArticleVO articleVO);
	
	/**
	 * 새로운 게시글 기본 정보를 DB에 등록한다
	 * @param articleVO 게시글 정보
	 * @return DB에 insert된 게시글의 수
	 */
	public int createNewArticle(ArticleVO articleVO);

	/**
	 * 기존 게시글 정보를 DB에서 수정한다
	 * @param articleVO 수정할 게시글 정보
	 * @return DB에 update된 게시글의 수
	 */
	public int modifyArticleInfo(ArticleVO articleVO);
	
	/**
	 * 기존 게시글 정보를 DB에서 수정한다
	 * @param articleVO 수정할 게시글 정보
	 * @return DB에 update된 게시글의 수
	 */
	public int modifyArticle(ArticleVO articleVO);
	
	/**
	 * 게시글 단건을 비공개 처리한다
	 * @param articleId 비공개 처리할 게시글ID
	 * @return DB에 update된 게시글의 수
	 */
	public int deleteOneArticle(String articleId);
	
	/**
	 * 게시글 단건의 비밀번호 정보를 가져온다
	 * @param articleId 게시글ID
	 * @return 비밀번호 정보(비밀번호, salt)
	 */
	public ArticleVO getArticlePassword(String articleId);
	
	/**
	 * 현재 게시글의 이전글과 다음글의 정보를 가져온다
	 * @param articleVO 현재 게시글 정보 
	 * @return 이전글과 다음글의 게시글 정보(ID, 제목)
	 */
	public NextArticleVO getBesideArticle(ArticleVO articleVO);
}
