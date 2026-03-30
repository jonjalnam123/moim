<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/board/adminNotice.js"></script>

<!-- Model 파라미터 [S]-->
<input type="hidden"  id="searchGbParam" name="searchGbParam" value="${pager.searchGb}">
<input type="hidden"  id="pageNumParam" name="pageNumParam" value="${pager.pageNum}">
<input type="hidden"  id="ss_admin_id" name="ss_admin_id" value="${SS_ADMIN_ID}">
<input type="hidden"  id="nowDate" name="nowDate" value="${nowDate}">
<!-- Model 파라미터 [E] -->

<div class="split-layout grid-split">
	<aside class="split-left list-panel">
		<!--  조회 조건 [S] -->
		<form action="/admin/notice.do" id="adminNoticeSearchForm">
			<input type="hidden"  id="pageNum" name="pageNum" value="1">
			<div class="list-header">
	      		<div class="list-title"></div>
	      		<div class="list-search">
	        		<select id="searchGb" name=searchGb class="form-select">
			          	<option class="s" value="noticeTitle">제목</option>
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
		      		<c:when test="${empty adminNoticeList}">
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
				                		<th>제목</th>
				                		<th>내용</th>
				                		<th>작성일시</th>
				              		</tr>
			            		</thead>
				            	<tbody>
				              		<c:forEach var="adminNotice" items="${adminNoticeList}" varStatus="cnt">
				                		<tr class="adminNoticeInfoTr" data-rowkey="${cnt.index}" data-id="${adminNotice.noticeId}" data-fix-yn="${adminNotice.noticeFixYn}">
				                			<td><span>${adminNotice.regId}</span></td>
				                  			<td><span>${adminNotice.noticeTitle}</span></td>
				                  			<td><span>${adminNotice.noticeCn}</span></td>
				                  			<td><span>${adminNotice.regDt}</span></td>
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
	        	<h2>${SS_MENU_NM}</h2> 
		        <div class="breadcrumb">
		        	<a href="#">${SS_MENU_PNM}</a>&nbsp;&gt;&nbsp;<span>${SS_MENU_NM}</span>
				    <button type="button"  id="btnFavorite" class="breadcrumb-fav-icon" aria-pressed="${SS_FAV_MENU_YN eq 'Y' ? 'true' : 'false'}" title="즐겨찾기 해제" onclick="selectFavoriteMenu(this)"> 
				        <svg viewBox="0 0 24 24" class="fav-star" aria-hidden="true">
				            <path d="M12 3.8l2.45 4.96 5.47.8-3.96 3.86.94 5.45L12 16.3 7.1 18.87l.94-5.45-3.96-3.86 5.47-.8L12 3.8z"/>
				        </svg>
				    </button>
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
	            		<label class="required" for="regId">작성자</label>
	            		<input id="regId" name="regId" class="form-control" type="text" readonly/>
	          		</div>

	          		<div class="field">
	            		<label class="required" for="regDt">작성일시</label>
	            		<input id="regDt" name="regDt" class="form-control" type="text" readonly/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="noticeTitle">제목</label>
	            		<input id="noticeTitle" name="noticeTitle" class="form-control" type="text"/>
	          		</div>
	          		
	          		<div class="field">
	            		<label class="required" for="noticeEffectGb">중요도</label>
	            		<select id="noticeEffectGb" name="noticeEffectGb" class="form-select" style="width:100%;">
	            			<option value="">선택</option>
	            			<c:forEach var="adminNoticeEffect" items="${adminNoticeEffectList}">
	            				<option value="${adminNoticeEffect.commCd}">${adminNoticeEffect.commNm}</option>
	            			</c:forEach>
	            		</select>
	          		</div>

	          		<div class="field full">
	            		<label for="noticeCn">내용</label>
	            		<textarea id="noticeCn" name="noticeCn" class="form-control" maxlength="300"></textarea>
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
					</div>
	          		
					<div class="field">
					  <label id="lblNoticeStrDt" for="noticeStrDt">시작날짜</label>
					  <input id="noticeStrDt" name="noticeStrDt" class="form-control" type="datetime-local" />
					</div>
					
					<div class="field">
					  <label id="lblNoticeEndDt" for="noticeEndDt">종료날짜</label>
					  <input id="noticeEndDt" name="noticeEndDt" class="form-control" type="datetime-local" />
					</div>

					<!-- ✅ 첨부파일(업로드/다운로드 분리) -->
					<div class="field full attach-field">
				  		<label>첨부파일</label>
					
					  	<div class="attach-card">
					    	<!-- 업로드 -->
					    	<div class="attach-col">
					      		<div class="attach-head">업로드</div>
					      		<div class="attach-body">
						        	<div class="filebox">
						        		<input type="file" id="adminFiles" name="adminFiles" multiple accept="image/*" />
						          		<button type="button" class="btn btn-file" id="btnPickFiles">첨부</button>
						          		<div class="filename" id="adminFilesSummary">선택된 파일 없음</div>
						        	</div>
						
						       	 	<ul class="attach-list" id="newFileList"></ul>
						
						        	<small class="hint" style="display:block;">
						          		이미지 파일만 첨부 가능 ( jpg, jpeg, png, gif, bmp, webp )
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