package eg.edu.alexu.csd.oop.calculator.cs67;

import eg.edu.alexu.csd.oop.calculator.Calculator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class MyCalculator implements Calculator {

	/*important variables*
	 * 
	 */
	private String input;
	private LinkedList<String> history = new LinkedList<>();;
	private int position = -1;
	private File saveData;
	private BufferedWriter bw = null;
	private BufferedReader br = null;
	FileReader fr = null;

	@Override
	final public void input(final String s) {
		input = s;
		position++;
		history.add(input);
		if (history.size() > 5) {
			position = position - 1;
			history.removeFirst();
		}
	}

	@Override
	final public String getResult() {
		try {
			String data [] = 
					history.get(position).split("\\+|\\-|\\*|\\/");
			double input1 = Double.parseDouble(data[0]);
			double input2 = Double.parseDouble(data[1]);
			char operator = history.get(position).charAt(data[0].length());
			if (operator == '+') {
				double v = (input1 + input2);

				return ("" + v);
			} else if (operator == '-') {
				return ("" + (input1 - input2));
			} else if (operator == '/') {
				return ("" + (input1 / input2));
			} else {
				return ("" + (input1 * input2));
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	@Override
	public String current() {
		if (history.size() == 0 || position < 0){
			return null;
		}
		return history.get(position);
	}

	@Override
	public String prev() {
		if (position == 0 || history.size() == 0) {
			return null;
		}
		position--;
		return history.get(position);
	}

	@Override
	public String next() {
		if (position >= history.size() - 1 || history.size() == 0) {
			return null;
		}
		position++;
		return history.get(position);
	}

	@Override
	public void save() {
		try {
			saveData = new File("save.txt");
			bw = new BufferedWriter(new FileWriter(saveData));
			bw.write("" + position);
			bw.newLine();
			for (int i = 0; i < history.size(); i++) {
				bw.write(history.get(i));
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void load() {
		history.clear();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("save.txt"));
			position = (int) Integer.parseInt(br.readLine());
			while ((sCurrentLine = br.readLine()) != null) {
				history.add(sCurrentLine);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

}
