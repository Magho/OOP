package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.HandelParsing;
import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.Table;


public class DataBaseAdapter implements Database {
	
	HandelParsing handelparsing = new HandelParsing();

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		try {
			handelparsing.SetCurrentDatabaseName(databaseName, dropIfExists);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File fp = new File("databases/" + databaseName + "/");
		return fp.getPath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		handelparsing.setSQLCommand(query);
		return true;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
	handelparsing.setSQLCommand(query);
	Table table = handelparsing.returnSelectedTable();
	
	return table.convertTableTo2Darray();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		handelparsing.setSQLCommand(query);		
		return 0;
	}

}
