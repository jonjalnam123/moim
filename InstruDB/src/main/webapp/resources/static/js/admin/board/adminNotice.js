/**
 * 작성자 : 최정석
 * 작성날짜 : 2026.03.18
 * 내용 : adminNotice 스크립트
 */

// 신규 선택 파일 누적 보관용
var selectedAdminFiles = [];

// 기존 저장 파일 중 "삭제 예정" 파일번호 목록
var deletedSavedFileId = [];

$(function () {

	// 리사이즈 함수
	initSplitResizeJQ();

	// 그리드 열 사이즈 조절 함수
	makeTableResizable('.table-grid', 'adminNotice.tableGrid.widths');

	// 페이징 이벤트 [S]
	var searchGb = $('#searchGbParam').val();
	var pageNum = $('#pageNumParam').val();
	setPagingParam(searchGb, pageNum);

	// 초기 화면
	setGridUi();
	syncNoticeLimitUI();
	switchToInsertMode();

	// 페이징 버튼 이벤트
	$('.p').on('click', function () {
		var n = $(this).attr('data-list-pn');
		$('#pageNum').val(n);
		$('#adminNoticeSearchForm').submit();
	});

	// 조회 버튼 이벤트
	$('#btnSearch').on('click', function () {
		$('#adminNoticeSearchForm').submit();
	});

	// 초기화 버튼 이벤트
	$('#btnReset').on('click', function () {
		$('#searchGb').val('noticeTitle');
		$('#searchTxt').val('');
		$('#pageNum').val(1);
		$('#adminNoticeSearchForm').submit();
	});

	// 엔터키 이벤트
	$('#searchTxt').on('keydown', function (e) {
		if (e.key === 'Enter') {
			e.preventDefault();
			$('#btnSearch').trigger('click');
		}
	});
	// 페이징 이벤트 [E]

	// 공지사항 번호 생성 이벤트 [S]
	$('#getNoticeId').on('click', function () {
		var url = '/admin/uniqueId.do';
		var params = {};
		var dataType = 'json';

		ajaxStart(url, params, dataType, function (data) {
			var result = data.result;

			if (!isEmpty(result)) {
				$('#noticeId').val(result);
				$('#regId').val($('#ss_admin_id').val());
				$('#regDt').val(toDisplayDatetime($('#nowDate').val()));
			} else {
				goToUri('')
			}
		});
	});
	// 공지사항 번호 생성 이벤트 [E]

	// 기한설정 체크 이벤트 [S]
	$('#noticeLimitYn').on('change', function () {
		syncNoticeLimitUI();
	});
	// 기한설정 체크 이벤트 [E]

	// 공지사항 상세 더블클릭 이벤트 [S]
	$('.adminNoticeInfoTr').on('dblclick', function () {

		var noticeId = $(this).data('id');

		if (isEmpty(noticeId)) {
			alert('공지사항 번호가 없습니다.');
			return;
		}

		$('.adminNoticeInfoTr').removeClass('is-selected');
		$(this).addClass('is-selected');

		var url = '/admin/noticeInfo.do';
		var params = {
			noticeId : noticeId
		};
		var dataType = 'json';

		ajaxStart(url, params, dataType, function (data) {
			var adminNoticeInfo = data.adminNoticeInfo;
			var adminNoticeFileList = data.adminNoticeFileList || [];

			if ( isEmpty(adminNoticeInfo) ) {
				goToUriAdminError();
				return;
			}

			deletedSavedFileId = [];

			$('#noticeId').val(adminNoticeInfo.noticeId || '');
			$('#regId').val(adminNoticeInfo.regId || '');
			$('#regDt').val(toDisplayDatetime(adminNoticeInfo.regDt));
			$('#noticeTitle').val(adminNoticeInfo.noticeTitle || '');
			$('#noticeEffectGb').trigger('change').val(adminNoticeInfo.noticeEffectGb || '');
			$('#noticeCn').val(adminNoticeInfo.noticeCn || '');


			$('#noticeFixYn').prop('checked', adminNoticeInfo.noticeFixYn === 'Y');
			$('#noticePopYn').prop('checked', adminNoticeInfo.noticePopYn === 'Y');
			$('#noticeLimitYn').prop('checked', adminNoticeInfo.noticeLimitYn === 'Y');

			$('#noticeStrDt').val(toDatetimeLocalValue(adminNoticeInfo.noticeStrDt));
			$('#noticeEndDt').val(toDatetimeLocalValue(adminNoticeInfo.noticeEndDt));

			renderSavedFiles(adminNoticeFileList);
			clearNewFiles();
			syncNoticeLimitUI();
			switchToUpdateMode();
		});
	});
	// 공지사항 상세 더블클릭 이벤트 [E]

	// 신규 버튼 이벤트 [S]
	$('#btnNew').on('click', function () {
		resetNoticeForm();
		switchToInsertMode();
	});
	// 신규 버튼 이벤트 [E]

	// 공지사항 등록, 수정 이벤트 [S]
	$('#btnReg, #btnUpd').on('click', function () {

		var btnVal = $(this).val();
		var url = '';

		var noticeId = $.trim($('#noticeId').val());
		var regId = $.trim($('#regId').val());
		var regDt = $.trim($('#regDt').val());
		var noticeTitle = $.trim($('#noticeTitle').val());
		var noticeEffectGb = $.trim($('#noticeEffectGb').val());
		var noticeCn = $.trim($('#noticeCn').val());

		var noticeFixYn = $('#noticeFixYn').is(':checked') ? 'Y' : 'N';
		var noticePopYn = $('#noticePopYn').is(':checked') ? 'Y' : 'N';
		var noticeLimitYn = $('#noticeLimitYn').is(':checked') ? 'Y' : 'N';
		var noticeStrDt = $('#noticeStrDt').val();
		var noticeEndDt = $('#noticeEndDt').val();

		var files = selectedAdminFiles;
		var allowExt = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'];
		var remainSavedCount = getActiveSavedFileCount();
		var totalCount = remainSavedCount + files.length;

		if (isEmpty(noticeId)) {
			alert('공지사항 번호를 먼저 생성하세요.');
			return;
		}

		if (isEmpty(regId)) {
			alert('작성자 정보가 없습니다.');
			return;
		}

		if (isEmpty(noticeTitle)) {
			alert('공지사항 제목을 입력하세요.');
			$('#noticeTitle').focus();
			return;
		}
		
		if (isEmpty(noticeEffectGb)) {
			alert('공지사항 중요도를 선택하세요.');
			$('#noticeEffectGb').focus();
			return;
		}

		if (isEmpty(noticeCn)) {
			alert('공지사항 내용을 입력하세요.');
			$('#noticeCn').focus();
			return;
		}

		if (noticeLimitYn === 'Y') {
			if (isEmpty(noticeStrDt) || isEmpty(noticeEndDt)) {
				alert('기한설정 사용 시 시작날짜와 종료날짜를 모두 입력하세요.');
				return;
			}

			if (noticeStrDt > noticeEndDt) {
				alert('종료날짜는 시작날짜보다 빠를 수 없습니다.');
				return;
			}
		}

		if (totalCount > 5) {
			alert('첨부파일은 최대 5개까지 가능합니다.');
			return;
		}

		var formData = new FormData();

		formData.append('noticeId', noticeId);
		formData.append('regId', regId);
		formData.append('regDt', regDt);
		formData.append('noticeTitle', noticeTitle);
		formData.append('noticeEffectGb', noticeEffectGb);
		formData.append('noticeCn', noticeCn);

		formData.append('noticeFixYn', noticeFixYn);
		formData.append('noticePopYn', noticePopYn);
		formData.append('noticeLimitYn', noticeLimitYn);
		formData.append('noticeStrDt', noticeStrDt);
		formData.append('noticeEndDt', noticeEndDt);

		for (var d = 0; d < deletedSavedFileId.length; d++) {
			formData.append('deleteFileId', deletedSavedFileId[d]);
		}

		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			var fileName = file.name || '';
			var ext = fileName.indexOf('.') > -1 ? fileName.split('.').pop().toLowerCase() : '';

			if ($.inArray(ext, allowExt) === -1) {
				alert('이미지 파일만 업로드 가능합니다.');
				return;
			}

			formData.append('adminFiles', file);
		}

		if (btnVal === 'I') {
			if (!confirm('공지사항' + regProcConfirm)) {
				return;
			}
			url = '/admin/noticeReg.do';
		} else {
			if (!confirm('공지사항을' + updProcConfirm)) {
				return;
			}
			url = '/admin/noticeUpd.do';
		}

		ajaxWithFileStart(url, formData, function (data) {
			var result = Number(data.result);

			if (result > 0) {
				alert(btnVal === 'I' ? '공지사항' + regSuccess : '공지사항' + updSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
			}
		});
	});
	// 공지사항 등록, 수정 이벤트 [E]

	// 공지사항 삭제 이벤트 [S]
	$('#btnDel').on('click', function () {

		var noticeId = $('#noticeId').val();

		if (isEmpty(noticeId)) {
			alert('삭제할 공지사항을 선택하세요.');
			return;
		}

		if (!confirm('공지사항을 삭제하시겠습니까?')) {
			return;
		}

		var url = '/admin/noticeDel.do';
		var params = {
			noticeId : noticeId
		};
		var dataType = 'json';

		ajaxStart(url, params, dataType, function (data) {
			var result = Number(data.result);

			if (result > 0) {
				alert('공지사항' + delSuccess);
				window.location.reload();
			} else {
				goToUriAdminError();
			}
		});
	});
	// 공지사항 삭제 이벤트 [E]

	// 파일 선택 버튼 이벤트 [S]
	$('#btnPickFiles').on('click', function () {
		$('#adminFiles').trigger('click');
	});
	// 파일 선택 버튼 이벤트 [E]

	// 파일 선택 이벤트 [S]
	$('#adminFiles').on('change', function () {

		var input = this;
		var newFiles = Array.from(input.files || []);

		if (newFiles.length === 0) {
			return;
		}

		var allowExt = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp'];
		var remainSavedCount = getActiveSavedFileCount();

		for (var i = 0; i < newFiles.length; i++) {
			var ext = newFiles[i].name.indexOf('.') > -1 ? newFiles[i].name.split('.').pop().toLowerCase() : '';

			if ($.inArray(ext, allowExt) === -1) {
				alert(fileExtImgChk);
				input.value = '';
				return;
			}
		}

		var mergedFiles = selectedAdminFiles.slice();

		$.each(newFiles, function (_, file) {
			var exists = mergedFiles.some(function (f) {
				return f.name === file.name
					&& f.size === file.size
					&& f.lastModified === file.lastModified;
			});

			if (!exists) {
				mergedFiles.push(file);
			}
		});

		if (remainSavedCount + mergedFiles.length > 5) {
			alert('첨부파일은 최대 5개까지 가능합니다. 현재 유지 파일 ' + remainSavedCount + '개, 신규 선택 가능 파일은 최대 ' + (5 - remainSavedCount) + '개입니다.');
			input.value = '';
			syncAdminFilesInput();
			return;
		}

		selectedAdminFiles = mergedFiles;

		syncAdminFilesInput();
		refreshNewFilesUI();
	});
	// 파일 선택 이벤트 [E]

	// 저장된 파일 다운로드 이벤트 [S]
	$('#savedFileList').on('click', '.btnFileDown', function () {

		var $item = $(this).closest('.attach-item');

		if ($item.hasClass('is-delete-pending')) {
			alert('삭제 예정 파일은 다운로드할 수 없습니다.');
			return;
		}

		var fileId = $item.data('file-id');

		if (isEmpty(fileId)) {
			alert('파일 번호가 없습니다.');
			return;
		}

		window.location.href = '/admin/fileDown.do?fileId=' + encodeURIComponent(fileId) + '&refType=' + encodeURIComponent('NOTICE');
	});
	// 저장된 파일 다운로드 이벤트 [E]

	// 저장된 파일 삭제예정 이벤트 [S]
	$('#savedFileList').on('click', '.btnFileDel', function () {

		var $item = $(this).closest('.attach-item');
		var fileId = String($item.data('file-id') || '');

		if (isEmpty(fileId)) {
			alert('파일 번호가 없습니다.');
			return;
		}

		toggleSavedFileDelete(fileId, $item);
	});
	// 저장된 파일 삭제예정 이벤트 [E]

	// 새 파일 제거 이벤트 [S]
	$('#newFileList').on('click', '.btnNewFileRemove', function () {
		var idx = parseInt($(this).closest('.attach-item').attr('data-new-idx'), 10);

		if (!isNaN(idx)) {
			removeNewFileByIndex(idx);
		}
	});
	// 새 파일 제거 이벤트 [E]
});

