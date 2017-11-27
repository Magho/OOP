package eg.edu.alexu.csd.oop.db.cs55;
/**
 * this class validate the Create and Drop sql command.
 * @author Muhammed
 *
 */
public class CreationValidator extends Validation {
	
	public CreationValidator(){
		super();
	}
	
	@Override
	public boolean IsValid(String sqlLine) {
		String sql = removeUnusedSpaces(sqlLine);
		boolean valid = false;
		sql = sql.toLowerCase();
		String regexDataBase = "create\\sdatabase\\s[a-z][a-z0-9]*.*";
		if(sql.matches(regexDataBase)){
			valid = true;
		}
		String regexTable = "create\\stable\\s[a-z][a-z0-9]*.*";
		if(sql.matches(regexTable)){
			sql = sql.replaceAll("create\\stable\\s[a-z][a-z0-9]*\\s[(]", "");
			sql = sql.replaceAll("[)];?", "");
			String[] elements = sql.split(",");
			valid = true;
			for(String str : elements){
				valid = valid && str.matches("[a-z][a-z0-9]*\\s[a-z]{3,7}");
			}
		}
		String ret = removeUnusedSpaces(sqlLine);
		if(sqlLine.contains(";")){
			ret = ret.substring(0,
					ret.length()-1);
		}
		
		System.out.println(valid);
		System.out.println(sql);
		if(valid){
			setSql(ret);
		}else{
			setSql(null);
		}
		return valid;
	}

	
	
	private boolean isTable(String sqlLine){
		String str = sqlLine;
		str = str.substring(this.nextSpaceIndex(sqlLine) + 1);
		if(str.length() > 5)
			str = str.substring(0, 5);//table consists of 5 characters.
		return str.equalsIgnoreCase("table");
	}
	
	
	private String extractTableName(String sqlLine){
		String variable;
		String str = sqlLine;
		str = str.substring(nextSpaceIndex(str) + 1);
		str = str.substring(nextSpaceIndex(str) + 1);
		if(str.length() == 0) return "#unAllowed";
		variable = str.substring(0, nextSpaceIndex(str));
		int lastIndex = variable.length()-1;
		if(variable.charAt(lastIndex) == '('){
			variable = variable.substring(0, lastIndex);
		}
		return variable;
	}
	private String extractDataBaseName(String sqlLine){
		String str = sqlLine;
		if(str.equalsIgnoreCase("create;")) return "#unAllowed";
		str = str.substring(0, nextSpaceIndex(str));
		str = str.substring(0, nextSpaceIndex(str));
		str = str.replace(";", "");
		if(str.length() == 0) return "#unAllowed";
		return str;
	}
	private boolean checkColumn(String line){
		boolean ret = true;
		String str = line.substring(1, line.length()-1);// remove ( , )
		str = str.trim();
		String[] prop = str.split(",");
		for(String s : prop){
			s = s.trim();
			String[] temp = s.split(" ");
			ret = ret && checkVariable(temp[0]);
			ret = ret && isDataType(temp[1]);
		}
		return ret;
	}
	private boolean isDataType(String word){
		for(String str : this.getDataType()) {
			if(str.equalsIgnoreCase(word)) return true;
		}
		return false;
	}
	
}