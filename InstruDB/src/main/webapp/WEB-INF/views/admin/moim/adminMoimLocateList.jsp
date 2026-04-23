<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/moim/adminMoimLocateList.js"></script>

<!-- Model 파라미터 -->
<input type="hidden"  id="searchGbParam" name="searchGbParam" value="${pager.searchGb}">
<input type="hidden"  id="pageNumParam" name="pageNumParam" value="${pager.pageNum}">

<div class="split-layout grid-split">
	<aside class="split-left list-panel">
		<!--  조회 조건 [S] -->
		<form action="/admin/moim/moimLocateList.do" id="adminMoimLocateListForm">
			<input type="hidden"  id="pageNum" name="pageNum" value="1">
			<div class="list-header">
	      		<div class="list-title"></div>
	      		<div class="list-search">
	        		<select id="searchGb" name=searchGb class="form-select">
			          	<option class="s" value="locateNm">장소명</option>
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
		      		<c:when test="${empty adminMoimLocateList}">
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
					          			<th>장소명</th>
								        <th>우편번호</th>
								        <th>주소</th>
								        <th>상세주소</th>
								        <th>사용여부</th>
									</tr>
			      				</thead>
			            		<tbody>
									<c:forEach var="adminMoimLocate" items="${adminMoimLocateList}" varStatus="cnt">
						        		<tr class="adminMoimLocateTr" data-rowkey="${cnt.index}" data-id="${adminMoimLocate.locateId}">
								        	<td>${adminMoimLocate.locateNm}</td>
								        	<td>${adminMoimLocate.locatePostCd}</td>
								        	<td>${adminMoimLocate.locateAddress}</td>
								        	<td>${adminMoimLocate.locateDAddress}</td>
								        	<td>${adminMoimLocate.locateUseYn}</td>
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
	            		<label class="required" for="locateNm">장소명</label>
	            		<input id="locateNm" name="locateNm" class="form-control" type="text" />
	          		</div>
	          		
	          		<div class="field zip-field zip-no-msg">
	            		<label class="required" for="locatePostCd">우편번호</label>
	            		<input id="locatePostCd" name="locatePostCd" class="form-control" type="text" placeholder="우편번호" readonly/>
	            		<input type="button" class="btn btn-zip" id="getPostCode" value="찾기" style="color : white;">
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="locateAddress">주소</label>
	            		<input id="locateAddress" name="locateAddress" class="form-control" type="text" placeholder="주소" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="locateDAddress">상세주소</label>
	            		<input id="locateDAddress" name="locateDAddress" class="form-control" type="text" />
	          		</div>
	          		
	          		<div class="field">
			            <label class="required">사용 여부</label>
			            <div>
		              		<label style="margin-right:10px;">
			                	<input type="radio" name="locateUseYn" value="Y" checked /> 사용
			              	</label>
			              	<label>
			                	<input type="radio" name="locateUseYn" value="N" /> 미사용
			              	</label>
			            </div>
		          	</div>
	
	          		<div class="field full">
	            		<label for="fDesc">설명</label>
	            		<textarea id="fDesc" class="form-control" maxlength="300"></textarea>
	          		</div>
	        	</div>
	
		        <div class="form-actions">
       				<button type="button" class="btn-insert" 		id="btnNew"		value="N" 		style="display: none;">신규</button>
       				<button type="button" class="btn-insert"   		id="btnReg"   		value="I" >저장</button>
        			<button type="button" class="btn-update"		id="btnUpd" 		value="U" 		style="display: none;">수정</button>
         			<button type="button" class="btn-delete"  		id="btnDel" 			value="D" 		style="display: none;">삭제</button>
		        </div>
	      	</div>
	    </div>
	</section>
	
</div>
<!-- Draw view [E] -->