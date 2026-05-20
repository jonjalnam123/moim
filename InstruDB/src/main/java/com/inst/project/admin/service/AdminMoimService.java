package com.inst.project.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.util.PagerUtil;

public interface AdminMoimService {
	
	// 관리자 모임장소 조회
	List<AdminMoimLocateDTO> selectAdminMoimLocateList(PagerUtil pager);
	
	// 관리자 모임장소 상세조회
	AdminMoimLocateDTO selectAdminMoimLocateInfo(AdminMoimLocateDTO adminMoimLocateDTO);
	
	// 관리자 모임장소 등록
	int adminMoimLocateListReg(AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req);

}
