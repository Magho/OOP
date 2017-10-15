package eg.edu.alexu.csd.oop.calculator.cs55;

import eg.edu.alexu.csd.oop.calculator.Calculator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class CalculatorImpl implements Calculator {

	private double firstNumber;
	private double secondNumber;
	private char operator;
	private String answer = null;
	private String current = null;
	private String formula = null;
	private LinkedList<String> formulas = new LinkedList<String>();
	private int lengthOfTheformulasLinkedList = formulas.size();
	private int currentElementInformulasLinkedList = lengthOfTheformulasLinkedList - 1;
	private LinkedList<String> currentFormulas = new LinkedList<String>();

	@Override
	public void input(String s) {
		boolean operatorfound = false;
		boolean oneNumber = false;
		try {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '+' | s.charAt(i) == '-' | s.charAt(i) == '*' | s.charAt(i) == '/') {
					// the string contain only one number
					if (i == 0 | i == s.length() - 1) {
						oneNumber = true;
					}
					operatorfound = true;
					firstNumber = Double.parseDouble(s.substring(0, i));
					secondNumber = Double.parseDouble(s.substring(i + 1));
					operator = s.charAt(i);
				}
			}
			// length of the string is less than 3 meaning don't contain two
			// numbers
			if (s.trim().length() < 3 | checkIfContainSpace(s) | !operatorfound | oneNumber) {
				throw (null);
			}
			formula = s;
		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();// TODO
		}
	}

	/**
	 * @param s
	 * @return
	 */
	private boolean checkIfContainSpace(String s) {
		boolean found = false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				found = true;
			}
		}
		return found;
	}
	@Override
	public String getResult() {
		if (formula != null) {
			try {
				switch (operator) {
				case '+':
					answer = String.valueOf(sum(firstNumber, secondNumber));
					break;
				case '-':
					answer = String.valueOf(substract(firstNumber, secondNumber));
					break;
				case '*':
					answer = String.valueOf(multiply(firstNumber, secondNumber));
					break;
				case '/':
					answer = String.valueOf(divide(firstNumber, secondNumber));
					break;
				default:
					throw (null);// TODO
				}
				formulas.addLast(formula);
				lengthOfTheformulasLinkedList++;
				currentElementInformulasLinkedList = lengthOfTheformulasLinkedList - 1;
				currentFormulas.addLast(formula);

				formula = null;
			} catch (Exception e) {
				System.out.println("exception");
				e.printStackTrace();// TODO
			}
			return answer;
		} else {
			throw (null);// TODO
		}
	}

	@Override
	public String current() {
		current = formulas.get(currentElementInformulasLinkedList);
		return current;
	}

	@Override
	public String prev() {
		String formula = null;
		if (currentElementInformulasLinkedList > 0) {
			formula = formulas.get(currentElementInformulasLinkedList - 1);
			currentElementInformulasLinkedList--;
		}
		return formula;
	}

	@Override
	public String next() {
		String formula = null;
		if (currentElementInformulasLinkedList <= lengthOfTheformulasLinkedList - 2) {
			formula = formulas.get(currentElementInformulasLinkedList + 1);
			currentElementInformulasLinkedList++;
		}
		return formula;
	}

	@Override
	public void save() {
		try {
			savefile();
		} catch (IOException e) {
			throw (null);// TODO
		}
	}

	/**
	 * @throws IOException
	 */
	private void savefile() throws IOException {
		int i;
		File fout = new File("out.txt");
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
		if (currentFormulas.size() >= 5) {
			i = formulas.size() - 5;
		} else {
			i = 0;
		}
	//	System.out.println("******************************saved**************************");
		for (int j = i; j < currentFormulas.size(); j++) {
			bw.write(currentFormulas.get(j));
		//	System.out.println(currentFormulas.get(j));
			bw.newLine();
		}
		bw.write(String.valueOf(currentElementInformulasLinkedList));
		//System.out.println(currentElementInformulasLinkedList);
		bw.newLine();
		bw.close();
	}

	@Override
	public void load() {
		try {
			loadFile();
		} catch (IOException e) {
			throw (null);// TODO
		}
	}

	/**
	 * @throws IOException
	 */
	private void loadFile() throws IOException {
		File fout = new File("out.txt");
		FileInputStream in = new FileInputStream(fout);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		formulas.clear();
	//	System.out.println("******************************saved**************************");
		while ((line = br.readLine()) != null) {
			formulas.addLast(line); // the last operation is the last
									// node
		//	System.out.println(line);
		}
		lengthOfTheformulasLinkedList = formulas.size();
		currentElementInformulasLinkedList = Integer.parseInt(formulas.getLast());
		formulas.removeLast();// remove current position
		br.close();
	}

	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private double sum(double firstNumber, double secondNumber) {
		return firstNumber + secondNumber;
	}

	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private double substract(double firstNumber, double secondNumber) {
		return firstNumber - secondNumber;
	}

	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private double multiply(double firstNumber, double secondNumber) {
		return firstNumber * secondNumber;
	}

	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private double divide(double firstNumber, double secondNumber) {
		// float TODO
		if (secondNumber == 0) {
			return 00;// TODO
		}
		return firstNumber / secondNumber;
	}

}
