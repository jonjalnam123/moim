package com.inst.project.admin.service.impl;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminLoginService;
import com.inst.project.admin.vo.AdminAgreeDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.aop.controller.AopController;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;
import com.inst.project.utill.PasswordHashUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	AdminLoginMapper adminLoginMapper;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
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
	        String adminRegGb = adminInfo.getAdminRegGb();
	        
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
	        
	        // 관리자가 회원등록 비밀번호 초기설정 상태
	        if ( !CommonUtil.isBlank(adminRegGb) && adminRegGb.equals("01") && CommonUtil.isBlank(adminInfo.getAdminPw())) {
	        	adminLoginMapper.insertAdminLoginLog(adminInfo);
	        	return GlobalConfig.LOGIN_PW;
	        }
	        
	        // 아이디, 비밀번호 불일치
	        if (!(adminIdChk && adminPwChk)) {
	        	adminLoginMapper.insertLoginFailLog(adminInfo);
	        	return GlobalConfig.LOGIN_N;
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
	    	String adminPw = adminDTO.getAdminPw(); //내가 입력한 번호
	    	AdminDTO adminInfo = adminLoginMapper.selectAdminInfo(adminDTO);
	    	if ( adminInfo == null ) {
	    		return GlobalConfig.N;
	    	}

	        boolean adminPwChk = PasswordHashUtil.matchesBcrypt(adminPw, adminInfo.getAdminPw());
	        if( adminPwChk ) {
	        	result = GlobalConfig.Y;
	        } else {
	        	result = GlobalConfig.N;
	        }

			return result;
			
	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminLoginPwNowchk failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);

	        return GlobalConfig.N;
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
	    	String adminPw = adminDTO.getAdminPw();
	    	if(adminPw == null ) {
	    		 return GlobalConfig.N;
	    	}
	    	String adminPwEnc = PasswordHashUtil.hashWithBcrypt(adminPw);
	    	adminDTO.setAdminPw(adminPwEnc);
	    	
	        int instCnt = adminLoginMapper.updateAdminLoginPwSet(adminDTO);
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
	
	//랜덤번호 추출 메소드
	public int makeRandomNumber() {
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		return checkNum;
	}
	
	//이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @methodName	 	: getAdminJoinMailChk
	* @author					: 최정석
	* @date            		: 2026. 02. 24
	* @description			: 관리자 회원가입 이메일 인증
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	*  2026. 02. 24        		최정석       			최초 생성
	*/
	@Override
	public int getAdminJoinMailChk(String adminEmail) {
		int authNumber = makeRandomNumber();
		String setFrom = "jeongseogc26@gmail.com";
		String toMail = adminEmail;
		String title = "🐶WnM 가입 인증 이메일 입니다.";
		String content = 
				"안녕하세요. WnM을 방문해주셔서 감사합니다. 멍!" +
                "<br><br>" + 
			    "인증 번호는 " + authNumber + "입니다. 멍!" + 
			    "<br>" + 
			    "해당 인증번호를 인증번호 확인란에 기입하여 주세요. 멍멍!";
		mailSend(setFrom, toMail, title, content);
		return authNumber;
	}
	
	/**
	* @methodName	 	: adminJoinProc
	* @author					: 최정석
	* @date            		: 2026. 02. 26
	* @description			: 관리자 회원가입 요청 진행
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	*  2026. 02. 26        		최정석       			최초 생성
	*/
	@Override
	public int adminJoinProc(AdminDTO adminDTO) {
	    log.info(" [ AdminLoginServiceImpl ] : adminJoinProc ");
	    try {
	    	
	    	// 가입자 정보
	    	String adminId 			= adminDTO.getAdminId();
	    	String adminPw 			= adminDTO.getAdminPw();
	    	String adminEmail 		= adminDTO.getAdminEmail();
	    	String adminPh 			= adminDTO.getAdminPh();
	    	String adminAddress		= adminDTO.getAdminAddress();
	    	String adminDAddress 	= adminDTO.getAdminDAddress();
	    	
	    	// 가입자 정보 암호화 [S]
	    	String adminPwCrypt 			= PasswordHashUtil.hashWithBcrypt(adminPw);
	    	String adminEmailCrypt 			= CommonUtil.toCrypEnc(adminEmail);
	    	String adminPhCrypt 				= CommonUtil.toCrypEnc(adminPh);
	    	String adminAddressCrypt 		= CommonUtil.toCrypEnc(adminAddress);
	    	String adminDAddressCrypt 	= CommonUtil.toCrypEnc(adminDAddress);
	    	
	    	adminDTO.setAdminPw(adminPwCrypt);
	    	adminDTO.setAdminEmail(adminEmailCrypt);
	    	adminDTO.setAdminPh(adminPhCrypt);
	    	adminDTO.setAdminAddress(adminAddressCrypt);
	    	adminDTO.setAdminDAddress(adminDAddressCrypt);
	    	// 가입자 정보 암호화 [E]

			// 약관동의 정보 [S] 
			String agreeService = adminDTO.getAgreeService();
			String agreePrivacy = adminDTO.getAgreeService();
			String agreeMarketing = adminDTO.getAgreeService();
			String agreeConsign = adminDTO.getAgreeService();
			
			AdminAgreeDTO adminAgreeDTO = new AdminAgreeDTO();
			adminAgreeDTO.setAgreeUserId(adminId);
			adminAgreeDTO.setAgreeService(agreeService);
			adminAgreeDTO.setAgreePrivacy(agreePrivacy);
			adminAgreeDTO.setAgreeMarketing(agreeMarketing);
			adminAgreeDTO.setAgreeConsign(agreeConsign);
			adminAgreeDTO.setAgreeGb("00"); // 약관동의구분 ( 00 : 관리자, 01 : 일반 사용자 )
			adminAgreeDTO.setRegId(adminId);
			adminAgreeDTO.setUpdId(adminId);
			// 약관동의 관련 [E]
			
			// 관리자 회원가입 약관동의 정보 저장
			int agreeResult = adminLoginMapper.insertAdminAgreeInfo(adminAgreeDTO);
			if ( agreeResult > 0 ) {
				int adminUserResult = adminLoginMapper.insertAdminUserInfo(adminDTO);
				return adminUserResult;
			}

			return 0;
	
	    } catch (Exception e) {
	        log.error("[ AdminLoginServiceImpl ] : adminJoinProc failed.");
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	
	        return 0;
	    }
	    
	}
	
}
