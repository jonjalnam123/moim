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
	
	// 메뉴 상세조회
	$('.menuTreeF, .menuTreeS').on('click', function() {
		var menuId = $(this).data('id');
		var menuPId = $(this).data('pid');

		if ( isEmptyMsg(menuId, selectDataChk) ) {
			return;
		}

		var url = '/admin/menuSelect.do';
		var params = {
				menuId : menuId
			  , menuPId : menuPId
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			
			var menuData = data.result;
			var menuDeptList = data.menuDeptList;
			var adminUnitList = data.adminUnitList;
			
			if ( !isEmpty(menuData) && !isEmptyArr(menuDeptList) && !isEmptyArr(adminUnitList) ) {
				
				var menuId = menuData.menuId
				var menuNm = menuData.menuNm
				var menuPId = menuData.menuPId
				var menuPNm = menuData.menuPNm
				var menuUrl = menuData.menuUrl
				var menuDeptCd = menuData.menuDeptCd
				var menuLvl = menuData.menuLvl
				var menuSort = menuData.menuSort
				var menuCn = menuData.menuCn
				var menuUseYn = menuData.menuUseYn
				var menuIcon = menuData.menuIcon

				$('#menuId').attr('data-id', menuId);
				$('#menuNm').attr('data-nm', menuNm);
				$('#menuId').val(menuId);
				$('#menuNm').val(menuNm);
				$('#menuPId').val(menuPId);
				$('#menuPNm').val(menuPNm);
				setDeptOptions(adminUnitList)
			  	setDeptSelected(menuDeptList);
				$('#menuUrl').val(menuUrl);
				$('#menuLvl').val(menuLvl).trigger('change');
				$('#menuSort').val(menuSort);
				$('#menuCn').val(menuCn);
				$('#menuIcon').val(menuIcon);
				$('input[name="menuUseYn"][value="' + menuUseYn + '"]').prop('checked', true);
				
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
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
	
	$('#btnRef').on('click', function() {
		window.location.reload();
	});
	
	// 메뉴 등록 이벤트
	$('#btnReg, #btnUpd').on('click', function() {
		var btnVal = $(this).val();
		var url = '';
		
		var menuId = $('#menuId').val();
		var menuNm = $('#menuNm').val();
		var menuPId = $('#menuPId').val();
		var menuUrl = $('#menuUrl').val();
		var menuDeptCd = $('#menuDeptCd').val();
		var menuLvl = $('#menuLvl').val();
		var menuSort = $('#menuSort').val();
		var menuCn = $('#menuCn').val();
		var menuIcon = $('#menuIcon').val();
		var menuUseYn = $('input[name="menuUseYn"]:checked').val();
		
		if ( isEmptyArr(menuDeptCd) ){
			menuDeptCd = '';
		}
		
		// 2레벨 진행시
		if ( isEmpty(menuPId) && Number(menuLvl) !== 0 ) {
			menuPId = menuId;
		}

		if ( btnVal === 'I' ) {
			
			if ( isEmptyMsg(menuNm, '메뉴명' + dataEmpty) ) {
				return;
			}
			
			if ( isEmptyMsg(menuNm, '메뉴 URL' + dataEmpty) ) {
				return;
			}
			
			if ( isEmptyMsg(menuLvl, '레벨' + dataEmpty) ) {
				return;
			}
			
			if ( isEmptyMsg(menuSort, '정렬순서' + dataEmpty) ) {
				return;
			}
			
			var orgMenuNm = $('#menuNm').data('nm');
			if (orgMenuNm === menuNm) {
				alert(menuNmChk);
				return;
			}
			
			if ( !confirm('메뉴' + regProcConfirm) ) {
				return;
			}
			url = '/admin/menuReg.do';
			
		} else {
			
			if ( isEmptyMsg(menuNm, '메뉴명' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(menuNm, '메뉴 URL' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(menuLvl, '레벨' + dataEmpty) ) {
				return;
			}

			if ( isEmptyMsg(menuSort, '정렬순서' + dataEmpty) ) {
				return;
			}
			
			if ( !confirm('메뉴' + updProcConfirm) ) {
				return;
			}
			url = '/admin/menuUpd.do';
			
		}

		var params = {
				menuId : menuId
			  , menuNm : menuNm
			  , menuPId : menuPId
			  , menuUrl : menuUrl
			  , menuDeptCd : menuDeptCd
			  , menuLvl : menuLvl
			  , menuSort : menuSort
			  , menuCn : menuCn
			  , menuIcon : menuIcon
			  , menuUseYn : menuUseYn
		}
		var dataType = 'json'
		ajaxStart(url, params, dataType, function(data) {
			var result = Number(data.result);
			if (result > 0 ) {
				window.location.reload();
			} else {
				var url = '/admin/error.do';
				goToUri(url);
			}
		});
	});
	
	// 메뉴 삭제 이벤트
	$('#btnDel').on('click', function() {
		var menuId = $('#menuId').val();
		var menuPId = $('#menuPId').val();
		
		if ( isEmptyMsg(menuId, delDataChk) ) {
			return;
		}
		
		if ( !confirm('메뉴' + delProcConfirm) ) {
			return;
		}

		var url = '/admin/menuDel.do';
		var params = {
				menuId : menuId
			  , menuPId : menuPId
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
	    $('#menuDeptCd').select2({
	      placeholder: '부서 선택',
	      allowClear: true,
	      width: 'resolve',
	      minimumResultsForSearch: Infinity,
	      containerCssClass: 'ez-s2',
	      selectionCssClass: 'ez-s2', 
	      dropdownCssClass: 'ez-s2',
	      dropdownParent: $('.form-card')
	    });
		
		$('#menuLvl').select2({
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
		    { id: '1', text: '2레벨' }
		  ]
		});
	 }
	 
});

/*******************************
* FuntionNm : setDeptOptions
* Date : 2025.10.02
* Author : CJS
* Description : 상세조회시 전체 값 조회
* PARAM : adminUnitList
********************************/
function setDeptOptions(adminUnitList) {
  var sel = $('#menuDeptCd');

  if (sel.find('option').length > 0) return;

  sel.empty();

  adminUnitList.forEach(function(item) {
    if (Number(item.adminUnitLvl) === 0) {
      sel.append(new Option(item.adminUnitNm, item.adminUnitCd, false, false));
    }
  });

  sel.trigger('change');
}

/*******************************
* FuntionNm : setDeptSelected
* Date : 2025.10.02
* Author : CJS
* Description : 상세조회 시 “선택값만” 세팅
* PARAM : menuDeptList
********************************/
function setDeptSelected(menuDeptList) {
  var sel = $('#menuDeptCd');

  var selected = [];
  if (menuDeptList && menuDeptList.length) {
    selected = menuDeptList.map(function(x) { return x.cd; });
  }

  sel.val(selected).trigger('change'); // select2 포함 갱신
}