package eg.edu.alexu.csd.oop.db.cs59;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseAdapter implements Database {

	HandelParsing handelparsing = new HandelParsing();

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		databaseName = databaseName.toLowerCase();
		try {
			handelparsing.SetCurrentDatabaseName(databaseName, dropIfExists);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		File fp = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Databases"
				+ System.getProperty("file.separator") + databaseName);
		return fp.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {

		try {
			handelparsing.setSQLCommand(query);
		} catch (SQLException e) {
			if (e.getMessage().equals("table exists before")) {
				return false;
			}
			throw e;
		}
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
