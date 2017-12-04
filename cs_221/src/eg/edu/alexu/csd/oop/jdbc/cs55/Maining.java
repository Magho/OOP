package eg.edu.alexu.csd.oop.jdbc.cs55;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class Maining {
	public static void main(String[] args) throws SQLException {
		Driver dr = new ImpDriver();
		Properties info = new Properties();
		File dbDir = new File("Data");
		info.put("path", dbDir.getAbsoluteFile());
		Connection connection = dr.connect("jdbc:xmldb://localhost", info);
		Statement db = connection.createStatement();
		
//		sta.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		
//		int count1 = sta.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		//Assert.assertNotEquals("Insert returned zero rows", 0, count1);
//		int count2 = sta.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
//		//Assert.assertNotEquals("Insert returned zero rows", 0, count2);
//		int count3 = sta.executeUpdate("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
//		//Assert.assertNotEquals("Insert returned zero rows", 0, count3);
//		
//		int count4 = sta.executeUpdate("DELETE From table_name1  WHERE coLUmn_NAME2=4");
//		//Assert.assertEquals("Delete returned wrong number", 1, count4);
//		
//		ResultSet result = sta.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
//		//Assert.assertNotNull("Null result returned", result);
//		//Assert.assertEquals("Wrong number of rows", 1, result.length);
//		//Assert.assertEquals("Wrong number of columns", 3, result[0].length);
//		
//		int count5 = sta.executeUpdate("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=10, column_name3='333333333' WHERE coLUmn_NAME2=5");
//		//Assert.assertEquals("Update returned wrong number", 1, count5);
//		
//		ResultSet result2 = sta.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 > 4");
//		//Assert.assertNotNull("Null result returned", result2);
//		//Assert.assertEquals("Wrong number of rows", 2, result2.length);
//		//Assert.assertEquals("Wrong number of columns", 3, result2[0].length);
//		//Object column_2_object = result2[0][1];
//		//Assert.assertEquals("Select did't return the updated record!", 10, column_2);
//		System.out.println(count1);//1
//		System.out.println(count2);//1
//		System.out.println(count3);//1
//		System.out.println(count4);//1
//		System.out.println(count5);//1
//		int i = 0;
//		while(result.next()){
//			i++;
//		}
//		System.out.println(i+" resault");
//		//System.out.println(result.length);//1
//		result.previous();
//		System.out.println(result.getInt(1) + " hnaaaa");
//		//System.out.println(result[0].length);//3
//		i = 0;
//		while(result2.next()){
//			i++;
//		}
//		System.out.println(i+"resault");
//		//System.out.println(result2.length);//2
//		//System.out.println(result2[0].length);//3
//		//System.out.println(column_2_object);//10
		
		
		
		/** Scenario 2***/
//		db.execute("CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar)"); 
//		int num1 = db.executeUpdate("INSERT INTO * table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		int num2 = db.executeUpdate("INSERT INTO table_name3(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		db.executeStructure("CREATE TABLE table_name4(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num3 = db.executeUpdateQuery("INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		int num4 = db.executeUpdateQuery("UPDATE table_name4 SET column_name1=='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'");
//		db.executeStructureQuery("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num5 = db.executeUpdateQuery("INSERT INTO table_name5(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		int num6 = db.executeUpdateQuery("UPDATE table_name5 SET column_name1='1111111111', COLUMN_NAME2='2222222', column_name3='333333333'");
//		db.executeStructureQuery("CREATE TABLE table_name6(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num7 = db.executeUpdateQuery("INSERT INTO table_name6(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		int num8 = db.executeUpdateQuery("UPDATE table_name6 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333");
//		db.executeStructureQuery("CREATE TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num9 = db.executeUpdateQuery("INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4");
//		db.executeStructureQuery("CREATE TABLE table_name8(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num10 = db.executeUpdateQuery("INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 4, 'value2')");
//		db.executeStructureQuery("CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		int num11 = db.executeUpdateQuery("INSERT INTO ta.ble_name9(column_NAME1, COLUMN_name3, column_name.2) VALUES ('value1', 'value3', 4)");
//      db.executeStructureQuery("Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
//      int num12 = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//      int num13 = db.executeUpdateQuery("INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//      int num14 = db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VAlUES ('value2', 'value4', 5)");
//      db.executeStructureQuery("UPDATE table_namE1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'");
//      db.executeStructureQuery("CREATE DATABASE TestDB");
//      db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
//      db.executeStructureQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//      db.executeStructureQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//      db.executeStructureQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
//      db.executeStructureQuery("DROP TABLE table_name1");
//      db.executeStructureQuery("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'");
//      db.executeStructureQuery("CREATE DATABASE TestDB");
//      db.executeStructureQuery("DROP DATABASE TestDB");
//      db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
      db.execute("CREATE DATABASE TestDB");
      db.execute("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)");
      db.executeUpdate("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
      db.executeUpdate("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
      db.executeUpdate("INSERT INTO table_name2(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
     int delete1 = db.executeUpdate("DELETE From table_name2 WHERE coLUmn_NAME1='VAluE1'");
      int update = db.executeUpdate("UPDATE table_name2 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME2=4");
      db.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
      db.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
      db.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
      db.executeUpdate("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
     // int delete2 = db.executeUpdate("DELETE From table_name1 WHERE coLUmn_NAME2=4");
      //Object[][] aa = db.execute("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
      System.out.println(delete1);//2
    //  System.out.println(delete2);//1
      System.out.println(update);//0
		
	}
}
