package org.jepetto.sql;

import java.sql.SQLException;
import org.jdom2.JDOMException;

/**
 * 
 * xpath로 xml 내용을 참조할 때 발생되는 예외사항
 *  
 * 
 * @author umlkorea 김창호
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class XQLException  extends SQLException{
	
	
	public XQLException(JDOMException e){
		super(e.getMessage());
    }
	public XQLException(java.lang.NullPointerException e){
		super(e.getMessage());
    }


}
