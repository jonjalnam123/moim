/**
 * 작성자 : 최정석
 * 작성날짜 : 2026.03.20
 * 내용 : adminJoin 스크립트
 */

// 이메일 인증 타이머 관련
var adminJoinTotalSeconds = 180;
var adminJoinRemaining = adminJoinTotalSeconds;
var adminJoinIntervalId = null;
var adminJoinEmailCode = '';

$(function () {

	// 초기 UI 세팅
	initAdminJoinUi();

	// 이벤트 바인딩
	bindAdminJoinCommonEvents();
	bindAdminJoinIdEvents();
	bindAdminJoinNameEvents();
	bindAdminJoinIdCheckEvent();
	bindAdminJoinPasswordEvents();
	bindAdminJoinEmailEvents();
	bindAdminJoinPhoneEvents();
	bindAdminJoinPostCodeEvent();
	bindAdminJoinGenderEvents();
	bindAdminJoinSubmitEvent();
});

/*******************************
* FuntionNm : initAdminJoinUi
* Date : 2026.03.20
* Author : CJS
* Description : 관리자 회원가입 화면 초기화
********************************/
function initAdminJoinUi() {

	$('#pwMismatch').removeClass('is-show');
	$('#adminPwMsg').removeClass('is-show');

	resetAdminJoinTimerUi();

	$('#adminEmaliNumChk').prop('readonly', true);
	$('#adminEmailNumChkBtn').prop('disabled', true);
	$('#adminEmailReChkBtn').hide();
}

/*******************************
* FuntionNm : bindAdminJoinCommonEvents
* Date : 2026.03.20
* Author : CJS
* Description : 공통 입력 이벤트 바인딩
********************************/
function bindAdminJoinCommonEvents() {

	$('#adminId, #adminNm, #adminPw, #adminPwChk, #adminEmail, #adminEmaliNumChk, #adminPh').on('input', function () {
		var val = $(this).val();
		$(this).val(removeSpace(val));
	});
}

/*******************************
* FuntionNm : bindAdminJoinIdEvents
* Date : 2026.03.20
* Author : CJS
* Description : 아이디 입력 이벤트 바인딩
********************************/
function bindAdminJoinIdEvents() {

	$('#adminId').on('keyup', function () {
		var adminIdVal = $(this).val();
		var adminId = onlyEngNum(adminIdVal);
		$('#adminId').val(adminId);
	});
}

/*******************************
* FuntionNm : bindAdminJoinNameEvents
* Date : 2026.03.20
* Author : CJS
* Description : 이름 입력 이벤트 바인딩
********************************/
function bindAdminJoinNameEvents() {

	$('#adminNm').on('keyup', function () {
		var adminNmVal = $(this).val();
		var adminNm = onlyKorEng(adminNmVal);
		$('#adminNm').val(adminNm);
	});
}

/*******************************
* FuntionNm : bindAdminJoinIdCheckEvent
* Date : 2026.03.20
* Author : CJS
* Description : 아이디 중복체크 이벤트 바인딩
********************************/
function bindAdminJoinIdCheckEvent() {

	$('#adminIdChkBtn').on('click', function () {

		var adminId = $('#adminId').val();

		if (isEmptyMsg(adminId, '아이디' + dataEmpty)) {
			return;
		}

		var url = '/admin/uniqueDupliChk.do';
		var params = {
			uniqueKey : adminId,
			tableNm : 'tb_admin_info'
		};
		var dataType = 'json';

		ajaxNoLoadingxStart(url, params, dataType, function (data) {
			var result = data.result;

			if (result === 'Y') {
				if (!confirm(joinIdChkSucConfirm)) {
					return;
				}

				$('#adminIdChk').val(result);
				$('#adminId').prop('readonly', true);
				$('#adminIdChkBtn').prop('disabled', true);
				$('#adminNm').focus();

			} else {
				alert(joinIdChkFail);
			}
		});
	});
}

/*******************************
* FuntionNm : bindAdminJoinPasswordEvents
* Date : 2026.03.20
* Author : CJS
* Description : 비밀번호 입력 이벤트 바인딩
********************************/
function bindAdminJoinPasswordEvents() {

	$('#adminPw').on('input', function () {
		validateAdminJoinPassword();
	});

	$('#adminPwChk').on('input', function () {
		validateAdminJoinPassword();
	});
}

