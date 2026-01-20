package com.inst.project.admin.controller;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inst.project.admin.vo.AdminErrorDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/admin")
public class AdminCommController {
	
	private final Clock clock;
	
    public AdminCommController(Clock clock) {
        this.clock = clock;
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
	
	/**
	* @methodName	 	: getAdminError
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통 에러 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value="/error.do")
	public String getAdminError( @ModelAttribute AdminErrorDTO adminErrorDTO, Model model) {
		log.info(" [ AdminCommController ] : getAdminError ");
		
		LocalDateTime now = now();
		adminErrorDTO.setAdminErrorDate(now);
		
		String adminErrorCd = "";
		String adminErrorMsg = "";
		
		if ( adminErrorCd.isEmpty() && adminErrorMsg.isEmpty() ) {
			adminErrorCd = GlobalConfig.RESULT_SYS_ERR_CD;
			adminErrorMsg = GlobalConfig.RESULT_SYS_ERR_MSG;
		} else {
			adminErrorCd = adminErrorDTO.getAdminErrorCd();
			adminErrorMsg = adminErrorDTO.getAdminErrorMsg();
		}
		
		LocalDateTime adminErrorDate = adminErrorDTO.getAdminErrorDate();
		
		model.addAttribute("adminErrorCd", adminErrorCd);
		model.addAttribute("adminErrorMsg", adminErrorMsg);
		model.addAttribute("adminErrorDate", adminErrorDate.format(GlobalConfig.ERR_FMT));
		
		return "admin/all/adminError.adm";
	}
}
