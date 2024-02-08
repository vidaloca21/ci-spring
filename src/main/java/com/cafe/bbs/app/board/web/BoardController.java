package com.cafe.bbs.app.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafe.bbs.app.article.service.ArticleService;
import com.cafe.bbs.app.article.vo.ArticleVO;

@Controller
public class BoardController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping(value = {"/", "/notice", "/shop"})
	public String getStart(Model model) {
		List<ArticleVO> articleList = articleService.getAllArticle();
		model.addAttribute("articleList", articleList);
		return "articleList";
	}
	
}
