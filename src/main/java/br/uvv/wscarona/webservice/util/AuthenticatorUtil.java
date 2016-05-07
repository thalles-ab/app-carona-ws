package br.uvv.wscarona.webservice.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.InfoUser;

public class AuthenticatorUtil {
	
	public static InfoUser generateToken(String code) throws ListMessageException{
		if(StringUtils.isNullOrEmpty(code)){
			throw new ListMessageException("error.no.code");
		}
		
		InfoUser token = new InfoUser();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		token.setExpirationToken(calendar.getTime());
		
		String aux = UUID.randomUUID().toString();
		aux += "-" + code + "-" + Instant.now();
		token.setToken(Base64.encodeBase64String(aux.getBytes()));
		return token;
	}
	
	public static String getPassword(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		return hashString(pass, "SHA-256");
	}
	
	private static String hashString(String message, String alg) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance(alg);
	        byte[] hashedBytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
	        return convertByteArrayToHexString(hashedBytes);
	    } catch (NoSuchAlgorithmException ex) {
	        return null;
	    }
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	
	// TODO REMOVER AO FINAL DO PROJETO
	public static void main(String[] args){
		String pass = hashString("123456", "MD5");
		System.out.println("PASSWORD PARA REQUEST " + pass);
		System.out.println("PASSWORD PARA BANCO " + hashString(pass, "SHA-256"));
	}
}
