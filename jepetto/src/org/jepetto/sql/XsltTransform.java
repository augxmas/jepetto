package org.jepetto.sql;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformException;
import org.jdom2.transform.XSLTransformer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class XsltTransform {

	
	
	public static Document getXSLTransformer(String style,Document doc) throws XSLTransformException{
		return new XSLTransformer(style).transform(doc);
	}
	
	public static Document transform(String path, String file,Document doc) throws XSLTransformException{
		File f = new File(path,file);
		return new XSLTransformer(f).transform(doc);
	}
	
	
	public static void save(OutputStream out, Document doc) throws IOException{
		new XMLOutputter().output(doc,out);
	}

}
