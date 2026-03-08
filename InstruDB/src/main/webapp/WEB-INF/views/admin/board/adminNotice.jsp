<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/board/adminNotice.js"></script>

<!-- Model 파라미터 [S]-->
<input type="hidden"  id="searchGbParam" name="searchGbParam" value="${pager.searchGb}">
<input type="hidden"  id="pageNumParam" name="pageNumParam" value="${pager.pageNum}">
<input type="hidden"  id="adminId" name="adminId" value="${adminId}">
<input type="hidden"  id="nowDate" name="nowDate" value="${nowDate}">
<!-- Model 파라미터 [E] -->

<div class="split-layout grid-split">
	<aside class="split-left list-panel">
		<!--  조회 조건 [S] -->
		<form action="/admin/user.do" id="adminUserSearchForm">
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
				                		<th>작성자</th>
				                		<th>작성날짜</th>
				                		<th>내용</th>
				                		<th>팝업여부</th>
				                		<th>기한여부</th>
				              		</tr>
			            		</thead>
				            	<tbody>
				              		<c:forEach var="admin" items="${adminList}" varStatus="cnt">
				                		<tr class="adminNoticeInfoTr" data-rowkey="${cnt.index}" data-no="${admin.adminNo}" data-id="${admin.adminId}">
				                  			<td><span>${admin.adminId}</span></td>
				                  			<td><span>${admin.regDt}</span></td>
				                  			<td><span>${admin.fDesc}</span></td>
				                  			<td><span>${admin.noticePopYn}</span></td>
				                  			<td><span>${admin.noticeLimitYn}</span></td>
				                		</tr>
				              		</c:forEach>
				            	</tbody>
				          	</table>
				        </div>
				
				        <div class="pagination-wrap">
			          		<div class="pagination" id="paging">
				            	<button class="p" data-list-pn="${pager.startNum-1}" type="button">&laquo;</button>
				            	<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
				              		<button class="p" data-list-pn="${i}">${i}</button>
				            	</c:forEach>
				            	<button class="p" data-list-pn="${pager.lastNum+1}" type="button">&raquo;</button>
				          	</div>
			        	</div>
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
	        	<h2>공지사항</h2>
		        <div class="breadcrumb">
		        	<a href="#">알림판</a>&nbsp;&gt;&nbsp;<span>공지사항</span>
		        </div>
	      	</div>
	     	<div class="form-card">
	       		<div class="form-grid">
		          		
	          		<div class="field zip-field zip-no-msg">
	            		<label class="required" for="adminPostCd">번호</label>
	            		<input id="noticeId" name="noticeId" class="form-control" type="text" readonly/>
	            		<input type="button" class="btn btn-zip" id="getNoticeId" value="생성" style="color : white;">
	          		</div>
	          		
     			    <div class="field">
	            		<label class="required" for="adminDAddress">작성자</label>
	            		<input id="regId" name="regId" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="adminPh">작성날짜</label>
	            		<input id="regDt" name="regDt" class="form-control" type="text" readonly/>
	          		</div>

	          		<div class="field full">
	            		<label for="fDesc">내용</label>
	            		<textarea id="fDesc" class="form-control" maxlength="300"></textarea>
	          		</div>
	          		
	          		<!-- ✅ 옵션 토글(가로 배치): 상단고정 + 기한설정 -->
					<div class="field full">
					  <label class="required">옵션</label>
					
					  <div class="toggle-pair notice-options" role="group" aria-label="공지 옵션">
						<label class="ez-toggle">
						  <input type="checkbox" id="noticeFixYn" name="noticeFixYn" value="Y" role="switch" />
						  <span class="ez-sw" aria-hidden="true">
						    <span class="track"></span>
						    <span class="thumb"></span>
						  </span>
						  <span class="txt">상단고정</span>
						</label>
						
						<label class="ez-toggle">
						  <input type="checkbox" id="noticePopYn" name="noticePopYn" value="Y" role="switch" />
						  <span class="ez-sw" aria-hidden="true">
						    <span class="track"></span>
						    <span class="thumb"></span>
						  </span>
						  <span class="txt">팝업설정</span>
						</label>
						
						<label class="ez-toggle">
						  <input type="checkbox" id="noticeLimitYn" name="noticeLimitYn" value="Y" role="switch" />
						  <span class="ez-sw" aria-hidden="true">
						    <span class="track"></span>
						    <span class="thumb"></span>
						  </span>
						  <span class="txt">기한설정</span>
						</label>
					  </div>
					
					  <!-- <small class="hint" style="display:block;">기한설정을 켜면 시작/종료 날짜를 입력합니다.</small> -->
					</div>
	          		
					<div class="field">
					  <label id="lblNotcieStrDt" for="notcieStrDt">시작날짜</label>
					  <input id="notcieStrDt" name="notcieStrDt" class="form-control" type="datetime-local" />
					</div>
					
					<div class="field">
					  <label id="lblNotcieEndDt" for="notcieEndDt">종료날짜</label>
					  <input id="notcieEndDt" name="notcieEndDt" class="form-control" type="datetime-local" />
					</div>

					<!-- ✅ 첨부파일(업로드/다운로드 분리) -->
					<div class="field full attach-field">
				  		<label>첨부파일</label>
					
					  	<!-- 저장된 파일 삭제목록(선택) -->
					  	<input type="hidden" id="delFileNos" name="delFileNos" value="" />
					
					  	<div class="attach-card">
					    	<!-- 업로드 -->
					    	<div class="attach-col">
					      		<div class="attach-head">업로드</div>
					      		<div class="attach-body">
						        	<div class="filebox">
						          		<input type="file" id="adminFiles" name="adminFiles" multiple />
						          		<button type="button" class="btn btn-file" id="btnPickFiles">첨부</button>
						          		<div class="filename" id="adminFilesSummary">선택된 파일 없음</div>
						        	</div>
						
						       	 	<ul class="attach-list" id="newFileList"></ul>
						
						        	<small class="hint" style="display:block;">
						          		여러 파일 선택 가능 (저장/수정 버튼 누를 때 함께 업로드)
						        	</small>
					      		</div>
					    	</div>
					
					    	<!-- 다운로드 -->
					    	<div class="attach-col">
					      		<div class="attach-head">다운로드</div>
					      		<div class="attach-body">
					        		<ul class="attach-list" id="savedFileList"></ul>
					        		<small class="hint" id="savedFileHint" style="display:block;">등록된 파일이 없습니다.</small>
					      		</div>
					    	</div>
					  	</div>
					</div>

	        	</div>
	
		        <div class="form-actions">
       				<button type="button" class="btn-insert" 	id="btnNew"	value="N" 	style="display: none;">신규</button>
       				<button type="button" class="btn-insert"   	id="btnReg"   	value="I" >저장</button>
        			<button type="button" class="btn-update"	id="btnUpd" 	value="U" 	style="display: none;">수정</button>
         			<button type="button" class="btn-delete"  	id="btnDel" 		value="D" 	style="display: none;">삭제</button>
		        </div>
	      	</div>
	    </div>
	</section>
	
</div>
<!-- Draw view [E] -->