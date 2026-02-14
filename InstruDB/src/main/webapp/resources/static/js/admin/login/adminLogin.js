/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 회원가입 페이지 스크립트
 */
$(document).ready(function(){
	
	// 엔터키 이벤트
	$('#adminPw').on('keyup', function(key) {
		if (key.keyCode == 13) {
			$('#adminLoginBtn').trigger('click');
		} 
	 });
	 
	// 관리자 로그인 버튼 이벤트
	$('#adminLoginBtn').on('click', function() {
		var adminId = $("#adminId").val();
		var adminPw = $("#adminPw").val();
		
		var url = '/admin/loginProc.do';
		var params = {
				adminId : adminId
		  	  , adminPw : adminPw
		}
		var dataType = 'json'
	 	ajaxStart(url, params, dataType, function(data) {
	        if (data.result === 'SUCCESS' ) { // 로그인 성공
	        	window.location.href = '/admin/main.do'
	        } else if (data.result === 'DISTINCT'){ // 로그인 중복.
				if ( confirm(loginDistinct) ) {
					
				} 
	        } else if ( data.result === 'EMPTY' ) { // 가입된 정보 없음.
				alert(loginEmpty);
				return;
			} else if ( data.result === 'NOTACCEPT' ) { // 가입 미승인 상태
				alert(loginNotAccept);
				return;
			} else if ( data.result === 'REJECT' ) {
				// TODO 추후에 반려 조회 팝업이 떠야할 것 같음.
				alert(loginReject);
				return;
			} else { // 아이디, 비밀번호 불일치
				alert(loginFailMsg);
				return;
			}
		});
	})
});