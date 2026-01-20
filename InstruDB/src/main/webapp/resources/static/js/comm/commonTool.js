/**
 * Name commonTool.js
 * Description	공통 기능
 * Modification Information
 */

/*******************************
* FuntionNm : ajaxStart
* Date : 2025.10.02
* Author : CJS
* Description : 공통 AJAX 통신 함수
* PARAM : URL, PARAMS, DATATYPE, CALLBACK
********************************/
function ajaxStart(url, params, dataType, callback) {
	$.ajax({
	    url: url,
	    method: 'post',
	    data : params,
	    dataType : dataType,
	  	beforeSend: function() {
	    	showGlobalLoading();
	 	},
	    success: function (data, status, xhr) {
	        if (typeof callback === 'function' && callback !== null ) {
				console.log('data>>>>', data);
	            callback(data);
	        } else {
	            console.warn("callback이 function이 아닙니다:", callback);
	        }
	    },
	    complete: function() {
	    	 hideGlobalLoading();
	    },
	    error: function (data, status, err) {
			console.error("AJAX 에러", err);
	    }
	});
}

/*******************************
* FuntionNm : getNowUri
* Date : 2025.10.02
* Author : CJS
* Description : 현재서버 URI 확인
********************************/
function getNowUri() {
	const fullUrl = window.location.origin;
	return fullUrl;
}

/*******************************
* FuntionNm : execDaumPostcode
* Date : 2025.10.02
* Author : CJS
* Description : 카카오 주소 찾기
********************************/
function execDaumPostcode(postId, adId) {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById(postId).value = data.zonecode;
            document.getElementById(adId).value = roadAddr;
        }
    }).open();
}

/*******************************
* FuntionNm : isEmpty()
* Date : 2025.12.18
* Author : CJS
* Description : 빈 값, null 체크
********************************/
function isEmpty(obj) {
	var result = false;
	var empty = obj === null || obj === undefined || obj === ''
	if (empty) {
		result = true;
	} 
	return result;
}

/*******************************
* FuntionNm : isEmptyMsg()
* Date : 2025.10.20
* Author : CJS
* Description : 빈 값, null 체크 후 메세지
********************************/
function isEmptyMsg(obj, msg) {
	var result = false;
	var empty = obj === null || obj === undefined || obj === ''
	if (empty) {
		alert(msg);
		result = true;
	} 
	return result;
}

/*******************************
* FuntionNm : isEmptyArr()
* Date : 2025.10.20
* Author : CJS
* Description : 빈 값, null 체크 후 메세지
********************************/
function isEmptyArr(arr)  {
  if(Array.isArray(arr) && arr.length === 0)  {
    return true;
  }
  return false;
}

/*******************************
* FuntionNm : getCheckedVal
* Date : 2025.10.02
* Author : CJS
* Description : 라디오 버튼 체크 값 구하기 함수
********************************/
function getCheckedVal(name) {
	const value = $('input[name=' + name + ']:checked').val();
	return value;
};

/*******************************
* FuntionNm : goToUri
* Date : 2025.10.02
* Author : CJS
* Description : uri ( 호출경로 )
********************************/
function goToUri(uri) {
	window.location.href = uri;
};

/*******************************
* FuntionNm : callConfirm
* Date : 2025.10.02
* Author : CJS
* Description : 컨펌 메세지
********************************/
function callConfirm(conMsg) {
	var result = '';
	if(confirm(conMsg)){
		result = 'Y';
	}else{
		result = 'N';
	}
	return result;
};

/*******************************
* FuntionNm : checkNum(obj)
* Date : 2025.10.20
* Author : CJS
* Description : 숫자만 허용
********************************/
function checkNum(obj) {
    var regExp = /[^0-9]/g;
    if (regExp.test(obj.value)) {
        obj.value = obj.value.replace(regExp, '');
    }
}

/*******************************
* FuntionNm : checkNumComma(obj)
* Date : 2025.10.20
* Author : CJS
* Description : 숫자랑, 콤마 허용
********************************/
function checkNumComma(obj) {
    var regExp = /[^0-9,]/g;
    if (regExp.test(obj.value)) {
        obj.value = obj.value.replace(regExp, '');
    }
}

/*******************************
* FuntionNm : checkNumOnlyTwo(obj)
* Date : 2025.10.20
* Author : CJS
* Description : 숫자만 2자리 구하는 함수
********************************/
function checkNumOnlyTwo(obj) {
    var regExp = /[^0-9]/g;
    if (regExp.test(obj.value)) {
        obj.value = obj.value.replace(regExp, '');
    }
    if (obj.value.length > 2) {
        obj.value = obj.value.substring(0, 2);
    }
}

/*******************************
* FuntionNm : checkNumForTime(obj, flag)
* Date : 2025.10.20
* Author : CJS
* Description : 시, 분 숫자만 입력 및 형식 생성
********************************/
function checkNumForTime(obj, flag) {
    obj.value = obj.value.replace(/[^0-9]/g, '');

    if (obj.value.length > 2) {
        obj.value = obj.value.slice(0, 2);
    }

    if (obj.value === '') return;

    if (flag === 'h') {
        if (Number(obj.value) >= 24) {
            obj.value = '23';
            return;
        }
        if (obj.value.length === 2 && obj.value === '00') {
            obj.value = '01';
            return;
        }
    }

    if (flag === 'm') {
        if (Number(obj.value) >= 60) {
            obj.value = '59';
            return;
        }
    }
}

/*******************************
* FuntionNm : padZero(obj)
* Date : 2025.10.17
* Author : CJS
* Description : 한자리 숫자 앞에 0 구하는 함수
********************************/
function padZero(obj) {
	var regExp = /[^0-9]/g;
	if (regExp.test(obj.value)) {
	    obj.value = obj.value.replace(regExp, '');
	}
    if (obj.value.length === 1) {
        obj.value = '0' + obj.value;
    }
}

/*******************************
* FuntionNm : showGlobalLoading
* Date : 2025.10.02
* Author : CJS
* Description : 로딩관련
********************************/
function showGlobalLoading() {
    $('#globalLoading').addClass('is-active');
}

function hideGlobalLoading() {
    $('#globalLoading').removeClass('is-active');
}