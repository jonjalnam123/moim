package com.inst.project.admin.service.impl; 

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminUserHomeService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminUserHomeService")
public class AdminUserHomeServiceImpl implements AdminUserHomeService {

	@Autowired
	AdminUserHomeMapper adminUserHomeMapper;
	
	/**
	* @methodName	 	: selectMenuList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMenuDTO> selectMenuList() {
		log.info(" [ AdminMngServiceImpl ] : selectMenuList ");
		
		try {
			
			List<AdminMenuDTO> adminMenuList = adminUserHomeMapper.selectMenuList();
			if ( adminMenuList == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			
			return adminMenuList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] selectMenuList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}
	}
	
	/**
	* @methodName	 	: selectMenuList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminMenuDTO> selectMenuList2() {
		log.info(" [ AdminMngServiceImpl ] : selectMenuList2 ");
		try {
			
			List<AdminMenuDTO> adminMenuList2= adminUserHomeMapper.selectMenuList2();
			if ( adminMenuList2 == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			
			return adminMenuList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] selectMenuList2 failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectAdminGradeList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 등급 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectAdminGradeList() {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminGradeList ");
	    try {
	    	
	    	List<AdminCommDTO> adminGradeList = adminUserHomeMapper.selectAdminGradeList();
		    if ( adminGradeList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
	        return adminGradeList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] selectAdminGradeList failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: adminMenuSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 상세조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminMenuDTO adminMenuSelect( AdminMenuDTO adminMenuDTO ) {
	    log.info(" [ AdminMngServiceImpl ] : adminMenuSelect ");

	    try {
	        
	    	AdminMenuDTO selectResult = adminUserHomeMapper.adminMenuSelect(adminMenuDTO);
	        if (selectResult == null) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
	        }

	        return selectResult;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMenuSelect failed", e );
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: adminMenuDeptCdSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 상세조회 결과로 부서코드 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<Map<String, Object>> adminMenuDeptCdSelect(AdminMenuDTO adminMenuDTO) {
		 log.info(" [ AdminMngServiceImpl ] : adminMenuDeptCdSelect ");
		 
	    try {
	        
		    String menuDeptCd = adminMenuDTO.getMenuDeptCd();
		    if ( CommonUtil.isBlank(menuDeptCd) ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }

		    List<Map<String, Object>> result = adminUserHomeMapper.adminMenuDeptCdSelect(menuDeptCd);

		    return result;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMenuDeptCdSelect failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }

	}
	
	/**
	* @methodName	 	: adminMenuReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminMenuReg(AdminMenuDTO adminMenuDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminMenuReg ");

	    try {

			String menuDeptCd = adminMenuDTO.getMenuDeptCd();
			String replMenuDeptCd = CommonUtil.removeLastComma(menuDeptCd);
			adminMenuDTO.setMenuDeptCd(replMenuDeptCd);
			
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
	        adminMenuDTO.setRegId(adminId); 
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminUserHomeMapper.adminMenuReg(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMenuReg failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminMenuUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminMenuUpd(AdminMenuDTO adminMenuDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminMenuUpd ");

	    try {

			String menuDeptCd = adminMenuDTO.getMenuDeptCd();
			String replMenuDeptCd = CommonUtil.removeLastComma(CommonUtil.isNull(menuDeptCd));
			adminMenuDTO.setMenuDeptCd(replMenuDeptCd);
			
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
			    return 0;
			}
			
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminUserHomeMapper.adminMenuUpd(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMenuUpd failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminMenuDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminMenuDel(AdminMenuDTO adminMenuDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminMenuDel ");

	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminUserHomeMapper.adminMenuDel(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] adminMenuDel failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: selectUnitAllList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitAllList() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitAllList ");
		
		try {
			
			List<AdminUnitDTO> adminUnitAllList = adminUserHomeMapper.selectUnitAllList();
		    if ( adminUnitAllList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
			
			return adminUnitAllList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] selectUnitAllList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}
		
	}
	
}
