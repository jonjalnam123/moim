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
	
	// 공통코드 중복 체크
	$('#commCd').on('keyup', function() {
		var commCd = $(this).val();
		var tableNm = 'tb_common_info';
		var url = '/admin/uniqueDupliChk.do';
		var params = {
			uniqueKey : commCd
		  , tableNm : tableNm
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = data.result;
			if ( result === 'Y' ) {
				$('#commCd').attr('required', false);
				$('.hint').show();
				$('.error').hide();
			} else {
				$('#commCd').attr('required', true);
				$('.hint').hide();
				$('.error').show();
			}
		});
	});
	
	// 코드 상세조회
	$('.commTreeF, .commTreeS').on('click', function() {
		
		$('#btnUpd').show();
		$('#btnDel').show();
		$('#btnReg').hide();
		$('#btnRef').show();
		
		var commId = $(this).data('id');
		var commPId = $(this).data('pid');
		var commLvl = $(this).data('lv');
		
		if ( isEmptyMsg(commId, selectDataChk) ) {
			return;
		}
		
		if ( Number(commLvl) ===  1 ) {
			$('#btnNew').hide();
		} else {
			$('#btnNew').show();
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
				$('#commGroupCd').attr('data-nm', commGroupCd);
				$('#commNm').val(commNm);
				$('#commSortNo').val(commSortNo);
				$('#commLvl').val(commLvl).trigger('change');
				$('#commCn').val(commCn);
				$('#commCd').val(commCd);
				$('input[name="commUseYn"][value="' + commUseYn + '"]').prop('checked', true);
				
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 추가 버튼 이벤트
	$('#btnNew').on('click', function() {

		$('#btnReg').show();
		$('#btnNew').hide();
		$('#btnDel').hide();
		$('#btnUpd').hide();
		$('#btnRef').show();
		
		var commId = $('#commId').val();
		var commNm = $('#commNm').val();
		var commLvl = $('#commLvl').val();
		
		$('#commId').val('');
		$('#commNm').val('');
	 	$('#commPId').val(commId);
		$('#commPNm').val(commNm);
		$('#commCd').val('');
		$('#commGroupCd').val('');
		if ( Number(commLvl) === 0 ) {
			$('#commLvl').val('1').trigger('change');
		} else if ( Number(commLvl) === 1 ) {
			$('#commLvl').val('1').trigger('change');
		}
		$('#commSortNo').val('');
		$('#commCn').val('');	
		
	});
	
	// 초기화 버튼 이벤트
	$('#btnRef').on('click', function() {

		$('#btnReg').show();
		$('#btnNew').hide();
		$('#btnDel').hide();
		$('#btnUpd').hide();
		$('#btnRef').hide();
		$('.commTreeS, .commTreeF').removeClass('active');

		$('#commId').val('');
		$('#commNm').val('');
		$('#commPId').val('');
		$('#commPNm').val('');
		$('#commCd').val('');
		$('#commGroupCd').val('');
		$('#commLvl').val('0').trigger('change');
		$('#commSortNo').val('');
		$('#commCn').val('');	
		
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
		var commCd = $('#commCd').val().toUpperCase();
		var commUseYn = $('input[name="commUseYn"]:checked').val();
		
		if ( btnVal === 'I' ) {
			
			if ( isEmptyMsg(commNm, '코드명' + dataEmpty) ) {
				return;
			}
			
			if ( isEmptyMsg(commGroupCd, '공통그룹' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(commLvl, '레벨' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(commSortNo, '정렬순서' + dataEmpty) ) {
				return;
			}
			
			if ( !confirm('코드' + regProcConfirm) ) {
				return;
			}
			url = '/admin/commReg.do';
			
		} else {
			
			if ( isEmptyMsg(commNm, '코드명' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(commGroupCd, '공통그룹' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(commLvl, '레벨' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(commSortNo, '정렬순서' + dataEmpty) ) {
				return;
			}
			
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
		var commId = $('#commId').val();
		var commPId = $('#commPId').val();
		
		if ( isEmptyMsg(commId, delDataChk) ) {
			return;
		}
		
		if ( !confirm('코드' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/commDel.do';
		var params = {
				commId : commId
			  , commPId : commPId
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