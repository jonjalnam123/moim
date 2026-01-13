package com.inst.project.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inst.project.admin.service.AdminLoginService;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminLoginController {

	@Autowired
	AdminLoginService adminLoginService;
	
	/**
	* @methodName	 	: getAdminLogin
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그인 화면 호출
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/login.do")
	public String getAdminLogin() {
		log.info(" [ AdminLoginController ] : getAdminLogin ");
		return "admin/login/adminLogin.none";
	}
	
	/**
	* @methodName	 	: adminLoginProc
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그인 프로세스
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/loginProc.do")
	@ResponseBody
	public Map<String,Object> adminLoginProc( @ModelAttribute AdminDTO adminDTO, HttpServletRequest req ) {
		log.info(" [ AdminLoginController ] : adminLoginProc ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		String resultData = adminLoginService.adminLoginProc(adminDTO, req);
		
		if ( resultData.equals("Y") ) {
			result.put("result", resultData);
			result.put("resultCd", GlobalConfig.RESULT_SUCC_CD);
			result.put("resultMsg", GlobalConfig.RESULT_SUCC_MSG);
		} else {
			result.put("result", resultData);
			result.put("resultCd", GlobalConfig.RESULT_FAIL_CD);
			result.put("resultMsg", GlobalConfig.RESULT_FAIL_MSG);
		}
		
		return result;
	}
	
	/**
	* @methodName	 	: adminLogOutProc
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그아웃 프로세스
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/logOut.do")
	@ResponseBody
	public Map<String,Object> adminLogOutProc( HttpServletRequest req ) {
		log.info(" [ AdminLoginController ] : adminLogOutProc ");
		
		Map<String, Object> result = new HashMap<String, Object>();
	    String resultData = adminLoginService.adminLogOutProc(req);
	    
	    result.put("result", resultData);
		
		return result;
	}
	
}
