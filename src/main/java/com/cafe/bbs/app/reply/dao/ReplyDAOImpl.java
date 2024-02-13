package com.cafe.bbs.app.reply.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.bbs.app.reply.vo.ReplyVO;

@Repository
public class ReplyDAOImpl extends SqlSessionDaoSupport implements ReplyDAO {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<ReplyVO> getRepliesByArticleId(String articleId) {
		return getSqlSession().selectList("getRepliesByArticleId", articleId);
	}
	
	@Override
	public int createNewReply(ReplyVO replyVO) {
		return getSqlSession().insert("createNewReply", replyVO);
	}
	
	@Override
	public int deleteOneReplyByReplyId(String replyId) {
		return getSqlSession().update("deleteOneReplyByReplyId", replyId);
	}

}
