/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 메인 페이지 스크립트
 */

$(function () {
	
	var time = $("#sessionTimeoutSec").val();
	var SESSION_TIMEOUT_MS = Number(time) * 1000;

	if (!SESSION_TIMEOUT_MS || SESSION_TIMEOUT_MS <= 0) return;

	setTimeout(function () {
		alert(sessionLogOutMsg);
	  	window.location.href = "/admin/sessionLogOut.do";
	}, SESSION_TIMEOUT_MS);
  
});

/*$(function () {

  var time = $("#sessionTimeoutSec").val();
  var SESSION_TIMEOUT_MS = Number(time) * 1000;

  if (!SESSION_TIMEOUT_MS || SESSION_TIMEOUT_MS <= 0) return;

  var logoutTimer = null;

  function clearTimer() {
    if (logoutTimer) clearTimeout(logoutTimer);
    logoutTimer = null;
  }

  function scheduleLogout() {
    clearTimer();
    logoutTimer = setTimeout(function () {
      window.location.href = window.APP_CTX + "/logout.do";
    }, SESSION_TIMEOUT_MS);
  }

  // 사용자 행동 시 타이머 리셋(= 비활동 기준)
  $(document).on("mousemove keydown click scroll touchstart", scheduleLogout);

  // 초기 시작
  scheduleLogout();

  // AJAX 401 시에도 로그아웃
  $(document).ajaxComplete(function (event, xhr) {
    if (xhr && xhr.status === 401) {
      window.location.href = window.APP_CTX + "/logout.do";
    }
  });

});*/