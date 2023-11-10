package org.jepetto.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {
	
	public static JSONObject getObject(String filePath) throws IOException, ParseException {
		FileReader reader = null;
		JSONObject obj = null;
		try {
			reader = new FileReader(filePath);
			JSONParser parser = new JSONParser();
			obj = (JSONObject)parser.parse(reader);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		}
		return obj;
	}
	
	public static JSONArray getJSONArray(String params[]) {
		JSONArray arr = new JSONArray();
		List <String>list = new ArrayList<String>();
		for(int i = 0 ; i < params.length ; i++) {
			list.add(0,params[i]);
		}
		for(int i = 0 ; i < params.length ; i++) {
			arr.add((String)list.get(i));
		}
		return arr;
	}
	
}
