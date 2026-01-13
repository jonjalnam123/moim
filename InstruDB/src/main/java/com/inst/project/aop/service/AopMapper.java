package com.inst.project.aop.service;

import org.apache.ibatis.annotations.Mapper;

import com.inst.project.aop.vo.AopDTO;

@Mapper
public interface AopMapper {

	void insertAopLog(AopDTO vo);

}
