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
import com.cafe.bbs.exceptions.PageNotFoundException;
import com.cafe.bbs.exceptions.RequestFailedException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {
	
	private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private AttachmentService attachmentService;
	
	@GetMapping("")
	public String getIntro() {
		return "intro";
	}
	
	/*
	 * 게시글 목록(게시판) controller
	 * 게시판 URL이 잘못되었을 경우 PageNotFoundException 반환
	 */
	@GetMapping("/{boardUrl}")
	public String getArticleList(@PathVariable String boardUrl
							   , @ModelAttribute SearchArticleVO searchArticleVO
							   , HttpServletRequest request
							   , Model model) {
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		if (boardVO == null) {
			throw new PageNotFoundException("페이지가 존재하지 않습니다");
		}
		searchArticleVO.setBoardId(boardVO.getBoardId());
		searchArticleVO.setPageCount(articleService.getArticleCount(searchArticleVO));

		List<ArticleVO> articleList = articleService.getAllArticle(searchArticleVO);
		String nextUrl = (String)request.getHeader("REFERER");
		model.addAttribute("nextUrl", nextUrl);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleList", articleList);
		model.addAttribute("searchArticleVO", searchArticleVO);
		return "articleList";
	}
	
	/*
	 * 게시글 단건조회 controller
	 * 게시글 정보를 불러오지 못했을 경우 PageNotFoundException을 반환
	 */
	@GetMapping("/{boardUrl}/view/{articleId}")
	public String getOneArticle(@PathVariable String boardUrl
			  				  , @PathVariable String articleId
							  , HttpServletRequest request
			  				  , Model model) {
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		ArticleVO article = articleService.getOneArticleByArticleId(articleId, true);
		if (boardVO == null || article == null || !article.getBoardId().equals(boardVO.getBoardId())) {
			throw new PageNotFoundException("페이지가 존재하지 않습니다");
		}
		List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
		List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
		NextArticleVO nextArticle = articleService.getBesideArticle(article);
		String nextUrl = (String)request.getHeader("REFERER");
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleVO", article);
		model.addAttribute("replyList", replyList);
		model.addAttribute("fileList", fileList);
		model.addAttribute("nextArticle", nextArticle);
		model.addAttribute("nextUrl", nextUrl);
		return "articleOne";
	}
	
	/*
	 * 댓글 수정을 위한 게시글 단건조회 controller
	 * 댓글 비밀번호 검증을 위해 PostMapping으로 구현
	 * 사용자가 전송한 댓글 비밀번호와 DB에 저장된 댓글 비밀번호 비교 후,
	 * 비밀번호가 일치하면 수정할 댓글의 원본 정보를 함께 반환
	 * 비밀번호가 일치하지 않으면 IncorrectPasswordException 반환
	 */
	@PostMapping("/{boardUrl}/view/{articleId}")
	public String getOneArticleWithModifyReply(@PathVariable String boardUrl
										     , @PathVariable String articleId
										     , @ModelAttribute ReplyVO replyVO
										     , HttpServletRequest request
						  					 , Model model) {
		boolean isConfirmed = replyService.confirmReplyPassword(replyVO);
		if (isConfirmed) {
			ReplyVO targetReply = replyService.getOneReplyByReplyId(replyVO.getReplyId());
			ArticleVO article = articleService.getOneArticleByArticleId(articleId, false);
			List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			NextArticleVO nextArticle = articleService.getBesideArticle(article);
			BoardVO boardVO = boardService.getBoardVOById(article.getBoardId());
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("articleVO", article);
			model.addAttribute("replyList", replyList);
			model.addAttribute("fileList", fileList);
			model.addAttribute("nextArticle", nextArticle);
			model.addAttribute("targetReply", targetReply);
			return "articleOne";
		} else {
			String nextUrl = (String)request.getHeader("REFERER");
			String msg = "잘못된 비밀번호입니다";
			model.addAttribute("msg", msg);
			model.addAttribute("nextUrl", nextUrl);
			logger.info("IncorrectPassword");
			return "error/sendNext";
		}
	}
	
	/*
	 * 게시글 작성 페이지 controller
	 * 새로운 게시글 작성 페이지로 이동
	 * 새로운 게시글이 기존 게시글의 답글일 경우 상위 게시글의 정보를 함께 반환
	 */
	@GetMapping("/{boardUrl}/write")
	public String createNewArticle(@PathVariable String boardUrl
								 , @RequestParam(name = "upperArticleId", required = false) String upperArticleId
							     , HttpServletRequest request
								 , Model model) {
		if (upperArticleId != "") {
			ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(upperArticleId, false);
			model.addAttribute("upperArticleVO", upperArticleVO);
		}
		BoardVO boardVO = boardService.getBoardVO(boardUrl);
		String nextUrl = (String)request.getHeader("REFERER");
		model.addAttribute("nextUrl", nextUrl);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("articleVO", new ArticleVO());
		return "articleWrite";
	}
	
	/*
	 * 게시글 작성 수행 controller
	 * 사용자 입력값에 대해 유효성 검사 수행 후,
	 * 문제가 있다면 게시글 작성 페이지 재반환
	 * 문제가 없을 경우 게시글 정보와 첨부 파일 목록을 DB에 저장
	 * 게시글 작성 성공 시 작성한 게시글의 단건 조회 페이지로 이동
	 * 게시글 작성 실패 시 RequestFailedException 반환
	 */
	@PostMapping("/{boardUrl}/write")
	public String createNewArticle(@PathVariable String boardUrl
			 					 , @Validated(ArticleCreateGroup.class)
			 					   @ModelAttribute ArticleVO articleVO
			 					 , BindingResult bindingResult
			 					 , Model model
								 , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles) {
		if (bindingResult.hasErrors()) {
			if (articleVO.getUpperArticleId() != "") {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(articleVO.getUpperArticleId(), false);
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
			return "redirect:view/"+articleId;
		}
		else {
			logger.info("RequestFailed");
			throw new RequestFailedException("게시글 등록에 실패하였습니다");
		}
	}
	
	/*
	 * 게시글 수정 페이지 controller
	 * 사용자가 전송한 게시글 비밀번호와 DB에 저장된 게시글 비밀번호 비교 후,
	 * 비밀번호가 일치하면 수정할 게시글의 원본 정보와 게시글 수정 페이지를 반환
	 * 비밀번호가 일치하지 않으면 IncorrectPasswordException 반환
	 */
	@PostMapping("/{boardUrl}/modify")
	public String modifyArticle(@PathVariable String boardUrl
			 				  , @ModelAttribute ArticleVO articleVO
			 				  , HttpServletRequest request
			  				  , Model model) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		String articleId = articleVO.getArticleId();
		String nextUrl = (String)request.getHeader("REFERER");
		model.addAttribute("nextUrl", nextUrl);
		if (isConfirmed) {
			ArticleVO article = articleService.getOneArticleByArticleId(articleId, false);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			BoardVO boardVO = boardService.getBoardVO(boardUrl);
			List<BoardVO> boardList = boardService.getAllBoard();
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("boardList", boardList);
			model.addAttribute("articleVO", article);
			model.addAttribute("fileList", fileList);
			if (article.getUpperArticleId() != null) {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(article.getUpperArticleId(), false);
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			return "articleMdfy";
		} else {
			String msg = "잘못된 비밀번호입니다";
			model.addAttribute("msg", msg);
			logger.info("IncorrectPassword");
			return "error/sendNext";
		}
	}
	
	/*
	 * 게시글 수정 수행 controller
	 * 사용자가 입력한 게시글의 정보에 대해 유효성 검사 수행 후,
	 * 이상이 있다면 사용자가 전송한 게시글 정보와 게시글 수정 페이지 재반환
	 * 이상이 없다면 게시글 수정 수행 후,
	 * 게시글 수정 성공 시 수정된 게시글의 단건 조회 페이지로 이동
	 * 게시글 수정 실패 시 RequestFailedException 반환
	 */
	@PostMapping("/{boardUrl}/modify.do")
	public String modifyArticle(@PathVariable String boardUrl
							  , @Validated(ArticleModifyGroup.class)
							    @ModelAttribute ArticleVO articleVO
							  , BindingResult bindingResult
							  , Model model
			 				  , @RequestParam(name = "attachFiles", required = false) List<MultipartFile> attachFiles
			 				  , @RequestParam(name = "deleteFiles", required = false) List<String> deleteFiles) {
		BoardVO boardInfo = boardService.getBoardVOById(articleVO.getBoardId());
		if (bindingResult.hasErrors()) {
			String articleId = articleVO.getArticleId();
			ArticleVO article = articleService.getOneArticleByArticleId(articleId, false);
			List<AttachmentVO> fileList = attachmentService.getAllFilesByArticleId(articleId);
			model.addAttribute("boardVO", boardInfo);
			model.addAttribute("articleVO", articleVO);
			model.addAttribute("fileList", fileList);
			if (article.getUpperArticleId() != null) {
				ArticleVO upperArticleVO = articleService.getOneArticleByArticleId(article.getUpperArticleId(), false);
				model.addAttribute("upperArticleVO", upperArticleVO);
			}
			return "articleMdfy";
		}
		boolean isSuccess = articleService.modifyArticle(articleVO, attachFiles, deleteFiles);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:/"+boardInfo.getBoardUrl()+"/view/"+articleId;
		}
		else {
			logger.info("RequestFailed");
			throw new RequestFailedException("게시글 수정에 실패하였습니다");			
		}
	}

	/*
	 * 게시글 삭제 수행 controller
	 * 사용자가 전송한 게시글의 비밀번호와 DB에 저장된 게시글 비밀번호가 일치할 경우 게시글 삭제 수행
	 * 두 비밀번호가 일치하지 않는다면 IncorrectPasswordException 반환
	 * 게시글 삭제 수행 후 게시판 목록으로 redirect
	 * 게시글 삭제 실패시 RequestFailedException 반환
	 */
	@PostMapping("/{boardUrl}/delete")
	public String deleteOneArticle(@PathVariable String boardUrl
			  					 , @ModelAttribute ArticleVO articleVO
			  					 , HttpServletRequest request
			  					 , Model model) {
		boolean isConfirmed = articleService.confirmPassword(articleVO);
		if (isConfirmed) {
			boolean isSuccess = articleService.deleteOneArticle(articleVO.getArticleId());
			if (isSuccess) {
				return "redirect:/" +boardUrl;
			} else {
				logger.info("RequestFailed");
				throw new RequestFailedException("게시글 삭제에 실패하였습니다");
			}
		} else{
			String nextUrl = (String)request.getHeader("REFERER");
			String msg = "잘못된 비밀번호입니다";
			model.addAttribute("msg", msg);
			model.addAttribute("nextUrl", nextUrl);
			logger.info("IncorrectPassword");
			return "error/sendNext";
		}
	}
	
}
