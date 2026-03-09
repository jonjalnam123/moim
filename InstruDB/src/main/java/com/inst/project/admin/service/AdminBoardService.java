package com.inst.project.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.util.PagerUtil;

public interface AdminBoardService {
	
	// 관리자 공지사항 조회
	List<AdminNoticeDTO> selectAdmionNotice(PagerUtil pager);
	
	// 관리자 공지사항 저장
	int adminNoticeReg(AdminNoticeDTO adminNoticeDTO, MultipartFile[] files);


}
