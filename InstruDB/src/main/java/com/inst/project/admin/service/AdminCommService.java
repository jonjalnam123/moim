package com.inst.project.admin.service;

import java.util.Map;

import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;

public interface AdminCommService {

	// 관리자 유니크 값 중복 체크
	String selectUniqueDupliChk(Map<String, Object> bodyMap);

	// 관리자 유니크 값 생성
	String selectUniqueId();
	
	// 관리자 메뉴 즐겨찾기
	int insertFavoriteMenu(AdminMenuFavoriteDTO adminMenuFavoriteDTO);
	
	// 관리자 파일 다운로드
	AdminFileDTO selectFileInfo(String fileId, String refType);
	


}
