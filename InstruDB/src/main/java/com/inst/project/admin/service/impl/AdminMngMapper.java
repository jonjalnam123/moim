package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;

@Mapper
public interface AdminMngMapper {

	// 공통코드 레벨 1 조회
	List<AdminCommDTO> selectCommList();
	
	// 공통코드 레벨 2 조회
	List<AdminCommDTO> selectCommList2();

	List<AdminMenuDTO> selectMenuList();

	List<AdminMenuDTO> selectMenuList2();

}
