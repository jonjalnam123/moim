package com.inst.project.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;

public interface AdminMainService {

	// 관리자 메뉴 조회
	Map<String, Object> selectAdminMenuInfo( HttpServletRequest req );
	
	// 관리자 메인 공지사항 등록 건 수 조회
	int selectAdminMainNoticeRegCnt();
	
	// 관리자 메인 공지사항 조회
	List<AdminNoticeDTO> selectAdminMainNoticeList();
	
	// 관리자 메인 즐겨찾기 조회
	List<AdminMenuDTO> selectAdminMainFavMenuList();

}
