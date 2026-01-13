<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminMenu.js"></script>

<!-- Draw view [S] -->
<div class="split-layout">
	<!-- 좌측: 트리 -->
	<aside class="split-left">
    	<div class="tree-header">메뉴</div>
	    <div class="tree-scroll">
		    <c:forEach var="menu" items="${menuList}">
	      		<ul class="tree">
		        <li class="open">
		          <button class="tw" aria-label="toggle"></button>
		          <a href="#" class="" data-id="A">${menu.menuNm}</a>
		          <ul>
		            <c:forEach var="menu2" items="${menuList2}">
		            	<c:if test="${menu.menuId eq menu2.menuPId && menu2.menuLvl eq '1'}">
		            		<li><span class="dot"></span><a href="#" data-id="A-1">${menu2.menuNm}</a></li>
		            	</c:if>
		            </c:forEach>
		          </ul>
		        </li>
		      </ul>
	      	</c:forEach>
	    </div>
  	</aside>

  <!-- 우측: 폼 -->
  <section class="split-right">
    <div class="content-scroll">
      <div class="page-header">
        <h2>샘플</h2>
        <div class="breadcrumb">
        <a href="#">샘플</a>&nbsp;&gt;&nbsp;<span>리스트폼화면</span>
        </div>
      </div>

      <form id="menuForm" class="form-card" action="/admin/menu/save.do" method="post">
        <div class="form-title">메뉴 정보</div>
        <p class="form-desc">좌측 트리에서 메뉴를 선택하면 기본 정보가 로딩됩니다.</p>

        <div class="form-grid">
          <div class="field">
            <label for="menu_id" class="required">메뉴 ID</label>
            <input type="text" id="menu_id" name="menu_id" class="form-control" required />
            <small class="hint">영문/숫자, 공백 없이 입력</small>
          </div>

          <div class="field">
            <label for="menu_nm" class="required">메뉴명</label>
            <input type="text" id="menu_nm" name="menu_nm" class="form-control" required />
          </div>

         <div class="field">
            <label for="menu_gb" class="required">구분</label>
            <select id="menu_gb" name="menu_gb" class="form-select" style="width:100%;" data-placeholder="카테고리를 선택하세요" required>
              <option value=""></option>
              <option value="NOTICE">공지</option>
              <option value="EVENT">이벤트</option>
              <option value="GUIDE">안내</option>
              <option value="CHECK">점검</option>
              <option value="UPDATE">업데이트</option>
            </select>
          </div>
          
          <div class="field">
            <label for="sort_no" class="required">정렬순서</label>
            <input type="number" id="sort_no" name="sort_no" class="form-control" min="0" required />
          </div>

          <div class="field full">
            <label for="menu_desc">설명</label>
            <textarea id="menu_desc" name="menu_desc" class="form-control" placeholder="간단한 설명을 입력하세요."></textarea>
          </div>

          <div class="field">
            <label class="required">사용 여부</label>
            <div>
              <label style="margin-right:10px;">
                <input type="radio" name="use_yn" value="Y" checked /> 사용
              </label>
              <label>
                <input type="radio" name="use_yn" value="N" /> 미사용
              </label>
            </div>
          </div>

          <div class="field">
            <label>노출 영역</label>
            <div>
              <label style="margin-right:10px;">
                <input type="checkbox" name="expose_pc" value="Y" checked /> PC
              </label>
              <label>
                <input type="checkbox" name="expose_m" value="Y" /> Mobile
              </label>
            </div>
          </div>
        </div>

        <div class="form-actions">
          <button type="button" class="btn-delete" id="btnDelete">삭제</button>
          <button type="button" class="btn-update" id="btnNew">신규</button>
          <button type="submit" class="btn-insert">저장</button>
          <button type="button" class="btn-cancel" onclick="history.back();">취소</button>
        </div>
      </form>
    </div>
  </section>
</div>
<!-- Draw view [E] -->