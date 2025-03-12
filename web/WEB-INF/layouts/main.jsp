<%-- 
    Document   : main
    Created on : Mar 2, 2025, 2:48:18 PM
    Author     : Mr. Tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="<c:url value="/css/site.css" />" rel="stylesheet" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-12 header" style="background-color: purple; padding: 50px;">
                    <h1 class="title"><a href="<c:url value="/" />" style="text-decoration: none; color: yellow">GameVUI</a></h1>
                    <p class="float-end">
                        <c:if test="${account==null}">
                            <a href="<c:url value="/login.jsp" />" style="color: white">Login</a>
                        </c:if>
                        <c:if test="${account!=null}">
                            <i style="color: white">Welcome ${account.name} |</i>
                            <a href="<c:url value="/account/logout.do" />" style="color: white">Logout</a>
                        </c:if>
                    </p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <jsp:include page="/WEB-INF/${controller}/${action}.jsp" />
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12 footer" style="padding-top: 20px">
                    Copyright by FPT students
                </div>
            </div>
            <div class="messenger-logo">
                <a href="https://www.facebook.com/choperzil.johny/">
                    <img src="<c:url value="/images/messenger-logo.jpg" />" alt="Messenger Logo">
                </a>
            </div>
        </div>    
    </div>        
</body>
</html>
