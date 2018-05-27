<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<%request.setAttribute("path", request.getContextPath()); %>
<!-- 引入jquery -->
<script type="text/javascript" src="${ path}/static/js/jquery-3.1.1.min.js"></script>

<!-- 引入样式 -->
<link href="${ path}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
 <script src="${ path}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>

<body>

<table class="table table-striped">
<tr><td>主键</td><td>名称</td><td>年龄</td><td>身份</td></tr>
<c:forEach items="${personlist }" var="person">
<tr><td>${ person.id}</td><td>${ person.name}</td><td>${ person.age}</td><td>${ person.roles==1?"高级管理员":"普通管理员"}</td><</tr>
</c:forEach>
</table>


</body> 
</html>