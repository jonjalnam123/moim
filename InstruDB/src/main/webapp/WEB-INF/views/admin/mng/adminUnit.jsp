<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/resources/static/js/admin/mng/adminUnit.js"></script>

<!-- Draw view [S] -->
<div class="split-layout">
<!-- 좌측: 트리 -->
<aside class="split-left">
  <div class="tree-header">메뉴</div>
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

          <a href="#" data-id="${unit.adminUnitId}">${unit.adminUnitNm}</a>

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

                    <a href="#" data-id="${unit.adminUnitId}-${unit2.adminUnitId}">
                      ${unit2.adminUnitNm}
                    </a>

                    <%-- 레벨3가 있을 때만 출력 --%>
                    <c:if test="${lvl3Count gt 0}">
                      <ul>
                        <!-- 3레벨 -->
                        <c:forEach var="unit3" items="${adminUnitList3}">
                          <c:if test="${unit2.adminUnitId eq unit3.adminUnitPId && unit3.adminUnitLvl eq '2'}">
                            <li>
                              <span class="dot"></span>
                              <a href="#" data-id="${unit.adminUnitId}-${unit2.adminUnitId}-${unit3.adminUnitId}">
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
            <label for="menu_nm" class="required">메뉴명</label>
            <input type="text" id="menu_nm" name="menu_nm" class="form-control" required />
            <small class="hint">영문/숫자, 공백 없이 입력</small>
          </div>
          
          <div class="field">
            <label for="menu_nm" class="required">부모메뉴명</label>
            <input type="text" id="menu_p_nm" name="menu_p_nm" class="form-control" readonly />
            <button type="button" id="delMpiBtn" class="btn-delete">삭제</button>
            <input type="hidden" id="menu_p_id" name="menu_p_id" class="form-control" />
            <small class="hint">영문/숫자, 공백 없이 입력</small>
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