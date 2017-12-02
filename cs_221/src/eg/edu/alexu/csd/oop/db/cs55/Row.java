package eg.edu.alexu.csd.oop.db.cs55;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Row implements IComponent {

	private String Rownumber;

	public Map<String, String> coloumn;
	public ArrayList<String> coloumnName;
	private ArrayList<String> ColoumnValue;

	Row(ArrayList<String> coloumnName, ArrayList<String> ColoumnValue) {
		this.coloumnName = coloumnName;
		this.ColoumnValue = ColoumnValue;
		create();

	}

	private void create() {
		coloumn = new LinkedHashMap<String, String>();
		while (ColoumnValue.size() < coloumnName.size()) {
			ColoumnValue.add("null");
		}
		for (int i = 0; i < coloumnName.size(); i++) {
			coloumn.put(coloumnName.get(i), ColoumnValue.get(i));
		}
	}

	public void updateRow(String coloumnName, String ColoumnValue) {
		coloumn.put(coloumnName, ColoumnValue);
	}

	public ArrayList<String> SelectSpecificColoumnsValuse(ArrayList<String> ColoumnsNames) {
		ArrayList<String> ColoumnsValues = new ArrayList<String>();
		for (int i = 0; i < ColoumnsNames.size(); i++) {
			ColoumnsValues.add(this.coloumn.get(ColoumnsNames.get(i)));
		}
		return ColoumnsValues;
	}

	@Override
	public String getName() {
		return Rownumber;
	}

	@Override
	public void setName(String name) {
		Rownumber = name;
	}

	@Override
	public long getSize() {
		return 0;
	}

}
