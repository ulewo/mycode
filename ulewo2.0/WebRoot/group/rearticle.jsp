﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<form id="articleform">
<div class="form_tit">
	<span class="form_tit_t">内容</span>
	（<span class="form_tit_x"><b>必填</b></span>）
</div>
<div class="form_editcontent" id="editor">

</div>
<input type="hidden" name="gid"  value="${gid}"/>
<input type="hidden" name="articleId"  value="${article.id}"/>
<input type="hidden" id="content" name="content">
<div class="form_sub_btn">
	<a href="javascript:void(0)" id="sub_article_btn" class="btn">发表回复</a>
	<a href="javascript:cancelAdd()" class="btn cancelbtn">取消</a>
	<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
</div>
</form>		