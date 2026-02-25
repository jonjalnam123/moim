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
		      	
		      	<div class="field zip-field">
	            	<label class="required" for="adminId">아이디</label>
		        	<input type="text" id="adminId" name="adminId" class="form-control" placeholder="아이디"/>
		        	<input type="hidden" id="adminIdChk" name="adminIdChk"/>
	           		<button type="button" class="btn-zip" id="adminIdChkBtn">중복확인</button>
		      	</div>
		
		      	<div class="field">
		        	<label for="adminNm" class="required">이름</label>
		        	<input id="adminNm" name="adminNm" class="form-control" type="text" placeholder="홍길동"/>
		      	</div>
		
				<div class="field">
		        	<label for="adminPw" class="required">비밀번호</label>
		        	<input id="adminPw" name="adminPw" class="form-control" type="password" placeholder="비밀번호"/>
		        	<small class="error">특수문자 1개 이상 영어, 숫자만 14자리 입력</small>
		      	</div>
		
		      	<div class="field">
		        	<label for="adminPwChk" class="required">비밀번호 확인</label>
		       		<input id="adminPwChk" name="adminPwChk" class="form-control" type="password" placeholder="비밀번호 확인"/>
		        	<small class="hint" id="pwMatch" style="display: none;">비밀번호가 일치합니다.</small>
		        	<small class="error" id="pwMismatch">비밀번호가 일치하지 않습니다.</small>
		      	</div>
		
		      	<div class="field zip-field">
	            	<label class="required" for="adminEmail">이메일</label>
		        	<input type="text" id="adminEmail" name="adminEmail" class="form-control" placeholder="name@example.com"/>
	           		<button type="button" class="btn-zip" id="adminEmailChkBtn">인증</button>
	           		<button type="button" class="btn-zip" id="adminEmailReChkBtn" style="display: none;">재인증</button>
		      	</div>

		      	<div class="field zip-field">
	            	<label class="required" for="adminEmaliNumChk">인증번호</label>
		        	<input type="text" id="adminEmaliNumChk" name="adminEmaliNumChk" class="form-control" placeholder="03:00" onkeyup="checkNumSixRepl(this)" readonly/>
	           		<input type="hidden" id="adminEmailChkYn" name="adminEmailChkYn" value="N"/>
	           		<button type="button" class="btn-zip" id="adminEmailNumChkBtn" disabled>확인</button>
		      	</div>
		      	
				<div class="field">
	           		<label class="required" for="adminPh">연락처</label>
	           		<input id="adminPh" name="adminPh" class="form-control" type="text" placeholder="숫자만 입력"/>
				</div>
	
				<!-- 우편번호 -->
				<div class="field zip-field">
	            	<label class="required" for="adminPostCd">우편번호</label>
	           		<input id="adminPostCd" name="adminPostCd" class="form-control" type="text" placeholder="우편번호" readonly/>
	           		<button type="button" class="btn-zip" id="getPostCode">찾기</button>
				</div>
				
				<!-- ✅ 주소: full 제거 (다른 인풋과 동일 폭) -->
				<div class="field">
	           		<label class="required" for="adminAddress">주소</label>
	           		<input id="adminAddress" name="adminAddress" class="form-control" type="text" placeholder="주소" readonly/>
				</div>
				
				<!-- ✅ 상세주소: full 제거 -->
				<div class="field">
            		<label class="required" for="adminDAddress">상세주소</label>
            		<input id="adminDAddress" name="adminDAddress" class="form-control" type="text" />
				</div>
				
				<!-- ✅ 성별(체크박스 UI, 단일 선택) -->
				<div class="field">
			  		<label class="required">성별</label>
				  	<div class="check-chips" role="group" aria-label="성별 선택">
				    	<input type="checkbox" id="genderM" name="adminGender" value="M" class="gender-check" />
				    	<label for="genderM">남</label>
				    	<input type="checkbox" id="genderF" name="adminGender" value="F" class="gender-check" />
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
		      	<button type="button" class="btn-insert" id="joinBtn">가입</button>
	    	</div>
	    	
		</form>
	</section>
</div>
<!-- Draw view [E] -->