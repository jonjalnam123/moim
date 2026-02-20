/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminLogin 스크립트
 */
$(function () {
	
	// 성별 체크 박스 이벤트
	$('.gender-check').on('change', function () {
  		if ($(this).is(':checked')) {
	    	$('.gender-check').not(this).prop('checked', false);
	  	}
	});
	
	 // 비밀번호 일치 체크(간단)
	 function checkPw(){
	   var p1 = $('#userPw').val();
	   var p2 = $('#userPw2').val();
	   if(!p2){
	     $('#pwMismatch').hide();
	     return true;
	   }
	   var ok = (p1 === p2);
	   $('#pwMismatch').toggle(!ok);
	   return ok;
	 }
	 $('#userPw, #userPw2').on('input', checkPw);
	 $('#pwMismatch').hide();
	
	 // 제출 시 비번 불일치면 막기
	 $('#joinBtn').on('click', function(e){
	   if(!checkPw()){
	     e.preventDefault();
	     $('#userPw2').focus();
	   }
	 });
	
	 // 우편번호 찾기 이벤트
	 $('#getPostCode').on('click', function(){
	 	var postId =  $('#adminPostCd').attr('id');
	 	var adId = $('#adminAddress').attr('id');
	 	execDaumPostcode( postId, adId )
	 })
	 
});