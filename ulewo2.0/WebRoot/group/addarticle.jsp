<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="form_tit">
	<span class="form_tit_t">标题</span>
	（<span class="form_tit_x">必填</span>）
</div>
<div class="form_editinput">
	<div class="form_title_input">
		<input type="text"/>
	</div>
	<div class="form_title_info">存放在</div>
	<div class="form_title_select">
		<select>
			<option>阿萨德发射点发</option>
			<option>阿萨德发射点发</option>
			<option>阿萨德发射点发</option>
		</select>
	</div>
	<div class="clear"></div>
</div>
<div class="form_tit">
	<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
	（<span class="form_tit_x">选填</span>）
</div>
<div class="form_editinput">
 	<div class="form_title_input"><input class="editinput" type="password" name="address" value="${userVo.address}"/></div>
 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
 	<div class="clear"></div>
</div>
<div class="form_tit">
	<span class="form_tit_t">内容</span>
	（<span class="form_tit_x">必填</span>）
</div>
<div class="form_editcontent" id="editor">

</div>
<div class="form_sub_btn">
	<a href="" class="btn">发表帖子</a>
	<a href="javascript:cancelAdd()" class="btn cancelbtn">取消</a>
</div>
				