/**
 * 작성자 : 최정석
 * 작성날짜 : 2026.02.18
 * 내용 : adminLoginPwSet 스크립트
 */
$(function () {
	
	// 비밀번호 입력 포커스
	$('#adminPw').focus();

	// 공백입력 방지 이벤트
	$('#adminPw, #adminPwChk').on('input', function() {
	    var val = $(this).val();
	    $(this).val(removeSpace(val));
	});
	
	// 비밀번호 입력 이벤트
	$('#adminPw').on('keyup', function() {
		var adminPw = $(this).val();
		$('#adminPwResult').val('N');
		$('#adminPwChk').val('');
		
		if ( isEmpty(adminPw) ) {
			$('.error').css('color', '#e53935');
			$('.error').text('특수문자 1개 이상 영어, 숫자만 14자리 입력');
			return;
		}
		
		if (adminPw.length > 14) {
		    adminPw = adminPw.substring(0, 14);
		    $(this).val(adminPw);
		}

		if ( !validatePassword(adminPw) || adminPw.length !== 14 ) {
			$('.error').css('color', '#e53935');
		    $('.error').text('사용 불가능한 비밀번호입니다.');
			$('#adminPwResult').val('N');
			$('#adminPwChk').attr('readonly', true);
		} else {
			$('.error').css('color', '#6b7');
		    $('.error').text('사용 가능한 비밀번호입니다.');
			$('#adminPwResult').val('Y');
			$('#adminPwChk').attr('readonly', false);
		}
	});
	
	// 비밀번호 확인 입력 이벤트
	$('#adminPwChk').on('keyup', function() {
		var adminPwOrg = $('#adminPw').val();
		var adminPwResult = $('#adminPwResult').val();
		var adminPw = $(this).val();
		$('#adminPwChkResult').val('N');
		
		if ( adminPwResult === 'N' ) {
			return;
		}

		if (adminPw.length > 14) {
		    adminPw = adminPw.substring(0, 14);
		    $(this).val(adminPw);
		}
		
		if( adminPwOrg !== adminPw) {
			$('.error').css('color', '#e53935');
			$('.error').text('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
			$('#adminPwChkResult').val('N');
		} else {
			$('.error').css('color', '#6b7');
			$('.error').text('비밀번호가 일치합니다. 설정 진행해주세요.');
			$('#adminPwChkResult').val('Y');
		}	
	});
	
	// 등록 이벤트
	$('#adminRegBtn').on('click', function() {
		var adminPwResult = $('#adminPwResult').val('');
		var adminPwChkResult = $('#adminPwChkResult').val('');
		
		if ( adminPwResult === 'Y' && adminPwChkResult === 'N' ) {
			
		}
		
	});

	
	// 취소 버튼 이벤트
	$('#adminCancelBtn').on('click', function() {
		goToUri('/admin/login.do');
	});
	
});