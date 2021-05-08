package com.caterpie.timeletter.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SaltSHA256 {
	
	public static String getEncrypt(String source, String salt) {
		return getEncrypt(source, salt.getBytes());
	}
	
	public static String getEncrypt(String source, byte[] salt) {
		
		String result = "";
		
		byte[] a = source.getBytes();
		byte[] bytes = new byte[a.length + salt.length];
		
		// a의 0부터 a.length까지 bytes의 0부터 채워나간다
		System.arraycopy(a, 0, bytes, 0, a.length);
		// salt의 0부터 salt.length까지 bytes의 a.length부터 채워나간다.
		System.arraycopy(salt,0,bytes,a.length,salt.length);
		
		try {
			// 암호화 방식 지정 메소드
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
			
			byte[] byteData = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (int i=0; i<byteData.length;++i) 
				sb.append(Integer.toString((byteData[i] & 0xFF)+256, 16));
			result = sb.toString();
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String generateSalt() {
		Random random = new Random();
		
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<salt.length;++i)
			sb.append(String.format("%02x", salt[i]));
		
		return sb.toString();
	}
}
