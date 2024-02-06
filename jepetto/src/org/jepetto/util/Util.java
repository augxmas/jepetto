package org.jepetto.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class Util {

	public static String url(javax.servlet.http.HttpServletRequest req, String key) {
		String value = null;
		String url = "";
		try {
			value = (String) req.getAttribute(key);
			if (value.length() != 0) {
				url += "&" + key + "=" + value;
			}
		} catch (NullPointerException e) {
			url = "$1=1";
		}
		return url;
	}

	/**
	 * 
	 * ISO-8859-1 -> euc-kr으로 변경
	 * 
	 * @param s
	 * @return
	 */
	public static String utf8(String s) {
		String str = null;
		try {

			str = new String(s.getBytes("8859_1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static String url(String key, String value) {
		String url = "";
		try {
			StringBuffer buffer = new StringBuffer("&");
			buffer.append(key);
			buffer.append("=");
			buffer.append(value.trim());
			url = buffer.toString();
		} catch (java.lang.NullPointerException e) {

		}
		return url;
	}

	public static String[] getSplitedStringArr(String str, String delim) {
		String arr[] = null;
		if (delim == null) {
			delim = "|";
		}
		java.util.StringTokenizer stk = new java.util.StringTokenizer(str, delim);
		Vector v = new Vector();
		while (stk.hasMoreTokens()) {
			String s = stk.nextToken();
			v.addElement(s.trim());
		}
		arr = new String[v.size()];
		v.copyInto(arr);
		return arr;
	}

	/**
	 * 문자열을 quotation으로 감싼다
	 * 
	 * @param s
	 * @return
	 */
	public static String quote(String s) {
		StringBuffer sb = new StringBuffer("'");
		sb.append(s);
		sb.append("'");
		return sb.toString();
	}

	/**
	 * 주어진 값으로 query를 초기화 한다
	 * 
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String initQuery(String prefix, String suffix) {
		String str = null;
		try {
			if (prefix.trim().length() == 0) {
				str = "";
			} else {
				str = suffix;
			}

		} catch (Exception e) {
			str = "";
		}
		return str;
	}

	/**
	 * 
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		boolean flag = false;
		try {
			if (s.trim().length() == 0) {
				flag = true;
			}
		} catch (NullPointerException e) {
			flag = true;
		}
		return flag;
	}

	public static String initValue(String s, String value) {
		if (isNull(s)) {
			return value;
		}
		return s;
	}

	public static String append(StringBuffer sb, String s) {
		return sb.append(s).toString();
	}

	public static String append(String s, String s2) {
		StringBuffer sb = new StringBuffer(s);
		return sb.append(s2).toString();
	}

	/**
	 * 오늘날짜, 시간 가져오기 (Format : YYYYMMDD, HH24MISS) type "D" : 날짜 type "T" : 시간 type
	 * "A" : 날짜 + 시간
	 * 
	 * @param String type <code>날짜를 가져올지 시간을 가져올지</code>
	 * @return String
	 */
	public static String getToDay(String type) {
		Calendar now = Calendar.getInstance();
		String year = null;
		String month = null;
		String day = null;
		String hour = null;
		String minute = null;
		String second = null;

		year = String.valueOf(now.get(Calendar.YEAR));
		month = String.valueOf((now.get(Calendar.MONTH) + 1));
		day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(now.get(Calendar.MINUTE));
		second = String.valueOf(now.get(Calendar.SECOND));

		if (month.length() == 1)
			month = "0" + month;
		if (day.length() == 1)
			day = "0" + day;
		if (hour.length() == 1)
			hour = "0" + hour;
		if (minute.length() == 1)
			minute = "0" + minute;
		if (second.length() == 1)
			second = "0" + second;

		if (type.equals("D"))
			return year + month + day;
		else if (type.equals("T"))
			return hour + minute + second;
		else if (type.equals("X"))
			return year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
		else
			return year + month + day + hour + minute + second;
	}

	/**
	 * 입력받은 날짜를 년,월,일로 파싱해 정수형으로 변경한 후 리턴한다.
	 * <p>
	 * 
	 * @param dateStr <code>입력 날짜</code><br>
	 * @return <code>생성된 int형 1차원 배열(각 필드순으로 년, 월, 일)</code>
	 * @author pronema
	 */
	public static int[] makeDateFactor(String dateStr) {
		int result[] = null;

		String tempStr = "";

		if (dateStr != null && !dateStr.equals("") && dateStr.length() == 8) {
			result = new int[3];

			result[0] = Integer.parseInt(dateStr.substring(0, 4));
			result[1] = Integer.parseInt(dateStr.substring(4, 6));
			result[2] = Integer.parseInt(dateStr.substring(6, 8));
		}

		return result;
	}

	public static String[] split(String str, String delim) {
		String arr[] = str.split(delim);
		return arr;
	}

	/**
	 * 파일의 존재 여부
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	public static boolean exists(String path, String name) {
		File f = new File(path, name);
		return f.exists();
	}

	public static boolean delete(String path, String name) {
		if (exists(path, name)) {
			File f = new File(path, name);
			return f.delete();
		}
		return false;
	}

	/**
	 * 
	 * @param res
	 * @param path
	 * @param name
	 * @throws IOException
	 */
	public static void download(HttpServletResponse res, String path, String fileName, String realName)
			throws IOException {

		OutputStream out = null;
		InputStream in = null;

		try {
			res.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
			res.setHeader("Content-Disposition", "attachment;filename=" + realName + ";");

			out = res.getOutputStream();
			in = getInputStream(path, fileName);

			copy(in, out);

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
			try {
				in.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 
	 * @param path
	 * @param renamed
	 * @return
	 * @throws IOException
	 */
	public static InputStream getInputStream(String path, String renamed) throws IOException {
		File file = new File(path, renamed);
		InputStream in = new FileInputStream(file);
		return in;
	}

	/**
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {

		DataInputStream dis = new DataInputStream(in);
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

		try {
			boolean flag = true;
			while (flag) {
				try {
					byte b = dis.readByte();
					baos.write(b);
				} catch (EOFException e) {
					flag = false;
				}
			}
			byte arr[] = baos.toByteArray();
			out.write(arr);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void write(String path, String file, byte contents[]) throws IOException {
		FileOutputStream fin = new FileOutputStream(new File(path, file));
		try {
			fin.write(contents);
		} finally {
			fin.close();
		}
	}

	/**
	 * 
	 * instance를 주어진 경로에 클래스명으로 해서 저장한다
	 * 
	 * @param o
	 * @param path
	 * @throws IOException
	 */
	public static void writeObject(Object o, String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			file = new File(path, o.getClass().getName());
		} else {
			file.mkdirs();
			file = new File(path, o.getClass().getName());
		}

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(o);
		} catch (IOException e) {
			throw e;
		} finally {
			oos.close();
		}
	}

	public static Object readObject(Object o, String path) throws IOException, ClassNotFoundException {

		File file = new File(path, o.getClass().getName());
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {
			fin = new FileInputStream(file);
			ois = new ObjectInputStream(fin);
			o = ois.readObject();
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			try {
				fin.close();
			} catch (Exception e) {
			}
			try {
				ois.close();
			} catch (Exception e) {
			}
		}
		return o;

	}

	/**
	 * 
	 * 
	 * @param ko
	 * @return
	 */
	public static String en(String ko) {
		String en = null;
		try {
			en = new String(ko.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return en;
	}

	public static String ko(String en) {
		String ko = null;
		try {
			ko = new String(en.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ko;
	}
	/*
	 * public static String unicode(String en){ String ko=null; try{ //ko=new
	 * String(en.getBytes("8859_1"),"euc-kr"); ko=new
	 * String(en.getBytes("euc-kr"),"8859_1"); }catch(UnsupportedEncodingException
	 * e){ e.printStackTrace(); } return ko; }
	 */

	public static String unicode(String en) {
		String ko = null;
		try {
			ko = new String(en.getBytes("8859_1"), "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ko;
	}

	public static String en_ko(String str) {
		return en(ko(str));
	}

	public static String ko_en(String str) {
		return ko(en(str));
	}

	/**
	 * 해당 명령어를 실행하고, 실행에 필요한 파라미터를 전달한다. 결과의 출력을 위한 outputStream를 참조한다
	 * 
	 * @param cmd
	 * @param arr
	 * @param out
	 */
	public static void exec(String cmd, String arr[], OutputStream out) {
		Runtime run = Runtime.getRuntime();
		Process pro = null;
		InputStream in = null;

		int i = 0;

		try {
			pro = run.exec(cmd, arr);
			// System.out.println(pro.waitFor());
			in = pro.getInputStream();

			int count = 0;
			while ((i = in.read()) != -1) {

				out.write(i);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 주어진 명령에 파라미터를 전달하여 실해하고 ,결과를 outputstream으로 출력한다. outputstream을 close할 지 여부를
	 * 결정한다
	 * 
	 * @param cmd
	 * @param arr
	 * @param out
	 * @param closed
	 * @return
	 */
	public static OutputStream exec(String cmd, String arr[], OutputStream out, boolean closed) {

		Runtime run = Runtime.getRuntime();
		Process pro = null;
		InputStream in = null;

		int i = 0;

		try {

			if (arr == null) {
				pro = run.exec(cmd);
			} else {
				pro = run.exec(cmd, arr);
			}

			in = pro.getInputStream();

			while ((i = in.read()) != -1) {
				out.write(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				if (closed) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
		return out;

	}

	/**
	 * 문자열의 길이가 size크기가 되도록 '0'을 문자열앞에 덧붙인다.
	 *
	 * @param str
	 * @param size
	 * @return
	 */

	public static String addZeroAsPrefix(String str, int size) {
		String result = "";

		if (size < str.length())
			return str;

		for (int i = 0; i < size - str.length(); i++)
			result = result + "0";

		return result + str;
	}

	/**
	 * 최대 길이 수보다 큰 문자열인 경우에 뒷부분을 "..."로 변환한다.
	 *
	 * @param text      주어진 문자열
	 * @param maxLength 최대 문자열 길이
	 * @return String 처리된 문자열
	 */

	public static String textMore(String str, int limit) {
		if (str == null || limit < 4)
			return str;

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			if (str.charAt(index++) < 256) // 1바이트 문자라면...
				cnt++; // 길이 1 증가
			else { // 2바이트 문자라면...

				if (cnt < limit - 3) {
					cnt += 2; // 길이 2 증가
				} else {
					break;
				} // 여기까지.
			}
		}

		if (index < len)
			str = str.substring(0, index).concat("...");

		return str;
	}

	public static String currenyMask(String value, Locale locale) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		String formattedMoney = currencyFormatter.format(Integer.parseInt(value));
		return formattedMoney;
	}

	public static String currenyMask(long value, Locale locale) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		String formattedMoney = currencyFormatter.format(value);
		return formattedMoney;
	}

	public static String currenyMask(double value, Locale locale) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		String formattedMoney = currencyFormatter.format(value);
		return formattedMoney;
	}

	/**
	 * 
	 * 주어진 경로의 디렉토리를 생성한다
	 * 
	 * @param path
	 * @return
	 */
	public static boolean mkdir(String path) {
		File file = new File(path);
		return file.mkdir();
	}

	public static double round(double f, int position) {

		String s = String.valueOf(f);
		int index = s.indexOf(".");
		// String prefix = s.substring(0,index+1);
		// String suffix = null;

		try {
			s = s.substring(0, index + position + 1);// , index+3);
		} catch (Exception e) {
			s = s.substring(0, index + position);
		}

		return Double.parseDouble(s);

	}// */

	public static String convertNumber2Hangul(int num) {
		String money = num + "";
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		String[] han2 = { "", "십", "백", "천" };
		String[] han3 = { "", "만", "억", "조", "경" };
		StringBuffer result = new StringBuffer();
		int len = money.length();

		for (int i = len - 1; i >= 0; i--) {
			result.append(han1[Integer.parseInt(money.substring(len - i - 1, len - i))]);
			if (Integer.parseInt(money.substring(len - i - 1, len - i)) > 0)
				result.append(han2[i % 4]);
			if (i % 4 == 0)
				result.append(han3[i / 4]);
		}
		return result.toString();
	}

	public static String fileToBase64(String path, String file) throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(path, file));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		return encodedString;
	}

	public static boolean base64ToFile(String path, String fileName, String encodedStr)
			throws IOException, DocumentException {
		Document document = new Document();
		OutputStream output = null;
		boolean isFail = false;
		try {
			output = new FileOutputStream(new File(path, fileName));
			PdfWriter.getInstance(document, output);
			byte[] bytes = Base64.getDecoder().decode(encodedStr);
			output.write(bytes);
			isFail = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isFail;

	}

	public static String inputStreamToString(InputStream in) throws IOException {
		String value = "";
		InputStreamReader isr = null;
		BufferedReader reader = null;
		isr = new InputStreamReader(in);
		reader = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		try {
			while (value != null) {
				buffer.append(value + "\n");
				value = reader.readLine();
				// System.out.println(value);
			}
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				isr.close();
			} catch (Exception e) {
			}
			try {
				reader.close();
			} catch (Exception e) {
			}

		}
		value = buffer.toString();
		return value;
	}

	public static void zip(String srcDir, String targetDir, String zipFileName) throws IOException {

		String zipFileEncoding = "UTF-8";

		File dir = new File(srcDir);
		FileInputStream fis = null;

		File zipFile = new File(targetDir, zipFileName);
		FileOutputStream fos = null;
		ZipArchiveOutputStream zos = null;

		try {
			fos = new FileOutputStream(zipFile);
			zos = new ZipArchiveOutputStream(fos);
			zos.setEncoding(zipFileEncoding);

			byte buffer[] = new byte[1024];
			File files[] = dir.listFiles();
			int len = 0;
			for (int i = 0; i < files.length; i++) {
				try {
					fis = new FileInputStream(files[i]);
					zos.putArchiveEntry(new ZipArchiveEntry(files[i].getName()));
					while ((len = fis.read(buffer, 0, buffer.length)) != -1) {
						zos.write(buffer, 0, len);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				} finally {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} // end of for-loop
			zos.closeArchiveEntry();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
		
		String srcDir = args[0];
		String targetDir = args[1];
		String zipFileName = args[2];
		
		try {
			zip(srcDir, targetDir, zipFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		String command = "curl -v http://www.naver.com";
		command = "curl -Ikv http://www.naver.com"; // getting
		String delim = " ";
		ProcessBuilder processBuilder = new ProcessBuilder(command.split(delim));
		// processBuilder = processBuilder.directory(new File("c:/Users/iiwoo"));

		InputStream in = null;

		String value = "";
		int exitCode = 0;
		try {
			Process process = Runtime.getRuntime().exec(command);
			in = process.getInputStream();
			value = inputStreamToString(in);
			// process.w
			exitCode = process.exitValue();
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(value);
		//*/
		
	}


	
}
