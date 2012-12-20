<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<div class="item">

	<a href="topics.jspx?gid=${gid }&itemId=0" <c:if test="${itemId==0}">style="border-bottom:3px solid #6FCEF4;color: #6FCEF4;"</c:if>>全部分类</a>
	<c:forEach var="item" items="${itemList}">
		<a href="topics.jspx?gid=${gid }&itemId=${item.id}" <c:if test="${itemId==item.id}">style="border-bottom:3px solid #6FCEF4;color: #6FCEF4;"</c:if>>${item.itemName}</a>
	</c:forEach>
</div>