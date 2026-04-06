package com.inst.project.admin.service.impl;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminAopDTO;

@Mapper
public interface AdminAopMapper {

	// 관리자 로그 이력 저장
	void adminLognInsert(AdminAopDTO vo);

}
