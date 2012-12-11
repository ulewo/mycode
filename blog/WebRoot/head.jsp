<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<div style="width:100%">
<c:if test="${name!=null}">
	<div style="background:#ffffff"><a href="admin_index.jsp">管理员</a></div>
</c:if>
	
	<div class="head">
		<div class="menue">
			<div><a href="article" class="article"></a></div>
			<div><a href="about" class="about"></a></div>
			<div><a href="note.jsp" class="note"></a></div>
			<div><a href="http://www.ulewo.com" class="ulewo"></a></div>
		</div>
	</div>
</div>

