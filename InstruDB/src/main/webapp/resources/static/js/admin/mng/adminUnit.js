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
	$('.unitTreeF, .unitTreeS, .unitTreeT').on('click', function() {
		var adminUnitId = $(this).data('id');
		var adminUnitPId = $(this).data('pid');
		
		if ( isEmptyMsg(adminUnitId, selectDataChk) ) {
			return;
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
				$('#adminUnitNm').attr('data-nm', adminUnitNm);
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
	
	$('#btnRef').on('click', function() {
		window.location.reload();
	});
	
	// 메뉴 등록 이벤트
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
		
		// 2레벨 진행시
		if ( isEmpty(adminUnitPId) && Number(adminUnitLvl) === 1 ) {
			adminUnitPId = adminUnitId;
		}
		
		// 3레벨 진행시
		if ( !isEmpty(adminUnitPId) && Number(adminUnitLvl) === 2 ) {
			adminUnitPId = adminUnitId;
		}
		
		if ( btnVal === 'I' ) {
			
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

			var unitNm = $('#adminUnitNm').data('nm');
			if (unitNm === adminUnitNm) {
				alert(adminUnitNmChk);
				return;
			}
			
			if ( !confirm('부서' + regProcConfirm) ) {
				return;
			}
			
			url = '/admin/unitReg.do';
			
		} else {
			
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
	
	if ( $.fn.select2 ) {
		$('#adminUnitLvl').select2({
		  placeholder: '레벨 선택',
		  allowClear: true,
		  width: 'resolve',
		  minimumResultsForSearch: Infinity,
		  containerCssClass: 'ez-s2',
		  selectionCssClass: 'ez-s2', 
		  dropdownCssClass: 'ez-s2',
		  dropdownParent: $('.form-card'),
		  data: [
		    { id: '', text: '선택' },
		    { id: '0', text: '1레벨' },
		    { id: '1', text: '2레벨' },
		    { id: '2', text: '3레벨' }
		  ]
		});
	 }
	 
});