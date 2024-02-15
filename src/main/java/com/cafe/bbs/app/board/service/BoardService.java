package com.cafe.bbs.app.board.service;

import java.util.List;

import com.cafe.bbs.app.board.vo.BoardVO;

public interface BoardService {

	public List<BoardVO> getAllBoardList();
	public String getBoardId(String boardUrl);
	public String getBoardUrl(String boardId);
}
