package com.cafe.bbs.app.article.vo;

public class ArticleMasterVO {
	
	private String articleId;
	private String boardId;
	private String memberName;
	private String articleCreateDate;
	private String articleModifyDate;
	private String articleDeleteDate;
	
	
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getArticleCreateDate() {
		return articleCreateDate;
	}
	public void setArticleCreateDate(String articleCreateDate) {
		this.articleCreateDate = articleCreateDate;
	}
	public String getArticleModifyDate() {
		return articleModifyDate;
	}
	public void setArticleModifyDate(String articleModifyDate) {
		this.articleModifyDate = articleModifyDate;
	}
	public String getArticleDeleteDate() {
		return articleDeleteDate;
	}
	public void setArticleDeleteDate(String articleDeleteDate) {
		this.articleDeleteDate = articleDeleteDate;
	}

}
