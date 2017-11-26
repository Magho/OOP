package eg.edu.alexu.csd.oop.db.cs55.sqlInJava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table implements IComponent {

	String TableName;
	public Map<String, String> coloumn  = new HashMap<String, String>();
	public ArrayList<Row> rows = new ArrayList<Row>();

	public void addColoumns (Map<String, String> coloumn){
		this.coloumn = coloumn;
	}
	public void create(Row component) {
	
		rows.add(component);
	}

	public void Drop(IComponent component) {

		rows.remove(component);
	}
	
	public Table Select (){
		return null;
	}
	public void addTable(Table table){
		for (int i = 0; i < table.getSize(); i++) {
			this.rows.add(table.rows.get(i));
		}
	}

	@Override
	public String getName() {
		return TableName;
	}
	
	@Override
	public void setName(String name) {
		TableName = name;
	}	
	
	@Override
	public long getSize(){
		return rows.size();
	}

}