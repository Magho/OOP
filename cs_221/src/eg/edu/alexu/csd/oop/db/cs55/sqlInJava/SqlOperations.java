package eg.edu.alexu.csd.oop.db.cs55.sqlInJava;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class SqlOperations {
	private String currentDatabase = null;
	SQL sql;

	HandelXml handelXml;

	SqlOperations(SQL sql, String currentDatabase) {
		this.sql = sql;
		this.currentDatabase = currentDatabase;
		this.handelXml = new HandelXml(sql, currentDatabase, this);
	}

	public void create_database(String DataBaseName) throws SQLException {
		DataBase database = new DataBase();
		if (!check_If_Database_Is_Already_exists(DataBaseName)) {
			Handel_Create_Database_If_Not_Exists_Before(database, DataBaseName);
		} else {
			Handel_Create_Database_If_Exists_Before();
		}
	}

	public boolean check_If_Database_Is_Already_exists(String DataBaseName) {
		boolean existsBefore = false;
		for (int i = 0; i < sql.DataBases.size(); i++) {
			if (sql.DataBases.get(i).getName().equals(DataBaseName)) {
				existsBefore = true;
			}
		}
		return existsBefore;
	}

	private void Handel_Create_Database_If_Not_Exists_Before(DataBase database, String DataBaseName) {

		database.setName(DataBaseName);
		sql.DataBases.add(database);
		currentDatabase = DataBaseName;// setting name to current database
		handelXml.create_database_toXML(database.getName());
	}

	private void Handel_Create_Database_If_Exists_Before() throws SQLException {
		throw new SQLException();
	}

	public void create_table(String TableName, Map<String, String> coloumn) throws SQLException {
		if (currentDatabase == null) {
			Handel_no_Current_DataBase_found();
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				Handel_Create_Table_If_Database_Is_Already_exists(TableName, coloumn);
			} else {
				Handel_Database_Is_Not_Exists();
			}
		}
	}

	private DataBase get_Current_Database() {
		int dataBaseIndex = 0;
		for (int i = 0; i < sql.DataBases.size(); i++) {
			if (sql.DataBases.get(i).getName().equals(currentDatabase)) {
				dataBaseIndex = i;
				break;
			}
		}
		DataBase database = sql.DataBases.get(dataBaseIndex);
		return database;
	}

	private void Handel_no_Current_DataBase_found() throws SQLException {
		throw new SQLException();
	}

	private boolean Check_If_table_Is_Already_Exists(String TableName, DataBase database) {
		boolean tableexistsBefore = false;
		for (int i = 0; i < database.tables.size(); i++) {
			if (database.tables.get(i).getName().equals(TableName)) {
				tableexistsBefore = true;
			}
		}
		return tableexistsBefore;
	}

	private void Handel_Create_Table_If_Not_Exists_Before(String TableName, DataBase database,
			Map<String, String> coloumn) {
		Table table = new Table();
		table.setName(TableName);
		table.addColoumns(coloumn);
		database.tables.add(table);
		System.out.println(table.getName() + ">>>" + TableName);
		handelXml.create_table_toXML(database.getName(), TableName);
		handelXml.createDtdFile(database.DataBaseName,table);
	}

	private void Handel_Create_Table_If_Exists_Before() throws SQLException {
		throw new SQLException();

	}

	private void Handel_Database_Is_Not_Exists() throws SQLException {
		throw new SQLException();
	}

	private void Handel_Create_Table_If_Database_Is_Already_exists(String TableName, Map<String, String> coloumn)
			throws SQLException {
		DataBase database = get_Current_Database();

		if (Check_If_table_Is_Already_Exists(TableName, database)) {
			Handel_Create_Table_If_Exists_Before();
		} else {
			Handel_Create_Table_If_Not_Exists_Before(TableName, database, coloumn);
		}
	}

	public void drop_database(String DataBaseName) throws SQLException {
		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				DataBase database = get_Current_Database();
				Handel_Drop_Database_If_Already_Exist(database);
			} else {
				Handel_Database_Is_Not_Exists();
			}
		}
	}

	private void Handel_Drop_Database_If_Already_Exist(DataBase database) {
		sql.DataBases.remove(database);
		handelXml.drop_database_toXML(database.getName());
	}

	public void drop_table(String TableName) throws SQLException {
		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				Handel_Drop_Table_If_Database_Is_Already_exists(TableName);
			} else {
				Handel_Database_Is_Not_Exists();
			}
		}
	}

	private void Handel_Drop_Table_If_Database_Is_Already_exists(String TableName) throws SQLException {
		DataBase database = get_Current_Database();
		if (Check_If_table_Is_Already_Exists(TableName, database)) {
			Handel_Drop_Table_If_Exists(TableName, database);
		} else {
			Handel_Table_If_Not_Exists();
		}
	}

	public Table get_Current_Table(String TableName, DataBase database) {
		int tableIndex = 0;
		for (int i = 0; i < database.tables.size(); i++) {
			if (database.tables.get(i).getName().equals(TableName)) {
				tableIndex = i;
				break;
			}
		}
		Table table = database.tables.get(tableIndex);
		return table;
	}

	private void Handel_Table_If_Not_Exists() throws SQLException {
		throw new SQLException();
	}

	private void Handel_Drop_Table_If_Exists(String TableName, DataBase database) {
		Table table = get_Current_Table(TableName, database);
		database.tables.remove(table);
		handelXml.drop_table_toXML(database.getName(), table.getName());
	}

	public void insert(String TableName, ArrayList<String> ColoumnName, ArrayList<String[]> coloumnsValues)
			throws SQLException {
		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				DataBase database = get_Current_Database();
				if (Check_If_table_Is_Already_Exists(TableName, database)) {
					Handel_Insert_If_Table_Exists(TableName, database, ColoumnName, coloumnsValues);
				} else {
					Handel_Table_If_Not_Exists();
				}
			} else {
				Handel_Database_Is_Not_Exists();
			}
		}
	}

	public void Handel_Insert_If_Table_Exists(String TableName, DataBase database, ArrayList<String> ColoumnName,
			ArrayList<String[]> coloumnsValues) {
		Table table = get_Current_Table(TableName, database);
		Table newInsertedRaws = new Table();
		newInsertedRaws.setName(TableName);
		for (int i = 0; i < coloumnsValues.size(); i++) {
			ArrayList<String> coloumnvalues = new ArrayList<String>();
			for (int j = 0; j < coloumnsValues.size(); j++) {
				coloumnvalues.add(coloumnsValues.get(i)[j]);
			}
			Row row = new Row(ColoumnName, coloumnvalues);
			table.rows.add(row);
			newInsertedRaws.rows.add(row);
		}
		handelXml.insert_toXML(database.getName(), newInsertedRaws);
	}

	public int update(ArrayList<String> coloumsnName, ArrayList<String> coloumsnValues, String tableName,
			String coloumnInCondition, String operator, String valueTobeCombared, boolean isWhereExist,
			boolean isStarExist) throws SQLException {

		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
			return 0;
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				return Handel_Update_If_Database_Is_Already_exists(coloumsnName, coloumsnValues, tableName, coloumnInCondition,
						operator, valueTobeCombared, isWhereExist);
			} else {
				Handel_Database_Is_Not_Exists();
				return 0;
			}
		}
	}

	public int Handel_Update_If_Database_Is_Already_exists(ArrayList<String> coloumsnName,
			ArrayList<String> coloumsnValues, String TableName, String coloumnInCondition, String operator,
			String valueTobeCombared, boolean isWhereExist) throws SQLException {
		DataBase database = get_Current_Database();
		if (Check_If_table_Is_Already_Exists(TableName, database)) {
			return Handel_Update_Table_If_Exists(coloumsnName, coloumsnValues, TableName, database, coloumnInCondition,
					operator, valueTobeCombared, isWhereExist);
		} else {
			Handel_Update_If_Table_Not_Exists();
			return 0;
		}
	}

	public int Handel_Update_Table_If_Exists(ArrayList<String> coloumsnName, ArrayList<String> coloumsnValues,
			String TableName, DataBase database, String coloumnInCondition, String operator, String valueTobeCombared,
			boolean isWhereExist) throws SQLException {
		int count = 0;
		handelXml.create_table_toXML(database.getName(), TableName + "Temp");
		count = handelXml.update_toXML(coloumsnName, coloumsnValues, TableName, database, coloumnInCondition, operator,
				valueTobeCombared, isWhereExist);
		handelXml.drop_table_toXML(database.getName(), TableName);
		File file = new File(database.getName() + "/" + TableName + "Temp.XmL");
		file.renameTo(new File(database.getName() + "/" + TableName + ".XmL"));
		return count;
	}

	public int processTablePart(ArrayList<String> coloumsnName, ArrayList<String> coloumsnValues, Table table,
			DataBase database, String coloumnInCondition, String operator, String valueTobeCombared,
			boolean isWhereExist) throws SQLException {
		int count = 0 ;
		for (int i = 0; i < table.rows.size(); i++) {
			if (isWhereExist) {
				if (table.coloumn.get(coloumnInCondition).compareToIgnoreCase("varchar") == 0) {
					if (operator.equals("=")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) == 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {

								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}

						}
					} else {
						handle_String_And_Comparing_With_Operator_Rather_Than_Equal_();
					}
				} else {
					// as if not VARCHAR then INT as it is validated
					if (operator.equals("=")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) == 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {
								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}
						}
					} else if (operator.equals(">")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) < 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {
								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}
						}
					} else if (operator.equals("<")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) > 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {
								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}
						}
					} else if (operator.equals("<=")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) == 0
								| table.rows.get(i).coloumn.get(coloumnInCondition)
										.compareToIgnoreCase(valueTobeCombared) < 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {
								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}
						}
					} else if (operator.equals(">=")) {
						if (table.rows.get(i).coloumn.get(coloumnInCondition)
								.compareToIgnoreCase(valueTobeCombared) == 0
								| table.rows.get(i).coloumn.get(coloumnInCondition)
										.compareToIgnoreCase(valueTobeCombared) > 0) {
							for (int j = 0; j < coloumsnName.size(); j++) {
								table.rows.get(i).updateRow(coloumsnName.get(j), coloumsnValues.get(j));
								count++;
							}
						}
					}
				}
			} else {
				// no where condition
				table.rows.get(i).updateRow(coloumnInCondition, valueTobeCombared);
				count++;
			}

		}
		handelXml.insert_toXML(database.getName(), table);
		return count;
	}

	private void handle_String_And_Comparing_With_Operator_Rather_Than_Equal_() throws SQLException {
		throw new SQLException();
	}

	private void Handel_Update_If_Table_Not_Exists() throws SQLException {
		throw new SQLException();
	}

	public int delete(String tableName, String coloumnInCondition, String operator, String valueTobeCombared,
			boolean isWhereExist, boolean isStarExist) throws SQLException {
		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
			return 0;
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				return Handel_Delete_If_Database_Is_Already_exists(tableName, coloumnInCondition, operator, valueTobeCombared,
						isWhereExist, isStarExist);
			} else {
				Handel_Database_Is_Not_Exists();
				return 0;
			}
		}
	}

	private int  Handel_Delete_If_Database_Is_Already_exists(String TableName, String coloumnInCondition,
			String operator, String valueTobeCombared, boolean isWhereExist, boolean isStarExist) throws SQLException {
		DataBase database = get_Current_Database();
		if (Check_If_table_Is_Already_Exists(TableName, database)) {
			return Handel_Delete_Table_If_Exists(TableName, database, coloumnInCondition, operator, valueTobeCombared,
					isWhereExist, isStarExist);
		} else {
			Handel_Update_If_Table_Not_Exists();
			return 0;
		}
	}

	public int  Handel_Delete_Table_If_Exists(String TableName, DataBase database, String coloumnInCondition,
			String operator, String valueTobeCombared, boolean isWhereExist, boolean isStarExist) throws SQLException {
		int count ;
		handelXml.create_table_toXML(database.getName(), TableName + "Temp");
		count = handelXml.deleteFromXml(TableName, database, coloumnInCondition, operator, valueTobeCombared, isWhereExist,
				isStarExist);
		handelXml.drop_table_toXML(database.getName(), TableName);
		File file = new File(database.getName() + "/" + TableName + "Temp.XmL");
		file.renameTo(new File(database.getName() + "/" + TableName + ".XmL"));
		return count;
	}

	public int deleteTablePart(Table tableToBeDeleted, DataBase database, String coloumnInCondition, String operator,
			String valueTobeCombared, boolean isWhereExist, boolean isStarExist) throws SQLException {

		int count = 0;
		Table table = tableToBeDeleted;
		if (isStarExist) {

			handelXml.drop_table_toXML(database.getName(), tableToBeDeleted.getName());
			handelXml.create_table_toXML(database.getName(), tableToBeDeleted.getName());

		} else {
			for (int i = 0; i < table.rows.size(); i++) {
				if (isWhereExist) {
					if (table.coloumn.get(coloumnInCondition).compareToIgnoreCase("varchar") == 0) {
						if (operator.equals("=")) {

							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {

								table.rows.remove(i);
								count++;
							}
						} else {
							handle_String_And_Comparing_With_Operator_Rather_Than_Equal_();
						}
					} else {
						// as if not VARCHAR then INT as it is validated
						if (operator.equals("=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {
								table.rows.remove(i);
								count++;
							}
						} else if (operator.equals(">")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) < 0) {
								table.rows.remove(i);
								count++;
							}
						} else if (operator.equals("<")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) > 0) {
								table.rows.remove(i);
								count++;
							}
						} else if (operator.equals("<=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) < 0) {
								table.rows.remove(i);
								count++;
							}
						} else if (operator.equals(">=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) > 0) {
								table.rows.remove(i);
								count++;
							}
						}
					}
				} else {
					// no where condition
					table.rows.remove(i);
					count++;
				}
			}
		}
		handelXml.insert_toXML(database.getName(), table);
		return count;
	}

	public Table select(ArrayList<String> coloumsnName, String tableName, String coloumnInCondition, String operator,
			String valueTobeCombared, ArrayList<String> ColoumnsNames, boolean isWhereExist, boolean isStarExist)
			throws SQLException {
		if (currentDatabase != null) {
			Handel_no_Current_DataBase_found();
		} else {
			if (check_If_Database_Is_Already_exists(currentDatabase)) {
				return Handel_Select_If_Database_Is_Already_exists(tableName, coloumnInCondition, operator, valueTobeCombared,
						ColoumnsNames, isWhereExist, isStarExist);
			} else {
				Handel_Database_Is_Not_Exists();
			}
		}
		return null;
	}

	public Table Handel_Select_If_Database_Is_Already_exists(String TableName, String coloumnInCondition,
			String operator, String valueTobeCombared, ArrayList<String> ColoumnsNames, boolean isWhereExist,
			boolean isStarExist) throws SQLException {
		DataBase database = get_Current_Database();
		if (Check_If_table_Is_Already_Exists(TableName, database)) {
			return handelXml.select_toXML(TableName, database, coloumnInCondition, operator, valueTobeCombared,
					ColoumnsNames, isWhereExist, isStarExist);
		} else {
			Handel_Update_If_Table_Not_Exists();
			return null;
		}
	}

	public Table Handel_Select_Table_If_Exists(Table tableToBeSelected, DataBase database, String coloumnInCondition,
			String operator, String valueTobeCombared, ArrayList<String> ColoumnsNames, boolean isWhereExist,
			boolean isStarExist) throws SQLException {
		Table table = tableToBeSelected;
		Table newTable = new Table();
		for (int i = 0; i < table.rows.size(); i++) {
			if (isStarExist) {
				if (isWhereExist) {
					if (table.coloumn.get(coloumnInCondition).compareToIgnoreCase("varchar") == 0) {
						if (operator.equals("=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						} else {
							handle_String_And_Comparing_With_Operator_Rather_Than_Equal_();
						}
					} else {
						// as if not VARCHAR then INT as it is validated
						if (operator.equals("=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						} else if (operator.equals(">")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) < 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						} else if (operator.equals("<")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) > 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						} else if (operator.equals("<=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) < 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						} else if (operator.equals(">=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) > 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(table.rows.get(i).coloumnName));
								newTable.rows.add(row);
							}
						}
					}
				} else {
					// no where condition
					Row row = new Row(ColoumnsNames, table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
					newTable.rows.add(row);
				}
			} else {
				if (isWhereExist) {
					if (table.coloumn.get(coloumnInCondition).compareToIgnoreCase("varchar") == 0) {
						if (operator.equals("=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						} else {
							handle_String_And_Comparing_With_Operator_Rather_Than_Equal_();
						}
					} else {
						// as if not VARCHAR then INT as it is validated
						if (operator.equals("=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						} else if (operator.equals(">")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) < 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						} else if (operator.equals("<")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) > 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						} else if (operator.equals("<=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) < 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						} else if (operator.equals(">=")) {
							if (table.rows.get(i).coloumn.get(coloumnInCondition)
									.compareToIgnoreCase(valueTobeCombared) == 0
									| table.rows.get(i).coloumn.get(coloumnInCondition)
											.compareToIgnoreCase(valueTobeCombared) > 0) {
								Row row = new Row(ColoumnsNames,
										table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
								newTable.rows.add(row);
							}
						}
					}
				} else {
					// no where condition
					Row row = new Row(ColoumnsNames, table.rows.get(i).SelectSpecificColoumnsValuse(ColoumnsNames));
					newTable.rows.add(row);
				}
			}
		}
		return newTable;
	}

}
