package org.jepetto.filter;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import org.jepetto.logger.DisneyLogger;
import org.jepetto.util.PropertyReader;
//import org.jepetto.proxy.HomeProxy;
//import org.jepetto.sql.Wrapper;
//import org.jepetto.util.Util;

public class FileInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appid;

	private String original;
	
	private String renamed;
	
	private String size;
	
	private String uploaddt;
	
	private String count;
	
	private String path = PropertyReader.getInstance().getProperty("savedRepository"); // 20161108 파일 저장위치가 tmp가 아닌 savedRepository임.
	
	DisneyLogger cat = new DisneyLogger(FileInfo.class.getName()); 
	
	public FileInfo(){}
	
	public FileInfo( String original , String renamed , String size , String uploaddt, String count ){
		this.original = original;
		this.renamed = renamed;
		this.size = size;
		this.uploaddt = uploaddt;
		this.count = count;
	}
	
	public FileInfo( String appid, String path, String original , String renamed , String size , String uploaddt, String count ){
		this.appid = appid;
		this.path = path;
		this.original = original;
		this.renamed = renamed;
		this.size = size;
		this.uploaddt = uploaddt;
		this.count = count; 
	}


	// 20161108 업로드된 파일이 존재하는 지 여부 체크
	public boolean exists() {
		File file = new File(path,renamed);
		return file.exists();
	}
	
	public boolean delete(){
		File file = new File(path,renamed);
		return file.delete();
	}
	
	
	public String getOriginal(){
		return original;
	}
	
	public void setOriginal(String original){
		this.original = original; 
	}
	
	public String getRenamed(){
		return renamed;
	}
	
	public void setRenamed(String renamed){
		this.renamed = renamed;
	}

	public String getSize(){
		return size;
	}
	
	public void setSize(String size){
		this.size = size;
	}
	
	public String getUploadDt(){
		return uploaddt;
	}
	
	public void setUploadDt(String uploaddt){
		this.uploaddt = uploaddt;
	}
	
	public String getCount(){
		return count;
	}
	
	public void setCount( String count){
		this.count = count;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	/**
	 * 二쇱뼱吏� 寃쎈줈�쓽 �뙆�씪濡� 遺��꽣 inputstream �쓣 �깮�꽦�븳�떎 
	 * @param path 		�뙆�씪 寃쎈줈
	 * @param renamed	�뙆�씪 紐�
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(String path, String renamed) throws IOException{
		//renamed = Util.ko(renamed); // 20161108 UTF8 환경에서 문제가 발생함. 주석처리
		File file = new File(path,renamed);
		InputStream in = new FileInputStream( file );
		return in;	
	}

	/**
	 * inputstream �깮�꽦
	 * @return 
	 * @throws IOException
	 */	
	public InputStream getInputStream() throws IOException{
		InputStream in = new FileInputStream( new File(path,renamed));
		return in;
	}
	

}
