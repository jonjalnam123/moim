<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="${pageContext.request.contextPath}/resources/static/js/user/userHeader.js"></script>

<div class="idb-container">
  <!-- 모바일 네비 토글 체크박스 (헤더 밖, 모바일 메뉴와 형제 관계) -->
  <input type="checkbox" id="idb-nav-toggle" class="idb-nav-toggle" />

  <div class="idb-header-inner">
    <!-- 로고 -->
    <div class="idb-logo-group">
      <div class="idb-logo-mark">
        <div class="idb-logo-mark-inner"></div>
      </div>
      <div class="idb-logo-text">
        Instru <span>DB</span>
      </div>
    </div>

    <!-- 모바일 햄버거 버튼 (체크박스는 위에 있고, 여기서는 label만) -->
    <label for="idb-nav-toggle" class="idb-nav-toggle-label">
      <span></span><span></span><span></span>
    </label>

    <!-- 데스크톱 네비 -->
    <nav class="idb-nav">
      <a href="<c:url value='/home'/>"
         class="idb-nav-link idb-nav-link--active">대시보드</a>
      <a href="<c:url value='/schemas'/>"
         class="idb-nav-link">스키마</a>
      <a href="<c:url value='/monitor'/>"
         class="idb-nav-link">모니터링</a>
      <a href="<c:url value='/audit'/>"
         class="idb-nav-link">감사 로그</a>
    </nav>

    <!-- 오른쪽 액션 (데스크톱에서만 보임, 모바일에서는 CSS로 숨김) -->
    <div class="idb-header-actions">
      <button type="button" class="idb-btn idb-btn-ghost">
        문서
      </button>
      <button type="button" class="idb-btn idb-btn-primary">
        새 연결 생성
      </button>
      <button type="button" class="idb-btn idb-btn-primary" id="goToAdminBtn">
        관리자 모드
      </button>
    </div>
  </div>

  <!-- 모바일 네비 (토글시 펼쳐짐 / 데스크톱에서는 CSS에서 display:none) -->
  <div class="idb-nav-mobile">
    <div class="idb-nav-mobile-inner">
      <a href="<c:url value='/home'/>"
         class="idb-nav-link idb-nav-link--active">대시보드</a>
      <a href="<c:url value='/schemas'/>"
         class="idb-nav-link">스키마</a>
      <a href="<c:url value='/monitor'/>"
         class="idb-nav-link">모니터링</a>
      <a href="<c:url value='/audit'/>"
         class="idb-nav-link">감사 로그</a>
      <a href="<c:url value='/docs'/>"
         class="idb-nav-link">문서</a>
    </div>
  </div>
</div>
