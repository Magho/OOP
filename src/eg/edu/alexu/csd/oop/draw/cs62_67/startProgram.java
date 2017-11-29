package eg.edu.alexu.csd.oop.draw.cs62_67;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.cs62_67.controller.MainController;
import eg.edu.alexu.csd.oop.draw.cs62_67.model.MyDrawingEngine;
import eg.edu.alexu.csd.oop.draw.cs62_67.model.ShapeFactory;
import eg.edu.alexu.csd.oop.draw.cs62_67.view.GUI;
import eg.edu.alexu.csd.oop.draw.Shape;

public class startProgram {
	public static void main(String[] args){
		DrawingEngine engine = new MyDrawingEngine();
		ShapeFactory factory = new ShapeFactory((MyDrawingEngine) engine);
		GUI Paint = new GUI();
		MainController main = new MainController(engine, factory, Paint);
		Paint.setVisible(true);
		List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> supportedShapes = engine.getSupportedShapes();
		
	}

}
