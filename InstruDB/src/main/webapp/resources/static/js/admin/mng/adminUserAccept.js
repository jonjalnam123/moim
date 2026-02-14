/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminUserAccept 스크립트
 */
$(function () {
	
	initSplitResizeJQ(); 
	
	// 그리드 더블클릭 이벤트
	$('.adminInfoTr').on('dblclick', function() {
		
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
				$('#adminPh').val(adminPh);
				$('#adminPostCd').val(adminPostCd);
				$('#adminAddress').val(adminAddress);
				$('#adminDAddress').val(adminDAddress);
				$('#adminRegAccept').val(adminRegAccept).trigger('change');
				$('input[name="adminGender"][value="' + adminGender + '"]').prop('checked', true);
				$('#adminRejectCn').val(adminRejectCn);
				
			} else {
				goToUri('/admin/error.do');
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
				window.location.reload();
			} else {
				goToUri('/admin/error.do');
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
				window.location.reload();
			} else {			
				goToUri('/admin/error.do');
			}
		});
	});
 
});