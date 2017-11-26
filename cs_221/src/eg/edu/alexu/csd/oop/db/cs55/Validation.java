package eg.edu.alexu.csd.oop.db.cs55;

public abstract class   Validation implements IValidator{
	private final String[] preservedWords =
		{"SELECT","UPDATE","INSERT","CREATE","ALTER","DROP",
			"TABLE","AND","OR","NOT","INTO",
			"DELETE","WHERE","FROM","AS","DROP",
			"BETWEEN","LIKE","INT","VARCHAR",
			"AVG","COUNT","DISTINCT"};
	private final char[] unsupportedCharInVariable = new char[]
		{'>','<','=','+','-','/','*','\\'
				,':',';','(',')','&','%','#'
				,'@','!','~','.',',','?'};
	private final String[] commands =
		{"SELECT","UPDATE","INSERT","DROP",
				"CREATE","ALTER","DELETE"};
	private final String[] dataType = {"INT", "VARCHAR"};
	private final String[] allowedFunction = {"COUNT","AVG"};
	private final String[] allowedKeyword = {"DISTINCT"};
	private String sql;
	public String[] getAllowedFunction() {
		return allowedFunction;
	}
	public String[] getAllowedKeyword() {
		return allowedKeyword;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String[] getPreservedWords() {
		return preservedWords;
	}
	public char[] getUnsupportedCharInVariable() {
		return unsupportedCharInVariable;
	}
	public String[] getCommands() {
		return commands;
	}
	public String[] getDataType() {
		return dataType;
	}
	@Override
	public boolean IsValid(String sqlLine){
		return false;
	}
	@Override
	public String getSQL(){
		return sql;
		
	}
	/**
	 * check that the sqlLine has only one command.
	 * @param sqlLine
	 * @return
	 */
	public boolean checkCommand(String sqlLine){
		if(sqlLine.indexOf(' ') == -1) return true;
		String command = sqlLine.substring(0,nextSpaceIndex(sqlLine));
		String work = sqlLine.substring(nextSpaceIndex(sqlLine) + 1);
		//indexOf return the index of the first space.
		boolean ret = false;
		for(String str : commands){
			if(str.equalsIgnoreCase(command)) ret = true;
		}
		return ret && !checkCommand(work);
	}
	/**
	 * check whether the name of the variable is allowed or not.
	 * @param variable
	 * @return
	 */
	public boolean checkVariable(String variable) {
		boolean ret = true;
		for(String str : preservedWords){
			if(str.equalsIgnoreCase(variable)) ret = ret && false;
		}
		for(char c :unsupportedCharInVariable){
			if(variable.indexOf(c) != -1) ret = ret && false;
		}
		return ret;
	}
	public int nextSpaceIndex(String line){
		int ret = line.indexOf(" ");
		if(!line.contains(" ")) ret = line.length() - 1;
		return ret;
	}
	
	public boolean isCreate(String sqlLine){
		//create consist of 6 characters.
		if(sqlLine.length() > 6)
			return sqlLine.substring(0, 6).equalsIgnoreCase("create");
		return false;
	}
	public boolean checkSemiColumn(String sqlLine){
		if(sqlLine.indexOf(';') != sqlLine.length() - 1) return false;
		int count = 0;
		for(int i = 0; i < sqlLine.length(); i++){
			if(sqlLine.charAt(i) == ';') count++;
		}
		if(count != 1) return false;
		return true;
	}
	public String removeUnusedSpaces(String line){
		String work = line;
		work = work.replaceAll("\n", " ");
		work = work.replaceAll("[(]", " (");
		work = work.replaceAll("[)]", ") ");
		work = work.trim();
		work = work.replaceAll("\\s+", " ");
		work = work.replaceAll("\\s+;", ";");
		work = work.replaceAll("([(])\\s*", "(");
		work = work.replaceAll("\\s*([)])", ")");
		work = work.replaceAll("\\s*([,])\\s*", ",");
		work = work.replaceAll("\\s+([<>=][=]?)\\s+", "$1");
		return work;
	}

}
