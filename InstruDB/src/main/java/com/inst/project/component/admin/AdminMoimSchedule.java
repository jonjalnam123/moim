package com.inst.project.component.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inst.project.admin.vo.AdminMoimDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("enable")
public class AdminMoimSchedule {
	
    private final SqlSessionTemplate sqlSession;
    
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    public AdminMoimSchedule(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    
    /**
    * @methodName	 	: 오늘날짜기준 지난 모임일정 진행상태 구분 값 수정 ( 서버 시작 후 1회 )
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminMoimStatusChkAtSvrStart
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    @EventListener(ApplicationReadyEvent.class)
    public void adminMoimStatusChkAtSvrStart() {
    	adminMoimStatusChk();
    }

    /**
    * @methodName	 	: 오늘날짜기준 지난 모임일정 진행상태 구분 값 수정 ( 매일 오전 9시 ) 
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminMoimStatusChkAtNne
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void adminMoimStatusChkAtNne() {
    	adminMoimStatusChk();
    }
    
    
    /**
    * @methodName	 	: 오늘날짜기준 지난 모임일정 진행상태 구분 값 수정 메소드
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminMoimStatusChk
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    private void adminMoimStatusChk() {
        log.info(" [ AdminMoimSchedule ] : adminMoimStatusChk ");
        if (!running.compareAndSet(false, true)) {
            return;
        }
        
        int resultCnt = 0;
        List<String> resultMoimId = new ArrayList<>();
        try {        	
            List<AdminMoimDTO> endMoimList = sqlSession.selectList("adminScheduleMapper.selectEndMoimList");
            if (endMoimList == null || endMoimList.isEmpty()) {
                log.info("[ AdminMoimSchedule ] : endMoimList 의 데이터가 없습니다.");
                return;
            }

            for (AdminMoimDTO endMoim : endMoimList) {
                String moimId = endMoim.getMoimId();
            	endMoim.setUpdId("SCHEDULE SYSTEM");
                int updResult = sqlSession.update("adminScheduleMapper.updateEndMoimList", endMoim);
                if (updResult > 0) {
                    resultCnt++;
                    resultMoimId.add(moimId);
                }
            }

            log.info("===================== updateEndMoimList Result =====================");
            log.info("처리 결과 갯 수 : {}", resultCnt);
            log.info("처리 결과 모임ID : {}", resultMoimId);
            log.info("==============================================================");
            
        } catch (Exception e) {
            log.error("[ AdminMoimSchedule ] adminMoimStatusChk Fail.", e);
            throw new RuntimeException(e);
        }
    }
	
}
