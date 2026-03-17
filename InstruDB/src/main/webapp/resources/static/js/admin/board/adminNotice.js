/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminNotice 스크립트
 */

// 신규 선택 파일 누적 보관용
var selectedAdminFiles = [];

// 기존 저장 파일 중 "삭제 예정" 파일번호 목록
var deletedSavedFileId = [];

$(function () {

	// 리사이즈
	initSplitResizeJQ();
	makeTableResizable('.table-grid', 'adminNotice.tableGrid.widths');

	// 페이징 세팅
	setPagingParam($('#searchGbParam').val(), $('#pageNumParam').val());

	// 초기 화면
	syncNoticeLimitUI();
	switchToInsertMode();

	// 페이징 버튼
	$('.p').on('click', function () {
		var n = $(this).attr('data-list-pn');
		$('#pageNum').val(n);
		$('#adminNoticeSearchForm').submit();
	});

	// 조회
	$('#btnSearch').on('click', function () {
		$('#adminNoticeSearchForm').submit();
	});

	// 초기화
	$('#btnReset').on('click', function () {
		$('#searchGb').val('noticeTitle');
		$('#searchTxt').val('');
		$('#pageNum').val(1);
		$('#adminNoticeSearchForm').submit();
	});

	// 엔터 조회
	$('#searchTxt').on('keydown', function (e) {
		if (e.key === 'Enter') {
			e.preventDefault();
			$('#btnSearch').trigger('click');
		}
	});

	// 공지사항 번호 생성
	$('#getNoticeId').on('click', function () {
		ajaxStart('/admin/uniqueId.do', {}, 'json', function (data) {
			var result = data.result;

			if (!isEmpty(result)) {
				$('#noticeId').val(result);
				$('#regId').val($('#adminId').val());
				$('#regDt').val(toDisplayDatetime($('#nowDate').val()));
			} else {
				$('#noticeId').val('');
				alert('공지사항 번호 생성에 실패했습니다.');
			}
		});
	});

	// 기한설정 토글
	$('#noticeLimitYn').on('change', syncNoticeLimitUI);

	// 공지사항 상세 더블클릭
	$('.adminNoticeInfoTr').on('dblclick', function () {

		var noticeId = $(this).data('id');

		if (isEmpty(noticeId)) {
			alert('공지사항 번호가 없습니다.');
			return;
		}

		$('.adminNoticeInfoTr').removeClass('is-selected');
		$(this).addClass('is-selected');

		var url = '/admin/noticeInfo.do';
		var param = { noticeId: noticeId };

		ajaxStart(url, param, 'json', function (data) {
			if (Number(data.result) <= 0) {
				goToUri('/admin/error.do');
				return;
			}

			deletedSavedFileId = [];
			fillNoticeForm(data.adminNoticeInfo);
			renderSavedFiles(data.adminNoticeFileList || []);
			clearNewFiles();

			switchToUpdateMode();
		});
	});

	// 신규
	$('#btnNew').on('click', function () {
		resetNoticeForm();
		switchToInsertMode();
	});

	// 저장 / 수정
	$('#btnReg, #btnUpd').on('click', function () {

		var mode = $(this).val();
		var url = '';

		var formData = buildNoticeFormData();
		if (!formData) return;

		if (mode === 'I') {
			if (!confirm('공지사항' + regProcConfirm)) return;
			url = '/admin/noticeReg.do';
		} else {
			if (!confirm('공지사항을' + updProcConfirm)) return;
			url = '/admin/noticeUpd.do';
		}

		ajaxWithFileStart(url, formData, function (data) {
			var result = Number(data.result);

			if (result > 0) {
				alert(mode === 'I' ? '공지사항' + regSuccess : '공지사항' + updSuccess);
				window.location.reload();
			} else {
				goToUri('/admin/error.do');
				return;
			}
		});
	});

	// 공지사항 삭제
	$('#btnDel').on('click', function () {

		var noticeId = $('#noticeId').val();

		if (isEmpty(noticeId)) {
			alert('삭제할 공지사항을 선택하세요.');
			return;
		}

		if (!confirm('공지사항을 삭제하시겠습니까?')) return;

		ajaxStart('/admin/noticeDel.do', { noticeId: noticeId }, 'json', function (data) {
			var result = Number(data.result);

			if (result > 0) {
				alert('공지사항' + delSuccess);
				window.location.reload();
			} else {
				goToUri('/admin/error.do');
				return;
			}
		});
	});

	// 파일 선택 버튼
	$('#btnPickFiles').on('click', function () {
		$('#adminFiles').trigger('click');
	});

	// 파일 선택 - 기존 선택 유지 + 누적 추가
	$('#adminFiles').on('change', function () {
		var input = this;
		var newFiles = Array.from(input.files || []);

		if (newFiles.length === 0) {
			return;
		}

		var allowExt = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp'];
		var remainSavedCount = getActiveSavedFileCount();

		// 확장자 체크
		for (var i = 0; i < newFiles.length; i++) {
			var ext = newFiles[i].name.indexOf('.') > -1 ? newFiles[i].name.split('.').pop().toLowerCase() : '';

			if ($.inArray(ext, allowExt) === -1) {
				alert(typeof fileExtImgChk !== 'undefined' ? fileExtImgChk : '이미지 파일만 업로드 가능합니다.');
				input.value = '';
				return;
			}
		}

		// 기존 선택 파일 + 새 선택 파일 합치기
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

		// 저장 유지 파일 + 신규 선택 파일 최대 5개 제한
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

	// 저장된 파일 다운로드
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

	// 저장된 파일 삭제예정 토글
	$('#savedFileList').on('click', '.btnFileDel', function () {
		var $item = $(this).closest('.attach-item');
		var fileId = String($item.data('file-id') || '');

		if (!fileId) {
			alert('파일 번호가 없습니다.');
			return;
		}

		toggleSavedFileDelete(fileId, $item);
	});

	// 새 파일 제거
	$('#newFileList').on('click', '.btnNewFileRemove', function () {
		var idx = parseInt($(this).closest('.attach-item').attr('data-new-idx'), 10);

		if (!isNaN(idx)) {
			removeNewFileByIndex(idx);
		}
	});
});

/*******************************
* 공지사항 FormData 생성
********************************/
function buildNoticeFormData() {

	var noticeId = $.trim($('#noticeId').val());
	var regId = $.trim($('#regId').val());
	var noticeTitle = $.trim($('#noticeTitle').val());
	var noticeCn = $.trim($('#noticeCn').val());

	var noticeLimitYn = $('#noticeLimitYn').is(':checked') ? 'Y' : 'N';
	var noticeStrDt = $('#noticeStrDt').val();
	var noticeEndDt = $('#noticeEndDt').val();

	var files = selectedAdminFiles;
	var allowExt = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'];

	var remainSavedCount = getActiveSavedFileCount();
	var totalCount = remainSavedCount + files.length;

	if (isEmpty(noticeId)) {
		alert('공지사항 번호를 먼저 생성하세요.');
		return null;
	}

	if (isEmpty(regId)) {
		alert('작성자 정보가 없습니다.');
		return null;
	}

	if (isEmpty(noticeTitle)) {
		alert('공지사항 제목을 입력하세요.');
		$('#noticeTitle').focus();
		return null;
	}

	if (isEmpty(noticeCn)) {
		alert('공지사항 내용을 입력하세요.');
		$('#noticeCn').focus();
		return null;
	}

	if (noticeLimitYn === 'Y') {
		if (isEmpty(noticeStrDt) || isEmpty(noticeEndDt)) {
			alert('기한설정 사용 시 시작날짜와 종료날짜를 모두 입력하세요.');
			return null;
		}

		if (noticeStrDt > noticeEndDt) {
			alert('종료날짜는 시작날짜보다 빠를 수 없습니다.');
			return null;
		}
	}

	if (totalCount > 5) {
		alert('첨부파일은 최대 5개까지 가능합니다.');
		return null;
	}

	var formData = new FormData();

	formData.append('noticeId', noticeId);
	formData.append('regId', regId);
	formData.append('regDt', $('#regDt').val());
	formData.append('noticeTitle', noticeTitle);
	formData.append('noticeCn', noticeCn);

	formData.append('noticeFixYn', $('#noticeFixYn').is(':checked') ? 'Y' : 'N');
	formData.append('noticePopYn', $('#noticePopYn').is(':checked') ? 'Y' : 'N');
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
			return null;
		}

		formData.append('adminFiles', file);
	}

	return formData;
}

