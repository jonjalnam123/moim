package com.inst.project.admin.service.impl;

import java.util.Map;

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
	    	// 가입된 정보 없음.
	        if (adminInfo == null) {
	        	adminDTO.setAdminIp(adminIp);
	        	adminLoginMapper.insertEmptyAdminLoginLog(adminDTO);
	        	return GlobalConfig.LOGIN_E;
	        }

	        boolean adminIdChk = CommonUtil.loginIdChk(adminId, adminInfo.getAdminId());
	        boolean adminPwChk = PasswordHashUtil.matchesBcrypt(adminPw, adminInfo.getAdminPw());
	        adminInfo.setAdminIp(adminIp);
	        String adminRegAccept = adminInfo.getAdminRegAccept();
	        
	        // 아이디, 비밀번호 불일치
	        if (!(adminIdChk && adminPwChk)) {
	        	adminLoginMapper.insertLoginFailLog(adminInfo);
	        	return GlobalConfig.LOGIN_N;
	        }
	        
	        // 관리자 가입 미승인 상태
	        if ( !CommonUtil.isBlank(adminRegAccept) && adminRegAccept.equals("N") ) {
	        	adminLoginMapper.insertNotAcceptAdminLoginLog(adminInfo);
	        	return GlobalConfig.LOGIN_NA;
	        }
	        
	        // 관리자 가입승인 반려 상태
	        if ( !CommonUtil.isBlank(adminRegAccept) && adminRegAccept.equals("R") ) {
	        	adminLoginMapper.insertRejectAdminLoginLog(adminInfo);
	        	return GlobalConfig.LOGIN_R;
	        }

	        boolean sessionResult = CommonUtil.setAdminInfoSession(adminInfo, req);
	        int instCnt = adminLoginMapper.insertAdminLoginLog(adminInfo);

	        return (sessionResult && instCnt > 0) ? GlobalConfig.LOGIN_Y : GlobalConfig.LOGIN_N;

	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLoginProc failed. adminId={}, ip={}", adminId, adminIp, e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

		    seession = req.getSession(false);
		    if (seession != null) {
		    	seession.invalidate();
		    }
		    
		    // 로그인 실패
		    adminLoginMapper.insertLoginFailLog(adminInfo);

	        return GlobalConfig.LOGIN_N;
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
	        adminInfo.setAdminIp(adminIp);

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
	
	/**
	* @methodName	 	: getAdminLoginPw
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 비밀번호 설정 화면 조회 
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminDTO getAdminLoginPw(AdminDTO adminDTO) {
	    log.info(" [ AdminLoginServiceImpl ] : getAdminLoginPw ");
	    try {
	    	
			AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
			if ( adminInfo == null ) {
				return null;
			}

			return adminInfo;
			
	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : getAdminLoginPw failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

	        return null;
	    }
	}
	
	/**
	* @methodName	 	: adminLoginPwNowchk
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 현재 비밀번호 체크
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String adminLoginPwNowchk(AdminDTO adminDTO) {
	    log.info(" [ AdminLoginServiceImpl ] : adminLoginPwNowchk ");
	    try {
	    	
	    	String result = "";
	    	String adminPw = adminDTO.getAdminPw();
	    	AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
	    	if ( adminInfo == null ) {
	    		return GlobalConfig.N;
	    	}

	        boolean adminPwChk = PasswordHashUtil.matchesBcrypt(adminPw, adminInfo.getAdminPw());

	        
			return result;
			
	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLoginPwNowchk failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

	        return null;
	    }
	}
	
	/**
	* @methodName	 	: adminLoginPwSet
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 비밀번호 설정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String adminLoginPwSet(AdminDTO adminDTO) {
	    log.info(" [ AdminLoginServiceImpl ] : adminLoginPwSet ");
	    try {
	    	
	    	AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
	    	String adminPw = adminInfo.getAdminPw();
	    	if ( CommonUtil.isBlank(adminPw) ) { // 관리자 등록 :  비밀번호 초기 설정
	    		
	    	} else { // 본인 가입 : 비밀번호 변경 또는 관리자 등록 초기설정 후 비밀번호 변경
	    		
	    	}
	    		
	        int instCnt = adminLoginMapper.insertAdminLogOutLog(adminInfo);
	        if (instCnt > 0) {
	            return GlobalConfig.Y;
	        }

	        return GlobalConfig.N;

	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLoginPwSet failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

	        return GlobalConfig.N;
	    }
	}
	
}
