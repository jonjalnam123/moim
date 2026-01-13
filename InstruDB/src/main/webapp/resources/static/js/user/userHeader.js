/**
 * Name : userHeader.js
 * Description	: 사용자 헤더
 * Modification Information
 */

$(function () {
    var $toggle = $('.idb-nav-mobile-toggle');
    var $panel  = $('.idb-nav-mobile-panel');

    // 토글 버튼 클릭 시 모바일 메뉴 열기/닫기
    $toggle.on('click', function () {
        $panel.toggleClass('is-open');
    });

    // 화면이 다시 넓어질 때(데스크톱 사이즈 복귀) 모바일 메뉴는 강제로 닫기
    var mq = window.matchMedia('(min-width: 961px)');
    function handleScreenChange(e) {
        if (e.matches) {
            $panel.removeClass('is-open');
        }
    }
    handleScreenChange(mq);
    if (mq.addEventListener) {
        mq.addEventListener('change', handleScreenChange);
    } else if (mq.addListener) { // 구형 브라우저 대응
        mq.addListener(handleScreenChange);
    }
	
	$('#goToAdminBtn').on('click', function(){
		window.location.href = '/admin/login.do';
	});
});