/*******************************
* 상세 폼 채우기
********************************/
function fillNoticeForm(noticeInfo) {
	$('#noticeId').val(noticeInfo.noticeId || '');
	$('#regId').val(noticeInfo.regId || '');
	$('#regDt').val(toDisplayDatetime(noticeInfo.regDt));
	$('#noticeTitle').val(noticeInfo.noticeTitle || '');
	$('#noticeCn').val(noticeInfo.noticeCn || '');

	$('#noticeFixYn').prop('checked', noticeInfo.noticeFixYn === 'Y');
	$('#noticePopYn').prop('checked', noticeInfo.noticePopYn === 'Y');
	$('#noticeLimitYn').prop('checked', noticeInfo.noticeLimitYn === 'Y');

	$('#noticeStrDt').val(toDatetimeLocalValue(noticeInfo.noticeStrDt));
	$('#noticeEndDt').val(toDatetimeLocalValue(noticeInfo.noticeEndDt));

	syncNoticeLimitUI();
}

/*******************************
* 화면 초기화
********************************/
function resetNoticeForm() {

	$('.adminNoticeInfoTr').removeClass('is-selected');

	$('#noticeId').val('');
	$('#regId').val('');
	$('#regDt').val('');
	$('#noticeTitle').val('');
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
* 입력 모드 전환
********************************/
function switchToInsertMode() {
	$('#btnReg').show();
	$('#btnUpd').hide();
	$('#btnDel').hide();
	$('#btnNew').hide();
	$('#getNoticeId').prop('disabled', false);
}

/*******************************
* 수정 모드 전환
********************************/
function switchToUpdateMode() {
	$('#btnReg').hide();
	$('#btnUpd').show();
	$('#btnDel').show();
	$('#btnNew').show();
	$('#getNoticeId').prop('disabled', true);
}

/*******************************
* 저장 파일 없을 때 힌트
********************************/
function toggleSavedFileHint() {
	if ($('#savedFileList .attach-item').length === 0) {
		$('#savedFileHint').show();
	} else {
		$('#savedFileHint').hide();
	}
}

/*******************************
* 활성 상태 저장 파일 개수
********************************/
function getActiveSavedFileCount() {
	return $('#savedFileList .attach-item').not('.is-delete-pending').length;
}

/*******************************
* 저장 파일 삭제예정 토글
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
* 바이트 포맷
********************************/
function formatBytes(bytes) {
	if (!bytes && bytes !== 0) return '';
	var units = ['B', 'KB', 'MB', 'GB', 'TB'];
	var i = 0;
	var n = bytes;

	while (n >= 1024 && i < units.length - 1) {
		n /= 1024;
		i++;
	}

	return (i === 0 ? n : n.toFixed(1)) + ' ' + units[i];
}

/*******************************
* 선택 파일 input 동기화
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
* 새 파일 목록 렌더
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

	$.each(files, function (idx, f) {
		$list.append(
			'<li class="attach-item" data-new-idx="' + idx + '">' +
				'<div class="attach-left">' +
					'<div class="attach-name" title="' + escapeHtml(f.name) + '">' + escapeHtml(f.name) + '</div>' +
					'<div class="attach-meta">' + formatBytes(f.size) + '</div>' +
				'</div>' +
				'<div class="attach-actions">' +
					'<button type="button" class="btn-mini remove btnNewFileRemove">제거</button>' +
				'</div>' +
			'</li>'
		);
	});
}

/*******************************
* 저장 파일 목록 렌더
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
* 기한설정 토글
********************************/
function syncNoticeLimitUI() {
	var on = $('#noticeLimitYn').is(':checked');

	$('#noticeStrDt, #noticeEndDt').prop('disabled', !on);
	$('#lblNoticeStrDt, #lblNoticeEndDt').toggleClass('required', on);

	if (!on) {
		$('#noticeStrDt, #noticeEndDt').val('');
	}
}

/*******************************
* 새 파일 제거
********************************/
function removeNewFileByIndex(removeIdx) {
	selectedAdminFiles = selectedAdminFiles.filter(function (_, idx) {
		return idx !== removeIdx;
	});

	syncAdminFilesInput();
	refreshNewFilesUI();
}

/*******************************
* 새 파일 초기화
********************************/
function clearNewFiles() {
	selectedAdminFiles = [];
	syncAdminFilesInput();
	$('#newFileList').empty();
	$('#adminFilesSummary').text('선택된 파일 없음');
}

/*******************************
* 페이징 표시
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
* datetime-local 변환
********************************/
function toDatetimeLocalValue(value) {
	if (!value) return '';
	var s = String(value).trim().replace(' ', 'T');
	return s.length >= 16 ? s.substring(0, 16) : s;
}

/*******************************
* 일반 텍스트 날짜 변환
********************************/
function toDisplayDatetime(value) {
	if (!value) return '';
	return String(value).replace('T', ' ').substring(0, 19);
}

/*******************************
* escape
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