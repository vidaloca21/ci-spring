package com.cafe.bbs.app.article.dao;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;

public interface ArticleDAO {
	
	public List<ArticleVO> getAllArticle(); 
	public int getAllArticleCount();
	public ArticleVO getOneArticleByArticleId(String articleId);
	public int increaseViewCount(String articleId);
	
	public int createNewArticleInfo(ArticleVO articleVO);
	public int createNewArticle(ArticleVO articleVO);

	public int modifyArticleInfo(ArticleVO articleVO);
	public int modifyArticle(ArticleVO articleVO);
	
	public int deleteOneArticleByArticleId(String articleId);
}
