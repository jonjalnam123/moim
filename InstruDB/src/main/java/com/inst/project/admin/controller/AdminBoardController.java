package com.inst.project.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminBoardService;
import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;
import com.inst.project.util.DateUtil;
import com.inst.project.util.FileUtil;
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
	public String getAdminNotice (Model model, HttpServletRequest req, PagerUtil pager, RedirectAttributes redirect) {
		log.info(" [ AdminBoardController ] : getAdminNotice ");
		
		// 공지사항 조회
		List<AdminNoticeDTO> adminNoticeList = adminBoardService.selectAdmionNotice( pager );
		if( adminNoticeList == null  ) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do"; 
		}
		
		model.addAttribute("nowDate", DateUtil.now());
		model.addAttribute("adminNoticeList", adminNoticeList);
		model.addAttribute("pager", pager);
		
		return "admin/board/adminNotice.adm";
	}
	
	/**
	* @methodName	 	: getAdminNoticeInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 상세조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/noticeInfo.do")
	@ResponseBody
	public Map<String, Object> getAdminNoticeInfo ( @ModelAttribute AdminNoticeDTO adminNoticeDTO) {
		log.info(" [ AdminBoardController ] : getAdminNoticeInfo ");
		
		Map<String, Object> result = adminBoardService.selectAdminNoticeInfo(adminNoticeDTO);
		
		return result;
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
	public Map<String,Object> adminNoticeReg( @ModelAttribute AdminNoticeDTO adminNoticeDTO, @RequestParam(value="adminFiles", required=false) MultipartFile[] files, HttpServletRequest req){
		log.info(" [ AdminBoardController ] : adminNoticeReg ");
	    Map<String,Object> resultMap = new HashMap<>();

	    int regResult = adminBoardService.adminNoticeReg(adminNoticeDTO, files, req);

	    resultMap.put("result", regResult);

	    return resultMap;
	}
	
	/**
	* @methodName	 	: adminNoticeUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/noticeUpd.do")
	@ResponseBody
	public Map<String, Object> adminNoticeUpd ( @ModelAttribute AdminNoticeDTO adminNoticeDTO
													  			, @RequestParam(value = "adminFiles", required = false) List<MultipartFile> adminFiles
													  			, @RequestParam(value = "deleteFileId", required = false) List<String> deleteFileId
													  			, HttpServletRequest req) {

	    Map<String, Object> resultMap = new HashMap<>();
	    
        int updResult = adminBoardService.adminNoticeUpd(adminNoticeDTO, adminFiles, deleteFileId, req);
        
	    resultMap.put("result", updResult);

	    return resultMap;
	}
	
	/**
	* @methodName	 	: adminNoticeDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공지사항 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@PostMapping(value = "/noticeDel.do")
	@ResponseBody
	public Map<String, Object> adminNoticeDel ( @ModelAttribute AdminNoticeDTO adminNoticeDTO, HttpServletRequest req) {

	    Map<String, Object> resultMap = new HashMap<>();
	    
        int delResult = adminBoardService.adminNoticeDel(adminNoticeDTO, req);
        
	    resultMap.put("result", delResult);

	    return resultMap;
	}

}
