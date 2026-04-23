package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.util.PagerUtil;

@Mapper
public interface AdminMoimMapper {

	// 관리자 모임장소 총 건수 조회
	Long selectAdminMoimLocateListTotalCount(PagerUtil pager);

	// 관리자 모임 장소 조회
	List<AdminMoimLocateDTO> selectAdminMoimLocateList(PagerUtil pager);

}
