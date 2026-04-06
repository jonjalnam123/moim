package com.inst.project.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminAopService;
import com.inst.project.admin.vo.AdminAopDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminAopService")
public class AdminAopServiceImpl implements AdminAopService {

	@Autowired
	AdminAopMapper adminAopMapper;
	
    /**
    * @methodName	 	: adminLognsert
    * @author					: 최정석
    * @date            		: 2026. 4. 6.
    * @description			: 관리자 로그 이력 저장
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 4. 6.        		최정석       			최초 생성
    */
	@Override
	public void adminLognInsert(AdminAopDTO aopDTO) {
		adminAopMapper.adminLognInsert(aopDTO);
	}
	
	
}
