package eg.edu.alexu.csd.oop.calculator.cs55;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class test {
//
//	public static void main(String[] args) {
//		
//		CalculatorImpl calc = new CalculatorImpl();
//		String str1 = "1+1";
//		String str2 = "2+2";
//		String str3 = "3+3";
//		String str4 = "4+4";
//		String str5 = "5+5";
//		calc.input(str1);
//		calc.getResult();
//		calc.input(str2);
//		calc.getResult();
//		calc.input(str3);
//		calc.getResult();
//		calc.input(str4);
//		calc.getResult();
//		calc.input(str5);
//		calc.getResult();
//		calc.save();
//		calc.prev();
//		calc.save();
//		calc.load();
//	}

	
	
	
//	private void loadFile() throws IOException {
//	File fout = new File("out.txt");
//	FileInputStream in = new FileInputStream(fout);
//	BufferedReader br = new BufferedReader(new InputStreamReader(in));
//	String line = null;
//	formulas.clear();
////	System.out.println("******************************saved**************************");
//	while ((line = br.readLine()) != null) {
//		formulas.addLast(line); // the last operation is the last
//								// node
//	//	System.out.println(line);
//	}
//	lengthOfTheformulasLinkedList = formulas.size();
//	currentElementInformulasLinkedList = Integer.parseInt(formulas.getLast());
//	formulas.removeLast();// remove current position
//	br.close();
//}
	
	
	
//	/**
//	 * @throws IOException
//	 */
//	private void savefile() throws IOException {
//		int i;
//		File fout = new File("out.txt");
//		FileOutputStream out = new FileOutputStream(fout);
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
//		if (currentFormulas.size() >= 5) {
//			i = formulas.size() - 5;
//		} else {
//			i = 0;
//		}
//	//	System.out.println("******************************saved**************************");
//		for (int j = i; j < currentFormulas.size(); j++) {
//			bw.write(currentFormulas.get(j));
//		//	System.out.println(currentFormulas.get(j));
//			bw.newLine();
//		}
//		bw.write(String.valueOf(currentElementInformulasLinkedList));
//		//System.out.println(currentElementInformulasLinkedList);
//		bw.newLine();
//		bw.close();
//	}
}
