<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminMenu.js"></script>

<!-- Draw view [S] -->
<div class="split-layout">

	<!-- 좌측: 트리 [S] -->
	<aside class="split-left">
   		<div class="tree-header">메뉴</div>
	    <div class="tree-scroll">
	    	<c:forEach var="menu" items="${menuList}">
      			<ul class="tree">
			        <li class="open">
		          		<button class="tw" aria-label="toggle"></button>
			          	<a href="#" class="" id="menuDept" data-id="${menu.menuId}">${menu.menuNm}</a>
			          	<ul>
				            <c:forEach var="menu2" items="${menuList2}">
				            	<c:if test="${menu.menuId eq menu2.menuPId && menu2.menuLvl eq '1'}">
				            		<li><span class="dot"></span><a href="#" id="menu2Dept" data-id="${menu2.menuId}">${menu2.menuNm}</a></li>
				            	</c:if>
				            </c:forEach>
			          	</ul>
			        </li>
	      		</ul>
	      	</c:forEach>
	    </div>
  	</aside>
	<!-- 좌측: 트리 [E] -->
	
  	<!-- 우측: 폼 [S] -->
	<section class="split-right">
   		<div class="content-scroll">
      		<div class="page-header">
		        <h2>메뉴관리</h2>
		        <div class="breadcrumb">
		        	<a href="#">관리자</a>&nbsp;&gt;&nbsp;<span>메뉴관리</span>
		        </div>
	      	</div>
	
      		<form id="menuForm" class="form-card">
        		<div class="form-title">메뉴 정보</div>
        		<p class="form-desc">좌측 트리에서 메뉴를 선택하면 기본 정보가 로딩됩니다.</p>
	
				<div class="form-grid">
	          	<div class="field">
		            <label for="menu_nm" class="required">메뉴명</label>
		            <input type="text" id="menuNm" name="menuNm" class="form-control" required />
		             <input type="hidden" id="menuId" name="menuId"/>
		            <small class="hint">영문/숫자, 공백 없이 입력</small>
		          </div>
		          
		        <div class="field">
		          <label for="menuPNm" class="required">부모 메뉴명</label>
		          <input type="text" id="menuPNm" name="menuPNm" class="form-control" readonly />
		          <input type="hidden" id="menuPId" name="menuPId"/>
		          <small class="hint">영문/숫자, 공백 없이 입력</small>
		        </div>
		          
				<div class="field">
				  <label for="menuUrl" class="required">메뉴 URL</label>
				  <input type="text" id="menuUrl" name="menuUrl" class="form-control" />
				  <small class="hint">영문/숫자, 공백 없이 입력</small>
				</div>

	          	<div class="field">
		            <label for="menuDeptCd" class="required">부서</label>
		            <select id="menuDeptCd" name="menuDeptCd" class="form-select" style="width:100%;" multiple="multiple" required>
		            	<option value=""></option> 
		            	<c:forEach var="unitList" items="${adminUnitList}">
		            		<c:if test="${unitList.adminUnitLvl eq 0}">
		            			<option data-id="${unitList.adminUnitId}" value="${unitList.adminUnitCd}">${unitList.adminUnitNm}</option>
		            		</c:if>
		            	</c:forEach>
		            </select>
	          	</div>
	          	
	          	<div class="field">
				  <label for="menuIcon" class="required">아이콘명</label>
				  <input type="text" id="menuIcon" name="menuIcon" class="form-control" />
				</div>
		          
	          	<div class="field">
	            	<label for="sort_no" class="required">레벨</label>
		            <input type="number" id="menuLvl" name="menuLvl" class="form-control" min="0" required />
	          	</div>
		          
	          	<div class="field">
		            <label for="sort_no" class="required">정렬순서</label>
		            <input type="number" id="menuSort" name="menuSort" class="form-control" min="0" required />
	          	</div>
		
	          	<div class="field full">
		            <label for="menu_desc">설명</label>
		            <textarea id="menuCn" name="menuCn" class="form-control" maxlength="500" placeholder="간단한 설명을 입력하세요."></textarea>
	          	</div>
		
	          	<div class="field">
		            <label class="required">사용 여부</label>
		            <div>
	              		<label style="margin-right:10px;">
		                	<input type="radio" name="menuUseYn" value="Y" checked /> 사용
		              	</label>
		              	<label>
		                	<input type="radio" name="menuUseYn" value="N" /> 미사용
		              	</label>
		            </div>
	          	</div>
		
		<!--           <div class="field">
		            <label>노출 영역</label>
		            <div>
		              <label style="margin-right:10px;">
		                <input type="checkbox" name="expose_pc" value="Y" checked /> PC
		              </label>
		              <label>
		                <input type="checkbox" name="expose_m" value="Y" /> Mobile
		              </label>
		            </div>
		          </div>-->
	        	</div> 
		
		        <div class="form-actions">
		          <!-- <button type="button" class="btn-update" id="btnNew">신규</button> -->
		          <button type="button" class="btn-insert" id="btnReg">저장</button>
		          <button type="button" class="btn-delete" id="btnDelete">삭제</button>
		        </div>
      		</form>
    	</div>
	</section>
	<!-- 우측: 폼 [E] -->
	
</div>
<!-- Draw view [E] -->