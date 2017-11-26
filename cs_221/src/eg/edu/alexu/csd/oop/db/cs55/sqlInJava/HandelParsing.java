package eg.edu.alexu.csd.oop.db.cs55.sqlInJava;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs55.IValidator;
import eg.edu.alexu.csd.oop.db.cs55.ValidationFactory;

public class HandelParsing {

	private String SQLCommand = "";
	private String currentDatabase = null;

	private Pattern create_database = Pattern.compile("create database");
	private Pattern create_table = Pattern.compile("create table");
	private Pattern drop_database = Pattern.compile("drop database");
	private Pattern drop_table = Pattern.compile("create database");
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
		create_database_Matcher = create_database.matcher(SQLCommand);
		create_table_Matcher = create_table.matcher(SQLCommand);
		drop_database_Matcher = drop_database.matcher(SQLCommand);
		drop_table_Matcher = drop_table.matcher(SQLCommand);
		insert_Matcher = insert.matcher(SQLCommand);
		update_Matcher = update.matcher(SQLCommand);
		delete_Matcher = delete.matcher(SQLCommand);
		select_Matcher = select.matcher(SQLCommand);
	}

	// constructor made for executing Query statment
	// add String SQLCommand
	public HandelParsing() {
		sql = SQL.CreateSQL();
		HandelXml.readAllDatabasesBasicInfos(sql);
		sqlOperations = new SqlOperations(sql, getCurrentDatabaseName());

	}

	public int setSQLCommand(String SQLCommand) {
		this.SQLCommand = SQLCommand;
		try {
			getSQlCommand();
			setMatchers();
			return determineOperation();
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public void SetCurrentDatabaseName(String dataBaseName, boolean dropIfExists) throws SQLException {
		if (sqlOperations.check_If_Database_Is_Already_exists(dataBaseName)) {
			if (dropIfExists) {
				sqlOperations.drop_database(dataBaseName);
				sqlOperations.create_database(dataBaseName);
			} else {
				this.currentDatabase = dataBaseName;
			}
		} else {
			System.out.println("set data");
			sqlOperations.create_database(dataBaseName);
			this.currentDatabase = dataBaseName;
		}

	}

	// get validated command
	private void getSQlCommand() {
		IValidator validator = ValidationFactory.getValidator(SQLCommand);
		if (validator.IsValid(SQLCommand)) {
		} else {
			throw new RuntimeException("unValid Query");
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
		} else {
			throw null;// error unknown command
		}
	}

	public Table returnSelectedTable() {
		return table;
	}

	public void get_create_database_info() throws SQLException {
		create_database_Matcher.reset(SQLCommand);
		create_database_Matcher.find();
		// +2 for escaping space and set index to the first element of the name
		int indexOfDataBaseName = create_database_Matcher.end() + 1;
		String DataBaseName = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()-1).trim();
		sqlOperations.create_database(DataBaseName);
	}

	public void get_create_table_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length() - 1);
		String tableName = null;
		Map<String, String> coloumn = new HashMap<>();
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
		sqlOperations.create_table(tableName, coloumn);
	}

	public void get_drop_database_info() throws SQLException {
		create_database_Matcher.reset(SQLCommand);
		create_database_Matcher.find();
		int indexOfDataBaseName = create_database_Matcher.end() + 2;
		String DataBaseName = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()).trim();
		sqlOperations.drop_database(DataBaseName);
	}

	public void get_drop_table_info() throws SQLException {
		create_database_Matcher.reset(SQLCommand);
		create_database_Matcher.find();
		int indexOfDataBaseName = create_database_Matcher.end() + 2;
		String DataTable = SQLCommand.substring(indexOfDataBaseName, SQLCommand.length()).trim();
		sqlOperations.drop_table(DataTable);
	}

	public int get_insert_info() throws SQLException {
		String tableName = null;
		ArrayList<String> groups = new ArrayList<>();
		ArrayList<String[]> coloumnsValues = new ArrayList<>();
		String regex = "((?<=(insert\\sinto\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_,]+)+(?=\\)))";

		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher m = re.matcher(SQLCommand);
		while (m.find()) {
			groups.add(m.group(0));
		}
		tableName = groups.get(0);
		String[] coloumnsNames = groups.get(1).split(" ");
		for (int i = 2; i < groups.size(); i++) {
			coloumnsValues.add(groups.get(i).split(" "));
		}

		ArrayList<String> coloumnNames = new ArrayList<String>();
		for (int i = 0; i < coloumnsNames.length; i++) {
			coloumnNames.add(coloumnsNames[i]);
		}

		sqlOperations.insert(tableName, coloumnNames, coloumnsValues);
		return coloumnsValues.size();
	}

	public int get_update_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length() - 1);
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
		String dataAfterSet = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("set") + 4,
				processedSQLCommand.lastIndexOf("where") - 1);
		String[] splitedDataAfterSet = dataAfterSet.split(",");
		for (String s : splitedDataAfterSet) {
			String data[] = s.split(" ");
			coloumnsNames.add(data[0]);
			String coloumnValue = data[2];
			if (coloumnValue.charAt(0) == '\'') {
				coloumnValue = coloumnValue.substring(1, coloumnValue.length() - 1);
			}
		}
		if (isWhereExist) {
			isStarExist = false;
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split(" ");
			coloumnInCondition = data[0];
			operator = data[1];
			valueToBecombared = data[2];
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		}
		return sqlOperations.update(coloumnsNames, coloumnsValues, tableName, coloumnInCondition, operator,
				valueToBecombared, isWhereExist, isStarExist);
	}

	public int get_delete_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length() - 1);
		String tableName = "";
		boolean isStarExist = true;
		boolean isWhereExist = processedSQLCommand.toLowerCase().contains("where");
		String coloumnInCondition = null;
		String operator = null;
		String valueToBecombared = null;
		String regex = "((?<=(delete\\sfrom\\s))[\\w\\d_]+(?=\\s+))";
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher m = re.matcher(processedSQLCommand);
		if (m.find()) {
			tableName = m.group(0);
		}
		if (isWhereExist) {
			isStarExist = false;
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split(" ");
			coloumnInCondition = data[0];
			operator = data[1];
			valueToBecombared = data[2];
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		}
		return sqlOperations.delete(tableName, coloumnInCondition, operator, valueToBecombared, isWhereExist,
				isStarExist);
	}

	public Table get_select_info() throws SQLException {
		String processedSQLCommand = new String();
		processedSQLCommand = SQLCommand.substring(0, SQLCommand.length() - 1);
		ArrayList<String> coloumnsName = new ArrayList<String>();
		boolean isStarExist = processedSQLCommand.toLowerCase().contains("*");
		boolean isWhereExist = processedSQLCommand.toLowerCase().contains("where");
		String tableName = "";
		String coloumnInCondition = null;
		String operator = null;
		String valueToBecombared = null;
		ArrayList<String> ColoumnsNames = null;
		if (!isStarExist) {
			String[] splitedColumnsNames = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("select") + 7,
					processedSQLCommand.lastIndexOf("from") - 1).split(",");
			for (String s : splitedColumnsNames) {
				coloumnsName.add(s);
			}
		}
		if (isWhereExist) {
			tableName = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("FROM") + 5,
					processedSQLCommand.lastIndexOf("WHERE") - 1);
			processedSQLCommand = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("where") + 6,
					processedSQLCommand.length());
			String data[] = processedSQLCommand.split(" ");
			coloumnInCondition = data[0];
			operator = data[1];
			valueToBecombared = data[2];
			if (valueToBecombared.charAt(0) == '\'') {
				valueToBecombared = valueToBecombared.substring(1, valueToBecombared.length() - 1);
			}
		} else {
			tableName = processedSQLCommand.substring(processedSQLCommand.lastIndexOf("FROM") + 5,
					processedSQLCommand.length());
		}
		return sqlOperations.select(coloumnsName, tableName, coloumnInCondition, operator, valueToBecombared,
				ColoumnsNames, isWhereExist, isStarExist);
	}

}
