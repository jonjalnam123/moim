/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 헤더 스크립트
 */

$(function () {
	initHeader();
});

/*******************************
* FuntionNm : initHeader
* Date : 2025.10.02
* Author : CJS
* Description : 헤더 초기화
* PARAM :
********************************/
function initHeader() {
	checkHeaderSession();
	bindHeaderEvent();
	syncNotiBadge();
}

/*******************************
* FuntionNm : bindHeaderEvent
* Date : 2025.10.02
* Author : CJS
* Description : 헤더 이벤트 바인딩
* PARAM :
********************************/
function bindHeaderEvent() {

	// 로그아웃 버튼 이벤트
	$('#logOutBtn').on('click', function () {
		logOut();
	});

	// 알림 버튼 클릭
	$('#headerNotiBtn').on('click', function (e) {
		e.stopPropagation();
		toggleNotiLayer();
	});

	// 알림 닫기 버튼 클릭
	$('#headerNotiCloseBtn').on('click', function (e) {
		e.preventDefault();
		closeNotiLayer();
	});

	// 알림 레이어 내부 클릭 시 닫힘 방지
	$('#headerNotiLayer').on('click', function (e) {
		e.stopPropagation();
	});

	// 문서 클릭 시 알림 레이어 닫기
	$(document).on('click', function () {
		closeNotiLayer();
	});
}

/*******************************
* FuntionNm : checkHeaderSession
* Date : 2025.10.02
* Author : CJS
* Description : 헤더 세션 체크
* PARAM :
********************************/
function checkHeaderSession() {
	var s_adminId = $('#s_adminId').val();
	console.log('s_adminId====', s_adminId);
	if (isEmpty(s_adminId)) {
		alert('세션이 만료되었습니다.');
		logOut();
	}
}

/*******************************
* FuntionNm : syncNotiBadge
* Date : 2025.10.02
* Author : CJS
* Description : 헤더 알림 뱃지 초기 표시 처리
* PARAM :
********************************/
function syncNotiBadge() {
	var $badge = $('#headerNotiBadge');

	if (!$badge.length) return;

	var cnt = parseInt($.trim($badge.text()) || '0', 10);

	if (!cnt || cnt <= 0) {
		$badge.addClass('is-zero');
	} else {
		$badge.removeClass('is-zero');
	}
}

/*******************************
* FuntionNm : toggleNotiLayer
* Date : 2025.10.02
* Author : CJS
* Description : 알림 레이어 토글
* PARAM :
********************************/
function toggleNotiLayer() {
	var $notiLayer = $('#headerNotiLayer');

	if (!$notiLayer.length) return;

	$notiLayer.toggleClass('is-open');
}

/*******************************
* FuntionNm : openNotiLayer
* Date : 2025.10.02
* Author : CJS
* Description : 알림 레이어 열기
* PARAM :
********************************/
function openNotiLayer() {
	var $notiLayer = $('#headerNotiLayer');

	if (!$notiLayer.length) return;

	$notiLayer.addClass('is-open');
}

/*******************************
* FuntionNm : closeNotiLayer
* Date : 2025.10.02
* Author : CJS
* Description : 알림 레이어 닫기
* PARAM :
********************************/
function closeNotiLayer() {
	var $notiLayer = $('#headerNotiLayer');

	if (!$notiLayer.length) return;

	$notiLayer.removeClass('is-open');
}

/*******************************
* FuntionNm : logOut
* Date : 2025.10.02
* Author : CJS
* Description : 로그아웃
* PARAM :
********************************/
function logOut() {
	var url = '/admin/logOut.do';
	var params = {};
	var dataType = 'json';

	ajaxStart(url, params, dataType, function (data) {
		if (data.result == 'Y') {
			goToUri('/admin/login.do');
		} else {
			goToUri('/admin/error.do');
		}
	});
}

/*******************************
* FuntionNm : setHeaderNotiCount
* Date : 2025.10.02
* Author : CJS
* Description : 헤더 알림 표시 숫자 설정
* PARAM : cnt
********************************/
function setHeaderNotiCount(cnt) {
	var $badge = $('#headerNotiBadge');

	if (!$badge.length) return;

	$badge.text(cnt);

	if (!cnt || cnt <= 0) {
		$badge.addClass('is-zero');
	} else {
		$badge.removeClass('is-zero');
	}
}