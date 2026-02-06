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
			        <a href="#" class="commTreeF" data-id="${comm.commId}">${comm.commNm}</a>
			        <ul>
			          	<c:forEach var="comm2" items="${commList2}">
				          	<c:if test="${comm.commId eq comm2.commPId && comm2.commLvl eq '1'}">
				          		<li><span class="dot"></span><a href="#" class="commTreeS" data-id="${comm2.commId}" data-pid="${comm2.commPId}" data-lv="${comm2.commLvl}">${comm2.commNm}</a></li>
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
        <p class="form-desc">좌측 트리에서 코드를 선택하면 기본 정보가 로딩됩니다.</p>

        <div class="form-grid">
        
         	<div class="field">
            	<label for="menu_nm" class="required">코드명</label>
            	<input type="text" id="commNm" name="commNm" class="form-control" />
            	<input type="hidden" id="commId" name="commId" class="form-contrㅜㅜol" />
          	</div>
          	
      	    <div class="field">
            	<label for="menu_nm" class="">부모코드명</label>
            	<input type="text" id="commPNm" name="commPNm" class="form-control" readonly/>
            	<input type="hidden" id="commPId" name="commPId" class="form-control" />
          	</div>
          	
          	<div class="field">
            	<label for="menu_nm" class="required">공통코드</label>
            	<input type="text" id="commCd" name="commCd" class="form-control"/>
            	<div class="error" style="display: none;">공통코드가 중복 됩니다. 다시 입력해주세요.</div>
            	<small class="hint" style="display: none;">사용가능한 공통코드 입니다.</small>
          	</div>
          	
          	<div class="field">
           		<label for="menu_nm" class="required">코드그룹</label>
            	<input type="text" id="commGroupCd" name="commGroupCd" class="form-control" />
          	</div>
          		
   			<div class="field">
            	<label for="commLvl" class="required">레벨</label>
            	<select id="commLvl" name="commLvl" class="form-select" style="width:100%;" disabled>
            		<option value="0">1레벨</option>
            		<option value="1">2레벨</option>
            	</select>
         	</div>
          
          	<div class="field">
            	<label for="commSortNo" class="required">정렬순서</label>
            	<input type="text" id="commSortNo" name="commSortNo" class="form-control" onkeyup="checkNum(this);" placeholder="숫자만 입력해주세요." />
          	</div>

          	<div class="field full">
            	<label for="menu_desc">설명</label>
            	<textarea id="commCn" name="commCn" class="form-control" placeholder="간단한 설명을 입력하세요."></textarea>
          	</div>

          	<div class="field">
	            <label class="required">사용 여부</label>
	            <div>
              		<label style="margin-right:10px;">
	                	<input type="radio" name="commUseYn" value="Y" checked /> 사용
             	 	</label>
	              	<label>
	               		<input type="radio" name="commUseYn" value="N" /> 미사용
              		</label>
	            </div>
          	</div>
        </div>

        <div class="form-actions">
  			<button type="button" class="btn-refresh" 	id="btnRef"		value="R"   style="display: none;">초기화</button>
       		<button type="button" class="btn-insert" 	id="btnNew"	value="N" 	style="display: none;">추가</button>
       		<button type="button" class="btn-insert"   	id="btnReg"   	value="I" >저장</button>
        	<button type="button" class="btn-update"	id="btnUpd" 	value="U" 	style="display: none;">수정</button>
         	<button type="button" class="btn-delete"  	id="btnDel" 		value="D" 	style="display: none;">삭제</button>
      	</div>
      </form>
    </div>
  </section>
</div>
<!-- Draw view [E] -->