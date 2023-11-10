package org.jepetto.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.jepetto.logger.DisneyLogger;

/**
 * <pre>
 *  
 *  xls file reader 濡쒖꽌 �븘�옒�� 媛숈씠 �궗�슜�븳�떎
 * 
 * 	String arr[] = {"�궗�뾽�냼肄붾뱶","1李⑥궗�뾽�냼紐�","2李⑥궗�뾽�냼紐�"}     // 李몄“�븯怨좎옄 �븯�뒗 �뿊�� 而щ읆紐�
 * 	XcelReader reader = new XcelReader("C:/temp/", "second.xls"); // �뙆�씪 寃쎈줈�� �뙆�씪 紐�
 *                                        
 *	while( reader.hasSheet() ){	                                  // sheet�쓽 議댁옱 �쑀臾�
 *								  
 *		reader.hasRow(); // skip column title                     // �븘�슂濡� �븯�뒗 raw data�쇅 ���씠�� 紐� �뵲�쐞�뒗 �씠�젃寃� skip�븳�떎.
 *                  
 *		while( reader.hasRow() ){                                 // sheet�뿉 row�쓽 議댁옱�쑀臾�
 *
 *			String s  = reader.getValue(1);                       // non zero base index, column index濡쒖꽌 cell 媛믪쓣 李몄“
 *			String s1 = reader.getValue(2);
 *			String s2 = reader.getValue(3);
 *		}
 *	}
 *
 * </pre>
 * 
 * @author umlkorea 源�李쏀샇
 *
 */
public class XcelReader {

	DisneyLogger cat = new DisneyLogger(XcelReader.class.getName());

	/**
	 * �씫�뼱�뱾�씪 excel臾몄꽌 寃쎈줈
	 */
	private String path;

	/**
	 * �씫�뼱�뱾�씤 excel �뙆�씪 紐�
	 */
	private String fileName;

	/**
	 * binary excel file reader
	 */
	private POIFSFileSystem fs;

	/**
	 * excel workbook
	 */
	private HSSFWorkbook wb;

	/**
	 * excel header �씠由꾨뱾
	 */
	private String columns[];

	/**
	 * cursor媛� �쐞移� row
	 */
	private HSSFRow cusorRow;

	/**
	 * cursor媛� �쐞移섑븳 sheet
	 */
	private HSSFSheet cusorSheet;

	/**
	 * cursor媛� �쐞移섑븳 cell
	 */
	private HSSFCell cusorCell;

	/**
	 * display�븷 row�쓽 媛��닔
	 */
	private int unit;

	/**
	 * row index, row cursor�쓽 �쐞移�
	 */
	private int iRowIndex = 0;

	/**
	 * sheet index, sheet cursor�쓽 �쐞移�
	 */
	private int iSheetIndex = 0;

	/**
	 * cell index, cell cursor�쓽 �쐞移�
	 */
	private int iCellIndex = 0;

	/**
	 * sheet�쓽 議댁옱 �쑀臾�
	 */
	private boolean hasSheet = false;

	/**
	 * row�쓽 議댁옱 �쑀臾�
	 */
	private boolean hasRow = false;

	/**
	 * cell 議댁옱 �쑀臾�
	 */
	private boolean hasCell = false;

	/**
	 * excel �뙆�씪�뿉 ���븳 inputstream
	 */
	private FileInputStream fin;

	/**
	 * when making csv type file, it's used such as delemeter
	 */
	private String del = "\t";

	/**
	 * when cell has no value, put period.
	 */
	private String period = ".";

	/**
	 * carriage and return
	 */
	private String newLine = "\n";

	/**
	 * when csv type excel file, it's excel file'suffix
	 */
	private String suffix = "csv";

	/**
	 * 二쇱뼱吏� 寃쎈줈�뿉�꽌 �빐�떦 excel �뙆�씪�쓣 �씫�뼱 load �떆�궓�떎
	 * 
	 * @param path     excel �뙆�씪�쓽 寃쎈줈
	 * @param fileName excel �뙆�씪 �씠由�
	 * @throws IOException
	 */
	public XcelReader(String path, String fileName) throws IOException { // ,int unit) throws IOException{

		this.path = path;
		// this.unit = unit;
		try {
			this.fileName = fileName;
			load();
		} catch (IOException e) {
			e.printStackTrace();
			this.fileName = fileName + ".xls";
		}

		loadWorkBook();

	}

	public XcelReader() {
	}

