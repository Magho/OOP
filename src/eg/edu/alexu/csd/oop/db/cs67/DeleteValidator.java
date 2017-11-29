package eg.edu.alexu.csd.oop.db.cs67;

public class DeleteValidator extends Validation{
	public DeleteValidator(){
		super();
	}
	@Override
	public boolean IsValid(String sqlLine){
		if(sqlLine.charAt(sqlLine.length()-1) == ';')
			sqlLine = sqlLine.substring(0, sqlLine.length()-1);
		boolean valid;
		String processedSqlCommand = sqlLine.toLowerCase();
		processedSqlCommand = removeUnusedSpaces(processedSqlCommand);
		String regex = "delete\\sfrom\\s([a-z][a-z0-9_]*)(\\swhere\\s([a-z][a-z0-9_]*)\\s?[<>=]=?\\s?[\"']?[a-z0-9_\\s]+[\"']?)?";
		valid = processedSqlCommand.matches(regex);
		String reg = ".+where\\s.+[<>=][=]?\\s?(\"?'?[a-z0-9]+\"?'?)";
		if(processedSqlCommand.contains("where")){
			String val = processedSqlCommand.replaceAll(reg, "$1");
			if((val.contains("\"")|| val.contains("\'"))){
				valid = valid && (val.charAt(val.length()-1) == val.charAt(0));
				val = val.substring(0, val.length());
			}else{
				val = val.substring(0, val.length());
				valid = valid && val.matches("[^a-z0-9]+");
			}
		}
		if(valid){
			setSql(processedSqlCommand.toLowerCase());
		} else {
			setSql(null);
		}
		return valid;
	}

}