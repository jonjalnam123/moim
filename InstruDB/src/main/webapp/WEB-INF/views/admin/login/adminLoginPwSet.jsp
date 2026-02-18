<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/login/adminLoginPwSet.js"></script>

<!-- Model Param [S] -->
<input type="hidden" id="adminNo" name="adminNo" value="${adminInfo.adminNo}">
<input type="hidden" id="adminId" name="adminId" value="${adminInfo.adminId}">
<!-- Model Param [E] -->

<!-- Draw view [S] -->
<div class="login-container">
	<div class="login-box">

			<h2 class="login-title">비밀번호 재설정</h2>
			
      		<div class="form-group">
        		<input type="password" id="adminPwNow" name="adminPwNow" class="login-input" placeholder="현재 비밀번호" >
        		<input type="hidden" id="adminPwNowResult" name="adminPwNowResult">
      		</div>
      		<div class="form-group">
	        	<input type="password" id="adminPw" name="adminPw" class="login-input" placeholder="비밀번호" readonly>
	        	<input type="hidden" id="adminPwResult" name="adminPwResult">
	      	</div>
	      	<div class="form-group">
	        	<input type="password" id="adminPwChk" name="adminPwChk" class="login-input" placeholder="비밀번호 확인" readonly>
	        	<input type="hidden" id="adminPwChkResult" name="adminPwChkResult">
	        	<div class="error">특수문자 1개 이상 영어, 숫자만 14자리 입력</div>
	      	</div>
	
	      	<div class="login-actions">
		        <button type="button" id="adminCancelBtn" 	class="cancel-btn" >취소</button>
		        <button type="button" id="adminRegBtn" 		class="reg-btn">설정</button>
	      	</div>
	</div>
</div>
<!-- Draw view [E] -->