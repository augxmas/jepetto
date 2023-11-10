package org.jepetto.sec;

public class PasswordGenerator {

	/**
	 * 
	 * 비밀번호를 주어진 자리에서 맞게 0~9a-zA~Z 형식으로 random으로 생성
	 * 
	 * @param length 비밀번호 자리 수
	 * @return
	 */
	public static String generatePasword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z','!','@','#','$','%','^','&','*'  };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}
		return sb.toString();
	}

	public static void main(String[] args) { 
		//파라미터 int값이 자리수 
		String password = PasswordGenerator.generatePasword(16); 
		System.out.println(password);
	}
}