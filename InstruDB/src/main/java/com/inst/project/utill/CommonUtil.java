package com.inst.project.utill;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inst.project.admin.vo.AdminDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	
	/**
	* @methodName	 	: setAdminInfoSession
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 정보 세션 저장
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	public static boolean setAdminInfoSession( AdminDTO adminInfo, HttpServletRequest req ) {
		log.info(" [ CommonUtil ] : setAdminInfoSession ");
		
		if ( adminInfo == null ) {
			return false;
		}

	    HttpSession oldSession = req.getSession(false);
	    if (oldSession != null) {
	        oldSession.invalidate();
	    }

		HttpSession session = req.getSession();
	    
	    String adminId = adminInfo.getAdminId();
	    String adminNm = adminInfo.getAdminNm();
	    String adminIp = adminInfo.getAdminIp();
	    String adminDeptCd = adminInfo.getAdminDeptCd();
	    String adminTeamCd = adminInfo.getAdminTeamCd();
	    String adminPositionCd = adminInfo.getAdminPositionCd();
	    String adminGender = adminInfo.getAdminGender();
	    String adminRegGb = adminInfo.getAdminRegGb();
	    String adminGradeCd = adminInfo.getAdminGradeCd();
	    String adminRegAccept = adminInfo.getAdminRegAccept();
	    
	    session.setAttribute("adminId", adminId);
	    session.setAttribute("adminNm", adminNm);
	    session.setAttribute("adminIp", adminIp);
	    session.setAttribute("adminDeptCd", adminDeptCd);
	    session.setAttribute("adminTeamCd", adminTeamCd);
	    session.setAttribute("adminPositionCd", adminPositionCd);
	    session.setAttribute("adminGender", adminGender);
	    session.setAttribute("adminRegGb", adminRegGb);
	    session.setAttribute("adminGradeCd", adminGradeCd);
	    session.setAttribute("adminRegAccept", adminRegAccept);
	    
		return true;
	}
	
	/**
	* @methodName	 	: getAdminInfoSession
	* @author					: 최정석
	* @date            		: 2026. 1. 6.
	* @description			: 관리자 세션 조회
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.        		최정석       			최초 생성
	*/
	public static String getAdminInfoSession(String value, HttpServletRequest req) {
		log.info(" [ ComminUtil ] : getAdminInfoSession ");
		
		HttpSession session = req.getSession(false);
	    if (session == null) {
	        return null;
	    }

	    Object result = session.getAttribute(value);
	    
	    return result.toString();
	}
	
	
	/**
	* @methodName	 	: getClientIp
	* @author				: 최정석
	* @date            		: 2026. 1. 7.
	* @description			: IP 구하기 메소드
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 7.        		최정석       			최초 생성
	*/
	public static String getClientIp(HttpServletRequest req) {
		log.info(" [ ComminUtil ] : getClientIp ");
		
	    String xff = req.getHeader("X-Forwarded-For");

	    if (xff != null && !xff.trim().isEmpty() && !"unknown".equalsIgnoreCase(xff)) {
	        String first = xff.split(",")[0].trim();
	        if (!first.isEmpty()) return first;
	    }

	    String ip = req.getHeader("X-Real-IP");
	    if (ip != null && !ip.trim().isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
	        return ip.trim();
	    }

	    return req.getRemoteAddr();
	}
	
	/**
	* @methodName	 	: loginIdChk
	* @author				: 최정석
	* @date            		: 2026. 1. 7.
	* @description			: 로그인 체크 메소드
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 7.        		최정석       			최초 생성
	*/
	public static boolean loginIdChk(String orgId, String selectId) {
		log.info(" [ ComminUtil ] : loginIdChk ");
		boolean result = true;
		if ( orgId.isEmpty() || selectId.isEmpty() ) { result = false; }
		result = orgId.equals(selectId) ? true : false;
		return result;
	}
	
	/**
	* @methodName	 	: removeLastComma
	* @author				: 최정석
	* @date            		: 2026. 1. 7.
	* @description			: 마지막 콤마 제거
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 7.        		최정석       			최초 생성
	*/
	public static String removeLastComma(String value) {
		log.info(" [ ComminUtil ] : removeLastComma ");
		String result = "";
		if( value.isEmpty() || value == null ) {
			return result;
		}
		result = value.replaceAll(",$", "");
		return result;
	}
	
	
	/**
	* @methodName	 	: isBlank
	* @author					: 최정석
	* @date            		: 2026. 1. 7.
	* @description			: 빈 값 체크
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 7.        		최정석       			최초 생성
	*/
	public static boolean isBlank(String s) {
	    return s == null || s.trim().isEmpty();
	}
	
	public static String toMd5(String s) {
		byte[] temp = s.getBytes();
		String result = null;

		try{
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
			md5.update(temp);
			result = toHex(md5.digest());
		} catch(Exception e) {
			return null;
		}
		return result;
	}

	public static String toSha256(String s) {
		byte[] temp = s.getBytes();
		String result = null;

		try{
			java.security.MessageDigest sha256 = java.security.MessageDigest.getInstance("SHA-256");
			sha256.update(temp);
			result = toHex(sha256.digest());

		} catch(Exception e) {
			return null;
		}
		return result;
	}

	public static String toCrypEnc(String s) {
		String result = null;

		try{
			CryptoUtill crypto = new CryptoUtill();
			result = crypto.encrypt(s);
		} catch(Exception e) {
			return null;
		}
		return result;
	}

	public static String toCrypDec(String s) {
		String result = null;

		try{
			CryptoUtill crypto = new CryptoUtill();
			result = crypto.decrypt(s);
		} catch(Exception e) {
			return null;
		}
		return result;
	}

	public static String getCertifyNum() {
		String result = "";

		Random r = new Random();
		for(int i=0;i<6;i++){
			result+=r.nextInt(9);
		}
		return result;
	}

	
	
//	str 원문(String), UrlEnc URL encoding 여부(boolean)
	public static String toCrypEncRan(String str, boolean UrlEnc) throws UnsupportedEncodingException {
		JSONObject jsonObj	= new JSONObject();
		jsonObj.put("random", getCertifyNum());
		jsonObj.put("str", str);
		String result = toCrypEnc(jsonObj.toString());
		if (UrlEnc) {
			result = result.replaceAll("/", "／／");
			result = isNull(URLEncoder.encode(result , "UTF-8"));
		}
		return result;
	}

	
