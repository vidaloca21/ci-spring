package com.cafe.bbs.app.attachment.service;

import java.util.List;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

public interface AttachmentService {
	
	/**
	 * 게시글에 첨부된 파일의 목록을 가져온다
	 * @param articleId 게시글ID
	 * @return 파일목록
	 */
	public List<AttachmentVO> getAllFilesByArticleId(String articleId);
	
	/**
	 * 개별 파일의 정보를 가져온다
	 * @param attachmentId 파일정보ID
	 * @return 개별 파일의 정보
	 */
	public AttachmentVO getOneAttachment(String attachmentId);
	
	public boolean imageUploader();
	
}
