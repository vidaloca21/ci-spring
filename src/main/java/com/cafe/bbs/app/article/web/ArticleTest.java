package com.cafe.bbs.app.article.web;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.bbs.app.article.service.ArticleService;
import com.cafe.bbs.app.article.vo.ArticleVO;
import com.cafe.bbs.app.board.service.BoardService;
import com.cafe.bbs.app.board.vo.BoardVO;

@RestController
public class ArticleTest {

//	@Autowired
//	private ArticleService articleService;
//	@Autowired
//	private BoardService boardService;
//
//	@GetMapping("/test/make-article/{boardUrl}")
//	public Map<String, Object> testCreateTestArticle(@PathVariable String boardUrl) {
//		BoardVO board = boardService.getBoardVO(boardUrl);
//		ArticleVO article = new ArticleVO();
//		boolean isSuccess = false;
//		if (board != null) {
//			LocalTime now = LocalTime.now();
//			article.setArticleTitle("0번째 테스트 insert입니다");
//			article.setArticleContent(now.toString());
//			article.setArticlePassword("1234");
//			article.setBoardId(board.getBoardId());
//			article.setMemberName("개발자0");
//			isSuccess = articleService.createNewArticle(article);
//		}
//		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("result", isSuccess);
//		return resultMap;
//	}
//	
//	@GetMapping("/api/make-article/{boardUrl}")
//	public int createTestArticle(@PathVariable String boardUrl) throws InterruptedException {
//		int insertCount = 0;
//		for (int i = 201; i < 400; i++) {
//			LocalTime now = LocalTime.now();
//			ArticleVO article = new ArticleVO();
//			article.setArticleTitle((i+1) + "번째 테스트 insert입니다");
//			article.setArticleContent(now.toString());
//			article.setArticlePassword("1234");
//			article.setBoardId(boardService.getBoardVO(boardUrl).getBoardId());
//			article.setMemberName("개발자"+(i+1));
//			articleService.createNewArticle(article);
//			insertCount++;
//		}
//		return insertCount;
//	}
	
}
