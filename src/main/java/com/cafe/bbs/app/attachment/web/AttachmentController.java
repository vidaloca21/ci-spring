package com.cafe.bbs.app.attachment.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cafe.bbs.app.attachment.service.AttachmentService;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.beans.FileHandler;
import com.cafe.bbs.beans.FileHandler.StoredFile;


@Controller
public class AttachmentController {
	
	private final static Logger logger = LoggerFactory.getLogger(AttachmentController.class);
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private FileHandler fileHandler;
	
	/*
	 * 파일 다운로드 수행을 위한 controller
	 * DB에 저장된 파일 정보를 바탕으로 서버에 저장된 파일을 ResponseEntity로 반환
	 */
	@GetMapping("/file/download/{attachmentId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String attachmentId) {
		AttachmentVO attachmentVO = attachmentService.getOneAttachment(attachmentId);
		File storedFile = fileHandler.getStoredFile(attachmentVO.getUuidFilename(), false);
		ResponseEntity<Resource> resource = fileHandler.getResponseEntity(storedFile, attachmentVO.getOriginFilename());
		logger.info("fileDownload: " + resource.getStatusCode());
		return resource;
	}

	@ResponseBody
	@PostMapping("/api/image/upload")
	public Map<String, Object> imageUploader(MultipartHttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		List<MultipartFile> fileList = request.getFiles("upload");
		for (MultipartFile file : fileList) {
			if (file.getSize() > 0 && file.getContentType().startsWith("image")) {
				StoredFile storedFile = fileHandler.storeFile(file, true);
				result.put("uploaded", true);
				result.put("filename", storedFile.getFileName());
				result.put("url", "/api/temp/" +storedFile.getRealFileName());
			}
		}
		return result;
	}
	
	@GetMapping("/api/{isTemp}/{filename}")
	public ResponseEntity<byte[]> getTempImg(@PathVariable String isTemp, @PathVariable String filename) {
		File storedFile = fileHandler.getStoredFile(filename, isTemp.equals("temp"));
		if (storedFile == null) {
			throw new IllegalArgumentException("파일이 존재하지 않습니다.");
		}
		try {
			byte[] imageBytes = Files.readAllBytes(storedFile.toPath());
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			
			return new ResponseEntity<>(imageBytes, header, HttpStatus.OK);
		} catch (IOException e) {
			throw new IllegalArgumentException("이미지를 불러올 수 없습니다.");
		}
	}
	
}
