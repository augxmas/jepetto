package org.jepetto.mail;



import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.proxy.HomeProxy;
import org.jepetto.sql.Wrapper;
import org.jepetto.util.Util;




/*
 * mail sending wrapping class
 * properties �꽕�젙 �븘�슂
 * mail.smtp.host
 * use case
 * 			String to  = "wefuture@yahoo.co.kr";//,sbang123@shinbiro.com";
 * 			String cc  = "kimdoyle@nate.com,chkim@umlkorea.com";
 * 			String bcc = "wefuture@nate.com";//,sbang123@shinbiro.com";
 * 			String ko = "�굹�옃 留먯떥誘� <b>�뱯援��뿉 �떖�씪</b> 臾몄옄�� �꽌濡� �궗留쏅씈 �븘�땲 �븷 �럡";
 * 			String en = "�궡 �씠瑜� �뼱�뿬�굪 �뿬寃� �깉濡� twentity"; // use 8 bit ko_en
 * 			String text = ko + "\n" + en +"\n"+"嫄� �븳援�";
 * 			String sub = "硫붿씪蹂대궡湲�";
 * 			int filesize =1;
 * 			SendMailFacade m = new SendMailFacade("energy","mailenergy",sub,"h5104381@kepco.co.kr",to,cc,bcc,text,filesize);
 * 			m.setHeader(null,null);
 * 			m.setFileDataSource( "C:/ant/lib","ant.jar","ant.jar",0);
 * 			m.send();
 * 
 * 
 * @date 2004. 8. 18.
 * @version
 * @since
 * @author 源�李쏀샇
 * copyright UMLKOREA Co,Ltd
 */


public class SendMailFacade
{
	
	
	DisneyLogger cat = new DisneyLogger(SendMailFacade.class.getName());
	
	private MimeMessage msg;
	//private static PropertyReader reader = PropertyReader.getInstance();
	private MimeBodyPart mbp;
	private MimeBodyPart mbps[];
	private MimeMultipart mmp;
	private String id;
	private String passwd;
	private String host;
	private int size;
	private String to;
	private String cc;
	private String bcc;
	private String from;
	private String text;
	private String subject;
	private Transport transport;
	
	
	//private static DailyLog log = DailyLog.getInstance();
	
	private int volumn;
	
	/**
	 *
	 *@param id 硫붿씪 怨꾩젙�쓽 �븘�씠�뵒
	 *@param passwd 硫붿씪 怨꾩젙�쓽 �뙣�뒪�썙�뱶
	 *@param size 泥⑤� �뙆�씪�쓽 媛쒖닔
	 *@throws IOException
	 *@throws NoSuchProviderException
	 *@throws MessagingException
	 */
	public SendMailFacade(String host, String port,String id, String passwd, String subject, String from , String to, String cc, String bcc, String text,int size) throws IOException,NoSuchProviderException,MessagingException
	{
		init(host,port,id,passwd,subject,from,to,cc,bcc,text,size);
	}
	
