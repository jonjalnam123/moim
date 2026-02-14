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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;

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
	* @methodName	 	: adminCommSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/commSelect.do")
	@ResponseBody
	public Map<String,Object> adminCommSelect( @ModelAttribute AdminCommDTO adminCommDTO ) {
		log.info(" [ AdminMngController ] : adminMenuSelect ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		AdminCommDTO selectResult = adminMngService.adminCommSelect(adminCommDTO);	
		
		result.put("result", selectResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminCommReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/commReg.do")
	@ResponseBody
	public Map<String,Object> adminCommReg( @ModelAttribute AdminCommDTO adminCommDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminCommReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminCommReg(adminCommDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminCommUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/commUpd.do")
	@ResponseBody
	public Map<String,Object> adminCommUpd( @ModelAttribute AdminCommDTO adminCommDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminCommUpd ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminCommUpd(adminCommDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminCommDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/commDel.do")
	@ResponseBody
	public Map<String,Object> adminCommDel( @ModelAttribute AdminCommDTO adminCommDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminCommDel ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int delResult = adminMngService.adminCommDel(adminCommDTO, req);
		result.put("result", delResult);

		return result;
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

		if ( menuDeptList == null ) {
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
	
	/**
	* @methodName	 	: adminUnitSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/unitSelect.do")
	@ResponseBody
	public Map<String,Object> adminUnitSelect( @ModelAttribute AdminUnitDTO adminUnitDTO ) {
		log.info(" [ AdminMngController ] : adminUnitSelect ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		AdminUnitDTO selectResult = adminMngService.adminUnitSelect(adminUnitDTO);	
		
		result.put("result", selectResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminUnitReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/unitReg.do")
	@ResponseBody
	public Map<String,Object> adminUnitReg( @ModelAttribute AdminUnitDTO adminUnitDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUnitReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminUnitReg(adminUnitDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminUnitUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/unitUpd.do")
	@ResponseBody
	public Map<String,Object> adminUnitUpd( @ModelAttribute AdminUnitDTO adminUnitDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUnitUpd ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int updResult = adminMngService.adminUnitUpd(adminUnitDTO, req);
		
		result.put("result", updResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminUnitDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/unitDel.do")
	@ResponseBody
	public Map<String,Object> adminUnitDel( @ModelAttribute AdminUnitDTO adminUnitDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUnitDel ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int delResult = adminMngService.adminUnitDel(adminUnitDTO, req);
		result.put("result", delResult);

		return result;
	}
	
	/**
	* @methodName	 	: getAdminUser
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 관리 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value="/user.do")
	public String getAdminUser( Model model, RedirectAttributes redirect ) {
		log.info(" [ AdminMngController ] : getAdminUser ");
		
		List<AdminDTO> adminList = adminMngService.selectAdminUser();
		
		// 유닛 레벨 1 조회
		List<AdminUnitDTO> adminUnitList = adminMngService.selectUnitList();
		
		// 관리자 등급 조회
		List<AdminCommDTO> adminGradeList = adminMngService.selectAdminGradeList();

		if( adminList == null || adminUnitList == null || adminGradeList == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}
		
		model.addAttribute("adminList", adminList);
		model.addAttribute("adminUnitList", adminUnitList);
		model.addAttribute("adminGradeList", adminGradeList);
		
		return "admin/mng/adminUser.adm";
	}
	
	/**
	* @methodName	 	: getAdminUserInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 관리자 정보 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/userInfo.do")
	@ResponseBody
	public Map<String, Object> getAdminUserInfo( @ModelAttribute AdminDTO adminDTO ) {
		log.info(" [ AdminMngController ] : getAdminUserInfo ");
		
		Map<String, Object> result = new HashMap<>();
		AdminDTO adminInfo = adminMngService.selectAdminUserInfo(adminDTO);
		
		result.put("adminInfo", adminInfo);

		return result;
	}
	
	/**
	* @methodName	 	: getAdminUnitTeam
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 팀 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/teamSelect.do")
	@ResponseBody
	public List<Map<String, Object>> getAdminUnitTeam( @RequestParam(defaultValue = "") String adminUnitId ) {
		log.info(" [ AdminMngController ] : getAdminUnitTeam ");

		// 관리자 유닛 팀 조회 
		List<Map<String, Object>> adminTeamList = adminMngService.selectAdminTeamList(adminUnitId);
		
		return adminTeamList;
	}
	
	/**
	* @methodName	 	: getAdminUnitPosition
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 직책 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/posotionSelect.do")
	@ResponseBody
	public List<Map<String, Object>> getAdminUnitPosition( @RequestParam(defaultValue = "") String adminUnitId ) {
		log.info(" [ AdminMngController ] : getAdminUnitPosition ");
		
		// 관리자 유닛 직책 조회
		List<Map<String, Object>> adminPositionList = adminMngService.selectAdminPositionList(adminUnitId);
		
		return adminPositionList;
	}
	
	/**
	* @methodName	 	: adminUnitReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/userReg.do")
	@ResponseBody
	public Map<String,Object> adminUserReg( @ModelAttribute AdminDTO adminDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUserReg ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int regResult = adminMngService.adminUserReg(adminDTO, req);
		
		result.put("result", regResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminUserUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/userUpd.do")
	@ResponseBody
	public Map<String,Object> adminUserUpd( @ModelAttribute AdminDTO adminDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUserUpd ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int updResult = adminMngService.adminUserUpd(adminDTO, req);
		
		result.put("result", updResult);

		return result;
	}
	
	/**
	* @methodName	 	: adminUserUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/userDel.do")
	@ResponseBody
	public Map<String,Object> adminUserDel( @ModelAttribute AdminDTO adminDTO, HttpServletRequest req ) {
		log.info(" [ AdminMngController ] : adminUserDel ");
		
		Map<String, Object> result = new HashMap<String, Object>();
		int delResult = adminMngService.adminUserDel(adminDTO, req);
		result.put("result", delResult);

		return result;
	}
	
	/**
	* @methodName	 	: getAdminUserAcceptInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 미승인 정보 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/userAccept.do")
	public String getAdminUserAcceptInfo( Model model, RedirectAttributes redirect ) {
		log.info(" [ AdminMngController ] : getAdminUserAcceptInfo ");
		
		// 미승인 상태 관리자들 조회해서 진행하는 메소드 짜야함...!!!!!!!!
		
		List<AdminDTO> adminList = adminMngService.selectAdminUser();
		
		// 유닛 레벨 1 조회
		List<AdminUnitDTO> adminUnitList = adminMngService.selectUnitList();
		
		// 관리자 등급 조회
		List<AdminCommDTO> adminGradeList = adminMngService.selectAdminGradeList();

		if( adminList == null || adminUnitList == null || adminGradeList == null) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do"; 
		}
		
		model.addAttribute("adminList", adminList);
		model.addAttribute("adminUnitList", adminUnitList);
		model.addAttribute("adminGradeList", adminGradeList);
		
		return "admin/mng/adminUser.adm";
	}
	
}
