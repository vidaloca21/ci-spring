package com.cafe.bbs.app.article.vo;

import com.cafe.bbs.app.common.vo.AbstractSearchVO;

public class SearchArticleVO extends AbstractSearchVO{
	
	private String boardId;
	private String searchType;
	private String keyword;

	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
