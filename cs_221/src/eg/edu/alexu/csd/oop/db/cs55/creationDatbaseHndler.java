package eg.edu.alexu.csd.oop.db.cs55;

import java.io.File;

public class creationDatbaseHndler {

	private File file ;
	creationDatbaseHndler (){
		file = new File ("databases");
		file.mkdirs();
	}
	
	public void createDatabse (String databasename){
		File database =  new File (file.getAbsolutePath() +file.separator+ databasename);
		database.mkdirs();
	}
	
	public String get_path (String databasename){
		File database =  new File (file.getAbsolutePath() +file.separator+ databasename);
		return  database.getAbsolutePath();
	}
}
