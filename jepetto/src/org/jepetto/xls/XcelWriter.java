package org.jepetto.xls;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * <pre>
 * 
 * �뿊�� �뙆�씪�쓣 write�븷 �닔 �엳�뒗 api瑜� �젣怨�
 * 
 * use case
 * 		XcelWriter excel = new XcelWriter();
 * 		excel.setHSSFWorkBook();
 * 		excel.createSheet("test");
 * 		excel.createRow(0);
 * 		excel.createRow(1);
 * 		excel.createRow(2);
 * 		excel.createCells(excel.getRow(0), 6);
 * 		excel.createCells(excel.getRow(1), 6);
 * 		excel.createCells(excel.getRow(2), 6);
 * 		excel.mergeCell(0,0,1,0);
 * 		excel.mergeCell(0,2,1,2);
 * 		excel.mergeCell(0,4,1,4);
 * 		excel.mergeCell(2,0,2,1);
 * 		excel.mergeCell(2,2,2,3);
 * 		excel.mergeCell(2,4,2,5);
 * 		excel.setCellValue(excel.getRow(0), 0, "00,10");
 * 		excel.setCellValue(excel.getRow(0), 1, "01");
 * 		excel.setCellValue(excel.getRow(1), 1, "11");
 * 		excel.setCellValue(excel.getRow(0), 2, "02,12");
 * 		excel.setCellValue(excel.getRow(0), 3, "03");
 * 		excel.setCellValue(excel.getRow(1), 3, "13");
 * 		excel.setCellValue(excel.getRow(0), 4, "04,14");
 * 		excel.setCellValue(excel.getRow(0), 5, "05");
 * 		excel.setCellValue(excel.getRow(1), 5, 1.0);
 * 		excel.setCellValue(excel.getRow(2), 0, true);
 * 		excel.setCellValue(excel.getRow(2), 2, new java.util.Date());
 * 		excel.setCellValue(excel.getRow(2), 4, 10);
 * 		excel.setCellValue(3, 4, 10);
 * 		excel.writeXLS("c:/","dulee.xls");
 * 
 *  </pre>
 * @author umlkorea 源�李쏀샇
 *
 */
public class XcelWriter {

	/**
	 * excel row�쓽 諛곗뿴
	 */	
	private HSSFRow rowset[];
	
	/**
	 * respose.xml reader 
	 */
	//private PresidioMeta entity;
	
	/**
	 * sheet�쓽 媛��닔 
	 */
	private int pages;
	
	/**
	 * �깮�꽦�맆 excel 臾몄꽌�쓽 header row �쓽 媛��닔
	 */	
	private int headerRow = 1;
	
	/**
	 * sheet�븞�쓽 row 媛앹닔
	 */
	private int unit;

	/**
	 * 湲곕낯 �옉�뾽 �옣(workbook)
	 */	
	private HSSFWorkbook wb;
	
	//private Workbook wb;
	
	/**
	 * �옉�뾽 sheet
	 */
	private HSSFSheet sheet;
	
	/**
	 * 理쒕� 5留� row�떒�쐞濡� sheet瑜� 遺꾨━�븳�떎. 利� sheet媛� �몢 �옣 �씠�긽�씪 寃쎌슦
	 */
	private HSSFSheet sheets[];
	
	/**
	 * cell style
	 */
	private HSSFCellStyle cellStyle;

	/**
	 * �뿊�� �뙆�씪�쓽 �뿤�뜑 rows
	 */
	
	private HSSFRow hRows[];
	
	/**
	 * �뿊�� �뙆�씪�쓽 �뿤�뜑 columns
	 */
	private HSSFCell hCells[];
	
	/**
	 * 濡쒖슦 吏묓빀泥�
	 * rows collection
	 */
	private Vector rows = new Vector();
	
	
	/**
	 * cell 吏묓빀泥�
	 */
	private Hashtable cells = new Hashtable();

	/**
	* �뿊�� �옉�뾽�옣�쓣 �깮�꽦
	*
	* @see getHSSFWorkBook()
	* @param
	* @return HSSFWorkbook
	* @exception
	*/