/*******************************
* FuntionNm : resetNoticeForm
* Date : 2026.03.18
* Author : CJS
* Description : 공지사항 입력 폼 초기화
********************************/
function resetNoticeForm() {

	$('.adminNoticeInfoTr').removeClass('is-selected');

	$('#noticeId').val('');
	$('#regId').val('');
	$('#regDt').val('');
	$('#noticeTitle').val('');
	$('#noticeEffectGb').trigger('change').val('');
	$('#noticeCn').val('');

	$('#noticeFixYn').prop('checked', false);
	$('#noticePopYn').prop('checked', false);
	$('#noticeLimitYn').prop('checked', false);

	$('#noticeStrDt').val('');
	$('#noticeEndDt').val('');

	deletedSavedFileId = [];
	renderSavedFiles([]);
	clearNewFiles();
	syncNoticeLimitUI();
}

/*******************************
* FuntionNm : setGridUi
* Date : 2026.03.18
* Author : CJS
* Description : 공지사항 그리드 화면 조회
********************************/
function setGridUi() {
	$('.adminNoticeInfoTr').each(function () {
	    var $tr = $(this);
	    var fixYn = $tr.data('fix-yn');
	
	    if (fixYn === 'Y') {
	        $tr.addClass('is-fixed-row');
	    }
	});
}

