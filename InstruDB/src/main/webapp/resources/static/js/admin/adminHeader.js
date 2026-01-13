/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 헤더 스크립트
 */

$(function () {	
	$('#logOutBtn').on('click', function(){
		logOut();
	});
});

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
