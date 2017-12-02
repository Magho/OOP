package eg.edu.alexu.csd.oop.db.cs59;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Table implements IComponent {

	String TableName;
	public Map<String, String> coloumn = new TreeMap<String, String>();
	public ArrayList<Row> rows = new ArrayList<Row>();
	public ArrayList<String> coloumnNamesInorder = new ArrayList<>();
	
//	public ArrayList<Coloumns> coloumnsInTheTable = new ArrayList<>();
	
//	Table() {
//		for (int i = 0; i < coloumnNamesInorder.size(); i++) {
//			Coloumns coloumn = new Coloumns();
//			coloumn.setName(coloumnNamesInorder.get(i));
//			coloumn.parentTable = this;
//			coloumnsInTheTable.add(coloumn);
//		}
//	}

	public ArrayList<String> getColoumnsNames() {
		return coloumnNamesInorder;
	}

	public void addColoumns(Map<String, String> coloumn) {
//		for (int i = 0; i < coloumnNamesInorder.size(); i++) {
//			coloumnsInTheTable.get(i).setType(coloumn.get(coloumnNamesInorder.get(i)));;
//		}		
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
		System.out.println(coloumn.toString());
		for (int i = 0; i < this.rows.size(); i++) {
			for (int j = 0; j < this.coloumn.size(); j++) {
				String str = this.coloumn.get(coloumnNamesInorder.get(j));
				System.out.println( this.rows.get(i).coloumn.toString());
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