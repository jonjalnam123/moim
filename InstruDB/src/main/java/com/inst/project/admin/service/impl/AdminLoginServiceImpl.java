package com.inst.project.admin.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminLoginService;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;
import com.inst.project.utill.PasswordHashUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	AdminLoginMapper adminLoginMapper;
	
	/**
	* @methodName	 	: adminLoginProc
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그인 프로세스
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String adminLoginProc(AdminDTO adminDTO, HttpServletRequest req) {
		log.info(" [ AdminLoginServiceImpl ] : adminLoginProc ");
		log.info(GlobalConfig.RESULT_PARAM_MSG, adminDTO);

		String result = "";
		String adminId = adminDTO.getAdminId();
		String adminPw = adminDTO.getAdminPw();
		String adminIp = CommonUtil.getClientIp(req);
		
		// 관리자정보 조회
		AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
		log.info(GlobalConfig.RESULT_MAP_MSG, adminInfo);
		if ( adminInfo == null ) {
			result = GlobalConfig.N;
			log.info(GlobalConfig.RESULT_LOGIN_FAIL_MSG, adminInfo);
			return result;
		}
		
		// 관리자정보 로그인 프로세스
		boolean adminIdChk = CommonUtil.loginIdChk(adminId , adminInfo.getAdminId());
		boolean adminPwChk = PasswordHashUtil.matchesBcrypt(adminPw, adminInfo.getAdminPw());
		
		if ( adminIdChk && adminPwChk ) {
			adminInfo.setAdminIp(adminIp);
			boolean sessionResult = setAdminInfoSession(adminInfo, req);
			int instCnt = adminLoginMapper.insertAdminLoginLog(adminInfo);
			
			if ( sessionResult && instCnt > 0 ) {
				result = GlobalConfig.Y;
				log.info(GlobalConfig.RESULT_LOGIN_SUC_MSG, adminInfo);
			}
		} else {
			result = GlobalConfig.N;
			log.info(GlobalConfig.RESULT_LOGIN_FAIL_MSG, adminInfo);
		}
		return result;
	}
	
	/**
	* @methodName	 	: setAdminInfoSession
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 정보 세션 저장
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	public boolean setAdminInfoSession(AdminDTO adminInfo, HttpServletRequest req) {
		log.info(" [ AdminLoginServiceImpl ] : setAdminInfoSession ");
		
		if ( adminInfo == null ) {
			return false;
		}
		
		// 기존 세션 무효화 (USER 세션 포함 전부 제거)
	    HttpSession oldSession = req.getSession(false);
	    if (oldSession != null) {
	        oldSession.invalidate();
	    }
	    
	    // 새 세션 생성
		HttpSession session = req.getSession();
	    session.setAttribute("adminInfo", adminInfo);
	    
		return true;
	}
	
	/**
	* @methodName	 	: adminLogOutProc
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 로그아웃 프로세스
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String adminLogOutProc(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
	    AdminDTO adminInfo =  (AdminDTO) session.getAttribute("adminInfo");
	    log.info("adminInfo : {}", adminInfo);
        if (session != null) {
            session.invalidate(); // 🔥 세션 완전 종료
        }
		return null;
	}
	
}
