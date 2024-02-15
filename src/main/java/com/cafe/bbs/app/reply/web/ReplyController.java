package com.cafe.bbs.app.reply.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.bbs.app.reply.service.ReplyService;
import com.cafe.bbs.app.reply.vo.ReplyVO;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/{articleId}")
	public Map<String, Object> getRepliesByArticleId(@PathVariable("articleId") String articleId) {
		List<ReplyVO> replyList = replyService.getRepliesByArticleId(articleId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", replyList);
		return resultMap;
	}
	
	@PostMapping("/write")
	public String createNewReply(@ModelAttribute ReplyVO replyVO) {
		String articleId = replyVO.getArticleId();
		boolean isSuccess = replyService.createNewReply(replyVO);
		if (isSuccess) {
			return "redirect:/view/" +articleId;
		} else {
			throw new IllegalArgumentException("작성 실패!");
		}
	}
	@PostMapping("/modify")
	public String modifyOneReply(@ModelAttribute ReplyVO replyVO) {
		String articleId = replyVO.getArticleId();
		boolean isSuccess = replyService.modifyOneReply(replyVO);
		if (isSuccess) {
			return "redirect:/view/" +articleId;
		} else {
			throw new IllegalArgumentException("수정 실패!");
		}
	}
	
	@PostMapping("/delete")
	public String deleteOneReply(@ModelAttribute ReplyVO replyVO) {
		boolean isSuccess = replyService.deleteOneReply(replyVO);
		String articleId = replyVO.getArticleId();
		if (isSuccess) {
			return "redirect:/view/" +articleId;
		} else {
			throw new IllegalArgumentException("삭제 실패!");
		}
	}
	
}
