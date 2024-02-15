package com.cafe.bbs.app.article.vo;

public class ArticleVO extends ArticleMasterVO {
	
	private ArticleMasterVO articleMasterVO;
	private String articleId;
	private String upperArticleId;
	private String articleTitle;
	private String articleContent;
	private String articlePassword;
	private String articleSalt;
	private int viewCnt;
	private int level;
	private int replyCnt;
	private int fileCnt;
	private String articleNum;
	
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
	public String getArticleSalt() {
		return articleSalt;
	}
	public void setArticleSalt(String articleSalt) {
		this.articleSalt = articleSalt;
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
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	public int getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
	}
	public String getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}

}
