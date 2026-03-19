<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/adminHeader.js"></script>

<!-- Param From SessionData [S] -->
<input type="hidden" id="ss_admin_id" value="${SS_ADMIN_ID}">
<!-- Param From SessionData [E] -->

<!-- Draw view [S] -->
<c:set var="loginAdminId" value="${empty adminId ? 'guest' : adminId}" />
<c:set var="loginAdminProfileImg" value="${sessionScope.loginAdminProfileImg}" />
<c:set var="notiCountVal" value="${empty requestScope.notiCount ? 0 : requestScope.notiCount}" />

<header class="moim-header">
	<div class="header-left">
		<a href="${pageContext.request.contextPath}/admin/main.do" class="logo-wrap">
			<div class="logo-mark">M</div>
			<div class="logo-text">
				<div class="logo-title">MOIM</div>
				<div class="logo-sub">모임 관리 서비스</div>
			</div>
		</a>
	</div>

	<div class="header-right">
		<div class="header-noti-wrap">
			<button type="button" class="icon-btn" id="headerNotiBtn" title="알림">
				<i class="fa-regular fa-bell"></i>
				<span class="noti-badge" id="headerNotiBadge">${notiCountVal}</span>
			</button>

			<div class="noti-dropdown" id="headerNotiLayer">
				<div class="noti-dropdown-head">
					<div class="noti-dropdown-title">알림</div>
					<button type="button" class="noti-close-btn" id="headerNotiCloseBtn">닫기</button>
				</div>

				<div class="noti-dropdown-body">
					<c:choose>
						<c:when test="${not empty requestScope.notiList}">
							<ul class="noti-list">
								<c:forEach var="item" items="${requestScope.notiList}">
									<li class="noti-item ${item.readYn eq 'Y' ? 'is-read' : 'is-new'}">
										<a href="${empty item.linkUrl ? 'javascript:void(0);' : item.linkUrl}" class="noti-link">
											<div class="noti-item-top">
												<span class="noti-item-badge ${item.readYn eq 'Y' ? 'is-read' : 'is-new'}">
													<c:choose>
														<c:when test="${item.readYn eq 'Y'}">읽음</c:when>
														<c:otherwise>NEW</c:otherwise>
													</c:choose>
												</span>
												<span class="noti-item-date">${item.regDt}</span>
											</div>
											<div class="noti-item-title">${item.title}</div>
											<div class="noti-item-desc">${item.content}</div>
										</a>
									</li>
								</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<div class="noti-empty">
								<div class="noti-empty-icon"><i class="fa-regular fa-bell"></i></div>
								<div class="noti-empty-title">새 알림이 없습니다.</div>
								<div class="noti-empty-desc">도착한 알림은 여기에 표시됩니다.</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="noti-dropdown-foot">
					<a href="${pageContext.request.contextPath}/mypage/notification.do" class="noti-more-link">전체 알림 보기</a>
				</div>
			</div>
		</div>

		<div class="user-box">
			<div class="user-avatar">
				<c:choose>
					<c:when test="${not empty loginAdminProfileImg}">
						<img src="${loginAdminProfileImg}" alt="프로필">
					</c:when>
					<c:otherwise>
						<div class="user-avatar-fallback">${fn:substring(loginAdminId, 0, 1)}</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="user-id">${loginAdminId}</div>
		</div>

		<a href="javascript:void(0);" id="myPageBtn" class="header-link-btn mypage">마이페이지</a>
		<a href="javascript:void(0);" id="logOutBtn" class="header-link-btn logout">로그아웃</a>
	</div>
</header>
<!-- Draw view [E] -->