package com.runer.cibao.util;

import com.runer.cibao.Config;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 编码工具类
 * @author 20160808
 *
 */
@Component("encoder")
public class Encoder {
	
	char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	char[] codeNumber = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	Random random = new Random();
	
	/**
	 * MD5+base64en 密码两次编码
	 * @param value 
	 * @return returnData
	 */
	public String passwordEncoderByMd5(String value) {
		String returnData = "";
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String newstr = "";
			try{
				newstr = base64en.encode(md5.digest(value.getBytes("utf-8")))+"RunER";
				returnData = base64en.encode(md5.digest(newstr.getBytes("utf-8")));
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return returnData;
	}
	
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成随机码
	 * @param length
	 * @return
	 */
	public String generateRandom(int length){
		String strRand = "";
		for (int i = 0; i < length; i++) {
			strRand += String.valueOf(codeSequence[random.nextInt(36)]);
		}
		return strRand;
	}
	
	/**
	 * 生成随机码纯数字
	 * @param length
	 * @return
	 */
	public String generateRandomNumber(int length){
		String strRand = "";
		for (int i = 0; i < length; i++) {
			strRand += String.valueOf(codeNumber[random.nextInt(10)]);
		}
		return strRand;
	}

	/**
	 * 将二进制编码为base64
	 * @param binaryData
	 * @return
	 */
	public  String encode(byte[] binaryData) {
		try {
			return new String(Base64.encodeBase64(binaryData), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 *
	 * @param base64String
	 * @return
	 */
	public  byte[] decode(String base64String) {
		try {
			return Base64.decodeBase64(base64String.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}


	public String encoder64(String pwd){
		String str = Config.PASS_SALT + pwd;
		BASE64Encoder encoder = new BASE64Encoder();
		String encoderPwd = encoder.encode(str.getBytes());
		return  encoderPwd ;
	}

	/**
	 * 密码解密
	 * @param pwd
	 * @return
	 */
	public   String decodePwd(String pwd) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			String saltPwd = new String(decoder.decodeBuffer(pwd));
			System.out.println(saltPwd);
			String passWord = saltPwd.substring(Config.PASS_SALT.length());
			return passWord;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}


	public static void main(String[] args) {
		System.out.println(new Encoder().passwordEncoderByMd5("123456"));

	}
}
