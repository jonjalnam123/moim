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

import com.inst.project.admin.service.AdminMainService;
import com.inst.project.admin.vo.DashboardSummaryDTO;
import com.inst.project.admin.vo.MeetingScheduleDTO;
import com.inst.project.admin.vo.NoticeDTO;
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
	public String getAdminMain(Model model) {
		log.info(" [ AdminMainController ] : getAdminMain ");
		
        model.addAttribute("todayYmd", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        model.addAttribute("dashboard", buildDashboardSummary());
        model.addAttribute("noticeList", buildNoticeList());
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

	    private List<NoticeDTO> buildNoticeList() {
	        List<NoticeDTO> list = new ArrayList<NoticeDTO>();

	        NoticeDTO n1 = new NoticeDTO();
	        n1.setNoticeId("NT202603170001");
	        n1.setNoticeType("중요");
	        n1.setTitle("3월 정기모임 장소 변경 안내");
	        n1.setSummary("기존 강남 스터디룸에서 역삼 세미나실로 변경되었습니다. 참석 전 반드시 위치를 확인해 주세요.");
	        n1.setRegDate("2026-03-17");
	        list.add(n1);

	        NoticeDTO n2 = new NoticeDTO();
	        n2.setNoticeId("NT202603160002");
	        n2.setNoticeType("일반");
	        n2.setTitle("신규 회원 가입 승인 프로세스 안내");
	        n2.setSummary("관리자 승인 이후 그룹 배정까지의 처리 절차가 일부 변경되었습니다.");
	        n2.setRegDate("2026-03-16");
	        list.add(n2);

	        NoticeDTO n3 = new NoticeDTO();
	        n3.setNoticeId("NT202603150003");
	        n3.setNoticeType("이벤트");
	        n3.setTitle("봄 시즌 네트워킹 이벤트 신청 오픈");
	        n3.setSummary("회원 간 교류 활성화를 위한 네트워킹 이벤트 신청이 시작되었습니다.");
	        n3.setRegDate("2026-03-15");
	        list.add(n3);

	        NoticeDTO n4 = new NoticeDTO();
	        n4.setNoticeId("NT202603140004");
	        n4.setNoticeType("일반");
	        n4.setTitle("출석 체크 방식 개선 안내");
	        n4.setSummary("모바일 출석 인증과 관리자 수동 체크 방식을 병행 운영합니다.");
	        n4.setRegDate("2026-03-14");
	        list.add(n4);

	        NoticeDTO n5 = new NoticeDTO();
	        n5.setNoticeId("NT202603130005");
	        n5.setNoticeType("중요");
	        n5.setTitle("개인정보 처리방침 개정 공지");
	        n5.setSummary("회원정보 보관 및 파기 기준이 최신 운영정책에 맞춰 일부 개정되었습니다.");
	        n5.setRegDate("2026-03-13");
	        list.add(n5);

	        NoticeDTO n6 = new NoticeDTO();
	        n6.setNoticeId("NT202603120006");
	        n6.setNoticeType("일반");
	        n6.setTitle("운영진 회의록 공유");
	        n6.setSummary("지난 운영진 회의에서 결정된 주요 안건을 공지사항에서 확인하실 수 있습니다.");
	        n6.setRegDate("2026-03-12");
	        list.add(n6);

	        return list;
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
		if ( result.isEmpty() || result == null ) {
			log.info(GlobalConfig.RESULT_NULL_DATA_MSG);
			result = null;
		}
		
		return result;
	}

}
