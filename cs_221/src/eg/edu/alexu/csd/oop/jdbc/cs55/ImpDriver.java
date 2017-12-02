package eg.edu.alexu.csd.oop.jdbc.cs55;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class ImpDriver implements java.sql.Driver {
	private Properties info;
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		
		return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		File dir = (File) info.get("path");
	    String path = dir.getAbsolutePath();
	    Connection con = new ImpConnection(path);
	    return con; 
	}
	
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		//DriverPropertyInfo[] i = new DriverPropertyInfo(info.get, value);
		return null;
	}
	
//-------------------------------------------------------------------------------------------------------------
	@Override
	public int getMajorVersion() {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public boolean jdbcCompliant() {
		// 
		throw new java.lang.UnsupportedOperationException();
	}
}