//	str 암호문(String), UrlEnc URL encoding 여부(boolean)
	public static String toCrypDecRan(String str, boolean UrlEnc) throws UnsupportedEncodingException {
		if(UrlEnc) {
			str = isNull(URLDecoder.decode(str , "UTF-8"));
			str = str.replaceAll("／／","/");
			str = str.replaceAll(" ","+");			
		}
		String encString = CommonUtil.toCrypDec(str);
		JSONObject jsonObj	= new JSONObject(encString);
		String result = isNull(jsonObj.get("str"));
		return result;
	}
	
	public static String toHtmlEnc(String s){
		if(s == null){
			return "";
		}else{
			return s.replaceAll("\"", "&quot;").replaceAll("'", "&#39;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		} // end of if
	}

	public static String toHtmlDec(String s){
		if(s == null){
			return "";
		}else{
			return s.replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&lt;", "<").replaceAll("&gt;",">");
		} // end of if
	}


	public static String lpad(String s1, int n, String s2){
		String result = "";

		for(int i=0; i<(n-s1.length());i++){
			result += s2;
		}
		result = result+s1;
		return result;
	}

	public static String getOnlyNum(String s){
		return (isNull(s).replaceAll("[^0-9]", ""));
	}

	public static String getFileKb(String str){
		String kb = "";
		if( str != null && str != "" ){
			int idx = str.lastIndexOf(".");

			if( idx != -1 ){
				kb = str.substring(idx+1,str.length());
			}
		}
		return kb;
	}

	public static String nullToSpace(String s){
		return (isNull(s).replaceAll("null", ""));
	}

	public static Object callMethod(Object obj, String methodName) {
		Class c = obj.getClass();
		try {
			Method m = c.getMethod(methodName, new Class[] {});
			return m.invoke(obj, new Object[] {});
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateFormat(int type, String date){

		String sDate = isNull(date);
		try{
			String sTmp = sDate;
			if(type==1){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8);
				}
			}else if(type==2){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8);
				}
			}else if(type==3){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(0,4)+"�뀈 "+ Integer.parseInt(sTmp.substring(4,6))+"�썡 "+ Integer.parseInt(sTmp.substring(6,8))+"�씪 "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(0,4)+"�뀈 "+ Integer.parseInt(sTmp.substring(4,6))+"�썡 "+ Integer.parseInt(sTmp.substring(6,8))+"�씪 "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(0,4)+"�뀈 "+ Integer.parseInt(sTmp.substring(4,6))+"�썡 "+ Integer.parseInt(sTmp.substring(6,8))+"�씪 ";
				}
			}else if(type==4){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(0,4)+"-"+ sTmp.substring(4,6)+"-"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(0,4)+"-"+ sTmp.substring(4,6)+"-"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(0,4)+"-"+ sTmp.substring(4,6)+"-"+ sTmp.substring(6,8)+" ";
				}
			}
		}catch(Exception e){
			return "";
		}
		return sDate;
	}

	public static String getDateFormat(int type, String date, String s){

		String sDate = isNull(date);
		if("".equals(sDate)) return s;
		try{
			String sTmp = sDate;
			if(type==1){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(0,4)+"/"+ sTmp.substring(4,6)+"/"+ sTmp.substring(6,8);
				}
			}else if(type==2){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8)+" "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(4,6)+"/"+ sTmp.substring(6,8);
				}
			}else if(type==3){
				if( sTmp.length()==14 ){
					sDate = sTmp.substring(0,4)+"년 "+ Integer.parseInt(sTmp.substring(4,6))+"월 "+ Integer.parseInt(sTmp.substring(6,8))+"일 "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12)+":"+ sTmp.substring(12,14);
				}else if( sTmp.length()==12 ){
					sDate = sTmp.substring(0,4)+"년 "+ Integer.parseInt(sTmp.substring(4,6))+"월 "+ Integer.parseInt(sTmp.substring(6,8))+"일 "+ sTmp.substring(8,10)+":"+sTmp.substring(10,12);
				}else if( sTmp.length()==8 ){
					sDate = sTmp.substring(0,4)+"년 "+ Integer.parseInt(sTmp.substring(4,6))+"월 "+ Integer.parseInt(sTmp.substring(6,8))+"일 ";
				}
			}
		}catch(Exception e){
			return "";
		}
		return sDate;
	}
	public static String getDateFormat(String date, String s){
		String sDate = isNull(date);
		if("".equals(sDate)) return sDate;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy"+s+"MM"+s+"dd");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			sDate = formatter1.format(formatter.parse(sDate));
		}catch(Exception e){
			return "";
		}
		return sDate;
	}


	/**
	 * �븳湲��씤肄붾뵫
	 * @param String
	 * @return
	 */
	public static String encode(String str){
		try {
			return new String(str.getBytes("8859_1"), "UTF-8");
		} catch (Exception e){return null;}

	}

	/*
	 * �쁺臾몄쓣 �븳湲�濡� 蹂��솚
	 */
	public static String E2K( String english ){
		String korean = null;

		if (english == null ) return "";

		try {
			korean = new String(new String(english.getBytes("8859_1"), "KSC5601"));
		}
		catch( Exception e ){
			korean = new String(english);
		}
		return korean.trim();
	}

	public static String K2E( String korean ){
		String english = null;

		if (korean == null ) return "";

		try {
			english = new String(new String(korean.getBytes("KSC5601"), "8859_1"));
		}
		catch( Exception e ){
			english = new String(korean);
		}
		return english.trim();
	}

	/*
	 * �쁺臾몄쓣 �븳湲�濡� 蹂��솚
	 */
	public static String E2K( String english, String s ){
		String korean = null;

		if("".equals(isNull(english))) return s;

		try {
			korean = new String(new String(english.getBytes("8859_1"), "KSC5601"));
		}
		catch( Exception e ){
			korean = new String(english);
		}
		return korean.trim();
	}

	public static String K2E( String korean, String s ){
		String english = null;

		if("".equals(isNull(korean))) return s;

		try {
			english = new String(new String(korean.getBytes("KSC5601"), "8859_1"));
		}
		catch( Exception e ){
			english = new String(korean);
		}
		return english.trim();
	}


	public static String toKorHex(String s){

		try {
			if("".equals(isNull(s))){
				return "";
			}else{
				byte[] b = s.getBytes("KSC5601");
				StringBuffer sb = new StringBuffer();
				for (byte element : b) {
					int hexVal = element&0xFF;
					if ( hexVal < 0x10 ){
						sb.append("0"+Integer.toHexString(hexVal));
					}else{
						sb.append(Integer.toHexString(hexVal));
					}
				}
				return sb.toString();
			}
		}
		catch( Exception e ){
			return "";
		}

	}

	public static String toHexKor(String s){

		try {
			if("".equals(isNull(s))){
				return "";
			}else{
				byte[] b = new byte[s.length() / 2];
				for (int i = 0; i < b.length; i++) {
					b[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
				}
				return new String( b, "KSC5601");
			}
		}
		catch( Exception e ){
			return "";
		}

	}

	public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }

	public static String toHex(byte[] b){

		try {
			StringBuffer buf = new StringBuffer();

			for (byte element : b) {
				int hexVal = element&0xFF;
				if ( hexVal < 0x10 ){
					buf.append("0"+Integer.toHexString(hexVal));
				}else{
					buf.append(Integer.toHexString(hexVal));
				}
			}

	    	return buf.toString();
		}
		catch( Exception e ){
			return "";
		}

	}

	public static List<String> toArrayList(String[] a){
		return new ArrayList(Arrays.asList(a));
	}

	public static String[] toListArray(List<String> l){
		String[] a = new String[l.size()];
		return l.toArray(a);
	}

	 /**
	 * 湲덉븸comma
	 * @param String
	 * @return
	 */
	public static String comma(String s)
    {
		if("".equals(isNull(s))) return s;
        try
        {
        	String tmp="";
        	String tmp2="";
        	if(s.indexOf(".",0) > -1 ){
        		tmp = s.substring(0,s.indexOf(".",0));
        		tmp2 =  s.substring(s.indexOf(".",0));
        	} else {
        		tmp = s;
        	}

        	long i = Long.parseLong(tmp);
            DecimalFormat decimalformat = new DecimalFormat("###,###,###,###");
            return decimalformat.format(i) +  tmp2;
        }
        catch(Exception exception)
        {
            return s;
        }
    }
	 /**
	 * 湲덉븸comma
	 * @param long
	 * @return
	 */
	 public static String comma(long i )
	    {
	        try
	        {
	            DecimalFormat decimalformat = new DecimalFormat("###,###,###,###");
	            return decimalformat.format(i);
	        }
	        catch(Exception exception)
	        {
	            return "" + i;
	        }
	    }


	 /**
		 * 臾몄옄�뿴 移섑솚
		 *
		 * @param str source String
		 * @param oldStr 諛붽� 臾몄옄�뿴
		 * @param newStr 諛붾�� 臾몄옄�뿴
		 * @return String 蹂�寃쎈맂 臾몄옄�뿴
		 */
		public static String replace(String str, String oldStr, String newStr){
			StringBuffer sb = new StringBuffer(str);
			int offset = sb.toString().indexOf(oldStr);
			int oldlength = oldStr.length();
			int newlength = newStr.length();

			while(offset >= 0){
			    sb.replace(offset,offset + oldlength, newStr);
			    offset = sb.toString().indexOf(oldStr, offset + newlength);
			}

			return sb.toString();
		}

		/**
		 * 臾몄옄�뿴�씠 Null�씠硫� ""�쓣 由ы꽩�븳�떎.
		 *
		 * @param value 諛붽� 臾몄옄�뿴
		 * @return value null�씠硫� ""由ы꽩
		 */
		public static String isNull(Object o){
			if(o == null){
				return "";
			}else{
				return o.toString().trim();
			} // end of if
		}

		public static String isNull(Object o, String s){
			if("".equals(isNull(o))){
				return s;
			}else{
				return o.toString().trim();
			} // end of if
		}

		public static String isNullToZero(Object o){
			if(o == null){
				return "0";
			}
			else if(o.toString().isEmpty()) {
				return "0";
			}
			else{
				return o.toString().trim();
			} // end of if
		}
		
		/**
		 * 臾몄옄�뿴�씠 Null�씠硫� ""�쓣 由ы꽩�븳�떎.
		 *
		 * @param value 諛붽� 臾몄옄�뿴
		 * @return value null�씠硫� ""由ы꽩
		 */
		public static String toString(String value){
			if(value == null){
				return "";
			}else{
				return value;
			} // end of if
		}

		/**
		 * 臾몄옄�뿴�씠 Null�씠硫� ""�쓣 由ы꽩�븳�떎.
		 *
		 * @param value 諛붽� 臾몄옄�뿴
		 * @param change ��泥� 臾몄옄�뿴
		 * @return value 諛붾�� 臾몄옄�뿴
		 */
		public static String toString(String value, String change){
			if(value == null || value.length() < 1){
				return change;
			}else{
				return value;
			} // end of if
		}

		/**
		 * 湲��옄�닔�젣�븳
		 *
		 * @param src 諛붽�臾몄옄�뿴
		 * @param trail 瑗щ━�몴(ex, "...")
		 * @param length �젣�븳 �븷 湲몄씠
		 */
		public static String cropString(String value, String trail, int length){
			String temp = value;

			if(value == null)	return "";

			if(value.length() > length){
				temp = value.substring(0, length) + trail;
			}

			return temp;
		}

		public static String getDiffTime(String start, String end) throws Exception {
			try{
			if( !"".equals(isNull(start)) && !"".equals(isNull(end))){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			    Date startDate = formatter.parse(start);
			    Date endDate = formatter.parse(end);

			    String s = getTimeFormat((int)((endDate.getTime() - startDate.getTime()) / 1000 ));

			    return s;
		    }
			}catch(Exception e){
				e.printStackTrace();
			}
		    return "";
		}


		public static int getDiffTimeSec(String start, String end) throws Exception {
		    int ret = 0;
			if( !"".equals(isNull(start)) && !"".equals(isNull(end))){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			    Date startDate = formatter.parse(start);
			    Date endDate = formatter.parse(end);

			    ret = (int)((endDate.getTime() - startDate.getTime()) / 1000 );

			}
		    return ret;
		}

	public static Object beanTobean(Object bean1, Object bean2){
		org.springframework.beans.BeanUtils.copyProperties(bean1,bean2);
		return bean2;
	}

	public static String getMessage(String code){

		String message = (new StringBuilder("\uBA54\uC138\uC9C0 \uCF54\uB4DC[")).append(code).append("]\uAC00 messages.properties\uC5D0 \uC815\uC758\uAC00 \uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4. ").toString();
		try{
//			message = MessageUtil_old.getMessage(code, null, Locale.KOREA);
			message = MessageUtil.getMessage(code, null, Locale.KOREA);
		}catch(Exception e){
			return "";
		}
		return message;
	}


	public static String getMessage(String code, String siteCode){

		String message = (new StringBuilder("\uBA54\uC138\uC9C0 \uCF54\uB4DC[")).append(code).append("]\uAC00 messages.properties\uC5D0 \uC815\uC758\uAC00 \uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4. ").toString();
		try{
//			message = MessageUtil_old.getMessage(code, null, Locale.KOREA);
			message = MessageUtil.getMessage(code, null, siteCode);
		}catch(Exception e){
			return "";
		}
		return message;
	}

	public static Map collectParameters(HttpServletRequest req) throws Exception{
		Enumeration paramNames = req.getParameterNames();
		String key = null;
		String values[] = null;
		Map<String, Object> map = new HashMap<>();
		while( paramNames.hasMoreElements() ) {
			key = (String)paramNames.nextElement();
			values = req.getParameterValues(key);
			if( values != null && values.length == 1){
				map.put(key, toHtmlEnc(values[0].trim()));
			}
			else{
				for(int i=0;i<values.length;i++){
					values[i] = toHtmlEnc(values[i].trim());
				}
				map.put(key, values);
			}
		}

		map.put("REQUEST",req);

		return map;
	}

	public static Map collectParametersMulti(HttpServletRequest req) throws Exception{

		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;

		Enumeration paramNames = multi.getParameterNames();
		String key = null;
		String values[] = null;
		Map<String, Object> map = new HashMap<>();

		while( paramNames.hasMoreElements() ) {
			key = (String)paramNames.nextElement();
			values = multi.getParameterValues(key);

			if( values != null && values.length == 1){
				map.put(key, toHtmlEnc(values[0].trim()));
			}else{
				for(int i=0;i<values.length;i++){
					values[i] = toHtmlEnc(values[i].trim());
				}
				map.put(key, values);
			}
		}
		//System.out.println(map);

		map.put("MREQUEST",multi);

		return map;

	}


	public static Map collectParameterNo(HttpServletRequest req) throws Exception{
		Enumeration paramNames = req.getParameterNames();
		String key = null;
		String values[] = null;
		Map<String, Object> map = new HashMap<>();
		while( paramNames.hasMoreElements() ) {
		key = (String)paramNames.nextElement();
		values = req.getParameterValues(key);
			if( values != null && values.length == 1){
				map.put(key, values[0].trim());
			}else{
				for(int i=0;i<values.length;i++){
					values[i] = values[i].trim();
				}
				map.put(key, values);
			}
		}
		map.put("REQUEST",req);
		return map;
	}


	public static void dispatch (HttpServletRequest request,
							HttpServletResponse response,
							String returnPage) throws javax.servlet.ServletException, java.io.IOException {
		ServletContext sc 		= request.getSession().getServletContext();
		RequestDispatcher rd 	= sc.getRequestDispatcher(returnPage);
		rd.forward(request, response);
	}

	public static void redirect (HttpServletRequest request,
						HttpServletResponse response,
						String returnPage) throws javax.servlet.ServletException, java.io.IOException {
		response.sendRedirect(returnPage);
	}

	public static String getTimeFormat(int sec){
		String s = "00:00:00";
		if( sec>0 ){
			int iTmp1 = sec%3600;
			int iSec = iTmp1%60;
			int iHour = (sec - iTmp1)/3600;
			int iMin = (iTmp1 - iSec)/60;

			s = ((iHour<10)?"0"+iHour:""+iHour)+":"+((iMin<10)?"0"+iMin:""+iMin)+":"+((iSec<10)?"0"+iSec:""+iSec);
		}
		return s;
	}

	public static String getCurDate(String s){

		String result = "";

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		String today     = formatter.format(new java.util.Date());
		String cur_year  = today.substring(0,4);
		String cur_month = today.substring(4,6);
		String cur_day   = today.substring(6,8);

		if( "YMD".equals(s) ){
			result = cur_year+cur_month+cur_day;
		}else if( "YM".equals(s) ){
			result = cur_year+cur_month;
		}else if( "MD".equals(s) ){
			result = cur_month+cur_day;
		}else if( "Y".equals(s) ){
			result = cur_year;
		}else if( "M".equals(s) ){
			result = cur_month;
		}else if( "D".equals(s) ){
			result = cur_day;
		}else{
			result = cur_year+cur_month+cur_day;
		}

		return result;
	}

	public static String getCurDateTo(String ymd, int d){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day 	= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.DATE,d);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}


	public static String getCurMonthTo(String ymd, int m){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day 	= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.MONTH,m);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}


	public static String getPreMonthStart(String ymd, int m){

		String result = "";
		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day 	= "01";

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));
		cal.add(Calendar.MONTH,m);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());
		return result;
	}

	public static String getPreMonthEnd(String ymd, int m){

		String result = "";
		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day = ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));
		cal.add(Calendar.MONTH,m);

		int iMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE,iMaxDay);



		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());
		return result;
	}



	public static String getNextDate(String ymd){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day 	= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.DATE,1);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String getPreDate(String ymd){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month = ymd.substring(4,6);
		String day 	= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.DATE,-1);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String getNextDateOfWeek(String ymd){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.WEEK_OF_MONTH,1);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String getPreDateOfWeek(String ymd){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.add(Calendar.WEEK_OF_MONTH,-1);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String[] getArrayDateOfWeek(String ymd){

		String result[] = new String[7];
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		cal.set(Calendar.WEEK_OF_MONTH,cal.get(Calendar.WEEK_OF_MONTH));

		for(int i=0;i<7;i++){
			cal.set(Calendar.DAY_OF_WEEK, (i+1));
			result[i] = formatter.format(cal.getTime());
		}

		return result;
	}

	public static String getDateOfWeekKor(String ymd){

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		String[] aDayOfWeek={"�씪","�썡","�솕","�닔","紐�","湲�","�넗"};

		return aDayOfWeek[cal.get(Calendar.DAY_OF_WEEK)-1];
	}


	public static String[] getArraySundayOfMonth(String year,String month){

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,1);
		cal.set(Calendar.WEEK_OF_MONTH,cal.get(Calendar.WEEK_OF_MONTH));

		int iMaxWeekOfMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

		String result[] = new String[iMaxWeekOfMonth];
		for(int i=0;i<iMaxWeekOfMonth;i++){
			cal.set(Calendar.DAY_OF_WEEK, 1);
			result[i] = formatter.format(cal.getTime());

			cal.add(Calendar.WEEK_OF_MONTH,1);
		}

		return result;
	}

	public static String getFirstSundayOfMonth(String year,String month){

		String result = "";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,1);

		cal.set(Calendar.WEEK_OF_MONTH,cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(Calendar.DAY_OF_WEEK, 1);
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String getLastSundayOfMonth(String year,String month){

		String result = "";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,1);

		int iMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		cal.set(Calendar.DATE,iMaxDay);
		cal.set(Calendar.WEEK_OF_MONTH,cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(Calendar.DAY_OF_WEEK, 1);
		result = formatter.format(cal.getTime());

		return result;
	}

	public static String getWeekOfMonth(String ymd){

		String result = "";

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		result = cal.get(Calendar.WEEK_OF_MONTH)+"";

		return result;
	}

	public static String getMaxDayOfMonth(String year,String month){

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,1);

		int iMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return iMaxDay+"";
	}

	public static String getDateAddYMD(String ymd, int addYear, int addMonth, int addDay){

		String result = "";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

		String year 	= ymd.substring(0,4);
		String month 	= ymd.substring(4,6);
		String day 		= ymd.substring(6,8);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));

		if(addYear != 0) cal.add(Calendar.YEAR, addYear);
		if(addMonth != 0) cal.add(Calendar.MONTH, addMonth);
		if(addDay != 0) cal.add(Calendar.DATE, addDay);

		result = formatter.format(cal.getTime());

		return result;
	}


	public static String getDateAddSec(String date, int addSec){

		String result = "";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");

		String year 	= date.substring(0,4);
		String month 	= date.substring(4,6);
		String day 		= date.substring(6,8);
		String hh 		= date.substring(8,10);
		String mm 		= date.substring(10,12);
		String ss 		= date.substring(12,14);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
		cal.set(Calendar.DATE,Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hh));
		cal.set(Calendar.MINUTE,Integer.parseInt(mm));
		cal.set(Calendar.SECOND,Integer.parseInt(ss));

		cal.add(Calendar.SECOND, addSec);

		result = formatter.format(cal.getTime());

		return result;
	}


	public static int getDiffDate(String sYmd, String eYmd){

		int result = 0;

		Calendar sCal = Calendar.getInstance();
		sCal.set(Calendar.YEAR,Integer.parseInt(sYmd.substring(0,4)));
		sCal.set(Calendar.MONTH,Integer.parseInt(sYmd.substring(4,6))-1);
		sCal.set(Calendar.DATE,Integer.parseInt(sYmd.substring(6,8)));

		Calendar eCal = Calendar.getInstance();
		eCal.set(Calendar.YEAR,Integer.parseInt(eYmd.substring(0,4)));
		eCal.set(Calendar.MONTH,Integer.parseInt(eYmd.substring(4,6))-1);
		eCal.set(Calendar.DATE,Integer.parseInt(eYmd.substring(6,8)));

		int millis_in_day = 1000 * 60 * 60 * 24;
		long diffInMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
		result = (int)(diffInMillis / millis_in_day);

		return result;
	}

	public static String getStringDate(String dt, String gb, String de){

		String result = "";
		String format = "";
		
		if(dt == null || dt.equals("")) return result;
		if(de==null || de.equals("")) {de = ".";}
		

		if(gb.equals("ym")){
			format = "yyyy"+de+"MM";
		}else if(gb.equals("ymd")){
			format = "yyyy"+de+"MM"+de+"dd";
		}else if(gb.equals("ymdh")){
			format = "yyyy"+de+"MM"+de+"dd HH:mm:ss";
		}else{
			format = "yyyy"+de+"MM"+de+"dd";
		}

		String year = dt.substring(0,4);
		String month = dt.substring(4,6);
		String day = "";
		String hh = "";
		String mm = "";
		String ss = "";

		if(!gb.equals("ym")){
			day = dt.substring(6,8);
		}

		if(gb.equals("ymdh")){
			hh = dt.substring(8,10);
			mm = dt.substring(10,12);
			ss = dt.substring(12,14);
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH,Integer.parseInt(month)-1);

		if(!gb.equals("ym")){
			cal.set(Calendar.DATE,Integer.parseInt(day));
		}

		if(gb.equals("ymdh")){
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hh));
			cal.set(Calendar.MINUTE,Integer.parseInt(mm));
			cal.set(Calendar.SECOND,Integer.parseInt(ss));
		}

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		result = formatter.format(cal.getTime());

		return result;
	}

	/**
	 * �쁽�옱 �떆遺꾩큹 援ы븯湲�.
	 * @param s  - 援щ텇�옄
	 * @return String result
	 */
	public static String getCurTime(String s){

		String result = "";

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HHmmss");
		String today  	= formatter.format(new java.util.Date());
		String cur_hh 	= today.substring(0,2);
		String cur_mm 	= today.substring(2,4);
		String cur_ss 	= today.substring(4,6);

		if( "HHMMSS".equals(s) ) {
			result = cur_hh + cur_mm + cur_ss;
		} else if( "HHMM".equals(s) ) {
			result = cur_hh + cur_mm;
		} else if( "HH".equals(s) ) {
			result = cur_hh;
		} else if( "MM".equals(s) ) {
			result = cur_mm;
		} else if( "SS".equals(s) ) {
			result = cur_ss;
		}

		return result;
	}

	/**
	 * �젅�긽, �젅�븯, 諛섏삱由� 泥섎━
	 * @param strMode  - �닔�떇
	 * @param nCalcVal - 泥섎━�븷 媛�(�냼�닔�젏 �씠�븯 �뜲�씠�꽣 �룷�븿)
	 * @param nDigit   - �뿰�궛 湲곗� �옄由우닔(�삤�씪�겢�쓽 ROUND�븿�닔 �옄由우닔 湲곗�)
	 *                   -2:�떗�떒�쐞, -1:�썝�떒�쐞, 0:�냼�닔�젏 1�옄由�
	 *                   1:�냼�닔�젏 2�옄由�, 2:�냼�닔�젏 3�옄由�, 3:�냼�닔�젏 4�옄由�, 4:�냼�닔�젏 5�옄由� 泥섎━
	 * @return String nCalcVal
	 */
	public static String calcMath(String strMode, double nCalcVal, int nDigit) {

		if(strMode.equals("ROUND")) {        //諛섏삱由�
			if(nDigit < 0) {
				nDigit = -(nDigit);
	   		  	nCalcVal = Math.round(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
			} else {
				nCalcVal = Math.round(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
			}
		} else if(strMode.equals("CEIL")) {  //�젅�궘
			if(nDigit < 0) {
				nDigit = -(nDigit);
   		        nCalcVal = Math.ceil(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
   		  	} else {
   		  		nCalcVal = Math.ceil(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
   		  	}
		} else if(strMode.equals("FLOOR")) { //�젅�븯
			if(nDigit < 0) {
				nDigit = -(nDigit);
   		        nCalcVal = Math.floor(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
			} else {
				nCalcVal = Math.floor(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
			}
		} else {                        //洹몃�濡�(臾댁“嫄� �냼�닔�젏 泥レ㎏ �옄由ъ뿉�꽌 諛섏삱由�)
			nCalcVal = Math.round(nCalcVal);
		}

   		return String.valueOf(nCalcVal);
	}

	/**
	 * �냼�닔�젏 誘몃쭔 踰꾨━湲�
	 * @param strValue  - �썝蹂� 媛�
	 * @return df.format(i)
	 */
	public static String toInteger(String strValue) {

		DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
		Double d = Double.parseDouble(strValue);

		int i = (int) (d * 1);

   		return (df.format(i));
	}

	public static WebApplicationContext getWebApplicationContext(){
		return ContextLoader.getCurrentWebApplicationContext();
	}
	public static String getWebRootPath() {
		String file_path = getWebApplicationContext().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		return file_path.substring(0,file_path.length()-1);
	}
	public static String getDefaultFilePath() {
		return  getWebRootPath()+"/WEB-INF/files";
	}
	public static String getDefaultFilePathTmp() {
		return  getWebRootPath()+"/WEB-INF/files/tmp";
	}
	public static String getCustomPropFilePath() {
		return  getWebRootPath()+"/WEB-INF/config/works/properties";
	}
	public static String getCustomPropFileNm() {
		return  "customcode_ko_KR";
	}
	public static Object getSpringBean(String beanName) {
		return getWebApplicationContext().getBean(beanName);
	}

	public static void setSessionRsa(HttpServletRequest req) throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(512);

		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey pubKey = keyPair.getPublic();
		PrivateKey priKey = keyPair.getPrivate();

		RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(pubKey, RSAPublicKeySpec.class);

		String pubKeyMod = publicSpec.getModulus().toString(16);
		String pubKeyExp = publicSpec.getPublicExponent().toString(16);

		req.getSession().setAttribute("SS_PUBKEY_MOD", pubKeyMod);
		req.getSession().setAttribute("SS_PUBKEY_EXP", pubKeyExp);
		req.getSession().setAttribute("SS_RSA_PRIKEY",priKey);

	}
	public static String toRsaDec(HttpServletRequest req, String s) {
		String result = null;

		try{

			Cipher cipher = Cipher.getInstance("RSA");
			PrivateKey p_key = (PrivateKey)req.getSession().getAttribute("SS_RSA_PRIKEY");
			cipher.init(Cipher.DECRYPT_MODE, p_key);

			byte[] inputBytes  = hexToByteArray(s);
			byte[] outputBytes = cipher.doFinal(inputBytes);

			result = new String(outputBytes, "UTF8");

		}catch(Exception e) {
			return null;
		}
		return result;
	}

	public static String getCurOrderYmd() {
		String curOrderYmd = "";

		String new_day_h = getMessage("NEW_DAY_H");
		String curDate = getCurDate("YMD");
		String curH = getCurTime("HH");
		if("00".equals(new_day_h)){
			curOrderYmd = curDate;
		}else{
			if(Integer.parseInt(curH)<Integer.parseInt(new_day_h)){
				curOrderYmd = getDateAddYMD(curDate,0, 0, -1);
			}else{
				curOrderYmd = curDate;
			}
		}

		return curOrderYmd;
	}

	public static String getJobStartTime(String order_ymd, String hh, String mm) {
		String jobStartTime = "";

		String new_day_h = getMessage("NEW_DAY_H");

		if(!"".equals(isNull(hh))){
			if("00".equals(new_day_h)){
				jobStartTime = order_ymd+hh+mm;
			}else{
				if(Integer.parseInt(hh)<Integer.parseInt(new_day_h)){
					jobStartTime = getDateAddYMD(order_ymd,0, 0, 1)+hh+mm;
				}else{
					jobStartTime = order_ymd+hh+mm;
				}
			}

		}else{
			jobStartTime = order_ymd+new_day_h+"00";
		}

		return jobStartTime;
	}

	public static String toJobStatus(String job_status, String confirm_yn, String in_job_cnt) {
		String sRet = job_status;

		if("01".equals(job_status)){
			if("Y".equals(confirm_yn)) sRet = "W1";
			else if(!"0".equals(in_job_cnt)) sRet = "W2";
			else sRet = "W3";
		}

		return sRet;
	}


	/*
	 *  泥쒕떒�쐞 肄ㅻ쭏 李띻린
	 */
	public static String toNumFormat(Long num) {
	  DecimalFormat df = new DecimalFormat("#,###");
	  return df.format(num);
	}

	public static String toNumFormat(int num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(num);
		}
	public static String toNumFormat(String num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(Long.parseLong(num));
		}

	/*
	 * �궇吏� 蹂��솚
	 */
	public static String getDateFormat2(String dt) throws ParseException{

		if(dt.equals("")) return "";

		String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
		Date javaDate = format.parse(dt);

		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");
		String today     = formatter.format(javaDate);

		return today;
	}

	public static String toDate(){

		String rtn = "";

		Date dt = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		rtn = sf.format(dt);

		return rtn;
	}

	public static String toDate2(){

		String rtn = "";

		Date dt = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		rtn = sf.format(dt);

		return rtn;
	}

	public static String getDirReplace(String str){

		String ret = "";

		if(str.equals("") || str == null) return "";

		ret = str.replace("//", "/");

		return ret;
	}


	//踰붿슜 Name 援щ텇..
	public static String getGb(String GBNM){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage(GBNM);
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage(GBNM+"."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage(GBNM+"."+gb[i])+",";
				}
			}

		}catch(Exception e){
			e.getMessage();
		}

		return gb_nm;
	}



	//吏곴툒
	public static String getDutyGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("DUTY.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("DUTY.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("DUTY.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�궗�슜�옄沅뚰븳
	public static String getUserGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("PERM.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("PERM.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("PERM.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//湲곌린援щ텇
	public static String getDeviceGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("DEVICE.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("DEVICE.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("DEVICE.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�꽌踰꾧뎄遺�
	public static String getServerGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("SERVER.TYPE");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("SERVER.TYPE."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("SERVER.TYPE."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�뾽臾닿뎄遺�
	public static String getJobGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("JOB.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("JOB.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("JOB.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�듅�씤��湲곗떆媛�
	public static String getApprovaWaitTime(){

		String wait_time = CommonUtil.getMessage("APPROVAL_WAIT_TIME");

		return wait_time;
	}

	//寃곗옱
	public static String getApprovalGb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("APPROVAL.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("APPROVAL.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("APPROVAL.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//寃곗옱�닚�꽌
	public static String getApprovalOrder(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("APPROVAL.ORDER");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("APPROVAL.ORDER");
				}else{
					gb_nm += CommonUtil.getMessage("APPROVAL.ORDER")+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�궗�슜�옄 �긽�깭
	public static String getUserStatus(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("USER.STATUS");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("USER.STATUS."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("USER.STATUS."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	//�듅�씤�슂泥�援щ텇
	public static String getAuth_Gb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("AUTH.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("AUTH.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("AUTH.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getApproval_State(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("APPROVAL.STATE");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("APPROVAL.STATE."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("APPROVAL.STATE."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getApproval_Scope(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("APPROVAL.SCOPE");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("APPROVAL.SCOPE."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("APPROVAL.SCOPE."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getAuthCode_Status(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("AUTH.CODE.STATUS");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("AUTH.CODE.STATUS."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("AUTH.CODE.STATUS."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getApproval_Type(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("APPROVAL.TYPE");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("APPROVAL.TYPE."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("APPROVAL.TYPE."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getSend_Gb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("SEND.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("SEND.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("SEND.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getLogin_Gb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("LOGIN.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("LOGIN.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("LOGIN.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static String getReq_Gb(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("REQ.GB");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("REQ.GB."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("REQ.GB."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}

	public static List<Map<String, String>> getReqGb_Map(){

		String[] gb = null;
		String gb_nm = "";
		List<Map<String, String>> map_list = new ArrayList<>();
		Map<String, String> map = null;

		try{

			String gb_cd = CommonUtil.getMessage("REQ.GB");
			gb = gb_cd.split(",");
			for (String element : gb) {

				map = new HashMap<>();

				map.put("REQ_NM", CommonUtil.getMessage("REQ.GB."+element));
				map.put("REQ_CD", element);

				map_list.add(map);
			}

		}catch(Exception e){

		}

		return map_list;
	}

	public static String getDeviceManage(){

		String[] gb = null;
		String gb_nm = "";

		try{

			String gb_cd = CommonUtil.getMessage("DEVICE.MANAGE");
			gb = gb_cd.split(",");
			for(int i=0;i<gb.length;i++){
				if(i== gb.length-1){
					gb_nm += CommonUtil.getMessage("DEVICE.MANAGE."+gb[i]);
				}else{
					gb_nm += CommonUtil.getMessage("DEVICE.MANAGE."+gb[i])+",";
				}
			}

		}catch(Exception e){

		}

		return gb_nm;
	}


	public static String AesCryptH(String inputStr){
		try {
			String EncBase64Str = "";
			EncBase64Str = AesCryptoUtil.encrypt(inputStr);
			byte[] EncBStr = Base64.decodeBase64(EncBase64Str);
			String returnStr = "";
			returnStr = toHex(EncBStr);
	    	return returnStr;
		}
		catch( Exception e ){
			e.printStackTrace();
			return "";
		}
	}



	public static String AesDecryptH(String inputStr){
		try {

			byte[] EncBStr = hexToByteArray(inputStr);

			String DecBase64Str = Base64.encodeBase64String(EncBStr);
			String DecStr = AesCryptoUtil.decrypt(DecBase64Str);

	    	return DecStr;
		}
		catch( Exception e ){
			e.printStackTrace();
			return inputStr;
		}
	}



	
	
	
	
	
	
	
	
	
	
	public static Map<String, Object> getMapFromJSONObject(org.json.JSONObject jsonObject) {
		if (ObjectUtils.isEmpty(jsonObject)) {
				throw new IllegalArgumentException(String.format("BAD REQUEST obj %s", jsonObject));
		}
		try {
				return new ObjectMapper().readValue(jsonObject.toString(), Map.class);
		} 
		catch (Exception e) {
				throw new RuntimeException(e);
		}
	}

	
	
	
	public static Map<String, String> getMapSFromJSONObject(org.json.JSONObject jsonObject) {
		if (ObjectUtils.isEmpty(jsonObject)) {
				throw new IllegalArgumentException(String.format("BAD REQUEST obj %s", jsonObject));
		}
		try {
				return new ObjectMapper().readValue(jsonObject.toString(), Map.class);
		} 
		catch (Exception e) {
				throw new RuntimeException(e);
		}
	}
	

//	배열 넣어서 select의 옵션 처리
	public static String arrToSelect(String[] Arr, String NameS, String IdS){
		String rtnStr = "";
		rtnStr = "<select name='"+NameS+"' id='"+IdS+"'>";
		for (String element : Arr) {
			rtnStr+="<option value='"+element+"'>"+element+"</option>";
		}
		rtnStr += "</select>";
		return rtnStr;
	}


//	배열 넣어서 그리드의 select의 옵션 처리
	public static String arrToSelectTitle(String paramNm, String NameS, String IdS, String selValue){
		String [] arr = CommonUtil.getMessage(paramNm).split(",");
		String [] arrTitle = CommonUtil.getMessage(paramNm+".TITLE").split(",");
		String dataStr = "";
		 	dataStr = "<select name='"+NameS+"' id='"+IdS+"'>";
			dataStr += "<option value=''>없음</option>";
			for(int i=0;arr !=null && i<arr.length; i++){
				if(selValue.equals(arr[i])) {
					dataStr += "<option selected value='" + arr[i]+"'>";					
				}else {
					dataStr += "<option value='" + arr[i]+"'>";
				}

			dataStr += "" + arrTitle[i]+"";
			dataStr += "</option>";
		}
		dataStr += "</select>";
		return dataStr;
	}
	
//	배열 넣어서 그리드의 select의 옵션 처리
	public static String arrToSelectSerial(String paramNm, String NameS, String IdS, String sSerial){
		String [] arr = CommonUtil.getMessage(paramNm).split(",");
		String [] arrTitle = CommonUtil.getMessage(paramNm+".TITLE").split(",");
		String dataStr = String.format("<select class='basic-info'  name='%s' id='%s' data-serial='%s'>", NameS, IdS, sSerial);
		dataStr += "<option value=''>없음</option>";
		for(int i=0;arr !=null && i<arr.length; i++){
			dataStr += "<option value='" + arr[i]+"'>";
			dataStr += "" + arrTitle[i]+"";
			dataStr += "</option>";
		}
		dataStr += "</select>";
		return dataStr;
	}


//	배열 넣어서 그리드의 select의 옵션 처리
	public static String arrToGridSelect(String[] Arr){
		String dataStr = " editor:{type:'select', options:{ listItems:[";
		for (String element : Arr) {
			dataStr += "{ ";
			dataStr += "text:'" + element+"',";
			dataStr += "value:'"+ element+"'";
			dataStr += "},";
		}
		dataStr += "]} }";
		return dataStr;
	}

//	배열 넣어서 그리드의 select의 옵션 처리
	public static String arrToGridSelectTitle(String paramNm){
		String [] arr = CommonUtil.getMessage(paramNm).split(",");
		String [] arrTitle = CommonUtil.getMessage(paramNm+".TITLE").split(",");
		String dataStr = " editor:{type:'select', options:{ listItems:[";
		dataStr += "{text:'',value:''},";
		for(int i=0;arr !=null && i<arr.length; i++){
			dataStr += "{ ";
			dataStr += "text:'" + arrTitle[i]+"',";
			dataStr += "value:'"+ arr[i]+"'";
			dataStr += "},";
		}
		dataStr += "]} }";
		return dataStr;
	}


//	리스트 넣어서 그리드의 select의 옵션 처리
	public static String listToGridSelect(List<Map<String, String>> list,  String colValue, String colNm){
		String dataStr = " editor:{type:'select', options:{ listItems:[";
		dataStr += "{text:'(N/A)', value:'(N/A)'},";
		for(int i=0;list !=null && i<list.size(); i++){
			dataStr += "{ ";
			dataStr += "text:'" + isNull(list.get(i).get(colNm))+"',";
			dataStr += "value:'"+ isNull(list.get(i).get(colValue))+"'";
			dataStr += "},";
		}
		dataStr += "]} }";
		return dataStr;
	}
	
	

// 리스트 넣어서 html의 select의 옵션 처리 
	public static String listToSelectH(List<Map<String, String>> list, String colValue, String colNm,  String nameStr, String idStr){
		String dataStr = "<select name="+nameStr+" id="+idStr+">";
		dataStr += "<option  value=''>없음</option>";
		for(int i=0;list !=null && i<list.size(); i++){
			dataStr += "<option value="+CommonUtil.isNull(list.get(i).get(colValue))+">";
			dataStr += "" + CommonUtil.isNull(list.get(i).get(colNm))+"";
			dataStr += "</option>";
		}
		dataStr += "</select>";
		return dataStr;
	}
	

	public static String listToSelectDepth(List<Map<String, String>> list, String colValue, String colNm,  String nameStr, String idStr, String idStrT ){
		String dataStr = "<select name="+nameStr+" id="+idStr+" onchange=grpChange('"+idStr+"','"+idStrT+"','');>";
		dataStr += "<option  value=''>없음</option>";
		for(int i=0;list !=null && i<list.size(); i++){
			dataStr += "<option value="+CommonUtil.isNull(list.get(i).get(colValue))+">";
			dataStr += "" + CommonUtil.isNull(list.get(i).get(colNm))+"";
			dataStr += "</option>";
		}
		dataStr += "</select>";
		return dataStr;
	}
	
	public static String listToSelectAttrData(List<Map<String, String>> list, String colValue, String colNm,  String nameStr, String idStr, String dataSerial){
		String dataStr = "<select class='basic-info'  name="+nameStr+" id="+idStr+" data-serial="+dataSerial+">";
		dataStr += "<option  value=''>없음</option>";
		for(int i=0;list !=null && i<list.size(); i++){
			dataStr += "<option value="+CommonUtil.isNull(list.get(i).get(colValue))+">";
			dataStr += "" + CommonUtil.isNull(list.get(i).get(colNm))+"";
			dataStr += "</option>";
		}
		dataStr += "</select>";
		return dataStr;
	}
	
	
// 배열 에서 select html 문 만들기 최종사용형태
	public static String listToSelect (List<Map<String, String>> list, String colValue, String colNm, String nameTxt, String idTxt, String opt){
		String returnValue= "";
		if (opt.equals("GRID")) {
			returnValue = listToGridSelect(list, colValue, colNm);
		}
		if (opt.equals("HTML")) {
			returnValue = listToSelectH(list, colValue, colNm, nameTxt, idTxt);
		}
		return  isNull(returnValue);
	}
	
// 배열 에서 select html 문 만들기 최종사용형태
	public static String arrToSelect (String[] Arr, String nameTxt, String idTxt, String opt){
		String returnValue= "";
		if (opt.equals("GRID")) {
			returnValue = arrToGridSelect(Arr);
		}
		if (opt.equals("HTML")) {
			returnValue = arrToSelect(Arr, nameTxt, idTxt);
		}
		return isNull(returnValue);
	}
	
// param 에서 select html 문 만들기 최종사용형태
	public static String paramToSelect (String param, String nameTxt, String idTxt, String opt, String selValue){
		String returnValue= "";
		if (opt.equals("GRID")) {
			returnValue = arrToGridSelectTitle(param);
		}
		if (opt.equals("HTML")) {
			returnValue = arrToSelectTitle(param, nameTxt, idTxt, selValue);
		}
		
		if (opt.equals("GRAPH")) {
			
			
			String [] arr = CommonUtil.getMessage(param).split(",");
			String [] arrTitle = CommonUtil.getMessage(param+".TITLE").split(",");
			
			String dataStr = "";
			 	dataStr = "var arrTest1 = [];";
				dataStr += "arrTest1.push({value:'',content:''});";
				for(int i=0;arr !=null && i<arr.length; i++){
					dataStr += "arrTest1.push({value:'" + arr[i]+"',content:'" + arrTitle[i]+"'});";
				}
			returnValue = dataStr;
		}
		
		return isNull(returnValue);
	}

	
	
	
	
	
	
	public static List<Map<String, String>> getApiList(HttpServletRequest req, String svrNm, String queryParam) {
		List<Map<String, String>> svrList =  new ArrayList<Map<String, String>>();
		try{
			Map<String, String> paramMap = new HashMap<>();
			Map<String, Object> mapSession = CommonUtil.getSessionNex(req, "user_session");
			String user_cd = CommonUtil.isNull(mapSession.get("nex_user_cd"));
			String user_id = CommonUtil.isNull(mapSession.get("nex_user_id"));
			String user_ip = CommonUtil.isNull(mapSession.get("nex_remote_ip"));
//		 	paramMap.put("ins_user_ip",user_ip);
//		 	paramMap.put("ins_user_cd",user_cd);
//		 	paramMap.put("ins_user_id",user_id);

		 	String param = "";
		 	String paramEnc = "";

		 	param += user_id;
		 	param += "&"+user_cd;
		 	param += "&"+user_ip;
		 	param += "&"+svrNm;
		 	
		 	if (!queryParam.equals("")) {
			 	param += "&"+queryParam;
		 	}else {
			 	param += "&1=1";
		 	}
		 	
		 	
		 	paramEnc = CommonUtil.toCrypEncRan(param, true);
			
			
			StringBuilder urlBuilder = new StringBuilder("http://127.0.0.1:1801"); /*URL*/
			urlBuilder.append("/" +  URLEncoder.encode("apiJsonRes","UTF-8") ); 
			urlBuilder.append("/" +  URLEncoder.encode(paramEnc,"UTF-8") ); 
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
//			System.out.println("Response code: " + conn.getResponseCode()); 
			StringBuilder sb = new StringBuilder();
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line = "";
	            while((line = br.readLine()) != null){
						sb.append(line);
				}
				JSONArray jsonArr = new JSONArray(sb.toString());
			 	for (Object jsonObject : jsonArr) {
			 		Map<String, String> objMap	= CommonUtil.getMapSFromJSONObject((JSONObject) jsonObject);
		 			svrList.add(objMap);
			 	}
				br.close();
			} else {
			}
			sb=null;
			conn.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		return svrList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	




//	jsonObject에 해당카값 존재하는지 체크
	public static String jsonHas(JSONObject jobj, String key){
		String returnData = "";
		if(jobj.has(key)){
			returnData = CommonUtil.isNull(jobj.get(key));
		}else {
			returnData = "{}";
		}
		return returnData;
	}


//	jsonArray 값 존재하는지 체크
	public static JSONArray jsonArrHas(JSONArray jobj, String arryStr){
		try {
			jobj = new JSONArray(arryStr);
		} catch(JSONException e) {
			jobj = new JSONArray("[]");
		} catch(Exception e) {
			jobj = new JSONArray("[]");
		}
		return jobj;
	}

	public static boolean imgDecBae64(String base64, String target){

		String data = base64;

		byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);

		try {
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
//			ImageIO.write(bufImg, "jpg", new File(target));
			ImageIO.write(bufImg, "png", new File(target));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}


	
	public static String getNowVersion(){
		Date date = new Date();
		SimpleDateFormat sfVersion = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowVersion = sfVersion.format(date);
		return nowVersion;
	}

	public static void setSessionNex(HttpServletRequest req, String sSessionKey, Map<String, Object>mapSession) throws Exception {
		try {
			req.getSession().setAttribute(sSessionKey, mapSession);
		}
		catch (Exception ex) {
			log.error("error : ", ex);
		}
	}
	
	public static Map<String, Object> getSessionNex(HttpServletRequest req, String sSessionName) {
		Map<String, Object> mapSession	= new HashMap<String, Object>();
		try {
			mapSession	= (Map<String, Object>)req.getSession().getAttribute(sSessionName);
		}
		catch (Exception ex) {
			log.error("error : ", ex);
		}
		return mapSession;
	}
	
	public static void setChkDate(HttpServletRequest req) throws Exception {
		try {
			Date dataTime				= new Date();
			SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss");
			String sDateTime			= formatter.format(dataTime);
			req.getSession().setAttribute("dateTime", sDateTime);
		}
		catch (Exception ex) {
			log.error("error : ", ex);
		}
	}
	
	public static boolean getChkDate(HttpServletRequest req) throws Exception {
		boolean bOk	= false;
		try {
			String sPrvTime				= (String)req.getSession().getAttribute("dateTime");
			if(sPrvTime == null) {
				bOk = true;
				CommonUtil.setChkDate(req);
			}
			else {
				Date dataTime				= new Date();
				SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss");
				String sDateTime			= formatter.format(dataTime);
				int iDiffSec				= CommonUtil.getDiffTimeSec(sPrvTime, sDateTime);
				if(iDiffSec > 10) {
					bOk = true;
					CommonUtil.setChkDate(req);
				}
			}
		}
		catch (Exception ex) {
			log.error("error : ", ex);
		}
		return bOk;
	}

	public static void setLoopSession(HttpServletRequest req) throws Exception {
		try {
			Date dataTime				= new Date();
			SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss");
			String sDateTime			= formatter.format(dataTime);
			req.getSession().setAttribute("sessionLoop", sDateTime);
		}
		catch (Exception ex) {
			log.error("error : ", ex);
		}
	}
	
	public static List<String> keyValidation(Map<String, Object> mapInput, List<String> requiredKeys) {
		// List<String> requiredKeys = Arrays.asList("job_id", "job_gb");
		// 누락된 키 추출
        List<String> missingKeys = requiredKeys.stream()
            .filter(key -> !mapInput.containsKey(key))
            .collect(Collectors.toList());

        return missingKeys;
	}
	
	public static void main(String[] args) throws Exception {

	}

}