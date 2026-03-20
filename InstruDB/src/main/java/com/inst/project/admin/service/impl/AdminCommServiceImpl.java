package com.inst.project.admin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inst.project.admin.service.AdminCommService;
import com.inst.project.admin.vo.AdminFileDTO;
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
	        log.error("[ AdminMngServiceImpl ] : selectCommList failed. {}", e);
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
	        log.error("[ AdminMngServiceImpl ] : selectUniqueDupliChk failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
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
	        log.error("[ AdminMngServiceImpl ] : selectFileInfo failed. {}", e);
			log.error(GlobalConfig.RESULT_SYS_ERR_CD);
			log.error(GlobalConfig.RESULT_SYS_ERR_MSG);
			return null;
		}
	}
	
	
}
