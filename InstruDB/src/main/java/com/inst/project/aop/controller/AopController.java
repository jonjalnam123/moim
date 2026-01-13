package com.inst.project.aop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.inst.project.aop.service.AopMapper;
import com.inst.project.aop.vo.AopDTO;
import com.inst.project.common.GlobalConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AopController {

    private final AopMapper aopMapper;

    @Pointcut("execution(* com.inst.project..controller..*(..))")
    public void allControllers() {}

    @Before("allControllers()")
    public void logAndInsert(JoinPoint joinPoint) {

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
            Object userCd = session.getAttribute("USER_CD");
            if (userCd != null) userId = userCd.toString();
        }

        AopDTO aopDTO = new AopDTO();
        aopDTO.setClassNm(classNm);
        aopDTO.setMethodNm(methodNm);
        aopDTO.setReqUri(uri);
        aopDTO.setHttpMethod(httpMethod);
        aopDTO.setClientIp(clientIp);
        aopDTO.setUserId(userId);
        log.info("[AOP] DATA : {}", aopDTO);

        try {
        	//aopMapper.insertAopLog(aopDTO);
        } catch (Exception e) {
        	log.error(GlobalConfig.RESULT_AOP_BEF_ERR_CD);
            log.error(GlobalConfig.RESULT_AOP_BEF_ERR_MSG);
        }
    }

}
