package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.HandelXml;
import eg.edu.alexu.csd.oop.db.cs55.sqlInJava.SQL;

public class MYMAIN {
	public static void main(String[] args) throws SQLException {
		Database db = new DataBaseAdapter();
		System.out.println(db.executeStructureQuery("CREATE DATABASE testing2;"));
	}

}
