package com.inst.project.admin.service.impl;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminDTO;

@Mapper
public interface AdminLoginMapper {

	// 관리자 로그인 프로세스
	AdminDTO selectAdminInfo(AdminDTO adminDTO);
	
	// 관리자 로그인 이력 저장
	int insertAdminLoginLog(AdminDTO adminDTO);
	
	// 관리자 로그인 실패 이력 저장
	void insertLoginFailLog(AdminDTO adminInfo);

	// 관리자 로그아웃 이력 저장
	int insertAdminLogOutLog(AdminDTO adminInfo);

}