/*******************************
* FuntionNm : switchToInsertMode
* Date : 2026.03.18
* Author : CJS
* Description : 등록 모드 화면 전환
********************************/
function switchToInsertMode() {
	$('#btnReg').show();
	$('#btnUpd').hide();
	$('#btnDel').hide();
	$('#btnNew').hide();
	$('#getNoticeId').prop('disabled', false);
}

/*******************************
* FuntionNm : switchToUpdateMode
* Date : 2026.03.18
* Author : CJS
* Description : 수정 모드 화면 전환
********************************/
function switchToUpdateMode() {
	$('#btnReg').hide();
	$('#btnUpd').show();
	$('#btnDel').show();
	$('#btnNew').show();
	$('#getNoticeId').prop('disabled', true);
}

/*******************************
* FuntionNm : syncNoticeLimitUI
* Date : 2026.03.18
* Author : CJS
* Description : 기한설정 체크 상태에 따라 날짜 입력 제어
********************************/
function syncNoticeLimitUI() {

	var isChecked = $('#noticeLimitYn').is(':checked');

	$('#noticeStrDt').prop('disabled', !isChecked);
	$('#noticeEndDt').prop('disabled', !isChecked);

	$('#lblNoticeStrDt').toggleClass('required', isChecked);
	$('#lblNoticeEndDt').toggleClass('required', isChecked);

	if (!isChecked) {
		$('#noticeStrDt').val('');
		$('#noticeEndDt').val('');
	}
}

