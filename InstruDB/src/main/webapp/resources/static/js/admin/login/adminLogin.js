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
	        if (data.result === 'Y' ) {
	        	window.location.href = '/admin/main.do'
	        } else if (data.result === 'D'){
				if ( confirm(loginDistinct) ) {
					
				} 
	        } else {
				alert(loginFailMsg);
				return;
			}
		});
	})
});