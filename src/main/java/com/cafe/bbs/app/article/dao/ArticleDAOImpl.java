package com.cafe.bbs.app.article.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;

@Repository
public class ArticleDAOImpl extends SqlSessionDaoSupport implements ArticleDAO{
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<ArticleVO> getAllArticle() {
		return getSqlSession().selectList("getAllArticle");
	}
	
	@Override
	public List<ArticleVO> searchArticle(SearchArticleVO searchArticleVO) {
		return getSqlSession().selectList("searchArticle", searchArticleVO);
	}
	
	@Override
	public int getArticleCount(SearchArticleVO searchArticleVO) {
		return getSqlSession().selectOne("getArticleCount", searchArticleVO);
	}

	@Override
	public ArticleVO getOneArticleByArticleId(String articleId) {
		return getSqlSession().selectOne("getOneArticleByArticleId", articleId);
	}
	
	@Override
	public int increaseViewCount(String articleId) {
		return getSqlSession().update("increaseViewCount", articleId);
	}
	
	@Override
	public int createNewArticleInfo(ArticleVO articleVO) {
		return getSqlSession().insert("createNewArticleInfo", articleVO);
	}
	
	@Override
	public int createNewArticle(ArticleVO articleVO) {
		return getSqlSession().insert("createNewArticle", articleVO);
	}

	@Override
	public int modifyArticleInfo(ArticleVO articleVO) {
		return getSqlSession().update("modifyArticleInfo", articleVO);
	}
	@Override
	public int modifyArticle(ArticleVO articleVO) {
		return getSqlSession().update("modifyArticle", articleVO);
	}
	
	@Override
	public int deleteOneArticle(String articleId) {
		return getSqlSession().update("deleteOneArticle", articleId);
	}
	
	@Override
	public ArticleVO getArticlePassword(String articleId) {
		return getSqlSession().selectOne("getArticlePassword", articleId);
	}
	
	@Override
	public NextArticleVO getBesideArticle(ArticleVO articleVO) {
		return getSqlSession().selectOne("getBesideArticle", articleVO);
	}
}
