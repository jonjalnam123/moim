<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/login/adminLogin.js"></script>

<!-- Draw view [S] -->
<div class="login-container">
  <div class="login-box">
    <h2 class="login-title">MOIM</h2>
    
      <div class="form-group">
        <input type="text" id="adminId" name="adminId" class="login-input" placeholder="아이디" value="mark1">
      </div>
      <div class="form-group">
        <input type="password" id="adminPw" name="adminPw" class="login-input" placeholder="비밀번호" value="1234">
      </div>

      <div class="login-actions">
        <button type="button" id="adminLoginBtn" class="action-btn">로그인</button>
      </div>
  </div>
</div>
<!-- Draw view [E] -->