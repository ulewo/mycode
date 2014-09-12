<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../common/path.jsp" %>
<link rel="stylesheet" type="text/css" href="${realPath}/jquery-easyui-1.3.5/themes/bootstrap/easyui.css?version=2.5">
<link rel="stylesheet" type="text/css" href="${realPath}/jquery-easyui-1.3.5/themes/icon.css?version=2.5">
<script type="text/javascript" src="${realPath}/jquery-easyui-1.3.5/jquery.easyui.min.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/manage.public.css?version=2.5">
<style>
.searchDiv{padding-left:10px;padding-top:8px;}
.toolbar-line{height: 24px;margin: 1px 1px;display:inline-block; border-right: 1px solid white; border-left: 1px solid rgb(204, 204, 204);}
label{cursor:pointer;}
.easyform{padding-left:10px;padding-top:10px;}
.easyform table tr{height:30px;font-size:13px;text-align:left;}
.easyform table tr td.rightclumn{text-align:right;padding-right:8px;}
.easyform table tr td input.textinput{height:25px;width:100%}
.easyform table tr td textarea{border:1px solid #A9A9A9}
.loading_con{display:inline-block;background:#40B3E9;padding:3px;font-size:13px;z-index:1000;position:fixed;}
.loading_info{display:inline-block;border:1px solid #494949;text-align:center;background:#ffffff;height:15px;padding:10px;}
.loading_msg{display:inline-block;float:left;}
.loading_icon{display:inline-block;float:left;}
#remote_load{position:fixed;width:100%;height:900px;z-index:999;top:0px;left:0px;background-color: #ccc;opacity: 0.3;filter: alpha(opacity = 30);}

</style>
<script type="text/javascript">
var global={};
global.realPath="${realPath}";
</script>