package org.jepetto.sec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureAlgCtr {

    public static final String alg			= "AES/CBC/PKCS5Padding";
    private static final String AES			= "AES"; // 256 bit
    private static final String encoding	= "UTF-8";
    
    public static String encrypt(String text, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes(encoding));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException  {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, encoding);
    }
    
    
    public static void main(String args[]) {
    	//String key = "1234567890123456";
    	String key = PasswordGenerator.generatePasword(16);
    	
    	key = "X^#ReN!13v^k^tio"; 	 
    	
    	String values[] = new String[]{"MA000041","62ac46eaa5a35","5391321011273998","2411","867", "KIM CHANGHO"}; 
    	// // merchantid, merchanttransid, cardnumber, expiry, cvc, holdername
    	
    	String encripted = null;
    	try {
    		System.out.println("Key : " + key);
    		for(int i = 0 ; i < values.length ; i++) {
    			encripted = SecureAlgCtr.encrypt(values[i], key);
				System.out.println(SecureAlgCtr.decrypt(encripted, key) + " : " + encripted);// + " : "+  " enc length : " + encripted.length());
    		}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("62ac46eaa5a35".hashCode());
    	
    	System.out.println(Math.abs("62ac46eaa5a3562ac46eaa5a3562ac46eaa5a3562ac46eaa5a3562ac46eaa5a3562ac46eaa5a35".hashCode()));
    	
    }
}
