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
	
	// 초기화 버튼 이벤트
	$('#btnReset').on('click', function() {
		$('#searchTxt').val('');
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
	
	// 그리드 더블클릭 이벤트
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
			
			if ( !isEmpty(moimLocateInfo) ) {
				
				var locateId = moimLocateInfo.locateId
				var locateNm = moimLocateInfo.locateNm
				var locatePostCd = moimLocateInfo.locatePostCd
				var locateAddress = moimLocateInfo.locateAddress
				var locateDAddress = moimLocateInfo.locateDAddress
				var locateCn = moimLocateInfo.locateCn
				var locateUseYn = moimLocateInfo.locateUseYn
				var locateDelYn = moimLocateInfo.locateDelYn

				$('#locateId').val(locateId);
				$('#locateNm').val(locateNm);
				$('#locatePostCd').val(locatePostCd);
				$('#locateAddress').val(locateAddress);
				$('#locateDAddress').val(locateDAddress);
				$('#locateCn').val(locateCn);
				$('#locateUseYn').val(locateUseYn).trigger('change');
				
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

		$('#locateId').val('');
		$('#locateNm').val('');
		$('#locatePostCd').val('');
		$('#locateAddress').val('');
		$('#locateDAddress').val('');
		$('input[name="locateUseYn"][value="Y"]').prop('checked', true);
		$('#locateCn').val('');
		
	});
	
	// 모임 등록, 수정 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
		var btnVal = $(this).val();
		var url = '';
		
		var locateId = $('#locateId').val();
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
			    locateId : locateId
			  , locateNm : locateNm
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
		var locateId = $('#locateId').val();
		var locateNm = $('#locateNm').val();
		
		if ( isEmptyMsg(locateId, delDataChk) ) {
			return;
		}
		
		if ( !confirm(locateNm + delProcConfirm) ) {
			return;
		}

		var url = '/admin/moim/moimLocateListDel.do';
		var params = {
				locateId : locateId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0) {
				alert('모임장소' + delSuccess);
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