package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminCommService;
import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMngService")
public class AdminCommServiceImpl implements AdminCommService {

	@Autowired
	AdminCommMapper adminCommMapper;
	
	/**
	* @methodName	 	: selectUniqueDupliChk
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유니크 값 중복 체크
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String selectUniqueDupliChk(Map<String, Object> bodyMap) {
		log.info(" [ AdminCommServiceImpl ] : selectUniqueDupliChk ");
		try {
			
			String result = "";
			String uniqueKey = String.valueOf(bodyMap.get("uniqueKey"));
			String tableNm = String.valueOf(bodyMap.get("tableNm"));
			if (CommonUtil.isBlank(uniqueKey) || CommonUtil.isBlank(tableNm) ) {
				return GlobalConfig.N;
			}
			
			// 테이블명에 따른 분기 처리 로직 진행 해야함.
			
			int resultChk = adminCommMapper.selectUniqueDupliChk(bodyMap);
			if( resultChk > 0 ) {
				result = GlobalConfig.N;
			} else {
				result = GlobalConfig.Y;
			}
			
			return result;

		} catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectCommList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}

	}
	
	
}
