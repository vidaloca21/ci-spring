package com.cafe.bbs.app.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Override
	public ArticleVO getOneArticleByArticleId(String articleId) {
		articleDAO.increaseViewCount(articleId);
		ArticleVO articleVO = articleDAO.getOneArticleByArticleId(articleId);
		return articleVO;
	}
	
	@Transactional
	@Override
	public boolean createNewArticle(ArticleVO articleVO) {
		articleDAO.createNewArticleInfo(articleVO);
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
	public boolean deleteOneArticleByArticleId(String articleId) {
		boolean isSuccess = articleDAO.deleteOneArticleByArticleId(articleId) >0;
		return isSuccess;
	}
}
