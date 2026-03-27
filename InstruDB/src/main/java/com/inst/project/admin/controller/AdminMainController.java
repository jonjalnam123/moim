package com.inst.project.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inst.project.admin.service.AdminMainService;
import com.inst.project.admin.vo.AdminMenuFavoriteDTO;
import com.inst.project.admin.vo.AdminNoticeDTO;
import com.inst.project.admin.vo.DashboardSummaryDTO;
import com.inst.project.admin.vo.MeetingScheduleDTO;
import com.inst.project.admin.vo.RecentActivityDTO;
import com.inst.project.common.GlobalConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminMainController {
	
	@Autowired
	AdminMainService adminMainService;
	
	/**
	* @methodName	 	: getAdminMain
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 메인 화면 호출
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	@GetMapping(value = "/main.do")
	public String getAdminMain ( Model model, RedirectAttributes redirect ) {
		log.info(" [ AdminMainController ] : getAdminMain ");
		
		// 메일공지사항 당일 등록 건 수 조회
		int adminMainNoticeRegCnt = adminMainService.selectAdminMainNoticeRegCnt();
		
		// 메인 공지사항 조회
		List<AdminNoticeDTO> adminMainNoticeList = adminMainService.selectAdminMainNoticeList();
		
		if( adminMainNoticeList == null ) {
			redirect.addAttribute("adminErrorCd", GlobalConfig.RESULT_NULL_DATA_CD);
			redirect.addAttribute("adminErrorMsg", GlobalConfig.RESULT_NULL_DATA_MSG);
			return "redirect:/admin/error.do";
		}
		
        model.addAttribute("todayYmd", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        model.addAttribute("dashboard", buildDashboardSummary());
        
        // 공지사항
        model.addAttribute("adminMainNoticeRegCnt", adminMainNoticeRegCnt);
        model.addAttribute("adminMainNoticeList", adminMainNoticeList);

        model.addAttribute("meetingScheduleList", buildMeetingScheduleList());
        model.addAttribute("recentActivityList", buildRecentActivityList());
		
		return "admin/main/adminMain.adm";
	}
	
	 private DashboardSummaryDTO buildDashboardSummary() {
		 DashboardSummaryDTO vo = new DashboardSummaryDTO();

	        vo.setTotalMemberCnt(1284);
	        vo.setMemberGrowthRate(12);

	        vo.setTotalMeetingCnt(342);
	        vo.setMonthMeetingCnt(18);

	        vo.setAvgAttendRate(87);
	        vo.setActiveGroupCnt(26);

	        vo.setMemberGoalRate(74);
	        vo.setMeetingGoalRate(81);
	        vo.setNoticeReadRate(92);

	        vo.setNewMemberCnt(38);
	        vo.setWeekMeetingCnt(9);

	        return vo;
	    }

	    private List<MeetingScheduleDTO> buildMeetingScheduleList() {
	        List<MeetingScheduleDTO> list = new ArrayList<MeetingScheduleDTO>();

	        MeetingScheduleDTO m1 = new MeetingScheduleDTO();
	        m1.setStartTime("10:00");
	        m1.setMeetingTitle("정기 운영 회의");
	        m1.setLocation("본관 3층 회의실");
	        m1.setExpectedCnt(12);
	        list.add(m1);

	        MeetingScheduleDTO m2 = new MeetingScheduleDTO();
	        m2.setStartTime("13:30");
	        m2.setMeetingTitle("신규 회원 오리엔테이션");
	        m2.setLocation("세미나실 A");
	        m2.setExpectedCnt(24);
	        list.add(m2);

	        MeetingScheduleDTO m3 = new MeetingScheduleDTO();
	        m3.setStartTime("15:00");
	        m3.setMeetingTitle("소모임 리더 간담회");
	        m3.setLocation("온라인 Zoom");
	        m3.setExpectedCnt(8);
	        list.add(m3);

	        MeetingScheduleDTO m4 = new MeetingScheduleDTO();
	        m4.setStartTime("17:30");
	        m4.setMeetingTitle("주간 활동 리뷰");
	        m4.setLocation("운영센터");
	        m4.setExpectedCnt(6);
	        list.add(m4);

	        MeetingScheduleDTO m5 = new MeetingScheduleDTO();
	        m5.setStartTime("19:00");
	        m5.setMeetingTitle("자유 네트워킹 모임");
	        m5.setLocation("카페 라운지");
	        m5.setExpectedCnt(18);
	        list.add(m5);

	        return list;
	    }

	    private List<RecentActivityDTO> buildRecentActivityList() {
	        List<RecentActivityDTO> list = new ArrayList<RecentActivityDTO>();

	        RecentActivityDTO a1 = new RecentActivityDTO();
	        a1.setActivityType("회원");
	        a1.setTitle("신규 회원 가입");
	        a1.setDescription("김민수 님이 신규 가입 신청을 완료했습니다.");
	        a1.setRegDate("5분 전");
	        list.add(a1);

	        RecentActivityDTO a2 = new RecentActivityDTO();
	        a2.setActivityType("공지");
	        a2.setTitle("공지사항 등록");
	        a2.setDescription("‘3월 정기모임 장소 변경 안내’ 공지가 등록되었습니다.");
	        a2.setRegDate("18분 전");
	        list.add(a2);

	        RecentActivityDTO a3 = new RecentActivityDTO();
	        a3.setActivityType("회원");
	        a3.setTitle("회원 등급 변경");
	        a3.setDescription("박지연 님이 일반회원에서 운영진으로 변경되었습니다.");
	        a3.setRegDate("32분 전");
	        list.add(a3);

	        RecentActivityDTO a4 = new RecentActivityDTO();
	        a4.setActivityType("오류");
	        a4.setTitle("출석 동기화 지연");
	        a4.setDescription("모바일 출석 데이터 반영이 일시적으로 지연되었으나 현재는 정상 처리되었습니다.");
	        a4.setRegDate("1시간 전");
	        list.add(a4);

	        RecentActivityDTO a5 = new RecentActivityDTO();
	        a5.setActivityType("기타");
	        a5.setTitle("모임 일정 수정");
	        a5.setDescription("소모임 리더 간담회 일정이 14:00에서 15:00로 변경되었습니다.");
	        a5.setRegDate("2시간 전");
	        list.add(a5);

	        RecentActivityDTO a6 = new RecentActivityDTO();
	        a6.setActivityType("공지");
	        a6.setTitle("이벤트 배너 노출 시작");
	        a6.setDescription("봄 시즌 네트워킹 이벤트 배너가 메인에 노출되었습니다.");
	        a6.setRegDate("오늘 09:00");
	        list.add(a6);

	        return list;
	    }
	
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
	@PostMapping(value = "/menuInfo.do")
	@ResponseBody
	public Map<String,Object> getAdminMenuInfo() {
		log.info(" [ AdminMainController ] : getAdminMenuInfo ");
		
		Map<String,Object> result = adminMainService.selectAdminMenuInfo();
		if ( result == null ) {
			log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
			result = null;
		}
		
		return result;
	}

}