/*******************************
* FuntionNm : getActiveSavedFileCount
* Date : 2026.03.18
* Author : CJS
* Description : 삭제 예정이 아닌 저장 파일 개수 반환
********************************/
function getActiveSavedFileCount() {
	return $('#savedFileList .attach-item').not('.is-delete-pending').length;
}

/*******************************
* FuntionNm : toggleSavedFileDelete
* Date : 2026.03.18
* Author : CJS
* Description : 저장 파일 삭제 예정 상태 토글
********************************/
function toggleSavedFileDelete(fileId, $item) {

	var idx = deletedSavedFileId.indexOf(fileId);

	if (idx > -1) {
		deletedSavedFileId.splice(idx, 1);
		$item.removeClass('is-delete-pending');
		$item.find('.btnFileDel').text('삭제');
		$item.find('.btnFileDown').prop('disabled', false).removeClass('disabled');
	} else {
		deletedSavedFileId.push(fileId);
		$item.addClass('is-delete-pending');
		$item.find('.btnFileDel').text('취소');
		$item.find('.btnFileDown').prop('disabled', true).addClass('disabled');
	}
}

/*******************************
* FuntionNm : syncAdminFilesInput
* Date : 2026.03.18
* Author : CJS
* Description : 선택된 파일 배열을 input file 에 동기화
********************************/
function syncAdminFilesInput() {

	var input = document.getElementById('adminFiles');
	var dt = new DataTransfer();

	$.each(selectedAdminFiles, function (_, file) {
		dt.items.add(file);
	});

	input.files = dt.files;
}

/*******************************
* FuntionNm : refreshNewFilesUI
* Date : 2026.03.18
* Author : CJS
* Description : 신규 선택 파일 목록 화면 갱신
********************************/
function refreshNewFilesUI() {

	var files = selectedAdminFiles || [];
	var $list = $('#newFileList');

	$list.empty();

	if (files.length === 0) {
		$('#adminFilesSummary').text('선택된 파일 없음');
		return;
	}

	$('#adminFilesSummary').text('선택됨: ' + files.length + '개');

	$.each(files, function (idx, file) {
		$list.append(
			'<li class="attach-item" data-new-idx="' + idx + '">' +
				'<div class="attach-left">' +
					'<div class="attach-name" title="' + escapeHtml(file.name) + '">' + escapeHtml(file.name) + '</div>' +
					'<div class="attach-meta">' + formatBytes(file.size) + '</div>' +
				'</div>' +
				'<div class="attach-actions">' +
					'<button type="button" class="btn-mini remove btnNewFileRemove">제거</button>' +
				'</div>' +
			'</li>'
		);
	});
}

