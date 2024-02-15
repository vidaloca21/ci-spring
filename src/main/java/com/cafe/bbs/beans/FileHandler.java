package com.cafe.bbs.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.exceptions.FileNotExistsException;

public class FileHandler {

	private String baseDir;
	private boolean enableObfuscation;
	private boolean enableObfuscationHideExt;

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setEnableObfuscation(boolean enableObfuscation) {
		this.enableObfuscation = enableObfuscation;
	}

	public void setEnableObfuscationHideExt(boolean enableObfuscationHideExt) {
		this.enableObfuscationHideExt = enableObfuscationHideExt;
	}

	public File getStoredFile(String fileName) {
		return new File(baseDir, fileName);
	}

	public ResponseEntity<Resource> getResponseEntity(File downloadFile
													, String downloadFileName) {
		HttpHeaders header = new HttpHeaders();
		try {
			header.add(HttpHeaders.CONTENT_DISPOSITION
					  , "attachment; filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(downloadFile));
		} catch (FileNotFoundException e) {
			throw new FileNotExistsException("파일이 존재하지 않습니다.");
		}
		
		return ResponseEntity.ok()
					.headers(header)
					.contentLength(downloadFile.length())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);
	}
	
	public StoredFile storeFile(MultipartFile multipartFile) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return null;
		}
		String originalFileName = multipartFile.getOriginalFilename();
		String fileName = getObfuscationFileName(originalFileName);
		File storePath = new File(baseDir, fileName);
		if (!storePath.getParentFile().exists()) {
			storePath.getParentFile().mkdirs();
		}
		try {
			multipartFile.transferTo(storePath);
		} catch (IllegalStateException | IOException e) {
			return null;
		}
		return new StoredFile(originalFileName, storePath);
	}
	
	private String getObfuscationFileName(String fileName) {
		if (enableObfuscation) {
			String ext = fileName.substring( fileName.lastIndexOf(".") );
			String obfuscationName = UUID.randomUUID().toString();
			if (enableObfuscationHideExt) {
				return obfuscationName;
			}
			return obfuscationName + ext;
		}
		return fileName;
	}
	
	public class StoredFile {
		private String fileName;
		private String realFileName;
		private String realFilePath;
		private long fileSize;
		
		public StoredFile(String fileName, File storeFile) {
			this.fileName = fileName;
			this.realFileName = storeFile.getName();
			this.realFilePath = storeFile.getAbsolutePath();
			this.fileSize = storeFile.length();
		}

		public String getFileName() {
			return fileName;
		}

		public String getRealFileName() {
			return realFileName;
		}

		public String getRealFilePath() {
			return realFilePath;
		}

		public long getFileSize() {
			return fileSize;
		}
		
	}
	
}
