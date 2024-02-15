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
	public List<BoardVO> getAllBoardList() {
		return boardDAO.getAllBoardList();
	}
	
	@Override
	public String getBoardId(String boardUrl) {
		return boardDAO.getBoardId(boardUrl);
	}
	
	@Override
	public String getBoardUrl(String boardId) {
		return boardDAO.getBoardUrl(boardId);
	}
}