	public HSSFWorkbook getHSSFWorkBook(){
	  return new HSSFWorkbook();
	}
	
	public HSSFWorkbook createHSSFWorkBook(){
	  return new HSSFWorkbook();
	}
	
	/**
	* �뿊�� �옉�뾽�옣�쓣 �깮�꽦�븯怨�, �꽕�젙
	*
	* @see getHSSFWorkBook()
	* @param
	* @return
	* @exception
	*/

	public void setHSSFWorkBook(){
	  wb = new HSSFWorkbook();
	}

	/**
	* �뿊�� �떆�듃 �깮�꽦
	*
	* @see createSheet(HSSFWorkbook, String )
	* @param  wb : �옉�뾽�옣
	* @ param name : �떆�듃 �씠由�
	* @return HSSFWorkbook
	* @exception
	*/
	public HSSFSheet createSheet(HSSFWorkbook wb, String name){
	  return wb.createSheet(name);
	}

	/**
	* �뿊�� �떆�듃 �깮�꽦�븯怨� �꽕�젙
	*
	* @see createSheet(HSSFWorkbook, String)
	* @param name : �떆�듃 �씠由�
	* @return HSSFSheet
	* @exception
	*/

	public HSSFSheet createSheet(String name){
	  sheet = wb.createSheet(name);
	  return sheet;
	}

	/**
	 * 
	 * 二쇱뼱吏� �궗�씠利�, 利� row瑜� �룷�븿�븷 �닔 �엳�뒗 sheet瑜� �깮�꽦�븳�떎
	 *  
	 * @param size
	 * @return
	 */
	public HSSFSheet[] createSheets(int size){//,String name){
	    
	    int index = 0;
	    
	    int value = size/(65536);
	    int mod = size%(65536);
	    
	    if ( mod != 0 ) {
	        index = value+1;
	        createSheets(value+1);
	    }else{
	        index = value;
	        createSheets(value);
	    }
	    
		HSSFSheet sheets[] = new HSSFSheet[index];
		for( int i = 0 ; i < sheets.length ; i++){
			sheets[i] = createSheet( + i + " th"); 
		}
		this.sheets = sheets;
		return sheets;
	}
	
	private HSSFRow rowarr[] = null;
	private HSSFCell cellarr[][] = null;
	
	public void create( int rowCount , String columnName[] ){
	    
	    int columnSize = columnName.length;
	    int rowLimit = 65536; // -1 for column name
	     
	    int rowIndex = -1;
	    
	    HSSFSheet sheets[] = createSheets( rowCount );
	    
	    HSSFRow row = null;
	    HSSFCell cell = null;
	    
	    int value = 0;
	    int mod	= 0;
	    
	    HSSFRow rows[][] = new HSSFRow[sheets.length][rowCount+1];
	    
	    if( sheets.length > 2 ){
	        value	= rowCount/(rowLimit-1);
	        mod		= rowCount%(rowLimit-1);
	        if( mod != 0 ){
	            for( int i = 0 ; i < sheets.length-1 ; i++){
	                rows[i] = new HSSFRow[rowLimit];
	            }
	            rows[sheets.length-1] = new HSSFRow[mod+1];
	        }
	    }
	    
	    for( int i = 0 ; i < sheets.length ; i++){
	        row = createRow(sheets[i],0);
	        for( int j = 0 ; j < columnSize ; j++){
	            cell = createCell(row,j);
	            cell.setCellValue(columnName[j]);
	        }
	    }
	    
	    
	    
	}
	

	/**
	* Row �깮�꽦
	*
	* @see createRow(HSSFSheet , int )
	* @ param sheet : �옉�뾽 ���긽�씠 �릺�뒗 �떆�듃
	* @ param rownum row�쓽 �씤�뜳�뒪
	* @return HSSFSheet
	* @exception
	*/

	public HSSFRow createRow(HSSFSheet sheet, int rownum){
		HSSFRow row = sheet.createRow((short)rownum);
		rows.addElement(row);
		return row;
	}

