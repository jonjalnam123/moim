<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/main/adminMain.js"></script>

<input type="hidden" id="sessionTimeoutSec" value="${sessionTimeoutSec}">

<!-- Draw view [S] -->
<div class="content-wrapper">
    <section class="dashboard-home">

      <!-- 상단 요약 영역 -->
      <div class="dashboard-hero">
        <div class="dashboard-hero-left">
          <h2 class="dashboard-hero-title">운영 현황 대시보드</h2>
          <p class="dashboard-hero-desc">
            오늘 기준 주요 지표, 공지, 일정, 바로가기를 한 화면에서 확인할 수 있습니다.
          </p>
        </div>

        <div class="dashboard-hero-right">
          <div class="hero-chip">
            <span class="k">오늘 날짜</span>
            <span class="v">${todayYmd}</span>
          </div>
          <div class="hero-chip">
            <span class="k">이번달 신규 회원</span>
            <span class="v">${dashboard.newMemberCnt}명</span>
          </div>
          <div class="hero-chip">
            <span class="k">이번주 예정 모임</span>
            <span class="v">${dashboard.weekMeetingCnt}건</span>
          </div>
        </div>
      </div>

      <!-- 4분할 -->
      <div class="dashboard-grid">

        <!-- 1. 통계 -->
        <article class="dash-panel">
          <div class="dash-panel-header">
            <div class="dash-panel-title-wrap">
              <h3 class="dash-panel-title">통계 현황</h3>
              <p class="dash-panel-sub">회원/모임/출석 기준 핵심 지표</p>
            </div>
          </div>

          <div class="dash-panel-body">
            <div class="stats-summary">
              <div class="kpi-card is-primary">
                <span class="label">전체 회원수</span>
                <span class="value">${dashboard.totalMemberCnt}</span>
                <span class="meta">전월 대비 +${dashboard.memberGrowthRate}%</span>
              </div>

              <div class="kpi-card">
                <span class="label">누적 모임 횟수</span>
                <span class="value">${dashboard.totalMeetingCnt}</span>
                <span class="meta">이번달 ${dashboard.monthMeetingCnt}회 진행</span>
              </div>

              <div class="kpi-card">
                <span class="label">평균 출석률</span>
                <span class="value">${dashboard.avgAttendRate}%</span>
                <span class="meta">최근 30일 기준</span>
              </div>

              <div class="kpi-card">
                <span class="label">활성 모임 수</span>
                <span class="value">${dashboard.activeGroupCnt}</span>
                <span class="meta">이번주 운영중</span>
              </div>
            </div>

            <div class="stats-progress">
              <div class="stats-progress-item">
                <div class="stats-progress-top">
                  <span class="name">이번달 회원 모집 목표</span>
                  <span class="percent">${dashboard.memberGoalRate}%</span>
                </div>
                <div class="stats-progress-bar">
                  <span style="width:${dashboard.memberGoalRate}%;"></span>
                </div>
              </div>

              <div class="stats-progress-item">
                <div class="stats-progress-top">
                  <span class="name">이번달 모임 진행률</span>
                  <span class="percent">${dashboard.meetingGoalRate}%</span>
                </div>
                <div class="stats-progress-bar">
                  <span style="width:${dashboard.meetingGoalRate}%;"></span>
                </div>
              </div>

              <div class="stats-progress-item">
                <div class="stats-progress-top">
                  <span class="name">공지 확인율</span>
                  <span class="percent">${dashboard.noticeReadRate}%</span>
                </div>
                <div class="stats-progress-bar">
                  <span style="width:${dashboard.noticeReadRate}%;"></span>
                </div>
              </div>
            </div>
          </div>
        </article>

        <!-- 2. 공지사항 -->
        <article class="dash-panel">
          <div class="dash-panel-header">
            <div class="dash-panel-title-wrap">
              <h3 class="dash-panel-title">공지사항</h3>
              <p class="dash-panel-sub">최신 공지 5건</p>
            </div>

            <button type="button"
                    class="notice-more-btn"
                    onclick="goNoticeMore();">
              더보기
            </button>
          </div>

          <div class="dash-panel-body">
            <c:choose>
              <c:when test="${not empty noticeList}">
                <ul class="notice-list">
                  <c:forEach var="notice" items="${noticeList}" begin="0" end="4">
                    <li class="notice-item">
                      <c:choose>
                        <c:when test="${notice.noticeType eq '중요'}">
                          <span class="notice-badge is-important">중요</span>
                        </c:when>
                        <c:when test="${notice.noticeType eq '이벤트'}">
                          <span class="notice-badge is-event">이벤트</span>
                        </c:when>
                        <c:otherwise>
                          <span class="notice-badge">일반</span>
                        </c:otherwise>
                      </c:choose>

                      <div class="notice-content">
                        <a href="javascript:void(0);"
                           class="notice-title"
                           onclick="goNoticeDetail('${notice.noticeId}');">
                          ${notice.title}
                        </a>
                        <div class="notice-desc">
                          ${notice.summary}
                        </div>
                      </div>

                      <div class="notice-date">${notice.regDate}</div>
                    </li>
                  </c:forEach>
                </ul>
              </c:when>

              <c:otherwise>
                <div class="grid-empty-wrap">
                  <div class="table-empty">
                    <div class="table-empty-title">등록된 공지사항이 없습니다.</div>
                    <div class="table-empty-desc">새 공지가 등록되면 이 영역에 표시됩니다.</div>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
        </article>

        <!-- 3. 빠른 실행 -->
        <article class="dash-panel">
          <div class="dash-panel-header">
            <div class="dash-panel-title-wrap">
              <h3 class="dash-panel-title">빠른 실행</h3>
              <p class="dash-panel-sub">자주 사용하는 메뉴로 바로 이동</p>
            </div>
          </div>

          <div class="dash-panel-body">
            <div class="quick-grid">
              <a href="javascript:void(0);" class="quick-card" onclick="goMenu('member');">
                <div class="quick-icon">M</div>
                <div class="quick-text">
                  <span class="quick-title">회원 관리</span>
                  <span class="quick-desc">회원 조회, 등록, 상태 변경</span>
                </div>
              </a>

              <a href="javascript:void(0);" class="quick-card" onclick="goMenu('meeting');">
                <div class="quick-icon">G</div>
                <div class="quick-text">
                  <span class="quick-title">모임 관리</span>
                  <span class="quick-desc">모임 생성, 일정 등록, 참여자 확인</span>
                </div>
              </a>

              <a href="javascript:void(0);" class="quick-card" onclick="goMenu('attendance');">
                <div class="quick-icon">A</div>
                <div class="quick-text">
                  <span class="quick-title">출석 관리</span>
                  <span class="quick-desc">출석 체크, 통계, 누락 확인</span>
                </div>
              </a>

              <a href="javascript:void(0);" class="quick-card" onclick="goMenu('settings');">
                <div class="quick-icon">S</div>
                <div class="quick-text">
                  <span class="quick-title">운영 설정</span>
                  <span class="quick-desc">카테고리, 권한, 기본 환경 설정</span>
                </div>
              </a>
            </div>
          </div>
        </article>

        <!-- 4. 일정 + 최근 활동 -->
        <article class="dash-panel">
          <div class="dash-panel-header">
            <div class="dash-panel-title-wrap">
              <h3 class="dash-panel-title">오늘 일정 / 최근 활동</h3>
              <p class="dash-panel-sub">운영자가 바로 확인해야 할 흐름</p>
            </div>
          </div>

          <div class="dash-panel-body">
            <div class="dual-stack">

              <div class="inner-box">
                <div class="inner-box-head">다가오는 일정</div>
                <div class="inner-box-body">
                  <c:choose>
                    <c:when test="${not empty meetingScheduleList}">
                      <c:forEach var="item" items="${meetingScheduleList}" begin="0" end="4">
                        <div class="schedule-item">
                          <div class="schedule-time">${item.startTime}</div>
                          <div class="schedule-content">
                            <div class="schedule-title">${item.meetingTitle}</div>
                            <div class="schedule-meta">${item.location} · 참석예정 ${item.expectedCnt}명</div>
                          </div>
                        </div>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <div class="table-empty">
                        <div class="table-empty-title">예정된 일정이 없습니다.</div>
                        <div class="table-empty-desc">새 일정이 등록되면 여기서 바로 확인할 수 있습니다.</div>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>

              <div class="inner-box">
                <div class="inner-box-head">최근 활동</div>
                <div class="inner-box-body">
                  <c:choose>
                    <c:when test="${not empty recentActivityList}">
                      <c:forEach var="act" items="${recentActivityList}" begin="0" end="5">
                        <div class="activity-item">
                          <c:choose>
                            <c:when test="${act.activityType eq '회원'}">
                              <div class="activity-dot"></div>
                            </c:when>
                            <c:when test="${act.activityType eq '공지'}">
                              <div class="activity-dot is-blue"></div>
                            </c:when>
                            <c:when test="${act.activityType eq '오류'}">
                              <div class="activity-dot is-red"></div>
                            </c:when>
                            <c:otherwise>
                              <div class="activity-dot is-gray"></div>
                            </c:otherwise>
                          </c:choose>

                          <div class="activity-content">
                            <div class="activity-title">${act.title}</div>
                            <div class="activity-meta">${act.description} · ${act.regDate}</div>
                          </div>
                        </div>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <div class="table-empty">
                        <div class="table-empty-title">최근 활동 내역이 없습니다.</div>
                        <div class="table-empty-desc">회원/공지/모임 관련 활동이 이 영역에 표시됩니다.</div>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>

            </div>
          </div>
        </article>

      </div>
    </section>
</div>
<!-- Draw view [E] -->