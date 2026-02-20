package com.inst.project.admin.controller;

import java.util.HashMap; 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminLoginService;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminLoginController {

	@Autowired
	AdminLoginService adminLoginService;
	
	
	/**
	* @methodName	 	: getAdminJoin
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그인 화면 호출
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/agree.do")
	public String getAdminAgree() {
		log.info(" [ AdminLoginController ] : getAdminAgree ");
		return "admin/login/adminAgree.none";
	}
	
	
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
	public String getAdminLogin( @RequestParam(required = false) Map<String, Object> bodyMap, Model model ) {
		log.info(" [ AdminLoginController ] : getAdminLogin ");
		if ( bodyMap != null ) {
			model.addAttribute("result", bodyMap);
		}
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
		
		result.put("result", resultData);
		result.put("resultCd", GlobalConfig.RESULT_SUCC_CD);
		result.put("resultMsg", GlobalConfig.RESULT_SUCC_MSG);
		
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
	
	/**
	* @methodName	 	: getAdminLoginPw
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 비밀번호 설정 화면 조회 
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/loginPw.do")
	public String getAdminLoginPw( @RequestParam String adminId, Model model, RedirectAttributes redirect) {
		log.info(" [ AdminLoginController ] : getAdminLoginPw ");
		
		AdminDTO adminDTO = new AdminDTO();
		if ( !CommonUtil.isBlank(adminId) ) {
			adminDTO.setAdminId(adminId);
		}
		
		AdminDTO adminInfo = adminLoginService.getAdminLoginPw(adminDTO);
		if(adminInfo == null) { // 가입정보 없음
			redirect.addAttribute("sendParam", adminId);
			redirect.addAttribute("sendParamGb", GlobalConfig.N);
			return "redirect:/admin/login.do";
		}
		
		String url = "";
		String adminPw = adminInfo.getAdminPw();
		String adminRegGb = adminInfo.getAdminRegGb();
		if ( CommonUtil.isBlank(adminPw) && adminRegGb.equals("01") ) {	// 슈퍼관리자 등록 비밀번호 초기 설정
			url = "admin/login/adminLoginNewPwSet.none";
		} else if ( !CommonUtil.isBlank(adminPw) ){ // 일반 비밀번호 설정
			url = "admin/login/adminLoginPwSet.none";
		}
		
		model.addAttribute("adminInfo", adminInfo);

		return url;
	}
	
	/**
	* @methodName	 	: adminLoginPwNowchk
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 현재 비밀번호 체크
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/loginPwNowChk.do")
	@ResponseBody
	public Map<String,Object> adminLoginPwNowchk( @ModelAttribute AdminDTO adminDTO ) {
		log.info(" [ AdminLoginController ] : adminLoginPwNowchk ");
		
		Map<String, Object> result = new HashMap<>();
		String resultData = adminLoginService.adminLoginPwNowchk(adminDTO);
		
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
	* @methodName	 	: adminLoginPwSet
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 비밀번호 설정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/loginPwSet.do")
	@ResponseBody
	public Map<String,Object> adminLoginPwSet( @ModelAttribute AdminDTO adminDTO ) {
		log.info(" [ AdminLoginController ] : adminLoginPwSet ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		String resultData = adminLoginService.adminLoginPwSet(adminDTO);
		
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
	
}
