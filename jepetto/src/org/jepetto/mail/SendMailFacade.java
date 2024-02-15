package org.jepetto.mail;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.jepetto.logger.DisneyLogger;
import org.jepetto.util.Util;




/*
 * mail sending wrapping class
 * properties 占쎄퐬占쎌젟 占쎈툡占쎌뒄
 * mail.smtp.host
 * use case
 * 			String to  = "wefuture@yahoo.co.kr";//,sbang123@shinbiro.com";
 * 			String cc  = "kimdoyle@nate.com,chkim@umlkorea.com";
 * 			String bcc = "wefuture@nate.com";//,sbang123@shinbiro.com";
 * 			String ko = "占쎄돌占쎌쁼 筌띾Ŋ�뼢沃섓옙 <b>占쎈굄�뤃占쏙옙肉� 占쎈뼎占쎌뵬</b> �눧紐꾩쁽占쏙옙 占쎄퐣嚥∽옙 占쎄텢筌띿룆�뵂 占쎈툡占쎈빍 占쎈막 占쎈윞";
 * 			String en = "占쎄땀 占쎌뵠�몴占� 占쎈선占쎈연占쎄뎁 占쎈연野껓옙 占쎄퉱嚥∽옙 twentity"; // use 8 bit ko_en
 * 			String text = ko + "\n" + en +"\n"+"椰꾬옙 占쎈립�뤃占�";
 * 			String sub = "筌롫뗄�뵬癰귣�沅→묾占�";
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
 * @author 繹먲옙筌≪��깈
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
	 *@param id 筌롫뗄�뵬 �④쑴�젟占쎌벥 占쎈툡占쎌뵠占쎈탵
	 *@param passwd 筌롫뗄�뵬 �④쑴�젟占쎌벥 占쎈솭占쎈뮞占쎌뜖占쎈굡
	 *@param size 筌ｂ뫀占� 占쎈솁占쎌뵬占쎌벥 揶쏆뮇�땾
	 *@throws IOException
	 *@throws NoSuchProviderException
	 *@throws MessagingException
	 */
	public SendMailFacade(String host, String port,String id, String passwd, String subject, String from , String to, String cc, String bcc, String text,int size) throws IOException,NoSuchProviderException,MessagingException
	{
		init(host,port,id,passwd,subject,from,to,cc,bcc,text,size);
	}
	
	/**
	 * 雅뚯눘堉깍쭪占� 癰귨옙占쎈땾嚥∽옙 占쎈꽊占쎈뻿占쎈막 筌롫뗄�뵬占쎌뱽 �룯�뜃由곤옙�넅 占쎈립占쎈뼄.
	 * 筌롫뗄�뵬 占쎄퐣甕곌쑴�벥 占쎌넎野껋럩肉� 占쎈뎡占쎌뵬, 占쎈７�몴�똾�쓠占쎈뼒�몴占� 占쎈땾占쎌젟占쎈퉸占쎈튊 占쎈립占쎈뼄.
	 * @param id 
	 * @param passwd
	 * @param subject 占쎌젫筌륅옙
	 * @param from 占쎈꽊占쎈뻿占쎌쁽
	 * @param to 占쎈땾占쎈뻿占쎌쁽
	 * @param cc 筌〓챷��
	 * @param bcc 占쎈떜占쎌벉筌〓챷��
	 * @param text 占쎄땀占쎌뒠
	 * @param size 筌ｂ뫀占쏙옙�솁占쎌뵬 揶쏉옙占쎈땾
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public void init (String host, String port, final String id, final String passwd, String subject, String from , String to, String cc, String bcc, String text,int size) throws IOException,NoSuchProviderException,MessagingException {
		// 占쎈늄嚥≪뮉�쓠占쎈뼒 �룯�뜃由곤옙�넅
		Properties props = new Properties();
		
		// stmp �몴占� �룯�뜃由곤옙�넅
		props.put("mail.smtp.host", host);
		props.put("mail.port", port );
		props.put("mail.smtp.port", port );
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.ssl.trust", host );
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");


		
		Session session = Session.getDefaultInstance(props, null);
		
		session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(id, passwd);
                    }
                });

        		
		// 占쎄쉭占쎈�∽옙�벥 占쎈탵甕곌쑨�돪 占쎌넅筌롳옙 �빊�뮆�젾
		session.setDebug(true);
		
		//筌롫뗄�뵬 占쎌읈占쎈꽊 �④쑴留� �뿆�뫀諭�
		transport = session.getTransport("smtp");
		
		
		
		// 筌ｂ뫀占� 占쎈솁占쎌뵬 筌ｌ꼶�봺揶쏉옙 揶쏉옙占쎈뮟占쎈립 mime message�몴占� 占쎄문占쎄쉐
		// 筌롫뗄�뵬 占쎈뼣 占쎈릭占쎄돌占쎌벥 mime message揶쏉옙 占쎈툡占쎌뒄
		createMimeMessage(session);
		
		// 筌롫뗄�뵬 占쎄땀占쎌뒠占쎌뱽 占쎈뼖癰귣똾釉� 占쎈땾 占쎌뿳占쎈뮉 疫꿸퀡�궚 MimeBodyPart 占쎄문占쎄쉐
		setMimeBodyPart();
		
		// 筌ｂ뫀占� 占쎈솁占쎌뵬占쎌벥 揶쏆뮇�떭 筌띾슦寃�占쎌벥 MimeBodyPart 占쎄문占쎄쉐
		if( size > 0){
			mbps = new MimeBodyPart[size];
			for( int i = 0 ; i < mbps.length ; i++){
				mbps[i] = createMimeBodyPart();
			}
		}
		
		//MimeBodyPart占쎈퓠占쏙옙占쎈립 Container 占쎄문占쎄쉐
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
		setText(mbp,text); //2010.10.28 獄쏅벡占쏙옙�겫 (HTML占쎌굨占쎈뻼占쎌몵嚥∽옙 筌ｂ뫀占쏙옙�솁占쎌뵬 占쎄텢占쎌뒠占쎈릭疫뀐옙 占쎌맄占쎈맙)
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
	 * 雅뚯눘堉깍쭪占� mail instance嚥∽옙 筌롫뗄�뵬 �④쑴�젟占쎌뱽 �룯�뜃由곤옙�넅 占쎈릭占쎈연 揶쏆빘猿� 占쎄문占쎄쉐
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
	 * 筌롫뗄�뵬 占쎈툡占쎌뵠占쎈탵�몴占� 占쎈막占쎈뼣占쎈립占쎈뼄
	 * @param id
	 */
	public void setId(String id){ this.id = id; }
	
	/**
	 * 筌롫뗄�뵬 �뜮袁⑨옙甕곕뜇�깈�몴占� �룯�뜃由곤옙�넅 占쎈립占쎈뼄.
	 * @param passwd
	 */
	public void setPasswd(String passwd){ this.passwd = passwd; }
	
	/**
	 * 雅뚯눘堉깍쭪占� 占쎄쉭占쎈�∽옙�뱽 揶쏉옙筌욑옙�⑨옙 MimeMessage�몴占� 占쎄문占쎄쉐
	 * @param session
	 * @return MimeMessage
	 */
	
	private MimeMessage createMimeMessage(Session session){
		msg = new MimeMessage(session);
		return msg;
	}
	
	/**
	 * MimeBodyPart �몴占� 占쎄문占쎄쉐,筌ｂ뫀占� 占쎈솁占쎌뵬占쎌벥 揶쏉옙占쎈땾 筌띾슦寃� 占쎌깈�빊�뮆留귨옙�뼄
	 * @return MimeBodyPart
	 */
	
	private MimeBodyPart createMimeBodyPart(){
		return new MimeBodyPart();
	}
	
	/**
	 * 筌롫뗄�뵬 占쎄땀占쎌뒠占쎌뱽 占쎈뼖癰귣똾釉� 占쎈땾 占쎌뿳占쎈뮉 疫꿸퀡�궚 MimeBodyPart
	 */
	
	private void setMimeBodyPart(){
		mbp = new MimeBodyPart();
	}
	
	/**
	 * MimeBodyPart占쎈퓠 占쏙옙占쎈립 �뚢뫂�믭옙�뵠占쎄섐
	 * @return MimeMultipart
	 */
	
	public MimeMultipart createMimeMultipart(){
		mmp = new MimeMultipart();
		return mmp;
	}
	
	/**
	 * 雅뚯눘堉깍쭪占� MimeBodyPart占쎈퓠 筌롫뗄�뵬 占쎄땀占쎌뒠占쎌뱽 占쎈막占쎈뼣占쎈립占쎈뼄
	 * @param mbp
	 * @param text
	 * @throws MessagingException
	 */
	private void setText(MimeBodyPart mbp, String text) throws MessagingException{
		mbp.setContent(text,"text/html; charset=\"utf-8\""); 
	}
	
	/**
	 * 雅뚯눘堉깍쭪占� index占쎈퓠 占쎈퉸占쎈뼣占쎈릭占쎈뮉 MimeBodyPart占쎈퓠 筌롫뗄�뵬 占쎄땀占쎌뒠占쎌뱽 �룯�뜃由곤옙�넅 占쎈립占쎈뼄.
	 * @param text
	 * @param index
	 * @throws MessagingException
	 */
	private void setText(String text,int index) throws MessagingException{
		mbps[index].setText(text);
	}
	
	/**
	 * 疫꿸퀡�궚 MimeBodyPart 占쎈퓠 占쎄땀占쎌뒠 占쎌읅占쎌뒠
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
	 *筌롫뗄�뵬 占쎈꽊占쎈뻿占쎌쁽�몴占� 占쎈막占쎈뼣占쎈립占쎈뼄
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
	 * 筌롫뗄�뵬 占쎌젫筌뤴뫗�뱽 占쎈막占쎈뼣占쎈립占쎈뼄
	 * @param subject
	 * @throws MessagingException
	 */
	private void setSubject(String subject) throws MessagingException{
		msg.setSubject(subject);
	}
	
	private void setSubject() throws MessagingException{
		
		try {
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MimeBodyPart占쎈퓠 雅뚯눘堉깍쭪占� 占쎈솁占쎌뵬筌뤿굞�몵嚥∽옙 筌ｂ뫀占쏙옙�솁占쎌뵬占쎌뱽 筌ｂ뫀占쏙옙釉놂옙�뼄
	 * @param mbp
	 * @param filename
	 * @throws MessagingException
	 */	
	private void setFileDataSource(MimeBodyPart mbp,String filename) throws MessagingException{
		FileDataSource fds = new FileDataSource(filename);
		mbp.setDataHandler( new DataHandler(fds) );
		// 占쎈엘占쎈쐭占쎈퓠 占쎈솁占쎌뵬占쎌뵠�뵳袁⑹뱽 占쎄쉭占쎈샒占쎈막占쎈르 base64占쎌굨占쎄묶嚥∽옙 獄쏆꼶諭띰옙�뻻.
		//mbp2.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
		mbp.setFileName(fds.getName());
	}
	
	/**
	 * 雅뚯눘堉깍쭪占� 占쎈솁占쎌뵬占쎌뱽 占쎈퉸占쎈뼣 占쎌뵥占쎈�볩옙�뮞占쎈퓠 占쎈퉸占쎈뼣占쎈릭占쎈뮉 MimeBodyPart占쎈퓠 筌ｂ뫀占쏙옙�뻻占쎄땀
	 * (餓λ쵐�뒄) 占쎈솁占쎌뵬筌뤿굞占� base64嚥∽옙 占쎌뵥�굜遺얜뎃
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
	 * 雅뚯눘堉깍쭪占� 野껋럥以덌옙�벥 占쎈솁占쎌뵬筌뤿굞肉� 占쎈퉸占쎈뼣占쎈릭占쎈뮉 占쎈솁占쎌뵬占쎌뱽 雅뚯눘堉깍쭪占� index占쎈퓠 占쎈퉸占쎈뼣占쎈릭占쎈뮉 MimeBodypart占쎈퓠 筌ｂ뫀占쏙옙�뻻占쎄땀
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
		
		
		mbps[index].setHeader("Content-Type", "multipart/mixed");
		mbps[index].setDataHandler(new DataHandler(fds));

		
		//mbps[index].setFileName(MimeUtility.encodeText(realfilename, "euc-kr","Q"));
		mbps[index].setFileName(Util.en(realfilename));
		
		//mbps[index].attachFile(file);
	}
	
	/**
	 * Message header�몴占� html 疫꿸퀡而뀐옙�몵嚥∽옙 �룯�뜃由곤옙�넅 占쎈맙
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
	 * 雅뚯눘堉깍쭪占� Multipart�몴占� Message占쎈퓠 占쎈막占쎈뼣占쎈맙
	 * @param mp
	 * @throws MessagingException
	 */
	private void setContent(Multipart mp) throws MessagingException{
		msg.setContent(mp);
	}
	
	/**
	 * 筌롫뗄�뵬占쎌뱽 癰귣�沅→묾占� 獄쏅뗀以� 占쎌읈 占쎈뼊�④쑬以덌옙苑� MimeMultipart �몴占� Message占쎈퓠 筌ｂ뫀占�
	 * @throws MessagingException
	 */
	private void setContent() throws MessagingException{
		msg.setContent(mmp);
	}
	
	/**
	 * 雅뚯눘堉깍쭪占� Multipart 占쎈퓠 MimeBodyPart�몴占� 筌ｂ뫀占쏙옙釉�
	 * @param mp Multipart
	 * @param mbp MimeBodyPart
	 * @throws MessagingException
	 */
	private void append(Multipart mp, MimeBodyPart mbp) throws MessagingException{
		mp.addBodyPart(mbp);
	}
	
	/**
	 * 疫꿸퀡�궚 MimeMultipart 占쎈퓠 占쎈퉸占쎈뼣 筌롫뗄�뵬�⑨옙 �꽴占쏙옙�졃占쎈쭆 筌뤴뫀諭� MimeBodyPart�몴占� �겫�늿�뿫
	 * @throws MessagingException
	 */
	private void append() throws MessagingException{
		mmp.addBodyPart(mbp);
		for( int i = 0 ; i < size ; i++){
			mmp.addBodyPart(mbps[i]);
		}
	}
	
	/**
	 * 筌롫뗄�뵬 獄쏆뮇�꽊 占쎄텊筌욑옙
	 * @throws MessagingException
	 */
	private void setSentDate() throws MessagingException{
		msg.setSentDate(new Date());
	}
	
	private void setSentDate(String _sent) throws MessagingException{
		//String _sent = "20210125";
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		Date sent = null;
		try {
			sent = df.parse(_sent);
		} catch (ParseException | java.lang.NullPointerException e) {
			sent = new java.util.Date();
			e.printStackTrace();
		}
		
		msg.setSentDate(sent);
	}
	
	
	/**
	 * 筌롫뗄�뵬 占쎈엘占쎈쐭 �룯�뜃由곤옙�넅, 占쎌뵠 揶쏅�⑸굶占쎌뵠 占쎄섯占쎌뵬 野껋럩�뒭, use-8bit嚥∽옙 占쎈쭆占쎈뼄
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
	 * 筌롫뗄�뵬 占쎌읈占쎈꽊
	 * @throws MessagingException
	 */
	public void send() throws MessagingException{
		try{
			transport.connect(host, id, passwd);
			transport.send(msg, msg.getAllRecipients());
		}catch(Exception e){
			e.printStackTrace();
			//cat.info(e);
		}finally {
			transport.close();
		}
	}
	
	/**
	 * 筌롫뗄苑�筌욑옙占쎌벥 size�몴占� 獄쏆꼹�넎
	 * @return 筌롫뗄苑�筌욑옙 占쎄텢占쎌뵠筌앾옙
	 * @throws MessagingException
	 */
	public int getSize() throws MessagingException{
		setSize();
		return volumn;
	}
	
	/**
	 * 筌롫뗄苑�筌욑옙占쎌벥 size�몴占� �룯�뜃由곤옙�넅
	 * @throws MessagingException
	 */
	public void setSize() throws MessagingException{
		volumn = msg.getSize();
	}
	
	/**
	 * 筌롫뗄�뵬 占쎈땾占쎈뻿占쎌쁽占쎈굶 �룯�뜃由곤옙�넅
	 * @param str
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	private void setRecipients() throws AddressException,MessagingException{
		//String arr[] = Util.getSplitedStringArr(to , "," );
		String arr[]  = to.split(",");
		InternetAddress[] address = new InternetAddress[arr.length];
		for( int i = 0 ; i < address.length ; i++){
			address[i] = new InternetAddress(arr[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, address);
	}
	
	/**
	 * 占쎈떜占쏙옙 筌〓챷�� 筌롫뗄�뵬 占쎈땾占쎈뻿占쎌쁽占쎈굶 �룯�뜃由곤옙�넅
	 * @param str
	 * @throws AddressException
	 * @throws MessagingException
	 */
	
	private void setRecipientsBCC() throws AddressException,MessagingException{
		String arr[] = bcc.split(",");
		InternetAddress[] address = new InternetAddress[arr.length];
		for( int i = 0 ; i < address.length ; i++){
			address[i] = new InternetAddress(arr[i]);
		}
		msg.setRecipients(Message.RecipientType.BCC, address);
	}
	
	/**
	 * 筌〓챷�� 筌롫뗄�뵬 占쎈땾占쎈뻿占쎌쁽占쎈굶 �룯�뜃由곤옙�넅
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
			String ko = "占쎄돌占쎌쁼 筌띾Ŋ�뼢沃섓옙 <b>占쎈굄�뤃占쏙옙肉� 占쎈뼎占쎌뵬</b> �눧紐꾩쁽占쏙옙 占쎄퐣嚥∽옙 占쎄텢筌띿룆�뵂 占쎈툡占쎈빍 占쎈막 占쎈윞";
			String en = "占쎄땀 占쎌뵠�몴占� 占쎈선占쎈연占쎄뎁 占쎈연野껓옙 占쎄퉱嚥∽옙 twentity"; // use 8 bit ko_en
			String text = ko + "\n" + en +"\n"+"椰꾬옙 占쎈립�뤃占�";
			String sub = "筌롫뗄�뵬癰귣�沅→묾占�";
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

			
			String names[] = new String[] {
						"김방영",
						"김성종",
						"김창호",
						"김동욱",
						"김봉관",
						"박홍석",
						"김다은",
						"허선",
						"배태주"
						};
			
			String emails[] = new String[] {
					"bykim@monorama.kr",
					"dldls@naver.com",
					"kimch@monorama.kr",
					"bass89@naver.com",
					"bkkim@monorama.kr",
					"doiturself@naver.com",
					"blast3017@monorama.kr",
					"ssun940910@gmail.com",
					"contact@monorama.kr"
					};
			
			
			
			String to  = "augxmas@gmail.com";
			//to = "kimch@mono-rama.com";
			String cc  = "kimch@mono-rama.com"; 
			String bcc = "";
			//String ko = "占쎄돌占쎌쁼 筌띾Ŋ�뼢沃섓옙 <b>占쎈굄�뤃占쏙옙肉� 占쎈뼎占쎌뵬</b> �눧紐꾩쁽占쏙옙 占쎄퐣嚥∽옙 占쎄텢筌띿룆�뵂 占쎈툡占쎈빍 占쎈막 占쎈윞 ";
			//String en = "占쎄땀 占쎌뵠�몴占� 占쎈선占쎈연占쎄뎁 占쎈연野껓옙 占쎄퉱嚥∽옙 twentity"; // use 8 bit ko_en
			String text = "수고해 주셔서 감사합니다.";
			String sub = "급여명세서";
			//String from = "augxmas@gmail.com";
			String from = "kimch@monorama.kr";
			String id = "kimch@monorama.kr";//
			String passwd = "zhzhfh@76";
			
			//id = "augxmas";//
			//passwd = "qwaszx!@";

			
			//for(int i = 0 ; i < names.length ; i++) {
			String path = "C:\\Temp\\급여명세서";
			File dir = new File(path);
			String files[] = dir.list();
			
			for(int i = 0 ; i < names.length ; i++) {
				int filesize =1;
				to = emails[i];
				cc = null;
				bcc = null;
				SendMailFacade m = new SendMailFacade("smtp.worksmobile.com","587",id,passwd,sub,from,to,cc,bcc,text,filesize);
				
				m.setHeader(null,null);
				for(int j = 0 ; j < files.length ; j++) {
					if(files[j].indexOf(names[i]) > 0) {
						System.out.println(files[j]);
						m.setFileDataSource( path,files[j],files[j],0);
						m.setSentDate("20221024");
						System.out.println(names[i] + " " + files[j] );
						continue;
					}
				}
				m.send();
			}//*/

		}catch(IOException e){
			e.printStackTrace();
		}catch(MessagingException e){
			e.printStackTrace();
		}

		System.out.println("end of main");
		

	}
		
}
	

