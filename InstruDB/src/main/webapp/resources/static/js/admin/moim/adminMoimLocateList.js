/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminUser 스크립트
 */

$(function () {
	
	// 리사이즈 함수
	initSplitResizeJQ(); 
	
	// 그리드 열 사이즈 조절 함수
	makeTableResizable('.table-grid', 'adminMoimLocateList.tableGrid.widths');

	// 페이징 이벤트 [S]
	var searchGb = 	$('#searchGbParam').val();
	var pageNum = 	$('#pageNumParam').val();
	setPagingParam(searchGb, pageNum);
	
	// 페이징 버튼 이벤트
	$(".p").click(function() {
		var n= $(this).attr("data-list-pn");
		$("#pageNum").val(n);
		$('#adminMoimLocateListForm').submit(); 
	});
	
	// 조회 버튼 이벤트
	$('#btnSearch').on('click', function() {
		$('#adminMoimLocateListForm').submit(); 
	});
	
	// 엔터키 이벤트
	$('#searchTxt').on('keydown', function(e) {
	    if (e.key === 'Enter') {
	        $('#btnSearch').trriger('click');
	    }
	});
	// 페이징 이벤트 [E]
	
	// 우편번호 찾기 이벤트
	$('#getPostCode').on('click', function(){
		var postId =  $('#locatePostCd').attr('id');
		var adId = $('#locateAddress').attr('id');
		execDaumPostcode( postId, adId )
	})
	
	// 성별 선택 이벤트
	$('.gender-check').on('change', function () {
	    if ($(this).is(':checked')) {
	        $('.gender-check').not(this).prop('checked', false);
	    }
	});
	
	// 그리드 더블클릭 이벤트
	var pendingTeamCd =  '';
	var pendingPositionCd = '';
	$('.adminMoimLocateTr').on('dblclick', function() {
		
		// ✅ 선택 행 배경 고정
		$('.adminMoimLocateTr').removeClass('is-selected');
		$(this).addClass('is-selected');
		
		$('#btnUpd').show();
		$('#btnDel').show();
		$('#btnReg').hide();
		$('#btnNew').show();
		
		var locateId = $(this).data('id');
		
	  	var url = '/admin/moim/moimLocateInfo.do';
	  	var params = { 
			locateId: locateId
		 };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
			
			var moimLocateInfo = data.moimLocateInfo;
			
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
				var adminEmail = adminInfo.adminEmail

				$('#adminNo').val(adminNo);
				
				$('#adminId').val(adminId);
				$('#adminIdOrg').val(adminId);
				
				$('#adminNm').val(adminNm);
				$('#adminEmail').val(adminEmail);

				$('#adminPh').val(adminPh);
				$('#adminPostCd').val(adminPostCd);
				$('#adminAddress').val(adminAddress);
				$('#adminDAddress').val(adminDAddress);
				
				$('#adminDeptCd').val(adminDeptCd).trigger('change');
				pendingTeamCd = adminTeamCd || '';
				pendingPositionCd = adminPositionCd || '';

				$('#adminGradeCd').val(adminGradeCd).trigger('change');

				setGender(adminGender);
				$('#adminCn').val(adminCn);
				
			} else {
				goToUriAdminError();
			}
		});
	});
	
	// 신규 버튼 이벤트
	$('#btnNew').on('click', function() {
		
		$('.adminMoimLocateTr').removeClass('is-selected');
		
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
		
		var locateNm = $('#locateNm').val();
		var locatePostCd = $('#locatePostCd').val();
		var locateAddress = $('#locateAddress').val();
		var locateDAddress = $('#locateDAddress').val();
		var locateUseYn =  $('input[name="locateUseYn"]:checked').val();		var adminAddress = $('#adminAddress').val();
		var locateCn =  $('#locateCn').val();

		if ( isEmptyMsg(locateNm, '장소명' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(locatePostCd, '우편번호' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(locateAddress, '주소' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(locateDAddress, '상세주소' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(locateUseYn, '사용여부' + dataEmpty) ) {	
			return;
		}

		if ( btnVal === 'I' ) {
			if ( !confirm('모임장소' + regProcConfirm) ) {
				return;
			}
			url = '/admin/moim/moimLocateListReg.do';
		} else {
			if ( !confirm('모임장소' + updProcConfirm) ) {
				return;
			}
			url = '/admin/moim/moimLocateListUpd.do';
		}

		var params = {
			    locateNm : locateNm
			  , locatePostCd : locatePostCd
			  , locateAddress : locateAddress
			  , locateDAddress : locateDAddress
			  , locateUseYn : locateUseYn
			  , locateCn : locateCn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert(btnVal === 'I' ? '모임장소' + regSuccess : '모임장소' + updSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
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
				alert('사원' + delSuccess);
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