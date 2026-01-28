/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 공통코드관리 스크립트
 */
$(document).ready(function() {
	
	// 트리 열기/닫기
	$(document).on('click', '.tree .tw', function(e){
	  e.preventDefault();
	  $(this).closest('li').toggleClass('open');
	});
	
	// 트리 항목 선택 시 활성 표시
	$(document).on('click', '.tree a', function(e){
	  e.preventDefault();
	  $('.tree a').removeClass('active');
	  $(this).addClass('active');
	});
	
	// 코드 상세조회
	$('.commTreeF, .commTreeS').on('click', function() {
		var commId = $(this).data('id');
		var commPId = $(this).data('pid');
		
		if ( isEmptyMsg(commId, selectDataChk) ) {
			return;
		}

		var url = '/admin/commSelect.do';
		var params = {
				commId : commId
			  , commPId : commPId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var commData = data.result;
			if ( !isEmpty(commData) ) {
				
				var commId = commData.commId
				var commPId = commData.commPId
				var commPNm = commData.commPNm
				var commGroupCd = commData.commGroupCd
				var commNm = commData.commNm
				var commUseYn = commData.commUseYn
				var commCn = commData.commCn
				var commSortNo = commData.commSortNo
				var commCd = commData.commCd
				var commLvl = commData.commLvl

				$('#commId').val(commId);
				$('#commPId').val(commPId);
				$('#commPNm').val(commPNm);
				$('#commGroupCd').val(commGroupCd);
				$('#commNm').val(commNm);
				$('#commSortNo').val(Number(commSortNo));
				$('#commLvl').val(Number(commLvl));
				$('#commCn').val(commCn);
				$('#commCd').val(commCd);
				$('input[name="commUseYn"][value="' + commUseYn + '"]').prop('checked', true);
				
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	$('#btnRef').on('click', function() {
		window.location.reload();
	});
	
	// 메뉴 등록 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
		var btnVal = $(this).val();
		var url = '';
		
		var commId = $('#commId').val();
		var commPId = $('#commPId').val();
		var commGroupCd = $('#commGroupCd').val();
		var commNm = $('#commNm').val();
		var commSortNo = $('#commSortNo').val();
		var commLvl = $('#commLvl').val();
		var commCn = $('#commCn').val();
		var commCd = $('#commCd').val();
		var commUseYn = $('input[name="commUseYn"]:checked').val();
		
		// 2레벨 진행시
		if ( isEmpty(commPId) && Number(commLvl) !== 0 ) {
			commPId = commId;
		}
		
		if ( btnVal === 'I' ) {
			if ( !confirm('코드' + regProcConfirm) ) {
				return;
			}
			url = '/admin/commReg.do';
		} else {
			if ( !confirm('코드' + updProcConfirm) ) {
				return;
			}
			url = '/admin/commUpd.do';
		}

		var params = {
				commId : commId
			  , commPId : commPId
			  , commGroupCd : commGroupCd
			  , commNm : commNm
			  , commSortNo : commSortNo
			  , commLvl : commLvl
			  , commCn : commCn
			  , commCd : commCd
			  , commUseYn : commUseYn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result === 1) {
				window.location.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 메뉴 삭제 이벤트
	$('#btnDel').on('click', function() {
		var commId = $('#commId').val();
		
		if ( isEmptyMsg(commId, delDataChk) ) {
			return;
		}
		
		if ( !confirm('코드' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/commDel.do';
		var params = {
				commId : commId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result === 1) {
				window.location.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	 
});