package com.inst.project.admin.service;

import java.util.List;

import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.util.PagerUtil;

public interface AdminMoimService {
	
	// 관리자 모임 장소 조회
	List<AdminMoimLocateDTO> selectAdminMoimLocateList(PagerUtil pager);
	

}
