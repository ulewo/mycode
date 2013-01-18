<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<style>
#selected6 a{background:#FFFFFF;}
</style>
<link rel="stylesheet"  href="../css/mamage.member.css" type="text/css"  />
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/manage.member.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
</head>
<body>
<div class="maincon">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
		<div class="reject_info">所有成员（${adminCount+countNumber}）</div>	
		<div class="op_area">
			<div class="op_area_se">
				<div class="op_area_se1">全选</div>
				<div class="op_area_se2"><input type="checkbox" name="all" id="checkAll"></div>
			</div>	
			<div class="bbtn1"><a href="javascript:void(0)" onclick="deleteMember()">删除成员</a></div>
			<div class="bbtn2"><a href="javascript:void(0)" onclick="set2Admin()">设为管理员</a></div>
			<div class="bbtn2"><a href="javascript:void(0)" onclick="cancelAdmin()">取消管理员</a></div>
		</div>	
		<div class="member_tit">管理员(${adminCount})</div>
		<div class="member_m" style="background:#F8F8F8">
			<form action="cancelAdmin.jspx" method="post" id="adminForm">
			<input type="hidden" name="gid" value="${gid}">
			<c:set var="num" value="0" ></c:set>
			<c:forEach var="member" items="${adminList}">
			<c:set var="num" value="${num+1}"></c:set>
				<div class="member_m_con">
					<div class="member_img"><a href="../userspace/userid=${member.userId}"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
					<div class="member_m_info">
						<div class="m_info_con">
							${member.userName}
						</div>
						<div class="member_info_op mbt">
							<c:if test="${num!=1}">
							<input type="checkbox" name="admin" class="checkId" value="${member.id}">
							</c:if>
						</div>
					</div>
				</div>
			</c:forEach>
			</form>
			<div class="clear"></div>
		</div>
		<div class="member_tit" style="line-height:10px;margin-top:10px;">普通成员(${countNumber})</div>
		<div class="member_m">
			<form action="set2Admin.jspx" method="post" id="memberForm">
			<input type="hidden" name="gid" value="${gid}">
			<c:forEach var="member" items="${memberList}">
				<div class="member_m_con">
					<div class="member_img"><a href="../userspace/userid=${member.userId}"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
					<div class="member_m_info">
						<div class="m_info_con">
							${member.userName}
						</div>
						<div class="member_info_op mbt">
							<input type="checkbox" name="member" class="checkId" value="${member.id}">
						</div>
					</div>
				</div>
			</c:forEach>
			</form>
			<div class="clear"></div>
		</div>
		<div class="pagebttom">
		   <div  class="pagination">
				<p:pager url="manageMember.jspx?gid=${gid}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div class="foot"></div>
</body>
</html>