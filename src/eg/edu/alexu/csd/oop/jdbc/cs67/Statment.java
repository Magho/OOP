package eg.edu.alexu.csd.oop.jdbc.cs67;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class Statment implements java.sql.Statement{

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		
	}

	@Override
	public void cancel() throws SQLException {
		
	}

	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		
	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return false;
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return null;
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return null;
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stubsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		return null;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssss
		return 0;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stubssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss	
	}
}
