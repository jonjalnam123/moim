package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminMenuDTO;

@Mapper
public interface AdminMainMapper {

	// 관리자 메뉴 1레벨 조회
	List<AdminMenuDTO> selectAdminMenuInfo();

	// 관리자 메뉴 2레벨 조회
	List<AdminMenuDTO> selectAdminMenuInfo2();

}
