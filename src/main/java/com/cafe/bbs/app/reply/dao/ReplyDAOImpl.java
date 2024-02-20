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
	public ReplyVO getOneReplyByReplyId(String replyId) {
		return getSqlSession().selectOne("getOneReplyByReplyId", replyId);
	}
	
	@Override
	public int getReplyCntByArticleId(String articleId) {
		return getSqlSession().selectOne("getReplyCntByArticleId", articleId);
	}
	
	@Override
	public int createNewReply(ReplyVO replyVO) {
		return getSqlSession().insert("createNewReply", replyVO);
	}
	
	@Override
	public int modifyOneReply(ReplyVO replyVO) {
		return getSqlSession().update("modifyOneReply", replyVO);
	}
	
	@Override
	public int deleteOneReply(ReplyVO replyVO) {
		return getSqlSession().update("deleteOneReply", replyVO);
	}


}
