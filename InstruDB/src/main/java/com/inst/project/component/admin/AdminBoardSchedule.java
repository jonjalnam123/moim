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
import com.inst.project.admin.vo.AdminNoticeDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("enable")
public class AdminBoardSchedule {
	
    private final SqlSessionTemplate sqlSession;
    
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    public AdminBoardSchedule(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    
    /**
    * @methodName	 	: 오늘날짜 기준 지난 공지사항 구분 값 수정 ( 서버 시작 후 1회 )
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminNoticeFinishChkAtSvrStart
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    @EventListener(ApplicationReadyEvent.class)
    public void adminNoticeFinishChkAtSvrStart() {
    	adminNoticeFinishChk();
    }

    /**
    * @methodName	 	: 오늘날짜 기준 지난 공지사항 구분 값 수정 ( 매일 오전 9시 ) 
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminNoticeFinishChkAtNine
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void adminNoticeFinishChkAtNine() {
    	adminNoticeFinishChk();
    }

    /**
    * @methodName	 	: 오늘날짜 기준 지난 공지사항 구분 값 수정 메소드
    * @author					: 최정석
    * @date            		: 2026. 5. 29.
    * @description			: adminNoticeFinishChk
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 5. 29.        		최정석       			최초 생성
    */
    private void adminNoticeFinishChk() {
        log.info(" [ AdminBoardSchedule ] : adminNoticeFinishChk ");
        if (!running.compareAndSet(false, true)) {
            return;
        }
        
        int resultCnt = 0;
        List<String> resultNoticeId = new ArrayList<>();
        try {        	
            List<AdminNoticeDTO> finishNoticeList = sqlSession.selectList("adminScheduleMapper.selectFinshNoticeList");
            if (finishNoticeList == null || finishNoticeList.isEmpty()) {
                log.info("[ AdminBoardSchedule ] : finishNoticeList 의 데이터가 없습니다.");
                return;
            }

            for (AdminNoticeDTO finishNotice : finishNoticeList) {
                String moimId = finishNotice.getNoticeId();
            	finishNotice.setUpdId("SCHEDULE SYSTEM");
                int updResult = sqlSession.update("adminScheduleMapper.updateFinshNoticeList", finishNotice);
                if (updResult > 0) {
                    resultCnt++;
                    resultNoticeId.add(moimId);
                }
            }

            log.info("===================== updateFinshNoticeList Result =====================");
            log.info("처리 결과 갯 수 : {}", resultCnt);
            log.info("처리 결과 모임ID : {}", resultNoticeId);
            log.info("==============================================================");
            
        } catch (Exception e) {
            log.error("[ AdminBoardSchedule ] adminNoticeFinishChk Fail.", e);
            throw new RuntimeException(e);
        }
    }
	
}
