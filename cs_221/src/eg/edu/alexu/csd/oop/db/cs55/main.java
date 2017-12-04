package eg.edu.alexu.csd.oop.db.cs55;

import java.sql.SQLException;

public class main {

	public static void main(String[] args) throws SQLException {
		DataBaseAdapter db = new DataBaseAdapter ();
		
		//null --done-- line 24
		//select/insert into coloumn not exist ??
		//delete return number
		
//		//test select
//		db.createDatabase("labib", false);
//		db.executeStructureQuery("create table table1 (id int, name varchar)");
//		db.executeStructureQuery("insert into table1 (id, name) values (1, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (1, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (1, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (1, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (1, 'magho')");
//		Object [][] mm = db.executeQuery("select * from table1 where id = 1 ");
//		System.out.println(mm.length);
	
	//2 --> 0
	//1 --> 0
	//2 --> 1
		
		
		

		
//		//test delete
//		db.createDatabase("labib", false);
//		//db.executeStructureQuery("create table table1 (id int, name varchar)");
//		db.executeStructureQuery("insert into table1 (id, name) values (9, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (9, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (9, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (9, 'magho')");
//		db.executeStructureQuery("insert into table1 (id, name) values (9, 'magho')");
//		int mmm = db.executeUpdateQuery("delete * from table1 where id = 9");
//		System.out.println(mmm);
		
		
		//test delete
//		db.createDatabase("labib", false);
//		//db.executeStructureQuery("create table table1 (id int, name varchar)");
//		int num = db.executeUpdateQuery("insert into table1 values (7)");
//	//	int mmm = db.executeUpdateQuery("delete from table1 where id = 7 ");
//		System.out.println(num);
		
		
		/**Scenario 3**/

		db.createDatabase("TestDB", true);
	
		db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
		
		int count1 = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
		//Assert.assertNotEquals("Insert returned zero rows", 0, count1);
		int count2 = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
		//Assert.assertNotEquals("Insert returned zero rows", 0, count2);
		int count3 = db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
		//Assert.assertNotEquals("Insert returned zero rows", 0, count3);
		
		int count4 = db.executeUpdateQuery("DELETE From table_name1  WHERE coLUmn_NAME2=4");
		//Assert.assertEquals("Delete returned wrong number", 1, count4);
		
		Object[][] result = db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
		//Assert.assertNotNull("Null result returned", result);
		//Assert.assertEquals("Wrong number of rows", 1, result.length);
		//Assert.assertEquals("Wrong number of columns", 3, result[0].length);
		
		int count5 = db.executeUpdateQuery("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=10, column_name3='333333333' WHERE coLUmn_NAME2=5");
		//Assert.assertEquals("Update returned wrong number", 1, count5);
		
		Object[][] result2 = db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 > 4");
		//Assert.assertNotNull("Null result returned", result2);
		//Assert.assertEquals("Wrong number of rows", 2, result2.length);
		//Assert.assertEquals("Wrong number of columns", 3, result2[0].length);
		Object column_2_object = result2[0][1];
		//Assert.assertEquals("Select did't return the updated record!", 10, column_2);
		System.out.println(count1);//1
		System.out.println(count2);//1
		System.out.println(count3);//1
		System.out.println(count4);//1
		System.out.println(count5);//1
		System.out.println(result.length);//1
		System.out.println(result[0].length);//3
		System.out.println(result2.length);//2
		System.out.println(result2[0].length);//3
		System.out.println(column_2_object);//10
		
		
		db.createDatabase("inver", false);
		db.executeStructureQuery("create table inver2(id int, ar varchar,ax varchar, count int)");
		db.executeUpdateQuery("insert into inver2 values(13,'the','get',1)");
		
		
		/** Scenario 2***/
//		db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar)"); 
//		int num1 = db.executeUpdateQuery("INSERT INTO * table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		int num2 = db.executeUpdateQuery("INS ERT INTO table_name3(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		db.executeStructureQuery("CREATE TABLE table_name4(column_name1 varchar, column_name2 int, column_name3 varchar)");
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
      db.executeStructureQuery("CREATE DATABASE TestDB");
      db.executeStructureQuery("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)");
      db.executeUpdateQuery("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
      db.executeUpdateQuery("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
      db.executeUpdateQuery("INSERT INTO table_name2(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
     int delete1 = db.executeUpdateQuery("DELETE From table_name2 where  column_name2 < 6");
      int update = db.executeUpdateQuery("UPDATE table_name2 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME2=4");
      db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
      db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
      db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
      db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
      int delete2 = db.executeUpdateQuery("DELETE From table_name1 WHERE coLUmn_NAME2=4");
      Object[][] aa = db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
      System.out.println(delete1);
      System.out.println(delete2);
      System.out.println(update);
  	
	}
}
