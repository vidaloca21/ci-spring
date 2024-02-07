package com.cafe.bbs.app.article.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafe.bbs.app.article.service.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("")
	public String getStart() {
		return "articleList";
	}
	
//	@GetMapping("/")
//	public String getAllArticle() {
//		System.out.println(articleService.getAllArticleCount());
//		return "articleList";
//	}
	
	@GetMapping("/1")
	public String getOneArticle() {
		return "articleOne";
	}
}
