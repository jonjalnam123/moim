package com.inst.project.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminMoimService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMoimDTO;
import com.inst.project.admin.vo.AdminMoimLocateDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.PagerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin/moim")
public class AdminMoimController {
	
	@Autowired
	AdminMoimService adminMoimService;
	
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
	@GetMapping(value = "/moimLocateList.do")
	public String getAdminMoimLocateList( Model model, RedirectAttributes redirect, PagerUtil pager ) {
		log.info(" [ AdminMoimController ] : getAdminMoimLocateList ");
		
		List<AdminMoimLocateDTO> adminMoimLocateList = adminMoimService.selectAdminMoimLocateList( pager );
		if( adminMoimLocateList == null ) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}
		
		model.addAttribute("adminMoimLocateList", adminMoimLocateList);
		model.addAttribute("pager", pager);
		
		return "admin/moim/adminMoimLocateList.adm";
	}
	
	/**
	* @methodName	 	: selectAdminLocateInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 모임장소 상세조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping("/moimLocateInfo.do")
	@ResponseBody
	public Map<String,Object> selectAdminMoimLocateInfo ( @ModelAttribute AdminMoimLocateDTO adminMoimLocateDTO ){
		log.info(" [ AdminMoimController ] : selectAdminLocateInfo ");
		Map<String,Object> resultMap = new HashMap<>();
		AdminMoimLocateDTO result = adminMoimService.selectAdminMoimLocateInfo(adminMoimLocateDTO);
	    resultMap.put("moimLocateInfo", result);
	    return resultMap;
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
	@PostMapping("/moimLocateListReg.do")
	@ResponseBody
	public Map<String,Object> adminMoimLocateListReg ( @ModelAttribute AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req ){
		log.info(" [ AdminMoimController ] : adminMoimLocateListReg ");
	    Map<String,Object> resultMap = new HashMap<>();
	    int regResult = adminMoimService.adminMoimLocateListReg(adminMoimLocateDTO, req);
	    resultMap.put("result", regResult);
	    return resultMap;
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
	@PostMapping("/moimLocateListUpd.do")
	@ResponseBody
	public Map<String,Object> adminMoimLocateListUpd ( @ModelAttribute AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req ){
		log.info(" [ AdminMoimController ] : adminMoimLocateListUpd ");
	    Map<String,Object> resultMap = new HashMap<>();
	    int regResult = adminMoimService.adminMoimLocateListUpd(adminMoimLocateDTO, req);
	    resultMap.put("result", regResult);
	    return resultMap;
	}
	
	/**
	* @methodName	 	: adminMoimLocateListDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 모임장소 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping("/moimLocateListDel.do")
	@ResponseBody
	public Map<String,Object> adminMoimLocateListDel ( @ModelAttribute AdminMoimLocateDTO adminMoimLocateDTO, HttpServletRequest req ){
		log.info(" [ AdminMoimController ] : adminMoimLocateListReg ");
	    Map<String,Object> resultMap = new HashMap<>();

	    int regResult = adminMoimService.adminMoimLocateListDel(adminMoimLocateDTO, req);

	    resultMap.put("result", regResult);

	    return resultMap;
	}
	
	/**
	* @methodName	 	: getAdminMoimList
	* @author					: 최정석
	* @date            		: 2026. 04. 23.
	* @description			: 관리자 모임일정 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 04.23.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/moimList.do")
	public String getAdminMoimList( Model model, RedirectAttributes redirect, PagerUtil pager ) {
		log.info(" [ AdminMoimController ] : getAdminMoimList ");
		
		// 관리자 모임일정 조회
		List<AdminMoimDTO> adminMoimList = adminMoimService.selectAdminMoimList( pager );
		
		// 관리자 모임일정 > 모임장소 조회
		List<AdminMoimLocateDTO> adminMoimLocateList = adminMoimService.selectAdminMoimLocateListForMoim();
		
		// 관리자 모임일정 > 모임구분 조회
		List<AdminCommDTO> adminMoimGbList = adminMoimService.selectAdminMoimGbList();
		
		if( adminMoimList == null || adminMoimLocateList == null || adminMoimGbList == null ) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}

		model.addAttribute("adminMoimList", adminMoimList);
		model.addAttribute("adminMoimLocateList", adminMoimLocateList);
		model.addAttribute("adminMoimGbList", adminMoimGbList);
		model.addAttribute("pager", pager);
		
		return "admin/moim/adminMoimList.adm";
	}


}
