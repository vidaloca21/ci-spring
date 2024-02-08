package com.cafe.bbs.app.article.service;

import java.util.List;

import com.cafe.bbs.app.article.vo.ArticleVO;

public interface ArticleService {

	public List<ArticleVO> getAllArticle();
	public int getAllArticleCount();
	public ArticleVO getOneArticleByArticleId(String articleId);
	public boolean createNewArticle(ArticleVO articleVO);
	public boolean modifyArticle(ArticleVO articleVO);
	public boolean deleteOneArticleByArticleId(String articleId);
}
