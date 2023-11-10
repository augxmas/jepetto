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

	public static final String alg = "AES/CBC/PKCS5Padding";
	private static final String AES = "AES"; // 256 bit
	private static final String encoding = "UTF-8";

	public static String encrypt(String text, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
		IvParameterSpec ivParamSpec = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
		byte[] encrypted = cipher.doFinal(text.getBytes(encoding));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decrypt(String cipherText, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
		IvParameterSpec ivParamSpec = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, encoding);
	}

	public static void main(String args[]) {

		String key = "X^#R2N!21v^k^tio"; // X^#ReN!20v^k^ti5
		

		String values[] = new String[] { "1INX1661324621", "459900410000029", "5391321011273998", "2411", "867",	"KIM CHANGHO" };

		values = new String[] { "34psoyq7hSdS2fy2DL+Wng==", "y1JmZKqiJJ/UV5ZTwqXp4MwKUFIY8nvNvR\\/Xx7Fw0os=","ctsnvxndkeqxtefdnuzp6a==", "np4pt1xxnb3ovcwgdoo9fg==", "803d3hdyoulev5q2q\\/ittg==" };

		String encripted = null;
		for (int i = 0; i < values.length; i++) {

			try {

				encripted = SecureAlgCtr.encrypt(values[i], key);
				//System.out.println(SecureAlgCtr.decrypt(values[i], key));// + " : " + encripted + " : "+ " enc length :
				System.out.println( " encripted " +  encripted);
				//System.out.println(SecureAlgCtr.decrypt(values[i], key));// + " : " +
				// encripted + " : "+ " enc length : " + encripted.length());

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
					| UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
