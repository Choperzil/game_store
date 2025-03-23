<%-- 
    Document   : register
    Created on : Mar 12, 2025, 10:29:50 PM
    Author     : Mr. Tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create an account</h1>
        <hr/>
        <form action="register" method="post">
            Name<br/>
            <input type="text" name="name" value="${param.name}"/><br/>
            Phone<br/>
            <input type="text" name="phone" value="${param.phone}"/><br/>
            Email<br/>
            <input type="text" name="email" value="${param.email}"/><br/>
            Password<br/>
            <input type="text" name="password" value="${param.password}"/><br/>
            <button type="submit" name="action" value="create">Create</button>
        </form>
    </body>
</html>