	/**
	 * excel �뙆�씪 load
	 * 
	 * @throws IOException
	 */
	private void load() throws IOException, OfficeXmlFileException, NotOLE2FileException {
		File file = new File(path, fileName);
		FileInputStream fin = new FileInputStream(file);
		this.fin = fin;
		POIFSFileSystem fs = null;
		try{
			fs = new POIFSFileSystem(fin);
			this.fs = fs;// = new POIFSFileSystem(fin);
		}catch(OfficeXmlFileException e) {
			e.printStackTrace();
		}catch(NotOLE2FileException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * load specific excle file
	 * 
	 * @param path
	 * @param fileName
	 * @param unit
	 * @throws IOException
	 */
	public void load(String path, String fileName, int unit) throws IOException {

		this.path = path;
		this.unit = unit;
		// isOLE = true;
		try {
			this.fileName = fileName;
			load();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OfficeXmlFileException e) {
			e.printStackTrace();
		}
		loadWorkBook();

	}

	/**
	 * excel inputstream �쓣 close �븯�뒗 硫붿냼�뱶
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		fin.close();
	}

	/**
	 * excel workbook�쓣 濡쒕뱶�떆�궡
	 * 
	 * @throws IOException
	 */
	private void loadWorkBook() throws IOException {

		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			wb = new HSSFWorkbook(fin);
		} 
		this.wb = wb;

	}

	/**
	 * 二쇱뼱吏� �씤�뜳�뒪�뿉 �빐�떦�븯�뒗 sheet瑜� 諛섑솚. zero base index
	 * 
	 * @param 李몄“�븯怨좎옄 �븯�뒗 sheet�쓽 �씤�뜳�뒪
	 * @return �빐�떦 �씤�뜳�뒪�쓽 sheet
	 */
	public HSSFSheet getSheet(int index) {
		HSSFSheet sheet = wb.getSheetAt(index);
		return sheet;
	}

	/**
	 * 二쇱뼱吏� 諛곗뿴�쓣 excel 臾몄꽌�쓽 column(header)�쑝濡� �궪�뒗�떎
	 * 
	 * @param columns column紐낅뱾
	 */
	public void setColumns(String columns[]) {
		this.columns = columns;
	}

	/**
	 * column names�쓣 諛섑솚�븯�뒗 硫붿냼�뱶
	 * 
	 * @return column names
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * �빐�떦 sheet�뿉�꽌 二쇱뼱吏� index�뿉 �빐�떦�븯�뒗 row瑜� 諛섑솚�븯�뒗 硫붿냼�뱶
	 * 
	 * @param sheet sheet
	 * @param index 李몄“�븯怨좎옄 �븯�뒗 濡쒖슦 �씤�뜳�뒪
	 * @return HSSFRow �빐�떦 �씤�뜳�뒪�쓽 sheet�쓽 row
	 */
	public HSSFRow getRow(HSSFSheet sheet, int index) {
		return sheet.getRow(index);
	}

	/**
	 * �쁽�옱 cursor媛� �쐞移섑븳 sheet�뿉�꽌 二쇱뼱吏� �씤�뜳�뒪�뿉 �빐�떦�븯�뒗 row瑜� 諛섑솚
	 * 
	 * @param index 李몄“�븯怨좎옄 �븯�뒗 row index
	 * @return HSSFRow
	 */
	public HSSFRow getRow(int index) {
		return cusorSheet.getRow(index);
	}

	/**
	 * 二쇱뼱吏� index濡� sheet cursor瑜� �쐞移섏떆�궓�떎
	 * 
	 * @param iSheetIndex sheet index
	 */
	public void setSheetIndex(int iSheetIndex) {
		this.iSheetIndex = iSheetIndex;
	}

	/**
	 * 二쇱뼱吏� index濡� row cursor瑜� �쐞移섏떆�궓�떎
	 * 
	 * @param iRowIndex sheet index
	 */
	public void setRowIndex(int iRowIndex) {
		this.iRowIndex = iRowIndex;
	}

	/**
	 * 二쇱뼱吏� index濡� cell cursor瑜� �쐞移섏떆�궓�떎
	 * 
	 * @param iCellIndex sheet index
	 */
	public void setCellIndex(int iCellIndex) {
		this.iCellIndex = iCellIndex;
	}

	/**
	 * cursor媛� sheet瑜� 媛뽮퀬 �엳�뒗吏� �뙋�떒�븯�뒗 硫붿냼�뱶, �닚諛⑺뼢�쑝濡쒕쭔 sheet 寃��깋�쓣 �븷 �닔
	 * �엳�떎
	 * 
	 * @return sheet�쓽 議댁옱 �쑀遺�
	 */
	public boolean hasSheet() {

		HSSFSheet sheet = null;
		try {
			sheet = this.getSheet(iSheetIndex);
		} catch (Exception e) {
			return false;
		}

		iSheetIndex++;
		this.cusorSheet = sheet;
		if (sheet != null) {
			hasSheet = true;
		} else {
			hasSheet = false;
			iSheetIndex = 0;
		}
		return hasSheet;
	}

	/**
	 * positioning �맂 sheet�뿉 row瑜� 媛뽮퀬 �엳�뒗吏� �뙋�떒�븯�뒗 硫붿냼�뱶 �닚諛⑺뼢�쑝濡쒕쭔 row 寃��깋�쓣
	 * �븷 �닔 �엳�떎
	 * 
	 * @return row�쓽 議댁옱 �쑀臾�
	 */
	public boolean hasRow() {
		if (hasSheet) {
			HSSFRow row = getRow(iRowIndex);
			iRowIndex++;
			this.cusorRow = row;
			if (row != null) {
				hasRow = true;
			} else {
				hasRow = false;
				hasSheet = false;
				iRowIndex = 0;
			}
		} else {
			hasRow = false;
			hasSheet = false;
		}
		return hasRow;
	}

	/**
	 * positioning�맂 row�뿉 cell�쓣 媛뽮퀬 �엳�뒗吏� �뙋�떒�븯�뒗 硫붿냼�뱶 �닚諛⑺뼢�쑝濡쒕쭔 cell 寃��깋�쓣
	 * �븷 �닔 �엳�떎
	 * 
	 * @return cell�쓽 議댁옱 �쑀臾�
	 */
	public boolean hasCell() {
		if (hasSheet && hasRow) {
			HSSFCell cell = getCell(iCellIndex);
			iCellIndex++;
			this.cusorCell = cell;
			if (cell != null) {
				hasCell = true;
			} else {
				hasCell = false;
				iCellIndex = 0;
			}
		}
		return hasCell;
	}

	/**
	 * 二쇱뼱吏� row�쓽 index�뿉 �빐�떦�븯�뒗 cell�쓣 諛섑솚�븯�뒗 硫붿냼�뱶
	 * 
	 * @param 李몄“ ���긽�씠 �릺�뒗 row
	 * @param 李몄“ �씤�뜳�뒪
	 * @return 二쇱뼱吏� 議곌굔�뿉 留뚯”�븯�뒗 cell
	 */
	public HSSFCell getCell(HSSFRow row, int index) {
		return row.getCell((short) index);
	}

	/**
	 * positioning�맂 row�쓽 index�뿉 �빐�떦�븯�뒗 cell�쓣 諛섑솚
	 * 
	 * @param 李몄“�븷 cell index
	 * @return �빐�떦 �씤�뜳�뒪�쓽 cell
	 */
	private HSSFCell getCell(int index) {

		return cusorRow.getCell(index + 1);
	}

	/**
	 * get given cell'value as String . ignoring cell'type
	 * 
	 * @param cusorCell
	 * @return cell's value
	 */
	public String getValue(HSSFCell cusorCell) { // , int index){

		String value = null;

		CellType type = cusorCell.getCellType();

		if (type.toString().equalsIgnoreCase(CellType.BLANK.toString())) {
			value = null;
		} else if (type.toString().equalsIgnoreCase(CellType.BOOLEAN.toString())) {
			value = new Boolean(cusorCell.getBooleanCellValue()).toString(); // return boolean
		} else if (type.toString().equalsIgnoreCase(CellType.ERROR.toString())) {
			value = new Byte(cusorCell.getErrorCellValue()).toString(); // return byte
		} else if (type.toString().equalsIgnoreCase(CellType.FORMULA.toString())) {
			value = new Byte(cusorCell.getErrorCellValue()).toString(); // return byte
		} else if (type.toString().equalsIgnoreCase(CellType.NUMERIC.toString())) {
			value = new Double(cusorCell.getNumericCellValue()).toString(); // return double
		} else if (type.toString().equalsIgnoreCase(CellType.STRING.toString())) {
			value = cusorCell.getStringCellValue();
		}

		return value;
	}

	/**
	 * assign value to given index's cell
	 * 
	 * @param index
	 * @param value
	 */
	public void setValue(int index, String value) {
		HSSFCell cusorCell = getCell(index);
		// int type = -1;
		CellType type = null;
		try {
			type = cusorCell.getCellType();
			// cusorCell.getCellType()
			if (value == null)
				throw new NullPointerException();
		} catch (NullPointerException e) {
			return;
		}

		// cusorCell.setEncoding(HSSFCell.ENCODING_UTF_16);

		if (type.toString().equalsIgnoreCase(CellType.BLANK.toString())) {
			// System.out.println("cell index : " + index);
		}

		else if (type.toString().equalsIgnoreCase(CellType.BOOLEAN.toString())) {
			cusorCell.setCellValue(Boolean.getBoolean(value));
		} else if (type.toString().equalsIgnoreCase(CellType.NUMERIC.toString())) {
			// System.out.println("tyep numeric cell index : " + index + " cell value : " +
			// value);
			try {
				cusorCell.setCellValue(Integer.parseInt(value));
			} catch (Exception e) {
				cusorCell.setCellValue(Double.parseDouble(value));
			}
		} else if (type.toString().equalsIgnoreCase(CellType.STRING.toString())) {
			// System.out.println("type String cell index : " + index + " cell value : " +
			// value);
			cusorCell.setCellValue(value);
		} else if (type.toString().equalsIgnoreCase(CellType.FORMULA.toString())) {
			cusorCell.setCellFormula(value);
		}

		// */
	}

	/**
	 * assign value to given cell.
	 * 
	 * @param cusorCell
	 * @param value
	 */
	public void setValue(HSSFCell cusorCell, String value) {

		// int type = cusorCell.getCellType();

		CellType type = cusorCell.getCellType();

		if (type.toString().equalsIgnoreCase(CellType.BLANK.toString())) {

		}

		else if (type.toString().equalsIgnoreCase(CellType.BOOLEAN.toString())) {
			cusorCell.setCellValue(Boolean.getBoolean(value));
		}

		else if (type.toString().equalsIgnoreCase(CellType.NUMERIC.toString())) {
			try {
				cusorCell.setCellValue(Integer.parseInt(value));
			} catch (Exception e) {
				cusorCell.setCellValue(Double.parseDouble(value));
			}
		}

		else if (type.toString().equalsIgnoreCase(CellType.STRING.toString())) {
			cusorCell.setCellValue(value);
		}

		else if (type.toString().equalsIgnoreCase(CellType.FORMULA.toString())) {
			cusorCell.setCellFormula(value);
		}

		// */
	}

	/**
	 * 二쇱뼱吏� 寃쎈줈�뿉 excel �뙆�씪�쓣 �깮�꽦�븳�떎
	 * 
	 * @param path
	 * @param name
	 * @throws java.io.FileNotFoundException
	 * @throws IOException
	 */
	public void write(String path, String name) throws java.io.FileNotFoundException, IOException {
		java.io.FileOutputStream fout = new java.io.FileOutputStream(new java.io.File(path, name));
		try {
			wb.write(fout);
			fout.close();// */
		} finally {
			fout.close();
		}
	}

	/**
	 * excel sheet copy
	 * 
	 * @param sheet
	 * @param _sheet
	 */
	public void copy(HSSFSheet sheet, HSSFSheet _sheet) {

		HSSFRow row = sheet.getRow(0);
		HSSFRow _row = null;

		if (row != null) {
			_row = _sheet.createRow(0);
		}

		Iterator iter = row.cellIterator();
		int count = 0;
		while (iter.hasNext()) {
			iter.next();
			++count;
		}

		short arr[] = new short[count];
		count = 0;

		while (row != null) {

			for (int i = 0; i < arr.length; i++) {
				_row = _sheet.createRow(count);

				HSSFCell cell = row.getCell((short) i);
				HSSFCell _cell = _row.createCell((short) i);
				// _cell.setEncoding(HSSFCell.ENCODING_UTF_16);

				if (cell != null) {

					_cell.setCellType(cell.getCellType());
					setValue(_cell, getValue(cell));

				}

			}
			count++;
			row = sheet.getRow(count);

		}

	}

	/**
	 * positioning�맂 sheet -> row �뿉�꽌 二쇱뼱吏� �씤�뜳�뒪�쓽 cell�쓽 媛믪쓣 諛섑솚�븯�뒗 硫붿냼�뱶
	 * 
	 * @param 李몄“�븷 cell�쓽 index
	 * @return cell's value
	 */
	public String getValue(int index) {

		String value = null;

		HSSFCell cusorCell = getCell(index);
		// int type = -1;
		CellType type = null;
		try {
			type = cusorCell.getCellType();
		} catch (NullPointerException e) {
			value = "";
			return value;
		}

		if (type.toString().equalsIgnoreCase(CellType.BLANK.toString())) {
			value = "";
		} else if (type.toString().equalsIgnoreCase(CellType.BOOLEAN.toString())) {

			value = new Boolean(cusorCell.getBooleanCellValue()).toString(); // return boolean

		} else if (type.toString().equalsIgnoreCase(CellType.ERROR.toString())) {

			value = new Byte(cusorCell.getErrorCellValue()).toString(); // return byte

		} else if (type.toString().equalsIgnoreCase(CellType.FORMULA.toString())) {

			try {
				value = new Byte(cusorCell.getErrorCellValue()).toString(); // return byte
			} catch (Exception e) {
				// System.out.println("sheet : " + this.iSheetIndex + " rowIndex : " +
				// this.iRowIndex + " columnIndex " + this.iCellIndex);
			}

		} else if (type.toString().equalsIgnoreCase(CellType.NUMERIC.toString())) {

			value = new Integer((int) cusorCell.getNumericCellValue()).toString(); // return double

		} else if (type.toString().equalsIgnoreCase(CellType.STRING.toString())) {

			value = cusorCell.getStringCellValue();

		}

		// value = com.exe.util.Util.en(value);
		// value = com.exe.util.Util.ko(value);
		// value = com.exe.util.Util.en(com.exe.util.Util.ko(value));
		// value = com.exe.util.Util.ko(com.exe.util.Util.en(value));
		return value;
	}

	/**
	 * 二쇱뼱吏� 寃쎈줈�뿉 濡쒕뵫�릺�뼱 �엳�뒗 xls瑜� csv濡� 蹂��솚. cell�뿉 媛믪씠 議댁옱�븯吏� �븡�쓣 寃쎌슦 '.'濡쒖꽌
	 * ��泥댄븯�뿬 �깮�꽦�븳�떎
	 * 
	 * @param path
	 * @param file
	 * @param unit       no meaning
	 * @param skip       column title rows
	 * @param columnSize
	 * @throws IOException
	 */
	public void tranXls2Txt(String path, String file, int unit, int skip, int columnSize) throws IOException {

		load(path, file, unit);
		StringBuffer sb = new StringBuffer();
		String value = null;
		file = file.substring(0, file.indexOf("."));

		File f = new File(path, file + "." + suffix);
		if (f.exists())
			f.delete();
		OutputStream out = new FileOutputStream(f, true);

		try {

			while (hasSheet()) {
				for (int i = 0; i < skip; i++) {
					hasRow();
				} // for
				while (hasRow()) {
					for (int i = 0; i < columnSize; i++) {
						try {
							value = getValue(i);
							try {
								sb.append(value.trim());
								if (value.trim().length() == 0) {
									sb.append(period);
								}

							} catch (NullPointerException e) {
								// e.printStackTrace();
								
								sb.append(period);
							}
							sb.append(del);

						} catch (NullPointerException e) {
							sb.append(period);
							sb.append(del);
						} // outter try/catch

					} // for

					sb.append(newLine);

					out.write(sb.toString().getBytes());
					sb.delete(0, sb.toString().getBytes().length);

				} // inner while

			} // outter while

		} catch (Exception e) {
			 e.printStackTrace();
			
		} finally {
			out.close();
		}

	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public static void main(String args[]) {
		char c = 'A';
		int row = 0;

		try {

			// XcelReader reader = new XcelReader();//("D:/java/test","emp.xls");
			// reader.setPeriod("-");
			// reader.tranXls2Txt("D:/java/test","emp.xls",0,0,7);

			// XcelReader reader = new XcelReader("D:/java/test","emp.xls");
			// XcelReader reader = new XcelReader("c:\\temp","receipt.xlsx");
			XcelReader reader = new XcelReader("c:/temp","test.csv");
			//XcelReader reader = new XcelReader("c:/temp", "sample.xlsx");
			//XcelReader reader = new XcelReader("c:/temp", "sample.xls");
			reader.hasSheet();
			// if(reader.hasRow()){
			while (reader.hasRow()) {
				row++;
				for (int i = 0; i < 8; i++) {
					System.out.print(reader.getValue(i) + "\t");
				}
				System.out.println();
			}
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
