<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: chjkt
  Date: 2020/10/17
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<shiro:hasPermission name="用户管理">
    <a href="#">用户管理</a>
</shiro:hasPermission>
<shiro:hasPermission name="部门管理">
    <a href="#">部门管理</a>
</shiro:hasPermission>
<shiro:hasPermission name="角色管理">
    <a href="#">角色管理</a>
</shiro:hasPermission>
<shiro:hasPermission name="装箱管理">
    <a href="#">装箱管理</a>
</shiro:hasPermission>
</body>
</html>
