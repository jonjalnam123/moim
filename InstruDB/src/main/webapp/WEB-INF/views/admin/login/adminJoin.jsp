<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/login/adminJoin.js"></script>

<!-- Model Param [S] -->
<input type="hidden" id="agreeService" value="${agreeService}"/>
<input type="hidden" id="agreePrivacy" value="${agreePrivacy}"/>
<input type="hidden" id="agreeMarketing" value="${agreeMarketing}"/>
<input type="hidden" id="agreeConsign" value="${agreeConsign}"/>
<!-- Model Param [E] -->

<!-- Draw view [S] -->
<div class="content-wrapper signup-page">
	<section class="signup-screen" aria-label="회원가입">
		<form class="form-card signup-card" action="/admin/joinProc.do" method="post" autocomplete="off">
	
	    	<h3 class="form-title">회원가입</h3>
		    <p class="form-desc">필수 항목을 입력해 주세요.</p>
		
		    <div class="form-grid">
	      		<div class="field">
		        	<label for="userId" class="required">아이디</label>
		        	<input id="userId" name="userId" class="form-control" type="text" placeholder="아이디"/>
		        	<!-- <small class="error">아이디는 필수입니다.</small> -->
		      	</div>
		
		      	<div class="field">
		        	<label for="userNm" class="required">이름</label>
		        	<input id="userNm" name="userNm" class="form-control" type="text" placeholder="홍길동"/>
		        	<!-- <small class="error">이름은 필수입니다.</small> -->
		      	</div>
		
				<div class="field">
		        	<label for="userPw" class="required">비밀번호</label>
		        	<input id="userPw" name="userPw" class="form-control" type="password" placeholder="비밀번호" required />
		        	<small class="hint">영문/숫자/특수문자 조합 권장</small>
		        	<small class="error">비밀번호는 필수입니다.</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="userPw2" class="required">비밀번호 확인</label>
		       		<input id="userPw2" name="userPw2" class="form-control" type="password" placeholder="비밀번호 확인"/>
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
	
				<!-- 우편번호 -->
				<div class="field zip-field">
<!-- 				  	<label for="postCd" class="required">우편번호</label>
				  	<input id="postCd" name="postCd" class="form-control" type="text" placeholder="우편번호" readonly required />
				  	<button type="button" class="btn-zip" id="btnPost">우편번호 찾기</button>
				  	<small class="error">우편번호는 필수입니다.</small> -->
		            	<label class="required" for="adminPostCd">우편번호</label>
	            		<input id="adminPostCd" name="adminPostCd" class="form-control" type="text" placeholder="우편번호" readonly required/>
	            		<button type="button" class="btn-zip" id="getPostCode">찾기</button>
	            		<small class="error">우편번호는 필수입니다.</small>
				</div>
				
				<!-- ✅ 주소: full 제거 (다른 인풋과 동일 폭) -->
				<div class="field">
<!-- 			  		<label for="addr1" class="required">주소</label>
				  	<input id="addr1" name="addr1" class="form-control" type="text" placeholder="기본주소" readonly required />
				  	<small class="error">주소는 필수입니다.</small> -->
	           		<label class="required" for="adminAddress">주소</label>
	           		<input id="adminAddress" name="adminAddress" class="form-control" type="text" placeholder="주소" readonly/>
				</div>
				
				<!-- ✅ 상세주소: full 제거 -->
				<div class="field">
<!-- 				  	<label for="addr2">상세주소</label>
				  	<input id="addr2" name="addr2" class="form-control" type="text" placeholder="상세주소" /> -->
            		<label class="required" for="adminDAddress">상세주소</label>
            		<input id="adminDAddress" name="adminDAddress" class="form-control" type="text" />
				</div>
				
				<!-- ✅ 성별(체크박스 UI, 단일 선택) -->
				<div class="field">
			  		<label class="required">성별</label>
				  	<div class="check-chips" role="group" aria-label="성별 선택">
				    	<input type="checkbox" id="genderM" name="gender" value="M" class="gender-check" />
				    	<label for="genderM">남</label>
				    	<input type="checkbox" id="genderF" name="gender" value="F" class="gender-check" />
				    	<label for="genderF">여</label>
				  	</div>
			  		<small class="error">성별은 필수입니다.</small>
				</div>
				
				<!-- ✅ 추가 설정(토글 3개) : full 폭으로 묶어서 보기 좋게 -->
				<div class="field full prefs">
			  		<label>추가 설정</label>
				
				  	<div class="prefs-grid">
				    	<div class="toggle-card">
				      		<label class="toggle-label" for="adminCarYn">자차유무</label>
					      	<label class="switch" aria-label="자차유무">
					        <input id="adminCarYn" type="checkbox" name="adminCarYn"/>
					        <span class="track"></span>
					        <span class="thumb"></span>
				      		</label>
					    </div>
					
					    <div class="toggle-card">
				      		<label class="toggle-label" for="adminEmailAlertYn">이메일수신</label>
					      	<label class="switch" aria-label="이메일수신">
					        	<input id="adminEmailAlertYn" type="checkbox" name="adminEmailAlertYn" />
					        	<span class="track"></span>
					        	<span class="thumb"></span>
				      		</label>
					    </div>
					
					    <div class="toggle-card">
				      		<label class="toggle-label" for="adminSmsAlertYn">문자수신</label>
					      	<label class="switch" aria-label="문자수신">
					        	<input id="adminSmsAlertYn" type="checkbox" name="adminSmsAlertYn" />
					        	<span class="track"></span>
					        	<span class="thumb"></span>
					      	</label>
			    		</div>
			    		
			  		</div>
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