/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminUser 스크립트
 */

$(function () {
	// 리사이즈 함수
	initSplitResizeJQ(); 

	// 페이징 이벤트 [S]
	var searchGb = 	$('#searchGbParam').val();
	var pageNum = 	$('#pageNumParam').val();
	setPagingParam(searchGb, pageNum);
	
	// 페이징 버튼 이벤트
	$(".p").click(function() {
		var n= $(this).attr("data-list-pn");
		$("#pageNum").val(n);
		$('#adminUserSearchForm').submit(); 
	});
	
	// 조회 버튼 이벤트
	$('#btnSearch').on('click', function() {
		$('#adminUserSearchForm').submit(); 
	});
	
	// 엔터키 이벤트
	$('#searchTxt').on('keydown', function(e) {
	    if (e.key === 'Enter') {
	        $('#btnSearch').trriger('click');
	    }
	});
	// 페이징 이벤트 [E]
	
	// 아이디 입력 이벤트 [S]
	$('#adminId').on('keyup', function() {
		$('.hint').hide();
		$('.error').hide();
		
		var adminIdVal = $(this).val();
		var adminId = onlyEngNum(adminIdVal);
		$('#adminId').val(adminId);
	});
	// 아이디 입력 이벤트 [E]
	
	// 이름 입력 이벤트 [S]
	$('#adminNm').on('keyup', function() {
		var adminNmVal = $(this).val();
		var adminNm = onlyKorEng(adminNmVal);
		$('#adminNm').val(adminNm);
	});
	// 이름 입력 이벤트 [E]
	
	// 아이디 중복체크 [S]
	$('#adminIdChkBtn').on('click', function() {

		var adminId = $('#adminId').val();
		
		if ( isEmptyMsg(adminId, '아이디' + dataEmpty) ) {
			return;
		}

		var tableNm = 'tb_admin_info';
		var url = '/admin/uniqueDupliChk.do';
		var params = {
			uniqueKey : adminId
		  , tableNm : tableNm
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = data.result;
			$('#adminIdChk').val(result);
			if ( result === 'Y' ) {
				$('.hint').show();
				$('.error').hide();
			} else {
				$('.hint').hide();
				$('.error').show();
			}
		});
	});
	// 아이디 중복체크 [E]
	
	// 우편번호 찾기 이벤트
	$('#getPostCode').on('click', function(){
		var postId =  $('#adminPostCd').attr('id');
		var adId = $('#adminAddress').attr('id');
		execDaumPostcode( postId, adId )
	})
	
	// 그리드 더블클릭 이벤트
	var pendingTeamCd =  '';
	var pendingPositionCd = '';
	$('.adminInfoTr').on('dblclick', function() {
		
		$('#btnUpd').show();
		$('#btnDel').show();
		$('#btnReg').hide();
		$('#btnNew').show();
		
		$('#adminIdChkBtn').hide();
		$('#adminId').attr('readonly', true);
		
		$('.hint').hide();
		$('.error').hide();
		
		var rowKey = $(this).data('rowkey');
		var adminNo = $(this).data('no');
		var adminId = $(this).data('id');
		
	  	var url = '/admin/userInfo.do';
	  	var params = { 
			adminNo: adminNo
		  , adminId : adminId
		 };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
			
			var adminInfo = data.adminInfo;
			
			if ( !isEmpty(adminInfo) ) {
				
				var adminNo = adminInfo.adminNo
				var adminId = adminInfo.adminId
				var adminNm = adminInfo.adminNm
				var adminPh = adminInfo.adminPh
				var adminPostCd = adminInfo.adminPostCd
				var adminAddress = adminInfo.adminAddress
				var adminDAddress = adminInfo.adminDAddress
				var adminDeptCd = adminInfo.adminDeptCd
				var adminTeamCd = adminInfo.adminTeamCd
				var adminPositionCd = adminInfo.adminPositionCd
				var adminCn = adminInfo.adminCn
				var adminGender = adminInfo.adminGender
				var adminGradeCd = adminInfo.adminGradeCd

				$('#adminNo').val(adminNo);
				
				$('#adminId').val(adminId);
				$('#adminIdOrg').val(adminId);
				
				$('#adminNm').val(adminNm);
				$('#adminPh').val(adminPh);
				$('#adminPostCd').val(adminPostCd);
				$('#adminAddress').val(adminAddress);
				$('#adminDAddress').val(adminDAddress);
				
				$('#adminDeptCd').val(adminDeptCd).trigger('change');
				pendingTeamCd = adminTeamCd || '';
				pendingPositionCd = adminPositionCd || '';

				$('#adminGradeCd').val(adminGradeCd).trigger('change');

				$('input[name="adminGender"][value="' + adminGender + '"]').prop('checked', true);
				$('#adminCn').val(adminCn);
				
			} else {
				goToUri('/admin/error.do');
			}
		});
	});
	
	// 부서 입력 이벤트
	$('#adminDeptCd').on('change', function() {
  		var adminUnitId = $(this).find('option:selected').data('id');
	  	var url = '/admin/teamSelect.do';
	  	var params = { adminUnitId: adminUnitId };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
	    	var unitTeamData = data || [];
	    	var team = $('#adminTeamCd');

	    	team.empty().append('<option value="">선택</option>');

		    if (!isEmptyArr(unitTeamData)) {
				
				$('#adminTeamDiv').show();
				
	      		var html = '';
	      		$.each(unitTeamData, function(idx, val){
		        	var id = val.adminUnitId;
		        	var nm = val.adminUnitNm;
		        	var cd = val.adminUnitCd;
	
		        	html += `<option value="${cd}" data-id="${id}">${nm}</option>`;
		      	});
				
				team.append(html);
				
				if (pendingTeamCd != null) {
				  team.val(pendingTeamCd).trigger('change');
				} else {
				  team.trigger('change');
				}
				
	    	} else {
				
				$('#adminTeamDiv').hide();
				$('#adminPositionDiv').hide();
				
				pendingTeamCd = null;
				pendingPositionCd = null;
				
			}
			
  		});
	});
	
	// 팀 입력 이벤트
	$('#adminTeamCd').on('change', function() {
  		var adminUnitId = $(this).find('option:selected').data('id');
	  	var url = '/admin/posotionSelect.do';
	  	var params = { adminUnitId: adminUnitId };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
	    	var unitPositionData = data || [];
	    	var position = $('#adminPositionCd');

	    	position.empty().append('<option value="">선택</option>');

		    if (!isEmptyArr(unitPositionData)) {
				
				$('#adminPositionDiv').show();
				
	      		var html = '';
	      		$.each(unitPositionData, function(idx, val){
		        	var id = val.adminUnitId;
		        	var nm = val.adminUnitNm;
		        	var cd = val.adminUnitCd;
	
		        	html += `<option value="${cd}" data-id="${id}">${nm}</option>`;
		      	});
				
				position.append(html);
				
				if (pendingPositionCd != null) {
				  position.val(pendingPositionCd).trigger('change');
				} else {
				  position.trigger('change');
				}
				
	    	} else {
				$('#adminPositionDiv').hide();
			}
		
			pendingTeamCd = null;
			pendingPositionCd = null;
			
  		});
	});
	
	// 신규 버튼 이벤트
	$('#btnNew').on('click', function() {
		
		$('#btnUpd').hide();
		$('#btnDel').hide();
		$('#btnReg').show();
		$('#btnNew').hide();
		$('#adminIdChkBtn').show();
		
		$('#adminId').attr('readonly', false);
		
		$('#adminNo').val('');
		$('#adminId').val('');
		$('#adminNm').val('');
		$('#adminPh').val('');
		$('#adminPostCd').val('');
		$('#adminAddress').val('');
		$('#adminDAddress').val('');
		$('#adminDeptCd').val('').trigger('change');
		$('#adminTeamCd').val('');
		$('#adminPositionCd').val('');
		$('#adminGradeCd').val('');
		$('input[name="adminGender"][value="M"]').prop('checked', true);
		$('#adminCn').val('');
		$('#adminIdChk').val('');
		
	});
	
	// 관리자 등록, 수정 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
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
		var adminIdChk = $('#adminIdChk').val();
		
		if ( isEmptyMsg(adminId, '아이디' + dataEmpty) ) {	
			return;
		}
		
		if ( btnVal === 'I' ) {
			if ( adminIdChk === '' ) {
				alert('아이디' + dataDupliChk);
				return;
			}
			
			if ( adminIdChk === 'N') {
				alert('아이디' + dataChk);
				return;
			}
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
			    adminNo : adminNo
			  , adminId : adminId
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
				goToUri('/admin/error.do');
			}
		});
	});
	
	// 메뉴 삭제 이벤트
	$('#btnDel').on('click', function() {
		var adminNo = $('#adminNo').val();
		var adminId = $('#adminId').val();
		var adminNm = $('#adminNm').val();
		
		if ( isEmptyMsg(adminId, delDataChk) ) {
			return;
		}
		
		if ( !confirm(adminNm + ' 관리자' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/userDel.do';
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
});

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