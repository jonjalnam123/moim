package com.inst.project.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.inst.project.admin.service.AdminAopService;
import com.inst.project.admin.vo.AdminAopDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAopController {
	
	@Autowired
	AdminAopService adminAopService;

    @Pointcut("execution(* com.inst.project.admin..controller..*(..))")
    public void allControllers() {}

    /**
    * @methodName	 	: adminLognInsert
    * @author					: 최정석
    * @date            		: 2026. 4. 6.
    * @description			: 관리자 로그 이력 저장
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 4. 6.        		최정석       			최초 생성
    */
    @Before("allControllers()")
    public void adminLognInsert(JoinPoint joinPoint) {
    	log.info(" [ AdminAopController ] : adminLognInsert ");
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return;

        HttpServletRequest request = attrs.getRequest();
        HttpSession session = request.getSession(false);

        String classNm    = joinPoint.getTarget().getClass().getSimpleName();
        String methodNm   = joinPoint.getSignature().getName();
        String uri        = request.getRequestURI();
        String httpMethod = request.getMethod();
        String clientIp   = request.getRemoteAddr();

        String userId = null;
        if (session != null) {
            userId = CommonUtil.isNull(session.getAttribute("SS_ADMIN_ID")) ;
        }

        AdminAopDTO aopDTO = new AdminAopDTO();
        aopDTO.setClassNm(classNm);
        aopDTO.setMethodNm(methodNm);
        aopDTO.setReqUri(uri);
        aopDTO.setHttpMethod(httpMethod);
        aopDTO.setClientIp(clientIp);
        aopDTO.setRegId(userId);

        try {
        	adminAopService.adminLognInsert(aopDTO);
        } catch (Exception e) {
        	log.error("[ AdminAopController ] adminLognInsert failed", e);
        	log.error(GlobalConfig.RESULT_AOP_BEF_ERR_CD);
            log.error(GlobalConfig.RESULT_AOP_BEF_ERR_MSG);
        }
    }

}