/*******************************
* FuntionNm : validateAdminJoinPassword
* Date : 2026.03.20
* Author : CJS
* Description : 비밀번호 / 비밀번호 확인 검증
********************************/
function validateAdminJoinPassword() {

	var pw = $('#adminPw').val();
	var pwChk = $('#adminPwChk').val();
	var $pwError = $('#adminPwMsg');

	$('#pwMismatch').removeClass('hint');
	$('#pwMismatch').addClass('error');
	$('#pwMismatch').text('비밀번호가 일치하지 않습니다.');

	if (pw.length > 14) {
		pw = pw.substring(0, 14);
		$('#adminPw').val(pw);
	}

	if (pw.length < 14 || !validatePassword(pw)) {
		$pwError.text('특수문자 1개 이상 영어, 숫자만 14자리 입력').addClass('is-show');
		$('#adminPwChk').prop('readonly', true);
		$('#adminPwChk').val('');
		$('#pwMismatch').removeClass('is-show');
		return false;
	}

	$('#adminPwChk').prop('readonly', false);
	$pwError.removeClass('is-show');

	if (pwChk) {
		if (pw !== pwChk) {
			$('#pwMismatch').addClass('is-show');
			return false;
		} else {
			$('#pwMismatch').removeClass('error');
			$('#pwMismatch').addClass('hint');
			$('#pwMismatch').text('비밀번호가 일치합니다.');
		}
	}

	return true;
}

/*******************************
* FuntionNm : bindAdminJoinEmailEvents
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 인증 관련 이벤트 바인딩
********************************/
function bindAdminJoinEmailEvents() {

	$('#adminEmailChkBtn').on('click', function () {
		requestAdminJoinEmailAuth();
	});

	$('#adminEmailNumChkBtn').on('click', function () {
		checkAdminJoinEmailAuthCode();
	});

	$('#adminEmailReChkBtn').on('click', function () {
		resetAdminJoinEmailAuth();
	});
}

/*******************************
* FuntionNm : requestAdminJoinEmailAuth
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 인증번호 요청
********************************/
function requestAdminJoinEmailAuth() {

	var adminEmail = $('#adminEmail').val();

	if (isEmptyMsg(adminEmail, '이메일' + dataEmpty)) {
		$('#adminEmail').focus();
		return;
	}

	if (!isValidEmail(adminEmail)) {
		alert(joinMailVali);
		$('#adminEmail').focus();
		return;
	}

	var url = '/admin/joinMailChk.do';
	var params = {
		adminEmail : adminEmail
	};
	var dataType = 'json';

	ajaxStart(url, params, dataType, function (data) {

		adminJoinEmailCode = data.result;

		if (data.resultCd === '00') {
			$('#adminEmail').prop('readonly', true);
			$('#adminEmailChkBtn').prop('disabled', true);

			$('#adminEmaliNumChk').prop('readonly', false);
			$('#adminEmailNumChkBtn').prop('disabled', false);

			alert(joinMailChkResult);
			startAdminJoinTimer();
		} else {
			alert(joinMailChkResultFail);
		}
	});
}

/*******************************
* FuntionNm : checkAdminJoinEmailAuthCode
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 인증번호 확인
********************************/
function checkAdminJoinEmailAuthCode() {

	var inputCode = $('#adminEmaliNumChk').val();

	if (Number(inputCode) === adminJoinEmailCode) {

		stopAdminJoinTimer();

		$('#adminEmailChkYn').val('Y');
		$('#adminEmaliNumChk').prop('readonly', true);
		$('#adminEmailNumChkBtn').prop('disabled', true);

		$('#adminEmailChkBtn').hide();
		$('#adminEmailReChkBtn').hide();
		$('#adminEmailNumChkBtn').hide();

		alert(joinMailChkResultMsg);

	} else {

		$('#adminEmailChkYn').val('N');
		$('#adminEmailChkBtn').hide();
		$('#adminEmailReChkBtn').show();

		alert(joinMailChkResultFailMsg);
	}
}

/*******************************
* FuntionNm : resetAdminJoinEmailAuth
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 재인증 초기화
********************************/
function resetAdminJoinEmailAuth() {

	stopAdminJoinTimer();
	resetAdminJoinTimerUi();

	$('#adminEmailChkBtn').show().prop('disabled', false);
	$('#adminEmailReChkBtn').hide();

	$('#adminEmail').val('').prop('readonly', false);
	$('#adminEmaliNumChk').val('').prop('readonly', true);
	$('#adminEmailNumChkBtn').prop('disabled', true);
	$('#adminEmailChkYn').val('N');
}

