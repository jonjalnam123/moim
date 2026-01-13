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
      <a href="#">샘플</a>&nbsp;&gt;&nbsp;<span>메인화면</span>
    </div>
  </div>
</div>
<!-- Draw view [E] -->