package eg.edu.alexu.csd.oop.draw.cs55;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DeleteOperation implements Operations {
	private DrawController dr;
	private Shape shape;
	private int index;
	public DeleteOperation(int index,Shape sh,DrawController d){
		this.index = index;
		shape = sh;
		dr = d;
	}
	@Override
	public void execute() {
		dr.getListShapes().remove(index);
	}

	@Override
	public void unExecute() {
		// TODO Auto-generated method stub
		dr.getListShapes().add(index, shape);;
	}
}
