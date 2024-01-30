package org.jepetto.sec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jepetto.sql.Constants;
import org.jepetto.sql.XmlConnection;

public class SSLGenerator {
	
	private static final String delim = "%";
	private static final String name = "private_key";
	private static final String BIT = "bit";
	
	private static String keyGenCmd = null; //"openssl genrsa -des3 -out " + delim + name + delim + ".key " + delim + BIT + delim;
	
	private void generatePrivateKey(String name, String bit) {
		String _keyGenCmd = keyGenCmd.replaceAll(name, name);
		_keyGenCmd = _keyGenCmd.replaceAll(BIT, bit);
	}
	
	
	static String csrGenCmd = null;
	static {
		// "openssl genrsa -des3 -out " + delim + name + delim + ".key " + delim + BIT + delim;
		StringBuffer buffer = new StringBuffer();
		buffer.append("openssl genrsa -des3 -out");
		buffer.append(" %name%.key");
		buffer.append(" %bit%");
		keyGenCmd = buffer.toString();
		
		//openssl req -new -key [name].key -out [name].csr -subj /C=[country]/ST=[stateOrProvince]/L=[locality]/O=[organizationName]/OU=[organizationUnit]/CN=[commonName]/emailAddress=[email]
		buffer = new StringBuffer();
		buffer.append("openssl req");
		buffer.append(" -new");
		buffer.append(" -key");
		buffer.append(" %name%.key");
		buffer.append(" -out %name%.csr");
		buffer.append(" -sub");
		buffer.append(" /C=%country%");
		buffer.append(" /ST=%stateOrProvince%");
		buffer.append(" /L=%locality%");
		buffer.append(" /O=%organizationName%");
		buffer.append(" /OU=%organizationUnit%");
		buffer.append(" /CN=%commonName%");
		buffer.append(" /emailAddress=%emailAddress%");
		csrGenCmd = buffer.toString();
		
	}
	
	private void generateCSR(
			String name, 
			String csr, 
			String country, 
			String stateOrProvince, 
			String locality, 
			String organizationName, 
			String organizationUnit, 
			String commonName, 
			String email) {
		
	}
	
	
	public void generate(Document doc) throws SQLException {
		String name = null;
		String bit = "2048";
		String csr = "";
		String country = null;
		String stateOrProvince = null;
		String locality = null;
		String organizationName = null;
		String organizationUnit = null;
		String commonName = null;
		String email = null;
		
		
		Connection con = new XmlConnection(doc);;
		PreparedStatement stmt = con.prepareStatement(Constants.xpath);
		ResultSet rset = stmt.executeQuery();
		
		
		while(rset.next()) {
			name = rset.getString("name");
		}
		
		
		generatePrivateKey(name,bit);
		generateCSR(
				name,
				csr,
				country,
				stateOrProvince,
				locality,
				organizationName,
				organizationUnit, 
				commonName,
				email
		);
	}
	
	
	public void generatorCsr(String cmd , Map<String,String> map) {
		Set set = map.keySet();
		Iterator iter = set.iterator();
		String key = null;
		String value = null;
		StringBuffer buffer = null;
		while(iter.hasNext()) {
			key = iter.next().toString();
			value = map.get(key);
			buffer = new StringBuffer();
			buffer.append(delim);
			buffer.append(key);
			buffer.append(delim);
			key = buffer.toString();
			cmd = cmd.replaceAll(key, value);
		}
	}
	
}
