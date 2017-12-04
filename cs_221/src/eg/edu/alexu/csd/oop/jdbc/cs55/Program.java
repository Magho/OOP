package eg.edu.alexu.csd.oop.jdbc.cs55;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Program {
	void print(Object[][] obj){
		
	}
	
	public static void main(String[] args) {
		System.out.println("JAVA DRIVER CONNECTION");
		Scanner sc = new Scanner(System.in);
		Driver dr = new ImpDriver();
		Connection con;
		Statement stat = null;
		ResultSet resSet = null;
		boolean queryMode = false;
		boolean connected = false;
		try {
			while(true){
				if(queryMode&&connected){
					System.out.println("SQL >> ");
				}else if(!connected){
					System.out.println("insert Connect ");
				}else if(connected){
					System.out.println("1-inserte query");
				}else{
					Object[][] obj = ((ImpResultset) resSet).resault;
					
				}
				String input = sc.nextLine();
				if(input.equalsIgnoreCase("quit")){
					break;
				}
				else if(input.equalsIgnoreCase("Connect")){
					Properties prop = new Properties();
					File fp = new File("Data");
					prop.put("path",fp.getAbsoluteFile());
					System.out.println("inserte username :");
					sc.nextLine();
					System.out.println("insert password :");
					sc.nextLine();
					con = dr.connect("jdbc:XMLDATABASE://localhost", prop);
					stat = con.createStatement();
					connected = true;
				}else if(input.equalsIgnoreCase("insert query")){
					queryMode = true;
				}else if(queryMode&&connected){
					String query = input;
					query = query.toLowerCase();
					if(query.trim().matches("create.+")||query.trim().matches("drop.+")){
						stat.execute(query);
						System.out.println("true");
					}else if(query.trim().matches("update.+")||query.trim().matches("insert.+")||query.trim().matches("delete.+")){
						int response = stat.executeUpdate(query);
						System.out.println(response +" rows effected");
					}else if(query.trim().matches("select.+")){
						resSet = stat.executeQuery(query);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
