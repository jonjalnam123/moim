package com.inst.project.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMoimService;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;
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
	
	
}
