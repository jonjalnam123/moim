/**
 * Name : commonGrid.js
 * Description	: 그리드 관련 함수
 * Modification Information
 */

function makeTableResizable(tableSelector, storageKey) {
  const table = document.querySelector(tableSelector);
  if (!table) return;

  table.classList.add('col-resize');

  // colgroup 없으면 생성
  let colgroup = table.querySelector('colgroup');
  const ths = table.querySelectorAll('thead th');
  if (!colgroup) {
    colgroup = document.createElement('colgroup');
    ths.forEach(() => colgroup.appendChild(document.createElement('col')));
    table.insertBefore(colgroup, table.firstChild);
  }

  const cols = colgroup.querySelectorAll('col');

  // 저장된 폭 복원
  if (storageKey) {
    try {
      const saved = JSON.parse(localStorage.getItem(storageKey) || 'null');
      if (saved && Array.isArray(saved) && saved.length === cols.length) {
        saved.forEach((w, i) => { if (w) cols[i].style.width = w + 'px'; });
      }
    } catch (e) {}
  }

  // 핸들 생성(마지막 컬럼은 보통 제외)
  ths.forEach((th, idx) => {
    if (idx === ths.length - 1) return;

    // 중복 방지
    if (th.querySelector('.col-handle')) return;

    const handle = document.createElement('span');
    handle.className = 'col-handle';
    th.appendChild(handle);

    let startX = 0;
    let startW = 0;

    const onMove = (e) => {
      const dx = e.clientX - startX;
      const newW = Math.max(60, startW + dx); // 최소폭 60px
      cols[idx].style.width = newW + 'px';
    };

    const onUp = () => {
      document.body.classList.remove('is-col-resizing');
      window.removeEventListener('mousemove', onMove);
      window.removeEventListener('mouseup', onUp);

      // 폭 저장
      if (storageKey) {
        const arr = Array.from(cols).map(c => {
          const w = parseInt(getComputedStyle(c).width, 10);
          return Number.isFinite(w) ? w : null;
        });
        localStorage.setItem(storageKey, JSON.stringify(arr));
      }
    };

    handle.addEventListener('mousedown', (e) => {
      e.preventDefault();
      document.body.classList.add('is-col-resizing');
      startX = e.clientX;

      // 현재 th 폭 기준
      startW = th.getBoundingClientRect().width;

      window.addEventListener('mousemove', onMove);
      window.addEventListener('mouseup', onUp);
    });
  });
}