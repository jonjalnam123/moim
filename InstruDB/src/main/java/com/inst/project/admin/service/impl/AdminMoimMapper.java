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
	
	// 관리자 모임장소 상세조회
	AdminMoimLocateDTO selectAdminMoimLocateInfo(AdminMoimLocateDTO adminMoimLocateDTO);
	
	// 관리자 모임장소 등록
	int adminMoimLocateListReg(AdminMoimLocateDTO adminMoimLocateDTO);
	
	// 관리자 모임장소 수정
	int adminMoimLocateListUpd(AdminMoimLocateDTO adminMoimLocateDTO);

	// 관리자 모임장소 삭제
	int adminMoimLocateListDel(AdminMoimLocateDTO adminMoimLocateDTO);





}
