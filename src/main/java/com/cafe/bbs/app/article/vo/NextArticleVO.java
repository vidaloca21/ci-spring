package com.cafe.bbs.app.article.vo;

public class NextArticleVO {

	private String prevArticleId;
	private String prevArticleTitle;
	private String articleId;
	private String nextArticleId;
	private String nextArticleTitle;
	
	
	public String getPrevArticleId() {
		return prevArticleId;
	}
	public void setPrevArticleId(String prevArticleId) {
		this.prevArticleId = prevArticleId;
	}
	public String getPrevArticleTitle() {
		return prevArticleTitle;
	}
	public void setPrevArticleTitle(String prevArticleTitle) {
		this.prevArticleTitle = prevArticleTitle;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getNextArticleId() {
		return nextArticleId;
	}
	public void setNextArticleId(String nextArticleId) {
		this.nextArticleId = nextArticleId;
	}
	public String getNextArticleTitle() {
		return nextArticleTitle;
	}
	public void setNextArticleTitle(String nextArticleTitle) {
		this.nextArticleTitle = nextArticleTitle;
	}

}
