<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="org.json.*"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
response.setContentType("text/csv;charset=UTF-8");  
response.setHeader("Content-Disposition", "attachment; filename=export.csv");  
out.print("\uFEFF");
out.println("測試作物代碼,測試項目(中文),測試項目(英文),方法偵測極限,\"食品衛生法規\n(1:<或2:<=)\",食品衛生法規容許值,\"不得檢驗出\n(1:不得檢出)(空白:可檢出)\"");
List<Map<String,Object>> rows = (List<Map<String,Object>>) request.getAttribute("$rows");
for(Map<String,Object> row : rows){
	String line = process_line(row);
	out.println(line);
}
%>
<%!
	public String process_line(Map row ) throws Exception {
		StringBuilder sb = new StringBuilder();
		Object cropid  = row.get("tiCropId");
		Object chtName  = row.get("tiTestCHTName");
		Object engName  = row.get("tiTestENGName");
		Object limit = row.get("tiMethodLimit");
		Object lawCond  = row.get("tiLawCond");
		Object lawItem  = row.get("tiLawItem");
		Object notAllow  = row.get("tiNotAllow");
		notAllow = (notAllow==null || "null".equals(notAllow)) ? "" : notAllow;
		sb.append(cropid).append(',');
		sb.append(chtName).append(',');
		sb.append(engName).append(',');
		sb.append(limit).append(',');
		sb.append(lawCond).append(',');
		sb.append(lawItem).append(',');
		sb.append(notAllow).append(',');
		return sb.toString();
	}
%>