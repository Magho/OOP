package eg.edu.alexu.csd.oop.draw.cs55;



import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;



public class DrawController implements DrawingEngine {

	static DrawController drawController = null;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Stack<Operations> undo;
	private Stack<Operations> redo;
	private List<Class<? extends Shape>> supportedShape;
	private int size = 0;
	public DrawController() {
		undo = new Stack<Operations>();
		redo = new Stack<Operations>();
	}
	
	public ArrayList<Shape> getListShapes(){
		return shapes;
	}

	public static DrawController CreateObject() {

		if (drawController == null) {
			drawController = new DrawController();
		}
		return drawController;
	}

	@Override
	public void refresh(Graphics canvas) {
		for(Shape sh: shapes){
			sh.draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		AddOperations add = new AddOperations(shape,this);
		add.execute();
		undo.push(add); 
		redo.clear();
		size = undo.size()+redo.size();
		steps();
	}

	@Override
	public void removeShape(Shape shape) {

		int deletedIndex = 0;
		for(int i = shapes.size()-1; i>= 0;i--){
			if(shape == shapes.get(i)){
				deletedIndex = i;
				break;
			}
		}
		DeleteOperation delete = new DeleteOperation(deletedIndex,shape,this);
		delete.execute();
		undo.push(delete);
		redo.clear();
		size = undo.size()+redo.size();
		steps();
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		int updatedIndex = 0;
		for(int i = 0; i< shapes.size();i++){
			if(newShape==shapes.get(i)){
				updatedIndex = i;
				break;
			}
		}
		UpdateOperation update = new UpdateOperation(oldShape,
				newShape,updatedIndex,this);
		update.execute();
		undo.push(update);
		redo.clear();
		size = undo.size()+redo.size();
		steps();
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapesArray = new Shape[shapes.size()];
		shapesArray = this.shapes.toArray(shapesArray);
		return shapesArray;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		supportedShape = new ArrayList<Class<? extends Shape>>();
		supportedShape.add(Square.class);
		return supportedShape;
	}

	@Override
	public void undo() {
		if(!undo.isEmpty()){
			Operations op = undo.pop();
			redo.push(op);
			op.unExecute();
		}
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		if(!redo.isEmpty()){
			Operations op = redo.pop();
			undo.push(op);
			op.execute();
		}
	}

	public void save(String path) {
		//TODO depending path
		
		try {
			Convert_JAVA_to_JSON(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(String path) {
		try {
			shapes = Convert_JSON_to_JAVA(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		undo.clear();
		redo.clear();
	}

	/** JSON or XML to java and vice versa **/

	// Tested
	public void Convert_JAVA_to_JSON(String path) throws IOException {

		File fout = new File(path);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

		// I write each shape as json on one line
		String myJson = "[ ";
		bw.write(myJson);
		bw.newLine();
		myJson = "";

		Shape[] shape = (Shape[]) this.getShapes();

		for (int i = 0; i < shape.length; i++) {

			if (shape[i] == null) {
				shape[i] = new Square();
			}
			shape[i] = new Square();
			myJson += "\"Shape " + "Square" + " \": { \"Color\" : \""
					+ Integer.toString(shape[i].getColor().getRGB()) + "\" , ";
			String MapJSONSting = MapJSON(shape[i].getProperties().toString());

			myJson += "\"FillColor\" : \"" + Integer.toString(shape[i].getFillColor().getRGB()) + "\" , ";
			myJson += "\"Position\" : \"(" + Integer.toString(shape[i].getPosition().x) + " , "
					+ Integer.toString(shape[i].getPosition().y) + ")\" , ";
			myJson += "\"MapProperities\" : [ " + MapJSONSting + "] } , ";

			// remove "," at the end of the last shape
			if (i == shape.length - 1) {
				myJson = myJson.substring(0, myJson.length() - 2);
				bw.write(myJson);
				bw.newLine();
				myJson = "";
			} else {
				bw.write(myJson);
				bw.newLine();
				myJson = "";
			}
		}
		myJson = "]";
		bw.write(myJson);
		bw.newLine();
		bw.close();
	}

	// Tested
	public ArrayList<Shape> Convert_JSON_to_JAVA(File file) throws IOException {

		FileInputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;

		// read Json from file line by line
		ArrayList<String> JSON = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			JSON.add(line);
		}
		br.close();

		ArrayList<Shape> Shapes = new ArrayList<Shape>();
		Shape shape;

		Map<String, String> ShapAsMap = new HashMap<String, String>();
		Map<String, Double> properities = new HashMap<String, Double>();

		Color Color;
		Color FillColor;

		// start from 1 as 0 ==> "["
		// end at 0 as JSON.size ==> "]"
		for (int i = 1; i < JSON.size() - 1; i++) {

			ShapAsMap = JsonStringTOJava(JSON.get(i));
			shape = Shapefactory.createShape(ShapAsMap.get("Shape"));
			Color = new Color(Integer.parseInt(ShapAsMap.get("Color")));
			shape.setColor(Color);
			FillColor = new Color(Integer.parseInt(ShapAsMap.get("FillColor")));
			shape.setFillColor(FillColor);
			Point point = new Point(getPointFromShapeMap(ShapAsMap.get("Position")));
			shape.setPosition(point);

			// TODO
			// extracting properities and adding it to shape
			for (String value : ShapAsMap.keySet()) {
				if (value.equals("Color") | value.equals("FillColor") | value.equals("Position")
						| value.equals("Shape")) {
				} else {
					properities.put(value, Double.parseDouble(ShapAsMap.get(value)));
				}
			}

			shape.setProperties(properities);
			Shapes.add(shape);
		}
		return Shapes;// TODO test case remove comment
	}

	// Tested
	public void Convert_JAVA_to_XML(String path) throws IOException {

		File fout = new File(path);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

		// I write each shape as json on one line
		String myJson = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
		bw.write(myJson);
		bw.newLine();
		myJson = "";

		myJson = "<Sahpes>";
		bw.write(myJson);
		bw.newLine();
		myJson = "";

		Shape[] shape = (Shape[]) this.getShapes();

		for (int i = 0; i < shape.length; i++) {

			if (shape[i] == null) {
				shape[i] = new Square();
			}

			myJson += "<Shape " + ((Square) shape[i]).getShapeName() + " > <Color> "
					+ Integer.toString(shape[i].getColor().getRGB()) + " </Color>";
			myJson += " <FillColor> " + Integer.toString(shape[i].getFillColor().getRGB()) + " </FillColor>";
			myJson += " <Position> (" + Integer.toString(shape[i].getPosition().x) + " , "
					+ Integer.toString(shape[i].getPosition().y) + ") </Position>";
			myJson += " <MapProperities> " + MapXML(shape[i].getProperties().toString()) + " </MapProperities> </Shape "
					+ (i + 1) + " >";

			bw.write(myJson);
			bw.newLine();
			myJson = "";

		}

		myJson += "</Sahpes>";
		bw.write(myJson);
		bw.newLine();
		bw.close();
	}

	// Tested
	public void Convert_XML_to_JAVA(File file) throws IOException {

		FileInputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;

		// read Json from file line by line
		ArrayList<String> XML = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			XML.add(line);
		}
		br.close();

		ArrayList<Shape> Shapes = new ArrayList<Shape>();
		Shape shape;
		// start from 1 as 0 ==> "["
		// end at 0 as JSON.size ==> "]"
		Map<String, String> ShapAsMap = new HashMap<String, String>();
		Map<String, Double> properities = new HashMap<String, Double>();
		Color Color;
		Color FillColor;
		for (int i = 2; i < XML.size() - 1; i++) {

			ShapAsMap = XMLStringTOJava(XML.get(i));
			shape = Shapefactory.createShape(ShapAsMap.get("Shape"));

			Color = new Color(Integer.parseInt(ShapAsMap.get("Color")));
			shape.setColor(Color);
			FillColor = new Color(Integer.parseInt(ShapAsMap.get("FillColor")));
			shape.setFillColor(FillColor);
			Point point = new Point(getPointFromShapeMap(ShapAsMap.get("Position")));
			shape.setPosition(point);

			// extracting properities and adding it to shape
			for (String value : ShapAsMap.keySet()) {
				if (value.equals("Color") | value.equals("FillColor") | value.equals("Position")
						| value.equals("Shape")) {
				} else {
					properities.put(value, Double.parseDouble(ShapAsMap.get(value)));
				}
			}

			shape.setProperties(properities);
			Shapes.add(shape);

		}

	}

	// Tested
	private Point getPointFromShapeMap(String PointAsString) {
		Point point = null;
		int indexOfNextInt = 0;
		int x = 0;
		int y = 0;
		// (1 , 2)
		char[] mapAsCharArray = new char[PointAsString.length()];
		mapAsCharArray = PointAsString.toCharArray();
		for (int i = 0; i < mapAsCharArray.length; i++) {
			if (mapAsCharArray[i] == ',') {
				x = Integer.parseInt(PointAsString.substring(1, i - 1));
				indexOfNextInt = i + 2;// escape ', '
				y = Integer.parseInt(PointAsString.substring(indexOfNextInt, mapAsCharArray.length - 1));
				break;

			}
		}
		point = new Point(x, y);
		return point;
	}

	// Tested
	private Map<String, String> JsonStringTOJava(String Json) {

		Map<String, String> ShapAsMap = new HashMap<String, String>();

		String buffer = "";
		String Key = "";
		String Value = "";

		int startOfTheWordOrValue = 1;
		char[] mapAsCharArray = new char[Json.length()];
		mapAsCharArray = Json.toCharArray();
		for (int i = 0; i < mapAsCharArray.length; i++) {
			if (mapAsCharArray[i] == ':') {

				buffer = Json.substring(startOfTheWordOrValue, i - 2);// -2 to
																		// escape
																		// '" '
				if (buffer.contains("Shape")) {

					ShapAsMap.put(buffer.substring(0, 5), buffer.substring(6, buffer.length()));
					i += 3;// escape ' { '
					startOfTheWordOrValue = i + 2;
				} else {
					startOfTheWordOrValue = i + 3;
					Key = buffer;
				}

			} else if (mapAsCharArray[i] == '(') {// escape ',' in ( , ) point

				while (mapAsCharArray[i] != ')') {
					i += 1;
				}

			} else if (mapAsCharArray[i] == ',') {

				buffer = Json.substring(startOfTheWordOrValue, i - 2);
				startOfTheWordOrValue = i + 3;// to escape ', "' and reach the
												// start of the next word
				// add to hash map
				Value = buffer;
				ShapAsMap.put(Key, Value);

				// reset value and key
				Value = "";
				Key = "";

			} else if (mapAsCharArray[i] == '[') {
				startOfTheWordOrValue = i + 3;

			} else if (mapAsCharArray[i] == ']') {

				buffer = Json.substring(startOfTheWordOrValue, i - 2);
				startOfTheWordOrValue = i + 3;// to escape ', "' and reach the
												// start of the next word
				// add to hash map
				Value = buffer;
				ShapAsMap.put(Key, Value);

				// reset value and key
				Value = "";
				Key = "";
				break;
			}
		}
		return ShapAsMap;
	}

	// Tested
	private Map<String, String> XMLStringTOJava(String xml) {

		Map<String, String> ShapAsMap = new HashMap<String, String>();

		String buffer = "";
		String Key = "";
		String Value = "";
		int lengthOfKey = 0;

		int startOfTheWordOrValue = 1;
		char[] mapAsCharArray = new char[xml.length()];
		mapAsCharArray = xml.toCharArray();

		for (int i = 0; i < mapAsCharArray.length; i++) {

			if (mapAsCharArray[i] == '>') {

				buffer = xml.substring(startOfTheWordOrValue, i);

				if (buffer.contains("Shape")) {

					ShapAsMap.put("Shape", buffer.substring(6, buffer.length() - 1));

					startOfTheWordOrValue = i + 3;
				} else if (buffer.contains("MapProperities")) {
					startOfTheWordOrValue = i + 3;
				}

				else {
					startOfTheWordOrValue = i + 2;
					Key = buffer;
					lengthOfKey = buffer.length();
				}

			} else if (mapAsCharArray[i] == '<' && mapAsCharArray[i + 1] == '/') {

				if (xml.substring(i + 2, i + 7).contains("Shape")) {
					break;

				} else if (xml.substring(i + 2, i + 7).contains("MapPr")) {

					break;

				} else {

					buffer = xml.substring(startOfTheWordOrValue, i - 1);
					i = i + lengthOfKey + 3;// escape closing tag
					startOfTheWordOrValue = i + 2;

					// add to hash map
					Value = buffer;
					ShapAsMap.put(Key, Value);
				}
				// reset value and key
				Value = "";
				Key = "";
				lengthOfKey = 0;
			}
		}
		return ShapAsMap;
	}

	// Tested
	private String MapJSON(String mapAsString) {

		String mapAsJson = "";
		int startOfTheWordOrValue = 1;

		char[] mapAsCharArray = new char[mapAsString.length()];
		mapAsCharArray = mapAsString.toCharArray();

		for (int i = 0; i < mapAsCharArray.length; i++) {
			if (mapAsCharArray[i] == '=') {
				mapAsJson = "\"";
				mapAsJson += mapAsString.substring(startOfTheWordOrValue, i);
				startOfTheWordOrValue = i + 1;
				mapAsJson += "\" : \"";

			} else if (mapAsCharArray[i] == ',') {
				mapAsJson += mapAsString.substring(startOfTheWordOrValue, i);
				startOfTheWordOrValue = i + 2;
				mapAsJson += "\" , ";
			} else if (mapAsCharArray[i] == '}') {
				mapAsJson += mapAsString.substring(startOfTheWordOrValue, i);
				mapAsJson += "\" ";
			}
		}

		return mapAsJson;
	}

	// Tested
	private String MapXML(String mapAsString) {

		String mapAsXML = "<";
		int startOfTheWordOrValue = 1;
		String PreviousKey = "";

		char[] mapAsCharArray = new char[mapAsString.length()];
		mapAsCharArray = mapAsString.toCharArray();
		for (int i = 0; i < mapAsCharArray.length; i++) {
			if (mapAsCharArray[i] == '=') {
				PreviousKey = mapAsString.substring(startOfTheWordOrValue, i);
				mapAsXML += PreviousKey;
				startOfTheWordOrValue = i + 1;
				mapAsXML += "> ";

			} else if (mapAsCharArray[i] == ',') {
				mapAsXML += mapAsString.substring(startOfTheWordOrValue, i);
				startOfTheWordOrValue = i + 2;
				mapAsXML += " </" + PreviousKey + ">";

			} else if (mapAsCharArray[i] == '}') {
				mapAsXML += mapAsString.substring(startOfTheWordOrValue, i);
				mapAsXML += " </" + PreviousKey + ">";
			}
		}

		return mapAsXML;
	}

	private void steps(){
		if(size == 21){
			undo.remove(0);
			size--;
		}
	}
}
