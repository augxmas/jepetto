package org.jepetto.util;

import java.util.Comparator;
import java.util.HashMap;

//
public class MapComparator implements Comparator<HashMap<String, String>> {
	 
    private final String key;
    private final boolean isDescend;
    
    public MapComparator(String key) {
        this.key = key;
        this.isDescend = false;
    }
    
    public MapComparator(String key, boolean isDescend) {
        this.key = key;
        this.isDescend = isDescend;
    }
    
    @Override
    public int compare(HashMap<String, String> first, HashMap<String, String> second) {
        int result = 0;//first.get(key).compareTo(second.get(key));
        
        if(isInteger(first.get(key)) && isInteger(second.get(key))) {
        	result = new Integer(first.get(key)).compareTo(new Integer(second.get(key)));
        } else {
        	result = first.get(key).compareTo(second.get(key));
        }
        if(isDescend) {
        	result = result * -1;
        }
        return result;
    }
    
    
    private static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
