/**
 * 작성자 : 최정석 
 * 작성날짜 : 2025.08.07
 * 내용 : 메인 페이지 스크립트
 */

$(function () { 

});

function goNoticeMore() {
	goToUri('/admin/notice.do');
}

function goNoticeDetail(noticeId) {
    if (!noticeId) {
        alert('공지사항 정보가 없습니다.');
        return;
    }

    openNoticeModal();
    loadNoticeDetailModal(noticeId);
}

function openNoticeModal() {
    $('#noticeModalBody').html('<div class="notice-modal-loading">공지사항 정보를 불러오는 중입니다.</div>');
    $('#noticeDetailModal').addClass('is-open').attr('aria-hidden', 'false');
    $('body').addClass('modal-open');
}

function closeNoticeModal() {
    $('#noticeDetailModal').removeClass('is-open').attr('aria-hidden', 'true');
    $('body').removeClass('modal-open');
    $('#noticeModalBody').html('<div class="notice-modal-loading">공지사항 정보를 불러오는 중입니다.</div>');
}

function loadNoticeDetailModal(noticeId) {
    $('#noticeModalBody').load( '/admin/noticePopUp.do?noticeId=' + encodeURIComponent(noticeId), function (response, status, xhr) {
            if (status !== 'success') {
                $('#noticeModalBody').html('<div class="notice-modal-empty">공지사항 정보를 불러오지 못했습니다.</div>');
            }
        }
    );
}

function downloadNoticeFile(fileId) {
    if (!fileId) {
        alert('다운로드할 파일 정보가 없습니다.');
        return;
    }

    location.href = '/admin/fileDownload.do?fileId=' + encodeURIComponent(fileId) + '?refType=' + + encodeURIComponent('NOTICE');
}

function goMenu(type) {
  switch (type) {
    case "member":
      location.href = "common.do?c=member&a=list";
      break;
    case "meeting":
      location.href = "common.do?c=meeting&a=list";
      break;
    case "attendance":
      location.href = "common.do?c=attendance&a=list";
      break;
    case "settings":
      location.href = "common.do?c=system&a=settings";
      break;
    default:
      break;
  }
}
