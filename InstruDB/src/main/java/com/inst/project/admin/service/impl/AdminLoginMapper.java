package com.inst.project.admin.service.impl;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminDTO;

@Mapper
public interface AdminLoginMapper {

	// 관리자 로그인 프로세스
	AdminDTO selectAdminInfo(AdminDTO adminDTO);
	
	// 관리자 가입정보 없음 저장
	void insertEmptyAdminLoginLog(AdminDTO adminDTO);
	
	// 관리자 가입 미승인 상태
	void insertNotAcceptAdminLoginLog(AdminDTO adminInfo);
	
	// 관리자 가입 승인 반려 상태
	void insertRejectAdminLoginLog(AdminDTO adminInfo);
	
	//관리자가 회원등록 비밀번호 초기설정 상태
	void insertPwSetAdminLoginLog(AdminDTO adminInfo);
	
	// 관리자 로그인 이력 저장
	int insertAdminLoginLog(AdminDTO adminDTO);
	
	// 관리자 로그인 실패 이력 저장
	void insertLoginFailLog(AdminDTO adminInfo);

	// 관리자 로그아웃 이력 저장
	int insertAdminLogOutLog(AdminDTO adminInfo);

	// 관리자 비밀번호 설정
	int updateAdminLoginPwSet(AdminDTO adminDTO);



}
