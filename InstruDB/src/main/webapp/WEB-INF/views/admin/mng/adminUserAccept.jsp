<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminUserAccept.js"></script>

<!-- Model 파라미터 -->
<input type="hidden"  id="searchGbParam" name="searchGbParam" value="${pager.searchGb}">
<input type="hidden"  id="pageNumParam" name="pageNumParam" value="${pager.pageNum}">

<div class="split-layout grid-split">
	<aside class="split-left list-panel">
		<!--  조회 조건 [S] -->
		<form action="/admin/userAccept.do" id="adminUserAcceptSearchForm">
			<input type="hidden"  id="pageNum" name="pageNum" value="1">
			<div class="list-header">
	      		<div class="list-title"></div>
	      		<div class="list-search">
	        		<select id="searchGb" name=searchGb class="form-select">
			          	<option class="s" value="adminNm">이름</option>
			          	<option class="s" value="adminId">아이디</option>
	        		</select>
	        		<input id="searchTxt" name="searchTxt" class="form-control" type="text" placeholder="검색어" value="${pager.searchTxt}"/>
	        		<button type="button" id="btnSearch" class="search-btn">조회</button>
	        		<button type="button" id="btnReset" class="btn-refresh">초기화</button>
	      		</div>
	    	</div>
	   	</form>
		<!--  조회 조건 [E] -->

		<!--  그리드 [S] -->
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
				          <th>성별</th>
				          <th>승인여부</th>
				          <th>등록일시</th>
				        </tr>
			      	</thead>
			      	<tbody>
				      	<c:forEach var="adminAccept" items="${adminAcceptList}" varStatus="cnt">
					        <tr class="adminInfoTr" data-rowkey="${cnt.index}" data-no="${adminAccept.adminNo}" data-id="${adminAccept.adminId}">
					        	<td>${adminAccept.adminId}</td>
					        	<td>${adminAccept.adminNm}</td>
					        	<td>${adminAccept.adminPh}</td>
					        	<td>${adminAccept.adminPostCd}</td>
					        	<td>${adminAccept.adminAddress}</td>
					        	<td>${adminAccept.adminDAddress}</td>
					        	<td>${adminAccept.adminGenderNm}</td>
					        	<td>${adminAccept.adminRegAcceptNm}</td>
					        	<td>${adminAccept.regDt}</td>
					        </tr>
				        </c:forEach>
		      		</tbody>
		    	</table>
		    	
		    	<!-- 페이징 [S] -->
			    <div class="pagination" id="paging">
				    <button class="p" data-list-pn="${pager.startNum-1}" type="button">&laquo;</button>
	    			<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
						<button class="p" data-list-pn="${i}">${i}</button>
					</c:forEach>
				    <button class="p" data-list-pn="${pager.lastNum+1}" type="button">&raquo;</button>
			  	</div>
			  	<!-- 페이징 [E] -->
			  	
			</div>
    	</div>
    	<!--  그리드 [E] -->
    	
	</aside>

	<div class="split-resizer" id="splitResizer" aria-hidden="true"></div>

	<section class="split-right">
		<div class="content-scroll" style="padding:12px 14px;">
	   		<div class="page-header">
	        	<h2>가입승인관리</h2>
		        <div class="breadcrumb">
		        	<a href="#">관리자</a>&nbsp;&gt;&nbsp;<span>가입승인관리</span>
		        </div>
	      	</div>
	     	<div class="form-card">
	       		<div class="form-grid">
	          		<div class="field">
	            		<label class="" for="adminId">아이디</label>
	            		<input id="adminId" name="adminId" class="form-control" type="text" readonly/>
	            		<input type="hidden" id="adminNo" name="adminNo" />
	          		</div>
	
	          		<div class="field">
	            		<label class="" for="adminNm">이름</label>
	            		<input id="adminNm" name="adminNm" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="adminPh">휴대폰</label>
	            		<input id="adminPh" name="adminPh" class="form-control" type="text" readonly/>
	          		</div>

	          		<div class="field">
	            		<label class="" for="adminPostCd">우편번호</label>
	            		<input id="adminPostCd" name="adminPostCd" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="adminAddress">주소</label>
	            		<input id="adminAddress" name="adminAddress" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="" for="adminDAddress">상세주소</label>
	            		<input id="adminDAddress" name="adminDAddress" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
		            	<label for="adminRegAccept" class="required">승인여부</label>
		            	<select id="adminRegAccept" name="adminRegAccept" class="form-select" style="width:100%;" disabled>
		            		<option value="N">미승인</option>
		            		<option value="R">반려</option>
		            	</select>
		         	</div>
	          		
	          		<div class="field">
			            <label class="required">성별</label>
			            <div>
		              		<label style="margin-right:10px;">
			                	<input type="radio" name="adminGender" value="M" checked disabled /> 남자
			              	</label>
			              	<label>
			                	<input type="radio" name="adminGender" value="W" disabled /> 여자
			              	</label>
			            </div>
		          	</div>
		          	
		          	<div class="field full">
		            	<label for="adminRejectCn">반려사유</label>
		            	<textarea id="adminRejectCn" name="adminRejectCn" class="form-control" placeholder="반려 선택 시 입력해 주세요."></textarea>
		          	</div>
		          	
	        	</div>
	
		        <div class="form-actions">
<!--   					<button type="button" class="btn-refresh" 	id="btnRef"		value="R"   	style="display: none;">초기화</button> -->
       				<button type="button" class="btn-insert"   	id="btnReg"   	value="U">승인</button>
        			<button type="button" class="btn-update"		id="btnDel" 		value="D">반려</button>
		        </div>
	      	</div>
	    </div>
	</section>
	
</div>
<!-- Draw view [E] -->