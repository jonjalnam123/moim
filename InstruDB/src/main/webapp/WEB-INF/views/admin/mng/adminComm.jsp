<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminComm.js"></script>

<!-- Draw view [S] -->
<div class="split-layout">
	<!-- 좌측: 트리 -->
	<aside class="split-left">
	   	<div class="tree-header">코드</div>
	    <div class="tree-scroll">
	    	<c:forEach var="comm" items="${commList}">
		   		<ul class="tree">
			      	<li class="open">
			        <button class="tw" aria-label="toggle"></button>
			        <a href="#" class="menuTreeF" data-id="${comm.commId}">${comm.commNm}</a>
			        <ul>
			          	<c:forEach var="comm2" items="${commList2}">
				          	<c:if test="${comm.commId eq comm2.commPId && comm2.commLvl eq '1'}">
				          		<li><span class="dot"></span><a href="#" class="menuTreeS" data-id="${comm2.commId}">${comm2.commNm}</a></li>
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
        <h2>코드관리</h2>
        <div class="breadcrumb">
        <a href="#">관리자</a>&nbsp;&gt;&nbsp;<span>코드관리</span>
        </div>
      </div>

      <form id="menuForm" class="form-card">
        <div class="form-title">코드 정보</div>
        <p class="form-desc">좌측 트리에서 메뉴를 선택하면 기본 정보가 로딩됩니다.</p>

        <div class="form-grid">
        
         	<div class="field">
            	<label for="menu_nm" class="required">메뉴명</label>
            	<input type="text" id="commNm" name="commNm" class="form-control" />
            	<input type="hidden" id="commId" name="commId" class="form-control" />
          	</div>
          	
      	    <div class="field">
            	<label for="menu_nm" class="required">부모 메뉴명</label>
            	<input type="text" id="commPNm" name="commPNm" class="form-control" />
            	<input type="hidden" id="commPId" name="commPId" class="form-control" />
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