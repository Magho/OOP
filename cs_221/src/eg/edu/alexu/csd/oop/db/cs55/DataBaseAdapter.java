package eg.edu.alexu.csd.oop.db.cs55;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseAdapter implements Database {

	HandelParsing handelparsing = new HandelParsing();

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		databaseName = databaseName.toLowerCase();
		try {
			if (dropIfExists) {
				executeStructureQuery("drop database " + databaseName);
				executeStructureQuery("create database " + databaseName);
			} else {
				executeStructureQuery("create database " + databaseName);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		// try {
		// handelparsing.SetCurrentDatabaseName(databaseName, dropIfExists);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// //File fp = File(System.getProperty("user.dir") + File.separator +
		// "Databases" + File.separator + databaseName);
		// /*if(true){
		// throw new RuntimeException(databaseName);
		// }*/
		return System.getProperty("user.dir") + File.separator + "Databases" + File.separator + databaseName;
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {

		try {
			handelparsing.setSQLCommand(query);
		} catch (SQLException e) {
			if (e.getMessage().equals("database doesn't exist")) {
				throw e;
			}
			return false;
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
