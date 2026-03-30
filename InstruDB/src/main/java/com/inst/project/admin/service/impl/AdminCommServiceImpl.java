package com.inst.project.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.inst.project.admin.service.AdminCommService;
import com.inst.project.admin.vo.AdminFileDTO;
import com.inst.project.admin.vo.AdminMenuDTO;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;
import com.inst.project.common.GlobalConfig;
import com.inst.project.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("adminCommService")
public class AdminCommServiceImpl implements AdminCommService {

	@Autowired
	AdminCommMapper adminCommMapper;
	
	/**
	* @methodName	 	: selectUniqueDupliChk
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유니크 값 중복 체크
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String selectUniqueDupliChk(Map<String, Object> bodyMap) {
		log.info(" [ AdminCommServiceImpl ] : selectUniqueDupliChk ");
		try {
			
			String result = "";
			String uniqueKey = String.valueOf(bodyMap.get("uniqueKey"));
			String tableNm = String.valueOf(bodyMap.get("tableNm"));
			if (CommonUtil.isBlank(uniqueKey) || CommonUtil.isBlank(tableNm) ) {
				return GlobalConfig.N;
			}
			
			int resultChk = 0;
			if ( tableNm.equals("tb_common_info") ) {
				String uniqueKeyVal = uniqueKey.toUpperCase();
				bodyMap.put("uniqueKey", uniqueKeyVal);
				resultChk = adminCommMapper.selectCommGroupCdDupliChk(bodyMap);
			} else if ( tableNm.equals("tb_admin_unit_info") ) {
				String uniqueKeyVal = uniqueKey.toUpperCase();
				bodyMap.put("uniqueKey", uniqueKeyVal);
				resultChk = adminCommMapper.selectUnitCdDupliChk(bodyMap);
			} else if ( tableNm.equals("tb_admin_menu_info") ) {
				String uniqueKeyVal = uniqueKey.toUpperCase();
				bodyMap.put("uniqueKey", uniqueKeyVal);
				resultChk = adminCommMapper.selectMenuNmDupliChk(bodyMap);
			} else if ( tableNm.equals("tb_admin_info") ) {
				resultChk = adminCommMapper.selectAdminIdDupliChk(bodyMap);
			}

			if( resultChk > 0 ) {
				result = GlobalConfig.N;
			} else {
				result = GlobalConfig.Y;
			}
			
			return result;

		} catch (Exception e) {
	        log.error("[ AdminCommServiceImpl ] : selectCommList failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}

	}
	
	/**
	* @methodName	 	: selectUniqueId
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 유니크 값 생성
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public String selectUniqueId() {
		log.info(" [ AdminCommServiceImpl ] : selectUniqueDupliChk ");
		try {
			
			String result = adminCommMapper.selectUniqueId();
			if (CommonUtil.isBlank(result)) {
				return null;
			}

			return result;

		} catch (Exception e) {
	        log.error("[ AdminCommServiceImpl ] : selectUniqueDupliChk failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}
	}
	
	/**
	* @methodName	 	: favoriteMenuDef
	* @author				: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메뉴 즐겨찾기
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int favoriteMenuDef(AdminMenuFavoriteDTO adminMenuFavoriteDTO, HttpServletRequest req) {
	    log.info("[AdminCommServiceImpl] : favoriteMenuDef");

	    try {
	    	
//            HttpSession session = req.getSession();
	    	
	        String adminId = CommonUtil.getAdminInfoSession("SS_ADMIN_ID", req);
	        String menuId = CommonUtil.getAdminInfoSession("SS_MENU_ID", req);
	        String menuNm = CommonUtil.getAdminInfoSession("SS_MENU_NM", req);
	        String menuPNm = CommonUtil.getAdminInfoSession("SS_MENU_PNM", req);
	        String menuUrl = CommonUtil.getAdminInfoSession("SS_MENU_URL", req);

	        if ( CommonUtil.isBlank(adminId) || CommonUtil.isBlank(menuId) || CommonUtil.isBlank(menuNm) || 
	        	 CommonUtil.isBlank(menuNm) || CommonUtil.isBlank(menuPNm) || CommonUtil.isBlank(menuUrl) ) {
	            log.info(GlobalConfig.RESULT_SESSION_FAIL_DATA_MSG);
	            return 0;
	        }
	        
	        adminMenuFavoriteDTO.setMenuFavoriteAdminId(adminId);
	        adminMenuFavoriteDTO.setMenuFavoriteMenuId(menuId);
	        adminMenuFavoriteDTO.setMenuFavoriteNm(menuNm);
	        adminMenuFavoriteDTO.setMenuFavoritePNm(menuPNm);
	        adminMenuFavoriteDTO.setMenuFavoriteUrl(menuUrl);
	        adminMenuFavoriteDTO.setRegId(adminId);
	        adminMenuFavoriteDTO.setUpdId(adminId);

	        String flag = CommonUtil.isNull(adminMenuFavoriteDTO.getFlag());
	        if (CommonUtil.isBlank(flag)) {
	            log.error(GlobalConfig.RESULT_NULL_DATA_MSG);
	            return 0;
	        }

	        int result = 0;
	        int updRsult = 0;

	        if ("Y".equals(flag)) { // 즐겨찾기 추가
	            result = adminCommMapper.insertFavoriteMenu(adminMenuFavoriteDTO);

	            if (result <= 0) {
	                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	                log.error(GlobalConfig.RESULT_INSERT_FAIL_MSG);
	                return 0;
	            }
	            
	            adminMenuFavoriteDTO.setMenuFavoriteYn(flag);
	            updRsult = adminCommMapper.updFavoriteMenu(adminMenuFavoriteDTO);
	           
//	            session.removeAttribute("SS_FAV_MENU_RESULT");
//	            session.removeAttribute("SS_FAV_MENU_ID_RESULT");
//	            session.setAttribute("SS_FAV_MENU_RESULT", result);
//	            session.setAttribute("SS_FAV_MENU_ID_RESULT", menuId);

	        } else if ("N".equals(flag)) { // 즐겨찾기 삭제
	            result = adminCommMapper.deleteFavoriteMenu(adminMenuFavoriteDTO);

	            if (result <= 0) {
	                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	                log.error(GlobalConfig.RESULT_DEL_FAIL_MSG);
	                return 0;
	            }
	            
	            adminMenuFavoriteDTO.setMenuFavoriteYn(flag);
	            updRsult = adminCommMapper.updFavoriteMenu(adminMenuFavoriteDTO);
	            
//	            session.removeAttribute("SS_FAV_MENU_RESULT");
//	            session.removeAttribute("SS_FAV_MENU_ID_RESULT");
	           // session.setAttribute("SS_FAV_MENU_RESULT", result);

	        } else {
	            log.error("[AdminCommServiceImpl] : invalid flag value. flag={}", flag);
	            return 0;
	        }

	        return result;

	    } catch (Exception e) {
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        log.error("[AdminCommServiceImpl] favoriteMenuDef failed", e);
	        log.error(GlobalConfig.RESULT_SYS_ERR_CD);
	        log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
	        return 0;
	    }
	}

	/**
	* @methodName	 	: selectFileInfo
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 파일 다운로드용 파일 정보 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@Override
	public AdminFileDTO selectFileInfo(String fileId, String refType) {
		log.info(" [ AdminCommServiceImpl ] : selectFileInfo ");
		try {
			
			AdminFileDTO fileInfo = adminCommMapper.selectFileInfo(fileId, refType);
			if( fileInfo == null ) {
				return null;
			}
			
			return fileInfo;

		} catch (Exception e) {
	        log.error("[ AdminCommServiceImpl ] : selectFileInfo failed", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}
	}
	
	
	
}
