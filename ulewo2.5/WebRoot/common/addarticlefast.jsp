<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${realPath}/js/group.addarticle4fast.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/calender/WdatePicker.js?version=2.5"></script>
</head>
	<body>
		<form id="articleform4fast" style="width:840px;">
		<div class="form_tit">
			<label for="wowoType0"><input type="radio" id="wowoType0" value="0" name="wowoType" checked="checked"/>我创建的窝窝</label>
			<label for="wowoType1"><input type="radio" id="wowoType1" value="1" name="wowoType"/>我加入的窝窝</label>
			<select id="group" name="gid">
				<option value="">==请选择窝窝==</option>
			</select>
		</div>
		<div class="form_tit">
			<span class="form_tit_t">标题</span>
			（<span class="form_tit_x"><span>标题不能超过150字符</span> <b>必填</b></span>）
		</div>
		<div class="form_editinput">
			<div class="form_title_input">
				<input type="text" name="title" id="article_title4fast"/>
			</div>
			<div class="form_title_info">发表在</div>
			<div class="form_title_select">
				<select id="article_item4fast" name="categoryId">
					<option value="0">全部分类</option>
				</select>
			</div>
			<div class="clear"></div>
		</div>
		<div class="form_tit">
			<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
			（<span class="form_tit_x"><b>选填</b></span>）
		</div>
		<div class="form_editinput">
		 	<div class="form_title_input"><input class="editinput" type="text" name="keyWord" id="article_keyword4fast" /></div>
		 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
		 	<div class="clear"></div>
		</div>
		
		<div class="form_tit" id="topicType4fast">
		<label for="topicType4fast0"><input type="radio" id="topicType4fast0" value="0" name="topicType" checked="checked"/>普通帖</label>
		<label for="topicType4fast1"><input type="radio" id="topicType4fast1" value="1" name="topicType"/>投票帖</label>
		</div>
		<div id="survey_con4fast" class="survey_con">
			<div class="survey_tit">投票选项：</div>
			<div id="surveyLine4fast">
				<div class="survey_input"><input type="text" name="surveyTitle"></div>
				<div class="survey_input"><input type="text" name="surveyTitle"></div>
				<div class="survey_input"><input type="text" name="surveyTitle"></div>
				<div class="survey_input"><input type="text" name="surveyTitle"></div>
				<div class="survey_input"><input type="text" name="surveyTitle"></div>
			</div>
			<div><a href="javascript:fastPost.addSurveyLine()">添加选项</a></div>
			<div class="survey_type">
				投票方式:
				<label for="surveyType4fast0"><input type="radio" id="surveyType4fast0" value="S" name="surveyType" checked="checked"/>单选</label>
				<label for="surveyType4fast1"><input type="radio" id="surveyType4fast1" value="M" name="surveyType"/>多选</label>
			</div>
			<div class="survey_type">
				截止日期:<input class="Wdate" id="wdates" type="text" onClick="WdatePicker()" readonly="readonly" name="endDate">
			</div>
		</div>
		<div class="form_tit">
			<span class="form_tit_t">内容</span>
			（<span class="form_tit_x"><b>必填</b></span>）
		</div>
		<div class="form_editcontent">
			<textarea name="content" id="editor4fast"></textarea>
			<script type="text/javascript">
				window.UEDITOR_CONFIG.initialFrameHeight=300
				var editor4fast = UE.getEditor('editor4fast');
			</script>
		</div>
		<div class="form_tit">
			<span class="form_tit_t">附件</span>
			（<span class="form_tit_x">大小不能超过1M，只能是.rar、.zip文件</span>）
		</div>
		<input type="hidden" name="attached_file_name" id="attached_file_name4fast" />
		<input type="hidden" name="attached_file" id="attached_file4fast" />
		<div class="file_upload" id="file_upload">
			<div class="file_upload_frame"><iframe src="${realPath}/group/fileupload.jsp" width="350" height="30" frameborder="0" id="uploadFrame4fast"></iframe></div>
			<div class="file_upload_mark">下载附件所需积分:<input type="text" name="mark" id="mark" value="0"></div>
			<div class="clear"></div>
		</div>
		<div class="form_sub_btn">
			<a href="javascript:void(0)" id="sub_article_btn4fast" class="btn">发表帖子</a>
			<a href="javascript:fastPost.cancelAdd()" class="btn cancelbtn">取消</a>
			<span class="result_info4fast"><i id="warm_icon4fast"></i><span id="warm_info4fast"></span></span>
		</div>
		</form>		
	</body>
</html>