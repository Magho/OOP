package eg.edu.alexu.csd.oop.draw.cs55;


import java.util.ArrayList;

import eg.edu.alexu.csd.oop.draw.Shape;



public class AddOperations implements Operations {

	private Shape newShape;
	private DrawController dr;
	AddOperations (Shape newShape,DrawController d) {
		this.newShape = newShape;
		dr = d;
	}
	
	@Override
	public void execute() {
		ArrayList<Shape> newCurrentShapes = dr.getListShapes();
		newCurrentShapes.add(newShape);
	}

	@Override
	public void unExecute() {
		int last = dr.getListShapes().size()-1;
		dr.getListShapes().remove(last);
	}

}
