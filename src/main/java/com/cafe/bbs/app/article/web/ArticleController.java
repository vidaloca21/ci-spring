package com.cafe.bbs.app.article.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.service.ArticleService;
import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;
import com.cafe.bbs.app.attachment.service.AttachmentService;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.app.reply.service.ReplyService;
import com.cafe.bbs.app.reply.vo.ReplyVO;


@Controller
@RequestMapping("/board")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ReplyService replyService;
	
	
	@GetMapping("")
	public String getAricleList(@ModelAttribute SearchArticleVO searchArticleVO, Model model) {
		List<ArticleVO> articleList = articleService.getAllArticle(searchArticleVO);
		searchArticleVO.setPageCount(articleService.getAllArticleCount());
		Map<String, Object> replyCntMap = new HashMap<>();
		Map<String, Object> fileMap = new HashMap<>();
		for (ArticleVO article : articleList) {
			String articleId = article.getArticleId();
			int replyCnt = replyService.getReplyCntByArticleId(articleId);
			replyCntMap.put(articleId, replyCnt);
			boolean hasFile = attachmentService.hasFile(articleId);
			fileMap.put(articleId, hasFile);
		}
		model.addAttribute("articleList", articleList);
		model.addAttribute("searchArticleVO", searchArticleVO);
		model.addAttribute("replyCnt", replyCntMap);
		model.addAttribute("hasFile", fileMap);
		return "articleList";
	}
	
	@GetMapping("/{articleId}")
	public String getOneArticle(@PathVariable("articleId") String articleId, Model model) {
		ArticleVO article = articleService.getOneArticleByArticleId(articleId);
		List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
		List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
		model.addAttribute("articleVO", article);
		model.addAttribute("replyList", replyList);
		model.addAttribute("fileList", fileList);
		return "articleOne";
	}

	@GetMapping("/write")
	public String createNewArticle(@RequestParam(name = "upperArticleId", required = false) String upperArticleId
								 , Model model) {
		if (upperArticleId != "") {
			ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(upperArticleId);
			model.addAttribute("upperArticleVO", upperArticleVO);
		}
		return "articleWrite";
	}
	
	@PostMapping("/write")
	public String createNewArticle(@ModelAttribute ArticleVO articleVO
								 , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles) {
		articleVO.setBoardId("BD-20240208-000001");
		boolean isSuccess = articleService.createNewArticle(articleVO, attachFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:/board/" +articleId;
		}
		return "articleWrite";
	}
	
	@GetMapping("/modify")
	public String modifyArticle(@ModelAttribute ArticleVO articleVO, Model model) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		if (isConfirmed) {
			ArticleVO article = articleService.getOneArticleByArticleId(articleVO.getArticleId());
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleVO.getArticleId());
			if (articleVO.getUpperArticleId() != "") {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(articleVO.getUpperArticleId());
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			model.addAttribute(article);
			model.addAttribute("fileList", fileList);
			return "articleMdfy";
		} else {
			throw new IllegalArgumentException("비밀번호 틀림!");
		}
	}
	
	@PostMapping("/modify")
	public String modifyArticle(@ModelAttribute ArticleVO articleVO
			 				  , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles) {
		boolean isSuccess = articleService.modifyArticle(articleVO, attachFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:/board/" +articleId;
		}
		return "articleMdfy";
	}
	

	@GetMapping("/delete")
	public String deleteOneArticle(@ModelAttribute ArticleVO articleVO) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		if (isConfirmed) {
			boolean isSuccess = articleService.deleteOneArticle(articleVO.getArticleId());
			if (isSuccess) {
				return "redirect:/board";
			} else {
				throw new IllegalArgumentException("삭제 실패!");
			}
		} else{
			throw new IllegalArgumentException("비밀번호 틀림!");
		}
	}
	
}
