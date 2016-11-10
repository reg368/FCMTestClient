package hyweb.util;

import hyweb.core.kit.NumberKit;

import java.util.Map;

import org.jdom2.Element;

public class PageHelper {
	public static int getPage(Map<String, String[]> req) {
		String[] values = req.get("page");
		if (values == null || values.length == 0) {
			return 1;
		}
		
		String value = values[0];
		return getValue(value, 1);
	}
	
	public static int getValue(String value, int defVal) {
		if (value == null) {
			return defVal;
		}
		
		if (NumberKit.isDigits(value)) {
			return Integer.parseInt(value);
		} else {
			return defVal;
		}
	}
	
	public static PageJump getPageJump(int page, int showNum, int pageSize, int count) {
		int nowPage = 0;
		int leftSize = 0;
		int firstPage = 0;
		int lastPage = 0;
		int prevPage = 0;
		int startPage = 0;
		int endPage = 0;
		int nextPage = 0;
		
		if (count > 0) {
			nowPage = page;
			leftSize = pageSize/2;
			firstPage = 1;
			lastPage = (count+showNum-1)/showNum;
			prevPage = nowPage-1 <= 0 ? nowPage : nowPage-1;
			startPage = nowPage <= leftSize ? firstPage : nowPage-leftSize;
			endPage = startPage+(pageSize-1) > lastPage ? lastPage : startPage+(pageSize-1);
			
			if (endPage == lastPage) {
				startPage = endPage-(pageSize-1) <= firstPage ? firstPage : endPage-(pageSize-1);
			}
			
			nextPage = nowPage+1 >= lastPage ? lastPage : nowPage + 1;
		}
		
		PageJump pageJump = new PageJump();
		pageJump.setNowPage(nowPage);
		pageJump.setLeftSize(leftSize);
		pageJump.setFirstPage(firstPage);
		pageJump.setLastPage(lastPage);
		pageJump.setPrevPage(prevPage);
		pageJump.setStartPage(startPage);
		pageJump.setEndPage(endPage);
		pageJump.setNextPage(nextPage);
		
		return pageJump;
	}
	
	/**
	 * 
	 * @param page
	 * @param showNum
	 * @param pageSize
	 * @param count
	 * @return «上一頁1..23456..7下一頁»
	 */
	public static PageJump getPageJump2(int page, int showNum, int pageSize, int count) {
		int nowPage = 1;
		int leftSize = 0;
		int firstPage = 0;
		int lastPage = 0;
		int prevPage = 0;
		int startPage = 1;
		int endPage = 1;
		int nextPage = 0;
		
		if (count > 0) {
			firstPage = 1;
			lastPage = (count+showNum-1)/showNum;
			nowPage = page;
			
			if (page <= 1) {
				firstPage = 0;
				prevPage = 0;
				startPage = 1;
			} else {
				prevPage = page-1;
				startPage = page;
			}
			
			if (page >= lastPage) {
				endPage = lastPage;
				lastPage = 0;
				nextPage = 0;
			} else {
				endPage = nowPage + pageSize;
				
				if (endPage > lastPage) {
					endPage = lastPage;
				}
				nextPage = page + 1;
			}
		}
		
		PageJump pageJump = new PageJump();
		pageJump.setNowPage(nowPage);
		pageJump.setLeftSize(leftSize);
		pageJump.setFirstPage(firstPage);
		pageJump.setLastPage(lastPage);
		pageJump.setPrevPage(prevPage);
		pageJump.setStartPage(startPage);
		pageJump.setEndPage(endPage);
		pageJump.setNextPage(nextPage);
		
		return pageJump;
	}
	
	public static void setPageJumpElement(Element pageJumpElm, String url, int page, int showNum, int pageSize, int count) {
		if (pageJumpElm == null) {
			pageJumpElm = new Element("pageJump");
		}
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		String separat = "?";
		if (url.indexOf('?') != -1) {
			separat = "&";
		}
		
		Element nowPageElm = new Element("nowPage");
		nowPageElm.setText(Integer.toString(pageJump.getNowPage()));
		pageJumpElm.addContent(nowPageElm);
		
		Element firstElm = new Element("page");
		firstElm.setAttribute("page", "first");
		firstElm.setAttribute("url", url+separat+"page="+pageJump.getFirstPage());
		pageJumpElm.addContent(firstElm);
		
		Element prevElm = new Element("page");
		prevElm.setAttribute("page", "prev");
		prevElm.setAttribute("url", url+separat+"page="+pageJump.getPrevPage());
		pageJumpElm.addContent(prevElm);
		
		for (int i = pageJump.getStartPage(); i <= pageJump.getEndPage(); i++) {
			Element pageElm = new Element("page");
			pageElm.setAttribute("page", Integer.toString(i));
			pageElm.setAttribute("url", url+separat+"page="+i);
			pageJumpElm.addContent(pageElm);
		}
		
		if (pageJump.getEndPage() < pageJump.getLastPage()) {
			Element alsoElm = new Element("page");
			alsoElm.setAttribute("page", "...");
			pageJumpElm.addContent(alsoElm);
		}
		
		Element nextElm = new Element("page");
		nextElm.setAttribute("page", "next");
		nextElm.setAttribute("url", url+separat+"page="+pageJump.getNextPage());
		pageJumpElm.addContent(nextElm);
		
		Element lastElm = new Element("page");
		lastElm.setAttribute("page", "last");
		lastElm.setAttribute("url", url+separat+"page="+pageJump.getLastPage());
		pageJumpElm.addContent(lastElm);
	}
}
