<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
$(document).ready(function() {
	// 트리 열기/닫기
	$(document).on('click', '.tree .tw', function(e){
	  e.preventDefault();
	  $(this).closest('li').toggleClass('open');
	});
	
	// 트리 항목 선택 시 활성 표시
	$(document).on('click', '.tree a', function(e){
	  e.preventDefault();
	  $('.tree a').removeClass('active');
	  $(this).addClass('active');
	  // 필요한 경우 우측 폼에 값 로드
	  // loadForm($(this).data('id'));
	});
	
    if ($.fn.select2) {
        $('.form-select').select2({
          placeholder: function(){ return $(this).data('placeholder') || '선택하세요'; },
          allowClear: true,
          width: 'resolve',
          minimumResultsForSearch: Infinity,
          // ↓↓↓ 스코프 고정: 생성 컨테이너/드롭다운에 식별 클래스 부여
          containerCssClass: 'ez-s2',
          selectionCssClass: 'ez-s2', 
          dropdownCssClass: 'ez-s2',
          // ↓↓↓ 드롭다운을 이 폼 카드 안에 붙여서 상위 선택자 스코프도 유지
          dropdownParent: $('.form-card')
        });
     }
});
</script>

<!-- Draw view [S] -->
  <div class="split-layout">
    <!-- 좌측: 트리 -->
    <aside class="split-left">
      <div class="tree-header">메뉴 트리</div>
      <div class="tree-scroll">
        <ul class="tree">
          <li class="open">
            <button class="tw" aria-label="toggle"></button>
            <a href="#" class="active" data-id="A">대메뉴 A</a>
            <ul>
              <li><span class="dot"></span><a href="#" data-id="A-1">하위 메뉴 A-1</a></li>
              <li><span class="dot"></span><a href="#" data-id="A-2">하위 메뉴 A-2</a></li>
              <li><span class="dot"></span><a href="#" data-id="A-3">하위 메뉴 A-3</a></li>
            </ul>
          </li>
          <li>
            <button class="tw" aria-label="toggle"></button>
            <a href="#" data-id="B">대메뉴 B</a>
            <ul>
              <li><span class="dot"></span><a href="#" data-id="B-1">하위 메뉴 B-1</a></li>
              <li><span class="dot"></span><a href="#" data-id="B-2">하위 메뉴 B-2</a></li>
            </ul>
          </li>
          <li>
            <button class="tw" aria-label="toggle"></button>
            <a href="#" data-id="C">대메뉴 C</a>
          </li>
        </ul>
      </div>
    </aside>

    <!-- 우측: 폼 -->
    <section class="split-right">
      <div class="content-scroll">
        <div class="page-header">
          <h2>샘플</h2>
          <div class="breadcrumb">
          <a href="#">샘플</a>&nbsp;&gt;&nbsp;<span>리스트폼화면</span>
          </div>
        </div>

        <form id="menuForm" class="form-card" action="/admin/menu/save.do" method="post">
          <div class="form-title">메뉴 정보</div>
          <p class="form-desc">좌측 트리에서 메뉴를 선택하면 기본 정보가 로딩됩니다.</p>

          <div class="form-grid">
            <div class="field">
              <label for="menu_id" class="required">메뉴 ID</label>
              <input type="text" id="menu_id" name="menu_id" class="form-control" required />
              <small class="hint">영문/숫자, 공백 없이 입력</small>
            </div>

            <div class="field">
              <label for="menu_nm" class="required">메뉴명</label>
              <input type="text" id="menu_nm" name="menu_nm" class="form-control" required />
            </div>

           <div class="field">
              <label for="menu_gb" class="required">구분</label>
              <select id="menu_gb" name="menu_gb" class="form-select" style="width:100%;" data-placeholder="카테고리를 선택하세요" required>
                <option value=""></option>
                <option value="NOTICE">공지</option>
                <option value="EVENT">이벤트</option>
                <option value="GUIDE">안내</option>
                <option value="CHECK">점검</option>
                <option value="UPDATE">업데이트</option>
              </select>
            </div>
            
            <div class="field">
              <label for="sort_no" class="required">정렬순서</label>
              <input type="number" id="sort_no" name="sort_no" class="form-control" min="0" required />
            </div>

            <div class="field full">
              <label for="menu_desc">설명</label>
              <textarea id="menu_desc" name="menu_desc" class="form-control" placeholder="간단한 설명을 입력하세요."></textarea>
            </div>

            <div class="field">
              <label class="required">사용 여부</label>
              <div>
                <label style="margin-right:10px;">
                  <input type="radio" name="use_yn" value="Y" checked /> 사용
                </label>
                <label>
                  <input type="radio" name="use_yn" value="N" /> 미사용
                </label>
              </div>
            </div>

            <div class="field">
              <label>노출 영역</label>
              <div>
                <label style="margin-right:10px;">
                  <input type="checkbox" name="expose_pc" value="Y" checked /> PC
                </label>
                <label>
                  <input type="checkbox" name="expose_m" value="Y" /> Mobile
                </label>
              </div>
            </div>
          </div>

          <div class="form-actions">
            <button type="button" class="btn-delete" id="btnDelete">삭제</button>
            <button type="button" class="btn-update" id="btnNew">신규</button>
            <button type="submit" class="btn-insert">저장</button>
            <button type="button" class="btn-cancel" onclick="history.back();">취소</button>
          </div>
        </form>
      </div>
    </section>
  </div>
<!-- Draw view [E] -->