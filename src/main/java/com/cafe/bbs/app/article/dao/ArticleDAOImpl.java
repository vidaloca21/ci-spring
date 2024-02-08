package com.cafe.bbs.app.article.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.bbs.app.article.vo.ArticleVO;

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
	public int getAllArticleCount() {
		return getSqlSession().selectOne("getAllArticleCount");
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
	public int deleteOneArticleByArticleId(String articleId) {
		return getSqlSession().update("deleteOneArticleByArticleId", articleId);
	}
}
