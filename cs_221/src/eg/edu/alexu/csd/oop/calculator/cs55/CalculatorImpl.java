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

/**
 * @author Magho
 *
 */
public final class CalculatorImpl implements Calculator {
	/**
	 * .
	 */
	private int zero = 0;
	/**
	 * .
	 */
	private int one = 1;
	/**
	 * .
	 */
	private final int three = 3;
	/**
	 * .
	 */
	private final int four = 4;
	/**
	 * .
	 */
	private final int five = 5;
	/**
	 * .
	 */
	private double firstNumber;
	/**
	 * .
	 */
	private double secondNumber;
	/**
	 * .
	 */
	private char operator;
	/**
	 * .
	 */
	private String answer = null;
	/**
	 * .
	 */
	private String current = null;
	/**
	 * .
	 */
	private LinkedList<String> formulas = new LinkedList<String>();
	/**
	 * @lengthOfTheformulasLinkedList
	 */
	private int lengthOfTheformulasLinkedList = formulas.size();
	/**
	 * @currentElementInformulasLinkedList.
	 */
	private int currentElementInformulasLinkedList
	= lengthOfTheformulasLinkedList - one;

	@Override
	public void input(final String s) {
		if (formulas.size() >= five) {
			for (int i = zero; i < four; i++) {
				formulas.set(i, formulas.get(i + one));
			}
			formulas.removeLast();
		}

		formulas.addLast(s);
		lengthOfTheformulasLinkedList = formulas.size();
		currentElementInformulasLinkedList = formulas.size() - one;
	}


	/**
	 * @param s .
	 * @return .
	 */
	private boolean checkIfContainSpace(final String s) {
		boolean found = false;
		for (int i = zero; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				found = true;
			}
		}
		return found;
	}

	@Override
	public String getResult() {

		boolean operatorfound = false;
		boolean oneNumber = false;
		String s = formulas.get(currentElementInformulasLinkedList);
		for (int i = zero; i < s.length(); i++) {
			if (s.charAt(i) == '+' | s.charAt(i) == '-'
					| s.charAt(i) == '*'
					| s.charAt(i) == '/') {
				// the string contain only one number
				if (i == zero | i == s.length() - one) {
					oneNumber = true;
				}
				operatorfound = true;
				firstNumber =
				Double.parseDouble(s.substring(zero, i));
				secondNumber =
				Double.parseDouble(s.substring(i + one));
				operator = s.charAt(i);
			}
		}
		// length of the string is less than 3 meaning don't contain two
		// numbers
		if (s.trim().length() < three
				| checkIfContainSpace(s)
				| !operatorfound | oneNumber) {
			answer = null;
		}

		switch (operator) {
		case '+':
			answer = String.valueOf(sum());
			break;
		case '-':
			answer =
			String.valueOf(substract());
			break;
		case '*':
			answer =
			String.valueOf(multiply());
			break;
		case '/':
			answer =
			String.valueOf(divide());
			break;
		default:
			return null;
		}
		return answer;
	}

	@Override
	public String current() {
		try {
			current = formulas.get(
					currentElementInformulasLinkedList);
			return current;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String prev() {
		String formula = null;
		if (currentElementInformulasLinkedList > zero) {
			formula = formulas.get(
					currentElementInformulasLinkedList
							- one);
			currentElementInformulasLinkedList--;
		}
		return formula;
	}

	@Override
	public String next() {
		String formula = null;
		if (currentElementInformulasLinkedList
				< lengthOfTheformulasLinkedList - one) {
			formula =
			formulas.get(currentElementInformulasLinkedList + one);
			currentElementInformulasLinkedList++;
		}
		return formula;
	}

	@Override
	public void save() {
		try {
			savefile();
		} catch (IOException e) {
			System.out.println("exception");
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException .
	 */
	private void savefile() throws IOException {
		File fout = new File("out.txt");
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter bw =
				new BufferedWriter(new OutputStreamWriter(out));
		for (int j = zero; j < formulas.size(); j++) {
			bw.write(formulas.get(j));
			bw.newLine();
		}
		bw.write(String.valueOf(currentElementInformulasLinkedList));
		bw.newLine();
		bw.close();
	}

	@Override
	public void load() {
		try {
			loadFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException .
	 */
	private void loadFile() throws IOException {
		File fout = new File("out.txt");
		FileInputStream in = new FileInputStream(fout);
		BufferedReader br =
				new BufferedReader(new InputStreamReader(in));
		String line = null;
		formulas.clear();
		while ((line = br.readLine()) != null) {
			formulas.addLast(line);
			// the last operation is the last
			// node
		}
		currentElementInformulasLinkedList =
				Integer.parseInt(formulas.getLast());
		formulas.removeLast();
		lengthOfTheformulasLinkedList = formulas.size();
		br.close();
	}

	/**
	 * @return .
	 */
	private double sum() {
		return this.firstNumber + this.secondNumber;
	}

	/**
	 * @return .
	 */
	private double substract() {
		return this.firstNumber - this.secondNumber;
	}

	/**
	 * @return .
	 */
	private double multiply() {
		return this.firstNumber * this.secondNumber;
	}

	/**
	 * @return .
	 */
	private double divide() {
		if (secondNumber == zero) {
			return 00;
		}
		return this.firstNumber / this.secondNumber;
	}
}
