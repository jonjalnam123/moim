<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	$(document).ready(function() {
	    if ($.fn.select2) {
	        $('.form-select').select2({
	          placeholder: function(){ return $(this).data('placeholder') || '선택하세요'; },
	          allowClear: true,
	          width: 'resolve',
	          minimumResultsForSearch: Infinity,
	          // ↓↓↓ 스코프 고정: 생성 컨테이너/드롭다운에 식별 클래스 부여
	          containerCssClass: 'ez-s2',
	          selectionCssClass: 'ez-s2',       // 4.1 에서 selection에 직접 클래스
	          dropdownCssClass: 'ez-s2',
	          // ↓↓↓ 드롭다운을 이 폼 카드 안에 붙여서 상위 선택자 스코프도 유지
	          dropdownParent: $('.form-card')
	        });
	      }
	  
	});
</script>

<!-- Draw view [S] -->
<div class="content-scroll">
  <div class="content-wrapper">
    <div class="page-header">
      <h2>샘플</h2>
      <div class="breadcrumb">
      <a href="#">샘플</a>&nbsp;&gt;&nbsp;<span>폼화면</span>
      </div>
    </div>

    <form class="form-card" action="${pageContext.request.contextPath}/admin/request/save.do" method="post" enctype="multipart/form-data">
      <h3 class="form-title">요청 정보</h3>
      <p class="form-desc">필수 항목을 정확히 입력해 주세요. 파일은 최대 20MB까지 업로드됩니다.</p>

      <div class="form-grid">
        <div class="field">
          <label for="title" class="required">제목</label>
          <input id="title" name="title" class="form-control" type="text" placeholder="예) 배치 작업 개선 요청" required />
          <small class="hint">업무를 쉽게 파악할 수 있게 간결하게 작성</small>
          <small class="error">제목은 필수입니다.</small>
        </div>

		<div class="field">
		  <label for="categorySelect" class="required">카테고리</label>
		  <select id="categorySelect" name="category" class="form-select" style="width:100%;" data-placeholder="카테고리를 선택하세요">
		    <option value=""></option> <!-- allowClear 동작을 위한 빈 옵션 -->
		    <option value="REQ">요청</option>
		    <option value="BUG">버그</option>
		    <option value="IMPROVE">개선</option>
		    <option value="ETC">기타</option>
		  </select>
		  <small class="error">카테고리를 선택해 주세요.</small>
		</div>

        <div class="field">
          <label for="requester" class="required">요청자</label>
          <input id="requester" name="requester" class="form-control" type="text" placeholder="홍길동" required />
          <small class="error">요청자를 입력해 주세요.</small>
        </div>

        <div class="field">
          <label for="email" class="required">이메일</label>
          <input id="email" name="email" class="form-control" type="email" placeholder="name@example.com" required />
          <small class="error">유효한 이메일 주소가 필요합니다.</small>
        </div>

        <div class="field">
          <label for="date" class="required">희망 처리일</label>
          <input id="date" name="date" class="form-control" type="date" required />
          <small class="error">희망 처리일을 선택해 주세요.</small>
        </div>

        <div class="field">
          <label for="phone">연락처</label>
          <input id="phone" name="phone" class="form-control" type="tel" placeholder="010-1234-5678" pattern="^0\\d{1,2}-\\d{3,4}-\\d{4}$" />
          <small class="hint">형식: 010-1234-5678</small>
          <small class="error">연락처 형식이 올바르지 않습니다.</small>
        </div>

        <div class="field full">
          <label for="desc" class="required">요청 상세</label>
          <textarea id="desc" name="desc" class="form-control" placeholder="상세 내용을 적어주세요." required></textarea>
          <small class="error">요청 상세는 필수입니다.</small>
        </div>

        <div class="field">
          <label class="required">우선순위</label>
          <div class="radio-chips">
            <input type="radio" id="p1" name="priority" value="LOW" required />
            <label for="p1">낮음</label>
            <input type="radio" id="p2" name="priority" value="MEDIUM" />
            <label for="p2">보통</label>
            <input type="radio" id="p3" name="priority" value="HIGH" />
            <label for="p3">높음</label>
          </div>
          <small class="error">우선순위를 선택해 주세요.</small>
        </div>

		<div class="field">
		  <label>알림 수신</label>
		  <div style="display:flex; align-items:center; gap:10px;">
		    <!-- label.switch로 감싸면 토글 전체가 클릭 타겟 -->
		    <label class="switch" aria-label="이메일 알림">
		      <input id="notify" type="checkbox" name="notify" />
		      <span class="track"></span>
		      <span class="thumb"></span>
		    </label>
		    <!-- 텍스트 라벨은 그대로 유지(텍스트 클릭도 동작) -->
		    <label for="notify" style="margin:0; font-size:0.95rem; color:#425a4f;">
		      이메일로 상태 알림 받기
		    </label>
		  </div>
		</div>

        <div class="field full">
          <label>첨부파일</label>
          <div class="filebox">
            <label class="btn btn-file" for="file">파일 선택</label>
            <input id="file" name="file" type="file" accept=".pdf,.png,.jpg,.jpeg,.xlsx,.xls,.zip" />
            <span class="filename" id="filename">선택된 파일이 없습니다.</span>
          </div>
          <div class="hint">최대 20MB, 여러 파일이 필요하면 ZIP으로 묶어 업로드</div>
        </div>
      </div>

      <div class="form-actions">
        <button type="button" class="btn btn-cancel" onclick="history.back();">취소</button>
        <button type="submit" class="btn btn-insert">등록</button>
      </div>
    </form>
  </div>
</div>

<!-- Draw view [E] -->