	/**
	 * 二쇱뼱吏� 蹂��닔濡� �넚�떊�븷 硫붿씪�쓣 珥덇린�솕 �븳�떎.
	 * 硫붿씪 �꽌踰꾩쓽 �솚寃쎌뿉 �뵲�씪, �룷瑜댄띁�떚瑜� �닔�젙�빐�빞 �븳�떎.
	 * @param id 
	 * @param passwd
	 * @param subject �젣紐�
	 * @param from �넚�떊�옄
	 * @param to �닔�떊�옄
	 * @param cc 李몄“
	 * @param bcc �닲�쓬李몄“
	 * @param text �궡�슜
	 * @param size 泥⑤��뙆�씪 媛��닔
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public void init (String host, String port, final String id, final String passwd, String subject, String from , String to, String cc, String bcc, String text,int size) throws IOException,NoSuchProviderException,MessagingException {
		// �봽濡쒗띁�떚 珥덇린�솕
		Properties props = new Properties();
		
		// stmp 瑜� 珥덇린�솕
		props.put("mail.smtp.host", host);
		props.put("mail.port", port );
		props.put("mail.smtp.port", port );
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		


		
		Session session = Session.getDefaultInstance(props, null);
		
		session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(id, passwd);
                    }
                });

        		
		// �꽭�뀡�쓽 �뵒踰꾧퉭 �솕硫� 異쒕젰
		session.setDebug(true);
		
		//硫붿씪 �쟾�넚 怨꾩링 痍⑤뱷
		transport = session.getTransport("smtp");
		
		
		
		// 泥⑤� �뙆�씪 泥섎━媛� 媛��뒫�븳 mime message瑜� �깮�꽦
		// 硫붿씪 �떦 �븯�굹�쓽 mime message媛� �븘�슂
		createMimeMessage(session);
		
		// 硫붿씪 �궡�슜�쓣 �떞蹂댄븷 �닔 �엳�뒗 湲곕낯 MimeBodyPart �깮�꽦
		setMimeBodyPart();
		
		// 泥⑤� �뙆�씪�쓽 媛쒖닽 留뚰겮�쓽 MimeBodyPart �깮�꽦
		if( size > 0){
			mbps = new MimeBodyPart[size];
			for( int i = 0 ; i < mbps.length ; i++){
				mbps[i] = createMimeBodyPart();
			}
		}
		
		//MimeBodyPart�뿉���븳 Container �깮�꽦
		createMimeMultipart();
		this.host = host;
		this.id = id;
		this.passwd = passwd;
		this.size = size;
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.text = text;
		this.subject = subject;
		
		setFrom();
		setSubject();
		//setText(text,0);
		//setText();
		setText(mbp,text); //2010.10.28 諛뺤��쁺 (HTML�삎�떇�쑝濡� 泥⑤��뙆�씪 �궗�슜�븯湲� �쐞�븿)
		setRecipients();
		
		if( cc != null){
			setRecipientsCC();
		}
		
		if( bcc != null ){
			setRecipientsBCC();
		}
		setContent();
		//this.setHtmlContent();
		
		setSentDate();
		append();
		
	}
	
	/**
	 * 二쇱뼱吏� mail instance濡� 硫붿씪 怨꾩젙�쓣 珥덇린�솕 �븯�뿬 媛앹껜 �깮�꽦
	 * @param m jepetto.mail.Mail instance
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public SendMailFacade(Mail m) throws IOException,NoSuchProviderException,MessagingException
	{
		String host = m.getHost();
		String port = m.getPort();
		String id = m.getId();
		String passwd = m.getPassword();
		String subject = m.getSubject();
		String from = m.getFrom();
		String to = m.getTo();
		String cc = m.getCc();
		String bcc = m.getBcc();
		String text = m.getContext();
		int size = -1;
		try
		{
			size = m.getFiles().length;
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			size = -1;
			//log.info("no attach files");
		}
		init(host,port,id,passwd,subject,from,to,cc,bcc,text,size);
	}
	
	/**
	 * 硫붿씪 �븘�씠�뵒瑜� �븷�떦�븳�떎
	 * @param id
	 */
	public void setId(String id){ this.id = id; }
	
	/**
	 * 硫붿씪 鍮꾨�踰덊샇瑜� 珥덇린�솕 �븳�떎.
	 * @param passwd
	 */
	public void setPasswd(String passwd){ this.passwd = passwd; }
	
	/**
	 * 二쇱뼱吏� �꽭�뀡�쓣 媛�吏�怨� MimeMessage瑜� �깮�꽦
	 * @param session
	 * @return MimeMessage
	 */
	
	private MimeMessage createMimeMessage(Session session){
		msg = new MimeMessage(session);
		return msg;
	}
	
	/**
	 * MimeBodyPart 瑜� �깮�꽦,泥⑤� �뙆�씪�쓽 媛��닔 留뚰겮 �샇異쒕맂�떎
	 * @return MimeBodyPart
	 */
	
	private MimeBodyPart createMimeBodyPart(){
		return new MimeBodyPart();
	}
	
	/**
	 * 硫붿씪 �궡�슜�쓣 �떞蹂댄븷 �닔 �엳�뒗 湲곕낯 MimeBodyPart
	 */
	
	private void setMimeBodyPart(){
		mbp = new MimeBodyPart();
	}
	
	/**
	 * MimeBodyPart�뿉 ���븳 而⑦뀒�씠�꼫
	 * @return MimeMultipart
	 */
	
