/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminJoin 스크립트 (통합 최종본)
 */
$(function () {

    /* ==============================
       공통 변수
    ============================== */
    var totalSeconds = 180;
    var remaining = totalSeconds;
    var intervalId = null;
    var adminEmailCode = '';

    /* ==============================
       타이머 영역
    ============================== */
    function formatTime(seconds) {
        var min = Math.floor(seconds / 60);
        var sec = seconds % 60;
        return (min < 10 ? "0" + min : min) + ":" +
               (sec < 10 ? "0" + sec : sec);
    }

    function resetTimerUI() {
        remaining = totalSeconds;
        $('#adminEmaliNumChk').attr('placeholder', formatTime(remaining));
    }

    function stopTimer() {
        if (intervalId !== null) {
            clearInterval(intervalId);
            intervalId = null;
        }
    }

    function expireProcess() {

        stopTimer();

        alert("3분이 종료되었습니다.");

        $('#adminEmail').val('').prop('readonly', false);

        $('#adminEmaliNumChk')
            .val('')
            .prop('readonly', true)
            .attr('placeholder', formatTime(totalSeconds));

        $('#adminEmailChkBtn').show().prop('disabled', false);
        $('#adminEmailReChkBtn').hide();
        $('#adminEmailNumChkBtn').prop('disabled', true);

        $('#adminEmailChkYn').val('N');
    }

    function startTimer() {

        stopTimer();
        remaining = totalSeconds;

        $('#adminEmaliNumChk').attr('placeholder', formatTime(remaining));

        intervalId = setInterval(function () {

            remaining--;
            $('#adminEmaliNumChk').attr('placeholder', formatTime(remaining));

            if (remaining <= 0) {
                expireProcess();
            }

        }, 1000);
    }

    resetTimerUI();

    /* ==============================
       비밀번호 검증 영역
    ============================== */

    $('#pwMismatch').hide();
    $('#adminPwMsg').hide();

    function validatePwAll() {
		
		$('#pwMismatch').removeClass('hint'); 
		$('#pwMismatch').addClass('error');
		$('#pwMismatch').text('비밀번호가 일치하지 않습니다.');
		
        var pw = $('#adminPw').val();
        var pwChk = $('#adminPwChk').val();
        var $pwError = $('#adminPwMsg');
		
		if (pw.length > 14) {
		    pw = pw.substring(0, 14);
		    $(this).val(pw);
		}

        if ( pw.length < 14 || !validatePassword(pw)) {
            $pwError.text('특수문자 1개 이상 영어, 숫자만 14자리 입력').show();
			$('#adminPwChk').prop('readonly', true);
			$('#adminPwChk').val('');
			$('#pwMismatch').hide();
            return false;
        }
		
		$('#adminPwChk').prop('readonly', false);
        $pwError.hide();

        if (pwChk) {
            if (pw !== pwChk) {
                $('#pwMismatch').show();
                return false;
            } else {
                $('#pwMismatch').removeClass('error');
                $('#pwMismatch').addClass('hint');
                $('#pwMismatch').text('비밀번호가 일치합니다.');
            }
        }

        return true;
    }
	
	/* ==============================
	   이벤트
	============================== */
	
	// 공백입력 방지 이벤트
	$('#adminId, #adminNm,#adminPw, #adminPwChk, #adminEmail, #adminEmaliNumChk, #adminPh').on('input', function() {
	    var val = $(this).val();
	    $(this).val(removeSpace(val));
	});
	
	// 아이디 입력 이벤트 [S]
	$('#adminId').on('keyup', function() {
		var adminIdVal = $(this).val();
		var adminId = onlyEngNum(adminIdVal);
		$('#adminId').val(adminId);
	});
	// 아이디 입력 이벤트 [E]
	
	// 이름 입력 이벤트 [S]
	$('#adminNm').on('keyup', function() {
		var adminNmVal = $(this).val();
		var adminNm = onlyKorEng(adminNmVal);
		$('#adminNm').val(adminNm);
	});
	// 이름 입력 이벤트 [E]
	
	// 아이디 중복체크 [S]
	$('#adminIdChkBtn').on('click', function() {

		var adminId = $('#adminId').val();
		
		if ( isEmptyMsg(adminId, '아이디' + dataEmpty) ) {
			return;
		}

		var tableNm = 'tb_admin_info';
		var url = '/admin/uniqueDupliChk.do';
		var params = {
			uniqueKey : adminId
		  , tableNm : tableNm
		}
		var dataType = 'json'
		ajaxNoLoadingxStart(url, params, dataType, function(data) {
			var result = data.result;
			if ( result === 'Y' ) { // 중복 X
				if ( !confirm(joinIdChkSucConfirm) ) {
					return;
				} else {
					$('#adminIdChk').val(result);
					$('#adminId').prop('readonly', true);
					$('#adminIdChkBtn').prop('disabled', true);
					$('#adminNm').focus();
				}
			} else { // 중복
				alert(joinIdChkFail);
			}
		});
	});
	// 아이디 중복체크 [E]

	// 비밀번호 입력 이벤트 [S]
    $('#adminPw').on('input', validatePwAll);
    $('#adminPwChk').on('input', validatePwAll);
	// 비밀번호 입력 이벤트 [E]

    /* ==============================
       이메일 인증 요청
    ============================== */
    $('#adminEmailChkBtn').click(function () {

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

        ajaxStart('/admin/joinMailChk.do',
            { adminEmail: adminEmail },
            'json',
            function (data) {

                adminEmailCode = data.result;

                if (data.resultCd === '00') {

                    $('#adminEmail').prop('readonly', true);
                    $('#adminEmailChkBtn').prop('disabled', true);

                    $('#adminEmaliNumChk').prop('readonly', false);
                    $('#adminEmailNumChkBtn').prop('disabled', false);

                    alert(joinMailChkResult);

                    startTimer();

                } else {
                    alert(joinMailChkResultFail);
                }
            });
    });

    /* ==============================
       인증번호 확인
    ============================== */
    $('#adminEmailNumChkBtn').on('click', function () {

        var inputCode = $('#adminEmaliNumChk').val();
		console.log('adminEmailCode==', adminEmailCode);
        if (Number(inputCode) === adminEmailCode) {

            stopTimer();

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
    });

    /* ==============================
       재인증
    ============================== */
    $('#adminEmailReChkBtn').on('click', function () {

        stopTimer();
        resetTimerUI();

        $('#adminEmailChkBtn').show().prop('disabled', false);
        $('#adminEmailReChkBtn').hide();

        $('#adminEmail').val('').prop('readonly', false);

        $('#adminEmaliNumChk').val('').prop('readonly', true);
        $('#adminEmailNumChkBtn').prop('disabled', true);
    });
	
	/* ==============================
	   핸드폰
	============================== */
	$('#adminPh').on('keyup', function () {
		var adminPh = $(this).val();
		if ( adminPh.length > 11 ) {
			adminPh = adminPh.substring(0, 11);
			$(this).val(adminPh);
		}
	});

    /* ==============================
       우편번호
    ============================== */
    $('#getPostCode').on('click', function () {
        execDaumPostcode(
            $('#adminPostCd').attr('id'),
            $('#adminAddress').attr('id')
        );
    });
	
	/* ==============================
	   성별 단일선택
	============================== */
	$('.gender-check').on('change', function () {
	    if ($(this).is(':checked')) {
	        $('.gender-check').not(this).prop('checked', false);
	    }
	});

    /* ==============================
       가입 버튼 최종 검증
    ============================== */
    $('#joinBtn').on('click', function (e) {

        var isValid = true;
		
		// 약관동의 파라미터
		var agreeService 				= $('#agreeService').val();
		var agreePrivacy 				= $('#agreePrivacy').val();
		var agreeMarketing 			= $('#agreeMarketing').val();
		var agreeConsign 				= $('#agreeConsign').val();
		
		// 가입정보 파라미터
		var adminId 				= $('#adminId').val();
		var adminIdChk 			= $('#adminIdChk').val();
		var adminNm 				= $('#adminNm').val();
		var adminPw 				= $('#adminPw').val();
		var adminPwChk 			= $('#adminPwChk').val();
		var adminEmail 			= $('#adminEmail').val();
		var adminEmaliNumChk 	= $('#adminEmaliNumChk').val();
		var adminEmailChkYn 	= $('#adminEmailChkYn').val();
		var adminPh 				= $('#adminPh').val();
		var adminSmsChkYn 		= $('#adminSmsChkYn').val();
	    var adminPostCd 			= $('#adminPostCd').val();
		var adminAddress 		= $('#adminAddress').val();
		var adminDAddress 		= $('#adminDAddress').val();
		var adminGender 			= $('input[name="adminGender"]:checked').val();
		var adminCarYn 			= $('#adminCarYn').is(':checked') ? 'Y' : 'N';
		var adminEmailAlertYn 	= $('#adminEmailAlertYn').is(':checked') ? 'Y' : 'N';
		var adminSmsAlertYn 	= $('#adminSmsAlertYn').is(':checked') ? 'Y' : 'N';
		
		if ( isEmptyMsg(adminId, '아이디' + dataEmpty) ) {
			isValid = false;
			$('#adminId').focus();
			return;
		}
		
		if ( adminIdChk !== 'Y' ) {
			isValid = false;
			alert(joinIdChk);
			return;
		}
		
		if ( isEmptyMsg(adminNm, '이름' + dataEmpty) ) {
			isValid = false;
			$('#adminNm').focus();
			return;
		}
		
		if ( isEmptyMsg(adminPw, '비밀번호' + dataEmpty) ) {
			isValid = false;
			$('#adminPw').focus();
			return;
		}
		
		if (!validatePwAll()) {
		    isValid = false;
			alert(joinChkPw);
			return;
		}
		
		if ( isEmptyMsg(adminPwChk, '비밀번호 확인' + dataEmpty) ) {
			isValid = false;
			$('#adminPwChk').focus();
			return;
		}
		
		if ( isEmptyMsg(adminEmail, '이메일' + dataEmpty) ) {
			isValid = false;
			$('#adminEmail').focus();
			return;
		}
		
		if (adminEmailChkYn !== 'Y') {
		    alert(joinChkEmail);
		    isValid = false;
			return;
		}
		
		if ( isEmptyMsg(adminEmaliNumChk, '인증번호' + dataEmpty) ) {
			isValid = false;
			$('#adminEmaliNumChk').focus();
			return;
		}
		
		if ( isEmptyMsg(adminPh, '핸드폰' + dataEmpty) ) {
			isValid = false;
			$('#adminPh').focus();
			return;
		}
		
		if ( isEmptyMsg(adminPostCd, '우편번호' + dataEmpty) ) {
			isValid = false;
			$('#adminPostCd').focus();
			return;
		}
		
		if ( isEmptyMsg(adminAddress, '주소' + dataEmpty) ) {
			isValid = false;
			$('#adminAddress').focus();
			return;
		}
		
		if ( isEmptyMsg(adminDAddress, '상세주소' + dataEmpty) ) {
			isValid = false;
			$('#adminDAddress').focus();
			return;
		}
		
		if ( isEmptyMsg(adminGender, '성별' + dataEmpty) ) {
			isValid = false;
			$('#adminGender').focus();
			return;
		}

        if (!isValid) {
            e.preventDefault();
            return false;
        }

		if ( !confirm(joinProcConfirm) ) {
			return;
		}
		
		var url = '/admin/joinProc.do';
		var params = {
			    agreeService : agreeService
			  , agreePrivacy : agreePrivacy
		 	  , agreeMarketing : agreeMarketing
			  , agreeConsign : agreeConsign
			  , adminId : adminId
			  , adminNm : adminNm
			  , adminPw : adminPw
			  , adminEmail : adminEmail
			  , adminEmailChkYn : adminEmailChkYn
			  , adminEmailAlertYn : adminEmailAlertYn
			  , adminPh : adminPh
			  , adminSmsChkYn : adminSmsChkYn
			  , adminSmsAlertYn : adminSmsAlertYn
			  , adminPostCd : adminPostCd
			  , adminAddress : adminAddress
			  , adminDAddress : adminDAddress
			  , adminGender : adminGender
			  , adminCarYn : adminCarYn

		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = data.result
			console.log('result====', result);
		});
		
    });

});