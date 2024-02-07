package com.cafe.bbs.app.article.dao;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;

public interface ArticleDAO {
	
	public List<ArticleVO> getAllArticle(); 
	public int getAllArticleCount();

}
