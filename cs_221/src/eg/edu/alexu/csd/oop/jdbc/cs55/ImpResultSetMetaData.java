package eg.edu.alexu.csd.oop.jdbc.cs55;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs55.Table;

public class ImpResultSetMetaData implements java.sql.ResultSetMetaData{

	private ArrayList<String> columns;
	private Table table;
	public ImpResultSetMetaData(ArrayList<String> columns, Table table) {
		this.columns = columns;
		this.table = table;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		
		return columns.get(column);
	}
	
	@Override
	public int getColumnCount() throws SQLException {

		throw new RuntimeException();
		//return this.columns.size();
	}
	
	@Override
	public String getColumnName(int column) throws SQLException {
	
		return columns.get(column);
	}
	
	@Override
	public int getColumnType(int column) throws SQLException {
		throw new RuntimeException();
		
//		String type = this.table.coloumn.get(columns.get(column)); 
//		if(type.equalsIgnoreCase("varchar")) {
//			return java.sql.Types.VARCHAR;
//		}
//		else {
//			return java.sql.Types.INTEGER;
//		}
	}
	
	@Override
	public String getTableName(int column) throws SQLException {
		return table.getName();
	}
	
	
//-------------------------------------------------------------------------------------------------------------
	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}


	@Override
	public String getColumnTypeName(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int getScale(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// 
		throw new java.lang.UnsupportedOperationException();
	}

}
