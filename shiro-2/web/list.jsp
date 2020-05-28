<%--
  Created by IntelliJ IDEA.
  User: Yixin Chen
  Date: 2020/5/26
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <h1>List</h1>
</head>
<body>
<shiro:notAuthenticated>
    未登录包括记住我
</shiro:notAuthenticated>
<br>
<shiro:authenticated>
    用户[<shiro:principal/>]验证已经通过
</shiro:authenticated>
<br>
<shiro:user>
    欢迎<shiro:principal/>
</shiro:user>
Welcome:<shiro:principal>

</shiro:principal>
<br>
<a href="shiro/testShiroAnnotation">testShiroAnnotation</a>
<br>
<shiro:hasRole name="admin">
    用户[<shiro:principal/>]可以访问<a href="admin.jsp">Admin Page</a>
</shiro:hasRole>
<br>
<shiro:hasRole name="user">
    用户[<shiro:principal/>]可以访问<a href="user.jsp">User Page</a>
</shiro:hasRole>
<br>
<shiro:hasAnyRoles name="admin">
    <h1>shiro不错</h1>
</shiro:hasAnyRoles>
<br>
<a href="shiro/logout">logout</a>
</body>
</html>
