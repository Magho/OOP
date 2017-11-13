package eg.edu.alexu.csd.oop.draw.cs55;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;


public class Square implements Shape {
	
	private Point base_Point;
	private Map<String, Double> properities;
	private Color choosen_color = Color.black;
	private Color fill_color = Color.white;

	public Square() {
		properities = new HashMap<String,Double>();
		properities.put("length",1.0);
		base_Point = new Point (2,2);
	}
	
	public Square(Square cloned_shape) {
		// TODO Auto-generated constructor stub
		this.base_Point = new Point(cloned_shape.getPosition());
		this.properities = new HashMap<String, Double>(cloned_shape.getProperties());
		this.choosen_color = cloned_shape.getColor(); 
		this.fill_color = cloned_shape.getFillColor();
	}
	@Override
	public void setPosition(Point position) {
		base_Point = new Point(position);
	}

	@Override
	public Point getPosition() {
		return base_Point;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		properities = new HashMap<String, Double>(properties);
	}

	@Override
	public Map<String, Double> getProperties() {
		return properities;
	}

	@Override
	public void setColor(Color color) {
		choosen_color = color;
	}

	@Override
	public Color getColor() {
		return choosen_color;
	}

	@Override
	public void setFillColor(Color color) {
		fill_color = color;
	}

	@Override
	public Color getFillColor() {
		return fill_color;
	}

	@Override
	public void draw(Graphics canvas) {

		((Graphics2D) canvas).setColor(getFillColor());
        ((Graphics2D) canvas).fillRect((int) getPosition().getX(),
                (int) getPosition().getY(),
                (int) getProperties().get("length").intValue(), 
                (int) getProperties().get("length").intValue());

        ((Graphics2D) canvas).setStroke(new BasicStroke(2));
        ((Graphics2D) canvas).setColor(getColor());
        ((Graphics2D) canvas).drawRect((int) getPosition().getX(),
                (int) getPosition().getY(),
                (int) getProperties().get("length").intValue(), 
                (int) getProperties().get("length").intValue());

	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Square cloned_Square = new Square (this);
		return cloned_Square;
	}
	
	public boolean isContain(Point p){
		if(!getProperties().containsKey("length")) 
			return false;
		int l = getProperties().get("length").intValue();
		if(getPosition().x <= p.x &&getPosition().x + l >= p.x){
			if(getPosition().y <= p.y &&getPosition().y + l >= p.y)
				return true;
		}
		return false;
	}
	
	public String getShapeName(){
		return "Square";
	}
}
