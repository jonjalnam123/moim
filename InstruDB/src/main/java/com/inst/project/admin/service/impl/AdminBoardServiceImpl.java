package com.inst.project.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
			Long totalCount = adminBoardMapper.selectAdminNoticeTotalCount( pager );
			pager.makeNum(totalCount);
	    	
			// 관리자 공지사항 조회
	    	List<AdminNoticeDTO> adminNoticeList = adminBoardMapper.selectAdminNotice( pager );
		    if ( adminNoticeList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    

	        return adminNoticeList;

	    } catch (Exception e) {
	        log.error("[ AdminBoardServiceImpl ] selectAdmionNotice failed : {}", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: selectAdminNoticeInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public Map<String, Object> selectAdminNoticeInfo(AdminNoticeDTO adminNoticeDTO) {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminNoticeInfo ");

	    try {
	    	
	    	Map<String, Object> result = new HashMap<>();
	    	
	    	// 관리자 공지사항 상세 조회
	    	AdminNoticeDTO adminNoticeInfo = adminBoardMapper.selectAdminNoticeInfo(adminNoticeDTO);
	    	
	    	// 관리자 공지사항 첨부파일 리스트 조회
			List<AdminFileDTO> adminNoticeFileList = adminBoardMapper.selectAdminNoticeFiles(adminNoticeDTO);

		    if ( adminNoticeInfo == null && adminNoticeFileList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
		    result.put("adminNoticeInfo", adminNoticeInfo);
		    result.put("adminNoticeFileList", adminNoticeFileList);
		    
	        return result;

	    } catch (Exception e) {
	        log.error("[ AdminBoardServiceImpl ] selectAdminNoticeInfo failed : {}", e);
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
	public int adminNoticeReg(AdminNoticeDTO adminNoticeDTO, MultipartFile[] files, HttpServletRequest req) {
		log.info(" [ AdminMngServiceImpl ] : adminNoticeReg ");
		
	    int result = 0;
	    String basePath = req.getServletContext().getRealPath("/resources/static/file");
	    List<AdminFileDTO> savedFiles = new ArrayList<>();
	    
	    try {
	    	
	    	result = adminBoardMapper.insertAdminNotice(adminNoticeDTO);
	    	
	        if (result <= 0) {
	            throw new RuntimeException("공지사항 등록 실패");
	        }
	        
	        if (files == null || files.length == 0) {
	            return result;
	        }
	        
	        if (files.length > 5) {
	            throw new RuntimeException("첨부파일은 최대 5개까지 가능합니다.");
	        }
	        
	        for (MultipartFile file : files) {
	            if (file == null || file.isEmpty()) {
	                continue;
	            }
	            
	            AdminFileDTO adminFileDTO = FileUtil.saveImageFile(file, basePath);
	            if (adminFileDTO == null) {
	                throw new RuntimeException("파일 저장 실패");
	            }
	            
	            adminFileDTO.setFileId(UUID.randomUUID().toString());
	            adminFileDTO.setRefType("NOTICE");
	            adminFileDTO.setRefId(adminNoticeDTO.getNoticeId());
	            adminFileDTO.setRegId(adminNoticeDTO.getRegId());
	            
	            savedFiles.add(adminFileDTO);
	            
	            int fileResult = adminBoardMapper.insertAdminFile(adminFileDTO);
	            if (fileResult <= 0) {
	                throw new RuntimeException("파일 DB 저장 실패");
	            } 
	        }
	        
	        return result;
	        
	    } catch (Exception e) {
	        log.error("[ AdminBoardServiceImpl ] adminNoticeReg failed", e);

	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

	        for (AdminFileDTO fileDTO : savedFiles) {
	            try {
	                FileUtil.deleteFile(fileDTO.getFilePath(), fileDTO.getFileNm());
	            } catch (Exception ex) {
	                log.error("[ FileRollback ] 파일 삭제 실패 : {}", fileDTO.getFileNm(), ex);
	            }
	        }

	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminNoticeUpd
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
	public int adminNoticeUpd(AdminNoticeDTO adminNoticeDTO, List<MultipartFile> adminFiles, List<Long> deleteFileId, HttpServletRequest req) {
		log.info(" [ AdminMngServiceImpl ] : adminNoticeUpd ");
		 
	    String basePath = req.getServletContext().getRealPath("/resources/static/file");
	    
	    try {
	        List<MultipartFile> uploadFiles = normalizeFiles(adminFiles);
	        List<Long> deleteIds = normalizeDeleteNos(deleteFileId);

	        // 삭제 대상 파일 조회
	        List<AdminFileDTO> deleteTargets = Collections.emptyList();
	        if (!deleteIds.isEmpty()) {
	            deleteTargets = adminBoardMapper.selectNoticeFilesForDelete(adminNoticeDTO.getNoticeId(), deleteIds);

	            if (deleteTargets.size() != deleteIds.size()) {
	                throw new IllegalArgumentException("삭제 대상 파일 정보가 올바르지 않습니다.");
	            }
	        }

	        // 현재 파일 수 조회
	        int existingCount = adminBoardMapper.countNoticeFiles(adminNoticeDTO.getNoticeId());
	        int remainCount = existingCount - deleteTargets.size();

	        if (remainCount < 0) {
	            remainCount = 0;
	        }

	        if (remainCount + uploadFiles.size() > 5) {
	            throw new IllegalArgumentException("첨부파일은 최대 5개까지 가능합니다.");
	        }

	        // 공지사항 수정
	        int updResult = adminBoardMapper.updateAdminNotice(adminNoticeDTO);

	        if (updResult <= 0) {
	            return 0;
	        }

	        // 삭제 예정 파일 DB 삭제
	        if (!deleteIds.isEmpty()) {
	        	adminBoardMapper.deleteAdminNoticeFile(adminNoticeDTO.getNoticeId(), deleteIds);
	        }

	        // 신규 파일 저장 + DB 등록
	        for (MultipartFile file : uploadFiles) {
	            AdminFileDTO fileDto = FileUtil.saveImageFile(file, basePath);
	            fileDto.setRefId(adminNoticeDTO.getNoticeId());
	            fileDto.setRegId(adminNoticeDTO.getRegId());

	            adminBoardMapper.insertAdminNoticeFile(fileDto);
	        }

	        // 실제 파일 삭제
	        for (AdminFileDTO deleteFile : deleteTargets) {
	            FileUtil.deleteFile(deleteFile.getFilePath(), deleteFile.getFileNm());
	        }

	        return updResult;

	    } catch (IllegalArgumentException e) {
	        log.error("adminNoticeUpd validation error. noticeId={}, message={}",
	                adminNoticeDTO != null ? adminNoticeDTO.getNoticeId() : null,
	                e.getMessage(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error("adminNoticeUpd error. noticeId={}",
	                adminNoticeDTO != null ? adminNoticeDTO.getNoticeId() : null, e);
	        throw new RuntimeException("공지사항 수정 중 오류가 발생했습니다.", e);
	    }
	}
	
    private List<MultipartFile> normalizeFiles(List<MultipartFile> adminFiles) {
        if (adminFiles == null) {
            return new ArrayList<>();
        }

        return adminFiles.stream()
                .filter(file -> file != null && !file.isEmpty())
                .collect(Collectors.toList());
    }

    private List<Long> normalizeDeleteNos(List<Long> deleteFileId) {
        if (deleteFileId == null || deleteFileId.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> set = new LinkedHashSet<>(deleteFileId);
        return new ArrayList<>(set);
    }
	
}
