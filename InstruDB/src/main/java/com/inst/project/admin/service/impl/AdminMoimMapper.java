package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMoimDTO;
import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.util.PagerUtil;

@Mapper
public interface AdminMoimMapper {

	// 관리자 모임장소 총 건수 조회
	Long selectAdminMoimLocateListTotalCount(PagerUtil pager);

	// 관리자 모임장소 조회
	List<AdminMoimLocateDTO> selectAdminMoimLocateList(PagerUtil pager);
	
	// 관리자 모임장소 상세조회
	AdminMoimLocateDTO selectAdminMoimLocateListInfo(AdminMoimLocateDTO adminMoimLocateDTO);
	
	// 관리자 모임장소 등록
	int adminMoimLocateListReg(AdminMoimLocateDTO adminMoimLocateDTO);
	
	// 관리자 모임장소 수정
	int adminMoimLocateListUpd(AdminMoimLocateDTO adminMoimLocateDTO);

	// 관리자 모임장소 삭제
	int adminMoimLocateListDel(AdminMoimLocateDTO adminMoimLocateDTO);

	// 관리자 모임일정 총 건수 조회
	Long selectAdminMoimListTotalCount(PagerUtil pager);
	
	// 관리자 모임일정 조회
	List<AdminMoimDTO> selectAdminMoimList(PagerUtil pager);
	
	// 관리자 모임일정 > 관리자 모임장소 조회
	List<AdminMoimLocateDTO> selectAdminMoimLocateListForMoim();

	// 관리자 모임일정 > 모임구분 조회
	List<AdminCommDTO> selectAdminMoimGbList();
	
	// 관리자 모임일정 상세조회
	AdminMoimDTO selectAdminMoimListInfo(AdminMoimDTO adminMoimDTO);
	
	// 관리자 모임일정 등록
	int adminMoimListReg(AdminMoimDTO adminMoimDTO);

	// 관리자 모임일정 수정
	int adminMoimListUpd(AdminMoimDTO adminMoimDTO);

	// 관리자 모임일정 삭제
	int adminMoimListDel(AdminMoimDTO adminMoimDTO);





}
