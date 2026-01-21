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

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
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
	public String getAdminComm(Model model, RedirectAttributes redirect) {
		log.info(" [ AdminMngController ] : getAdminComm ");
		
		// 공통코드 레벨 1 조회
		List<AdminCommDTO> adminCommList = adminMngService.selectCommList();
		
		// 공통코드 레벨 2 조회
		List<AdminCommDTO> adminCommList2 = adminMngService.selectCommList2();
		
		if( adminCommList == null || adminCommList2 == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
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
	public String getAdminMenu(Model model, RedirectAttributes redirect) {
		log.info(" [ AdminMngController ] : getAdminMenu ");
		
		// 메뉴 레벨 1 조회
		List<AdminMenuDTO> adminMenuList = adminMngService.selectMenuList();
		
		// 메뉴 레벨 2 조회
		List<AdminMenuDTO> adminMenuList2 = adminMngService.selectMenuList2();
		
		// 유닛 레벨 조회
		List<AdminUnitDTO> adminUnitList = adminMngService.selectUnitAllList();
		
		if( adminMenuList == null || adminMenuList2 == null || adminUnitList == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}

		model.addAttribute("menuList", adminMenuList);
		model.addAttribute("menuList2", adminMenuList2);
		model.addAttribute("adminUnitList", adminUnitList);
		
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
	public Map<String,Object> adminMenuSelect( @ModelAttribute AdminMenuDTO adminMenuDTO ) {
		log.info(" [ AdminMngController ] : adminMenuSelect ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		AdminMenuDTO selectResult = adminMngService.adminMenuSelect(adminMenuDTO);	
		List<Map<String, Object>> menuDeptList = adminMngService.adminMenuDeptCdSelect(selectResult);

		if ( menuDeptList == null  || menuDeptList.isEmpty() ) {
			List<AdminUnitDTO> adminUnitList = adminMngService.selectUnitAllList();
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
	public Map<String,Object> adminMenuReg( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminMenuReg(adminMenuDTO, req);
		
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
	public Map<String,Object> adminMenuUpd( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminMenuUpd(adminMenuDTO, req);
		
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
	public Map<String,Object> adminMenuDel( @ModelAttribute AdminMenuDTO adminMenuDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminMenuDel ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int delResult = adminMngService.adminMenuDel(adminMenuDTO, req);
		
		result.put("result", delResult);

		return result;
	}
	
	/**
	* @methodName	 	: getAdminUnit
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛관리 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value="/unit.do")
	public String getAdminUnit(Model model, RedirectAttributes redirect) {
		log.info(" [ AdminMngController ] : getAdminUnit ");
		
		// 유닛 레벨 1 조회
		List<AdminUnitDTO> adminUnitList = adminMngService.selectUnitList();
		
		// 유닛 레벨 2 조회
		List<AdminUnitDTO> adminUnitList2 = adminMngService.selectUnitList2();
		
		// 유닛 레벨 3 조회
		List<AdminUnitDTO> adminUnitList3 = adminMngService.selectUnitList3();
		
		if( adminUnitList == null || adminUnitList2 == null || adminUnitList2 == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}

		model.addAttribute("adminUnitList", adminUnitList);
		model.addAttribute("adminUnitList2", adminUnitList2);
		model.addAttribute("adminUnitList3", adminUnitList3);
		
		return "admin/mng/adminUnit.adm";
	}

}
