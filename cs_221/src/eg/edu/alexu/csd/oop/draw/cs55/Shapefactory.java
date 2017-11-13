package eg.edu.alexu.csd.oop.draw.cs55;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Shapefactory {
	
	static public Shape createShape (String shapeType){

		if (shapeType.compareToIgnoreCase("Square") == 0){
			Square square = new Square();
			return square;
		}

		return null;
	}

}
