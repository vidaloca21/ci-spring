package com.cafe.bbs.app.attachment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.bbs.app.attachment.dao.AttachmentDAO;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	private AttachmentDAO attachmentDAO;
	
	@Override
	public List<AttachmentVO> getAllFilesByArticleId(String articleId) {
		return attachmentDAO.getAllFilesByArticleId(articleId);
	}
	
	@Override
	public AttachmentVO getOneAttachment(String attachmentId) {
		return attachmentDAO.getOneAttachment(attachmentId);
	}
	
}
