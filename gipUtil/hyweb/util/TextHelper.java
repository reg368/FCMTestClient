package hyweb.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Element;

public class TextHelper {
	private static String CMS_HTML = "<cms/>";
	public static String getTextType(String text) {
		if (text.startsWith(CMS_HTML)) {
			return "html";
		} else {
			return "text";
		}
	}
	public static ArticleText noLoginText(String introCheck, String intro, List<String> pages, String textType) {
		ArticleText artText = new ArticleText();
		
		boolean replaceTags = true;
		if ("html".equals(textType)) {
			replaceTags = false;
		}
		
		if ("1".equals(introCheck)) {
			artText.resultText = TextHelper.replaceN(intro, replaceTags);
			
			for (int i = 0; i < pages.size(); i++) {
				artText.remain += pages.get(i).length();
			}
		} else {
			if (pages.size() > 0) {
				artText.resultText = TextHelper.replaceN(pages.get(0), replaceTags);
			}
			
			for (int i = 1; i < pages.size(); i++) {
				artText.remain += pages.get(i).length();
			}
		}
		
		return artText;
	}
	
	public static ArticleText splitContent(List<String> pages, int page, Element pageElm, String textType) {
		ArticleText artText = new ArticleText();
		if (page > pages.size()) {
			artText.resultText = "";
			return artText;
		}
		
		Element nowPageElm = new Element("nowPage");
		nowPageElm.setText(Integer.toString(page));
		pageElm.addContent(nowPageElm);
		
		Element countElm = new Element("count");
		countElm.setText(Integer.toString(pages.size()));
		pageElm.addContent(countElm);
		
		Element firstElm = new Element("first");
		firstElm.setText("1");
		pageElm.addContent(firstElm);
		
		Element lastElm = new Element("last");
		lastElm.setText(Integer.toString(pages.size()));
		pageElm.addContent(lastElm);
		
		int prev = page - 1;
		if (prev <= 0) {
			prev = 1;
		}
		Element prevElm = new Element("prev");
		prevElm.setText(Integer.toString(prev));
		pageElm.addContent(prevElm);
		
		int next = page + 1;
		if (next > pages.size()) {
			next = pages.size();
		}
		Element nextElm = new Element("next");
		nextElm.setText(Integer.toString(next));
		pageElm.addContent(nextElm);
		
		boolean replaceTags = true;
		if ("html".equals(textType)) {
			replaceTags = false;
		}
		
		artText.resultText = TextHelper.replaceN(pages.get(page-1), replaceTags);
		
		return artText;
	}
	
	public static String replaceSid(String content, Long sid) {
		return StringUtils.replaceEach(
				content, 
				new String[] {"-sid-"},
				new String[] {Long.toString(sid)}
		);
	}
	
	public static String replaceN(String content, boolean replaceTags) {
		if (content == null) {
			return null;
		}
		
		if (replaceTags) {
			return StringUtils.replaceEach(
					content, 
					new String[] {CMS_HTML, "<", ">", "\r\n", "\n", "\r"}, 
					new String[] {"", "&lt;", "&gt;", "<br />", "<br />", "<br />"}
				);
		} else {
			return StringUtils.replaceEach(
					content, 
					new String[] {CMS_HTML, "\r\n", "\n", "\r"}, 
					new String[] {"", "<br />", "<br />", "<br />"}
				);
		}
	}
	
	public static class ArticleText {
		public String resultText;
		public int remain;
	}
}
