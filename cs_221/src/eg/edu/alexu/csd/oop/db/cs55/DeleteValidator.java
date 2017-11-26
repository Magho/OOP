package eg.edu.alexu.csd.oop.db.cs55;

public class DeleteValidator extends Validation{
	public DeleteValidator(){
		super();
	}
	public boolean isValid(String sqlLine){
		if(sqlLine.charAt(sqlLine.length()-1) == ';')
			sqlLine = sqlLine.substring(0, sqlLine.length()-1);
		boolean valid;
		String processedSqlCommand = sqlLine.toLowerCase();
		processedSqlCommand = removeUnusedSpaces(processedSqlCommand);
		String regex = "delete\\sfrom\\s([a-z^][a-z0-9]*)(\\swhere\\s([a-z][a-z0-9]*)\\s?[<>=]=?\\s?[\"]?[a-z0-9]+[\"]?)?";
		valid = processedSqlCommand.matches(regex);
		System.out.println(valid);
		String reg = ".+where\\s.+[<>=][=]?\\s?(\"?[a-z0-9]+\"?)";
		String val = processedSqlCommand.replaceAll(reg, "$1");
		System.out.println(val);
		if((val.contains("\"")||!val.contains("\'"))){
			valid = valid && (val.charAt(val.length()-1) == val.charAt(0));
			val = val.substring(0, val.length());
		}else{
			val = val.substring(0, val.length());
			valid = valid && val.matches("[^a-z]+");
		}
		return valid;
	}

}