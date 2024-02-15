package com.cafe.bbs.app.board.dao;

import java.util.List;

import com.cafe.bbs.app.board.vo.BoardVO;

public interface BoardDAO {
	
	public List<BoardVO> getAllBoardList();
	public String getBoardId(String boardUrl);
	public String getBoardUrl(String boardId);
	public BoardVO getBoardVO(String boardUrl);

}
