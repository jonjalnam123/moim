/**
 * 작성자 : 최정석
 * 작성날짜 : 2026.03.18
 * 내용 : adminNoticePopUp 스크립트
 */

$(function () {
	
});

function fnNoticeFileDownload(fileId) {
  if (!fileId) {
    alert('다운로드할 파일 정보가 없습니다.');
    return;
  }

  location.href = '${pageContext.request.contextPath}/admin/notice/fileDownload.do?fileId=' + encodeURIComponent(fileId);
}
