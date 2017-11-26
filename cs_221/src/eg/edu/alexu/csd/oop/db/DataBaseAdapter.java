package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.HandelParsing;
import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.Row;
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
		return "databases/" + databaseName;
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
		Row [][] selectedrows = new Row[table.coloumn.size()][table.rows.size()];
		//TODO set elements from selectedrows to array of objects
		return selectedrows;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		handelparsing.setSQLCommand(query);		
		return 0;
	}

}
