package com.cafe.bbs.app.attachment.service;

import java.util.Arrays;
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
		List<AttachmentVO> fileList = attachmentDAO.getAllFilesByArticleId(articleId);
		List<String> imgList = Arrays.asList(".PNG", ".JPG", ".JPEG", ".WEBP");
		for (AttachmentVO file: fileList) {
			if( imgList.contains(file.getOriginFilename().toUpperCase().substring( file.getOriginFilename().lastIndexOf(".") )) ) {
				file.setIsImage("Y");
			} else {
				file.setIsImage("N");
			};
		}
		return fileList;
	}
	
	@Override
	public AttachmentVO getOneAttachment(String attachmentId) {
		return attachmentDAO.getOneAttachment(attachmentId);
	}
	
}
