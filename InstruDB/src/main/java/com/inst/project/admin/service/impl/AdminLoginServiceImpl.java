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
	    
	    String adminId = adminDTO.getAdminId();
	    String adminPw = adminDTO.getAdminPw();
	    String adminIp = CommonUtil.getClientIp(req);
        AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
        HttpSession seession = req.getSession(false);
        
	    try {
	        if (adminInfo == null) return GlobalConfig.N;

	        boolean adminIdChk = CommonUtil.loginIdChk(adminId, adminInfo.getAdminId());
	        boolean adminPwChk = PasswordHashUtil.matchesBcrypt(adminPw, adminInfo.getAdminPw());

	        if (!(adminIdChk && adminPwChk)) return GlobalConfig.N;

	        adminInfo.setAdminIp(adminIp);

	        boolean sessionResult = CommonUtil.setAdminInfoSession(adminInfo, req);
	        int instCnt = adminLoginMapper.insertAdminLoginLog(adminInfo);

	        return (sessionResult && instCnt > 0) ? GlobalConfig.Y : GlobalConfig.N;

	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLoginProc failed. adminId={}, ip={}", adminId, adminIp, e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

		    seession = req.getSession(false);
		    if (seession != null) {
		    	seession.invalidate();
		    }

		    adminLoginMapper.insertLoginFailLog(adminInfo);

	        return GlobalConfig.N;
	    }
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
	    log.info(" [ AdminLoginServiceImpl ] : adminLogOutProc ");

	    try {
	        HttpSession session = req.getSession(false);
	        if (session == null) {
	            return GlobalConfig.N;
	        }

	        AdminDTO adminInfo = new AdminDTO();
	        String adminId = CommonUtil.getAdminInfoSession("adminId", req);
	        String adminIp = CommonUtil.getAdminInfoSession("adminIp", req);
	        
	        adminInfo.setAdminId(adminId);
	        adminInfo.setAdminId(adminIp);

	        int instCnt = adminLoginMapper.insertAdminLogOutLog(adminInfo);
	        if (instCnt > 0) {
	            session.invalidate();
	            return GlobalConfig.Y;
	        }

	        return GlobalConfig.N;

	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLogOutProc failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

	        return GlobalConfig.N;
	    }
	}
	
}
