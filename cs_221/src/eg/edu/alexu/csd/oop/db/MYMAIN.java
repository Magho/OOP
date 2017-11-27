package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;


public class MYMAIN {
	public static void main(String[] args) throws SQLException {
		Database db = new DataBaseAdapter();
		System.out.println(db.createDatabase("testing1", false));
		//System.out.println(SQL.CreateSQL().DataBases);
		//System.out.println(SQL.CreateSQL().DataBases.get(1).tables.get(0).getName());
		//db.executeStructureQuery("create table ts(id int,name varchar);");
	
		//db.executeStructureQuery("drop table ts");
		//System.out.println(db.executeStructureQuery("CREATE TABLE tablor(id int,name varchar);"));
//	db.executeStructureQuery("drop database testing1;");
//		db.executeStructureQuery("drop database testing1;");
		
		
//
		int num = db.executeUpdateQuery("delete from ts where id = 3");
	//	int num = db.executeUpdateQuery("update ts set name = 'labib' where id = 3");


//int num = db.executeUpdateQuery("insert into ts (id, name) values (2, \"sajed\")");
//Object[][] test = db.executeQuery("selecty from ts where id = 2");
//		System.out.println(test[0][0] + " result");
//		
	}

}
