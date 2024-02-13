package com.cafe.bbs.app.article.dao;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;

public interface ArticleDAO {
	
	public List<ArticleVO> getAllArticle(); 
	public List<ArticleVO> searchArticle(SearchArticleVO searchArticleVO);
	public int getAllArticleCount();
	public ArticleVO getOneArticleByArticleId(String articleId);
	public int increaseViewCount(String articleId);
	
	public int createNewArticleInfo(ArticleVO articleVO);
	public int createNewArticle(ArticleVO articleVO);

	public int modifyArticleInfo(ArticleVO articleVO);
	public int modifyArticle(ArticleVO articleVO);
	
	public int deleteOneArticleByArticleId(String articleId);
}
