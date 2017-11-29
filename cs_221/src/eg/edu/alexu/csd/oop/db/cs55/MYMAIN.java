package eg.edu.alexu.csd.oop.db.cs55;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import eg.edu.alexu.csd.oop.db.Database;

public class MYMAIN {
	public static void main(String[] args) throws SQLException {
		
		Database db = new DataBaseAdapter();
		
		/**bugs**/
		// insert without coloumns names --done--
		//select all                          
		//insert with no coloumns             --done--
		//catch exception and return false    --done--
		/**bugs**/
		/**Scenario 1 **/
		
//		db.executeStructureQuery("CREATE DATABASE TestDB");
//		db.executeUpdateQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
//		db.executeUpdateQuery("DROP TABLE table_name1");
		
		
		
		/** Scenario 2**/
		
		
//		System.out.println(db.createDatabase("saMple", false));
//		System.out.println(db.createDatabase("sAmPl", false));
//		System.out.println(db.createDatabase("sAMPLE", true));
//
//		db.executeStructureQuery("create table ts(id int,name varchar);");
//	
//		db.executeStructureQuery("drop database testing1;");
//		
//		
	//	boolean num = db.executeStructureQuery("create database tarek;");
//		System.out.println(num);
		
		//System.out.println(db.createDatabase("saMple", false));
		//db.executeStructureQuery("create table ts(id int,name varchar);");
		/*
		db.executeUpdateQuery("INSERT INTO ts (id, name) VALUES (4, 'magho')");

		db.executeUpdateQuery("INSERT INTO ts (id, name) VALUES (2, 'sajed')");

		db.executeUpdateQuery("INSERT INTO ts (id, name) VALUES (3, 'Labib')");

		db.executeUpdateQuery("INSERT INTO ts (id, name) VALUES (4, 'Kamal')");
		
	 	Object[][] test = db.executeQuery("SELECT * from ts where id = 2");
		System.out.println(test.length);
		*/
		db.createDatabase("testingMuhammed", false);
		//db.executeStructureQuery("create table table1(id int,name varchar)");
		//db.executeStructureQuery("drop table table1");
		db.executeUpdateQuery("insert into table1 (id, name) values(4,'muhammed')");
		db.executeUpdateQuery("insert into table1 (id, name) values(4,'muhammed')");
		db.executeUpdateQuery("insert into table1 (id, name) values(56,'musdsdgsdghammed')");
		db.executeUpdateQuery("insert into table1 values(4,'musdsdgsdghammed')");
		db.executeUpdateQuery("update table1 set id=4,name='magho' where name='muhammed' ");

		int num = db.executeUpdateQuery("delete from table1 where id = 4;");
		//System.out.println(num);
//
//		int num = db.executeUpdateQuery("insert into table1 (id, name) values (2, \"sajed\")");
	//Object[][] test = db.executeQuery("select * from table1 where id = 4");
		
//		System.out.println(test[0][0]);
//		System.out.println(test[0][0] + " result");
			}

}
