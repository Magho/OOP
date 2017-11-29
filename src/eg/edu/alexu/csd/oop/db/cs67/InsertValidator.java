package eg.edu.alexu.csd.oop.db.cs67;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertValidator extends Validation{
	public InsertValidator(){
		super();
	}
	@Override
	public boolean IsValid(String sqlLine){
	
		if(sqlLine.charAt(sqlLine.length()-1)==';')
			sqlLine = sqlLine.substring(0, sqlLine.length()-1);
		boolean valid;
		int flag = 0;
		String variable = null;
		String processedSqlCommand = sqlLine.toLowerCase();
		processedSqlCommand = removeUnusedSpaces(processedSqlCommand);
		String regex = "insert\\sinto\\s([a-z][a-z0-9_-]*)\\s(?:\\((.*)\\))?\\s*values\\s(\\((.*)(,)?\\))*";
		valid = processedSqlCommand.matches(regex);
		String regex2 = "((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()(\\s*[\\w\\d_,'?\"?]+\\s*)+(?=\\)))";
		Pattern re = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
		Matcher m = re.matcher(processedSqlCommand);
	/*	while (m.find() && flag ==0) {
			variable = m.group(0);
			flag = 1;
		}
	//	flag = 0;
		valid = valid && checkVariable(variable);*/
		String reg = ".+([(]([\"\']?[a-z0-9]+[\"\']?,?)*[)]).*";
		String val = processedSqlCommand.replaceAll(reg, "$1");
		val = val.substring(1,val.length()-1);
		for(String str : val.split(",")) {
			if((str.contains("\"")||str.contains("\'"))){
				valid = valid && (str.charAt(str.length()-1)==str.charAt(0));
			} else {
				valid = valid && !val.matches("[a-z0-9]+");
			}
		}
	
		if(valid){
			setSql(processedSqlCommand.toLowerCase());
		}else{
			setSql(null);
		}
		
		return valid;
	}

}
