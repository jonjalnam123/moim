<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminUserAccept.js"></script>

<div class="split-layout grid-split">

	<aside class="split-left list-panel">
		<div class="list-header">
      		<div class="list-title">관리자 목록</div>

      		<div class="list-search">
        		<select id="schType" class="form-select">
	          		<option value="">전체</option>
		          	<option value="code">코드</option>
		          	<option value="name">명칭</option>
        		</select>

        		<input id="schText" class="form-control" type="text" placeholder="검색어" />

        		<select id="schUseYn" class="form-select">
          			<option value="">사용여부 전체</option>
          			<option value="Y">사용</option>
          			<option value="N">미사용</option>
        		</select>

        		<button type="button" id="btnSearch" class="search-btn">조회</button>
        		<button type="button" id="btnReset" class="btn-refresh">초기화</button>
      		</div>
    	</div>

    	<div class="list-body">
			<div class="grid-wrap" style="padding:10px; overflow:auto;">
			    <table class="table-grid">
		      		<thead>
				        <tr>
				          <th>아이디</th>
				          <th>이름</th>
				          <th>휴대폰</th>
				          <th>우편번호</th>
				          <th>주소</th>
				          <th>상세주소</th>
				          <th>부서코드</th>
				          <th>팀코드</th>
				          <th>직책코드</th>
				          <th>성별</th>
				          <th>삭제여부</th>
				        </tr>
			      	</thead>
			      	<tbody>
				      	<c:forEach var="admin" items="${adminList}" varStatus="cnt">
					        <tr class="adminInfoTr" data-rowkey="${cnt.index}" data-no="${admin.adminNo}" data-id="${admin.adminId}">
					        	<td>${admin.adminId}</td>
					        	<td>${admin.adminNm}</td>
					        	<td>${admin.adminPh}</td>
					        	<td>${admin.adminPostCd}</td>
					        	<td>${admin.adminAddress}</td>
					        	<td>${admin.adminDAddress}</td>
					        	<td>${admin.adminDeptNm}</td>
					        	<td>${admin.adminTeamNm}</td>
					        	<td>${admin.adminPositionNm}</td>
					        	<td>${admin.adminGenderNm}</td>
					        	<td>${admin.adminDelYnNm}</td>
					        </tr>
				        </c:forEach>
		      		</tbody>
		    	</table>
			</div>
    	</div>
	</aside>

	<div class="split-resizer" id="splitResizer" aria-hidden="true"></div>

	<section class="split-right">
		<div class="content-scroll" style="padding:12px 14px;">
	   		<div class="page-header">
	        	<h2>사용자</h2>
		        <div class="breadcrumb">
		        	<a href="#">관리자</a>&nbsp;&gt;&nbsp;<span>사용자</span>
		        </div>
	      	</div>
	     	<div class="form-card">
	       		<div class="form-grid">
	          		<div class="field">
	            		<label class="" for="fCode">아이디</label>
	            		<input id="adminId" name="adminId" class="form-control" type="text" />
	            		<input type="hidden" id="adminNo" name="adminNo" />
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	
	          		<div class="field">
	            		<label class="" for="fName">이름</label>
	            		<input id="adminNm" name="adminNm" class="form-control" type="text" />
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="fName">휴대폰</label>
	            		<input id="adminPh" name="adminPh" class="form-control" type="text" />
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	          		
	          		<div class="field zip-field">
	            		<label class="" for="adminPostCd">우편번호</label>
	            		<input id="adminPostCd" name="adminPostCd" class="form-control" type="text" placeholder="우편번호" readonly/>
	            		<input type="button" class="btn btn-zip" id="getPostCode" value="우편번호 찾기">
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="adminAddress">주소</label>
	            		<input id="adminAddress" name="adminAddress" class="form-control" type="text" placeholder="주소" readonly/>
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="adminDAddress">상세주소</label>
	            		<input id="adminDAddress" name="adminDAddress" class="form-control" type="text" />
	            		<!-- <div class="error">필수 입력입니다.</div> -->
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="adminDeptCd">부서</label>
	            		<select id="adminDeptCd" name="adminDeptCd" class="form-select" style="width:100%;">
	            			<option value="">선택</option>
	            			<c:forEach var="adminUnit" items="${adminUnitList}">
	            				<option value="${adminUnit.adminUnitCd}" data-id="${adminUnit.adminUnitId}">${adminUnit.adminUnitNm}</option>
	            			</c:forEach>
	            		</select>
	        			<!-- <div class="error">필수 선택입니다.</div> -->
	          		</div>
	          		
	          		<div id="adminTeamDiv" class="field" style="display: none;">
	            		<label class="" for="adminTeamCd">팀</label>
	            		<select id="adminTeamCd" name="adminTeamCd" class="form-select" >
	            		</select>
	          		</div>
	
	          		<div id="adminPositionDiv" class="field" style="display: none;">
	            		<label class="" for="adminPositionCd">직책</label>
	            		<select id="adminPositionCd" name="adminPositionCd" class="form-select" >
	            		</select>
	        			<!-- <div class="error">필수 선택입니다.</div> -->
	          		</div>
	          		
        			<div class="field">
	            		<label class="required" for="adminGradeCd">권한등급</label>
	            		<select id="adminGradeCd" name="adminGradeCd" class="form-select" style="width:100%;">
	            			<option value="">선택</option>
	            			<c:forEach var="adminGrade" items="${adminGradeList}">
	            				<option value="${adminGrade.commCd}">${adminGrade.commNm}</option>
	            			</c:forEach>
	            		</select>	
	        			<!-- <div class="error">필수 선택입니다.</div> -->
	          		</div>
	          		
	          		<div class="field">
			            <label class="required">성별</label>
			            <div>
		              		<label style="margin-right:10px;">
			                	<input type="radio" name="adminGender" value="M" checked /> 남자
			              	</label>
			              	<label>
			                	<input type="radio" name="adminGender" value="W" /> 여자
			              	</label>
			            </div>
		          	</div>
	
	          		<div class="field full">
	            		<label for="fDesc">설명</label>
	            		<textarea id="fDesc" class="form-control"></textarea>
	          		</div>
	        	</div>
	
		        <div class="form-actions">
  					<button type="button" class="btn-refresh" 	id="btnRef"		value="R"   	style="display: none;">초기화</button>
       				<button type="button" class="btn-insert" 		id="btnNew"		value="N" 		style="display: none;">추가</button>
       				<button type="button" class="btn-insert"   	id="btnReg"   	value="I" >저장</button>
        			<button type="button" class="btn-update"		id="btnUpd" 		value="U" 		style="display: none;">수정</button>
         			<button type="button" class="btn-delete"  	id="btnDel" 		value="D" 		style="display: none;">삭제</button>
		        </div>
	      	</div>
	    </div>
	</section>
	
</div>
<!-- Draw view [E] -->