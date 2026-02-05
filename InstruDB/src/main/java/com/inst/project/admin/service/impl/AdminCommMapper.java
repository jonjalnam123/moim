package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;

@Mapper
public interface AdminCommMapper {
	
	// 관리자 유니크 값 중복 체크
	int selectUniqueDupliChk(Map<String, Object> bodyMap);

}
