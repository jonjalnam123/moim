package com.inst.project.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.service.AdminMoimService;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminMoimDTO;
import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;
import com.inst.project.util.FileUtil;
import com.inst.project.util.PagerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMoimService")
public class AdminMoinServiceImpl implements AdminMoimService {

	@Autowired
	AdminMoimMapper adminMoimMapper;
	
	/**
	* @methodName	 	: getAdminMoimLocateList
	* @author					: 최정석
	* @date            		: 2026. 04. 23.
	* @description			: 관리자 모임 장소 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 04.23.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMoimLocateDTO> selectAdminMoimLocateList(PagerUtil pager) {
	    log.info(" [ AdminMoinServiceImpl ] : getAdminMoimLocateList ");

	    try {
	    	
			pager.makeRow();
			
			// 관리자 모임장소 총 건수 조회
			Long totalCount = adminMoimMapper.selectAdminMoimLocateListTotalCount( pager );
			pager.makeNum(totalCount);
	    	
			// 관리자 모임장소 조회
			List<AdminMoimLocateDTO> adminMoimLocateList = adminMoimMapper.selectAdminMoimLocateList( pager );
		    if ( adminMoimLocateList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }

	        return adminMoimLocateList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] selectAdminUser failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	    
	}
	
	/**
	* @methodName	 	: adminMoimLocateListReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 모임장소 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int adminMoimLocateListReg(AdminMoimDTO adminMoimDTO, HttpServletRequest req) {
		log.info(" [ AdminMngServiceImpl ] : adminMoimLocateListReg ");
		
	    int result = 0;
	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
	    	
	    	if( adminMoimDTO == null ) {
	    		return result;
	    	}
	    	
	    	adminMoimDTO.setRegId(adminId);
	    	adminMoimDTO.setUpdId(adminId);
	        
	        return adminMoimMapper.adminMoimLocateListReg(adminMoimDTO);
	        
	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMoimLocateListReg failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	
}
