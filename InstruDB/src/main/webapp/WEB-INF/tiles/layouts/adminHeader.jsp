<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/adminHeader.js"></script>

<!-- Draw view [S] -->
<header>
  <div class="header-left">
    <span class="logo">MOIM</span>
  </div>
  <div class="header-right">
    <span class="user-info">안녕하세요, <strong><c:out value="${adminInfo.adminId}" /></strong>님</span>
    <button class="header-btn" id="">마이페이지</button>
    <button class="header-btn logout" id="logOutBtn" >로그아웃</button>
  </div>
</header>
<!-- Draw view [E] -->