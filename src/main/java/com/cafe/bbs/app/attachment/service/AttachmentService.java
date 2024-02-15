package com.cafe.bbs.app.attachment.service;

import java.util.List;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

public interface AttachmentService {
	
	public List<AttachmentVO> getAllFilesByArticleId(String articleId);
	public AttachmentVO getOneAttachment(String attachmentId);
	public boolean deleteAttachmentVO(String attachmentId);

}
