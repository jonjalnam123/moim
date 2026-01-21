package com.inst.project.common;

import java.time.format.DateTimeFormatter;

public class GlobalConfig {
    
    // 생성 막기 (new GlobalConst() 금지)
    private GlobalConfig() {}

    /* ============================
     *  공통 결과 코드 / 메시지
     * ============================ */

    public static final String RESULT_SUCC_CD      			= "00";
    public static final String RESULT_SUCC_MSG     			= "[ RESULT ] : SUCCESS";

    public static final String RESULT_FAIL_CD      				= "01";
    public static final String RESULT_FAIL_MSG     			= "[ RESULT ] : FAIL";

    public static final String RESULT_PARAM_ERR_CD 		= "02";
    public static final String RESULT_PARAM_ERR_MSG 		= "[ INVALID_PARAMETER ] : 파라미터 오류";

    public static final String RESULT_AUTH_ERR_CD  		= "03";
    public static final String RESULT_AUTH_ERR_MSG 		= "[ UNAUTHORIZED ] : 승인 오류";
    
    public static final String RESULT_VIEW_ERR_CD  		= "04";
    public static final String RESULT_VIEW_ERR_MSG 		= "[ VIEW_ERROR ] : 화면 오류";
    
    public static final String RESULT_MAP_CD   					= "05";
    public static final String RESULT_MAP_MSG  				= "[ MAP_RESULT ] : {}";
    
    public static final String RESULT_MAP_FAIL_CD   			= "06";
    public static final String RESULT_MAP_FAIL_MSG  		= "[ MAP_RESULT ] : 조회실패";
    
    public static final String RESULT_PARAM_CD   				= "07";
    public static final String RESULT_PARAM_MSG  			= "[ VIEW PARAM ] : {}";
    
    public static final String RESULT_NULL_DATA_CD  		= "08";
    public static final String RESULT_NULL_DATA_MSG  		= "[ DATA ] : 데이터가 없습니다.";
    
    public static final String RESULT_INSERT_SUC_CD  		= "09";
    public static final String RESULT_INSERT_SUC_MSG  	= "[ INSERT ] : SUCCESS";
    
    public static final String RESULT_INSERT_FAIL_CD  		= "10";
    public static final String RESULT_INSERT_FAIL_MSG  	= "[ INSERT ] : FAIL";
    
    public static final String RESULT_UPDATE_SUC_CD  		= "11";
    public static final String RESULT_UPDATE_SUC_MSG  	= "[ UPDATE ] : SUCCESS";
    
    public static final String RESULT_UPDATE_FAIL_CD  	= "12";
    public static final String RESULT_UPDATE_FAIL_MSG  	= "[ UPDATE ] : FAIL";
    
    public static final String RESULT_DEL_SUC_CD  			= "13";
    public static final String RESULT_DEL_SUC_MSG  			= "[ DELETE ] : SUCCESS";
    
    public static final String RESULT_DEL_FAIL_CD  			= "14";
    public static final String RESULT_DEL_FAIL_MSG  		= "[ DELETE ] : FAIL";
    
    public static final String RESULT_SESSION_SUC_CD  	= "17";
    public static final String RESULT_SESSION_SUC_MSG  	= "[ SESSION ] : SUCCESS";
    
    public static final String RESULT_SESSION_FAIL_CD  	= "18";
    public static final String RESULT_SESSION_FAIL_MSG 	= "[ SESSION ] : FAIL";
    
    public static final String RESULT_LIST_CD   					= "19";
    public static final String RESULT_LIST_MSG  				= "[ LIST_RESULT ] : {}";
    
    public static final String RESULT_SESSION_FAIL_DATA_CD  	= "20";
    public static final String RESULT_SESSION_FAIL_DATA_MSG 	= "[ SESSION ] : NO DATA";
    
    public static final String RESULT_AOP_BEF_ERR_CD   	= "98";
    public static final String RESULT_AOP_BEF_ERR_MSG  = "[ AOP_BEFORE ] : 로그 ISNERT 시스템 실패";

    public static final String RESULT_SYS_ERR_CD   			= "99";
    public static final String RESULT_SYS_ERR_MSG  			= "[ SYSTEM_ERROR ] : 시스템 에러";

    /* ============================
     *  공통 플래그(Y/N 등)
     * ============================ */

    public static final String Y 	     	= "Y";
    public static final String N         	= "N";
    public static final String D 			= "D";

    public static final String USE_Y     = "Y";
    public static final String USE_N     = "N";

    public static final String DEL_Y     = "Y";
    public static final String DEL_N     = "N";

    /* ============================
     *  날짜 / 시간 포맷 패턴
     * ============================ */

    // 예: 20251120
    public static final String FMT_YMD        = "yyyyMMdd";

    // 예: 2025-11-20
    public static final String FMT_YMD_DASH   = "yyyy-MM-dd";

    // 예: 20251120153045
    public static final String FMT_YMD_HMS    = "yyyyMMddHHmmss";

    // 예: 2025-11-20 15:30:45
    public static final String FMT_YMD_HMS_BAR = "yyyy-MM-dd HH:mm:ss";
    
    
    // 날짜 형식 지정
    public static final DateTimeFormatter ERR_FMT = DateTimeFormatter.ofPattern(GlobalConfig.FMT_YMD_HMS_BAR);

    /* ============================
     *  페이징 기본값
     * ============================ */

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE     = 100;

    /* ============================
     *  인코딩 / 컨텐츠 타입
     * ============================ */

    public static final String CHARSET_UTF8       = "UTF-8";
    public static final String CONTENT_TYPE_JSON  = "application/json; charset=UTF-8";
    public static final String CONTENT_TYPE_HTML  = "text/html; charset=UTF-8";

    /* ============================
     *  시스템/업무 구분 코드 (예시)
     * ============================ */

    public static final String SYS_GB_INSTRUDB     = "InstruDB";
}

