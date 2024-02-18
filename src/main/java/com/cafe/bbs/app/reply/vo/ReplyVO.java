package com.cafe.bbs.app.reply.vo;

import com.cafe.bbs.app.article.vo.validategroup.ArticleCreateGroup;
import com.cafe.bbs.app.article.vo.validategroup.ArticleModifyGroup;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ReplyVO {

	private String replyId;
	private String articleId;
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Size(max = 12, message = "")
	private String memberName;
	@NotEmpty(message = "제목을 입력하세요")
	@Size(max = 200, message = "최대 200자까지 입력 가능합니다")
	private String replyContent;
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하로 입력해주세요")
	private String replyPassword;
	private String replyCreateDate;
	private String replyModifyDate;
	private String replyDeleteDate;
	
	
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyPassword() {
		return replyPassword;
	}
	public void setReplyPassword(String replyPassword) {
		this.replyPassword = replyPassword;
	}
	public String getReplyCreateDate() {
		return replyCreateDate;
	}
	public void setReplyCreateDate(String replyCreateDate) {
		this.replyCreateDate = replyCreateDate;
	}
	public String getReplyModifyDate() {
		return replyModifyDate;
	}
	public void setReplyModifyDate(String replyModifyDate) {
		this.replyModifyDate = replyModifyDate;
	}
	public String getReplyDeleteDate() {
		return replyDeleteDate;
	}
	public void setReplyDeleteDate(String replyDeleteDate) {
		this.replyDeleteDate = replyDeleteDate;
	}	
	
}
