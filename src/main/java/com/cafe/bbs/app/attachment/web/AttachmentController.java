package com.cafe.bbs.app.attachment.web;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafe.bbs.app.attachment.service.AttachmentService;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.beans.FileHandler;

@Controller
public class AttachmentController {
	
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private FileHandler fileHandler;
	
	@GetMapping("/file/download/{attachmentId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String attachmentId) {
		AttachmentVO attachmentVO = attachmentService.getOneAttachment(attachmentId);
		File storedFile = fileHandler.getStoredFile(attachmentVO.getUuidFilename());
		return fileHandler.getResponseEntity(storedFile, attachmentVO.getOriginFilename());
	}

}