/*******************************
* FuntionNm : bindAdminJoinPhoneEvents
* Date : 2026.03.20
* Author : CJS
* Description : 핸드폰 입력 이벤트 바인딩
********************************/
function bindAdminJoinPhoneEvents() {

	$('#adminPh').on('keyup', function () {
		var adminPh = $(this).val();

		if (adminPh.length > 11) {
			adminPh = adminPh.substring(0, 11);
			$(this).val(adminPh);
		}
	});
}

/*******************************
* FuntionNm : bindAdminJoinPostCodeEvent
* Date : 2026.03.20
* Author : CJS
* Description : 우편번호 찾기 이벤트 바인딩
********************************/
function bindAdminJoinPostCodeEvent() {

	$('#getPostCode').on('click', function () {
		execDaumPostcode(
			$('#adminPostCd').attr('id'),
			$('#adminAddress').attr('id')
		);
	});
}

/*******************************
* FuntionNm : bindAdminJoinGenderEvents
* Date : 2026.03.20
* Author : CJS
* Description : 성별 단일선택 이벤트 바인딩
********************************/
function bindAdminJoinGenderEvents() {

	$('.gender-check').on('change', function () {
		if ($(this).is(':checked')) {
			$('.gender-check').not(this).prop('checked', false);
		}
	});
}

/*******************************
* FuntionNm : bindAdminJoinSubmitEvent
* Date : 2026.03.20
* Author : CJS
* Description : 가입 버튼 이벤트 바인딩
********************************/
function bindAdminJoinSubmitEvent() {

	$('#joinBtn').on('click', function (e) {
		submitAdminJoin(e);
	});
}

/*******************************
* FuntionNm : submitAdminJoin
* Date : 2026.03.20
* Author : CJS
* Description : 관리자 회원가입 처리
********************************/
function submitAdminJoin(e) {

	var isValid = true;

	// 약관동의 파라미터
	var agreeService = $('#agreeService').val();
	var agreePrivacy = $('#agreePrivacy').val();
	var agreeMarketing = $('#agreeMarketing').val();
	var agreeConsign = $('#agreeConsign').val();

	// 가입정보 파라미터
	var adminId = $('#adminId').val();
	var adminIdChk = $('#adminIdChk').val();
	var adminNm = $('#adminNm').val();
	var adminPw = $('#adminPw').val();
	var adminPwChk = $('#adminPwChk').val();
	var adminEmail = $('#adminEmail').val();
	var adminEmaliNumChk = $('#adminEmaliNumChk').val();
	var adminEmailChkYn = $('#adminEmailChkYn').val();
	var adminPh = $('#adminPh').val();
	var adminSmsChkYn = $('#adminSmsChkYn').val();
	var adminPostCd = $('#adminPostCd').val();
	var adminAddress = $('#adminAddress').val();
	var adminDAddress = $('#adminDAddress').val();
	var adminGender = $('input[name="adminGender"]:checked').val();
	var adminCarYn = $('#adminCarYn').is(':checked') ? 'Y' : 'N';
	var adminEmailAlertYn = $('#adminEmailAlertYn').is(':checked') ? 'Y' : 'N';
	var adminSmsAlertYn = $('#adminSmsAlertYn').is(':checked') ? 'Y' : 'N';

	if (isEmptyMsg(adminId, '아이디' + dataEmpty)) {
		isValid = false;
		$('#adminId').focus();
		return;
	}

	if (adminIdChk !== 'Y') {
		isValid = false;
		alert(joinIdChk);
		return;
	}

	if (isEmptyMsg(adminNm, '이름' + dataEmpty)) {
		isValid = false;
		$('#adminNm').focus();
		return;
	}

	if (isEmptyMsg(adminPw, '비밀번호' + dataEmpty)) {
		isValid = false;
		$('#adminPw').focus();
		return;
	}

	if (!validateAdminJoinPassword()) {
		isValid = false;
		alert(joinChkPw);
		return;
	}

	if (isEmptyMsg(adminPwChk, '비밀번호 확인' + dataEmpty)) {
		isValid = false;
		$('#adminPwChk').focus();
		return;
	}

	if (isEmptyMsg(adminEmail, '이메일' + dataEmpty)) {
		isValid = false;
		$('#adminEmail').focus();
		return;
	}

	if (adminEmailChkYn !== 'Y') {
		alert(joinChkEmail);
		isValid = false;
		return;
	}

	if (isEmptyMsg(adminEmaliNumChk, '인증번호' + dataEmpty)) {
		isValid = false;
		$('#adminEmaliNumChk').focus();
		return;
	}

	if (isEmptyMsg(adminPh, '핸드폰' + dataEmpty)) {
		isValid = false;
		$('#adminPh').focus();
		return;
	}

	if (isEmptyMsg(adminPostCd, '우편번호' + dataEmpty)) {
		isValid = false;
		$('#adminPostCd').focus();
		return;
	}

	if (isEmptyMsg(adminAddress, '주소' + dataEmpty)) {
		isValid = false;
		$('#adminAddress').focus();
		return;
	}

	if (isEmptyMsg(adminDAddress, '상세주소' + dataEmpty)) {
		isValid = false;
		$('#adminDAddress').focus();
		return;
	}

	if (isEmptyMsg(adminGender, '성별' + dataEmpty)) {
		isValid = false;
		$('#adminGender').focus();
		return;
	}

	if (!isValid) {
		e.preventDefault();
		return false;
	}

	if (!confirm(joinProcConfirm)) {
		return;
	}

	var url = '/admin/joinProc.do';
	var params = {
		agreeService : agreeService,
		agreePrivacy : agreePrivacy,
		agreeMarketing : agreeMarketing,
		agreeConsign : agreeConsign,
		adminId : adminId,
		adminNm : adminNm,
		adminPw : adminPw,
		adminEmail : adminEmail,
		adminEmailChkYn : adminEmailChkYn,
		adminEmailAlertYn : adminEmailAlertYn,
		adminPh : adminPh,
		adminSmsChkYn : adminSmsChkYn,
		adminSmsAlertYn : adminSmsAlertYn,
		adminPostCd : adminPostCd,
		adminAddress : adminAddress,
		adminDAddress : adminDAddress,
		adminGender : adminGender,
		adminCarYn : adminCarYn
	};
	var dataType = 'json';

	ajaxStart(url, params, dataType, function (data) {
		var result = data.result;

		if (result > 0) {
			alert(joinProcSucc);
			goToUri('/admin/login.do');
		} else {
			alert(joinProcFail);
			goToUri('/admin/login.do');
		}
	});
}

