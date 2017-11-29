package eg.edu.alexu.csd.oop.db.cs59;

import java.util.ArrayList;

public class DataBase implements IComponent {

	String DataBaseName;
	public ArrayList<Table> tables = new ArrayList<Table>();

	public void create(Table component) {
		
		tables.add(component);
	}

	public void Drop(Table component) {
		tables.remove(component);
	}

	@Override
	public String getName() {
		return DataBaseName;
	}

	@Override
	public void setName(String name) {
		DataBaseName = name;
	}
	
	@Override
	public long getSize(){
		return 0;
	}

}
