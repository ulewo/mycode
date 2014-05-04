﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<form id="articleform">
<div class="form_tit">
	<span class="form_tit_t">标题</span>
	（<span class="form_tit_x"><span>标题不能超过150字符</span> <b>必填</b></span>）
</div>
<div class="form_editinput">
	<div class="form_title_input">
		<input type="text" name="title" id="article_title"/>
	</div>
	<div class="form_title_info">发表在</div>
	<div class="form_title_select">
		<c:if test="${itemId==null}">
			<select id="article_item" name="categoryId">
				<option value="0" <c:if test="${cateId==null}">selected="selected"</c:if>>全部分类</option>
				<c:forEach var="item" items="${categoryList}">
					<c:if test="${item.allowPost=='Y'||group.groupUserId==user.userId}">
						<option  <c:if test="${cateId==item.categoryId}">selected="selected"</c:if> value="${item.categoryId}">${item.name}</option>
					</c:if>
				</c:forEach>
			</select>
		</c:if>
		<c:if test="${itemId!=null}">
			<input type="hidden" id="article_item" name="itemId" value="${itemId}">
			<select disabled="disabled">
			<c:forEach var="item" items="${itemList}">
				<option <c:if test="${itemId==item.id}">selected="selected"</c:if> value="${item.id}">${item.itemName}</option>
			</c:forEach>
			</select>
		</c:if>
		
	</div>
	<div class="clear"></div>
</div>
<div class="form_tit">
	<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
	（<span class="form_tit_x"><b>选填</b></span>）
</div>
<div class="form_editinput">
 	<div class="form_title_input"><input class="editinput" type="text" name="keyWord" id="article_keyword" /></div>
 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
 	<div class="clear"></div>
</div>

<div class="form_tit">
<label for="topicType0"><input type="radio" id="topicType0" value="0" name="topicType" checked="checked"/>普通帖</label>
<label for="topicType1"><input type="radio" id="topicType1" value="1" name="topicType"/>投票帖</label>
</div>
<div id="survey_con" class="survey_con">
	<div class="survey_tit">投票选项：</div>
	<div id="surveyLine">
		<div class="survey_input"><input type="text" name="surveyTitle"></div>
		<div class="survey_input"><input type="text" name="surveyTitle"></div>
		<div class="survey_input"><input type="text" name="surveyTitle"></div>
		<div class="survey_input"><input type="text" name="surveyTitle"></div>
		<div class="survey_input"><input type="text" name="surveyTitle"></div>
	</div>
	<div><a href="javascript:addSurveyLine()">添加选项</a></div>
	<div class="survey_type">
		投票方式:
		<label for="surveyType0"><input type="radio" id="surveyType0" value="S" name="surveyType" checked="checked"/>单选</label>
		<label for="surveyType1"><input type="radio" id="surveyType1" value="M" name="surveyType"/>多选</label>
	</div>
	<div class="survey_type">
		截止日期:<input class="Wdate" id="wdate" type="text" onClick="WdatePicker()" readonly="readonly" name="endDate">
	</div>
</div>
<div class="form_tit">
	<span class="form_tit_t">内容</span>
	（<span class="form_tit_x"><b>必填</b></span>）
</div>
<div class="form_editcontent">
	<textarea name="content" id="editor"></textarea>
	<script type="text/javascript">
			var editor;
			editor = new UE.ui.Editor();
			editor.render("editor");
	</script>
</div>
<div class="form_tit">
	<span class="form_tit_t">附件</span>
	（<span class="form_tit_x">大小不能超过1M，只能是.rar、.zip文件</span>）
</div>
<input type="hidden" name="attached_file_name" id="attached_file_name" />
<input type="hidden" name="attached_file" id="attached_file" />
<input type="hidden" name="gid"  value="${gid}"/>
<div class="file_upload" id="file_upload">
	<div class="file_upload_frame"><iframe src="${realPath}/group/fileupload.jsp" width="350" height="30" frameborder="0" id="uploadFrame"></iframe></div>
	<div class="file_upload_mark">下载附件所需积分:<input type="text" name="mark" id="mark" value="0"></div>
	<div class="clear"></div>
</div>
<div class="form_sub_btn">
	<a href="javascript:void(0)" id="sub_article_btn" class="btn">发表帖子</a>
	<a href="javascript:cancelAdd()" class="btn cancelbtn">取消</a>
	<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
</div>
</form>		