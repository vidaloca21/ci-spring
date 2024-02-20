package com.cafe.bbs.app.board.service;

import com.cafe.bbs.app.board.vo.BoardVO;

public interface BoardService {

	public BoardVO getBoardVO(String boardUrl);
	public BoardVO getBoardVOById(String boardId);
}
