<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>MOIM</title>

<!-- jQuery 3.6.0 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap 5 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

<!-- kakao 주소 API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- JS 추가  -->
<script src="${pageContext.request.contextPath}/resources/static/js/comm/commonMsg.js"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/comm/commonTool.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<!-- CSS 추가 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/commonAdm.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

<!--  폰트 및 이미지 추가 -->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@600&family=Noto+Sans+KR:wght@400;500;700&display=swap" rel="stylesheet">

</head>
<body class="sb-nav-fixed">
	  <div id="layoutSidenav_content">
	    <main>
	      <tiles:insertAttribute name="body"/>
	    </main>
	  </div>
</body>
</html>