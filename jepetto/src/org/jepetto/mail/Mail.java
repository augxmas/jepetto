package org.jepetto.mail;

import java.io.Serializable;

/**
 * 
 * java.mail.Message에 대한 wrapping 클래스 
 * 
 * @author umlkorea 김창호
 *
 */
public class Mail implements Serializable{
	/**
	 * mail server url
	 */
	private String host;
	
	/**
	 * mail server port
	 */
	private String port;
	
	/**
	 * 사용자 id
	 */
	private String id;
	
	/**
	 * 사용자 암호 
	 */
	private String password;
	
	/**
	 * 메일 제목
	 */
	private String subject;
	
	/**
	 * 메일 송신자
	 */
	private String from;
	
	/**
	 * 메일  수신자
	 */
	private String to;
	
	/**
	 * 메일 참조자
	 */
	private String cc;
	
	/**
	 * 메일 숨은 참조자
	 */
	private String bcc;

	/**
	 * 메일 내용
	 */	
	private String context;

	/**
	 * 첨부파일 갯수
	 */	
	private int attach;
	
	//private String path[];
	
	/**
	 * 첨부 파일 명
	 */
	private String files[];	
	
	/**
	 * Message가 wrapping 되어 ValueObject로 변환된 instance   
	 * 
	 * @param id		사용자 아이디
	 * @param pw		사용자 비밀번호
	 * @param sub		메일 제목
	 * @param from		메일 송신자
	 * @param to		메일 수신자
	 * @param cc		메일 참조자
	 * @param bcc		메일 숨은 참조자
	 * @param context	메일 내용
	 * @param files		첨부파일
	 */
	public Mail(String host, String port, String id, String pw, String sub, String from, String to, String cc, String bcc, String context, String files[]){
		this.host = host;
		this.port = port;
		this.id = id;
		this.password = pw;
		this.subject = sub;
		this.from = from;
		this.to = to;
		this.cc= cc;
		this.bcc = bcc;
		this.context = context;
		this.files = files;
	}
	
	public String getHost(){
		return host;
	}
	
	public String getPort(){
		return port;
	}
	
	public String getId(){
		return id;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getTo(){
		return to;
	}
	
	public String getCc(){
		return cc;
	}
	
	public String getBcc(){
		return bcc;
	}
	
	public String getContext(){
		return context;
	}
	
	public String[] getFiles(){
		return files;	
	}
	

}