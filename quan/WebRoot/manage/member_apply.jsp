<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="../css/mamage.member.css" type="text/css"  />
<title>Justlearning 学习，生活，娱乐</title>
<style>
#sel_left2 a{background:#34B3E6;font-weight:bold;color:#FFFFFF;background-image:url("../images/sjj2.gif");background-position:right center;background-repeat:no-repeat;}
#sel_top2{background:url("../images/mtopsel.gif");}		
</style>
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
		<jsp:include page="member_top.jsp"></jsp:include>
		<div class="reject_info">未处理的请求（8）</div>	
		<div class="op_area">
			<div class="op_area_se">
				<div class="op_area_se1">全选</div>
				<div class="op_area_se2"><input type="checkbox" id="checkAll"></div>
			</div>	
			<div class="bbtn1"><a href="javascript:void()" onclick="acceptAll()">批量接受</a></div>
			<div class="bbtn1"><a href="javascript:void()" onclick="refuseAll()">批量拒绝</a></div>
		</div>	
		<div class="member">
		<form action="" method="post" id="memberForm">
		<input type="hidden" name="gid" value="${gid}">
			<c:forEach var="member" items="${memberList}">
				<div class="member_con">
					<div class="member_img"><a href="../userspace/userid=${member.userId}"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
					<div class="member_info">
						<div class="member_info_con">
							<div class="info_con_name">${member.userName}</div>	
							<div class="info_con_check"><input type="checkbox" name="ids" value="${member.id}" class="checkId"></div>
						</div>
						<div class="member_info_op mbt">
							<a href="javascript:vlid()" onclick="acceptMember(this)">接受</a><a href="javascript:void()" onclick="refuseMember(this)">拒绝</a>
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