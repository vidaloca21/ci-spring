package com.cafe.bbs.app.reply.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/{articleId}")
	public Map<String, Object> getRepliesByArticleId(@PathVariable String articleId) {
		List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", replyList);
		return resultMap;
	}
	
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
