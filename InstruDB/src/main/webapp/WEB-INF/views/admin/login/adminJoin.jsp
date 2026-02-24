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
		
		      	<div class="field zip-field">
	            	<label class="required" for="adminEmail">이메일</label>
	            	<input id="adminEmailOrg" name="adminEmailOrg" type="hidden"/>
		        	<input id="adminEmail" name="adminEmail" class="form-control" type="email" placeholder="name@example.com" required />
	           		<button type="button" class="btn-zip" id="getEamilChkBtn">인증</button>
	           		<small class="error">유효한 이메일이 필요합니다.</small>
		      	</div>
		      	
		      	<div class="field" id="emailChkDiv">
		        	<label for="emailChk">메일인증번호</label>
		        	<input type="text" id="emailChk" name="emailChk" class="form-control" />
		        	<small class="error">유효한 이메일이 필요합니다.</small>
		      	</div>
		      	
     			<div class="field zip-field">
	            	<label class="required" for="adminPhone">연락처</label>
	            	<input id="adminPhoneOrg" name="adminPhoneOrg" type="hidden"/>
		        	<input id="adminPhone" name="adminPhone" class="form-control" type="email" placeholder="숫자만 입력해주세요." required />
	           		<button type="button" class="btn-zip" id="getPhoneChkBtn">인증</button>
	           		<small class="error">유효한 이메일이 필요합니다.</small>
		      	</div>
		      	
 			    <div class="field" id="poneChkDiv">
		        	<label for="emailChk">핸드폰인증번호</label>
		        	<input type="text" id="poneChk" name="poneChk" class="form-control" />
		        	<small class="error">유효한 이메일이 필요합니다.</small>
		      	</div>
	
				<!-- 우편번호 -->
				<div class="field zip-field">
	            	<label class="required" for="adminPostCd">우편번호</label>
	           		<input id="adminPostCd" name="adminPostCd" class="form-control" type="text" placeholder="우편번호" readonly required/>
	           		<button type="button" class="btn-zip" id="getPostCode">찾기</button>
	           		<small class="error">우편번호는 필수입니다.</small>
				</div>
				
				<!-- ✅ 주소: full 제거 (다른 인풋과 동일 폭) -->
				<div class="field">
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