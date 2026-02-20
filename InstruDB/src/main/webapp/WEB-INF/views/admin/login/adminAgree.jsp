<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Script Part -->
<script src="${pageContext.request.contextPath}/resources/static/js/admin/login/adminAgree.js"></script>

<!-- Draw view [S] -->
<div class="content-wrapper terms-page">
  <section class="terms-screen" aria-label="약관 동의">
    <form class="form-card terms-card" action="/admin/join.do" method="post">
    
      <h3 class="form-title">약관 동의</h3>
      <p class="form-desc">
        서비스 이용을 위해 아래 약관에 동의해 주세요. 필수 항목 미동의 시 가입이 진행되지 않습니다.
      </p>

      <div class="terms-all">
        <label class="terms-check">
          <input type="checkbox" id="agreeAll" />
          <span class="terms-check-text"><b>전체 동의</b> (선택 포함)</span>
        </label>
        <small class="hint">필수 약관 2개 + 선택 약관 2개를 한 번에 설정합니다.</small>
      </div>

      <div class="terms-list">

        <!-- [필수] 서비스 이용약관 -->
        <div class="terms-item" data-required="Y">
          <div class="terms-item-head">
            <label class="terms-check">
              <input type="checkbox" class="agree-item req" name="agreeService" value="Y" />
              <span class="terms-check-text">[필수] 서비스 이용약관 동의</span>
            </label>
            <button type="button" class="btn btn-refresh btn-sm terms-toggle">내용보기</button>
          </div>

          <div class="terms-item-body">
            <div class="terms-box">
              <h4>제1조(목적)</h4>
              <p>본 약관은 회사(이하 “회사”)가 제공하는 관리/업무지원 서비스(이하 “서비스”)의 이용과 관련하여 회사와 이용자 간 권리·의무 및 책임사항을 규정함을 목적으로 합니다.</p>

              <h4>제2조(정의)</h4>
              <ol>
                <li>“이용자”란 회사가 정한 절차에 따라 계정을 발급받아 서비스를 이용하는 자를 말합니다.</li>
                <li>“계정”이란 이용자의 식별과 서비스 이용을 위해 부여되는 ID 및 인증수단을 말합니다.</li>
              </ol>

              <h4>제3조(계정 및 보안)</h4>
              <ol>
                <li>이용자는 계정 정보를 본인 책임 하에 관리해야 하며, 제3자에게 양도/대여할 수 없습니다.</li>
                <li>회사는 합리적인 사유가 있는 경우 보안 강화를 위해 비밀번호 변경을 요구할 수 있습니다.</li>
              </ol>

              <h4>제4조(서비스 이용)</h4>
              <ol>
                <li>서비스는 회사의 업무정책/운영시간/점검 등에 따라 제공 범위가 변경될 수 있습니다.</li>
                <li>회사는 서비스 안정성 확보를 위해 점검, 장애 조치, 고도화 작업을 수행할 수 있습니다.</li>
              </ol>

              <h4>제5조(이용 제한)</h4>
              <ol>
                <li>이용자가 관련 법령 또는 본 약관을 위반하는 경우 회사는 경고, 이용정지, 계정해지 등의 조치를 할 수 있습니다.</li>
                <li>비정상 트래픽 유발, 권한 오남용, 무단 접근 시도 등 보안 위협 행위는 즉시 차단될 수 있습니다.</li>
              </ol>

              <h4>제6조(책임 제한)</h4>
              <ol>
                <li>회사는 천재지변, 통신장애, 불가항력 사유로 인한 서비스 제공 중단에 대해 책임을 부담하지 않습니다.</li>
                <li>이용자의 귀책사유로 발생한 손해에 대해 회사는 책임을 지지 않습니다.</li>
              </ol>

              <h4>제7조(약관의 변경)</h4>
              <p>회사는 관련 법령을 위반하지 않는 범위에서 약관을 변경할 수 있으며, 변경 시 시행일 및 주요 변경사항을 서비스 내 공지합니다.</p>

              <h4>제8조(문의)</h4>
              <p>약관 및 서비스 이용 관련 문의는 “문의하기” 또는 운영 담당자에게 접수할 수 있습니다.</p>

              <p class="terms-footer-note">시행일: 2026-02-20</p>
            </div>
          </div>
        </div>

        <!-- [필수] 개인정보 수집/이용 동의 -->
        <div class="terms-item" data-required="Y">
          <div class="terms-item-head">
            <label class="terms-check">
              <input type="checkbox" class="agree-item req" name="agreePrivacy" value="Y" />
              <span class="terms-check-text">[필수] 개인정보 수집·이용 동의</span>
            </label>
            <button type="button" class="btn btn-refresh btn-sm terms-toggle">내용보기</button>
          </div>

          <div class="terms-item-body">
            <div class="terms-box">
              <p>회사는 회원가입 및 서비스 제공을 위해 아래와 같이 개인정보를 수집·이용합니다.</p>

              <div class="terms-table">
                <div class="terms-row">
                  <div class="k">수집 항목</div>
                  <div class="v">아이디, 이름, 이메일, 연락처, 부서/권한, 주소(선택 또는 업무상 필요 시)</div>
                </div>
                <div class="terms-row">
                  <div class="k">이용 목적</div>
                  <div class="v">회원 식별 및 계정 관리, 공지/알림 발송, 문의/민원 처리, 시스템 보안 및 부정이용 방지</div>
                </div>
                <div class="terms-row">
                  <div class="k">보유 기간</div>
                  <div class="v">원칙적으로 회원 탈퇴 또는 계정 삭제 시까지. 단, 관련 법령에 따라 보존할 필요가 있는 경우 해당 기간 보관</div>
                </div>
                <div class="terms-row">
                  <div class="k">동의 거부</div>
                  <div class="v">필수 항목 동의를 거부할 수 있으나, 이 경우 회원가입 및 서비스 이용이 제한됩니다.</div>
                </div>
              </div>

              <h4>이용자 권리</h4>
              <ol>
                <li>개인정보 열람/정정/삭제/처리정지 요청이 가능합니다.</li>
                <li>문의 채널을 통해 요청 시 본인확인 후 조치합니다.</li>
              </ol>

              <p class="terms-footer-note">고지일: 2026-02-20</p>
            </div>
          </div>
        </div>

        <!-- [선택] 마케팅/공지 수신 동의 -->
        <div class="terms-item" data-required="N">
          <div class="terms-item-head">
            <label class="terms-check">
              <input type="checkbox" class="agree-item opt" name="agreeMarketing" value="Y" />
              <span class="terms-check-text">[선택] 마케팅/이벤트 정보 수신 동의</span>
            </label>
            <button type="button" class="btn btn-refresh btn-sm terms-toggle">내용보기</button>
          </div>

          <div class="terms-item-body">
            <div class="terms-box">
              <p>회사는 서비스 개선 소식, 이벤트/프로모션 안내를 이메일 등으로 전송할 수 있습니다.</p>
              <ul>
                <li>수신 채널: 이메일, 서비스 내 알림</li>
                <li>철회 방법: 마이페이지 또는 운영 담당자 요청</li>
              </ul>
              <p class="terms-footer-note">선택 동의는 가입 필수 요건이 아닙니다.</p>
            </div>
          </div>
        </div>

        <!-- [선택] 제3자 제공/위탁 안내(예시) -->
        <div class="terms-item" data-required="N">
          <div class="terms-item-head">
            <label class="terms-check">
              <input type="checkbox" class="agree-item opt" name="agreeConsign" value="Y" />
              <span class="terms-check-text">[선택] 개인정보 처리위탁 안내 확인</span>
            </label>
            <button type="button" class="btn btn-refresh btn-sm terms-toggle">내용보기</button>
          </div>

          <div class="terms-item-body">
            <div class="terms-box">
              <p>서비스 운영을 위해 일부 업무를 외부 전문업체에 위탁할 수 있습니다(예: 이메일 발송, 인프라 운영).</p>
              <ul>
                <li>위탁 범위/수탁사 변경 시 서비스 내 공지합니다.</li>
                <li>회사는 수탁사가 개인정보보호 의무를 준수하도록 관리·감독합니다.</li>
              </ul>
            </div>
          </div>
        </div>

      </div>

      <div class="form-actions">
        <button type="button" class="btn-cancel" onclick="history.back();">이전</button>
        <button type="submit" class="btn-insert" id="nextBtn" disabled>동의하고 계속</button>
      </div>

    </form>
  </section>
</div>
<!-- Draw view [E] -->