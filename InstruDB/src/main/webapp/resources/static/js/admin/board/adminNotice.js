/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : adminNotice 스크립트
 */

// 신규 선택 파일 누적 보관용
var selectedAdminFiles = [];

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
	$(document).on('click', '.p', function () {
		var n = $(this).attr('data-list-pn');
		$('#pageNum').val(n);
		$('#adminNoticeSearchForm').submit();
	});

	// 조회
	$('#btnSearch').on('click', function () {
		$('#pageNum').val(1);
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
	
		var url ='/admin/noticeInfo.do';
		var param = { noticeId: noticeId }
		var dataType = 'json'
		ajaxStart(url, param, dataType, function (data) {
			if ( Number(data.result) <= 0 ) {
				goToUri('/admin/error.do');
				return;
			}

			fillNoticeForm(data.adminNoticeInfo);
			renderSavedFiles(data.adminNoticeFileList || []);
			clearNewFiles();
			$('#delFileNos').val('');

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

		if (mode === 'I') {
			if (!confirm('공지사항을 등록하시겠습니까?')) return;
			url = '/admin/noticeReg.do';
		} else {
			if (!confirm('공지사항을 수정하시겠습니까?')) return;
			url = '/admin/noticeUpd.do';
		}

		var formData = buildNoticeFormData();
		if (!formData) return;

		ajaxWithFileStart(url, formData, function (data) {
			var result = Number(data.result);

			if (result > 0) {
				alert(mode === 'I' ? '등록되었습니다.' : '수정되었습니다.');
				window.location.reload();
			} else {
				alert(data.resultMsg || '처리에 실패했습니다.');
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
				alert('삭제되었습니다.');
				window.location.reload();
			} else {
				alert(data.resultMsg || '삭제에 실패했습니다.');
			}
		});
	});

	// 저장된 파일 다운로드
	$(document).on('click', '.btnFileDown', function () {
		var fileNo = $(this).closest('.attach-item').data('file-no');
		if (isEmpty(fileNo)) return;
		window.location.href = '/admin/fileDownload.do?fileNo=' + encodeURIComponent(fileNo);
	});

	// 저장된 파일 삭제목록 추가
	$(document).on('click', '.btnFileDel', function () {
		var $item = $(this).closest('.attach-item');
		var fileNo = String($item.data('file-no') || '');

		if (!fileNo) return;

		if (!confirm('이 파일을 삭제 목록에 추가하시겠습니까?\n수정 버튼을 눌러야 실제 반영됩니다.')) return;

		appendDelFileNo(fileNo);
		$item.remove();
		toggleSavedFileHint();
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
		var remainSavedCount = $('#savedFileList .attach-item').length;

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

		// 저장된 파일 + 신규 선택 파일 최대 5개 제한
		if (remainSavedCount + mergedFiles.length > 5) {
			alert(typeof fileLengthFiveChk !== 'undefined' ? fileLengthFiveChk : '첨부파일은 최대 5개까지 가능합니다.');
			input.value = '';
			return;
		}

		selectedAdminFiles = mergedFiles;

		syncAdminFilesInput();
		refreshNewFilesUI();
	});
	
	// 새 파일 제거
	$(document).on('click', '.btnNewFileRemove', function () {
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
	var noticeCn = $.trim($('#fDesc').val());

	var noticeLimitYn = $('#noticeLimitYn').is(':checked') ? 'Y' : 'N';
	var notcieStrDt = $('#notcieStrDt').val();
	var notcieEndDt = $('#notcieEndDt').val();

	var files = selectedAdminFiles;
	var allowExt = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'];

	var remainSavedCount = $('#savedFileList .attach-item').length;
	var totalCount = remainSavedCount + files.length;

	if (isEmpty(noticeId)) {
		alert('공지사항 번호를 먼저 생성하세요.');
		return null;
	}

	if (isEmpty(regId)) {
		alert('작성자 정보가 없습니다.');
		return null;
	}

	if (isEmpty(noticeCn)) {
		alert('공지사항 내용을 입력하세요.');
		$('#fDesc').focus();
		return null;
	}

	if (noticeLimitYn === 'Y') {
		if (isEmpty(notcieStrDt) || isEmpty(notcieEndDt)) {
			alert('기한설정 사용 시 시작날짜와 종료날짜를 모두 입력하세요.');
			return null;
		}

		if (notcieStrDt > notcieEndDt) {
			alert('종료날짜는 시작날짜보다 빠를 수 없습니다.');
			return null;
		}
	}

	if (totalCount > 5) {
		alert(typeof fileLengthFiveChk !== 'undefined' ? fileLengthFiveChk : '첨부파일은 최대 5개까지 가능합니다.');
		return null;
	}

	var formData = new FormData();

	formData.append('noticeId', noticeId);
	formData.append('regId', regId);
	formData.append('regDt', $('#regDt').val());
	formData.append('noticeCn', noticeCn);

	formData.append('noticeFixYn', $('#noticeFixYn').is(':checked') ? 'Y' : 'N');
	formData.append('noticePopYn', $('#noticePopYn').is(':checked') ? 'Y' : 'N');
	formData.append('noticeLimitYn', noticeLimitYn);

	formData.append('notcieStrDt', notcieStrDt);
	formData.append('notcieEndDt', notcieEndDt);

	formData.append('delFileNos', $('#delFileNos').val());

	for (var i = 0; i < files.length; i++) {
		var file = files[i];
		var fileName = file.name || '';
		var ext = fileName.indexOf('.') > -1 ? fileName.split('.').pop().toLowerCase() : '';

		if ($.inArray(ext, allowExt) === -1) {
			alert(typeof fileExtImgChk !== 'undefined' ? fileExtImgChk : '이미지 파일만 업로드 가능합니다.');
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
	$('#fDesc').val(noticeInfo.noticeCn || '');

	$('#noticeFixYn').prop('checked', noticeInfo.noticeFixYn === 'Y');
	$('#noticePopYn').prop('checked', noticeInfo.noticePopYn === 'Y');
	$('#noticeLimitYn').prop('checked', noticeInfo.noticeLimitYn === 'Y');

	$('#notcieStrDt').val(toDatetimeLocalValue(noticeInfo.notcieStrDt));
	$('#notcieEndDt').val(toDatetimeLocalValue(noticeInfo.notcieEndDt));

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
	$('#fDesc').val('');

	$('#noticeFixYn').prop('checked', false);
	$('#noticePopYn').prop('checked', false);
	$('#noticeLimitYn').prop('checked', false);

	$('#notcieStrDt').val('');
	$('#notcieEndDt').val('');

	$('#delFileNos').val('');

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
* 삭제 파일 번호 누적
********************************/
function appendDelFileNo(fileNo) {
	var raw = $('#delFileNos').val();
	var arr = raw ? raw.split(',') : [];

	if ($.inArray(String(fileNo), arr) === -1) {
		arr.push(String(fileNo));
	}

	$('#delFileNos').val(arr.join(','));
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
			'<li class="attach-item" data-file-no="' + file.fileNo + '">' +
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

	$('#notcieStrDt, #notcieEndDt').prop('disabled', !on);
	$('#lblNotcieStrDt, #lblNotcieEndDt').toggleClass('required', on);

	if (!on) {
		$('#notcieStrDt, #notcieEndDt').val('');
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