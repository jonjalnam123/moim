/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminUser 스크립트
 */

$(function () {
	
	// 리사이즈 함수
	initSplitResizeJQ(); 
	
	// 그리드 열 사이즈 조절 함수
	makeTableResizable('.table-grid', 'adminUser.tableGrid.widths');

	// 페이징 이벤트 [S]
	var searchGb = 	$('#searchGbParam').val();
	var pageNum = 	$('#pageNumParam').val();
	setPagingParam(searchGb, pageNum);
	
	// 페이징 버튼 이벤트
	$(".p").click(function() {
		var n= $(this).attr("data-list-pn");
		$("#pageNum").val(n);
		$('#adminMoimListSearchForm').submit(); 
	});
	
	// 조회 버튼 이벤트
	$('#btnSearch').on('click', function() {
		$('#adminMoimListSearchForm').submit(); 
	});
	
	// 엔터키 이벤트
	$('#searchTxt').on('keydown', function(e) {
	    if (e.key === 'Enter') {
	        $('#btnSearch').trriger('click');
	    }
	});
	// 페이징 이벤트 [E]
	
	// 그리드 더블클릭 이벤트
	$('.adminMoimTr').on('dblclick', function() {
		
		// ✅ 선택 행 배경 고정
		$('.adminMoimTr').removeClass('is-selected');
		$(this).addClass('is-selected');
		
		$('#btnUpd').show();
		$('#btnDel').show();
		$('#btnReg').hide();
		$('#btnNew').show();
		
		var moimId = $(this).data('id');
		
	  	var url = '/admin/moim/moimListInfo.do';
	  	var params = { 
			moimId: moimId
		 };
	  	var dataType = 'json';

		ajaxStart(url, params, dataType, function(data) {
			
			var moimInfo = data.moimInfo;
			
			if ( !isEmpty(moimInfo) ) {
				
				var moimId = moimInfo.moimId
				var moimTitle = moimInfo.moimTitle
				var moimDt = moimInfo.moimDt
				var moimMaxCnt = moimInfo.moimMaxCnt
				var moimLocateId = moimInfo.moimLocateId
				var moimGb = moimInfo.moimGb
				var moimStatusYn = moimInfo.moimStatusYn
				var regDt = moimInfo.regDt

				$('#moimId').val(moimId);
				$('#moimTitle').val(moimTitle);
				$('#moimDt').val(moimDt);
				
				$('#moimMaxCnt').val(moimMaxCnt);
				$('#moimLocateId').val(moimLocateId).trigger('change');
				$('#moimGb').val(moimGb).trigger('change');
				$('#moimStatusYn').val(moimStatusYn).trigger('change');
				$('#regDt').val(regDt);
				
			} else {
				goToUriAdminError();
			}
		});
	});

	// 신규 버튼 이벤트
	$('#btnNew').on('click', function() {
		
		$('.adminMoimTr').removeClass('is-selected');
		
		$('#btnUpd').hide();
		$('#btnDel').hide();
		$('#btnReg').show();
		$('#btnNew').hide();

		$('#moimTitle').val('');
		$('#moimDt').val('');
		$('#moimMaxCnt').val('');
		$('#moimLocateId').val('').trigger('change');
		$('#moimGb').val('').trigger('change');
		$('#moimStatusYn').val('').trigger('moimStatusYn');
		$('#moimCn').val('');

	});
	
	// 등록, 수정 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
		var btnVal = $(this).val();
		var url = '';
		
		var moimId = $('#moimId').val();
		var moimTitle = $('#moimTitle').val();
		var moimDt = $('#moimDt').val();
		var moimMaxCnt = $('#moimMaxCnt').val();
		var moimLocateId = $('#moimLocateId').val();
		var moimGb = $('#moimGb').val();
		var moimStatusYn = $('#moimStatusYn').val();
		var moimCn = $('#moimCn').val();
		
		if ( isEmptyMsg(moimTitle, '모임명' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(moimDt, '일시' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(moimMaxCnt, '최대인원' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(moimLocateId, '장소' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(moimGb, '구분' + dataEmpty) ) {	
			return;
		}
		
		if ( isEmptyMsg(moimStatusYn, '진행상태' + dataEmpty) ) {	
			return;
		}
		
		if ( btnVal === 'I' ) {
			if ( !confirm('모임일정' + regProcConfirm) ) {
				return;
			}
			url = '/admin/moim/moimListReg.do';
		} else {
			if ( !confirm('모임일정' + updProcConfirm) ) {
				return;
			}
			url = '/admin/moim/moimListUpd.do';
		}

		var params = {
			    moimId : moimId
			  , moimTitle : moimTitle
			  , moimDt : moimDt
			  , moimMaxCnt : moimMaxCnt
			  , moimLocateId : moimLocateId
			  , moimGb : moimGb
			  , moimStatusYn : moimStatusYn
			  , moimCn : moimCn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert(btnVal === 'I' ? '모임일정' + regSuccess : '모임일정' + updSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
			}
		});
	});
	
	// 삭제 이벤트
	$('#btnDel').on('click', function() {
		var moimId = $('#moimId').val();
		
		if ( isEmptyMsg(moimId, delDataChk) ) {
			return;
		}
		
		if ( !confirm('모임일정' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/moimListUpd.do';
		var params = {
				moimId : moimId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert('모임일정' + delSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
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