	/**
	* Row �깮�꽦, �꽕�젙
	*
	* @see createRow(int )
	* @ param rownum row�쓽 �씤�뜳�뒪
	* @return HSSFSheet
	* @exception
	*/
	public HSSFRow createRow(int rownum){
		HSSFRow row = sheet.createRow((short)rownum);
		rows.addElement(row);
		return row;
	}

	/**
	 * 二쇱뼱吏� index�뿉 �빐�떦�븯�뒗 row瑜� 諛섑솚
	 * @param i
	 * @return
	 */
	public HSSFRow getRow(int i){
		HSSFRow row = (HSSFRow)rows.elementAt(i);
		return row;
	}

	/**
	* Cell �깮�꽦
	*
	* @see createCell(HSSFRow, int s)
	* @ param  row : �옉�뾽 ���긽�씠 �릺�뒗 Row
	* @ param s : �깮�꽦�븷 Cell�쓽 �씤�뜳�뒪
	* @return HSSFCell
	* @exception
	*/

	public HSSFCell createCell(HSSFRow row, int s){
	  return row.createCell((short)s);
	}
	
	
	/**
	 * �빐�떦 row�뿉 �룷�븿�릺�뒗 cell�쓽 媛��닔瑜� �깮�꽦
	 * @param row
	 * @param size
	 */
	public void createCells(HSSFRow row, int size){
		HSSFCell arr[] = new HSSFCell[size]; 
		for( int i = 0 ; i < arr.length ; i++){
			arr[i] = createCell(row,i);
		}
		cells.put(row,arr);
	}
	
	/**
	 * �뿊�� �뙆�씪 �뿤�뜑瑜� 留뚮뱾湲� �쐞�븳 �� 怨듦컙 �솗蹂�, �뿤�뜑�뿉�꽌�쓽 �끂 蹂묓빀�� �쁽 �떒怨꾩뿉�꽌�뒗 
	 * 怨좊젮�븯吏� �븡�뒗�떎, �슦�꽑 怨듦컙�쓣 �솗蹂댄븳 �썑 蹂묓빀 硫붿냼�뱶瑜� �샇異쒗븯�뿬 蹂묓빀 �븷 �닔 �엳�룄濡� �븳�떎
	 * @param row �뿤�뜑�쓽 row 媛��닔
	 * @param column �뿤�뜑�쓽 column 媛��닔
	 */	
	public void initHeaderCell(HSSFSheet sheet, int row, int column){
		HSSFRow rows[] = new HSSFRow[row];
		for( int i = 0 ; i < row ; i++){
			rows[i] = createRow(sheet,i);
		}
		HSSFCell cells[] = new HSSFCell[column];
		for( int i = 0 ; i < row ; i++){
			for( int j = 0 ; j < column ; j++){
				cells[j] = createCell(rows[i],j);
			}
		}
		this.hRows = rows;
		this.hCells = cells;
	}
	
	
	/**
	 * 二쇱뼱吏� reponse.xml �쓽 �쟾泥� row�쓽 媛��닔�� sheet�븞�쓽 �룷�븿�맆 �닔 �엳�뒗
	 * 理쒕� row 媛��닔瑜� 鍮꾧탳�븯�뿬 �깮�꽦�릺�뼱�빞 �븷 sheet 媛��닔瑜� 寃곗젙
	 * @param size row�쓽 媛��닔
	 * @return �깮�꽦�맆 sheet 媛��닔
	 */
	private int getPages(int size){
		int count = size/unit;
		if( size%unit > 0){
			++count;
		}
		return count;
	}
	
