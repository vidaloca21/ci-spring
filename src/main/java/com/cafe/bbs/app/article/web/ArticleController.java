package com.cafe.bbs.app.article.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.cafe.bbs.app.article.vo.validategroup.ArticleCreateGroup;
import com.cafe.bbs.app.article.vo.validategroup.ArticleModifyGroup;
import com.cafe.bbs.app.attachment.service.AttachmentService;
import com.cafe.bbs.app.attachment.vo.AttachmentVO;
import com.cafe.bbs.app.board.service.BoardService;
import com.cafe.bbs.app.board.vo.BoardVO;
import com.cafe.bbs.app.reply.service.ReplyService;
import com.cafe.bbs.app.reply.vo.ReplyVO;
import com.cafe.bbs.exceptions.IncorrectPasswordException;
import com.cafe.bbs.exceptions.PageNotFoundException;
import com.cafe.bbs.exceptions.RequestFailedException;

@Controller
public class ArticleController {
	
	private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	
	
	@GetMapping("")
	public String getIntro() {
		return "intro";
	}
	
	@GetMapping("/{boardUrl}")
	public String getArticleList(@PathVariable String boardUrl
							   , @ModelAttribute SearchArticleVO searchArticleVO
							   , Model model) {
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		if (boardVO == null) {
			throw new PageNotFoundException("페이지가 존재하지 않습니다");
		}
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
	public String getOneArticle(@PathVariable String boardUrl
			  				  , @RequestParam String articleId
			  				  , Model model) {
		ArticleVO article = articleService.getOneArticleByArticleId(articleId);
		if (article == null) {
			throw new PageNotFoundException("페이지가 존재하지 않습니다");
		}
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
	public String createNewArticle(@PathVariable String boardUrl
								 , @RequestParam(name = "upperArticleId", required = false) String upperArticleId
								 , Model model) {
		if (upperArticleId != "") {
			ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(upperArticleId);
			model.addAttribute("upperArticleVO", upperArticleVO);
		}
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleVO", new ArticleVO());
		return "articleWrite";
	}
	
	@PostMapping("/{boardUrl}/write")
	public String createNewArticle(@PathVariable String boardUrl
			 					 , @Validated(ArticleCreateGroup.class)
			 					   @ModelAttribute ArticleVO articleVO
			 					 , BindingResult bindingResult
			 					 , Model model
								 , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles) {
		if (bindingResult.hasErrors()) {
			if (articleVO.getUpperArticleId() != "") {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(articleVO.getUpperArticleId());
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			BoardVO boardVO = boardService.getBoardVO(boardUrl);
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("articleVO", articleVO);
			model.addAttribute("attachFiles", attachFiles);
			return "articleWrite";
		}
		boolean isSuccess = articleService.createNewArticle(articleVO, attachFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:view?articleId="+articleId;
		}
		else {
			throw new RequestFailedException("게시글 등록에 실패하였습니다");
		}
	}
	
	@PostMapping("/{boardUrl}/modify")
	public String modifyArticle(@PathVariable String boardUrl
			 				  , @ModelAttribute ArticleVO articleVO
			 				  , Model model) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		String articleId = articleVO.getArticleId();
		if (isConfirmed) {
			ArticleVO article = articleService.getOneArticleByArticleId(articleId);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			BoardVO boardVO = boardService.getBoardVO(boardUrl);
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("articleVO", article);
			model.addAttribute("fileList", fileList);
			if (article.getUpperArticleId() != null) {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(article.getUpperArticleId());
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			return "articleMdfy";
		} else {
			throw new IncorrectPasswordException("잘못된 비밀번호입니다");
		}
	}
	
	@PostMapping("/{boardUrl}/modify.do")
	public String modifyArticle(@PathVariable String boardUrl
							  , @Validated(ArticleModifyGroup.class)
							    @ModelAttribute ArticleVO articleVO
							  , BindingResult bindingResult
							  , Model model
			 				  , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles
			 				  , @RequestParam(name = "deleteFiles", required = false) List<String> deleteFiles) {
		if (bindingResult.hasErrors()) {
			String articleId = articleVO.getArticleId();
			ArticleVO article = articleService.getOneArticleByArticleId(articleId);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			BoardVO boardVO = boardService.getBoardVO(boardUrl);
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("articleVO", articleVO);
			model.addAttribute("fileList", fileList);
			if (article.getUpperArticleId() != null) {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(article.getUpperArticleId());
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			return "articleMdfy";
		}
		boolean isSuccess = articleService.modifyArticle(articleVO, attachFiles, deleteFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:view?articleId="+articleId;
		}
		else {
			throw new RequestFailedException("게시글 수정에 실패하였습니다");			
		}
	}

	@PostMapping("/{boardUrl}/delete")
	public String deleteOneArticle(@PathVariable String boardUrl
			  					 , @ModelAttribute ArticleVO articleVO) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		if (isConfirmed) {
			boolean isSuccess = articleService.deleteOneArticle(articleVO.getArticleId());
			if (isSuccess) {
				return "redirect:/" +boardUrl;
			} else {
				throw new RequestFailedException("게시글 삭제에 실패하였습니다");
			}
		} else{
			throw new IncorrectPasswordException("잘못된 비밀번호입니다");
		}
	}
	
}
