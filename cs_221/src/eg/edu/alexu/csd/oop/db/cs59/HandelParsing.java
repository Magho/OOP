package eg.edu.alexu.csd.oop.db.cs59;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandelParsing {

	private String SQLCommand = "";
	private String currentDatabase = null;

	private Pattern create_database = Pattern.compile("create database");
	private Pattern create_table = Pattern.compile("create table");
	private Pattern drop_database = Pattern.compile("drop database");
	private Pattern drop_table = Pattern.compile("drop table");
	private Pattern insert = Pattern.compile("insert");
	private Pattern update = Pattern.compile("update");
	private Pattern delete = Pattern.compile("delete");
	private Pattern select = Pattern.compile("select");

	private Matcher create_database_Matcher;
	private Matcher create_table_Matcher;
	private Matcher drop_database_Matcher;
	private Matcher drop_table_Matcher;
	private Matcher insert_Matcher;
	private Matcher update_Matcher;
	private Matcher delete_Matcher;
	private Matcher select_Matcher;

	SQL sql;
	SqlOperations sqlOperations;

	private void setMatchers() {
		create_database_Matcher = create_database.matcher(SQLCommand.toLowerCase());
		create_table_Matcher = create_table.matcher(SQLCommand.toLowerCase());
		drop_database_Matcher = drop_database.matcher(SQLCommand.toLowerCase());
		drop_table_Matcher = drop_table.matcher(SQLCommand.toLowerCase());
		insert_Matcher = insert.matcher(SQLCommand.toLowerCase());
		update_Matcher = update.matcher(SQLCommand.toLowerCase());
		delete_Matcher = delete.matcher(SQLCommand.toLowerCase());
		select_Matcher = select.matcher(SQLCommand.toLowerCase());
	}

	// constructor made for executing Query statment
	// add String SQLCommand
	public HandelParsing() {
		sql = SQL.CreateSQL();
		HandelXml.readAllDatabasesBasicInfos(sql);
		sqlOperations = new SqlOperations(sql, getCurrentDatabaseName());
	}

	public int setSQLCommand(String SQLCommand) throws SQLException {
		this.SQLCommand = SQLCommand;
		getSQlCommand();
		setMatchers();
		return determineOperation();
	}

	public void SetCurrentDatabaseName(String dataBaseName, boolean dropIfExists) throws SQLException {
		
		currentDatabase = dataBaseName;
		sqlOperations.setCurrentDatabase(dataBaseName);
		if (sqlOperations.check_If_Database_Is_Already_exists(dataBaseName)) {
			if (dropIfExists) {
				sqlOperations.drop_database(dataBaseName);
				sqlOperations.create_database(dataBaseName);
			} else {
				this.currentDatabase = dataBaseName;
			}
		} else {
			sqlOperations.create_database(dataBaseName);
			this.currentDatabase = dataBaseName;
		}
	}

	// get validated command
	private void getSQlCommand() throws SQLException {
		IValidator validator = ValidationFactory.getValidator(SQLCommand);
		if (validator.IsValid(SQLCommand)) {
		} else {
			throw new SQLException("unValid Query");
		}
		SQLCommand = validator.getSQL();
	}

	public String getCurrentDatabaseName() {
		return this.currentDatabase;
	}

	private static Table table;

	private int determineOperation() throws SQLException {
		if (create_database_Matcher.find()) {
			get_create_database_info();
			return 0;
		} else if (create_table_Matcher.find()) {
			get_create_table_info();
			return 0;
		} else if (drop_database_Matcher.find()) {
			get_drop_database_info();
			return 0;
		} else if (drop_table_Matcher.find()) {
			get_drop_table_info();
			return 0;
		} else if (insert_Matcher.find()) {
			
			return get_insert_info();
		} else if (update_Matcher.find()) {
			
			return get_update_info();
		} else if (delete_Matcher.find()) {
			
			return get_delete_info();
		} else if (select_Matcher.find()) {
			table = get_select_info();
			return 0;
		} else {}
			throw new RuntimeException("Invalid query");// error unknown command
		
	}

	public Table returnSelectedTable() {
		return table;
	}

	public void get_create_database_info() throws SQLException {
		create_database_Matcher.reset(SQLCommand.toLowerCase());
		create_database_Matcher.find();
		// +2 for escaping space and set index to the first element of the name
		int indexOfDataBaseName = create_database_Matcher.end() + 1;
		String DataBaseName = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()).trim();

		SetCurrentDatabaseName(DataBaseName, false);
		//sqlOperations.create_database(DataBaseName);
	}

	public void get_create_table_info() throws SQLException {
		String processedSQLCommand = new String();
	

		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length());
		String tableName = null;
		Map<String, String> coloumn = new TreeMap<>();
		String regex = "((?<=(create\\stable\\s))[\\w\\d_]+(?=\\s+))";
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = re.matcher(processedSQLCommand);
		if (m.find()) {
			tableName = m.group(0);
		}
		processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.indexOf('(') + 1,
				processedSQLCommand.length() - 1);
		String[] splitedProcessedSQLCommand = processedSQLCommand.split(",");
		for (String data : splitedProcessedSQLCommand) {
			String[] splitedData = data.split(" ");
			String key = new String();
			String dataType = new String();
			key = splitedData[0];
			dataType = splitedData[1];
			coloumn.put(key, dataType);
		}
		
		sqlOperations.setCurrentDatabase(currentDatabase);
		sqlOperations.create_table(tableName, coloumn);
	}

	public void get_drop_database_info() throws SQLException {
		drop_database_Matcher.reset(SQLCommand);
		drop_database_Matcher.find();
		int indexOfDataBaseName = drop_database_Matcher.end() + 1;
		String DataBaseName = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()).trim();
		sqlOperations.setCurrentDatabase(DataBaseName);
		sqlOperations.drop_database(DataBaseName);
	}

	public void get_drop_table_info() throws SQLException {
		drop_table_Matcher.reset(SQLCommand);
		drop_table_Matcher.find();
		int indexOfDataBaseName = drop_table_Matcher.end() + 1;
		String DataTable = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()).trim();
		if (DataTable.charAt(DataTable.length()-1) == ';'){
			DataTable = DataTable.substring(0, DataTable.length()-1);
		}
		
		sqlOperations.setCurrentDatabase(currentDatabase);
		sqlOperations.drop_table(DataTable);
	}

	public int get_insert_info() throws SQLException {
		String tableName = null;
		ArrayList<String> groups = new ArrayList<String>();
		ArrayList<String[]> coloumnsValues = new ArrayList<>();
		ArrayList<String> coloumnNames = new ArrayList<String>();
		String regex = "((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()(\\s*[\\w\\d_,'?\"?]+\\s*)+(?=\\)))";
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = re.matcher(SQLCommand);
		while (m.find()) {
			groups.add(m.group(0));
		}
		tableName = groups.get(0);
		if(!SQLCommand.substring(SQLCommand.lastIndexOf(tableName)+tableName.length(), SQLCommand.lastIndexOf("values")).equals(" ")){
			String[] coloumnsNames = groups.get(1).split(",");
			for (int i = 0; i < coloumnsNames.length; i++) {
				coloumnNames.add(coloumnsNames[i]);
			}
			for (int i = 2; i < groups.size(); i++) {
				String[] data = groups.get(i).split(",");
				for(int j = 0; j < data.length; j++){
					if(data[j].charAt(0) == '\'' || data[j].charAt(0) == '\"'){
						data[j] = data[j].substring(1, data[j].length()-1);
					}
				}
				coloumnsValues.add(data);
			}
		}
		else{
			for (int i = 1; i < groups.size(); i++) {
				String[] data = groups.get(i).split(",");
				for(int j = 0; j < data.length; j++){
					if(data[j].charAt(0) == '\'' || data[j].charAt(0) == '\"'){
						data[j] = data[j].substring(1, data[j].length()-1);
					}
				}
				coloumnsValues.add(data);
			}
		}
		sqlOperations.setCurrentDatabase(currentDatabase);
		int i = 0;
		try {
		for(String str : coloumnsValues.get(0)){
			if(str.contains("\"")){
				str = str.substring(1, str.length()-1);
				coloumnsValues.get(0)[i] = str;
			}
			i++;
		}
		} catch(Exception e) {
			throw new SQLException(SQLCommand);
		}
		sqlOperations.insert(tableName, coloumnNames, coloumnsValues);
		
		return coloumnsValues.size();
	}

	public int get_update_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length());
		ArrayList<String> coloumnsNames = new ArrayList<String>();
		ArrayList<String> coloumnsValues = new ArrayList<String>();
		String tableName = "";
		boolean isStarExist = true;
		boolean isWhereExist = processedSQLCommand.toLowerCase().contains("where");
		String coloumnInCondition = null;
		String operator = null;
		String valueToBecombared = null;
		String regex = "((?<=(update\\s))[\\w\\d_]+(?=\\s+))";
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = re.matcher(processedSQLCommand);
		if (m.find()) {
			tableName = m.group(0);
		}
		String dataAfterSet; 
		if(isWhereExist)
			dataAfterSet = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("set") + 4,
				processedSQLCommand.lastIndexOf("where") - 1);
		else{
			dataAfterSet = processedSQLCommand.substring(processedSQLCommand.indexOf("set") + 4);
		}
		String[] splitedDataAfterSet = dataAfterSet.split(",");
		for (String s : splitedDataAfterSet) {
			String data[] = s.split("[><=]=?");
			coloumnInCondition = data[0];
			operator = processedSQLCommand.replaceAll(".+([><=]).+", "$1");
			valueToBecombared = data[1];
			
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
			coloumnsNames.add(coloumnInCondition);
			coloumnsValues.add(valueToBecombared);
		}
		if (isWhereExist) {
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.indexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split("[><=]=?");
			coloumnInCondition = data[0];
			operator = processedSQLCommand.replaceAll(".+([><=]).+", "$1");
			valueToBecombared = data[1];
			
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		}	
		
		for (int i = 0 ; i < coloumnsValues.size() ; i++){
			if (coloumnsValues.get(i).contains("\"")) {
				coloumnsValues.add(coloumnsValues.get(i).substring(1,coloumnsValues.get(i).length()-1));
				coloumnsValues.remove(i);
			}
		}
		return sqlOperations.update(coloumnsNames, coloumnsValues, tableName, coloumnInCondition, operator,
				valueToBecombared, isWhereExist, isStarExist);
	}

	public int get_delete_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length());
		String tableName = "";
		boolean isStarExist = true;
		boolean isWhereExist = processedSQLCommand.toLowerCase().contains("where");
		if(!isWhereExist && processedSQLCommand.contains("*") ){
			tableName = (processedSQLCommand.split("\\*")[1]).split(" ")[2];
		}
		else{
			String regex = "((?<=(delete\\sfrom\\s))[\\w\\d_]+(?=\\s+)?)";
			Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

			Matcher m = re.matcher(processedSQLCommand);
			if (m.find()) {
				tableName = m.group(0);
			}

		}
		String coloumnInCondition = null;
		String operator = null;
		String valueToBecombared = null;
				if (isWhereExist) {
			isStarExist = false;
			tableName = processedSQLCommand
					.substring(processedSQLCommand
							.indexOf("from") + 5,
					processedSQLCommand.indexOf("where") - 1);
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.indexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split("[><=]=?");
			coloumnInCondition = data[0];
			operator = processedSQLCommand.replaceAll(".+([><=]).+", "$1");
			valueToBecombared = data[1];
			
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		}
		return sqlOperations.delete(tableName, coloumnInCondition, operator, valueToBecombared, isWhereExist,
				isStarExist);
	}

	public Table get_select_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length());
		ArrayList<String> coloumnsName = new ArrayList<String>();
		boolean isStarExist = processedSQLCommand.toLowerCase().contains("*");
		boolean isWhereExist = processedSQLCommand.toLowerCase().contains("where");
		String tableName = "";
		String coloumnInCondition = null;
		String operator = null;
		String valueToBecombared = null;
		if (!isStarExist) {
			coloumnsName = new ArrayList<>();
			String[] splitedColumnsNames;
			String subColumnsNames;
			subColumnsNames = processedSQLCommand.substring(processedSQLCommand.indexOf(" ") + 1,
					processedSQLCommand.indexOf("from") - 1);
			if(subColumnsNames.contains(",")){
					splitedColumnsNames = subColumnsNames.split(",");
				
				for (String s : splitedColumnsNames) {
					coloumnsName.add(s);
				}
			}
			else{
				coloumnsName.add(subColumnsNames);
			}
		}
		if (isWhereExist) {
			tableName = processedSQLCommand
					.substring(processedSQLCommand
							.indexOf("from") + 5,
					processedSQLCommand.indexOf("where") - 1);
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.indexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split("[><=]=?");
			coloumnInCondition = data[0];
			operator = processedSQLCommand.replaceAll(".+([><=]).+", "$1");
			valueToBecombared = data[1];

			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		} else {
			tableName = processedSQLCommand.substring(processedSQLCommand.indexOf("from") + 5,
					processedSQLCommand.length());
		}
	
		Table tableToBeReturned = sqlOperations.select(tableName, coloumnInCondition, operator, valueToBecombared,
				coloumnsName, isWhereExist, isStarExist);

		return tableToBeReturned;
	}

}