/*******************************
* FuntionNm : formatAdminJoinTime
* Date : 2026.03.20
* Author : CJS
* Description : 인증시간 포맷 변환
********************************/
function formatAdminJoinTime(seconds) {

	var min = Math.floor(seconds / 60);
	var sec = seconds % 60;

	return (min < 10 ? '0' + min : min) + ':' + (sec < 10 ? '0' + sec : sec);
}

/*******************************
* FuntionNm : resetAdminJoinTimerUi
* Date : 2026.03.20
* Author : CJS
* Description : 인증 타이머 UI 초기화
********************************/
function resetAdminJoinTimerUi() {

	adminJoinRemaining = adminJoinTotalSeconds;
	$('#adminEmaliNumChk').attr('placeholder', formatAdminJoinTime(adminJoinRemaining));
}

/*******************************
* FuntionNm : stopAdminJoinTimer
* Date : 2026.03.20
* Author : CJS
* Description : 인증 타이머 중지
********************************/
function stopAdminJoinTimer() {

	if (adminJoinIntervalId !== null) {
		clearInterval(adminJoinIntervalId);
		adminJoinIntervalId = null;
	}
}

/*******************************
* FuntionNm : expireAdminJoinEmailAuth
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 인증 만료 처리
********************************/
function expireAdminJoinEmailAuth() {

	stopAdminJoinTimer();

	alert(joinEmailChkTimeFail);

	$('#adminEmail').val('').prop('readonly', false);

	$('#adminEmaliNumChk')
		.val('')
		.prop('readonly', true)
		.attr('placeholder', formatAdminJoinTime(adminJoinTotalSeconds));

	$('#adminEmailChkBtn').show().prop('disabled', false);
	$('#adminEmailReChkBtn').hide();
	$('#adminEmailNumChkBtn').prop('disabled', true);

	$('#adminEmailChkYn').val('N');
}

/*******************************
* FuntionNm : startAdminJoinTimer
* Date : 2026.03.20
* Author : CJS
* Description : 이메일 인증 타이머 시작
********************************/
function startAdminJoinTimer() {

	stopAdminJoinTimer();

	adminJoinRemaining = adminJoinTotalSeconds;
	$('#adminEmaliNumChk').attr('placeholder', formatAdminJoinTime(adminJoinRemaining));

	adminJoinIntervalId = setInterval(function () {

		adminJoinRemaining--;
		$('#adminEmaliNumChk').attr('placeholder', formatAdminJoinTime(adminJoinRemaining));

		if (adminJoinRemaining <= 0) {
			expireAdminJoinEmailAuth();
		}

	}, 1000);
}