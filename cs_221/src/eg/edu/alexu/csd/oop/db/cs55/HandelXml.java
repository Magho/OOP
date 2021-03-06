package eg.edu.alexu.csd.oop.db.cs55;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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
					.createXMLEventReader(new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ database.getName() + System.getProperty("file.separator") + TableName + ".XmL"));

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
		File dir = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName);
		dir.mkdirs();
	}

	public void create_table_toXML(String databaseName, String tableName) {
		File tableFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") +tableName + ".XmL");
	

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

			PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") + tableName + ".XmL", "ISO-8859-1");
			writer.println(xmlString);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drop_database_toXML(String databaseName) {
		File db = new File (System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName );
		String[] entries = db.list();
		if (entries != null) {
			for (String s : entries) {
				
				File currentFile = new File(db.getPath(), s);
				currentFile.delete();
			}
		}
		db.delete();
	}

	public void drop_table_toXML(String databaseName, String tableName) {
		File tableFileXmL = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") + tableName + ".XmL");
		tableFileXmL.delete();
	}
	
	public void drop_table_toDTD(String databaseName, String tableName) {
		File tableFileDtd = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") + tableName + ".dtd");
		tableFileDtd.delete();
	}

	public void insert_toXML(String databaseName, Table newInsertedRaws) {

		
		

		String path = System.getProperty("user.dir") + System.getProperty("file.separator") +"Databases"+ System.getProperty("file.separator") + databaseName + System.getProperty("file.separator") +newInsertedRaws.getName();
 
     	InputStream inputStream = null;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(path + ".XmL");
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
 
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				document = db.parse(is);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
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
    		
        }catch(IOException e){
        	e.printStackTrace();
        }
 
 
 
    // in saving file
 
        Transformer tr = null;
		try {
			tr = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        try {
        	FileOutputStream aa = new FileOutputStream(path + ".XmL");
			tr.transform(new DOMSource(document), new StreamResult(aa));
			reader.close();
			inputStream.close();
			aa.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
					.createXMLEventReader(new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ database.getName() + System.getProperty("file.separator") + TableName + ".XmL"));
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
					.createXMLEventReader(new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ database.getName() + System.getProperty("file.separator") + TableName + ".XmL"));
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

	public void createDtdFile(String databaseName, Table table) {
		File tableFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") + table.getName() + ".dtd");
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
			File fp = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databaseName + System.getProperty("file.separator") + table.getName() + ".dtd");
			fp.createNewFile();
			writer = new PrintWriter(fp, "UTF-8");
			writer.println(sw);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readAllDatabasesBasicInfos(SQL sql) {
		File databases = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases");
		FileFilter directoryFileFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		// if the directory does not exist, create it
		if (!databases.exists()) {
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
			File database = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+ "Databases" +System.getProperty("file.separator")+ databasesNames.get(i));

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
		Map<String, String> columnsInfo = new LinkedHashMap<String, String>();
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
