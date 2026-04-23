package com.inst.project.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminMoimService;
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
	* @methodName	 	: getAdminMoimList
	* @author					: 최정석
	* @date            		: 2026. 04. 23.
	* @description			: 관리자 모임 일정 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 04.23.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/moimList.do")
	public String getAdminMoimList() {
		log.info(" [ AdminMoimController ] : getAdminMoimList ");
		return "admin/moim/adminMoimList.adm";
	}


}
