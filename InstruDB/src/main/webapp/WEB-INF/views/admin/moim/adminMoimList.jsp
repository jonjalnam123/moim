<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/moim/adminMoimList.js"></script>

<!-- Model 파라미터 -->
<input type="hidden"  id="searchGbParam" name="searchGbParam" value="${pager.searchGb}">
<input type="hidden"  id="pageNumParam" name="pageNumParam" value="${pager.pageNum}">

<div class="split-layout grid-split">
	<aside class="split-left list-panel">
		<!--  조회 조건 [S] -->
		<form action="/admin/user.do" id="adminUserSearchForm">
			<input type="hidden"  id="pageNum" name="pageNum" value="1">
			<div class="list-header">
	      		<div class="list-title"></div>
	      		<div class="list-search">
	        		<select id="searchGb" name=searchGb class="form-select">
			          	<option class="s" value="moimTitle">모임명</option>
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
	  		<div class="grid-wrap">
				
			    <c:choose>
		      		<c:when test="${empty adminList}">
		        		<div class="grid-empty-wrap">
			          		<div class="table-empty">
			            		<div class="table-empty-illu" aria-hidden="true">
			              			<img src="${pageContext.request.contextPath}/resources/static/img/empty-state.svg" alt="" class="empty-illu-img"/>
			            		</div>
			           		 	<div class="table-empty-title">데이터가 없습니다</div>
			            		<div class="table-empty-desc">검색 조건을 변경하거나 초기화 후 다시 조회해보세요.</div>
			          		</div>
			        	</div>
			      	</c:when>
					
			      	<c:otherwise>
			        	<div class="grid-scroller">
			          		<table class="table-grid col-resize">
			      				<thead>
					        		<tr>
					          			<th>모임명</th>
								        <th>날짜</th>
								        <th>최대인원</th>
								        <th>장소명</th>
								        <th>구분</th>
								        <th>등록날짜</th>
									</tr>
			      				</thead>
			            		<tbody>
									<c:forEach var="adminMoim" items="${adminMoimList}" varStatus="cnt">
						        		<tr class="adminMoimTr" data-rowkey="${cnt.index}" data-id="${admin.adminId}">
								        	<td>${adminMoim.moimTitle}</td>
								        	<td>${adminMoim.moimDt}</td>
								        	<td>${adminMoim.moimMaxCnt}</td>
								        	<td>${adminMoim.moimLocateNm}</td>
								        	<td>${adminMoim.moimGbNm}</td>
								        	<td>${adminMoim.regDt}</td>
						        		</tr>
					        		</c:forEach>
			            		</tbody>
			          		</table>
			        	</div>
			
						<!-- 페이징 [S] -->
			        	<div class="pagination-wrap">
			          		<div class="pagination" id="paging">
			            		<button class="p" data-list-pn="${pager.startNum-1}" type="button">&laquo;</button>
			            		<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
			             			 <button class="p" data-list-pn="${i}">${i}</button>
			            		</c:forEach>
			            		<button class="p" data-list-pn="${pager.lastNum+1}" type="button">&raquo;</button>
			          		</div>
			        	</div>
			        	<!-- 페이징 [E] -->
		        
      				</c:otherwise>
	    		</c:choose>
	  		</div>
		</div>
    	<!--  그리드 [E] -->
    	
	</aside>

	<div class="split-resizer" id="splitResizer" aria-hidden="true"></div>

	<section class="split-right">
		<div class="content-scroll" style="padding:12px 14px;">
	   		<div class="page-header">
	        	<h2>${SS_MENU_NM}</h2> 
				<div class="breadcrumb">
				    <a href="#">${SS_MENU_PNM}</a>&nbsp;&gt;&nbsp;<span>${SS_MENU_NM}</span>
				    <button type="button"
				            id="btnFavorite"
				            class="breadcrumb-fav-icon"
				            aria-pressed="${SS_FAV_MENU_YN eq 'Y' ? 'true' : 'false'}"
				            title="즐겨찾기 해제"
				            onclick="selectFavoriteMenu(this, '${SS_MENU_ID}')">
				
				        <svg viewBox="0 0 24 24" class="fav-star" aria-hidden="true">
				            <path d="M12 3.8l2.45 4.96 5.47.8-3.96 3.86.94 5.45L12 16.3 7.1 18.87l.94-5.45-3.96-3.86 5.47-.8L12 3.8z"/>
				        </svg>
				
				    </button>
				</div>
	      	</div>
	     	<div class="form-card">
	       		<div class="form-grid">
	          		
	          		<div class="field">
	            		<label class="required" for="adminNm">모임명</label>
	            		<input id="moimTitle" name="moimTitle" class="form-control" type="text" />
	            		<input id="moimId" name="moimId" class="form-control" type="hidden" />
	          		</div>
	          		
     			    <div class="field">
	            		<label class="required" for="adminDAddress">날짜</label>
	            		<input id="adminEmail" name="adminEmail" class="form-control" type="text" />
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="adminPh">최대인원</label>
	            		<input id="adminPh" name="adminPh" class="form-control" type="text" onkeyup="checkNumPhone(this);" placeholder="숫자만 입력해주세요."/>
	          		</div>

	          		<div class="field">
	            		<label class="required" for="adminDeptCd">모임장소</label>
	            		<select id="adminDeptCd" name="adminDeptCd" class="form-select" style="width:100%;">
	            			<option value="">선택</option>
	            			<c:forEach var="adminUnit" items="${adminUnitList}">
	            				<option value="${adminUnit.adminUnitCd}" data-id="${adminUnit.adminUnitId}">${adminUnit.adminUnitNm}</option>
	            			</c:forEach>
	            		</select>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="adminDAddress">상세주소</label>
	            		<input id="adminDAddress" name="adminDAddress" class="form-control" type="text" />
	          		</div>

	          		<div id="adminTeamDiv" class="field" style="display: none;">
	            		<label class="required" for="adminTeamCd">팀</label>
	            		<select id="adminTeamCd" name="adminTeamCd" class="form-select" >
	            		</select>
	          		</div>
	
	          		<div id="adminPositionDiv" class="field" style="display: none;">
	            		<label class="required" for="adminPositionCd">직책</label>
	            		<select id="adminPositionCd" name="adminPositionCd" class="form-select" >
	            		</select>
	          		</div>
	          		
        			<div class="field">
	            		<label class="required" for="adminGradeCd">권한등급</label>
	            		<select id="adminGradeCd" name="adminGradeCd" class="form-select" style="width:100%;">
	            			<option value="">선택</option>
	            			<c:forEach var="adminGrade" items="${adminGradeList}">
	            				<option value="${adminGrade.commCd}">${adminGrade.commNm}</option>
	            			</c:forEach>
	            		</select>	
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
					</div>
	
	          		<div class="field full">
	            		<label for="fDesc">설명</label>
	            		<textarea id="fDesc" class="form-control" maxlength="300"></textarea>
	          		</div>
	        	</div>
	
		        <div class="form-actions">
       				<button type="button" class="btn-insert" 		id="btnNew"		value="N" 		style="display: none;">신규</button>
       				<button type="button" class="btn-insert"   	id="btnReg"   	value="I" >저장</button>
        			<button type="button" class="btn-update"		id="btnUpd" 		value="U" 		style="display: none;">수정</button>
         			<button type="button" class="btn-delete"  	id="btnDel" 		value="D" 		style="display: none;">삭제</button>
		        </div>
	      	</div>
	    </div>
	</section>
	
</div>
<!-- Draw view [E] -->