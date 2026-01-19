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
	  // 필요한 경우 우측 폼에 값 로드
	  // loadForm($(this).data('id'));
	});
	
	// 메뉴 상세조회
	$('#menuDept').on('click', function() {
		alert('1234');
	});
	
	// 부서선택 변경 이벤트
	$('#menuDeptCd').on('change', function() {
		var deptCd = $(this).val()
		var deptCdList = [];
		$.each(deptCd, function(idx, value){
			if ( value !== '' ) {
				deptCdList.push(value);
			}
		});
		$('#menuDeptCd').val(deptCdList);
	});
	
	// 메뉴 등록 이벤트
	$('#btnReg').on('click', function() {
		var menuNm = $('#menuNm').val();
		var menuPId = $('#menuPId').val();
		var menuUrl = $('#menuUrl').val();
		var menuDeptCd = $('#menuDeptCd').val();
		var menuLvl = $('#menuLvl').val();
		var menuSort = $('#menuSort').val();
		var menuCn = $('#menuCn').val();
		var menuUseYn = $('input[name="menuUseYn"]:checked').val();

		var url = '/admin/menuReg.do';
		var params = {
				menuNm : menuNm
			  , menuPId : menuPId
			  , menuUrl : menuUrl
			  , menuDeptCd : menuDeptCd
			  , menuLvl : menuLvl
			  , menuSort : menuSort
			  , menuCn : menuCn
			  , menuUseYn : menuUseYn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			if (data.result === '1') {
				window.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 메뉴 삭제 이벤트
	$('#btnReg').on('click', function() {
		var menuId = $('#menuId').val();
		
		if ( isEmptyMsg(menuId, delDataChk) ) {
			return;
		}

		var url = '/admin/menuDel.do';
		var params = {
				menuId : menuId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			if (data.result === '1') {
				window.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});

	if ( $.fn.select2 ) {
	    $('#menuDeptCd').select2({
	      placeholder: '부서 선택',
	      allowClear: true,
	      width: 'resolve',
	      minimumResultsForSearch: Infinity,
	      // ↓↓↓ 스코프 고정: 생성 컨테이너/드롭다운에 식별 클래스 부여
	      containerCssClass: 'ez-s2',
	      selectionCssClass: 'ez-s2', 
	      dropdownCssClass: 'ez-s2',
	      // ↓↓↓ 드롭다운을 이 폼 카드 안에 붙여서 상위 선택자 스코프도 유지
	      dropdownParent: $('.form-card'),
	    });
		
	 }
	 
});