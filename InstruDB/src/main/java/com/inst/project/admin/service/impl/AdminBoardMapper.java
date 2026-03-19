package com.inst.project.admin.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.util.PagerUtil;

@Mapper
public interface AdminBoardMapper {
	
	// 관리자 공지사항 총 건수 조회
	Long selectAdminNoticeTotalCount(PagerUtil pager);

	// 관리자 공지사항 조회
	List<AdminNoticeDTO> selectAdminNotice(PagerUtil pager);
	
	// 관리자 공지사항 중요도 코드 조회
	List<AdminCommDTO> selectAdminNoticeEffectList();
	
	// 관리자 공지사항 상세 조회
	AdminNoticeDTO selectAdminNoticeInfo(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 공지사항 첨부파일 리스트 조회
	List<AdminFileDTO> selectAdminNoticeFiles(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 첨부파일 저장
	int insertAdminFile(AdminFileDTO adminFileDTO);

	// 관리자 공지사항 저장
	int insertAdminNotice(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 공지사항 삭제 대상 파일 조회
	List<AdminFileDTO> selectNoticeFilesForDelete(String noticeId, List<String> deleteIds);
	
	// 관리자 공지사항 현재 파일 수 조회
	int countNoticeFiles(String noticeId);

	// 관리자 공지사항 수정
	int updateAdminNotice(AdminNoticeDTO adminNoticeDTO);
	
	// 관리자 공지사항 파일 삭제
	int deleteAdminNoticeFile(String noticeId, List<String> deleteIds);
	
	// 관리자 공지사항 신규 파일 저장
	int insertAdminNoticeFile(AdminFileDTO fileDto);
	
	// 관리자 공지사항 삭제용 파일 조회
	List<AdminFileDTO> selectNoticeFileInfo(String noticeId);
	
	// 관리자 공지사항 삭제
	int deleteAdminNotice(AdminNoticeDTO adminNoticeDTO);

	// 관리자 공지사항 삭제시 파일 삭제
	void adminNoticeFileDel(String noticeId);









}
