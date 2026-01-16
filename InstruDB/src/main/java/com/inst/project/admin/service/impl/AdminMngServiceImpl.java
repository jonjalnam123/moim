package com.inst.project.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMngService")
public class AdminMngServiceImpl implements AdminMngService {

	@Autowired
	AdminMngMapper adminMngMapper;
	
	/**
	* @methodName	 	: selectCommList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectCommList() {
		log.info(" [ AdminMngServiceImpl ] : selectCommList ");
		try {

			List<AdminCommDTO> adminCommList = adminMngMapper.selectCommList();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminCommList);
			
			return adminCommList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectCommList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}

	}
	
	/**
	* @methodName	 	: selectCommList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectCommList2() {
		log.info(" [ AdminMngServiceImpl ] : selectCommList2 ");
		try {
			
			List<AdminCommDTO> adminCommList2 = adminMngMapper.selectCommList2();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminCommList2);
			
			return adminCommList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectCommList2 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}
		
	}
	
	/**
	* @methodName	 	: selectMenuList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMenuDTO> selectMenuList() {
		log.info(" [ AdminMngServiceImpl ] : selectMenuList ");
		
		try {
			
			List<AdminMenuDTO> adminMenuList = adminMngMapper.selectMenuList();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminMenuList);
			
			return adminMenuList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectMenuList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectMenuList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMenuDTO> selectMenuList2() {
		log.info(" [ AdminMngServiceImpl ] : selectMenuList2 ");
		try {
			
			List<AdminMenuDTO> adminMenuList2= adminMngMapper.selectMenuList2();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminMenuList2);
			
			return adminMenuList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectMenuList2 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectUnitAllList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitAllList() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitAllList ");
		
		try {
			
			List<AdminUnitDTO> adminUnitAllList = adminMngMapper.selectUnitAllList();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminUnitAllList);
			
			return adminUnitAllList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectUnitAllList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}
		
	}
	
	/**
	* @methodName	 	: selectUnitList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList ");
		try {
			
			List<AdminUnitDTO> adminUnitList= adminMngMapper.selectUnitList();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminUnitList);
			
			return adminUnitList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectUnitList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectUnitList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList2() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList2 ");
		try {
			
			List<AdminUnitDTO> adminUnitList2= adminMngMapper.selectUnitList2();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminUnitList2);
			
			return adminUnitList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : adminUnitList2 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectUnitList3
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 3레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList3() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList3 ");
		try {
			
			List<AdminUnitDTO> adminUnitList3= adminMngMapper.selectUnitList3();
			log.info(GlobalConfig.RESULT_LIST_MSG, adminUnitList3);
			
			return adminUnitList3;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : adminUnitList3 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
}
