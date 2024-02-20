package com.cafe.bbs.app.board.dao;

import com.cafe.bbs.app.board.vo.BoardVO;

public interface BoardDAO {
	
	public BoardVO getBoardVO(String boardUrl);
	public BoardVO getBoardVOById(String boardId);

}
