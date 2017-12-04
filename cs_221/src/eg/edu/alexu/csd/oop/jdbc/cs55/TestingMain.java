package eg.edu.alexu.csd.oop.jdbc.cs55;
import java.sql.Statement;
import java.util.Properties;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TestingMain {
	public static void main(String[] args) throws SQLException {
		Driver driver = new ImpDriver();
		Properties info = new Properties();
		File dbDir = new File("Data");
		info.put("path", dbDir.getAbsoluteFile());
		Connection connection = driver.connect("jdbc:xmldb://localhost", info);
		Statement sta = connection.createStatement();
		System.out.println(sta.execute("create table test12(id int,ar varchar)"));
		
		sta.execute("insert into test12(id,ar) values(1,'muhammed')");
		sta.execute("insert into test12(id,ar) values(2,'ahmed')");
		sta.execute("insert into test12 values(3,'yasser')");
		
		ResultSet obj = sta.executeQuery("select * from test12 where id < 14");
		System.out.println(obj.getInt("id"));
	}

}
