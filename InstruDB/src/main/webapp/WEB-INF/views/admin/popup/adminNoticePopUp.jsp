<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Draw view [S] -->
<div class="notice-view-grid">
    <div class="notice-view-label">작성자</div>
    <div class="notice-view-value">
        <c:out value="${adminNotice.regId}" />
    </div>

    <div class="notice-view-label">등록일</div>
    <div class="notice-view-value">
        <c:out value="${adminNotice.regDt}" />
    </div>

    <div class="notice-view-label">제목</div>
    <div class="notice-view-value is-title">
        <c:out value="${adminNotice.noticeTitle}" />
    </div>

    <div class="notice-view-label is-content">내용</div>
    <div class="notice-view-value is-content">
        <c:out value="${adminNotice.noticeCn}" />
    </div>
</div>

<div class="notice-modal-section">
    <h3 class="notice-modal-section-title">첨부파일</h3>

    <div class="notice-modal-filebox">
        <div class="notice-modal-filebox-head">첨부파일 목록</div>
        <div class="notice-modal-filebox-body">
            <c:choose>
                <c:when test="${not empty noticeFileList}">
                    <ul class="attach-list">
                        <c:forEach var="file" items="${noticeFileList}">
                            <li class="attach-item">
                                <div class="attach-left">
                                    <div class="attach-name"><c:out value="${file.fileOrgNm}" /></div>
                                    <div class="attach-meta">
                                        <c:out value="${empty file.fileSize ? '' : file.fileSize}" />
                                        <c:if test="${not empty file.fileSize}"> KB</c:if>
                                    </div>
                                </div>
                                <div class="attach-actions">
                                    <button type="button" class="btn-mini download" onclick="downloadNoticeFile('<c:out value="${file.fileId}" />');">
                                        다운로드
                                    </button>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="notice-modal-empty">첨부파일이 없습니다.</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!-- Draw view [E] -->