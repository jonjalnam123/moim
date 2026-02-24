/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminLogin 스크립트
 */
$(function () {

	// 이메일 인증 버튼 이벤트
	var code = '';
	$('#getEamilChkBtn').click(function() {
		var adminEmail = $('#adminEmail').val();
		$('#adminEmailOrg').val(adminEmail);
		
		var url = '/admin/joinMailChk.do';
		var params = {
				adminEmail : adminEmail
		};
		var dataType = 'json';
	 	ajaxStart(url, params, dataType, function(data) {
			code = data.result;
			var resultCd = data.resultCd;
			if(  resultCd === '00' ) {
				$('#emailChkDiv').show();
				alert(joinMailChkResult);
			}
		});
	});

	// 이메일 인증 코드 이벤트
	$('#emailChk').on('keyup', function () {
		$(this).val(checkNum('mailCheckInput'));
		
		var inputCode = $(this).val();
		var resultMsg = $('#mailCheckWarn');
		
		if ( !isEmpty(inputCode) ) {
			resultMsg.html('');
			return;
		} else {
			if(inputCode === code){
				resultMsg.html('✅ 인증번호가 일치합니다.');
				resultMsg.css('color','green');
				$('#mailCheckBtn').attr('disabled', true); 
				$('#mailCheckInput').attr('readonly', true);
				$('#userEmail1').attr('readonly', true);
				$('#userEmail2').attr('readonly', true);
				$('#userEmail2').attr('onFocus', 'this.initialSelect = this.selectedIndex');
		        $('#userEmail2').attr('onChange', 'this.selectedIndex = this.initialSelect');
		        $('#mailCheck').val('1');
			}else{
				resultMsg.html('❌ 인증번호가 불일치 합니다. 다시 확인해주세요!.');
				resultMsg.css('color','red');
				$('#mailCheck').val('0');
			}	
		}
	});
	
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