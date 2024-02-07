package com.cafe.bbs.app.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.bbs.app.article.dao.ArticleDAO;
import com.cafe.bbs.app.article.vo.ArticleVO;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDAO articleDAO;
	
	@Override
	public List<ArticleVO> getAllArticle() {
		return articleDAO.getAllArticle();
	}
	
	@Override
	public int getAllArticleCount() {
		return articleDAO.getAllArticleCount();
	}
	
}
