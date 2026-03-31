/**
 * 작성자 		: CJS
 * 작성날짜 	: 2025.08.07
 * 내용 		: adminHeader 스크립트
 */

$(function () {	
	getAdminMenuInfo();
});

function getAdminMenuInfo() {
	var url = '/admin/menuInfo.do';
	var params = {}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(data) {
		if ( isEmpty(data) ) {
			goToUriAdminError();
			return;
		} 
		
		setMenuInfo(data);
	});
}

function setMenuInfo(menuInfo) {
	
	if(isEmpty(menuInfo)) {
		
		var $sideMenu = $('#sideMenu');
		$sideMenu.empty();

		var $fli = $(` <li>
							 <a class="nav-link" href="/admin/main.do">
							 	<i class="fas fa-home"></i> 홈
							 </a>
						  </li> `)
						  
		$sideMenu.append($fli);
		
	} else {
		
	    var menuFirstInfo = menuInfo.menuList;
	    var menuSecInfo   = menuInfo.menuList2;
	
	    var $sideMenu = $('#sideMenu');
	    $sideMenu.empty();
		
		var $fli = $(` <li>
							 <a class="nav-link" href="/admin/main.do">
							 	<i class="fas fa-home"></i> 홈
							 </a>
						  </li> `)
						  
		$sideMenu.append($fli);
	
	    $.each(menuFirstInfo, function (i, first) {
			
			var collapseId = 'collapse_' + first.menuId;
			
			if ( first.menuLvl === 0 && first.menuSort === 0 ) {
				return;
			}
	
	        // 1차 메뉴
	        var $li = $(`<li>
	                			<a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#${collapseId}" aria-expanded="false" aria-controls="${collapseId}">
		                    		<i class="${first.menuIcon || 'fas fa-folder'}"></i>
		                    		<span>${first.menuNm}</span>
									<span class="sb-sidenav-collapse-arrow">
		                        		<i class="fas fa-angle-down"></i>
		                    		</span>
	                			</a>
				                <div class="collapse" id="${collapseId}" data-bs-parent="#sidenavAccordion">
				                    <ul></ul>
				                </div>
				            </li> `);
	
	        var $subUl = $li.find('ul');
	
			/*href="${second.menuUrl}"*/
	        // 2차 메뉴 연결
	        $.each(menuSecInfo, function (j, second) {
	            if ( second.menuPId === first.menuId && second.menuLvl === 1 ) {
	                $subUl.append(` <li>
				                           		<a class="nav-link" id="myPageBtn" href="javascript:void(0);" onclick="setMenuToSession('${second.menuId}', '${first.menuNm}', '${second.menuUrl}', '${second.menuNm}', '${second.menuFavoriteYn}');" > 
													${second.menuNm}
						                        </a>
					                       </li> `);
	            }
	        });
			
	        $sideMenu.append($li);
	    });
			
	}
}

/*******************************
* FuntionNm : setMenuToSession
* Date : 2025.10.02
* Author : CJS
* Description : 메뉴 세션 저장
* PARAM : menuId : 메뉴ID,  menuPNm : 메뉴부모명, menuUrl : 메뉴 경로, menuNm : 메뉴명
********************************/
function setMenuToSession(menuId, menuPNm, menuUrl, menuNm, menuFavoriteYn) {
	
	if ( isEmptyMsg(menuId, '메뉴ID' + dataNull) ) {
		return;
	}
	
	if ( isEmptyMsg(menuPNm, '메뉴부모이름' + dataNull) ) {
		return;
	}

	if ( isEmptyMsg(menuUrl, '메뉴경로' + dataNull) ) {
		return;
	}
	
	if ( isEmptyMsg(menuNm, '메뉴명' + menuNm) ) {
		return;
	}
	
	console.log('menuFavoriteYn===', menuFavoriteYn);
	
	var url = '/admin/menuToSession.do';
	var params = {
		menuId : menuId
	  , menuPNm : menuPNm
      , menuUrl : menuUrl
	  , menuNm : menuNm
	  , menuFavoriteYn : menuFavoriteYn
	}
	var dataType = 'json'
	ajaxStart(url, params, dataType, function(result) {
		if ( result.menuInfoResult ) {
			goToUri(menuUrl)
		} else { 
			goToUri('/admin/login.do')
		}
	
	});

} 