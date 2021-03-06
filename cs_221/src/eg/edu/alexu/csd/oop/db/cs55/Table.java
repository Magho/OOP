package eg.edu.alexu.csd.oop.db.cs55;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Table implements IComponent {

	String TableName;
	public Map<String, String> coloumn = new LinkedHashMap<String, String>();
	public ArrayList<Row> rows = new ArrayList<Row>();
	
	
	public ArrayList<String> getColoumnsNames() {
		ArrayList <String> coloumnsNames = new ArrayList <String>();
		Set<String> set = coloumn.keySet();
		for(String s : set){
			coloumnsNames.add(s);
		}
		return coloumnsNames;
	}

	public void addColoumns(Map<String, String> coloumn) {
		this.coloumn = coloumn;
	}

	public void create(Row component) {

		rows.add(component);
	}

	public void Drop(IComponent component) {

		rows.remove(component);
	}

	public Table Select() {
		return null;
	}

	public void addTable(Table table) {
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
	public long getSize() {
		return rows.size();
	}

	public Map<String, String> returnSelectedColoumns(ArrayList<String> selectedColoumnsNames) {
		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < selectedColoumnsNames.size(); i++) {
			map.put(selectedColoumnsNames.get(i), coloumn.get(selectedColoumnsNames.get(i)));
		}
		return map;
	}

	public Object[][] convertTableTo2Darray() {

		Object[][] object = new Object[this.rows.size()][this.coloumn.size()];
		for (int i = 0; i < this.rows.size(); i++) {
			int j = 0;
			for (String value : this.coloumn.keySet()) {

				String str = this.coloumn.get(value);
				if (str.compareToIgnoreCase("varchar") == 0) {

					object[i][j] = this.rows.get(i).coloumn.get(value);
				} else {

					object[i][j] = Integer.parseInt(this.rows.get(i).coloumn.get(value));
				}
				j++;
			}
		}
		return object;
	}

}