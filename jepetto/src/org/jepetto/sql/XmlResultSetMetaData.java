package org.jepetto.sql;
import java.sql.*;
import java.util.*;
import org.jdom2.*;

public class XmlResultSetMetaData implements ResultSetMetaData{

    private List list;

	private Element ele;
    private Attribute attr;
    private Element arr[] = null;
    
    public XmlResultSetMetaData(List list){
        ele = (Element)list.get(0);
		this.list = ele.getChildren();
		Object o[] = list.toArray();
		//arr = new String[o.length];
		//list = ((Element)o[0]).getChildren();
		
    }

    public int getColumnCount() throws SQLException{
        return list.size();
    }

    public boolean isAutoIncrement(int column) throws SQLException{ return false;}

    public boolean isCaseSensitive(int column) throws SQLException{ return true;}

    public boolean isSearchable(int column) throws SQLException{
        return true;
    }

    public boolean isCurrency(int column) throws SQLException{ return false;}

    //
    public int isNullable(int column) throws SQLException{ return -1;}

    public boolean isSigned(int column) throws SQLException{ return false;}
	//
    public int getColumnDisplaySize(int column) throws SQLException{ return -1;}

	//
    public String getColumnLabel(int column) throws SQLException{
		return null;
    }
	//
    public String getColumnName(int column) throws SQLException{ 
    	column = column -1;
    	Element ele = (Element)this.list.get(column);
    	String columnName = ele.getName();
    	return columnName;
    }

    //
    public String getSchemaName(int column) throws SQLException{ return null;}

    //
    public int getPrecision(int column) throws SQLException{ return -1 ;}

    //
    public int getScale(int column) throws SQLException{ return -1;}

    public String getTableName(int column) throws SQLException{ return null;}

    public String getCatalogName(int column) throws SQLException{ return null;}

	//
    public int getColumnType(int column) throws SQLException{ return -1;}

    public String getColumnTypeName(int column) throws SQLException{ return null;}

    public boolean isReadOnly(int column) throws SQLException{ return false;}

    public boolean isWritable(int column) throws SQLException{ return true;}

    public boolean isDefinitelyWritable(int column) throws SQLException{ return true;}

    public String getColumnClassName(int column) throws SQLException{ return null;}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
