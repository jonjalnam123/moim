package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminMenuFavoriteDTO {
	
	// 메뉴 즐겨찾기ID
	private String menuFavoriteId;
	
	// 메뉴 즐쳐찾기 메뉴ID
	private String menuFavoriteMenuId;
	
	// 메뉴 즐겨찾기등록관리자ID
	private String menuFavoriteAdminId;
	
	// 즐겨찾기메뉴URL
	private String menuFavoriteUrl;
	
	// 메뉴 즐겨찾기 부모 메뉴명
	private String menuFavoritePNm;
	
	// 즐겨찾기메뉴명
	private String menuFavoriteNm;
	
	// 메뉴 즐겨찾기 여부
	private String menuFavoriteYn;
	
	// 즐겨찾기 등록 구분
	private String flag;
	
	// 등록자
	private String regId;
	
	// 등록날짜
	private String regDt;
	
	// 수정자
	private String updId;
	
	// 수정날짜
	private String updDt;
}
