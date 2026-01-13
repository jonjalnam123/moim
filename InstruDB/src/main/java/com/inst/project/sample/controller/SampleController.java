package com.inst.project.sample.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inst.project.sample.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/sample")
public class SampleController {
	
	@Autowired
	SampleService sampleService;
	
	@GetMapping(value = "")
	public String getSampleMain() {
		log.info("[ SampleController ] : getSampleMain 진입");
		return "sample/sampleMain.adm";
	}
	
	@GetMapping("/view/{viewUrl}")
	public String getSampleView(@PathVariable String viewUrl) {
		log.info("[ SampleController ] : getSampleView 진입");
		String returnUrl = "";
		if(!viewUrl.isEmpty()) {
			switch (viewUrl) {
			case "sampleForm" 		: 	returnUrl = "sample/sampleForm.adm";
						       					break;
			case "sampleList"	  		: 	returnUrl = "sample/sampleList.adm";
							   					break;
			case "sampleTreeForm" 	: 	returnUrl = "sample/sampleTreeForm.adm";
								   				break;
			default						:	returnUrl = "sample/sampleMain.adm";
												break;
			}
		}
		log.info(" VIEWURL : {}", returnUrl);
		List<Map<String,Object>> resultList = sampleService.getListData();
		log.info("resultList : {}", resultList);
		return returnUrl;
	}
	
}
