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
		var btnVal = $(this).val();
		var url = '';
		
		var adminNo = $('#adminNo').val();
		var adminId = $('#adminId').val();
		var adminNm = $('#adminNm').val();
		var adminPh = $('#adminPh').val();
		var adminPostCd = $('#adminPostCd').val();
		var adminAddress = $('#adminAddress').val();
		var adminDAddress = $('#adminDAddress').val();
		var adminDeptCd = $('#adminDeptCd').val();
		var adminTeamCd = $('#adminTeamCd').val();
		var adminPositionCd = $('#adminPositionCd').val();
		var adminGradeCd = $('#adminGradeCd').val();
		var adminGender =  $('input[name="adminGender"]:checked').val();
		var adminCn =  $('#adminCn').val();
		
		if ( isEmptyMsg(adminId, '아이디' + dataEmpty) ) {	
			return;
		}

		if ( isEmptyMsg(adminNm, '이름' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminPh, '핸드폰' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminPostCd, '우편번호' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminAddress, '주소' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminGradeCd, '권한등급' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminGender, '성별' + dataEmpty) ) {
			return;
		}
		
		if ( btnVal === 'I' ) {
			if ( !confirm('관리자' + regProcConfirm) ) {
				return;
			}
			url = '/admin/userReg.do';
		} else {
			if ( !confirm('관리자' + updProcConfirm) ) {
				return;
			}
			url = '/admin/userUpd.do';
		}

		var params = {
				adminId : adminId
			  , adminNm : adminNm
			  , adminPh : adminPh
			  , adminPostCd : adminPostCd
			  , adminAddress : adminAddress
			  , adminDAddress : adminDAddress
			  , adminDeptCd : adminDeptCd
			  , adminTeamCd : adminTeamCd
			  , adminPositionCd : adminPositionCd
			  , adminGradeCd : adminGradeCd
			  , adminGender : adminGender
			  , adminCn : adminCn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				window.location.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 메뉴 삭제 이벤트
	$('#btnDel').on('click', function() {
		var adminUnitId = $('#adminUnitId').val();
		var adminUnitPId = $('#adminUnitPId').val();
		
		if ( isEmptyMsg(adminUnitId, delDataChk) ) {
			return;
		}
		
		if ( !confirm('부서' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/unitDel.do';
		var params = {
				adminUnitId : adminUnitId
			  , adminUnitPId : adminUnitPId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				window.location.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
 
});