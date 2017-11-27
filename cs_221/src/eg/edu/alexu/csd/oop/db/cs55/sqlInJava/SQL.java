package eg.edu.alexu.csd.oop.db.cs55.sqlInJava;

import java.util.ArrayList;

public class SQL implements IComponent {

	// Singleton
	private static SQL sql = null;
	private SQL() {}
	public static SQL CreateSQL() {
		if (sql == null) {
			sql = new SQL();
		}
		return sql;
	}

	public ArrayList<DataBase> DataBases = new ArrayList<DataBase>();

	public void create(DataBase component) {
		DataBases.add(component);
	}

	public void Drop(DataBase component) {
		DataBases.remove(component);

	}

	@Override
	public String getName() {
		return "Sql";
	}

	@Override
	public void setName(String name) {
	}
	
	@Override
	public long getSize() {
		return 0;
	}

}
