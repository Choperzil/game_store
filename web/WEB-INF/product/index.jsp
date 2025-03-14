<%-- 
    Document   : index.jsp
    Created on : Mar 2, 2025, 3:04:33 PM
    Author     : Mr. Tuan
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<div class="row">
    <c:forEach var="product" items="${list}">
        <div class="col-sm-4 mt-5">
            <img src="<c:url value="/images/${product.id}.jpg" />" width="100%" class="image">
<!--            Id: ${product.id}<br/>-->
            <b>Description:</b> ${product.description}<br/>
            <c:if test="${product.discount!=0}">
                <b>Price:</b> $<span class="text-secondary text-decoration-line-through">${product.price}</span> $${product.cost} <span class="badge bg-danger text-white">-${product.discount * 100}%</span>
            </c:if>
            <c:if test="${product.discount==0}">
                <b>Price:</b> $${product.cost}<br/>
            </c:if> 
            <br/>    
            <a href="<c:url value="/cart/add.do?id=${product.id}" />" class="btn btn-primary mt-3 mb-3"><i class="bi bi-cart-plus"></i> Add to Cart</a>    

            <!--
            Discount: ${product.discount}<br/>     -->
        </div>              
    </c:forEach>
</div>
<div class="row">
    <div class="d-flex justify-content-center align-items-center">
        <a href="<c:url value="/?page=1" />" class="btn btn-primary ${page==1?"disabled":""}" title="First"><i class="bi bi-arrow-left-circle"></i></a>
        <a href="<c:url value="/?page=${page - 1}" />" class="btn btn-primary ${page==1?"disabled":""}" title="Previous"><i class="bi bi-arrow-left-circle-fill"></i></a>
        <a href="<c:url value="/?page=${page + 1}" />" class="btn btn-primary ${page==totalPage?"disabled":""}" title="Next"><i class="bi bi-arrow-right-circle-fill"></i></a>
        <a href="<c:url value="/?page=${totalPage}" />" class="btn btn-primary ${page==totalPage?"disabled":""}" title="End"><i class="bi bi-arrow-right-circle"></i></a>
        Page: ${page}/${totalPage}
    </div>
</div>

