<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminUnit.js"></script>

<!-- Draw view [S] -->
<div class="split-layout">
<!-- 좌측: 트리 -->
<aside class="split-left">
  <div class="tree-header">부서</div>
  <div class="tree-scroll">

    <ul class="tree">
      <!-- 1레벨 -->
      <c:forEach var="unit" items="${adminUnitList}">

        <%-- 레벨1(unit)의 레벨2 자식 개수 카운트 --%>
        <c:set var="lvl2Count" value="0" />
        <c:forEach var="unit2" items="${adminUnitList2}">
          <c:if test="${unit.adminUnitId eq unit2.adminUnitPId && unit2.adminUnitLvl eq '1'}">
            <c:set var="lvl2Count" value="${lvl2Count + 1}" />
          </c:if>
        </c:forEach>

        <li class="${lvl2Count gt 0 ? 'open' : ''}">
          <%-- 레벨1: 자식(레벨2) 있으면 버튼, 없으면 dot --%>
          <c:choose>
            <c:when test="${lvl2Count gt 0}">
              <button class="tw" type="button" aria-label="toggle"></button>
            </c:when>
            <c:otherwise>
              <span class="dot"></span>
            </c:otherwise>
          </c:choose>

          <a href="#" class="unitTreeF" data-id="${unit.adminUnitId}">${unit.adminUnitNm}</a>

          <%-- 레벨2가 있을 때만 출력 --%>
          <c:if test="${lvl2Count gt 0}">
            <ul>
              <!-- 2레벨 -->
              <c:forEach var="unit2" items="${adminUnitList2}">
                <c:if test="${unit.adminUnitId eq unit2.adminUnitPId && unit2.adminUnitLvl eq '1'}">

                  <%-- 레벨2(unit2)의 레벨3 자식 개수 카운트 --%>
                  <c:set var="lvl3Count" value="0" />
                  <c:forEach var="unit3" items="${adminUnitList3}">
                    <c:if test="${unit2.adminUnitId eq unit3.adminUnitPId && unit3.adminUnitLvl eq '2'}">
                      <c:set var="lvl3Count" value="${lvl3Count + 1}" />
                    </c:if>
                  </c:forEach>

                  <li class="${lvl3Count gt 0 ? 'open' : ''}">
                    <%-- 레벨2: 자식(레벨3) 있으면 버튼, 없으면 dot --%>
                    <c:choose>
                      <c:when test="${lvl3Count gt 0}">
                        <button class="tw" type="button" aria-label="toggle"></button>
                      </c:when>
                      <c:otherwise>
                        <span class="dot"></span>
                      </c:otherwise>
                    </c:choose>

                    <a href="#" class="unitTreeS" data-id="${unit2.adminUnitId}" data-pid="${unit2.adminUnitPId}">${unit2.adminUnitNm}</a>

                    <%-- 레벨3가 있을 때만 출력 --%>
                    <c:if test="${lvl3Count gt 0}">
                      <ul>
                        <!-- 3레벨 -->
                        <c:forEach var="unit3" items="${adminUnitList3}">
                          <c:if test="${unit2.adminUnitId eq unit3.adminUnitPId && unit3.adminUnitLvl eq '2'}">
                            <li>
                              <span class="dot"></span>
                              <a href="#" class="unitTreeT" data-id="${unit3.adminUnitId}" data-pid="${unit3.adminUnitPId}" data-lv="${unit3.adminUnitLvl}">
                                ${unit3.adminUnitNm}
                              </a>
                            </li>
                          </c:if>
                        </c:forEach>
                      </ul>
                    </c:if>

                  </li>
                </c:if>
              </c:forEach>
            </ul>
          </c:if>

        </li>
      </c:forEach>
    </ul>

  </div>
</aside>

  <!-- 우측: 폼 -->
  <section class="split-right">
    <div class="content-scroll">
      <div class="page-header">
        <h2>부서관리</h2>
        <div class="breadcrumb">
        <a href="#">관리자</a>&nbsp;&gt;&nbsp;<span>부서관리</span>
        </div>
      </div>

      <form id="menuForm" class="form-card">
        <div class="form-title">부서 정보</div>
        <p class="form-desc">좌측 트리에서 부서를 선택하면 기본 정보가 로딩됩니다.</p>

        <div class="form-grid">
        
         	<div class="field">
            	<label for="menu_nm" class="required">부서명</label>
            	<input type="text" id="adminUnitNm" name="adminUnitNm" class="form-control" />
            	<input type="hidden" id="adminUnitId" name="adminUnitId" class="form-control" />
          	</div>
          	
      	    <div class="field">
            	<label for="adminUnitPNm" class="">부모부서명</label>
            	<input type="text" id="adminUnitPNm" name="adminUnitPNm" class="form-control"  readonly/>
            	<input type="hidden" id="adminUnitPId" name="adminUnitPId" class="form-control" />
          	</div>
          	
          	<div class="field">
            	<label for="adminUnitCd" class="required">부서코드</label>
            	<input type="text" id="adminUnitCd" name="adminUnitCd" class="form-control" />
            	<input type="hidden" id="adminUnitCdChk" name="adminUnitCdChk" class="form-control"/>
            	<input type="hidden" id="adminUnitCdOrg" name="adminUnitCdOrg" class="form-control"/>
            	<div class="error" style="display: none;">부서코드가 중복 됩니다. 다시 입력해주세요.</div>
            	<small class="hint" style="display: none;">사용가능한 부서코드 입니다.</small>
          	</div>
          	
   			<div class="field">
            	<label for="adminUnitLvl" class="required">레벨</label>
            	<select id="adminUnitLvl" name="adminUnitLvl" class="form-select" style="width:100%;" disabled>
            		<option value="0">1레벨</option>
            		<option value="1">2레벨</option>
            		<option value="2">3레벨</option>
            	</select>
         	</div>
          
          	<div class="field">
            	<label for="sort_no" class="required">정렬순서</label>
            	<input type="text" id="adminUnitSortNo" name="adminUnitSortNo" class="form-control" onkeyup="checkNum(this);" placeholder="숫자만 입력해주세요."S/>
          	</div>
          	
          	<div class="field full">
            	<label for="menu_desc">설명</label>
            	<textarea id="adminUnitCn" name="adminUnitCn" class="form-control" placeholder="간단한 설명을 입력하세요."></textarea>
          	</div>

          	<div class="field">
	            <label class="required">사용 여부</label>
	            <div>
              		<label style="margin-right:10px;">
	                	<input type="radio" name="adminUnitUseYn" value="Y" checked /> 사용
             	 	</label>
	              	<label>
	               		<input type="radio" name="adminUnitUseYn" value="N" /> 미사용
              		</label>
	            </div>
          	</div>
        </div>

        <div class="form-actions">
  			<button type="button" class="btn-refresh" 	id="btnRef"		value="R"   	style="display: none;">초기화</button>
       		<button type="button" class="btn-insert" 		id="btnNew"		value="N" 		style="display: none;">추가</button>
       		<button type="button" class="btn-insert"   	id="btnReg"   	value="I" >저장</button>
        	<button type="button" class="btn-update"		id="btnUpd" 		value="U" 		style="display: none;">수정</button>
         	<button type="button" class="btn-delete"  	id="btnDel" 		value="D" 		style="display: none;">삭제</button>
      	</div>
      </form>
    </div>
  </section>
</div>
<!-- Draw view [E] -->