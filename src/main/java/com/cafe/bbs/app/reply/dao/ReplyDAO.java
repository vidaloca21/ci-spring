package com.cafe.bbs.app.reply.dao;

import java.util.List;

import com.cafe.bbs.app.reply.vo.ReplyVO;

public interface ReplyDAO {

	/**
	 * 각 게시글에 작성된 댓글의 목록을 불러온다 
	 * @param articleId 게시글ID
	 * @return 댓글 목록
	 */
	public List<ReplyVO> getRepliesByArticleId(String articleId);
	
	/**
	 * 개별 댓글의 정보를 가져온다
	 * @param replyId 댓글ID
	 * @return 댓글 정보
	 */
	public ReplyVO getOneReplyByReplyId(String replyId);
	
	/**
	 * 각 게시글에 작성된 댓글의 수를 가져온다
	 * @param articleId 게시글ID
	 * @return 댓글의 수
	 */
	public int getReplyCntByArticleId(String articleId);
	
	/**
	 * 새로운 댓글을 DB에 저장한다
	 * @param replyVO 작성할 댓글 정보
	 * @return DB에 insert된 댓글의 수
	 */
	public int createNewReply(ReplyVO replyVO);
	
	/**
	 * 기존 댓글을 DB에서 수정한다
	 * @param replyVO 수정할 댓글 정보
	 * @return DB에 update된 댓글의 수 
	 */
	public int modifyOneReply(ReplyVO replyVO);
	
	/**
	 * 기존 댓글을 비공개한다
	 * @param replyVO 비공개할 댓글 정보
	 * @return DB에 update된 댓글의 수
	 */
	public int deleteOneReply(ReplyVO replyVO);
	
}
