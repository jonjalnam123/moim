/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminUserAccept 스크립트
 */
$(function () {
	
	initSplitResizeJQ(); 
	
	// 그리드 열 사이즈 조절 함수
	makeTableResizable('.table-grid', 'adminUserAccept.tableGrid.widths');
	
	// 페이징 이벤트 [S]
	var searchGb = 	$('#searchGbParam').val();
	var pageNum = 	$('#pageNumParam').val();
	setPagingParam(searchGb, pageNum);

	// 페이징 버튼 이벤트
	$(".p").on('click', function() {
		var n= $(this).attr("data-list-pn");
		$("#pageNum").val(n);
		$('#adminUserAcceptSearchForm').submit(); 
	});

	// 조회 버튼 이벤트
	$('#btnSearch').on('click', function() {
		$('#adminUserAcceptSearchForm').submit(); 
	});

	// 엔터키 이벤트
	$('#searchTxt').on('keydown', function(e) {
	    if (e.key === 'Enter') {
	        $('#btnSearch').trriger('click');
	    }
	});
	// 페이징 이벤트 [E]
	
	// 그리드 더블클릭 이벤트
	$('.adminInfoTr').on('dblclick', function() {
		
		// ✅ 선택 행 배경 고정
		$('.adminInfoTr').removeClass('is-selected');
		$(this).addClass('is-selected');
		
		var rowKey = $(this).data('rowkey');
		var adminNo = $(this).data('no');
		var adminId = $(this).data('id');
		
	  	var url = '/admin/userAcceptInfo.do';
	  	var params = { 
			adminNo: adminNo
		  , adminId : adminId
		 };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
			
			var adminAcceptInfo = data.adminAcceptInfo;
			
			if ( !isEmpty(adminAcceptInfo) ) {
				
				var adminNo = adminAcceptInfo.adminNo
				var adminId = adminAcceptInfo.adminId
				var adminNm = adminAcceptInfo.adminNm
				var adminEmail = adminAcceptInfo.adminEmail
				var adminPh = adminAcceptInfo.adminPh
				var adminPostCd = adminAcceptInfo.adminPostCd
				var adminAddress = adminAcceptInfo.adminAddress
				var adminDAddress = adminAcceptInfo.adminDAddress
				var adminGender = adminAcceptInfo.adminGender
				var adminRegAccept = adminAcceptInfo.adminRegAccept
				var adminRejectCn = adminAcceptInfo.adminRejectCn

				$('#adminNo').val(adminNo);
				$('#adminId').val(adminId);
				$('#adminNm').val(adminNm);
				$('#adminEmail').val(adminEmail);
				$('#adminPh').val(adminPh);
				$('#adminPostCd').val(adminPostCd);
				$('#adminAddress').val(adminAddress);
				$('#adminDAddress').val(adminDAddress);
				$('#adminRegAccept').val(adminRegAccept).trigger('change');
				setGender(adminGender);
				$('#adminRejectCn').val(adminRejectCn);
				
			} else {
				goToUriAdminError();
			}
		});
	});
	
	// 승인 버튼 이벤트
	$('#btnReg').on('click', function() {
		
		var adminNo = $('#adminNo').val();
		var adminId = $('#adminId').val();
		var adminNm = $('#adminNm').val();
		
		if ( !confirm(adminNm + acceptConfirm) ) {
			return;
		}
		
		var url  = '/admin/userAcceptUpd.do';
		var params = {
				adminNo : adminNo
			  , adminId : adminId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert(acceptSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
			}
		});
	});
	
	// 반려 버튼 이벤트
	$('#btnDel').on('click', function() {
		
		var adminNo = $('#adminNo').val();
		var adminId = $('#adminId').val();
		var adminNm = $('#adminNm').val();
		var adminRejectCn = $('#adminRejectCn').val();
		
		if ( isEmptyMsg(adminRejectCn, '반려사유' + dataEmpty)) {
			return;
		}
		
		if ( !confirm(adminNm + rejectConfirm) ) {
			return;
		}

		var url = '/admin/userAcceptDel.do';
		var params = {
				adminNo : adminNo
			  , adminId : adminId
			  , adminNm : adminNm
			  , adminRejectCn : adminRejectCn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert(rejectSuccess);
				window.location.reload();
			} else {			
				goToUriAdminError();
			}
		});
	});
});

/*******************************
* FuntionNm : setGender
* Date : 2026.02.15
* Author : CJS
* Description : 성별 셋팅 함수 (checkbox 단일 선택용)
* PARAM : adminGender : 성별 값
********************************/
function setGender(adminGender) {

  // 일단 전체 해제
  $('.gender-check').prop('checked', false);

  // 값이 없으면 끝
  if (!adminGender) return;

  // 해당 값만 체크 (M / F)
  var target = $('.gender-check[value="' + adminGender + '"]');
  target.prop('checked', true).trigger('change');
  
}

/*******************************
* FuntionNm : setPagingParam
* Date : 2026.02.15
* Author : CJS
* Description : 페이징 진행 후 페이징 데이터 세팅 함수
* PARAM : kind : 조회 조건, pageNum : 조회 페이지 번호
********************************/
function setPagingParam(searchGb, pageNum) {
	$(".s").each(function() {
		if( $(this).val() === searchGb ){
			$(this).prop("selected", true);
		}
	})
	
	$(".p").each(function() {
		if( $(this).attr("data-list-pn") === pageNum ){
			$(this).addClass('active');
		}
	})
};