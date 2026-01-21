/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 헤더 스크립트
 */

$(function () {	
	
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
			var url = '/admin/error.do';
			goToUri(url);
		}
	});
}



