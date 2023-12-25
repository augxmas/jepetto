package org.jepetto.sec;

//import org.apache.commons.codec.binary.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Base64;
import java.time.*;
/*
import java.text.SimpleDateFormat;
import java.util.Base64.Encoder;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.io.UnsupportedEncodingException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.HmacUtils;
//*/

public class AES256Cipher {

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    public static String AES_Encode(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException,
                        NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,	IllegalBlockSizeException, BadPaddingException {
        byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        //return Base64.encodeBase64String(cipher.doFinal(textBytes));
        return null;
    }

    public static String AES_Decode(String str, String key, byte[] ivBytes)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException,
                        NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //byte[] textBytes =  Base64.decodeBase64(str.getBytes());
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        //return new String(cipher.doFinal(textBytes), "UTF-8");
        return null;
    }

    public static String AES_Decode(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException,
                        NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,	IllegalBlockSizeException, BadPaddingException {
    return AES_Decode(str, key, ivBytes);
    }
    
    
//    public static String base64sha256(String message, String secretKey) {
//        String result = "";
//        try {
//             Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//             
//             SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
//             sha256_HMAC.init(secret_key);
//             
//             byte[] bt = sha256_HMAC.doFinal(message.getBytes());
//             Encoder encoder = Base64.getEncoder();
//             String hash = encoder.encodeToString(bt);
//             result = hash;
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                System.out.println("!! base64sha256 Error !!");
//            }
//        return result;
//    }
//    
//    public static String encrypt(final String keys, final String data) {
//        return toBase64String(HmacUtils.getHmacSha256(keys.getBytes()).doFinal(data.getBytes()));
//    }
//
//    public static String toBase64String(byte[] bytes) {
//        byte[] byteArray = org.apache.commons.codec.binary.Base64.encodeBase64(bytes);
//        return new String(byteArray);
//    }
    
    /**
     * @param args
     */
    public static void main(String args[]) {
        
        SHA256 sha256 = new SHA256();
        
        Instant instant = Instant.now();
        System.out.println(instant.toString());
        
        java.util.Date date = new Date();
        long time = date.getTime();
        String timeStr = time+"";
        
        String Identifier = "63d35a33360e2837676941a0";
        String OrgUnitId = "63d35a33f2ac6f13f3a2e8ab";
        String ApiKey = "e029ee1b-e811-4576-8ad7-d2a8bf6ec399";
        String Timestamp = null;//new org.joda.time.DateTime( org.joda.time.DateTimeZone.UTC ).toString();
        String TransactionId = "MTID"+time;
        
        System.out.println("timeStr:"+timeStr);
        System.out.println("Timestamp:"+Timestamp);
        System.out.println("TransactionId:"+TransactionId);
        
        //Identifier = "5fd11e066bb38432eb8364b4";
        //OrgUnitId = "5fd059d109c50b2d408f646c";
        //timeStr = "1547498533216";
        //TransactionId = "MTID-35a85034-f8da-45a7-8a72-eec01f63772e";
        //ApiKey = "13f1fd1b-XXXX-XXXX-XXXX-ca61878f2a44";
        
        String SignatureText = Timestamp+TransactionId;
        
        try {
/*                    
            String Signature = sha256.encrypt(SignatureText);
            byte[] encodedBytes = Base64.encodeBase64(Signature.getBytes());
            System.out.println("Signature:"+new String(encodedBytes));
*/            
            //System.out.println("base64sha256 test ----- " + base64sha256(SignatureText, ApiKey));
           
            //System.out.println("encrypt      test ----- " + encrypt(ApiKey, SignatureText)); 
            

            System.out.println("base64sha256 test ----- " + encode(SignatureText));
            
            //String originalInput = SignatureText;
            //byte[] targetBytes = originalInput.getBytes();
            
            // Base64 인코딩 ///////////////////////////////////////////////////
            //Encoder encoder = Base64.getEncoder();
            //byte[] encodedBytes = encoder.encode(targetBytes);
            
//            System.out.println("Signature:"+new String(encodedBytes));
            
//            String jwtId = "1234567890";
//            long ttlMillis = time;
//            String orderObject = "{\n"
//                    + "    \"AccountNumber\": \"4000000000001091\"\n"
//                    + "  }";
//            
            
//            GenerateJWT gJWT = new GenerateJWT();
//            String ss=gJWT.createJWT(jwtId, ttlMillis, orderObject);
//            System.out.println("ss:"+ss);              
            
        } catch (Exception e) {
                
        }

    	/*
    	String str = "DWAYdXOe5Et05CVTqQDevJFVK1rD/homRlCdu8ruNopxOsPOF/x1+aiShZxyu1wQ64FbhaHHtIiE1jWdIlRIrkmqknvRwA+PRB7jhMEuhDNeFy+ZEPxH46XNZSH259pZxLhRmjy8s/4FZJJLy6JSPqaiXqFV9deMusPWUSaGCY6FA3Gie/J0VFUoFTQ4nGKwNGIBJUgpA3+4DjxDiSjknW7t757M3adUz0HpS+iRnNyC8F02vOQL0MBATRl3UFCFRLBT7Vid91HsFH46Llrw3dnoqtGAjeMqSSvcvm4qHMxFzw1mGMSJegk+zDYQy3342755nKOi9zXOUOJIpx53WVEBfgamREA7ozXyQwyCmkehCXM9eEPiVKuJEieftse1aiVG7CjaWg+G1fwcgL35fBLUAyN68TBXGG5eB+qkmnzhohsvo0tqGxnOpl5N5xyXZO92HO0i7jnZQ1d0HH9CbKlAC/DioWlUfBjMaouStV8bCNdUY0pdbjbUhoktoiE2QISVyVU1x2Slfllas5fUqEijHWBLxW8lyjWv1/lM2OAHPZ4BRdEudcp0K1Mja39l4vjXqYrvauiZpXV5AAT6ZXjRAcjk7yWKUQxA6OIxjvA38UuUXBl1cLum/vE1KtrkLqbpaOPoAJChERM9VOs8fq+9ASXwfhrQVrOtpB2ycgwRAm5Qx9QrHs7hiVQQpw7AdjLdjQqcx1PdRwlv6TEVHA==";
    	              
    	
    	String key = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==".substring(0,32);
    	try {
			str = AES_Decode(str,key, ivBytes);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//*/
    	
    }
    
    public static String encode(final String clearText) throws NoSuchAlgorithmException {
        return new String(
                Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(clearText.getBytes(StandardCharsets.UTF_8))));
    }
    
}
    