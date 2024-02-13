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
	
	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		return replyDAO.createNewReply(replyVO) >0;
	}
	
	@Transactional
	@Override
	public boolean deleteOneReplyByReplyId(String replyId) {
		return replyDAO.deleteOneReplyByReplyId(replyId) >0;
	}
}
