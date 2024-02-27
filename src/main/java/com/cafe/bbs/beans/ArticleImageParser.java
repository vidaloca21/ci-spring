package com.cafe.bbs.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cafe.bbs.app.attachment.vo.AttachmentVO;

public class ArticleImageParser {

	public List<String> getAttachedImages(String articleContent) {
		List<String> result = new ArrayList<>();
		Document doc = Jsoup.parse(articleContent);
		Elements imgs = doc.getElementsByTag("img");
		for (Element img: imgs) {
			result.add(img.attr("src").replace("/api/temp/", ""));
		}
		return result;
	}
	
	public String replaceImagePath(List<AttachmentVO> contentImages, String articleContent) {
		Map<String, String> imgNameMap = new HashMap<>();
		for (AttachmentVO attImg: contentImages) {
			imgNameMap.put(attImg.getOriginFilename(), attImg.getUuidFilename());
		}
		Document doc = Jsoup.parse(articleContent);
		Elements imgs = doc.select("img");
		imgs.forEach(img -> {
			String imgName = img.attr("src").replace("/api/temp/", "");
			img.attr("src", "/api/file/"+imgNameMap.get(imgName));
		});
		return doc.toString();
	}
}
