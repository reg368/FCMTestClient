<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jdom2.Document"%>
<%@ page import="org.jdom2.Element"%>
<%@ page import="java.util.List"%>
<%@ page import="hyweb.util.cacheable.DomHelper"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setContentType("text/csv;charset=UTF-8");  
	response.setHeader("Content-Disposition", "attachment; filename=export.csv");  
 	out.print("\uFEFF");
	Document doc = (Document) request.getAttribute("doc");
	//DomHelper.toHTMLWriter(doc, out);
	
	List<Element> head = DomHelper.getListByXpath(
			"htPage/pageSpec/mainContent/topicList/columnHead/column",
			doc);
	out.println(process_line(head));
	
	List<Element> body = DomHelper.getListByXpath(
			"htPage/pageSpec/mainContent/topicList/columnBody/article",
			doc);
	
	for (Element e : body) {
		List<Element> child = DomHelper.getListByXpath("column ",e);
		out.println(process_line(child));
	}
	
%>
<%!
	public String process_line(List<Element> elems) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Element e : elems) {
			String item = e.getAttributeValue("value", "");
			String dataType = e.getAttributeValue("dataType", "");
			if(item.length()>0  && item.charAt(0)=='0' || (dataType.indexOf("char") != -1)){
				item = "=\""+item+"\"";
			}if(item.indexOf(',')>=0){
				item = "\"" + item + '"';
			}
			sb.append(item).append(',');
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
%>
