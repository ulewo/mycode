    <%@ page language="java" pageEncoding="utf-8"%>
	<%@ page import="com.ulewo.util.RemoteUploader" %>
    <%
    request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
    RemoteUploader up = new RemoteUploader(request);
    up.upload();
   // response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+realPath+"/"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
    response.getWriter().print("{'url':'" + up.getOriginalName() + "','tip':'"+up.getState()+"','srcUrl':'" + up.getUrl() + "'}" );
    %>
