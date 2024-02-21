package com.cafe.bbs.app.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.bbs.app.board.dao.BoardDAO;
import com.cafe.bbs.app.board.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public BoardVO getBoardVO(String boardUrl) {
		return boardDAO.getBoardVO(boardUrl);
	}
	
	@Override
	public BoardVO getBoardVOById(String boardId) {
		return boardDAO.getBoardVOById(boardId);
	}
	
	@Override
	public List<BoardVO> getAllBoard() {
		return boardDAO.getAllBoard();
	}
}
