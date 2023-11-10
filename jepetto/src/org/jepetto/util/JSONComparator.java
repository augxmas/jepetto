package org.jepetto.util;

import java.util.Comparator;

import org.json.JSONObject;

public class JSONComparator implements Comparator<JSONObject> {
	
	private final String key;
	private final boolean isDescend;
    
    public JSONComparator(String key) {
        this.key = key;
        this.isDescend = false;
    }
    
    public JSONComparator(String key, boolean isDescend) {
        this.key = key;
        this.isDescend = isDescend;
    }
    
    @Override
    public int compare(JSONObject first, JSONObject second) {
        int result = 0;
        
        Object obj =  first.get(key);
        
        // 2017.02.13 JSON의 데이터 타입으로 가져오는 것으로 변경 
        if(obj instanceof Integer){
        	 int fv =  first.getInt(key);
             int sv =  second.getInt(key);
        	
        	result = new Integer(fv).compareTo(new Integer(sv));
        } else if(obj instanceof String){
        	 String fv =  first.getString(key);
             String sv =  second.getString(key);
             result = fv.compareTo(sv);
        } else if(obj instanceof Double) {
        	Double fv = first.getDouble(key);
        	Double sv = second.getDouble(key);
        	
        	result = fv.compareTo(sv);
        } else if(obj instanceof Long) {	
        	Long fv = first.getLong(key);
        	Long sv = second.getLong(key);
        	
        	result = fv.compareTo(sv);
        }
        
        if(isDescend) {
        	result = result * -1;
        }
        return result;
    }

}
