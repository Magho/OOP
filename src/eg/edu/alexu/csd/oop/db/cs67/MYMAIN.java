package eg.edu.alexu.csd.oop.db.cs67;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs67.HandelXml;
import eg.edu.alexu.csd.oop.db.cs67.SQL;

public class MYMAIN {
	public static void main(String[] args) throws SQLException {
		Database db = new DataBaseAdapter();
		System.out.println(db.createDatabase("testing1", false));
		//System.out.println(SQL.CreateSQL().DataBases);
		//System.out.println(SQL.CreateSQL().DataBases.get(1).tables.get(0).getName());
		//db.executeStructureQuery("create table ts(id int,name varchar);");
	
	//	db.executeStructureQuery("CREATE TABLE table_name13(column_name1 varchar , column_name2 int, column_name3 varchar)");
		//System.out.println(db.executeStructureQuery("CREATE TABLE tablor(id int,name varchar);"));
//	db.executeStructureQuery("drop database testing1;");
//		db.executeStructureQuery("drop database testing1;");
	//	db.createDatabase("TestDB", false);
		
//
//		int num = db.executeUpdateQuery("delete from ts where id = 3");
		int num = db.executeUpdateQuery("INSERT INTO table_name13(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");


//int num = db.executeUpdateQuery("insert into ts (id, name) values (2, \"sajed\")");
//Object[][] test = db.executeQuery("selecty from ts where id = 2");
//		System.out.println(test[0][0] + " result");
//		
	}

}
