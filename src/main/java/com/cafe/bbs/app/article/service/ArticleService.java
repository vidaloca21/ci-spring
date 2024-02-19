package com.cafe.bbs.app.article.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;

public interface ArticleService {

	public List<ArticleVO> getAllArticle(SearchArticleVO searchArticleVO);
	public ArticleVO getOneArticleByArticleId(String articleId, boolean isIncrease);
	public boolean createNewArticle(ArticleVO articleVO, List<MultipartFile> attachFiles);
	public boolean modifyArticle(ArticleVO articleVO, List<MultipartFile> attachFiles, List<String> deleteFiles);
	public boolean deleteOneArticle(String articleId);
	public boolean confirmPassword(ArticleVO articleVO);
	public NextArticleVO getBesideArticle(ArticleVO articleVO);
}
