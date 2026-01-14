/**
 * 작성자 : 최정석
 * 작성날짜 : 2025.08.07
 * 내용 : 헤더 스크립트
 */

/*************************************************************
* 작성자 : 최정석
* 작성날짜 : 2025.08.08
* 내용 : 관리자 메뉴 정보
**************************************************************/
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
	
	        // 2차 메뉴 연결
	        $.each(menuSecInfo, function (j, second) {
	            if ( second.menuPId === first.menuId && second.menuLvl === 1 ) {
	                $subUl.append(` <li>
				                           		<a class="nav-link" href="${second.menuUrl}">
						                            ${second.menuNm}
						                        </a>
					                       </li> `);
	            }
	        });
	
	        $sideMenu.append($li);
	    });
			
	}
}