    <%@ page language="java" contentType="text/html; charset=utf-8"
        pageEncoding="utf-8"%>
   <%@ page import="com.ulewo.util.Uploader" %>
	<%
	String port = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
	String realPath = "http://" + request.getServerName() + port + request.getContextPath(); 
	%>
    <%
    request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
    Uploader up = new Uploader(request);
    up.setSavePath("upload");
    String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
    up.setAllowFiles(fileType);
    up.setMaxSize(10000); //单位KB
    up.upload();
    response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+realPath+"/"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
    %>
