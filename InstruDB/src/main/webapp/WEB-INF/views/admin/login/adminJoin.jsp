<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/login/adminJoin.js"></script>

<!-- Draw view [S] -->
<div class="content-wrapper signup-page">
	<section class="signup-screen" aria-label="회원가입">
		<form class="form-card signup-card" action="/admin/joinProc.do" method="post" autocomplete="off">
	
	    	<h3 class="form-title">회원가입</h3>
		    <p class="form-desc">필수 항목을 입력해 주세요.</p>
		
		    <div class="form-grid">
	      		<div class="field">
		        	<label for="userId" class="required">아이디</label>
		        	<input id="userId" name="userId" class="form-control" type="text" placeholder="아이디" required />
		        	<small class="error">아이디는 필수입니다.</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="userNm" class="required">이름</label>
		        	<input id="userNm" name="userNm" class="form-control" type="text" placeholder="홍길동" required />
		        	<small class="error">이름은 필수입니다.</small>
		      	</div>
		
				<div class="field">
		        	<label for="userPw" class="required">비밀번호</label>
		        	<input id="userPw" name="userPw" class="form-control" type="password" placeholder="비밀번호" required />
		        	<small class="hint">영문/숫자/특수문자 조합 권장</small>
		        	<small class="error">비밀번호는 필수입니다.</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="userPw2" class="required">비밀번호 확인</label>
		       		<input id="userPw2" name="userPw2" class="form-control" type="password" placeholder="비밀번호 확인" required />
		        	<small class="error" id="pwMismatch">비밀번호가 일치하지 않습니다.</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="email" class="required">이메일</label>
		        	<input id="email" name="email" class="form-control" type="email" placeholder="name@example.com" required />
		        	<small class="error">유효한 이메일이 필요합니다.</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="phone">연락처</label>
		        	<input id="phone" name="phone" class="form-control" type="tel" placeholder="010-1234-5678" pattern="^0\\d{1,2}-\\d{3,4}-\\d{4}$" />
		        	<small class="hint">형식: 010-1234-5678</small>
		        	<small class="error">연락처 형식이 올바르지 않습니다.</small>
		      	</div>
	
		      	<div class="field zip-field">
		        	<label for="postCd" class="required">우편번호</label>
		        	<input id="postCd" name="postCd" class="form-control" type="text" placeholder="우편번호" readonly required />
		        	<button type="button" class="btn-zip" id="btnPost">우편번호 찾기</button>
		        	<small class="error">우편번호는 필수입니다.</small>
		      	</div>
			
		      	<div class="field full">
		        	<label for="addr1" class="required">주소</label>
		        	<input id="addr1" name="addr1" class="form-control" type="text" placeholder="기본주소" readonly required />
		        	<small class="error">주소는 필수입니다.</small>
		      	</div>
		
		      	<div class="field full">
		        	<label for="addr2">상세주소</label>
		        	<input id="addr2" name="addr2" class="form-control" type="text" placeholder="상세주소" />
		      	</div>
		
		      	<div class="field full">
			        <label>알림 수신</label>
			        <div style="display:flex; align-items:center; gap:10px;">
	          			<label class="switch" aria-label="이메일 알림">
			            	<input id="notify" type="checkbox" name="notify" />
			            	<span class="track"></span>
			            	<span class="thumb"></span>
			          	</label>
			          	<label for="notify" style="margin:0; font-size:0.95rem; color:#425a4f;">
			            	이메일로 안내 알림 받기
			          	</label>
			        </div>
		      	</div>
		
		      	<div class="field full">
			        <label class="required">
		          		<input type="checkbox" id="agree" name="agree" required />이용약관/개인정보 처리방침에 동의합니다.
			        </label>
			        <small class="error">동의가 필요합니다.</small>
		      	</div>
		    </div>
		
		    <div class="form-actions">
	      		<button type="button" class="btn-delete" onclick="history.back();">취소</button>
		      	<button type="submit" class="btn-insert" id="joinBtn">가입</button>
	    	</div>
	    	
		</form>
	</section>
</div>
<!-- Draw view [E] -->