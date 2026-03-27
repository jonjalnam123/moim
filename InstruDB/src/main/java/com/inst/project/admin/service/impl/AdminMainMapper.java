package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;

@Mapper
public interface AdminMainMapper {

	// 관리자 메뉴 1레벨 조회
	List<AdminMenuDTO> selectAdminMenuInfo();

	// 관리자 메뉴 2레벨 조회
	List<AdminMenuDTO> selectAdminMenuInfo2();

	// 관리자 메인 공지사항 등록 건 수 조회
	int selectAdminMainNoticeRegCnt();
	
	// 관리자 메인 공지사항 조회
	List<AdminNoticeDTO> selectAdminMainNoticeList();

	// 관리자 메인 즐겨찾기 조회
	List<AdminMenuFavoriteDTO> selectAdminMainFavMenuList();

}
