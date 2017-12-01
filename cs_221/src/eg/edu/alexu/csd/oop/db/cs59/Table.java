package eg.edu.alexu.csd.oop.db.cs59;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Table implements IComponent {

	String TableName;
	public Map<String, String> coloumn = new TreeMap<String, String>();
	public ArrayList<Row> rows = new ArrayList<Row>();
	public ArrayList<String> coloumnNamesInorder = new ArrayList<>();

	public ArrayList<String> getColoumnsNames() {
		return coloumnNamesInorder;
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
		Map<String, String> map = new TreeMap<>();
		for (int i = 0; i < selectedColoumnsNames.size(); i++) {
			map.put(selectedColoumnsNames.get(i), coloumn.get(selectedColoumnsNames.get(i)));
		}
		return map;
	}

	public Object[][] convertTableTo2Darray() {

		Object[][] object = new Object[this.rows.size()][this.coloumn.size()];
//		System.out.println(this.rows.size());
//		System.out.println(this.coloumn.size());
//		System.out.println(coloumnNamesInorder.size());
		
		for (int i = 0; i < this.rows.size(); i++) {
			for (int j = 0; j < coloumn.size(); j++) {
				String str = this.coloumn.get(coloumnNamesInorder.get(j));
//				System.out.println(str);
				if (str.compareToIgnoreCase("varchar") == 0) {
					object[i][j] = this.rows.get(i).coloumn.get(coloumnNamesInorder.get(j));
				} else {
					object[i][j] = Integer.parseInt(this.rows.get(i).coloumn.get(coloumnNamesInorder.get(j)));
				}
			}
		}
		return object;
	}

	
}