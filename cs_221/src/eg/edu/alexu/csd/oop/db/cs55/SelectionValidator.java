package eg.edu.alexu.csd.oop.db.cs55;


public class SelectionValidator  extends Validation {
	/*
	 * Select ((name of columns) or keyWord or Function) 
	 * Form (table or another Sql statement) (Where optional) (Condition);
	 */
	/**
	 * used to separate the complicated Statement into small statement and valid each one. 
	 */
	public SelectionValidator () {
		super();
	}
	@Override
	public boolean IsValid(String sqlLine){
		boolean valid;
		String work = removeUnusedSpaces(sqlLine);
		work = work.toLowerCase();
		String selectionRegex = "select\\s([a-z\\*][a-z0-9]*(,)?)*\\sfrom\\s[a-z][a-z0-9]*";
		selectionRegex +="(\\swhere\\s[a-z][a-z0-9]*\\s?[<>=]=?\\s?[\"]?([a-z0-9]+)[\"]?)?;";
		valid = work.matches(selectionRegex);
		String regex = ".+[=<>][=]?([\"]?([a-z0-9]+)[\"]?).+";
		String val = work.replaceAll(regex, "$1");
		if((val.contains("\"")||val.contains("\'"))&&work.matches(regex)){
			valid = valid && (val.charAt(val.length()-1)==val.charAt(0));
			val = val.substring(1, val.length());
			valid = valid && !val.matches("[a-z]+");
		}
		//System.out.println(work);
		if(valid){
			setSql(removeUnusedSpaces(sqlLine));
		}
		return valid;
	}
}
