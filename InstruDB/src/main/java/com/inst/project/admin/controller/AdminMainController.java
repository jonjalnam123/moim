package com.inst.project.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inst.project.admin.service.AdminMainService;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminMainController {
	
	@Autowired
	AdminMainService adminMainService;
	
	/**
	* @methodName	 	: getAdminMain
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메인 화면 호출
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/main.do")
	public String getAdminMain( Model model ) {
		log.info(" [ AdminMainController ] : getAdminMain ");
		return "admin/main/adminMain.adm";
	}
	
	/**
	* @methodName	 	: getAdminInfoToSession
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 정보 세션 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/sessionInfo.do")
	@ResponseBody
	public Object getAdminInfoToSession( HttpSession session ) {
		log.info(" [ AdminMainController ] : getAdminInfoToSession ");
		
	    Object adminInfo = session.getAttribute("ADMIN_INFO");
	    if ( adminInfo == null ) {
	    	return null;
	    }
	    
		return adminInfo;
	}
	
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
	@PostMapping(value = "/menuInfo.do")
	@ResponseBody
	public Map<String,Object> getAdminMenuInfo( HttpSession session ) {
		log.info(" [ AdminMainController ] : getAdminMenuInfo ");
		
		Map<String,Object> result = adminMainService.selectAdminMenuInfo();
		if ( result.isEmpty() || result == null ) {
			log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
			result = null;
		}
		
		return result;
	}
}
