package com.cafe.bbs.app.article.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.bbs.app.article.service.ArticleService;
import com.cafe.bbs.app.article.vo.ArticleVO;


@Controller
@RequestMapping("/board")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("")
	public String getAricleList(Model model) {
		List<ArticleVO> articleList = articleService.getAllArticle();
		model.addAttribute("articleList", articleList);
		return "articleList";
	}
	
	@GetMapping("/{articleId}")
	public String getOneArticle(@PathVariable("articleId") String articleId, Model model) {
		ArticleVO article = articleService.getOneArticleByArticleId(articleId);
		model.addAttribute(article);
		return "articleOne";
	}

	@GetMapping("/write")
	public String createNewArticle() {
		return "articleWrite";
	}
	
	@PostMapping("/write")
	public String createNewArticle(@ModelAttribute ArticleVO articleVO) {
		boolean isSuccess = articleService.createNewArticle(articleVO);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:/board/" +articleId;
		}
		return "articleWrite";
	}
	
	@GetMapping("/modify/{articleId}")
	public String modifyArticle(@PathVariable("articleId") String articleId, Model model) {
		ArticleVO article = articleService.getOneArticleByArticleId(articleId);
		model.addAttribute(article);
		return "articleMdfy";
	}
	
	@PostMapping("/modify/{articleId}")
	public String modifyArticle(@ModelAttribute ArticleVO articleVO) {
		boolean isSuccess = articleService.modifyArticle(articleVO);
		if (isSuccess) {
			String articleId = articleVO.getArticleId();
			return "redirect:/board/" +articleId;
		}
		return "articleMdfy";
	}
	

	@GetMapping("/delete/{articleId}")
	public String deleteOneArticle(@PathVariable("articleId") String articleId) {
		boolean isSuccess = articleService.deleteOneArticleByArticleId(articleId);
		if (isSuccess) {
			return "redirect:/board";
		}
		return "articleList";
	}
}
