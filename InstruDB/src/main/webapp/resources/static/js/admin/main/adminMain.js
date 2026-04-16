/**
 * 작성자 		: CJS
 * 작성날짜 	: 2026.03.20
 * 내용 		: adminMain 페이지 스크립트
 */

$(function () { 
	
	setMainNotice();
	
	$(document).off('click', '#mainNoticeMoreBtn').on('click', '#mainNoticeMoreBtn', function () {
	    goNoticeMore();
	});

	$(document).off('click', '#mainNoticeArticle .main-notice-detail-btn')
	           .on('click', '#mainNoticeArticle .main-notice-detail-btn', function () {
	    var noticeId = $(this).data('notice-id');
	    goNoticeDetail(noticeId);
	});
	
});


/*******************************
* Description : 메인 공지사항 셋팅 [S]
********************************/

/*******************************
* FuntionNm : setMainNotice
* Date : 2026.03.18
* Author : CJS
* Description : 메인 공지사항 셋팅
********************************/
function setMainNotice() {

	var url = '/admin/setMainNotice.do';
	var params = {};
	var dataType = 'json';

	ajaxStart(url, params, dataType, function (data) {
	    console.log('main notice data : ', data);

	    // 응답 구조가 캡처처럼 바로 들어오는 경우
	    renderMainNotice(data);

	    // 만약 실제 응답이 { data : {...} } 구조면 아래로 바꾸면 됨
	    // renderMainNotice(data.data);
	});
}

function renderMainNotice(data) {
    var regCnt = nvl(data.adminMainNoticeCnt, '0');
    var noticeList = $.isArray(data.adminMainNoticeList) ? data.adminMainNoticeList : [];
    var html = [];

    html.push('<div class="dash-panel-header">');
    html.push('  <div class="dash-panel-title-wrap">');
    html.push('    <h3 class="dash-panel-title">공지사항</h3>');
    html.push('    <p class="dash-panel-sub">오늘 등록된 공지 ' + escapeHtml(regCnt) + '건</p>');
    html.push('  </div>');
    html.push('  <button type="button" class="notice-more-btn" id="mainNoticeMoreBtn">더보기</button>');
    html.push('</div>');

    html.push('<div class="dash-panel-body">');

    if (noticeList.length > 0) {
        html.push('<ul class="notice-list">');

        $.each(noticeList.slice(0, 10), function (idx, notice) {
            var noticeId = escapeHtml(nvl(notice.noticeId, ''));
            var noticeTitle = escapeHtml(nvl(notice.noticeTitle, ''));
            var noticeCn = escapeHtml(truncateText(notice.noticeCn, 30));
            var regDt = escapeHtml(nvl(notice.regDt, ''));

            html.push('<li class="notice-item">');
            html.push(getNoticeBadgeHtml(nvl(notice.noticeEffectGb, '')));
            html.push('  <div class="notice-content">');
            html.push('    <a href="javascript:void(0);" class="notice-title main-notice-detail-btn" data-notice-id="' + noticeId + '">');
            html.push(          noticeTitle);
            html.push('    </a>');
            html.push('    <div class="notice-desc">' + noticeCn + '</div>');
            html.push('  </div>');
            html.push('  <div class="notice-date">' + regDt + '</div>');
            html.push('</li>');
        });

        html.push('</ul>');
    } else {
        html.push('<div class="grid-empty-wrap">');
        html.push('  <div class="table-empty">');
        html.push('    <div class="table-empty-title">등록된 공지사항이 없습니다.</div>');
        html.push('    <div class="table-empty-desc">새 공지가 등록되면 이 영역에 표시됩니다.</div>');
        html.push('  </div>');
        html.push('</div>');
    }

    html.push('</div>');

    $('#mainNoticeArticle').html(html.join(''));
}

function getNoticeBadgeHtml(noticeEffectGb) {
    if (noticeEffectGb === 'I') {
        return '<span class="notice-badge is-important">중요</span>';
    } else if (noticeEffectGb === 'E') {
        return '<span class="notice-badge is-event">이벤트</span>';
    }
    return '<span class="notice-badge">일반</span>';
}

/*******************************
* Description : 메인 공지사항 셋팅 [E]
********************************/


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
* FuntionNm : loadNoticeDetailModal
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
* FuntionNm : downloadNoticeFile
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
