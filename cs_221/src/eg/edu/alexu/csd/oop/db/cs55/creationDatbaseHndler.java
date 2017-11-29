package eg.edu.alexu.csd.oop.db.cs55;

import java.io.File;

public class creationDatbaseHndler {

	private File file ;
	creationDatbaseHndler (){
		file = new File ("databases");
		file.mkdirs();
	}
	
	public void createDatabse (String databasename){
		File database =  new File (file.getAbsolutePath() +File.separator+ databasename);
		database.mkdirs();
	}
	
	public String get_path (String databasename){
		File database =  new File (file.getAbsolutePath() +File.separator+ databasename);
		return  database.getAbsolutePath();
	}
	
	public void deleteDatabase(String databaseName){
    	File dir = new File(file.getAbsolutePath() + File.separator + databaseName);
    	delete(dir);
    }
	
	private void delete(File dir) {
    	File[] files = dir.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    delete(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
	}
	
	public boolean dataBaseExists(String databaseName) {
        for(File dir : file.listFiles()){
            if(dir.getName().equalsIgnoreCase(databaseName))
            {
                return true;
            }
        }
        return false;
    }
}
