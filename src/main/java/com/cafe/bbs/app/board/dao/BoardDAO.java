package com.cafe.bbs.app.board.dao;

import com.cafe.bbs.app.board.vo.BoardVO;

public interface BoardDAO {
	
	/**
	 * 게시판의 정보를 가져온다
	 * @param boardUrl 게시판URL
	 * @return 게시판 정보
	 */
	public BoardVO getBoardVO(String boardUrl);
	
	/**
	 * 게시판의 정보를 가져온다
	 * @param boardId 게시판ID
	 * @return 게시판 정보
	 */
	public BoardVO getBoardVOById(String boardId);

}
