package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.util.PagerUtil;

@Mapper
public interface AdminBoardMapper {
	
	// 관리자 공지사항 총 건수 조회
	Long selectAdminNoticeTotalCount(PagerUtil pager);

	// 관리자 공지사항 조회
	List<AdminNoticeDTO> selectAdminNotice(PagerUtil pager);
	
	// 관리자 공지사항 상세 조회
	AdminNoticeDTO selectAdminNoticeInfo(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 공지사항 첨부파일 리스트 조회
	List<AdminFileDTO> selectAdminNoticeFiles(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 첨부파일 저장
	int insertAdminFile(AdminFileDTO adminFileDTO);

	// 관리자 공지사항 저장
	int insertAdminNotice(AdminNoticeDTO adminNoticeDTO);







}
