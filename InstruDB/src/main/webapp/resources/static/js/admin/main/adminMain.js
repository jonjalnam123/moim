/**
 * 작성자 		: CJS
 * 작성날짜 	: 2026.03.20
 * 내용 		: adminMain 페이지 스크립트
 */

$(function () { 

});

/*******************************
* Description : 메인 공지사항 모달 팝업 [S]
********************************/

/*******************************
* FuntionNm : goNoticeMore
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 더보기 함수
********************************/
function goNoticeMore() {
	goToUri('/admin/notice.do');
}

/*******************************
* FuntionNm : goNoticeDetail
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 상세 조회 모달 오픈
********************************/
function goNoticeDetail(noticeId) {
    if (!noticeId) {
        alert('공지사항 정보가 없습니다.');
        return;
    }

    openNoticeModal();
    loadNoticeDetailModal(noticeId);
}

/*******************************
* FuntionNm : openNoticeModal
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 모달 오픈
********************************/
function openNoticeModal() {
    $('#noticeModalBody').html('<div class="notice-modal-loading">공지사항 정보를 불러오는 중입니다.</div>');
    $('#noticeDetailModal').addClass('is-open').attr('aria-hidden', 'false');
    $('body').addClass('modal-open');
}

/*******************************
* FuntionNm : closeNoticeModal
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 모달 닫기
********************************/
function closeNoticeModal() {
    $('#noticeDetailModal').removeClass('is-open').attr('aria-hidden', 'true');
    $('body').removeClass('modal-open');
    $('#noticeModalBody').html('<div class="notice-modal-loading">공지사항 정보를 불러오는 중입니다.</div>');
}

/*******************************
* FuntionNm : closeNoticeModal
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 모달 정보 조회
********************************/
function loadNoticeDetailModal(noticeId) {
    $('#noticeModalBody').load( '/admin/noticePopUp.do?noticeId=' + encodeURIComponent(noticeId), function (response, status, xhr) {
            if (status !== 'success') {
                $('#noticeModalBody').html('<div class="notice-modal-empty">공지사항 정보를 불러오지 못했습니다.</div>');
            }
        }
    );
}

/*******************************
* FuntionNm : closeNoticeModal
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 모달 파일 다운로드
********************************/
function downloadNoticeFile(fileId) {
    if (!fileId) {
        alert('다운로드할 파일 정보가 없습니다.');
        return;
    }

    window.location.href = '/admin/fileDown.do?fileId=' + encodeURIComponent(fileId) + '&refType=' + encodeURIComponent('NOTICE');
}

/*******************************
* Description : 메인 공지사항 모달 팝업 [E]
********************************/

/*******************************
* Description : 메인 즐겨찾기 [S]
********************************/

/*******************************
* FuntionNm : goMenu
* Date : 2026.03.18
* Author : CJS
* Description : 메인 즐겨찾기 경로 이동
********************************/
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

/*******************************
* Description : 메인 즐겨찾기 [E]
********************************/
