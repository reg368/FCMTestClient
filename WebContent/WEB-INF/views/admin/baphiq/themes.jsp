<%
    String cp = request.getContextPath();
    request.setAttribute("themes", cp+"/admin/themes");
    request.setAttribute("theme", cp+"/admin/themes/baphiq");
%>
