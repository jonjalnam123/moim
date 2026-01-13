package com.inst.project.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminDTO;

public interface AdminLoginService {

	// 관리자 로그인 프로세스
	String adminLoginProc(AdminDTO adminDTO, HttpServletRequest req);

	String adminLogOutProc(HttpServletRequest req);

}
