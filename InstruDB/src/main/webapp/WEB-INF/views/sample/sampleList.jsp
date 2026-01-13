<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	$(document).ready(function() {
		// 셀렉트 박스 이벤트
		$('#param').select2({
			allowClear: true,
			width: 'resolve',
			minimumResultsForSearch: Infinity // 셀렉트박스 검색창 숨기기
		});
	});
</script>

<!-- Draw view [S] -->
<div class="content-wrapper">
  <div class="page-header">
    <h2>샘플</h2>
    <div class="breadcrumb">
      <a href="#">샘플</a>&nbsp;&gt;&nbsp;<span>리스트화면</span>
    </div>
  </div>

  <!-- 검색 조건 -->
  <div class="search-bar">
    <form class="search-form" action="" method="get" id="seacrhForm">
  		<select id="param" name="param" class="combo-scroll">  
	      <option value="">전체</option>
	      <option value="공지">공지</option>
	      <option value="이벤트">이벤트</option>
	      <option value="안내">안내</option>
	      <option value="점검">점검</option>
	      <option value="업데이트">업데이트</option>
	  	</select>
      <input type="text" class="search-input" placeholder="제목 검색">
      <button type="button" id="searchBtn" class="search-btn">검색</button>
      <button type="button" id="regBtn" class="btn-insert">등록</button>
  	  <button type="button" id="delBtn" class="btn-delete">삭제</button>
    </form>
  </div>

  <div class="table-wrapper">
    <table class="table-grid">
      <thead>
        <tr>
          <th>No</th>
          <th>제목</th>
          <th>작성자</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr><td>1</td><td>시스템 점검 안내</td><td>관리자</td><td>2025-09-30</td></tr>
        <tr><td>2</td><td>이벤트 공지</td><td>운영팀</td><td>2025-09-28</td></tr>
        <tr><td>3</td><td>업데이트 내역</td><td>개발팀</td><td>2025-09-25</td></tr>
      </tbody>
    </table>
  </div>

  <div class="pagination">
    <button>&laquo;</button>
    <button class="active">1</button>
    <button>2</button>
    <button>3</button>
    <button>&raquo;</button>
  </div>

</div>
<!-- Draw view [E] -->