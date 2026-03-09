package com.inst.project.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.service.AdminBoardService;
import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.FileUtil;
import com.inst.project.util.PagerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminBoardService")
public class AdminBoardServiceImpl implements AdminBoardService {

	@Autowired
	AdminBoardMapper adminBoardMapper;
	
	/**
	* @methodName	 	: selectAdmionNotice
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminNoticeDTO> selectAdmionNotice(PagerUtil pager) {
	    log.info(" [ AdminMngServiceImpl ] : selectAdmionNotice ");

	    try {
	    	
			pager.makeRow();
			
			// 관리자 공지사항 총 건수 조회
			Long totalCount = adminBoardMapper.selectAdmionNoticeTotalCount( pager );
			pager.makeNum(totalCount);
	    	
			// 관리자 공지사항 조회
	    	List<AdminNoticeDTO> adminNoticeList = adminBoardMapper.selectAdmionNotice( pager );
		    if ( adminNoticeList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    

	        return adminNoticeList;

	    } catch (Exception e) {
	        log.error("[ AdminBoardServiceImpl ] : selectAdmionNotice failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: adminNoticeReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 저장
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int adminNoticeReg(AdminNoticeDTO adminNoticeDTO, MultipartFile[] files) {

	    int result = 0;

	    // 업로드 경로
	    String basePath = "D:/upload/notice";

	    // 저장된 파일 목록 (롤백용)
	    List<AdminFileDTO> savedFiles = new ArrayList<>();

	    try {

	        // 1. 공지사항 저장
	        result = adminBoardMapper.insertAdminNotice(adminNoticeDTO);

	        if (result <= 0) {
	            throw new RuntimeException("공지사항 등록 실패");
	        }

	        // 2. 파일 없는 경우 종료
	        if (files == null || files.length == 0) {
	            return result;
	        }

	        // 3. 파일 개수 제한
	        if (files.length > 5) {
	            throw new RuntimeException("첨부파일은 최대 5개까지 가능합니다.");
	        }

	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            // 4. 파일 저장
	            AdminFileDTO adminFileDTO = FileUtil.saveImageFile(file, basePath);

	            if (adminFileDTO == null) {
	                throw new RuntimeException("파일 저장 실패");
	            }

	            adminFileDTO.setFileId(UUID.randomUUID().toString());
	            adminFileDTO.setRefType("NOTICE");
	            adminFileDTO.setRefId(adminNoticeDTO.getNoticeId());
	            adminFileDTO.setRegId(adminNoticeDTO.getRegId());

	            // 롤백용 저장
	            savedFiles.add(adminFileDTO);

	            // 5. DB 저장
	            int fileResult = adminBoardMapper.insertAdminFile(adminFileDTO);

	            if (fileResult <= 0) {
	                throw new RuntimeException("파일 DB 저장 실패");
	            }

	        }

	        return result;

	    } catch (Exception e) {

	        log.error("[AdminBoardServiceImpl] adminNoticeReg error", e);

	        // 6. 저장된 파일 삭제 (롤백)
	        for (AdminFileDTO fileDTO : savedFiles) {

	            try {

	                FileUtil.deleteFile( fileDTO.getFilePath(), fileDTO.getFileNm() );

	            } catch (Exception ex) {

	                log.error("[FileRollback] 파일 삭제 실패 : {}", fileDTO.getFileNm());

	            }

	        }

	        throw new RuntimeException("공지사항 등록 중 오류 발생", e);
	    }
	}

	
}
