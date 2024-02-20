package com.cafe.bbs.app.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.bbs.app.reply.dao.ReplyDAO;
import com.cafe.bbs.app.reply.vo.ReplyVO;
import com.cafe.bbs.beans.SHA;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;
	@Autowired
	private SHA sha;
	
	@Override
	public List<ReplyVO> getRepliesByArticleId(String articleId) {
		return replyDAO.getRepliesByArticleId(articleId);
	}
	
	@Override
	public ReplyVO getOneReplyByReplyId(String replyId) {
		return replyDAO.getOneReplyByReplyId(replyId);
	}
	
	@Override
	public int getReplyCntByArticleId(String articleId) {
		return replyDAO.getReplyCntByArticleId(articleId);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		String salt = sha.generateSalt();
		String password = replyVO.getReplyPassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		replyVO.setReplyPassword(encryptedPassword);
		replyVO.setReplySalt(salt);
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
	
	@Override
	public boolean confirmReplyPassword(ReplyVO replyVO) {
		String replyId = replyVO.getReplyId();
		ReplyVO originReply = replyDAO.getOneReplyByReplyId(replyId);
		String originPassword = originReply.getReplyPassword();
		String salt = originReply.getReplySalt();
		String userPassword = replyVO.getReplyPassword();
		String encryptedPassword = sha.getEncrypt(userPassword, salt);
		return originPassword.equals(encryptedPassword);
	}
}
