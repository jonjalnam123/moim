package com.inst.project.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminBoardService")
public class AdminBoardServiceImpl implements AdminBoardService {

	@Autowired
	AdminBoardMapper adminBoardMapper;

	
}