/*******************************
* FuntionNm : renderSavedFiles
* Date : 2026.03.18
* Author : CJS
* Description : 저장된 파일 목록 화면 출력
********************************/
function renderSavedFiles(fileList) {

	var $list = $('#savedFileList');
	$list.empty();

	if (!fileList || fileList.length === 0) {
		$('#savedFileHint').show();
		return;
	}

	$('#savedFileHint').hide();

	$.each(fileList, function (_, file) {
		$list.append(
			'<li class="attach-item" data-file-id="' + file.fileId + '">' +
				'<div class="attach-left">' +
					'<div class="attach-name" title="' + escapeHtml(file.fileOrgNm) + '">' + escapeHtml(file.fileOrgNm) + '</div>' +
					'<div class="attach-meta">' + formatBytes(file.fileSize) + '</div>' +
				'</div>' +
				'<div class="attach-actions">' +
					'<button type="button" class="btn-mini download btnFileDown">다운</button>' +
					'<button type="button" class="btn-mini remove btnFileDel">삭제</button>' +
				'</div>' +
			'</li>'
		);
	});
}

/*******************************
* FuntionNm : removeNewFileByIndex
* Date : 2026.03.18
* Author : CJS
* Description : 신규 선택 파일 1건 제거
********************************/
function removeNewFileByIndex(removeIdx) {

	selectedAdminFiles = selectedAdminFiles.filter(function (_, idx) {
		return idx !== removeIdx;
	});

	syncAdminFilesInput();
	refreshNewFilesUI();
}

/*******************************
* FuntionNm : clearNewFiles
* Date : 2026.03.18
* Author : CJS
* Description : 신규 선택 파일 전체 초기화
********************************/
function clearNewFiles() {
	selectedAdminFiles = [];
	syncAdminFilesInput();
	$('#newFileList').empty();
	$('#adminFilesSummary').text('선택된 파일 없음');
}

/*******************************
* FuntionNm : setPagingParam
* Date : 2026.03.18
* Author : CJS
* Description : 페이징 진행 후 페이징 데이터 세팅 함수
* PARAM : searchGb : 조회 조건, pageNum : 조회 페이지 번호
********************************/
function setPagingParam(searchGb, pageNum) {
	$('.s').each(function () {
		if ($(this).val() === searchGb) {
			$(this).prop('selected', true);
		}
	});

	$('.p').each(function () {
		if ($(this).attr('data-list-pn') === pageNum) {
			$(this).addClass('active');
		}
	});
}

/*******************************
* FuntionNm : toDatetimeLocalValue
* Date : 2026.03.18
* Author : CJS
* Description : datetime-local 형식으로 변환
********************************/
function toDatetimeLocalValue(value) {
	if (!value) return '';
	var str = String(value).trim().replace(' ', 'T');
	return str.length >= 16 ? str.substring(0, 16) : str;
}

/*******************************
* FuntionNm : toDisplayDatetime
* Date : 2026.03.18
* Author : CJS
* Description : 화면 표시용 날짜 변환
********************************/
function toDisplayDatetime(value) {
	if (!value) return '';
	return String(value).replace('T', ' ').substring(0, 19);
}

/*******************************
* FuntionNm : formatBytes
* Date : 2026.03.18
* Author : CJS
* Description : 파일 사이즈 포맷 변환
********************************/
function formatBytes(bytes) {

	if (!bytes && bytes !== 0) {
		return '';
	}

	var units = ['B', 'KB', 'MB', 'GB', 'TB'];
	var i = 0;
	var size = bytes;

	while (size >= 1024 && i < units.length - 1) {
		size = size / 1024;
		i++;
	}

	return (i === 0 ? size : size.toFixed(1)) + ' ' + units[i];
}

/*******************************
* FuntionNm : escapeHtml
* Date : 2026.03.18
* Author : CJS
* Description : HTML 특수문자 치환
********************************/
function escapeHtml(str) {
	if (str == null) return '';
	return String(str)
		.replace(/&/g, '&amp;')
		.replace(/</g, '&lt;')
		.replace(/>/g, '&gt;')
		.replace(/"/g, '&quot;')
		.replace(/'/g, '&#39;');
}