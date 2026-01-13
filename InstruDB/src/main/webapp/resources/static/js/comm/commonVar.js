/**
 * Name commonVar.js
 * Description	공통 전역변수
 * Modification Information
 */

/*************************************************************
* 작성자 : 최정석
* 작성날짜 : 2025.08.08
* 내용 : 관리자 메뉴 정보
**************************************************************/

var menuId 				= '';
var menuPId 			= '';
var menuNm 			= '';
var menuUrl				= '';
var menuDeptId		= '';
var menuTeaId			= '';
var menuPositionId	= '';

$(function () {	
	getAdminMenuInfo();
});

function getAdminMenuInfo() {
	var url = '/admin/menuInfo.do';
	var params = {}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(data) {
		setMenuInfo(data);
	});
}

function setMenuInfo(menuInfo) {

    var menuFirstInfo = menuInfo.menuList;
    var menuSecInfo   = menuInfo.menuList2;

    var $sideMenu = $('#sideMenu');
    $sideMenu.empty();

    $.each(menuFirstInfo, function (i, first) {

        var collapseId = 'collapse_' + first.menuId;

        // 1차 메뉴
        var $li = $(`
            <li>
                <a class="nav-link collapsed" href="#"
                   data-bs-toggle="collapse"
                   data-bs-target="#${collapseId}"
                   aria-expanded="false"
                   aria-controls="${collapseId}">
                    <i class="${first.icon || 'fas fa-folder'}"></i>
                    <span>${first.menuNm}</span>
                    <span class="sb-sidenav-collapse-arrow">
                        <i class="fas fa-angle-down"></i>
                    </span>
                </a>
                <div class="collapse" id="${collapseId}">
                    <ul class="nav flex-column ms-3"></ul>
                </div>
            </li>
        `);

        var $subUl = $li.find('ul');

        // 2차 메뉴 연결
        $.each(menuSecInfo, function (j, second) {
            if (second.menuPId === first.menuId) {
                $subUl.append(`
                    <li>
                        <a class="nav-link" href="${second.menuUrl}">
                            ${second.menuNm}
                        </a>
                    </li>
                `);
            }
        });

        $sideMenu.append($li);
    });
}

/*************************************************************
* 작성자 : 최정석
* 작성날짜 : 2025.08.08
* 내용 : 관리자 정보
**************************************************************/

var adminId 			= '';
var adminIp 			= '';
var adminNm 			= '';
var adminDeptCd 		= '';
var adminTeamCd 	= '';
var adminPositionCd 	= '';

$(function () {	
	getAdminInfoToSession();
});

function getAdminInfoToSession() {
	var url = '/admin/sessionInfo.do';
	var params = {}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(data) {
		setAdminInfo(data);
	});
}

function setAdminInfo(adminInfo) {
	adminId = adminInfo.adminId;
	adminIp = adminInfo.adminIp;
	adminNm = adminInfo.adminNm;
	adminDeptCd = adminInfo.adminDeptCd;
	adminTeamCd = adminInfo.adminTeamCd;
	adminPositionCd = adminInfo.adminPositionCd;
}