	/*
	 * response.xml�쓽 data瑜� excel 臾몄꽌�뿉 �쟻�떦�엳 諛붿씤�뵫�븳�떎
	 * @param contents response.xml濡� 遺��꽣 �씫�뼱�삩 data
	 
	public void setValue(String contents[][]){
		
		try{
		int row = entity.getRowSize();
		int column = entity.getColumnSize();
		int page = getPages(row);
		HSSFSheet []sheets = createSheets(page);
		HSSFRow rowSet[][] = new HSSFRow[sheets.length][unit+headerRow];
		for( int i = 0 ; i < sheets.length ; i++){
			for(int j = 0 ; j < unit + headerRow ; j++){
				HSSFRow _row = createRow(sheets[i], j);
				rowSet[i][j] = _row;
			}
		}
		String columns[] = entity.getColumns();
		for( int i = 0 ; i < sheets.length ; i++){
			int cIndex = 0;
			for( int j = 0 ; j < headerRow ; j++){
				HSSFRow _row = rowSet[i][j];
				for( int k = 0 ; k < column ; k++){
					HSSFCell cell = createCell( _row , k );
					setEncoding(cell);
					//System.out.println("column : " + columns[cIndex++] );
					setCellValue( cell, columns[cIndex++]);
				}
			}
			
			for( int j = headerRow ; j < (unit + headerRow) && j < (row + headerRow) ; j++){
				HSSFRow _row = rowSet[i][j];
				for( int k = 0 ; k < column ; k++){
					HSSFCell cell = createCell( _row , k );
					setEncoding(cell);
					String value = contents[i*unit + j-headerRow][k];
					setCellValue(cell, value);
				}
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}*/
	

	/**
	* CellStyle �깮�꽦,李몄“
	*
	* @see getCellStyle(HSSFSheet , int )
	* @ param
	* @return
	* @exception
	*/

	public HSSFCellStyle getCellStyle(){
	  return wb.createCellStyle();
	}

	/**
	* CellStyle �깮�꽦, 李몄“
	*
	* @see getCellStyle(HSSFWorkbook)
	* @ param  wb : CellStyle�쓣 李몄“�븯湲� �쐞�븳 �옉�뾽�옣
	* @return HSSFCellStyle
	* @exception
	*/

	public HSSFCellStyle getCellStyle(HSSFWorkbook wb){
	  return wb.createCellStyle();
	}

	/**
	* CellStyle �꽕�젙
	*
	* @see setCellStyle(HSSFCell, HSSFCellStyle )
	* @ param  cell : CellStyle�쓣 �꽕�젙�븷 Cell
	* @ param style : �꽕�젙�븳 cellstyle
	* @return
	* @exception
	*/

	public void setCellStyle(HSSFCell cell, HSSFCellStyle style){
	  cell.setCellStyle(style);
	}

	/**
	* �븳湲� �씤肄붾뵫 吏��썝, �쑀�땲肄붾뱶濡� �꽕�젙
	*
	* @see setEncoding(HSSFCell)
	* @ param cell : �쑀�땲肄붾뱶濡� �꽕�젙�븷 Cell
	* @return
	* @exception
	*/

	public void setEncoding(HSSFCell cell){
	  //cell.setEncoding( HSSFCell.ENCODING_UTF_16 );
	}
	
	/**
	 * 二쇱뼱吏� row�뿉 �빐�떦 cell �씤�뜳�뒪�뿉 媛믪쓣 �븷�떦
	 * @param row
	 * @param index
	 * @param value
	 */
	public void setCellValue(HSSFRow row, int index, int value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		setCellValue(arr[index],value);
	}

	public void setCellValue(HSSFRow row, int index, java.util.Calendar value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		
		setCellValue(arr[index],value);
	}
	
	public void setCellValue( int row , int column , int value ){
	    setCellValue(this.getRow(row), column, value);
	}
	

	/**
	 * 二쇱뼱吏� row�뿉 �빐�떦 cell �씤�뜳�뒪�뿉 媛믪쓣 �븷�떦
	 * @param row
	 * @param index
	 * @param value
	 */
	public void setCellValue(HSSFRow row, int index, double value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		setCellValue(arr[index],value);
	}
	
	public void setCellValue( int row, int column, double value){
	    setCellValue(getRow(row),column,value);
	}
	
	/**
	 * 二쇱뼱吏� row�뿉 �빐�떦 cell �씤�뜳�뒪�뿉 媛믪쓣 �븷�떦
	 * @param row
	 * @param index
	 * @param value
	 */
	public void setCellValue(HSSFRow row, int index, boolean value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		setCellValue(arr[index],value);
	}
	
	
	public void setCellValue( int row, int column, boolean value){
	    setCellValue(getRow(row),column, value);
	}

	
	/**
	 * 二쇱뼱吏� row�뿉 �빐�떦 cell �씤�뜳�뒪�뿉 媛믪쓣 �븷�떦
	 * @param row
	 * @param index
	 * @param value
	 */
	public void setCellValue(HSSFRow row, int index, String value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		setCellValue(arr[index],value);
	}
	
