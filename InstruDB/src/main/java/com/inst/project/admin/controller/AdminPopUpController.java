package com.inst.project.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inst.project.admin.service.AdminBoardService;
import com.inst.project.admin.service.AdminPopUpService;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminPopUpController {
	
	@Autowired
	AdminPopUpService adminPopUpService;
	
	@Autowired
	AdminBoardService adminBoardService;
	
	@GetMapping("/noticePopUp.do")
	public String getNoticePopUp(@RequestParam String noticeId, Model model) {
		log.info(" [ AdminPopUpController ] : getNoticePopUp ");

		AdminNoticeDTO adminNoticeDTO = new AdminNoticeDTO();
		if ( !CommonUtil.isBlank(noticeId) ) {
			adminNoticeDTO.setNoticeId(noticeId);
		}
		
		Map<String, Object> result = adminBoardService.selectAdminNoticeInfo(adminNoticeDTO);

	    model.addAttribute("adminNotice", result.get("adminNoticeInfo"));
	    model.addAttribute("noticeFileList", result.get("adminNoticeFileList"));

	    return "admin/popup/adminNoticePopUp.none";
	}

}
