
package eg.edu.alexu.csd.oop.draw.cs55;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.draw.Shape;


public class UpdateOperation implements Operations {

	private DrawController dr;
	private Shape newShape;
	private Shape oldShape;
	private int index;

	UpdateOperation(Shape oldShape, Shape newShape,int index,DrawController d) {
		this.oldShape = oldShape;
		this.newShape = newShape;
		this.index = index;
		dr = d;
	}

	@Override
	public void execute() {
		dr.getListShapes().set(index, newShape);
	}

	@Override
	public void unExecute() {
		dr.getListShapes().set(index, oldShape);
	}

}
