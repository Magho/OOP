package eg.edu.alexu.csd.oop.jdbc.cs55;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs55.*;

public class ImpStatement implements java.sql.Statement{

	private int ret = 2;
	private ResultSet rec = null;
	private int timeout = 10;
	private ArrayList<String> batch = new ArrayList<String>();
	private Database db;
	private ResultSet res;
	private Connection con;
	private int updateRows = 0;
	public ImpStatement(Database db,Connection con) {
		
		this.db = db;
		this.con = con;
	}
	@Override
	public void addBatch(String sql) throws SQLException {

		IValidator valid = ValidationFactory.getValidator(sql);
		if(valid.IsValid(sql)){
			batch.add(valid.getSQL());
		}
		else{
			throw new SQLException("inValid SQL");
		}
	}

	@Override
	public void clearBatch() throws SQLException {
	
		this.batch.clear();
	}
	
	@Override
	public void close() throws SQLException {
		//TODO not sure of the implementation
		db = null;
	}
	@Override
	public ResultSet getResultSet() throws SQLException {
		// 
		return rec;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// 
		return ret;
	}	
	@Override
	public boolean execute(String sql) throws SQLException {
	
		boolean response = false;
		try{
			String query = sql.toLowerCase().trim();
			if(query.matches("create.*")||query.matches("drop.*")){
				response = db.executeStructureQuery(query);
			}else if(query.matches("select.*")){
				rec = executeQuery(query);
				if(((ImpResultset) res).resault.length != 0)
					response = true;
			}else if(query.matches("insert.*")){
				ret = db.executeUpdateQuery(query);
				if(updateRows != 0)
					response = true;
			}else if(query.matches("delete.*")||query.matches("update.*")){
				ret = db.executeUpdateQuery(query);
					response = true;
			}
			return response;
		} catch(SQLException e){
			if(sql.toLowerCase().matches("create\\stable\\s.+"))
				throw e;
			return false;
		}
	}
	
	@Override
	public int[] executeBatch() throws SQLException {
		// TODO not sure of the return type
		int updateCount[] = new int[batch.size()];
		int counter = 0;
		for(String query:batch){
			query = query.toLowerCase();
			if(query.contains("create")||query.contains("drop"))
				updateCount[counter] = db.executeStructureQuery(query) ? 1 : 0;
			else {
				updateCount[counter] = db.executeUpdateQuery(query);
			}
			counter++;
		}
		return updateCount;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
	
		Object[][] res = db.executeQuery(sql);
		String name = sql.replaceAll("select\\s.+\\sfrom\\s([a-z0-9_]+).+", "$1");
		Table table = ((DataBaseAdapter)db).SelectedTable;
		
		ResultSet resualt = null;
		ArrayList<String> ColoumnNames = new ArrayList<>();
		System.out.println(table.coloumn.toString());
		for (String key  : table.coloumn.keySet()){
			ColoumnNames.add(key);
		}
		if(sql.contains("*")){
			resualt = new ImpResultset(res,name,ColoumnNames,this);
		//	System.out.println(ColoumnNames);
		}
		else{
			System.out.println("in");
			String coloums = sql.trim().toLowerCase().replaceAll("select\\s+(.+)\\s+from.+", "$1");
			coloums = coloums.replaceAll("\\s*,\\s*", ",");
			ArrayList<String> coloumsName = new ArrayList<String>();
			for(String str:coloums.split(",")){
				coloumsName.add(str);
			}
			resualt = new ImpResultset(res, name, coloumsName, this);
		}
		return resualt;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		//
		//if (db.executeUpdateQuery(sql) == 0) {
			//throw new RuntimeException(sql);
		//}
		return db.executeUpdateQuery(sql);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return con;
	}
	
	@Override
	public int getQueryTimeout() throws SQLException {
	
		return this.timeout;
	}


	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		
		this.timeout = seconds;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------	
	
	
	
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		//
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	
	@Override
	public void cancel() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public void clearWarnings() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	
	@Override
	public void closeOnCompletion() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}



	
	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public int getFetchDirection() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	


	@Override
	public int getResultSetHoldability() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

}
