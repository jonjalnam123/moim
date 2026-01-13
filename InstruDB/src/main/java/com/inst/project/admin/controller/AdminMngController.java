package com.inst.project.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/admin")
public class AdminMngController {
	
	@Autowired
	AdminMngService adminMngService;
	
	/**
	* @methodName	 	: getAdminComm
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 관리 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value="/comm.do")
	public String getAdminComm(Model model) {
		log.info(" [ AdminMngController ] : getAdminComm ");
		
		// 공통코드 레벨 1 조회
		List<AdminCommDTO> adminCommList = adminMngService.selectCommList();
		
		// 공통코드 레벨 2 조회
		List<AdminCommDTO> adminCommList2 = adminMngService.selectCommList2();
		
		if( adminCommList == null || adminCommList2 == null) {
			log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
			return "admin/all/adminError.adm";
		}

		model.addAttribute("commList", adminCommList);
		model.addAttribute("commList2", adminCommList2);
		
		return "admin/mng/adminComm.adm";
	}
	
	/**
	* @methodName	 	: getAdminMenu
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴관리 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value="/menu.do")
	public String getAdminMenu(Model model) {
		log.info(" [ AdminMngController ] : getAdminMenu ");
		
		// 메뉴 레벨 1 조회
		List<AdminMenuDTO> adminMenuList = adminMngService.selectMenuList();
		
		// 메뉴 레벨 2 조회
		List<AdminMenuDTO> adminMenuList2 = adminMngService.selectMenuList2();
		
		if( adminMenuList == null || adminMenuList2 == null) {
			log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
			return "admin/all/adminError.adm";
		}

		model.addAttribute("menuList", adminMenuList);
		model.addAttribute("menuList2", adminMenuList2);
		
		return "admin/mng/adminMenu.adm";
	}

}
