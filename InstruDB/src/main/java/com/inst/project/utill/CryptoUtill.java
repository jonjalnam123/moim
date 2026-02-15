package com.inst.project.utill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoUtill {

	public final int kBufferSize = 8192;
	public Key key = null;

	public CryptoUtill() throws Exception{
		initKey(this.getClass().getResourceAsStream("default.key"));
	}

	public CryptoUtill(String file_path) throws Exception{
		initKey(new File(file_path,"default.key"));
	}

	public CryptoUtill(String file_path,String file_name) throws Exception{
		initKey(new File(file_path,file_name));
	}

	public void initKey(File file) throws Exception{
		if (!file.exists()) {
			makekey(file);
		}

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		key = (Key)in.readObject();
		in.close();

	}

	public void initKey(InputStream is) throws Exception{
		ObjectInputStream in = new ObjectInputStream(is);
		key = (Key)in.readObject();
		in.close();
	}

	public void makekey(File file) throws IOException, NoSuchAlgorithmException{
		javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("DES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(key);
		out.close();
	}

	public String encrypt(String ID) throws Exception{
		if ( ID == null ) return null;
		if ( ID.length() == 0 ) return "";
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,key);
		String amalgam = ID;

		byte[] inputBytes1 = amalgam.getBytes("UTF8");
		byte[] outputBytes1 = cipher.doFinal(inputBytes1);

		String outputStr1 = Base64CoderUtil.encodeLines(outputBytes1);
		return outputStr1.replaceAll("\r\n", "\n").replaceAll("\n", "");
	}

	public String decrypt(String codedID) throws Exception{
		if ( codedID == null ) return null;
		if ( codedID.length() == 0 ) return "";
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

		byte[] inputBytes1  = Base64CoderUtil.decodeLines(codedID);
		byte[] outputBytes2 = cipher.doFinal(inputBytes1);

		String strResult = new String(outputBytes2,"UTF8");
		return strResult;
	}


}