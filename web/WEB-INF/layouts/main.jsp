<%-- 
    Document   : main
    Created on : Mar 2, 2025, 2:48:18 PM
    Author     : Mr. Tuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 header" style="background-color: #5561F5; padding: 20px;">
                    <div class="d-flex align-items-center justify-content-between">
                        <!-- Logo -->
                        <div class="logo">
                            <a href="<c:url value="/" />">
                                <img src="<c:url value="/images/logo.png" />" alt="Store Logo" width="400px"/>
                            </a>
                        </div>

                        <!-- Login & Cart -->
                        <div class="d-flex align-items-center ms-auto">
                            <c:if test="${account == null}">
                                <a href="<c:url value="/login.jsp" />" class="text-white me-3" style="text-decoration: none; font-size: 20px"><i class="bi bi-person-circle fs-3"></i> Login |</a>
                            </c:if>
                            <c:if test="${account != null}">
                                <span class="text-white me-3" style="text-decoration: none; font-size: 20px">${account.name} |</span>
                                <a href="<c:url value="/account/logout.do" />" class="text-white me-3" style="text-decoration: none; font-size: 20px">Logout |</a>
                            </c:if>

                            <!-- Cart -->
                            <a href="<c:url value='/cart/index.do' />" class="btn btn-outline-light text-light btn-sm p-2">
                                <i class="bi bi-bag-dash"> Cart</i>
                                <span class="badge bg-light text-dark">${cart.quantity}</span>
                            </a>
                        </div>
                    </div>
                </div>
<!--        Body Content                -->
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <jsp:include page="/WEB-INF/${controller}/${action}.jsp" />
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12 footer" style="padding-top: 50px; background-color: #5561F5; color: white;">
                    Project by group 8
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
