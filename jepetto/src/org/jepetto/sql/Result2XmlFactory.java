package org.jepetto.sql;

import java.beans.Beans;
import java.io.IOException;

/**
 * 
 * @(#) Result2XmlFactory.java 
 *
 * <pre> 
 * this factory class provides instance that transfer resultset to xml
 * </pre>
 * 
 * @author 김창호
 * @version 1.0 2006.02.10
 * @ see IResult2Xml.java Result2Xml.java Result2Properties.java
 *
 */
public class Result2XmlFactory {

    private static Result2XmlFactory instance = new Result2XmlFactory();
    private Result2XmlFactory(){}

    public static Result2XmlFactory getInstance(){
		return instance;
    }

	/**
	 * provide given class's instance  that  transfer resultset to xml
	 * @param className
	 * @return IResult2Xml instance
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
    public IResult2Xml getInstance(String className) throws ClassNotFoundException,IOException {
		IResult2Xml rx = (IResult2Xml)Beans.instantiate( super.getClass().getClassLoader(),className);
		return rx;
    }

}
