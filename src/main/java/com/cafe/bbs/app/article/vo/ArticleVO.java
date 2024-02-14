package com.cafe.bbs.app.article.vo;

public class ArticleVO extends ArticleMasterVO {
	
	private ArticleMasterVO articleMasterVO;
	private String articleId;
	private String upperArticleId;
	private String articleTitle;
	private String articleContent;
	private String articlePassword;
	private int viewCnt;
	private int level;
	
	public ArticleMasterVO getArticleMasterVO() {
		return articleMasterVO;
	}
	public void setArticleMasterVO(ArticleMasterVO articleMasterVO) {
		this.articleMasterVO = articleMasterVO;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getUpperArticleId() {
		return upperArticleId;
	}
	public void setUpperArticleId(String upperArticleId) {
		this.upperArticleId = upperArticleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public String getArticlePassword() {
		return articlePassword;
	}
	public void setArticlePassword(String articlePassword) {
		this.articlePassword = articlePassword;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
