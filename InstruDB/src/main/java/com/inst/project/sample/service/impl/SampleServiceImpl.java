package com.inst.project.sample.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.sample.service.SampleService;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	
	@Autowired
	SampleMapper sampleMapper;
	
	@Override
	public List<Map<String, Object>> getListData() {
		return sampleMapper.getListData();
	}

}
