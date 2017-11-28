package eg.edu.alexu.csd.oop.db.cs55;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;



public class DataBaseAdapter implements Database {
	
	HandelParsing handelparsing = new HandelParsing();

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		try {
			handelparsing.SetCurrentDatabaseName(databaseName, dropIfExists);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		databaseName = databaseName.toLowerCase();
		File fp = new File("Database/" + databaseName + "/");
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
		return handelparsing.setSQLCommand(query);
	}

}
