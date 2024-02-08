package com.cafe.bbs.app.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.bbs.app.board.vo.BoardVO;


@Repository
public class BoardDAOImpl extends SqlSessionDaoSupport implements BoardDAO {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<BoardVO> getAllBoardList() {
		return getSqlSession().selectList("getAllBoardList");
	}
}
