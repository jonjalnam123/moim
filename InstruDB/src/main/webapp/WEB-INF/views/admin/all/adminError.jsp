<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


 <div class="content-wrapper">
    <section class="error-screen" role="alert" aria-live="assertive">
      <div class="error-card">
        <div class="error-card-header">
          <div class="error-icon" aria-hidden="true">
            <!-- 경고 아이콘 (inline svg) -->
			<img src="${pageContext.request.contextPath}/resources/static/img/error-warning.svg" alt="error">
          </div>

          <div class="error-title-wrap">
            <div class="error-badge">오류 발생</div>
            <h1 class="error-title">요청을 처리하는 중 문제가 발생했습니다.</h1>
            <p class="error-subtitle">
              잠시 후 다시 시도해 주세요. 문제가 반복되면 오류내용을 첨부해 문의해 주세요.
            </p>
          </div>
        </div>

        <div class="error-card-body">
          <div class="error-meta">
            <div class="error-meta-row">
              <div class="k">오류 코드</div>
              <div class="v"><code id="errCode">${adminErrorCd}</code></div> 
            </div>

            <div class="error-meta-row">
              <div class="k">발생 시간</div>
              <div class="v"><code id="errTime">${adminErrorDate}</code></div>
            </div>

            <div class="error-meta-row">
              <div class="k">오류 내용</div>
              <div class="v">
                <code id="reqId">${adminErrorMsg}</code>
                <button type="button" class="btn-copy" id="copyReqIdBtn">복사</button>
              </div>
            </div>
          </div>

		<div class="error-actions">
		  <button class="btn err-back" onclick="history.back()">이전 페이지</button>
		  <a class="btn err-home" href="/admin/main.do">홈으로</a>
		  <a class="btn err-contact" href="/admin/contact.do">문의하기</a>
		</div>

          <details class="error-details">
            <summary>기술 정보 보기</summary>
            <pre id="errDetail">예) NullPointerException at ...</pre>
          </details>
        </div>
      </div>
    </section>
  </div>