package eg.edu.alexu.csd.oop.db.cs55;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HandelXml {

	SQL sql;
	String currentDatabase;
	SqlOperations sqlOperations;

	public HandelXml(SQL sql, String currentDatabase, SqlOperations sqlOperations) {
		this.sql = SQL.CreateSQL();
		this.currentDatabase = currentDatabase;
		this.sqlOperations = sqlOperations;
	}

	public Table select_toXML(String TableName, DataBase database, String coloumnInCondition, String operator,
			String valueTobeCombared, ArrayList<String> ColoumnsNames, boolean isWhereExist, boolean isStarExist)
			throws SQLException {
		Table selectedTable = new Table();
		Table tableRed = new Table();
		tableRed.setName(TableName + "Temp");
		Table dummytable;
		tableRed.addColoumns(sqlOperations.get_Current_Table(TableName, database).coloumn);
		ArrayList<String> coloumns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory
					.createXMLEventReader(new FileReader(System.getProperty("user.dir") + "\\" + "databases/" + database.getName() + "/" + TableName + ".XmL"));

			int counter = 0;
			while (eventReader.hasNext()) {

				
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {

				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					values.add(characters.getData());
					break;

				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();

					if (endElement.getName().getLocalPart().equals("table")) {

					
						dummytable = sqlOperations.Handel_Select_Table_If_Exists(tableRed, database, coloumnInCondition,
								operator, valueTobeCombared, ColoumnsNames, isWhereExist, isStarExist);
						selectedTable.addColoumns(dummytable.coloumn);
						selectedTable.addTable(dummytable);
						tableRed.rows.clear();
						break;
					} else if (endElement.getName().getLocalPart().equalsIgnoreCase("row")) {
						if (counter % 20 == 0) {

							dummytable = sqlOperations.Handel_Select_Table_If_Exists(tableRed, database, coloumnInCondition,
									operator, valueTobeCombared, ColoumnsNames, isWhereExist, isStarExist);
							selectedTable.addColoumns(dummytable.coloumn);
							selectedTable.addTable(dummytable);
							tableRed.rows.clear();
						}
						counter++;
						Row row = new Row(coloumns, values);

						tableRed.create(row);

						coloumns = new ArrayList<String>();
						values = new ArrayList<String>();
					} else {
						coloumns.add(endElement.getName().getLocalPart());
						if (coloumns.size() > values.size()) {
							values.add("");
						}
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}


		return selectedTable;

	}

	public void create_database_toXML(String databaseName) {
		File dir = new File(System.getProperty("user.dir") + "/" + "Database/" + databaseName);
		dir.mkdir();
		
	}

	public void create_table_toXML(String databaseName, String tableName) {
		File tableFile = new File(System.getProperty("user.dir") + "/" + "Database/" +databaseName + "/" + tableName + ".XmL");
	

		try {
			tableFile.createNewFile();
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("table");

			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();

			xMLStreamWriter.flush();
			xMLStreamWriter.close();

			String xmlString = stringWriter.getBuffer().toString();

			stringWriter.close();

			PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/" + "Database/" + databaseName + "/" + tableName + ".XmL", "UTF-8");
			writer.println(xmlString);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drop_database_toXML(String databaseName) {
		File db = new File("Databases/" + databaseName);
		String[] entries = db.list();
		// System.out.println(databaseName);
		if (entries != null) {
			for (String s : entries) {
				
				File currentFile = new File(db.getPath(), s);
				currentFile.delete();
			}
		}
		System.out.println("end");
		db.delete();
	}

	public void drop_table_toXML(String databaseName, String tableName) {
		File tableFileXmL = new File(System.getProperty("user.dir") + "/" + "Database/" + databaseName + "/" + tableName + ".XmL");
		tableFileXmL.delete();
	}
	
	public void drop_table_toDTD(String databaseName, String tableName) {
		File tableFileDtd = new File(System.getProperty("user.dir") + "/" + "Database/" + databaseName + "/" + tableName + ".dtd");
		tableFileDtd.delete();
	}

	public void insert_toXML(String databaseName, Table newInsertedRaws) {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document document = null;
		try {
			document = documentBuilder.parse(System.getProperty("user.dir") + "/" + "Database/" + databaseName + "/" + newInsertedRaws.getName() + ".XmL");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList rootList = document.getElementsByTagName("table");
		Node root = rootList.item(0);

		for (int i = 0; i < newInsertedRaws.rows.size(); i++) {
			Element row = document.createElement("row");
			root.appendChild(row);

			for (Map.Entry<String, String> r : newInsertedRaws.rows.get(i).coloumn.entrySet()) {
				Element prop = document.createElement(r.getKey());
				prop.setTextContent(r.getValue());
				row.appendChild(prop);
			}
		}

		DOMSource source = new DOMSource(document);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		StreamResult result = new StreamResult("Database/" + databaseName + "/" + newInsertedRaws.getName() + ".XmL");
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public int deleteFromXml(String TableName, DataBase database, String coloumnInCondition, String operator,
			String valueTobeCombared, boolean isWhereExist, boolean isStarExist) throws SQLException {
		Table tableRed = new Table();
		int count = 0;
		tableRed.setName(TableName + "Temp");
		tableRed.addColoumns(sqlOperations.get_Current_Table(TableName, database).coloumn);
		ArrayList<String> coloumns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory
					.createXMLEventReader(new FileReader(System.getProperty("user.dir")+ "/" + "Database/" +database.getName() + "/" + TableName + ".XmL"));
			int counter = 0;
			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();

				switch (event.getEventType()) {

				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					values.add(characters.getData());
					break;

				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();

					if (endElement.getName().getLocalPart().equals("table")) {
						count += sqlOperations.deleteTablePart(tableRed, database, coloumnInCondition, operator,
								valueTobeCombared, isWhereExist, isStarExist);
						tableRed.rows.clear();
						break;
					} else if (endElement.getName().getLocalPart().equalsIgnoreCase("row")) {
						if (counter % 20 == 0) {

							count += sqlOperations.deleteTablePart(tableRed, database, coloumnInCondition, operator,
									valueTobeCombared, isWhereExist, isStarExist);
							tableRed.rows.clear();
						}
						counter++;
						Row row = new Row(coloumns, values);
						tableRed.create(row);
						coloumns = new ArrayList<String>();
						values = new ArrayList<String>();
					} else {
						coloumns.add(endElement.getName().getLocalPart());
						if (coloumns.size() > values.size()) {
							values.add("");
						}
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int update_toXML(ArrayList<String> coloumsnName, ArrayList<String> coloumsnValues, String TableName,
			DataBase database, String coloumnInCondition, String operator, String valueTobeCombared,
			boolean isWhereExist,boolean isStarExist) throws SQLException {
		int count = 0;
		Table tableRed = new Table();
		tableRed.setName(TableName + "Temp");
		tableRed.addColoumns(sqlOperations.get_Current_Table(TableName, database).coloumn);
		ArrayList<String> coloumns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory
					.createXMLEventReader(new FileReader(System.getProperty("user.dir") + "/" + "Database/" + database.getName() + "/" + TableName + ".XmL"));
			int counter = 0;
			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {

				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					// System.out.println(characters.getData());
					values.add(characters.getData());
					break;

				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();

					if (endElement.getName().getLocalPart().equals("table")) {
						count += sqlOperations.processTablePart(coloumsnName, coloumsnValues, tableRed, database,
								coloumnInCondition, operator, valueTobeCombared, isWhereExist, isStarExist);
						tableRed.rows.clear();
						break;
					} else if (endElement.getName().getLocalPart().equalsIgnoreCase("row")) {
						if (counter % 20 == 0) {

							count += sqlOperations.processTablePart(coloumsnName, coloumsnValues, tableRed, database,
									coloumnInCondition, operator, valueTobeCombared, isWhereExist, isStarExist);
							tableRed.rows.clear();
						}
						counter++;
						Row row = new Row(coloumns, values);
						tableRed.create(row);
						coloumns.clear();
						values.clear();
					} else {
						coloumns.add(endElement.getName().getLocalPart());
						if (coloumns.size() > values.size()) {
							values.add("");
						}
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void createDtdFile(String databaseName, Table table) {
		File tableFile = new File(System.getProperty("user.dir") + "/" + "Database/" + databaseName + "/" + table.getName() + ".dtd");
		try {
			tableFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		StringWriter sw = new StringWriter();
		sw.write("<!DOCTYPE table [\n");
		sw.write("<!ELEMENT table (row+)>\n");
		StringBuilder columnsOfARow = new StringBuilder(10 * table.coloumn.size());
		for (String columnName : table.coloumn.keySet()) {
			columnsOfARow.append(columnName + ",");
		}
		sw.write("<!ELEMENT row (" + columnsOfARow.toString().substring(0, columnsOfARow.toString().length() - 1)
				+ ")>\n");
		for (Map.Entry<String, String> column : table.coloumn.entrySet()) {
			sw.write("<!ELEMENT " + column.getKey() + " (#" + column.getValue() + ")>\n");
		}
		sw.write("]>");
		PrintWriter writer = null;
		try {
			File fp = new File("Database/" + databaseName + "/" + table.getName() + ".dtd");
			fp.createNewFile();
			writer = new PrintWriter(fp, "UTF-8");
			writer.println(sw);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readAllDatabasesBasicInfos(SQL sql) {
		File databases = new File(System.getProperty("user.dir") + "/" +"Database");
		FileFilter directoryFileFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		// if the directory does not exist, create it
		if (!databases.exists()) {
			
			boolean result = false;

			try {
				databases.mkdir();
			} catch (SecurityException se) {
				// handle it
			}
		}

		File[] directoryListAsFiles = databases.listFiles(directoryFileFilter);
		List<String> databasesNames = new ArrayList<String>(directoryListAsFiles.length);
		
		for (File database : directoryListAsFiles) {
			databasesNames.add(database.getName());
			DataBase compositeDatabase = new DataBase();
			compositeDatabase.setName(database.getName());
			sql.create(compositeDatabase);
		}
		
		for (int i = 0; i < databasesNames.size(); i++) {
			File database = new File("Database/" + databasesNames.get(i));

			File[] tables = database.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().toLowerCase().endsWith(".dtd");
				}
			});

			for (File currentFile : tables) {
				List<String> fileContent = read(currentFile);
				Map<String, String> columns = getColumnsInfo(fileContent);
				// if(columns == null) return;
				Table table = new Table();
				table.coloumn = columns;
				table.setName(currentFile.getName().substring(0, currentFile.getName().length() - 4));
				sql.DataBases.get(i).tables.add(table);
			}
		}
	}

	static List<String> read(File currentFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(currentFile.getPath()));
			List<String> allLines = new ArrayList<String>();
			String line = br.readLine();

			while (line != null) {
				allLines.add(line);
				line = br.readLine();
			}
			return allLines;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	static Map<String, String> getColumnsInfo(List<String> fileContent) {
		if (fileContent.isEmpty())
			return null;
		fileContent.remove(0);
		fileContent.remove(0);
		fileContent.remove(0);
		fileContent.remove(fileContent.size() - 1);
		Map<String, String> columnsInfo = new HashMap<String, String>();
		for (int i = 0; i < fileContent.size(); i++) {
			String[] splittedLine = fileContent.get(i).split("\\s+");
			String type = null;
			if (splittedLine[2].charAt(splittedLine[2].length() - 3) == 'r') {
				type = "varchar";
			} else {
				type = "int";
			}
			columnsInfo.put(splittedLine[1], type);
		}
		return columnsInfo;
	}

}
