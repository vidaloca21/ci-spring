package com.cafe.bbs.app.article.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.bbs.app.article.service.ArticleService;
import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.article.vo.NextArticleVO;
import com.cafe.bbs.app.article.vo.SearchArticleVO;
import com.cafe.bbs.app.attachment.service.AttachmentService;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.app.board.service.BoardService;
import com.cafe.bbs.app.board.vo.BoardVO;
import com.cafe.bbs.app.reply.service.ReplyService;
import com.cafe.bbs.app.reply.vo.ReplyVO;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	
	

	@GetMapping(value = {"", "/", "/{boardUrl}"})
	public String getArticleList(@PathVariable(name = "boardUrl", required = false) String boardUrl
							  , @ModelAttribute SearchArticleVO searchArticleVO
							  , Model model) {
		if (boardUrl == null) {
			return "intro";
		}
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		if (searchArticleVO.getBoardId() == null) {
			searchArticleVO.setBoardId(boardVO.getBoardId());
		}
		List<ArticleVO> articleList = articleService.getAllArticle(searchArticleVO);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleList", articleList);
		model.addAttribute("searchArticleVO", searchArticleVO);
		return "articleList";
	}
	
	@GetMapping("/{boardUrl}/view")
	public String getOneArticle(@PathVariable("boardUrl") String boardUrl
			  				  , @RequestParam("articleId") String articleId
			  				  , Model model) {
		ArticleVO article = articleService.getOneArticleByArticleId(articleId);
		List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
		List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
		NextArticleVO nextArticle = articleService.getBesideArticle(article);
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleVO", article);
		model.addAttribute("replyList", replyList);
		model.addAttribute("fileList", fileList);
		model.addAttribute("nextArticle", nextArticle);
		return "articleOne";
	}

	@GetMapping("/{boardUrl}/write")
	public String createNewArticle(@PathVariable("boardUrl") String boardUrl
								 , @RequestParam(name = "upperArticleId", required = false) String upperArticleId
								 , Model model) {
		if (upperArticleId != "") {
			ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(upperArticleId);
			model.addAttribute("upperArticleVO", upperArticleVO);
		}
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		model.addAttribute("boardVO", boardVO);
		return "articleWrite";
	}
	
	@PostMapping("/{boardUrl}/write.do")
	public String createNewArticle(@PathVariable("boardUrl") String boardUrl
			 					 , @ModelAttribute ArticleVO articleVO
								 , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles) {
		boolean isSuccess = articleService.createNewArticle(articleVO, attachFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:view?articleId="+articleId;
		}
		return "articleWrite";
	}
	
	@PostMapping("/{boardUrl}/modify")
	public String modifyArticle(@PathVariable("boardUrl") String boardUrl
			 				  , @ModelAttribute ArticleVO articleVO, Model model) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		String articleId = articleVO.getArticleId();
		if (isConfirmed) {
			ArticleVO article = articleService.getOneArticleByArticleId(articleId);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			BoardVO boardVO = boardService.getBoardVO(boardUrl);
			model.addAttribute("boardVO", boardVO);
			model.addAttribute(article);
			model.addAttribute("fileList", fileList);
			if (article.getUpperArticleId() != null) {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(article.getUpperArticleId());
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			return "articleMdfy";
		} else {
			throw new IllegalArgumentException("비밀번호 틀림!");
		}
	}
	
	@PostMapping("/{boardUrl}/modify.do")
	public String modifyArticle(@PathVariable("boardUrl") String boardUrl
			  				  , @ModelAttribute ArticleVO articleVO
			 				  , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles
			 				  , @RequestParam(name = "deleteFiles", required = false) List<String> deleteFiles) {
		boolean isSuccess = articleService.modifyArticle(articleVO, attachFiles, deleteFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:view?articleId="+articleId;
		}
		return "articleMdfy";
	}
	

	@PostMapping("/{boardUrl}/delete")
	public String deleteOneArticle(@PathVariable("boardUrl") String boardUrl
			  					 , @ModelAttribute ArticleVO articleVO) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		if (isConfirmed) {
			boolean isSuccess = articleService.deleteOneArticle(articleVO.getArticleId());
			if (isSuccess) {
				return "redirect:/" +boardUrl;
			} else {
				throw new IllegalArgumentException("삭제 실패!");
			}
		} else{
			throw new IllegalArgumentException("비밀번호 틀림!");
		}
	}
	
}
