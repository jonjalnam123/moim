package com.inst.project.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMoimService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMoimService")
public class AdminMoinServiceImpl implements AdminMoimService {

	@Autowired
	AdminMoimMapper adminMoimMapper;
	
	
}
