package com.inst.project.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMainService;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMainService")
public class AdminMainServiceImpl implements AdminMainService {

	@Autowired
	AdminMainMapper adminMainMapper;
	
	/**
	* @methodName	 	: getAdminMenuInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public Map<String, Object> selectAdminMenuInfo() {
		log.info(" [ AdminMainServiceImpl ] : selectAdminMenuInfo ");
		
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			
			// 관리자 메뉴 1레벨 조회
			List<AdminMenuDTO> menuList = adminMainMapper.selectAdminMenuInfo();
			
			// 관리자 메뉴 2레벨 조회
			List<AdminMenuDTO> menuList2 = adminMainMapper.selectAdminMenuInfo2();
			
			if ( menuList.isEmpty() && menuList2.isEmpty() ) {
				result = null;
			}
			
			result.put("menuList", menuList);
			result.put("menuList2", menuList2);
			
			return result;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectCommList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}

	}
}
