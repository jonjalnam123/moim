/**
 * Name commonTool.js
 * Description	공통 기능
 * Modification Information
 */

/*******************************
* AJAX 관련 [S]
********************************/

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
				console.log('조회한 결과 데이터 : ', data);
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
* FuntionNm : ajaxNoLoadingxStart
* Date : 2025.10.02
* Author : CJS
* Description : 공통 AJAX 통신 함수
* PARAM : URL, PARAMS, DATATYPE, CALLBACK
********************************/
function ajaxNoLoadingxStart(url, params, dataType, callback) {
	$.ajax({
	    url: url,
	    method: 'post',
	    data : params,
	    dataType : dataType,
	    success: function (data, status, xhr) {
	        if (typeof callback === 'function' && callback !== null ) {
				console.log('조회한 결과 데이터 : ', data);
	            callback(data);
	        } else {
	            console.warn("callback이 function이 아닙니다:", callback);
	        }
	    },
	    error: function (data, status, err) {
			console.error("AJAX 에러", err);
	    }
	});
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

/*******************************
* AJAX 관련 [E]
********************************/



/*******************************
* 기능 관련 [S]
********************************/

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
* 기능 관련 [E]
********************************/



/*******************************
* 유효성 검사 관련 [S]
********************************/

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
* FuntionNm : checkNumPhone(obj)
* Date : 2025.10.20
* Author : CJS
* Description : 숫자만 11자리 구하는 함수 ( 핸드폰 )
********************************/
function checkNumPhone(obj) {
    var regExp = /[^0-9]/g;
    if (regExp.test(obj.value)) {
        obj.value = obj.value.replace(regExp, '');
    }
    if (obj.value.length > 11) {
        obj.value = obj.value.substring(0, 11);
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
* FuntionNm : getEngUpperCase
* Date : 2025.10.02
* Author : CJS
* Description : 영어 대문자 전환
********************************/
function getEngUpperCase(val) {
	var valReplToEng = val.replace(/[^a-zA-Z]/g, ''); 
	var valReplToEngUpper = valReplToEng.toUpperCase();
	return valReplToEngUpper;
}

/*******************************
* FuntionNm : onlyKorEng
* Date : 2025.10.02
* Author : CJS
* Description : 한국어, 영어만 입력 가능
********************************/
function onlyKorEng(val) {
    return val.replace(/[^가-힣a-zA-Z]/g, '');
}

/*******************************
* FuntionNm : onlyKor
* Date : 2026.02.14
* Author : CJS
* Description : 한글만 입력 가능
********************************/
function onlyKor(val) {
    return val.replace(/[^가-힣]/g, '');
}

/*******************************
* FuntionNm : onlyEng
* Date : 2026.02.14
* Author : CJS
* Description : 영어만 입력 가능
********************************/
function onlyEng(val) {
    return val.replace(/[^a-zA-Z]/g, '');
}

/*******************************
* FuntionNm : onlyEngLower
* Date : 2026.02.14
* Author : CJS
* Description : 영어 소문자만 입력 가능
********************************/
function onlyEngLower(val) {
    return val.replace(/[^a-z]/g, '');
}

/*******************************
* FuntionNm : onlyEngNum
* Date : 2026.02.14
* Author : CJS
* Description : 영어 + 숫자만 입력 가능
********************************/
function onlyEngNum(val) {
    return val.replace(/[^a-zA-Z0-9]/g, '');
}

/*******************************
* FuntionNm : onlyKorNum
* Date : 2026.02.14
* Author : CJS
* Description : 한글 + 숫자만 입력 가능
********************************/
function onlyKorNum(val) {
    return val.replace(/[^가-힣0-9]/g, '');
}

/*******************************
* FuntionNm : onlyKorEngSpace
* Date : 2026.02.14
* Author : CJS
* Description : 한글, 영어, 공백 허용
********************************/
function onlyKorEngSpace(val) {
    return val.replace(/[^가-힣a-zA-Z\s]/g, '');
}

/*******************************
* FuntionNm : onlyIdRule
* Date : 2026.02.14
* Author : CJS
* Description : 아이디용 문자만 허용
********************************/
function onlyIdRule(val) {
    return val.replace(/[^a-zA-Z0-9._-]/g, '');
}

/*******************************
* FuntionNm : onlyEmailChar
* Date : 2026.02.14
* Author : CJS
* Description : 이메일 문자만 허용
********************************/
function onlyEmailChar(val) {
    return val.replace(/[^a-zA-Z0-9@._-]/g, '');
}

/*******************************
* FuntionNm : removeSpecialChar
* Date : 2026.02.14
* Author : CJS
* Description : 특수문자 제거
********************************/
function removeSpecialChar(val) {
    return val.replace(/[^가-힣a-zA-Z0-9\s]/g, '');
}

/*******************************
* FuntionNm : trimValue
* Date : 2026.02.14
* Author : CJS
* Description : 앞뒤 공백 제거
********************************/
function trimValue(val) {
    return val.replace(/^\s+|\s+$/g, '');
}

/*******************************
* FuntionNm : removeMultiSpace
* Date : 2026.02.14
* Author : CJS
* Description : 연속 공백 제거
********************************/
function removeMultiSpace(val) {
    return val.replace(/\s{2,}/g, ' ');
}

/*******************************
* FuntionNm : validatePassword
* Date : 2026.02.14
* Author : CJS
* Description : 특수문자, 영어, 숫자만 입력 비밀번호 체크 
********************************/
function validatePassword(pw) {
    var regex = /^(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+$/;
    return regex.test(pw);
}

/*******************************
* FuntionNm : removeSpace
* Date : 2026.02.14
* Author : CJS
* Description : 공백제거
********************************/
function removeSpace(val) {
    return val.replace(/\s/g, '');
}
/*******************************
* 유효성 검사 관련 [E]
********************************/