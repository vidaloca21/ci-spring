package com.cafe.bbs.app.article.service;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;

public interface ArticleService {

	public List<ArticleVO> getAllArticle();
	public int getAllArticleCount();
}
