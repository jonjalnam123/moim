package com.inst.project.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.service.AdminBoardService;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.util.CommonUtil;
import com.inst.project.util.DateUtil;
import com.inst.project.util.PagerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminBoardController {
	
	@Autowired
	AdminBoardService adminBoardService;
	
	/**
	* @methodName	 	: getAdminNotice
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/notice.do")
	public String getAdminNotice(Model model, HttpServletRequest req, PagerUtil pager) {
		
		String adminId = CommonUtil.getAdminInfoSession("adminId", req);
		if (adminId == null) {
			return "redirect:/admin/login.do";
		}
		
		// 공지사항 조회
		List<AdminNoticeDTO> adminNoticeList = adminBoardService.selectAdmionNotice( pager );
		
		model.addAttribute("adminId", adminId);
		model.addAttribute("nowDate", DateUtil.now());
		model.addAttribute("adminNoticeList", adminNoticeList);
		
		return "admin/board/adminNotice.adm";
	}
	
	/**
	* @methodName	 	: adminNoticeReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping("/noticeReg.do")
	@ResponseBody
	public Map<String,Object> adminNoticeReg(AdminNoticeDTO adminNoticeDTO, @RequestParam(value="adminFiles", required=false) MultipartFile[] files, HttpServletRequest req){

	    Map<String,Object> resultMap = new HashMap<>();

	    int result = adminBoardService.adminNoticeReg(adminNoticeDTO, files, req);

	    resultMap.put("result", result);

	    return resultMap;
	}

}
