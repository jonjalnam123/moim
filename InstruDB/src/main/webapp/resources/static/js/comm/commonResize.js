/**
 * Name commonResize.js
 * Description	공통 리사이즈 기능
 * Modification Information
 */

/*******************************
   Split Resizer (jQuery 버전)
   - 필요 요소
     1) .split-layout
     2) .split-left
     3) #splitResizer  (가운데 리사이저 div)
   - 공통 스크립트에서 호출:
     $(function(){ initSplitResizeJQ(); });
********************************/
function initSplitResizeJQ(options) {
	
  var opt = $.extend({
    splitSelector: '.split-layout',
    leftSelector: '.split-left',
    resizerSelector: '#splitResizer',
    storageKey: 'split-left-width:' + location.pathname,

    // ✅ 여기 기본값을 박아둠
    minLeft: 800,        // 왼쪽 최소폭 800px
    minRight: 520,       // 오른쪽 최소폭
    dragThreshold: 3,
    baseLeftWidth: 1100  // 더블클릭 리셋 폭
  }, options || {});

  var $split = $(opt.splitSelector).first();
  if ($split.length === 0) return;

  var $left = $split.find(opt.leftSelector).first();
  var $resizer = $(opt.resizerSelector);
  if ($left.length === 0 || $resizer.length === 0) return;

  $resizer.off('.splitResize');
  $(document).off('.splitResize');

  try {
    var saved = localStorage.getItem(opt.storageKey);
    if (saved && !isNaN(parseFloat(saved))) {
      $split[0].style.setProperty('--left-w', Math.round(parseFloat(saved)) + 'px');
    }
  } catch (e) {}

  var startX = 0, startW = 0, rafId = 0;
  var resizing = false, moved = false, lastW = null;

  function getGap() {
    var gap = 14;
    try {
      var cssGap = getComputedStyle($split[0]).getPropertyValue('--gap').trim();
      if (cssGap) gap = parseInt(cssGap, 10) || gap;
    } catch (e) {}
    return gap;
  }

  function clampWidth(newW) {
    var totalW = $split[0].getBoundingClientRect().width;
    var gap = getGap();

    var maxLeft = totalW - opt.minRight - gap;
    if (maxLeft < opt.minLeft) maxLeft = opt.minLeft;

    var w = Math.round(newW); // ✅ 소수 제거
    if (w < opt.minLeft) return opt.minLeft; // ✅ 800 아래로 내려가지 않음
    if (w > maxLeft) return Math.round(maxLeft);
    return w;
  }

  function refreshGridLayout() {
    $(window).trigger('resize');
  }

  function applyWidth(w) {
    w = Math.round(w);
    if (rafId) cancelAnimationFrame(rafId);
    rafId = requestAnimationFrame(function () {
      $split[0].style.setProperty('--left-w', w + 'px');
      refreshGridLayout();
    });
  }

  function stopResize() {
    if (!resizing) return;
    resizing = false;
    $('body').removeClass('is-resizing');
    refreshGridLayout();

    if (moved && lastW != null) {
      try { localStorage.setItem(opt.storageKey, String(lastW)); } catch (e) {}
    }
  }

  // ✅ 더블클릭 리셋
  $resizer.on('dblclick.splitResize', function (e) {
    e.preventDefault();
    e.stopPropagation();

    resizing = false;
    moved = false;
    lastW = null;
    $('body').removeClass('is-resizing');

    var base = clampWidth(opt.baseLeftWidth);
    $split[0].style.setProperty('--left-w', base + 'px');
    try { localStorage.setItem(opt.storageKey, String(base)); } catch (err) {}

    refreshGridLayout();
    return false;
  });

  $resizer.on('pointerdown.splitResize', function (e) {
    e.preventDefault();
    resizing = true;
    moved = false;
    lastW = null;

    $('body').addClass('is-resizing');
    startX = e.clientX;
    startW = $left[0].getBoundingClientRect().width;

    if (this.setPointerCapture && e.pointerId != null) {
      try { this.setPointerCapture(e.pointerId); } catch (err) {}
    }
  });

  $resizer.on('pointermove.splitResize', function (e) {
    if (!resizing) return;

    var dx = e.clientX - startX;
    if (!moved && Math.abs(dx) < opt.dragThreshold) return;

    moved = true;
    var nextW = clampWidth(startW + dx);
    lastW = nextW;
    applyWidth(nextW);
  });

  $resizer.on('pointerup.splitResize pointercancel.splitResize', function () {
    stopResize();
  });

  $(document).on('pointerup.splitResize pointercancel.splitResize', function () {
    stopResize();
  });
  
}