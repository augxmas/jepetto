package org.jepetto.util;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.jepetto.exception.InvalidSizeException;
import org.jepetto.exception.NotFoundException;
import org.jepetto.exception.RequiredValueMissingException;
import org.jepetto.exception.UnMatchedPatternException;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.sec.SecureAlgCtr;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.finhub.controller.PaymentServlet;
//import com.finhub.controller.Transaction2Smartro4Web;
//import com.finhub.model.Constants;
//import com.finhub.model.Messages;

public class Validator {
	
	DisneyLogger cat = new DisneyLogger(Validator.class.getName());	

	//Category cat = DisneyLogger.getInstance(Validator.class.getName());
	
	// parsing & validation  
	private static JSONObject parsingNvalidation4json;
	private JSONObject parsedJSon;

	private int parsingStartIndex = 0;

	public Validator(String file)  {
		
		Reader reader = null;
		try {
			//cat.info("loading......Message...");
			reader = new FileReader(file);
			JSONParser parser = new JSONParser();			
			parsingNvalidation4json = (JSONObject)parser.parse(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		parsedJSon = new JSONObject();
	}
	
	
	public boolean isValidLength(String queryString) {
		String totalLength = "totallength"; 
		JSONObject obj = (JSONObject)parsingNvalidation4json.get(totalLength);
		long length		= (long)obj.get("length");
		return queryString.length() == length;
		
	}
	
	public JSONObject parse(String queryString, String parameter) {
		// reading parsing rule
		JSONObject obj			= (JSONObject)parsingNvalidation4json.get(parameter);
		long length				= (long)obj.get("length");
		String value = queryString.substring(parsingStartIndex, (int)length);
		parsingStartIndex += (int)length;
		parsedJSon.put(parameter, value);
		return parsedJSon;
	}
	
	

	/**
	 * key=value
	 * key占쎈퓠 筌욑옙占쎌젟占쎈┷占쎈선 占쎌뿳占쎈뮉 占쎌뿯占쎌젾揶쏉옙 value 揶쏉옙 占쎈솭占쎄쉘, 占쎈릊占쎌깈占쎌넅, null 占쎈연�겫占썹몴占� 筌욑옙占쎄텕�⑨옙 占쎌뿳占쎈뮉筌욑옙 占쎌넇占쎌뵥
	 * 
	 * @param parameter key揶쏉옙
	 * @param value
	 * @return
	 * @throws UnMatchedPatternException 
	 * @throws NotFoundException 
	 * @throws RequiredValueMissingException 
	 * @throws InvalidSizeException 
	 */
	public JSONObject isValid(String parameter, String value, String encriptKey) throws javax.crypto.BadPaddingException, UnMatchedPatternException, NotFoundException, RequiredValueMissingException, InvalidSizeException{

		// reading parsing rule
		JSONObject obj			= (JSONObject)parsingNvalidation4json.get(parameter);
		
		String required 		= (String)obj.get("required");
		String pattern			= (String)obj.get("pattern");
		String enc					= (String)obj.get("enc");
		
		int valueLength = 0;
		if(required.equalsIgnoreCase("Y")) {
			try {
				valueLength = value.length();
			}catch(java.lang.NullPointerException e) {
				throw new RequiredValueMissingException(e);
			}
		}

		
		String length			= null;
		int _length = 0;
		try{
			length = (String)obj.get("length");
			_length = Integer.parseInt(length) ;
			if( _length< valueLength) {
				throw new InvalidSizeException(_length, valueLength);
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		

		
		boolean flag = false;


		
		
		try {
			if(enc.equalsIgnoreCase("y")) {
				// changing to parsing result
				value = encrypted(value, encriptKey);
			}
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			return obj;
		}
		
		Pattern p = Pattern.compile(pattern);		
		Matcher matcher = null;
		
		try{
			matcher = p.matcher(value);
			flag = matcher.find();
			
			if(!flag) {
				//throw new UnMatchedPatternException("501","not matched pattern");
				throw new UnMatchedPatternException();
			}
		}catch(NullPointerException e) {
			throw new NotFoundException(e,value);
		}
		
		obj.put("value",value);
		obj.put("lenght",length);
		
		return obj;
	}
	
	/*
	public boolean isCorrectEncrypted(String value, String encriptKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		boolean flag = true;
		String descripted = SecureAlgCtr.decrypt(value,encriptKey);
		parsedJSon.put(Constants.SECRETKEY, descripted);
		return flag;
	}
	//*/
	
	public String encrypted(String value, String encriptKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NullPointerException,IllegalArgumentException {

		String decripted = null;
		try{
			decripted = SecureAlgCtr.decrypt(value,encriptKey);
			
			//parsedJSon.put(Constants.SECRETKEY, decripted);
		}catch(NullPointerException e) {
			throw e;
		}catch(javax.crypto.BadPaddingException e) {
			throw e;
		}catch(java.lang.IllegalArgumentException e) {
			throw e;
		}
		
		return decripted;

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reader reader = null;
		try{
			reader = new FileReader("c:\\tomcat\\conf\\payment.json");
			
			JSONParser parser = new JSONParser(); 
			JSONObject json =(JSONObject)parser.parse(reader);
			JSONObject obj = (JSONObject)json.get("version");
			//System.out.println(obj.get("length"));
			//System.out.println(obj.get("format"));
			//System.out.println(obj.get("required"));
			
			JSONObject o = new JSONObject();
			o.put("name", "jkim");
			//System.out.println(o.toJSONString());
			//System.out.println(o.toString());
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Pattern p = Pattern.compile("^[0-9]{10}$");
		
		Matcher matcher = p.matcher("1234567890");
		//System.out.println(matcher.find());

		matcher = p.matcher("1234567890");
		//System.out.println(matcher.find());

		//占쎈떭占쎌쁽, �눧紐꾩쁽 占쎈７占쎈맙 8占쎌쁽�뵳占� 占쎄텢占쎌뵠 
		p = Pattern.compile("^[a-zA-Z0-9]{8}$");
		matcher = p.matcher("asd@2345");
		System.out.println("8占쎌쁽�뵳占� : " + matcher.find());

		
		// 5占쎌쁽�뵳�딅퓠占쎄퐣 8占쎌쁽�뵳占� 占쎄텢占쎌뵠
		p = Pattern.compile("^[a-zA-Z0-9]{5,8}$");
		matcher = p.matcher("asd");
		System.out.println("5-8占쎌쁽�뵳占� : " + matcher.find());
		
		// MM(占쎌뜞) 01 ~ 12
		String mm = "09";
		p = Pattern.compile("(0[1-9]|1[0-2])");
		matcher = p.matcher(mm);
		//System.out.println("mmm " + matcher.find());
		
		// yy(占쎈��) 20 ~ 99占쎈�덃틦�슣占�
		String yy = "20";
		p = Pattern.compile("([2-9][0-9])");
		matcher = p.matcher(yy);
		//System.out.println("yy " + matcher.find());
		
		
		String yymm = "2613";
		p = Pattern.compile("([2-9][0-9]0[1-9]|1[0-2])");
		matcher = p.matcher(yymm);
		System.out.println("yymm " + matcher.find());
		
		
		// 占쎈떭占쎄텢 4占쎌쁽�뵳占�
		String val = "1234..";
		p = Pattern.compile("^[0-9]{4}$");
		matcher = p.matcher(val);
		//System.out.println(val + " : " + matcher.find());
		
		// 占쎈꺖占쎈떭占쎌젎 占쎌쁽�뵳�딅땾
		val = "1223";
		p = Pattern.compile("[0-9]*\\.[0-9]{2}");
		matcher = p.matcher(val);
		//System.out.println("占쎈꺖占쎈땾占쎌젎 占쎌쁽�뵳占� " + val + " : " + matcher.find());

		// email pattern
		val = "augxmas@gmail.com";
		p = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
		matcher = p.matcher(val);
		//System.out.println(val + " : " + matcher.find());

		val = "http://monorama.kr";
		p = Pattern.compile("^((http(s?))\\:\\/\\/)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$");
		matcher = p.matcher(val);
		//System.out.println(val + " : " + matcher.find());
		
		//System.out.println(System.currentTimeMillis());
		
		//Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
		
		
		val = "1974-10-18";
		p = Pattern.compile("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");
		matcher = p.matcher(val);
		System.out.println(val + " : " + matcher.find());

		val = "JCB";
		p = Pattern.compile("^(VISA|MASTER|JCB)");
		matcher = p.matcher(val);
		System.out.println(val + " : " + matcher.find());
		
		val = "JPY";
		p = Pattern.compile("^(USD|EUR|CNY|JPY|KRW)");
		matcher = p.matcher(val);
		System.out.println(val + " 占쎌넎占쎌몛 : " + matcher.find());
		
		
		val = "255.244.255.255";
		p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
		matcher = p.matcher(val);
		System.out.println(val + " ipv4 " + " : " + matcher.find());
		
		val = "255.255.255.255.255.255";
		p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$");
		matcher = p.matcher(val);
		System.out.println(val + " ipv6 " + " : " + matcher.find());
		
		val = "255.255.255.255.256";
		p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$");
		matcher = p.matcher(val);
		System.out.println(val + " ipv6 " + " : " + matcher.find());

		val = "20210823235959";
		p = Pattern.compile("([2]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[0-9]|1[0-9]|2[0-3])(0[0-9]|[1-5][0-9])(0[0-9]|[1-5][0-9]))");
		matcher = p.matcher(val);
		System.out.println(val + " yyyymmddhhMMss " + " : " + matcher.find());
		
		val = "UTC+23:59";
		p = Pattern.compile("[UTC][+|-](0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])");
		matcher = p.matcher(val);
		System.out.println(val + " utc~ " + " : " + matcher.find());

		val = "mwéliTidjine";
		p = Pattern.compile("^[a-zA-Z0-9]{10,18}$");
		matcher = p.matcher(val);
		System.out.println("name pattern " + val + " name " + " : " + matcher.find());
		
		val = "01026854082";
		p = Pattern.compile("^01([0|1])-?([0-9]{3,4})-?([0-9]{4})$");
		matcher = p.matcher(val);
		System.out.println(val + " phone number " + " : " + matcher.find());
		
		val = "A1";
		p = Pattern.compile("^(A1)");
		matcher = p.matcher(val);
		System.out.println(val + " version valid ? " + " : " + matcher.find());		

		long l = System.currentTimeMillis();
		
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyMMddHHmmSSS");
		String transId = f.format(today);
		System.out.println(transId+ " : " + transId.length());
		
		val = "/giMDw1PfeXTHeLoExWdXg==";
		p = Pattern.compile("^.*(?=^.{1,100}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=/]).*$");
		matcher = p.matcher(val);
		System.out.println(val + " holdername " + " : " + matcher.find());
		
		val = "0100";
		p = Pattern.compile("^(0200)");
		matcher = p.matcher(val);
		System.out.println(val + " version " + " : " + matcher.find());

		val = "@234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		val += "가234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";		
		val += "a234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		
		p = Pattern.compile("^.{0,300}$");
		matcher = p.matcher(val);
		System.out.println(val + " remark " + " : " + matcher.find());

        val = "1200";
        p = Pattern.compile("^[0-9]{3}$|USD|EUR");
        matcher = p.matcher(val);
        System.out.println(val + " usd for cancel " + " : " + matcher.find());
		
		

		
		
	}
	

	private String getCardNumber(String cardNumber, String encriptKey) {
		try {
			cardNumber = SecureAlgCtr.decrypt(cardNumber, encriptKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
			System.out.println(cardNumber + " : " + encriptKey + " " + encriptKey.length());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cardNumber;
	}

	private String getCVC(String cvc, String encriptKey) {
		try {
			cvc = SecureAlgCtr.decrypt(cvc, encriptKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
			System.out.println(cvc + " : " + encriptKey + " " + encriptKey.length());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cvc;
	}
	
	private String getExpiry(String expiry, String encriptKey) {
		try {
			expiry = SecureAlgCtr.decrypt(expiry, encriptKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
			System.out.println(expiry + " : " + encriptKey + " " + encriptKey.length());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return expiry;
	}
	
	

}
