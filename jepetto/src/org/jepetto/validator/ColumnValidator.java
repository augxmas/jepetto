package org.jepetto.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnValidator {

	private String query;
	int columnIndex ;
	private String type;
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String pattern;
	
	int min;
	int max;
	
	private String value;
	
	public ColumnValidator(String query, int columnIndex, String pattern, String min, String max){
		this.query		= query;
		this.pattern	= pattern;
		this.min		= Integer.parseInt(min);
		this.max		= Integer.parseInt(max);
		this.columnIndex = columnIndex;
	}
	
	public ColumnValidator(int columnIndex, String type, String pattern, String min, String max){
		//this.query		= query;
		this.columnIndex = columnIndex;
		this.type		= type;
		this.pattern	= pattern;
		try{
			this.min		= Integer.parseInt(min);
		}catch(java.lang.NumberFormatException e){
			//e.printStackTrace();
			this.min = -1;
		}
		try{
			this.max		= Integer.parseInt(max);
		}catch(java.lang.NumberFormatException e){
			//e.printStackTrace();
			this.max = -1;
		}
		
		
	}
	
	private boolean isLimitOver(String value){
		
		boolean flag = false;
		
		if(type.equalsIgnoreCase("NUMERIC")){
			flag = true;
			return flag;
		}
		
		try{

			if(max == -1){
				flag = false;
			}
			
			if(max != -1 && value.length() <= max && min != -1 && value.length() >= min){
				flag = true;
			}
			
			if(min != -1 && value.length() >= min){
				flag = true;
			}
			
		}catch(java.lang.NullPointerException e){
			
			if(min != -1) {
				flag = true;
			}
			
		}
		
		return flag;
	}
	
	public boolean validator(String value, int rowIndex) throws Exception{
		
		boolean isLimit = false;
		StringBuffer errorMsg = new StringBuffer();
		
		if(isLimitOver(value)){
			isLimit = true;
		}else{
			errorMsg.append("cell[ ");
			errorMsg.append(rowIndex);
			errorMsg.append(" , ");
			errorMsg.append(columnIndex+1);
			errorMsg.append(" ] data size is error");
			//errorMsg.append("\n");
		}
		
		boolean isPattern = false;
		if(isPattern(value)){
			isPattern = true;
		}else{
			errorMsg.append("cell[ ");
			errorMsg.append(columnIndex+1);
			errorMsg.append(" , ");
			errorMsg.append(rowIndex+1);
			errorMsg.append(" ] data pattern is error. ");
			//errorMsg.append("\n");
			errorMsg.append("requested data pattern is ");
			errorMsg.append(this.pattern);
			errorMsg.append(" requested data type is ");
			errorMsg.append(this.type);			
			//errorMsg.append("\n");
		}
		
		errorMsg.append("input data is ");
		errorMsg.append(value);
		
		boolean flag = isLimit && isPattern;
		if(!flag){
			throw new Exception(errorMsg.toString());
		}
		
		return flag;
	}
	
	private boolean isPattern(String value){
		boolean flag = false;	
		if(type.equalsIgnoreCase("NUMERIC")){
			return true;
		}
		try{	
			if(pattern.trim().length() == 0){
				flag = true;
			}else{
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(value);
				flag = m.matches();				
			}
		}catch(java.lang.NullPointerException e){
			flag = true;
		}
		
		return flag;
	}

}
