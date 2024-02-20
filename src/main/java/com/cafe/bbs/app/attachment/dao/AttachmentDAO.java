package com.cafe.bbs.app.attachment.dao;

import java.util.List;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

public interface AttachmentDAO {

	/**
	 * DB에 저장된 게시글별 파일 정보를 조회
	 * @param articleId 게시글ID 
	 * @return 해당 게시글에 첨부된 파일 목록
	 */
	public List<AttachmentVO> getAllFilesByArticleId(String articleId);
	
	/**
	 * DB에 저장된 게시글별 첨부파일 수를 조회
	 * @param articleId 게시글ID
	 * @return 해당 게시글에 첨부된 파일의 수
	 */
	public int getFileCntByArticleId(String articleId);
	
	/**
	 * 첨부 파일의 개별 정보를 조회 
	 * @param attachmentId 첨부파일ID
	 * @return 해당 첨부파일의 정보
	 */
	public AttachmentVO getOneAttachment(String attachmentId);
	
	/**
	 * 새로운 파일을 저장
	 * @param attachmentVO 저장할 첨부파일의 정보
	 * @return DB에 insert된 첨부파일의 수
	 */
	public int storeNewFile(AttachmentVO attachmentVO);
	
	/**
	 * DB에 저장된 파일 정보를 삭제
	 * @param attachmentId 삭제할 첨부파일ID
	 * @return 삭제된 첨부 파일의 수 
	 */
	public int deleteAttachment(String attachmentId);
}
