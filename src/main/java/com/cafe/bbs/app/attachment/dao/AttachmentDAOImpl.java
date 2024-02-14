package com.cafe.bbs.app.attachment.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

@Repository
public class AttachmentDAOImpl extends SqlSessionDaoSupport implements AttachmentDAO {
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<AttachmentVO> getAllFilesByArticleId(String articleId) {
		return getSqlSession().selectList("getAllFilesByArticleId", articleId);
	}
	
	@Override
	public AttachmentVO getOneAttachment(String attachmentId) {
		return getSqlSession().selectOne("getOneAttachment", attachmentId);
	}
	
	@Override
	public int storeNewFile(AttachmentVO attachmentVO) {
		return getSqlSession().insert("storeNewFile", attachmentVO);
	}
}
