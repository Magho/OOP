package eg.edu.alexu.csd.oop.draw.cs55;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Main {
	public static void main(String[] argu){
		DrawController dr = new DrawController();
		System.out.println(dr.getSupportedShapes());
		try {
			Shape s = dr.getSupportedShapes().get(0).newInstance();
			System.out.println(s);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
