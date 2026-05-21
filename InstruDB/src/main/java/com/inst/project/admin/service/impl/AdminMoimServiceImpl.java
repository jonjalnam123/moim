package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inst.project.admin.service.AdminMoimService;
import com.inst.project.admin.vo.AdminMoimDTO;
import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;
import com.inst.project.util.PagerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMoimService")
public class AdminMoimServiceImpl implements AdminMoimService {

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
	    log.info(" [ AdminMoimServiceImpl ] : getAdminMoimLocateList ");

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
	        log.error("[ AdminMoimServiceImpl ] getAdminMoimLocateList failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	    
	}
	
	/**
	* @methodName	 	: selectMoimLocateInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 모임장소 상세조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminMoimLocateDTO selectAdminMoimLocateInfo(AdminMoimLocateDTO adminMoimLocateDTO) {
		log.info(" [ AdminMoimServiceImpl ] : selectMoimLocateInfo ");

	    try {
	    	String locateId = adminMoimLocateDTO.getLocateId();
	    	if( CommonUtil.isBlank(locateId) ) {
	    		log.error("조회할 파라미터가 없습니다. {}", locateId);
	    		return null;
	    	}
	    	
	    	AdminMoimLocateDTO result = adminMoimMapper.selectAdminMoimLocateInfo(adminMoimLocateDTO);
	    	if( result == null ) {
	    		return null;
	    	}
	        
	        return result;
	        
	    } catch (Exception e) {
	        log.error("[ AdminMoimServiceImpl ] selectMoimLocateInfo failed", e);
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
	public int adminMoimLocateListReg(AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req) {
		log.info(" [ AdminMoimServiceImpl ] : adminMoimLocateListReg ");
		
	    int result = 0;
	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
	    	
	    	if( adminMoimLocateDTO == null ) {
	    		log.error("[ AdminMoimServiceImpl ] adminMoimLocateListReg 모임장소 정보가 없습니다 : {}", adminMoimLocateDTO);
	    		return result;
	    	}
	    	
	    	adminMoimLocateDTO.setRegId(adminId);
	    	adminMoimLocateDTO.setUpdId(adminId);
	        
	        return adminMoimMapper.adminMoimLocateListReg(adminMoimLocateDTO);
	        
	    } catch (Exception e) {
	        log.error("[ AdminMoimServiceImpl ] adminMoimLocateListReg failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminMoimLocateListUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 모임장소 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminMoimLocateListUpd(AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req) {
		log.info(" [ AdminMoimServiceImpl ] : adminMoimLocateListUpd ");
		
	    int result = 0;
	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
	    	
	    	if( adminMoimLocateDTO == null ) {
	    		log.error("[ AdminMoimServiceImpl ] adminMoimLocateListUpd 모임장소 정보가 없습니다 : {}", adminMoimLocateDTO);
	    		return result;
	    	}
	    	
	    	adminMoimLocateDTO.setUpdId(adminId);
	        
	        return adminMoimMapper.adminMoimLocateListUpd(adminMoimLocateDTO);
	        
	    } catch (Exception e) {
	        log.error("[ AdminMoimServiceImpl ] adminMoimLocateListUpd failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
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
	public int adminMoimLocateListDel(AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req) {
		log.info(" [ AdminMoimServiceImpl ] : adminMoimLocateListDel ");
		
	    int result = 0;
	    try {
	    	
	    	String locateId = adminMoimLocateDTO.getLocateId();
	    	if( CommonUtil.isBlank(locateId) ) {
	    		log.error("[ AdminMoimServiceImpl ] adminMoimLocateListDel 모임장소ID가 없습니다 : {}", locateId);
	    		return result;
	    	}
	    	
	        return adminMoimMapper.adminMoimLocateListDel(adminMoimLocateDTO);
	        
	    } catch (Exception e) {
	        log.error("[ AdminMoimServiceImpl ] adminMoimLocateListDel failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: getAdminMoimList
	* @author					: 최정석
	* @date            		: 2026. 04. 23.
	* @description			: 관리자 모임 일정 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 04.23.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMoimDTO> selectAdminMoimList( PagerUtil pager ) {
		log.info(" [ AdminMoimServiceImpl ] : selectAdminMoimList ");
	    try {
	    	
			pager.makeRow();
			
			// 관리자 모임장소 총 건수 조회
			Long totalCount = adminMoimMapper.selectAdminMoimListTotalCount( pager );
			pager.makeNum(totalCount);
	    	
			// 관리자 모임장소 조회
			List<AdminMoimDTO> adminMoimList = adminMoimMapper.selectAdminMoimList( pager );
		    if ( adminMoimList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }

	        return adminMoimList;

	    } catch (Exception e) {
	        log.error("[ AdminMoimServiceImpl ] selectAdminMoimList failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	
}
