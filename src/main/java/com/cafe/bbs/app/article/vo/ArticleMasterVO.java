package com.cafe.bbs.app.article.vo;

import com.cafe.bbs.app.article.vo.validategroup.ArticleCreateGroup;
import com.cafe.bbs.app.article.vo.validategroup.ArticleModifyGroup;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ArticleMasterVO {
	
	private String articleId;
	private String boardId;
	@NotEmpty(groups = {ArticleCreateGroup.class, ArticleModifyGroup.class}, message = "작성자 이름을 입력하세요")
	@Size(groups = {ArticleCreateGroup.class, ArticleModifyGroup.class}, max = 10, message = "최대 10자까지 입력 가능합니다")
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
