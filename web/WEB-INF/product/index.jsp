<%-- 
    Document   : index.jsp
    Created on : Mar 2, 2025, 3:04:33 PM
    Author     : Mr. Tuan
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<!DOCTYPE html>
<form action="<c:url value='/product/index.do' />" method="get" class="mb-3">
    <div class="input-group">
        <input type="text" name="search" class="form-control" placeholder="Search for games..." value="${param.search}" />
        <select name="sortBy" class="form-select">
            <option value="description ASC" <c:if test="${param.sortBy == 'description ASC'}">selected</c:if>>A to Z</option>
            <option value="description DESC" <c:if test="${param.sortBy == 'description DESC'}">selected</c:if>>Z to A</option>
            <option value="price ASC" <c:if test="${param.sortBy == 'price ASC'}">selected</c:if>>Price Low to High</option>
            <option value="price DESC" <c:if test="${param.sortBy == 'price DESC'}">selected</c:if>>Price High to Low</option>
            </select>
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>
    <div class="row">
    <c:if test="${empty list}">
        <div class="alert alert-warning" role="alert">
            Sorry, no games found 😟!
        </div>
    </c:if>
    <c:forEach var="product" items="${list}">
        <div class="col-sm-4 mt-5">
            <img src="<c:url value="/images/${product.id}.jpg" />" width="100%" class="image">
            <b>${product.description}</b><br/>
            <c:if test="${product.discount!=0}">
                <span class="text-secondary text-decoration-line-through"><fmt:formatNumber value="${product.cost}" type="currency"/></span> <fmt:formatNumber value="${product.cost}" type="currency"/> <span class="badge bg-danger text-white">-${product.discount * 100}%</span>
            </c:if>
            <c:if test="${product.discount==0}">
                <fmt:formatNumber value="${product.cost}" type="currency"/><br/>
            </c:if> 
            <br/>    
            <div class="d-flex justify-content-center">
                <a href="<c:url value="/cart/add.do?id=${product.id}" />" class="btn btn-primary mt-3 mb-3 w-100">
                    <i class="bi bi-bag-plus"></i> Add to Cart
                </a>
            </div>   
        </div>              
    </c:forEach>
</div>
<div class="row">
    <div class="d-flex justify-content-center align-items-center">
        <a href="<c:url value="/?page=1" />" class="btn btn-primary ${page==1?"disabled":""}" title="First"><i class="bi bi-arrow-left-circle"></i></a>
        <a href="<c:url value="/?page=${page - 1}" />" class="btn btn-primary ${page==1?"disabled":""}" title="Previous"><i class="bi bi-arrow-left-circle-fill"></i></a>
        <a href="<c:url value="/?page=${page + 1}" />" class="btn btn-primary ${page==totalPage?"disabled":""}" title="Next"><i class="bi bi-arrow-right-circle-fill"></i></a>
        <a href="<c:url value="/?page=${totalPage}" />" class="btn btn-primary ${page==totalPage?"disabled":""}" title="End"><i class="bi bi-arrow-right-circle"></i></a>
    </div>
    <div class="d-block w-100 text-center mt-2">
        Page: ${page}/${totalPage}
    </div>
</div>

