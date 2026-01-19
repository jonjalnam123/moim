/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 헤더 스크립트
 */

$(function () {	
	
	getSessionTime();
	
	// 로그아웃 버튼 이벤트
	$('#logOutBtn').on('click', function(){
		logOut();
	});
	
});


/*******************************
* FuntionNm : logOut
* Date : 2025.10.02
* Author : CJS
* Description : 로그아웃 
* PARAM : 
********************************/
function logOut() {
	var url = '/admin/logOut.do';
	var params = {}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(data) {
		if ( data.result = 'Y' ) {
			goToUri('/admin/login.do');
		} else {
			alert(logOutFailMsg);
			return;
		}
	});
}

/*******************************
* FuntionNm : getSessionTime
* Date : 2025.10.02
* Author : CJS
* Description : 세션 시간 조회
* PARAM : 
********************************/
function getSessionTime() {
	console.log('getSessionTime');
	var url = '/admin/sessionTime.do';
	var params = {}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(data) {
		var time = data.timeoutSec;
		
		var SESSION_TIMEOUT_MS = Number(time) * 1000;

		if (!SESSION_TIMEOUT_MS || SESSION_TIMEOUT_MS <= 0) return;

		setTimeout(function () {
			alert(sessionLogOutMsg);
		  	window.location.href = "/admin/sessionLogOut.do";
		}, SESSION_TIMEOUT_MS);
		
	});
}

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
