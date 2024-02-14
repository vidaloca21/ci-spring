package com.cafe.bbs.app.attachment.dao;

import java.util.List;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

public interface AttachmentDAO {

	public List<AttachmentVO> getAllFilesByArticleId(String articleId);
	public AttachmentVO getOneAttachment(String attachmentId);
	public int storeNewFile(AttachmentVO attachmentVO);
}
