package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminMngService;
import com.inst.project.admin.vo.AdminCommDTO;
import com.inst.project.admin.vo.AdminDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminUnitDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.utill.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminMngService")
public class AdminMngServiceImpl implements AdminMngService {

	@Autowired
	AdminMngMapper adminMngMapper;

	/**
	* @methodName	 	: selectCommList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectCommList() {
		log.info(" [ AdminMngServiceImpl ] : selectCommList ");
		try {

			List<AdminCommDTO> adminCommList = adminMngMapper.selectCommList();
			if ( adminCommList == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			return adminCommList;

		} catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectCommList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}

	}
	
	/**
	* @methodName	 	: selectCommList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectCommList2() {
		log.info(" [ AdminMngServiceImpl ] : selectCommList2 ");
		try {
			
			List<AdminCommDTO> adminCommList2 = adminMngMapper.selectCommList2();
			if ( adminCommList2 == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			
			return adminCommList2;
			
		} catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectCommList2 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}
		
	}
	
	/**
	* @methodName	 	: adminCommSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminCommDTO adminCommSelect(AdminCommDTO adminCommDTO) {
	    log.info(" [ AdminMngServiceImpl ] : adminCommSelect ");

	    try {
	        
	    	AdminCommDTO selectResult = adminMngMapper.adminCommSelect(adminCommDTO);
	        if (selectResult == null) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
	        }

	        return selectResult;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminCommSelect failed." );
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	
	/**
	* @methodName	 	: adminCommReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminCommReg(AdminCommDTO adminCommDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminCommReg ");

	    try {
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
			adminCommDTO.setRegId(adminId); 
	        adminCommDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminCommReg(adminCommDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminCommReg failed.", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminCommUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminCommUpd(AdminCommDTO adminCommDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminCommUpd ");

	    try {
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
			    return 0;
			}
			
			adminCommDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminCommUpd(adminCommDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminCommUpd failed.", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminCommDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 공통코드 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminCommDel(AdminCommDTO adminCommDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminCommDel ");

	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
			adminCommDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminCommDel(adminCommDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminCommDel failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
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
			
			List<AdminMenuDTO> adminMenuList = adminMngMapper.selectMenuList();
			if ( adminMenuList == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			
			return adminMenuList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectMenuList failed. {}", e);
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
			
			List<AdminMenuDTO> adminMenuList2= adminMngMapper.selectMenuList2();
			if ( adminMenuList2 == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
			}
			
			return adminMenuList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectMenuList2 failed. {}", e);
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
	        
	    	AdminMenuDTO selectResult = adminMngMapper.adminMenuSelect(adminMenuDTO);
	        if (selectResult == null) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
	        }

	        return selectResult;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminMenuSelect failed." );
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

		    List<Map<String, Object>> result = adminMngMapper.adminMenuDeptCdSelect(menuDeptCd);

		    return result;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminMenuDeptCdSelect failed." );
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
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
	        adminMenuDTO.setRegId(adminId); 
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminMenuReg(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminMenuReg failed.", e);
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
			String replMenuDeptCd = CommonUtil.removeLastComma(menuDeptCd);
			adminMenuDTO.setMenuDeptCd(replMenuDeptCd);
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
			    return 0;
			}
			
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminMenuUpd(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminMenuUpd failed.", e);
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
	    	
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
	        adminMenuDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminMenuDel(adminMenuDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminMenuDel failed.");
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
			
			List<AdminUnitDTO> adminUnitAllList = adminMngMapper.selectUnitAllList();
		    if ( adminUnitAllList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
			
			return adminUnitAllList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectUnitAllList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}
		
	}
	
	/**
	* @methodName	 	: selectUnitList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 1레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList ");
		try {
			
			List<AdminUnitDTO> adminUnitList= adminMngMapper.selectUnitList();
		    if ( adminUnitList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
			return adminUnitList;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : selectUnitList failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectUnitList2
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 2레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList2() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList2 ");
		try {
			
			List<AdminUnitDTO> adminUnitList2= adminMngMapper.selectUnitList2();
		    if ( adminUnitList2 == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
			return adminUnitList2;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : adminUnitList2 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: selectUnitList3
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 3레벨 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminUnitDTO> selectUnitList3() {
		log.info(" [ AdminMngServiceImpl ] : selectUnitList3 ");
		try {
			
			List<AdminUnitDTO> adminUnitList3= adminMngMapper.selectUnitList3();
		    if ( adminUnitList3 == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
			
			return adminUnitList3;
			
		} catch (Exception e) {
			
	        log.error("[ AdminMngServiceImpl ] : adminUnitList3 failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			
			return null;
			
		}

	}
	
	/**
	* @methodName	 	: adminUnitSelect
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 상세 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminUnitDTO adminUnitSelect(AdminUnitDTO adminUnitDTO) {
	    log.info(" [ AdminMngServiceImpl ] : adminUnitSelect ");

	    try {
	        
	    	AdminUnitDTO selectResult = adminMngMapper.adminUnitSelect(adminUnitDTO);
	        if (selectResult == null) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
	        }

	        return selectResult;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminUnitSelect failed." );
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	
	/**
	* @methodName	 	: adminUnitReg
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 등록
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminUnitReg(AdminUnitDTO adminUnitDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminUnitReg ");

	    try {
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
			adminUnitDTO.setRegId(adminId); 
			adminUnitDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminUnitReg(adminUnitDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminUnitReg failed.", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminUnitUpd
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 수정
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminUnitUpd(AdminUnitDTO adminUnitDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminUnitUpd ");

	    try {
			
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
			    return 0;
			}
			
			adminUnitDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminUnitUpd(adminUnitDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminUnitUpd failed.", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: adminUnitDel
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 부서 삭제
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public int adminUnitDel(AdminUnitDTO adminUnitDTO, HttpServletRequest req) {
	    log.info(" [ AdminMngServiceImpl ] : adminUnitDel ");

	    try {
	    	
			String adminId = CommonUtil.getAdminInfoSession("adminId", req);
			if ( CommonUtil.isBlank(adminId) ) {
				log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
				return 0;
			}
			
			adminUnitDTO.setUpdId(adminId);
	        
	        return adminMngMapper.adminUnitDel(adminUnitDTO);

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : adminUnitDel failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}
	
	/**
	* @methodName	 	: selectAdminUser
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 관리 화면 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminDTO> selectAdminUser() {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminUser ");

	    try {
	    	
	    	List<AdminDTO> adminList = adminMngMapper.selectAdminUser();
		    if ( adminList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
	        return adminList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectAdminUser failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: selectAdminGradeList
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 등급 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<AdminCommDTO> selectAdminGradeList() {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminGradeList ");
	    try {
	    	
	    	List<AdminCommDTO> adminGradeList = adminMngMapper.selectAdminGradeList();
		    if ( adminGradeList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
	        return adminGradeList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectAdminGradeList failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: getAdminUserInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 관리자 정보 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminDTO selectAdminUserInfo(AdminDTO adminDTO) {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminUserInfo ");

	    try {
	    	
	    	AdminDTO adminInfo = adminMngMapper.selectAdminUserInfo(adminDTO);
	    	if ( adminInfo == null ) {
	            log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return null;
	    	}  
	    	 
	        return adminInfo;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectAdminUserInfo failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: selectAdminTeamList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 팀 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<Map<String, Object>> selectAdminTeamList(String adminUnitId) {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminTeamList ");

	    try {
	    	
	    	List<Map<String, Object>> adminTeamList = adminMngMapper.selectAdminTeamList(adminUnitId);
		    if ( adminTeamList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
	        return adminTeamList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectAdminTeamList failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
	/**
	* @methodName	 	: selectAdminPositionList
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유닛 직책 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public List<Map<String, Object>> selectAdminPositionList(String adminUnitId) {
	    log.info(" [ AdminMngServiceImpl ] : selectAdminPositionList ");

	    try {
	    	
	    	List<Map<String, Object>> adminPositionList = adminMngMapper.selectAdminPositionList(adminUnitId);
		    if ( adminPositionList == null ) {
		    	log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
		        return null;
		    }
		    
	        return adminPositionList;

	    } catch (Exception e) {
	        log.error("[ AdminMngServiceImpl ] : selectAdminPositionList failed.");
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return null;
	    }
	}
	
}
