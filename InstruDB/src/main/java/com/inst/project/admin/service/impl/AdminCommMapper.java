package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;

@Mapper
public interface AdminCommMapper {
	
	// 관리자 메뉴명 유니크 값 중복 체크
	int selectMenuNmDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 코드그룹 유니크 값 중복 체크
	int selectCommGroupCdDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 부서코드 유니크 값 중복 체크
	int selectUnitCdDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 ID 중복 체크 
	int selectAdminIdDupliChk(Map<String, Object> bodyMap);
	
	// 관리자 유니크 값 생성
	String selectUniqueId();
	
	// 관리자 메뉴 즐겨찾기 등록
	int insertFavoriteMenu(AdminMenuFavoriteDTO adminMenuFavoriteDTO);
	
	// 관리자 메뉴 즐겨찾기 삭제
	int deleteFavoriteMenu(AdminMenuFavoriteDTO adminMenuFavoriteDTO);
	
	// 관리자 메뉴 즐겨찾기 여부 수정
	int updFavoriteMenu(AdminMenuFavoriteDTO adminMenuFavoriteDTO);
	
	// 파일 다운로드용 파일 정보 조회
	AdminFileDTO selectFileInfo(String fileId, String refType);




}
