package com.inst.project.component.admin;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("disabled")
public class AdminBoardSchedule {

    @Scheduled(fixedDelayString = "${scheduler.admin.board.time}")
    public void adminNoticeLimitChk() {
        System.out.println("Hello World");
    }
	
}
