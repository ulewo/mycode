<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>
<%@ include file="ueditorcommon.jsp" %>
<script type="text/javascript" src="${realPath}/js/group.addarticle4fast.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/calender/WdatePicker.js?version=2.5"></script>
<link rel="stylesheet"  href="${realPath}/css/toolbar.css?vsersion=2.5" type="text/css"  />
<style>
.tips{width:150px;height:50px;position:fixed;top:400px;background:#40B3E9;padding:3px;font-size:13px;z-index:10000}
.tipscon{border:1px solid #494949;text-align:center;background:#ffffff;height:33px;padding-top:15px;}
.tipscon span{display:inline-block;}
.tipscon img{display: inline-block;margin-left:5px;}
.tips2{width:150px;height:50px;position:fixed;top:400px;background:#D4D4D4;padding:3px;font-size:13px;z-index:10000}
.result_info,.result_info4fast{float:left;margin-left:10px;color:red;display:none;}
.result_info i,.result_info4fast i{background:url("${realPath}/images/icon.png") 0px -46px no-repeat;width:20px;height:20px;display:inline-block;float:left;margin-top:10px;}
.result_info i.info,.result_info4fast i.info{background-position:-23px -46px}
.result_info span,.result_info4fast span{float:left;margin-left:2px;padding-top:13px;}
*{font-size:14px;}
.file_con a{text-decoration:underline;color:#3E62A6}
.loading_con{display:inline-block;background:#40B3E9;padding:3px;font-size:13px;z-index:1000;position:fixed;}
.loading_info{display:inline-block;border:1px solid #494949;text-align:center;background:#ffffff;height:15px;padding:10px;}
.loading_msg{display:inline-block;float:left;}
.loading_icon{display:inline-block;float:left;}
#remote_load{position:fixed;width:100%;height:900px;z-index:999;top:0px;left:0px;background-color: #ccc;opacity: 0.3;filter: alpha(opacity = 30);}

</style>
</head>
	<body>
		<form id="articleform4fast" style="width:820px;">
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
		<input type="hidden" name="attached_file_name" id="attached_file_name" />
		<input type="hidden" name="attached_file" id="attached_file" />
		<input type="hidden" name="gid"  value="${gid}"/>
		<div class="file_upload" id="file_upload">
			<div class="file_upload_frame"><iframe src="${realPath}/group/fileupload.jsp" width="350" height="30" frameborder="0" id="uploadFrame"></iframe></div>
			<div class="file_upload_mark">下载附件所需积分:<input type="text" name="mark" id="mark" value="0"></div>
			<div class="clear"></div>
		</div>
		<div class="form_sub_btn">
			<a href="javascript:void(0)" id="sub_article_btn4fast" class="btn">发表帖子</a>
			<a href="javascript:parent.closeToolbarCon()" class="btn cancelbtn">取消</a>
			<span class="result_info4fast"><i id="warm_icon4fast"></i><span id="warm_info4fast"></span></span>
		</div>
		</form>		
	</body>
</html>