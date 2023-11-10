package org.jepetto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import org.json.JSONObject;

public class DocumentGenerator {
	
	public static void main(String[] args){
		String filePath = "/tmp/04.docx";
        String outPath = "/tmp/"+ new Date().getTime()+".doc";
        
        JSONObject json = new JSONObject();
		
		json.put("name", "이름");
		json.put("address", "주소");
        
		try {
			DocumentGenerator.makeWord(filePath, outPath, json, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param inpPath : word 템플리트 파일 패스
	 * @param outPath : 저장되는 word의 파일 패스
	 * @param json : 대치될 문구 key 와 value 조합
	 * @param readOnly : word 파일을 읽기전용으로 할지
	 * @throws IOException 
	 */
	public static void makeWord(String inpPath, String outPath, JSONObject json, boolean readOnly) throws IOException {
		//XWPFDocument docx;
        //FileOutputStream out;
		try (FileOutputStream out = new FileOutputStream(outPath); ){
			XWPFDocument docx = new XWPFDocument(new FileInputStream(new File(inpPath)));
				
			Iterator<String> it = json.keys();
			while(it.hasNext()) {
				String key = it.next();
				String val = json.getString(key);
				replaceWordTemplate(docx, key, val);
			}
			if(readOnly) docx.enforceReadonlyProtection();
			docx.write(out);
		} catch (IOException fe) {
			//throws fe;
			throw fe;
		} finally {
			
		}
	}
	
	private static void replace(List<XWPFRun> runs, String findText, String replaceText) {
		if (runs != null) {
	        for(int i=0;i<runs.size();i++) {
	        	XWPFRun r = runs.get(i);
	        	String text = r.getText(0);
	        	
	        	if(text.equals("{"+findText+"}")) {
	        		r.setText(replaceText,0);
	        	} else if("{".equals(text)) {
	        		i++;
	        		XWPFRun r2 = runs.get(i);
	        		String text2 = r2.getText(0);

	        		if(findText.equals(text2)) {
	        			i++;
	        			XWPFRun r3 = runs.get(i);
		        		String text3 = r3.getText(0);
		        		
		        		if("}".equals(text3)) {
		        			r.setText("",0);
		        			r2.setText(replaceText,0);
		        			r3.setText("",0);
		        		}
	        		} else if(text2.equals(findText+"}")) {
	        			r.setText("",0);
	        			r2.setText(replaceText,0);
	        		}
	        	} else if (text.contains("{"+findText+"}")) {
	        		text = text.replace("{"+findText+"}", replaceText);
	        		r.setText(text, 0);
	        	}
	        }
	    }
	}
	
	
	private static void replaceWordTemplate(XWPFDocument doc, String findText, String replaceText) {
		for (XWPFParagraph p : doc.getParagraphs()) {
			replace(p.getRuns(), findText, replaceText);
		}
		for (XWPFTable tbl : doc.getTables()) {
		   for (XWPFTableRow row : tbl.getRows()) {
		      for (XWPFTableCell cell : row.getTableCells()) {
		         for (XWPFParagraph p : cell.getParagraphs()) {
		        	 replace(p.getRuns(), findText, replaceText);
		         }
		      }
		   }
		}
	}

}
