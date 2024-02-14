package com.cafe.bbs.app.reply.service;

import java.util.List;

import com.cafe.bbs.app.reply.vo.ReplyVO;

public interface ReplyService {

	public List<ReplyVO> getRepliesByArticleId(String articleId);
	public int getReplyCntByArticleId(String articleId);
	public boolean createNewReply(ReplyVO replyVO);
	public boolean modifyOneReply(ReplyVO replyVO);
	public boolean deleteOneReply(ReplyVO replyVO);
	
}
