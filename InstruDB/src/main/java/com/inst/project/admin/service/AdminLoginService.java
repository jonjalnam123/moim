package com.inst.project.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.inst.project.admin.vo.AdminDTO;

public interface AdminLoginService {

	// 관리자 로그인 프로세스
	String adminLoginProc(AdminDTO adminDTO, HttpServletRequest req);

	// 관리자 로그아웃 프로세스
	String adminLogOutProc(HttpServletRequest req);

	// 관리자 비밀번호 설정
	String adminLoginPwSet(AdminDTO adminDTO);

}
