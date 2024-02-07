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

}
