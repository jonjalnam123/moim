package com.inst.project.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inst.project.admin.service.AdminBoardService;
import com.inst.project.util.CommonUtil;
import com.inst.project.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminBoardController {
	
	@Autowired
	AdminBoardService adminBoardService;
	
	@GetMapping(value = "/notice.do")
	public String getAdminNotice(Model model, HttpServletRequest req) {
		
		String adminId = CommonUtil.getAdminInfoSession("adminId", req);
		
		
		model.addAttribute("adminId", adminId);
		model.addAttribute("nowDate", DateUtil.now());
		
		return "admin/board/adminBoard.adm";
	}

}
