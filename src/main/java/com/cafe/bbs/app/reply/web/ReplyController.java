package com.cafe.bbs.app.reply.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.bbs.app.reply.service.ReplyService;
import com.cafe.bbs.app.reply.vo.ReplyVO;
import com.cafe.bbs.app.reply.vo.validategroup.ReplyCreateGroup;
import com.cafe.bbs.app.reply.vo.validategroup.ReplyModifyGroup;
import com.cafe.bbs.exceptions.RequestFailedException;


@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	private final static Logger logger = LoggerFactory.getLogger(ReplyController.class);
	@Autowired
	private ReplyService replyService;
	
	/*
	 * 댓글 작성 수행 contoller
	 * 댓글 유효성 검사 수행 후,
	 * 문제가 있다면 댓글 작성을 수행하지 않고 기존 페이지로 redirect
	 * 문제가 없다면 댓글 작성 수행
	 * 댓글 작성 성공시 기존 페이지로 redirect
	 * 댓글 작성 실패시 RequestFailedException 반환
	 */
	@PostMapping("/write")
	public String createNewReply(@Validated(ReplyCreateGroup.class)
								 @ModelAttribute ReplyVO replyVO
							   , BindingResult bindingResult
			   				   , @RequestParam("currentUrl") String currentUrl) {
		if (bindingResult.hasErrors()) {
			return "redirect:"+currentUrl;
		}
		boolean isSuccess = replyService.createNewReply(replyVO);
		if (isSuccess) {
			return "redirect:"+currentUrl;
		} else {
			logger.warn("Failed to write new reply");
			throw new RequestFailedException("댓글 작성에 실패하였습니다");
		}
	}
	
	/*
	 * 댓글 수정 수행 controller
	 * 댓글 유효성 검사 수행 후,
	 * 문제가 있다면 댓글 수정을 수행하지 않고 기존 페이지로 redirect
	 * 문제가 없다면 댓글 수정 수행
	 * 댓글 수정 성공시 기존 페이지로 redirect
	 * 댓글 수정 실패시 RequestFailedException 반환
	 */
	@PostMapping("/modify.do")
	public String modifyOneReply(@Validated(ReplyModifyGroup.class)
								 @ModelAttribute ReplyVO replyVO
							   , BindingResult bindingResult
							   , @RequestParam String currentUrl) {
		if (bindingResult.hasErrors()) {
			return "redirect:"+currentUrl;
		}
		boolean isSuccess = replyService.modifyOneReply(replyVO);
		if (isSuccess) {
			return "redirect:" +currentUrl;
		} else {
			logger.warn("Failed to modify a reply");
			throw new RequestFailedException("댓글 수정에 실패하였습니다");
		}
	}
	
	/*
	 * 댓글 삭제 수행 controller
	 * 사용자가 입력한 비밀번호와 DB에 저장된 댓글 비밀번호 비교 후,
	 * 일치하지 않으면 댓글 삭제를 수행하지 않고 기존 페이지로 redirect
	 * 일치하면 댓글 삭제 수행
	 * 댓글 삭제 성공시 기존 페이지로 redirect
	 * 댓글 삭제 실패시 RequestFailedException 반환
	 */
	@PostMapping("/delete")
	public String deleteOneReply(@ModelAttribute ReplyVO replyVO
			   				   , @RequestParam String currentUrl) {
		boolean isConfirmed = replyService.confirmReplyPassword(replyVO);
		if (isConfirmed) {
			boolean isSuccess = replyService.deleteOneReply(replyVO);
			if (isSuccess) {
				return "redirect:"+currentUrl;
			} else {
				logger.warn("Failed to delete a reply");
				throw new RequestFailedException("댓글 삭제에 실패하였습니다");
			}
		} else {
			return "redirect:"+currentUrl;
		}
	}
	
}