	public void setCellValue(int row, int column, String value){
	    setCellValue(getRow(row),column,value);
	}
	
	
	
	/**
	 * 二쇱뼱吏� row�뿉 �빐�떦 cell �씤�뜳�뒪�뿉 媛믪쓣 �븷�떦
	 * @param row
	 * @param index
	 * @param value
	 */
	public void setCellValue(HSSFRow row, int index, java.util.Date value){
		HSSFCell arr[] = (HSSFCell[])cells.get(row);
		//CreationHelper createHelper = wb.getCreationHelper();
		
		setCellValue(arr[index],value);
	}
	
	public void setCellVvalue(int row, int column , java.util.Date value){
	    setCellValue(getRow(row), column, value);
	}
	

	/**
	* ���뿉 媛믪쓣 �꽕�젙
	*
	* @see setCellValue(HSSFCell, int )
	* @ param cell : 媛믪쓣 �꽕�젙�븷 Cell
	* @ param value : �꽕�젙�븷 媛�
	* @return
	* @exception
	*/

	public void setCellValue(HSSFCell cell, int value){
	  cell.setCellValue(value);
	}

	/**
	* ���뿉 媛믪쓣 �꽕�젙
	*
	* @see setCellValue(HSSFCell, String )
	* @ param cell : 媛믪쓣 �꽕�젙�븷 Cell
	* @ param value : �꽕�젙�븷 媛�
	* @return
	* @exception
	*/


	public void setCellValue(HSSFCell cell, String value){
	  setEncoding(cell);
	  cell.setCellValue(value);
	}

	/**
	* ���뿉 媛믪쓣 �꽕�젙
	*
	* @see setCellValue(HSSFCell, java.util.Date )
	* @ param cell : 媛믪쓣 �꽕�젙�븷 Cell
	* @ param value : �꽕�젙�븷 媛�
	* @return
	* @exception
	*/


	public void setCellValue(HSSFCell cell, java.util.Date value){
	  cell.setCellValue(value);
	}

	/**
	 * 
	 * @param cell
	 * @param value
	 */
	public void setCellValue(HSSFCell cell, java.util.Calendar value){
		  cell.setCellValue(value);
	}
	

	/**
	* ���뿉 媛믪쓣 �꽕�젙
	*
	* @see setCellValue(HSSFCell, boolean )
	* @ param cell : 媛믪쓣 �꽕�젙�븷 Cell
	* @ param value : �꽕�젙�븷 媛�
	* @return
	* @exception
	*/


	public void setCellValue(HSSFCell cell, boolean value){
	  cell.setCellValue(value);
	}

	/**
	* ���뿉 媛믪쓣 �꽕�젙
	*
	* @see setCellValue(HSSFCell, double )
	* @ param cell : 媛믪쓣 �꽕�젙�븷 Cell
	* @ param value : �꽕�젙�븷 媛�
	* @return
	* @exception
	*/


	public void setCellValue(HSSFCell cell, double value){
	  cell.setCellValue(value);
	}


	/**
	* �� 蹂묓빀
	*
	* @see mergeCell(int, int , int, int )
	* @ param rowFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Row 醫뚰몴
	* @ param colFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Column 醫뚰몴
	* @ param rowTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Row 醫뚰몴
	* @ param colTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Column 醫뚰몴
	* @return
	* @exception
	*/

	public void mergeCell(int rowFrom, int colFrom, int rowTo, int colTo){
	  mergeCell( getMergedRegion(rowFrom,(short)colFrom,rowTo,(short)colTo) );
	}

