/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminLogin 스크립트
 */
$(function () {// Select2 스코프 적용 (네가 쓰던 옵션 그대로)
	 if ($.fn.select2) {
	   $('.form-select').select2({
	     placeholder: function(){ return $(this).data('placeholder') || '선택하세요'; },
	     allowClear: true,
	     width: 'resolve',
	     minimumResultsForSearch: Infinity,
	     containerCssClass: 'ez-s2',
	     selectionCssClass: 'ez-s2',
	     dropdownCssClass: 'ez-s2',
	     dropdownParent: $('.signup-card') // ✅ 이 폼 카드 안으로 드롭다운 고정
	   });
	 }
	
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
	
	 // 우편번호 찾기 버튼 이벤트는 여기서 연결 (카카오/다음 API 등)
	 $('#btnPost').on('click', function(){
	   // TODO: 우편번호 팝업/레이어 띄우고 결과를 #postCd, #addr1에 세팅
	   // 예시:
	   // $('#postCd').val('06236');
	   // $('#addr1').val('서울 강남구 ...');
	 });
});