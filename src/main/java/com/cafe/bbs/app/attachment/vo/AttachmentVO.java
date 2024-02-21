package com.cafe.bbs.app.attachment.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AttachmentVO {

	private String attachmentId;
	private String articleId;
	@NotBlank
	@Size(max = 100)
	private String originFilename;
	private String uuidFilename;
	private String uploadDate;
	private String isImage;
	
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getOriginFilename() {
		return originFilename;
	}
	public void setOriginFilename(String originFilename) {
		this.originFilename = originFilename;
	}
	public String getUuidFilename() {
		return uuidFilename;
	}
	public void setUuidFilename(String uuidFilename) {
		this.uuidFilename = uuidFilename;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getIsImage() {
		return isImage;
	}
	public void setIsImage(String isImage) {
		this.isImage = isImage;
	}
	
}
