package com.inst.project.sample.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {

	List<Map<String, Object>> getListData();

}
