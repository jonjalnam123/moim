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

import com.inst.project.admin.service.AdminUserHomeService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/admin/userHome")
@RequiredArgsConstructor
public class AdminUserHomeController {
	
	@Autowired
	AdminUserHomeService adminUserHomeService;
	
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
	public String getAdminUserHomeMenu(Model model, RedirectAttributes redirect) {
		log.info(" [ AdminMngController ] : getAdminMenu ");
		
		// 메뉴 레벨 1 조회
		List<AdminMenuDTO> adminMenuList = adminUserHomeService.selectMenuList();
		
		// 메뉴 레벨 2 조회
		List<AdminMenuDTO> adminMenuList2 = adminUserHomeService.selectMenuList2();

		// 관리자 등급 조회
		List<AdminCommDTO> adminGradeList = adminUserHomeService.selectAdminGradeList();
		
		if( adminMenuList == null || adminMenuList2 == null || adminGradeList == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}

		model.addAttribute("menuList", adminMenuList);
		model.addAttribute("menuList2", adminMenuList2);
		model.addAttribute("adminGradeList", adminGradeList);
		
		return "admin/mng/adminMenu.adm";
	}
	
	/**
	* @methodName	 	: adminMenuSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/menuSelect.do")
	@ResponseBody
	public Map<String,Object> adminUserHomeMenuSelect( @ModelAttribute AdminMenuDTO adminMenuDTO ) {
		log.info(" [ AdminMngController ] : adminMenuSelect ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 관리자 메뉴 상세 조회
		AdminMenuDTO selectResult = adminUserHomeService.adminMenuSelect(adminMenuDTO);	
		
		// 관리자 메뉴 상세조회 결과로 부서코드 조회
		List<Map<String, Object>> menuDeptList = adminUserHomeService.adminMenuDeptCdSelect(selectResult);

		if ( menuDeptList == null ) {
			
			// 관리자 유닛 조회
			List<AdminUnitDTO> adminUnitList = adminUserHomeService.selectUnitAllList();
			result.put("adminUnitList", adminUnitList);
		}
		
		result.put("result", selectResult);
		result.put("menuDeptList", menuDeptList);

		return result;
	}
	
	/**
	* @methodName	 	: adminMenuReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/menuReg.do")
	@ResponseBody
	public Map<String,Object> adminUserHomeMenuReg( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminUserHomeService.adminMenuReg(adminMenuDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminMenuUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/menuUpd.do")
	@ResponseBody
	public Map<String,Object> adminUserHomeMenuUpd( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuUpd ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminUserHomeService.adminMenuUpd(adminMenuDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminMenuDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/menuDel.do")
	@ResponseBody
	public Map<String,Object> adminUserHomeMenuDel( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuDel ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int delResult = adminUserHomeService.adminMenuDel(adminMenuDTO, req);
		
		result.put("result", delResult);

		return result;
	}
	
}
