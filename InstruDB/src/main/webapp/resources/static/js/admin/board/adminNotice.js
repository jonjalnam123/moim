/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminNotice 스크립트
 */

$(function () {
	
	// 리사이즈 함수
	initSplitResizeJQ(); 
	
	// 그리드 열 사이즈 조절 함수
	makeTableResizable('.table-grid', 'adminNotice.tableGrid.widths');

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
	
	// 공지사항ID 생성 [S]
	$('#getNoticeId').on('click', function() {
		var url = '/admin/uniqueId.do';
		var params = {}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = data.result;
			if ( !isEmpty(result) ) {
				$('#noticeId').val(result);
				$('#regId').val($('#adminId').val());
				$('#regDt').val($('#nowDate').val());
			} else {
				$('#noticeId').val('');
			}
		});
	});
	// 공지사항ID 생성 [E]
	
	// 토글 변경 [S]
	syncNoticeLimitUI();

	$('#noticeLimitYn').on('change', syncNoticeLimitUI);
	// 토글 변경 [E]

	// 그리드 더블클릭 이벤트
	var pendingTeamCd =  '';
	var pendingPositionCd = '';
	$('.adminNoticeInfoTr').on('dblclick', function() {
		
		// ✅ 선택 행 배경 고정
		$('.adminNoticeInfoTr').removeClass('is-selected');
		$(this).addClass('is-selected');
		
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
				goToUri('/admin/error.do');
			}
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

	// 파일 선택 버튼
	$(document).on('click', '#btnPickFiles', function () {
	  $('#adminFiles').trigger('click');
	});

	// 파일 선택 변경
	$(document).on('change', '#adminFiles', function () {
	  refreshNewFilesUI();
	});

	// 선택 파일 제거
	$(document).on('click', '.btnNewFileRemove', function () {
	  const idx = parseInt($(this).closest('.attach-item').attr('data-new-idx'), 10);
	  if (!isNaN(idx)) removeNewFileByIndex(idx);
	});

});


/*******************************
* FuntionNm : formatBytes
* Date : 2026.02.15
* Author : CJS
* Description : 첨부파일 파일 크기 계산
* PARAM : bytes
********************************/
function formatBytes(bytes) {
	if (!bytes && bytes !== 0) return '';
	const units = ['B','KB','MB','GB','TB'];
	let i = 0, n = bytes;
	while (n >= 1024 && i < units.length - 1) { n /= 1024; i++; }
	return (i === 0 ? n : n.toFixed(1)) + ' ' + units[i];
}

/*******************************
* FuntionNm : refreshNewFilesUI
* Date : 2026.02.15
* Author : CJS
* Description : 첨부파일 첨부시 추가
* PARAM :
********************************/
function refreshNewFilesUI() {
	const input = document.getElementById('adminFiles');
  	const files = input.files ? Array.from(input.files) : [];

  	const $list = $('#newFileList');
  	$list.empty();

  	if (files.length === 0) {
    	$('#adminFilesSummary').text('선택된 파일 없음');
    	return;
  	}

  	$('#adminFilesSummary').text('선택됨: ' + files.length + '개');

	files.forEach((f, idx) => {
    	$list.append(`
	      <li class="attach-item" data-new-idx="${idx}">
	        <div class="attach-left">
	          <div class="attach-name" title="${f.name}">${f.name}</div>
	          <div class="attach-meta">${formatBytes(f.size)}</div>
	        </div>
	        <div class="attach-actions">
	          <button type="button" class="btn-mini remove btnNewFileRemove">제거</button>
	        </div>
	      </li>
    	`);
  	});
}

/*******************************
* FuntionNm : syncNoticeLimitUI
* Date : 2026.02.15
* Author : CJS
* Description : 기한설정 토글 기능
* PARAM : 
********************************/
function syncNoticeLimitUI() {
	var on = $('#noticeLimitYn').is(':checked');

  	$('#notcieStrDt, #notcieEndDt').prop('disabled', !on);

 	// 필수표시(별표) 토글
  	$('#lblNotcieStrDt, #lblNotcieEndDt').toggleClass('required', on);

  	// OFF면 값 비움(원치 않으면 제거)
  	if (!on) {
    	$('#notcieStrDt, #notcieEndDt').val('');
  	}
}

/*******************************
* FuntionNm : removeNewFileByIndex
* Date : 2026.02.15
* Author : CJS
* Description : 첨부파일 제거
* PARAM : removeIdx
********************************/
function removeNewFileByIndex(removeIdx) {
	const input = document.getElementById('adminFiles');
  	const files = input.files ? Array.from(input.files) : [];
  	const dt = new DataTransfer();

	files.forEach((f, idx) => {
  		if (idx !== removeIdx) dt.items.add(f);
	});

  	input.files = dt.files;
 	refreshNewFilesUI();
}

/*******************************
* FuntionNm : clearNewFiles
* Date : 2026.02.15
* Author : CJS
* Description : 첨부파일 비우시
* PARAM : 
********************************/
function clearNewFiles() {
	$('#adminFiles').val('');
  	$('#newFileList').empty();
  	$('#adminFilesSummary').text('선택된 파일 없음');
}

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