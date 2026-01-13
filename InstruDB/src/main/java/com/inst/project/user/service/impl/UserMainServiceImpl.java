package com.inst.project.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.user.service.UserMainService;

@Service("userMainService")
public class UserMainServiceImpl implements UserMainService{
	
	@Autowired
	UserMainMapper userMainMapper;
	

}
