/**
 * 작성자 : 최정석 
 * 작성날짜 : 2025.08.07
 * 내용 : 메인 페이지 스크립트
 */

$(function () { 
	
});

function goNoticeMore() {
  // 실제 공지사항 메뉴 URL로 교체
  location.href = "common.do?c=community&a=notice.list";
}

function goNoticeDetail(noticeId) {
  // 실제 상세 URL로 교체
  location.href = "common.do?c=community&a=notice.view&noticeId=" + encodeURIComponent(noticeId);
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
