package com.inst.project.utill;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesCryptoUtill {

	public static byte[] byteIv  = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	public static String strKey  = "2013_ftvs_nsm2142013_ftvs_nsm214";


	public static String encrypt(String strData) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] byteText = strData.getBytes("UTF-8");
		AlgorithmParameterSpec aps = new IvParameterSpec(byteIv);
		SecretKeySpec sks = new SecretKeySpec(strKey.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sks, aps);

		return Base64.encodeBase64String(cipher.doFinal(byteText));
	}

	public static String decrypt(String strData) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		try{
			byte[] byteText = Base64.decodeBase64(strData);
			AlgorithmParameterSpec aps = new IvParameterSpec(byteIv);
			SecretKeySpec sks = new SecretKeySpec(strKey.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			cipher.init(Cipher.DECRYPT_MODE, sks, aps);

			return new String(cipher.doFinal(byteText), "UTF-8");

		}catch(Exception e){
			return strData;
		}
	}
}
