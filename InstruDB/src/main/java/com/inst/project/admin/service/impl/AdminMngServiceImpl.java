package com.inst.project.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
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
		
		// 공통코드 레벨 1 조회
		List<AdminCommDTO> adminCommList = adminMngMapper.selectCommList();
		log.info(GlobalConfig.RESULT_LIST_MSG, adminCommList);
		
		return adminCommList;
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
		
		// 공통코드 레벨 2 조회
		List<AdminCommDTO> adminCommList2 = adminMngMapper.selectCommList2();
		log.info(GlobalConfig.RESULT_LIST_MSG, adminCommList2);
		
		return adminCommList2;
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
		
		// 공통코드 레벨 2 조회
		List<AdminMenuDTO> adminMenuList = adminMngMapper.selectMenuList();
		log.info(GlobalConfig.RESULT_LIST_MSG, adminMenuList);
		
		return adminMenuList;
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
		// 공통코드 레벨 2 조회
		List<AdminMenuDTO> adminMenuList2= adminMngMapper.selectMenuList2();
		log.info(GlobalConfig.RESULT_LIST_MSG, adminMenuList2);
		
		return adminMenuList2;
	}
}
