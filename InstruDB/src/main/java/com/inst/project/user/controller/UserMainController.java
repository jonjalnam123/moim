package com.inst.project.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inst.project.user.service.UserMainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class UserMainController {
	
	@Autowired
	UserMainService userMainService;
	
	@GetMapping(value = "/")
	public String getUserMain() {
		log.info("[UserMainController] 사용자 메인 진입");
		return "user/userMain.user";
	}
}
