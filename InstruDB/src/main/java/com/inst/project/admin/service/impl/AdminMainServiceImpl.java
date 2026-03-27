package com.inst.project.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMainService;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMainService")
public class AdminMainServiceImpl implements AdminMainService {

	@Autowired
	AdminMainMapper adminMainMapper;
	
	/**
	* @methodName	 	: getAdminMenuInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public Map<String, Object> selectAdminMenuInfo() {
		log.info(" [ AdminMainServiceImpl ] : selectAdminMenuInfo ");
		
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			
			// 관리자 메뉴 1레벨 조회
			List<AdminMenuDTO> menuList = adminMainMapper.selectAdminMenuInfo();
			
			// 관리자 메뉴 2레벨 조회
			List<AdminMenuDTO> menuList2 = adminMainMapper.selectAdminMenuInfo2();
			
			if ( menuList == null && menuList2 == null ) {
				result = null;
			}
			
			result.put("menuList", menuList);
			result.put("menuList2", menuList2);
			
			return result;
			
		} catch (Exception e) {
	        log.error("[ AdminMainServiceImpl ] : selectCommList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}
	}
	
	/**
	* @methodName	 	: selectAdminMainNoticeRegCnt
	* @author					: 최정석
	* @date            		: 2026. 03. 19.
	* @description			: 관리자 메인 공지사항 등록 건 수 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int selectAdminMainNoticeRegCnt() {
		log.info(" [ AdminMainServiceImpl ] : selectAdminMainNoticeRegCnt ");
		
		try {
			
			return adminMainMapper.selectAdminMainNoticeRegCnt();
			
		} catch (Exception e) {
	        log.error("[ AdminMainServiceImpl ] : selectAdminMainNoticeRegCnt failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return 0;
		}
	}
	
	/**
	* @methodName	 	: selectAdminMainNoticeList
	* @author					: 최정석
	* @date            		: 2026. 03. 19.
	* @description			: 관리자 메인 공지사항 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminNoticeDTO> selectAdminMainNoticeList() {
		log.info(" [ AdminMainServiceImpl ] : selectAdminMainNoticeList ");
		
		try {
			
			// 관리자 메뉴 1레벨 조회
			List<AdminNoticeDTO> adminMainNoticeList = adminMainMapper.selectAdminMainNoticeList(); 
			if ( adminMainNoticeList == null ) {
				return null;
			}
			
			return adminMainNoticeList;
			
		} catch (Exception e) {
	        log.error("[ AdminMainServiceImpl ] : selectCommList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}
	}
	
	/**
	* @methodName	 	: selectAdminMainFavMenuList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메인 즐겨찾기 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMenuFavoriteDTO> selectAdminMainFavMenuList() {
		log.info(" [ AdminMainServiceImpl ] : selectAdminMainFavMenuList ");
		
		try {
			
			// 관리자 메뉴 1레벨 조회
			List<AdminMenuFavoriteDTO> adminMainFavMenuList = adminMainMapper.selectAdminMainFavMenuList(); 
			if ( adminMainFavMenuList == null ) {
				return null;
			}
			
			return adminMainFavMenuList;
			
		} catch (Exception e) {
	        log.error("[ AdminMainServiceImpl ] : selectAdminMainFavMenuList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
		}
	}
	
}