	/**
	* �� 蹂묓빀
	*
	* @see mergeCell(HSSFSheet ,int, int , int, int )
	* @ param sheet :sheet 蹂묓빀�븷 cell�쓣 �룷�븿�븯怨� �엳�뒗 sheet
	* @ param rowFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Row 醫뚰몴
	* @ param colFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Column 醫뚰몴
	* @ param rowTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Row 醫뚰몴
	* @ param colTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Column 醫뚰몴
	* @return
	* @exception
	*/

	public void mergeCell(HSSFSheet sheet, int rowFrom, int colFrom, int rowTo, int colTo){
	  mergeCell(sheet, getMergedRegion(rowFrom,colFrom,rowTo,colTo) );
	}

	/**
	* �� 蹂묓빀
	*
	* @see mergeCell(Region)
	* @ param  region : 蹂묓빀�븷 吏��뿭
	* @return
	* @exception
	*/

	public void mergeCell(CellRangeAddress region){
	  sheet.addMergedRegion(region);
	  //sheet.addMergedRegionUnsafe((CellRangeAddress)region);
	  
	}

	/**
	* �� 蹂묓빀
	*
	* @see mergeCell(HSSFSheet , Region region)
	* @ param sheet : 蹂묓빀�븷 cell�쓣 �룷�븿�븯怨� �엳�뒗 cell
	* @ param region : 蹂묓빀�븷 援ъ뿭
	* @return
	* @exception
	*/

	public void mergeCell(HSSFSheet sheet, CellRangeAddress region){
	  sheet.addMergedRegion(region);
	}

	/**
	* 蹂묓빀�븷 Cell�쓽 援ъ뿭�쓣 �깮�꽦
	*
	* @see getMergedRegion(int rowFrom, short colFrom, int rowTo, short colTo)
	* @ param rowFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Row 醫뚰몴
	* @ param colFrom : 蹂묓빀�븷 ���쓽 醫뚯긽�떒 Column 醫뚰몴
	* @ param rowTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Row 醫뚰몴
	* @ param colTo : 蹂묓빀�븷 ���쓽 �슦�븯�떒 Column 醫뚰몴
	* @return Region
	* @exception
	*/

	/*
	public Region getMergedRegion(int rowFrom, int colFrom, int rowTo, int colTo){
	  return new Region(rowFrom,(short)colFrom,rowTo,(short)colTo);
	}//*/
	
	public CellRangeAddress getMergedRegion(int rowFrom, int colFrom, int rowTo, int colTo){
		  return new CellRangeAddress(rowFrom,(short)colFrom,rowTo,(short)colTo);
	}
	

	/**
	* 諛붾떏湲� �꽕�젙
	*
	* @see setFooter()
	* @ param
	* @return
	* @exception
	*/
	public void setFooter(){
	  HSSFFooter footer = sheet.getFooter();
	  footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
	}//*/

	/**
	* 諛붾떏湲� �꽕�젙
	*
	* @see setFooter(HSSFSheet)
	* @ param sheet : 諛붾떏湲��쓣 �꽕�젙�븷 SHEET
	* @return
	* @exception
	*/
	public void setFooter(HSSFSheet sheet){
	  HSSFFooter footer = sheet.getFooter();
	  footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
	}//*/



	/**
	* �뿊�뀥 �뙆�씪 ���옣
	*
	* @see writeXLS(HSSFWorkbook ,String , String)
	* @ param wb: �옉�뾽�옣
	* @ param path: �뿊�� �뙆�씪 ���옣 寃쎈줈
	* @ param name: ���옣�맆 �뙆�씪 �씠由�
	* @return
	* @exception IOException
	*/

	public void writeXLS(HSSFWorkbook wb,String path, String name) throws IOException{
	  FileOutputStream fout = getFileTarget(path,name);
	  wb.write( fout );
	  fout.close();//*/
	}

