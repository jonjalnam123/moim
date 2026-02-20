/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminLogin 스크립트
 */
$(function(){
   // 초기: 내용 접기
   $('.terms-item-body').hide();

   // 내용보기 토글
   $('.terms-toggle').on('click', function(){
     var $item = $(this).closest('.terms-item');
     var $body = $item.find('.terms-item-body');
     var isOpen = $body.is(':visible');
     $body.toggle(!isOpen);
     $(this).text(isOpen ? '내용보기' : '닫기');
   });

   // 전체 동의
   $('#agreeAll').on('change', function(){
     var checked = this.checked;
     $('.agree-item').prop('checked', checked).trigger('change');
   });

   // 필수 체크 상태에 따라 버튼 활성화
   function refreshNextBtn(){
     var ok = $('.agree-item.req:checked').length === $('.agree-item.req').length;
     $('#nextBtn').prop('disabled', !ok);
   }

   // 개별 체크 -> 전체동의 상태 동기화 + 버튼 갱신
   $('.agree-item').on('change', function(){
     var all = $('.agree-item').length;
     var checked = $('.agree-item:checked').length;
     $('#agreeAll').prop('checked', all === checked);
     refreshNextBtn();
   });

   refreshNextBtn();
 });