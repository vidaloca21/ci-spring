package com.cafe.bbs.app.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.bbs.app.reply.dao.ReplyDAO;
import com.cafe.bbs.app.reply.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> getRepliesByArticleId(String articleId) {
		return replyDAO.getRepliesByArticleId(articleId);
	}
	
	@Override
	public int getReplyCntByArticleId(String articleId) {
		return replyDAO.getReplyCntByArticleId(articleId);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		return replyDAO.createNewReply(replyVO) >0;
	}
	
	@Transactional
	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		return replyDAO.modifyOneReply(replyVO) >0;
	}
	
	@Transactional
	@Override
	public boolean deleteOneReply(ReplyVO replyVO) {
		return replyDAO.deleteOneReply(replyVO) >0;
	}
}
