/**
 * 작성자 : 최정석
 * 작성날짜 : 2026.02.18
 * 내용 : adminLoginPwSet 스크립트
 */
$(function () {
	
	// 현재 비밀번호 입력 포커스
	$('#adminPwNow').focus();

	// 공백입력 방지 이벤트
	$('#adminPwNow, #adminPw, #adminPwChk').on('input', function() {
	    var val = $(this).val();
	    $(this).val(removeSpace(val));
	});
	
	// 현재 비밀번호 입력 이벤트
	$('#adminPwNow').on('keyup', function() {
		
		var adminNo = $('#adminNo').val();
		var adminId = $('#adminId').val();
		var adminPw = $(this).val();
		$('#adminPwNowResult').val('N');
		
		if (adminPw.length > 14) {
		    adminPw = adminPw.substring(0, 14);
		    $(this).val(adminPw);
		}

		var url = '/admin/loginPwNowChk.do';
		var params = {
				adminNo : adminNo
			  , adminId : adminId
			  , adminPw : adminPw
		}
		var dataType = 'json'
		ajaxNoLoadingxStart(url, params, dataType, function(data) {
			var result = data.result;
			if ( result === 'Y' ) {
				$('#adminPwNowResult').val('Y');
				$('#adminPw').attr('readonly', false);
				
				$('.error').css('color', '#6b7');
				$('.error').text('현재 비밀번호와 일치합니다. 변경할 비밀번호를 입력해주세요.');
				
				$('#adminPwNow').attr('readonly', true);	
				
				$('#adminPw').focus();	
			} else {
				$('#adminPwNowResult').val('N');
				$('#adminPw').attr('readonly', true);
				
				$('.error').css('color', '#e53935');
				$('.error').text('현재 비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
			}
		});
		
	});
	
	// 비밀번호 입력 이벤트
	$('#adminPw').on('keyup', function() {
		var adminPw = $(this).val();
		$('#adminPwResult').val('N');
		$('#adminPwChk').val('');
		
		if (adminPw.length > 14) {
		    adminPw = adminPw.substring(0, 14);
		    $(this).val(adminPw);
		}

		if ( !validatePassword(adminPw) || adminPw.length !== 14 ) {
			$('#adminPwResult').val('N');
			$('#adminPwChk').attr('readonly', true);
			
			$('.error').css('color', '#e53935');
		    $('.error').text('특수문자 1개 이상 영어, 숫자만 14자리 입력');
		} else {
			$('#adminPwResult').val('Y');
			
			$('.error').css('color', '#6b7');
		    $('.error').text('사용 가능한 비밀번호입니다. 비밀번호 확인을 입력해주세요.');

			$('#adminPwChk').focus();
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
			$('#adminPwChkResult').val('N');
			
			$('.error').css('color', '#e53935');
			$('.error').text('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
		} else {
			$('#adminPwChkResult').val('Y');
			
			$('.error').css('color', '#6b7');
			$('.error').text('비밀번호가 일치합니다. 설정 진행해주세요.');
		}	
	});
	
	// 등록 이벤트
	$('#adminRegBtn').on('click', function() {
		var adminPwNowResult = $('#adminPwNowResult').val();
		var adminPwResult = $('#adminPwResult').val();
		var adminPwChkResult = $('#adminPwChkResult').val();
		
		if ( adminPwNowResult === 'Y' && adminPwResult === 'Y' && adminPwChkResult === 'Y' ) {
			
		} else {
			alert('비밀번호를 다시 확인해주세요.');
			return;
		}
		
	});
	
	// 번호확인 버튼 이벤트
	var count = 0;
	$('#adminShowPwBtn').on('click', function() {
		if ( count === 0 ) {
			$('#adminPwNow').attr('type', 'text');
			$('#adminPw').attr('type', 'text');
			$('#adminPwChk').attr('type', 'text');
			count++;
			return;
		}
		
		if ( count > 0 ) {
			$('#adminPwNow').attr('type', 'password');
			$('#adminPw').attr('type', 'password');
			$('#adminPwChk').attr('type', 'password');
			count = 0;
			return;
		}
	});

	
	// 취소 버튼 이벤트
	$('#adminCancelBtn').on('click', function() {
		goToUri('/admin/login.do');
	});
	
});