	/**
	 * 
	 * 二쇱뼱吏� 寃쎈줈�뿉 �뿊��濡� �뙆�씪 ���옣
	*
	* @see writeXLS(String , String)
	* @ param path: �뿊�� �뙆�씪 ���옣 寃쎈줈
	* @ param name: ���옣�맆 �뙆�씪 �씠由�
	* @return
	* @exception IOException
	*/
	public void writeXLS(String path,String name) throws IOException{
		FileOutputStream fout = getFileTarget(path,name);
		wb.write( fout );
		fout.close();//*/
	}
	
	
	/**
	 * 
	 * @param res
	 * @throws IOException
	 */
	public void writeXLS(HttpServletResponse res, String fileName) throws IOException{
		res.setHeader( "Content-Type", "application/octet-stream; charset=euc-kr");
		res.setHeader( "Content-Disposition", "attachment;filename=" + fileName +";" );		
		wb.write(res.getOutputStream());	
	}

	
	

	/**
	 * 二쇱뼱吏� 寃쎈줈�뿉 二쇱뼱吏� �뙆�씪 �씠由꾩쑝濡� �뙆�씪�쓣 �깮�꽦�븷 �닔 �엳�뒗 FileOutputStream�쓣 
	 * �깮�꽦�븯�뒗 硫붿냼�뱶, 留뚯빟 二쇱뼱吏� 寃쎈줈�뿉 二쇱뼱吏� �뙆�씪 �씠由꾩쓣 媛�吏� �뙆�씪�씠 議댁옱�븷 寃쎌슦
	 * replace �릺�뼱 吏꾨떎. 
	 * 
	 * @param path �뙆�씪 寃쎈줈
	 * @param name �뙆�씪 �씠由�
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private FileOutputStream getFileTarget(String path, String name) throws FileNotFoundException, IOException {
		FileOutputStream fout = new FileOutputStream(getFile(path, name));
		return fout;
	}

	/**
	 * 二쇱뼱吏� 寃쎈줈�� �씠由꾩쓣 媛뽯뒗 File instance瑜� 諛섑솚
	 * @param path �뙆�씪 寃쎈줈
	 * @param name �뙆�씪 �씠由�
	 * @return 二쇱뼱吏� 寃쎈줈�� �씠由꾩쓣 媛뽯뒗 File instance
	 * @throws IOException
	 */
	private File getFile(String path, String name) throws IOException {
	  return new File(path + File.separator + name);
	}
	
	
	
	public static void main(String args[]) throws java.io.IOException{

		System.out.println(2^32);
	    
	    try{
			XcelWriter excel = new XcelWriter();
			
			excel.setHSSFWorkBook();
			excel.createSheet("test");
			excel.createRow(0);
			excel.createRow(1);
			excel.createRow(2);
	
			excel.createCells(excel.getRow(0), 6);
			excel.createCells(excel.getRow(1), 6);
			excel.createCells(excel.getRow(2), 6);
			
			excel.createCells(excel.createRow(3),6);
		
			excel.mergeCell(0,0,1,0);
			excel.mergeCell(0,2,1,2);
			excel.mergeCell(0,4,1,4);
		
			excel.mergeCell(2,0,2,1);
			excel.mergeCell(2,2,2,3);
			excel.mergeCell(2,4,2,5);
		
			excel.setCellValue(excel.getRow(0), 0, "00,10");
			excel.setCellValue(excel.getRow(0), 1, "01");		
			excel.setCellValue(excel.getRow(1), 1, "11");
		
	
			excel.setCellValue(excel.getRow(0), 2, "02,12");
			excel.setCellValue(excel.getRow(0), 3, "03");
			excel.setCellValue(excel.getRow(1), 3, "13");
		
			excel.setCellValue(excel.getRow(0), 4, "04,14");
			excel.setCellValue(excel.getRow(0), 5, "05");
			excel.setCellValue(excel.getRow(1), 5, 1.0);
		
			excel.setCellValue(excel.getRow(2), 0, true);
			//excel.setCellValue(excel.getRow(2), 2, new java.util.Date());
			excel.setCellValue(excel.getRow(2), 2, Calendar.getInstance());
			excel.setCellValue(excel.getRow(2), 4, 10);
			
			excel.setCellValue(3,0,"temp");
			excel.setCellValue(3,1,"djflkdjlk");
			
		
			excel.writeXLS("c:\\temp","dulee.xls");
		
	    }catch(Exception e){
	        e.printStackTrace();
	    }
		
	}

	
}