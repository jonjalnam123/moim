package com.inst.project.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminPopUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminPopUpService")
public class AdminPopUpServiceImpl implements AdminPopUpService {

	@Autowired
	AdminPopUpMapper adminPopUpMapper;
	
}