	public MimeMultipart createMimeMultipart(){
		mmp = new MimeMultipart();
		return mmp;
	}
	
	/**
	 * 二쇱뼱吏� MimeBodyPart�뿉 硫붿씪 �궡�슜�쓣 �븷�떦�븳�떎
	 * @param mbp
	 * @param text
	 * @throws MessagingException
	 */
	private void setText(MimeBodyPart mbp, String text) throws MessagingException{
		mbp.setContent(text,"text/html; charset=\"euc-kr\""); 
	}
	
	/**
	 * 二쇱뼱吏� index�뿉 �빐�떦�븯�뒗 MimeBodyPart�뿉 硫붿씪 �궡�슜�쓣 珥덇린�솕 �븳�떎.
	 * @param text
	 * @param index
	 * @throws MessagingException
	 */
	private void setText(String text,int index) throws MessagingException{
		mbps[index].setText(text);
	}
	
	/**
	 * 湲곕낯 MimeBodyPart �뿉 �궡�슜 �쟻�슜
	 * @param text
	 * @throws MessagingException
	 */
	
	private void setText(String text) throws MessagingException{
		mbp.setText(text);
	}
	
	private void setText() throws MessagingException{
		mbp.setText(text);
	}
	
	/**
	 *硫붿씪 �넚�떊�옄瑜� �븷�떦�븳�떎
	 *@param from
	 *@throws AddressException
	 *@throws MessagingException
	 */
	
	private void setFrom(String from) throws AddressException,MessagingException{
		msg.setFrom(new InternetAddress(from));
	}
	
	private void setFrom() throws AddressException,MessagingException{
		msg.setFrom(new InternetAddress(from));
	}
	
	/**
	 * 硫붿씪 �젣紐⑹쓣 �븷�떦�븳�떎
	 * @param subject
	 * @throws MessagingException
	 */
	private void setSubject(String subject) throws MessagingException{
		msg.setSubject(subject);
	}
	
