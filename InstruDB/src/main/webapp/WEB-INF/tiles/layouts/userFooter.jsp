<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="idb-container">
  <div class="idb-footer-inner">

    <div class="idb-footer-top">
      <div class="idb-footer-brand">
        <div class="idb-footer-brand-name">Instru DB</div>
        <div class="idb-footer-brand-text">
          악기를 다루듯 데이터베이스를 컨트롤하는 팀용 DB 콘솔.
          모니터링, 감사 로그, 연결 관리까지 한 번에 제공합니다.
        </div>
      </div>

      <div class="idb-footer-links">
        <div>
          <div class="idb-footer-column-title">제품</div>
          <ul class="idb-footer-list">
            <li><a href="<c:url value='/features'/>">주요 기능</a></li>
            <li><a href="<c:url value='/pricing'/>">가격 정책</a></li>
            <li><a href="<c:url value='/roadmap'/>">로드맵</a></li>
          </ul>
        </div>
        <div>
          <div class="idb-footer-column-title">리소스</div>
          <ul class="idb-footer-list">
            <li><a href="<c:url value='/docs'/>">문서</a></li>
            <li><a href="<c:url value='/api'/>">API 가이드</a></li>
            <li><a href="<c:url value='/status'/>">서비스 상태</a></li>
          </ul>
        </div>
        <div>
          <div class="idb-footer-column-title">회사</div>
          <ul class="idb-footer-list">
            <li><a href="<c:url value='/about'/>">About</a></li>
            <li><a href="<c:url value='/contact'/>">문의</a></li>
            <li><a href="<c:url value='/policy'/>">개인정보 처리방침</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="idb-footer-bottom">
      <span>© Instru DB. All rights reserved.</span>
      <span>Made for data teams who love clean UIs 🎧</span>
    </div>

  </div>
</div>


<!-- 로딩 아이콘 -->
<div id="globalLoading" class="idb-spinner-backdrop">
    <div class="idb-spinner-box">
        <div class="idb-spinner"></div>
        <div class="idb-spinner-text">잠시만 기다려 주세요...</div>
    </div>
</div>