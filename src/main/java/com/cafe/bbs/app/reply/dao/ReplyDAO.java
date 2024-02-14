package com.cafe.bbs.app.reply.dao;

import java.util.List;

import com.cafe.bbs.app.reply.vo.ReplyVO;

public interface ReplyDAO {

	public List<ReplyVO> getRepliesByArticleId(String articleId);
	public int getReplyCntByArticleId(String articleId);
	public int createNewReply(ReplyVO replyVO);
	public int modifyOneReply(ReplyVO replyVO);
	public int deleteOneReply(ReplyVO replyVO);
	
}