	private void setSubject() throws MessagingException{
		
		try {
			msg.setSubject(MimeUtility.encodeText(subject, "euc-kr", "B"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MimeBodyPart�뿉 二쇱뼱吏� �뙆�씪紐낆쑝濡� 泥⑤��뙆�씪�쓣 泥⑤��븳�떎
	 * @param mbp
	 * @param filename
	 * @throws MessagingException
	 */	
	private void setFileDataSource(MimeBodyPart mbp,String filename) throws MessagingException{
		FileDataSource fds = new FileDataSource(filename);
		mbp.setDataHandler( new DataHandler(fds) );
		// �뿤�뜑�뿉 �뙆�씪�씠由꾩쓣 �꽭�똿�븷�븣 base64�삎�깭濡� 諛섎뱶�떆.
		//mbp2.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
		mbp.setFileName(fds.getName());
	}
	
	/**
	 * 二쇱뼱吏� �뙆�씪�쓣 �빐�떦 �씤�뀓�뒪�뿉 �빐�떦�븯�뒗 MimeBodyPart�뿉 泥⑤��떆�궡
	 * (以묒슂) �뙆�씪紐낆� base64濡� �씤肄붾뵫
	 * @param filenam
	 * @param index
	 * @throws MessagingException
	 */
	
	public void setFileDataSource(String filename , String realfilename , int index) throws MessagingException,IOException{
		filename = Util.ko_en(filename);
		FileDataSource fds = new FileDataSource(filename);
		File file = fds.getFile();
		
		mbps[index].setHeader("Content-Type", "multipart/mixed");
		mbps[index].setDataHandler(new DataHandler(fds));
		mbps[index].setFileName(MimeUtility.encodeText(realfilename, "euc-kr","B"));
	}
	
	/**
	 * 二쇱뼱吏� 寃쎈줈�쓽 �뙆�씪紐낆뿉 �빐�떦�븯�뒗 �뙆�씪�쓣 二쇱뼱吏� index�뿉 �빐�떦�븯�뒗 MimeBodypart�뿉 泥⑤��떆�궡
	 * @param path
	 * @param filename
	 * @param realfilename
	 * @param index
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void setFileDataSource(String path, String filename , String realfilename , int index) throws MessagingException,IOException{
		filename = Util.ko_en(filename);
		File file = new java.io.File(path,filename);
		FileDataSource fds = new FileDataSource( file );
		
		System.out.println(file.exists());
		mbps[index].setHeader("Content-Type", "multipart/mixed");
		mbps[index].setDataHandler(new DataHandler(fds));

		
		//mbps[index].setFileName(MimeUtility.encodeText(realfilename, "euc-kr","Q"));
		mbps[index].setFileName(realfilename);
		
		//mbps[index].attachFile(file);
	}
	
	/**
	 * Message header瑜� html 湲곕컲�쑝濡� 珥덇린�솕 �븿
	 * @throws MessagingException
	 */
	public void setHtmlContent() throws MessagingException{
		//msg.setContent(text,"text/html");
		/*
		try {
			text = MimeUtility.encodeText(text, "euc-kr", "B");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		msg.setContent(text,"text/html; charset=\"euc-kr\""); 
	}
	
	/**
	 * 二쇱뼱吏� Multipart瑜� Message�뿉 �븷�떦�븿
	 * @param mp
	 * @throws MessagingException
	 */
	private void setContent(Multipart mp) throws MessagingException{
		msg.setContent(mp);
	}
	
	/**
	 * 硫붿씪�쓣 蹂대궡湲� 諛붾줈 �쟾 �떒怨꾨줈�꽌 MimeMultipart 瑜� Message�뿉 泥⑤�
	 * @throws MessagingException
	 */
	private void setContent() throws MessagingException{
		msg.setContent(mmp);
	}
	
	/**
	 * 二쇱뼱吏� Multipart �뿉 MimeBodyPart瑜� 泥⑤��븿
	 * @param mp Multipart
	 * @param mbp MimeBodyPart
	 * @throws MessagingException
	 */
	private void append(Multipart mp, MimeBodyPart mbp) throws MessagingException{
		mp.addBodyPart(mbp);
	}
	
	/**
	 * 湲곕낯 MimeMultipart �뿉 �빐�떦 硫붿씪怨� 愿��젴�맂 紐⑤뱺 MimeBodyPart瑜� 遺숈엫
	 * @throws MessagingException
	 */
	private void append() throws MessagingException{
		mmp.addBodyPart(mbp);
		for( int i = 0 ; i < size ; i++){
			mmp.addBodyPart(mbps[i]);
		}
	}
	
	/**
	 * 硫붿씪 諛쒖넚 �궇吏�
	 * @throws MessagingException
	 */
	private void setSentDate() throws MessagingException{
		msg.setSentDate(new java.util.Date());
	}
	
	/**
	 * 硫붿씪 �뿤�뜑 珥덇린�솕, �씠 媛믩뱾�씠 �꼸�씪 寃쎌슦, use-8bit濡� �맂�떎
	 * @param header
	 * @param bit
	 * @throws MessagingException
	 */
	
	public void setHeader(String header,String bit) throws MessagingException{
		if( header == null || bit == null){
			mbp.setHeader("Content-Transfer-Encoding","8bit");
		}else{
			msg.setHeader(header, bit);
		}//*/
	}
	
	/**
	 * 硫붿씪 �쟾�넚
	 * @throws MessagingException
	 */
	public void send() throws MessagingException{
		try{
			transport.connect(host, id, passwd);
			transport.send(msg, msg.getAllRecipients());
			//transport.send(arg0, arg1)
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
			cat.error(e);
		}
	}
	
	/**
	 * 硫붿꽭吏��쓽 size瑜� 諛섑솚
	 * @return 硫붿꽭吏� �궗�씠利�
	 * @throws MessagingException
	 */
	public int getSize() throws MessagingException{
		setSize();
		return volumn;
	}
	
	/**
	 * 硫붿꽭吏��쓽 size瑜� 珥덇린�솕
	 * @throws MessagingException
	 */
	public void setSize() throws MessagingException{
		volumn = msg.getSize();
	}
	
	/**
	 * 硫붿씪 �닔�떊�옄�뱾 珥덇린�솕
	 * @param str
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	private void setRecipients() throws AddressException,MessagingException{
		String arr[] = Util.getSplitedStringArr(to , "," );
		InternetAddress[] address = new InternetAddress[arr.length];
		for( int i = 0 ; i < address.length ; i++){
			address[i] = new InternetAddress(arr[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, address);
	}
	
	/**
	 * �닲�� 李몄“ 硫붿씪 �닔�떊�옄�뱾 珥덇린�솕
	 * @param str
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	private void setRecipientsBCC() throws AddressException,MessagingException{
		String arr[] = Util.getSplitedStringArr(bcc , "," );
		InternetAddress[] address = new InternetAddress[arr.length];
		for( int i = 0 ; i < address.length ; i++){
			address[i] = new InternetAddress(arr[i]);
		}
		msg.setRecipients(Message.RecipientType.BCC, address);
	}
	
	/**
	 * 李몄“ 硫붿씪 �닔�떊�옄�뱾 珥덇린�솕
	 * @param str
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	private void setRecipientsCC() throws AddressException,MessagingException{
		String arr[] = Util.getSplitedStringArr(cc , "," );
		InternetAddress[] address = new InternetAddress[arr.length];
		for( int i = 0 ; i < address.length ; i++){
			address[i] = new InternetAddress(arr[i]);
		}
		msg.setRecipients(Message.RecipientType.CC, address);
	}
	
	public static void main(String args[]){
		try{

			/*
			
			String to  = "augxmas@gmail.com";//,sbang123@shinbiro.com";
			String cc  = "";
			String bcc = "";//,sbang123@shinbiro.com";
			String ko = "�굹�옃 留먯떥誘� <b>�뱯援��뿉 �떖�씪</b> 臾몄옄�� �꽌濡� �궗留쏅씈 �븘�땲 �븷 �럡";
			String en = "�궡 �씠瑜� �뼱�뿬�굪 �뿬寃� �깉濡� twentity"; // use 8 bit ko_en
			String text = ko + "\n" + en +"\n"+"嫄� �븳援�";
			String sub = "硫붿씪蹂대궡湲�";
			String from = "augxmas@gmail.com";
			
			String id = "augxmas@gmail.com";
			String passwd = "rotc1629";
			int filesize =1;
			// 23 smtp port, id, password
			SendMailFacade m = new SendMailFacade("smtp.gmail.com","25",id,passwd,sub,from,to,cc,bcc,text,filesize);
			m.setHeader(null,null);
			m.setFileDataSource( "C:/","rc4.log","rc4.log",0);
			m.send();
			//*/

			//String to  = "jiyoung1.park@doosan.com";//,sbang123@shinbiro.com";

			
			String to  = "wefuture@thepowerbrains.com";
			to = "kimch@mono-rama.com";
			String cc  = ""; 
			String bcc = "";
			String ko = "�굹�옃 留먯떥誘� <b>�뱯援��뿉 �떖�씪</b> 臾몄옄�� �꽌濡� �궗留쏅씈 �븘�땲 �븷 �럡 ";
			String en = "�궡 �씠瑜� �뼱�뿬�굪 �뿬寃� �깉濡� twentity"; // use 8 bit ko_en
			String text = ko + "\n" + en +"\n"+"嫄� �븳援�";
			String sub = "硫붿씪 �뀒�뒪�듃";
			String from = "augxmas@gmail.com";
			from = "hidongmun1@naver.com";
			String id = "wefuture";//
			String passwd = "!Q2w3e4r";
			
			id = "augxmas";//
			passwd = "qwaszx!@";

			int filesize =2;
			// 23 smtp port, id, password
			//SendMailFacade m = new SendMailFacade("mail.thepowerbrains.com","25",id,passwd,sub,from,to,cc,bcc,text,filesize);
			SendMailFacade m = new SendMailFacade("smtp.gmail.com","587",id,passwd,sub,from,to,cc,bcc,text,filesize);
			m.setHeader(null,null);
			m.setFileDataSource( "C:\\","info.dat","myinfo.dat",0);
			m.setFileDataSource( "C:\\","info.dat","myinfo.dat",1);
			m.send();

			//*/


		}catch(IOException e){
			e.printStackTrace();
		}catch(MessagingException e){
			e.printStackTrace();
		}

		System.out.println("end of main");
		

	}
		
}
	

