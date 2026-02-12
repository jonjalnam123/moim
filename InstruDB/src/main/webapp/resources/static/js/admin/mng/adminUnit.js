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
	$('#adminUnitCd').on('keyup', function() {
		var adminUnitCdVal = $(this).val();
		var adminUnitCd = getEngUpperCase(adminUnitCdVal);
		$(this).val(adminUnitCd);
		
		var tableNm = 'tb_admin_unit_info';
		var url = '/admin/uniqueDupliChk.do';
		var params = {
			uniqueKey : adminUnitCd
		  , tableNm : tableNm
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = data.result;
			if ( result === 'Y' ) {
				$('#adminUnitCd').attr('required', false);
				$('.hint').show();
				$('.error').hide();
			} else {
				$('#adminUnitCd').attr('required', true);
				$('.hint').hide();
				$('.error').show();
			}
		});
	});
	
	// 코드 상세조회
	$('.unitTreeF, .unitTreeS, .unitTreeT').on('click', function() {
		
		$('#btnUpd').show();
		$('#btnDel').show();
		$('#btnReg').hide();
		$('#btnRef').show();
		
		$('.hint').hide();
		$('.error').hide();

		var adminUnitId = $(this).data('id');
		var adminUnitPId = $(this).data('pid');
		var adminUnitLvl = $(this).data('lv');
		
		if ( isEmptyMsg(adminUnitId, selectDataChk) ) {
			return;
		}
		
		if ( Number(adminUnitLvl) ===  2 ) {
			$('#btnNew').hide();
		} else {
			$('#btnNew').show();
		}

		var url = '/admin/unitSelect.do';
		var params = {
				adminUnitId : adminUnitId
			  , adminUnitPId : adminUnitPId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			
			var unitData = data.result;
			
			if ( !isEmpty(unitData) ) {
				
				var adminUnitId = unitData.adminUnitId
				var adminUnitNm = unitData.adminUnitNm
				var adminUnitPId = unitData.adminUnitPId
				var adminUnitPNm = unitData.adminUnitPNm
				var adminUnitCd = unitData.adminUnitCd
				var adminUnitLvl = unitData.adminUnitLvl
				var adminUnitSortNo = unitData.adminUnitSortNo
				var adminUnitUseYn = unitData.adminUnitUseYn
				var adminUnitCn = unitData.adminUnitCn

				$('#adminUnitId').val(adminUnitId);
				$('#adminUnitNm').val(adminUnitNm);
				$('#adminUnitPId').val(adminUnitPId);
				$('#adminUnitPNm').val(adminUnitPNm);
				
				$('#adminUnitCd').val(adminUnitCd);
				
				$('#adminUnitLvl').val(adminUnitLvl).trigger('change');
				$('#adminUnitSortNo').val(adminUnitSortNo);
				$('#adminUnitCn').val(adminUnitCn);
				$('input[name="adminUnitUseYn"][value="' + adminUnitUseYn + '"]').prop('checked', true);
				
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 초기화 버튼 이벤트
	$('#btnRef').on('click', function() {

		$('#btnReg').show();
		$('#btnNew').hide();
		$('#btnDel').hide();
		$('#btnUpd').hide();
		$('#btnRef').hide();
		$('.unitTreeF, .unitTreeS, .unitTreeT').removeClass('active');

		$('#adminUnitId').val('');
		$('#adminUnitNm').val('');
		$('#adminUnitPId').val('');
		$('#adminUnitPNm').val('');
		$('#adminUnitCd').val('');
		$('#adminUnitLvl').val('0').trigger('change');
		$('#adminUnitSortNo').val('');
		$('#adminUnitCn').val('');	
		
	});
	
	// 추가 버튼 이벤트
	$('#btnNew').on('click', function() {

		$('#btnReg').show();
		$('#btnNew').hide();
		$('#btnDel').hide();
		$('#btnUpd').hide();
		$('#btnRef').hide();
		
		var adminUnitId = $('#adminUnitId').val();
		var adminUnitNm = $('#adminUnitNm').val();
		var adminUnitLvl = $('#adminUnitLvl').val();
		
		$('#adminUnitId').val('');
		$('#adminUnitNm').val('');
	 	$('#adminUnitPId').val(adminUnitId);
		$('#adminUnitPNm').val(adminUnitNm);
		$('#adminUnitCd').val('');
		if ( Number(adminUnitLvl) === 0 ) {
			$('#adminUnitLvl').val('1').trigger('change');
		} else if (Number(adminUnitLvl) === 1) {
			$('#adminUnitLvl').val('2').trigger('change');
		} else if ( Number(adminUnitLvl) === 2) {
			$('#adminUnitLvl').val('2').trigger('change');
		}
		$('#adminUnitSortNo').val('');	
		$('#adminUnitCn').val('');	
		
	});
	
	// 부서 등록 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
		var btnVal = $(this).val();
		var url = '';
		
		var adminUnitId = $('#adminUnitId').val();
		var adminUnitNm = $('#adminUnitNm').val();
		var adminUnitPId = $('#adminUnitPId').val();
		
		var adminUnitCd = $('#adminUnitCd').val();

		var adminUnitLvl = $('#adminUnitLvl').val();
		var adminUnitSortNo = $('#adminUnitSortNo').val();
		var adminUnitCn = $('#adminUnitCn').val();
		var adminUnitUseYn = $('input[name="adminUnitUseYn"]:checked').val();
		
		
		if ( isEmptyMsg(adminUnitNm, '부서명' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminUnitCd, '부서코드' + dataEmpty) ) {
			return;
		}
		
		if ( isEmptyMsg(adminUnitLvl, '레벨' + dataEmpty) ) {
			return;
		}

		if ( isEmptyMsg(adminUnitSortNo, '정렬순서' + dataEmpty) ) {
			return;
		}
		
		if ( btnVal === 'I' ) {
			if ( !confirm('부서' + regProcConfirm) ) {
				return;
			}
			url = '/admin/unitReg.do';
		} else {
			if ( !confirm('부서' + updProcConfirm) ) {
				return;
			}
			url = '/admin/unitUpd.do';
		}

		var params = {
				adminUnitId : adminUnitId
			  , adminUnitNm : adminUnitNm
			  , adminUnitPId : adminUnitPId
			  , adminUnitCd : adminUnitCd
			  , adminUnitLvl : adminUnitLvl
			  , adminUnitSortNo : adminUnitSortNo
			  , adminUnitCn : adminUnitCn
			  , adminUnitUseYn : adminUnitUseYn
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