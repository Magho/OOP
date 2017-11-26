package eg.edu.alexu.csd.oop.db.cs55;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertValidator extends Validation{
	public InsertValidator(){
		super();
	}
	public boolean isValid(String sqlLine){
	
		if(sqlLine.charAt(sqlLine.length()-1)==';')
			sqlLine = sqlLine.substring(0, sqlLine.length()-1);
		boolean valid;
		int flag = 0;
		String variable = null;
		String processedSqlCommand = sqlLine.toLowerCase();
		processedSqlCommand = removeUnusedSpaces(processedSqlCommand);
		String regex = "insert\\sinto\\s([a-z][a-z0-9_-]*)\\s(?:\\((.*)\\))?\\s*values\\s(\\((.*)(,)?\\))*";
		valid = processedSqlCommand.matches(regex);
		Pattern re = Pattern.compile("((?<=(insert\\sinto\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_,]+)+(?=\\)))", Pattern.CASE_INSENSITIVE);
		Matcher m = re.matcher(processedSqlCommand);
		while (m.find() && flag != 1) {
           variable = m.group(0);
            flag = 1;
		}
		valid = valid && checkVariable(variable);
		String reg = ".+([(]([\"\']?[a-z0-9]+[\"\']?,?)*[)]).*";
		System.out.println(processedSqlCommand);
		String val = processedSqlCommand.replaceAll(reg, "$1");
		System.out.println(val);
		val = val.substring(1,val.length()-1);
		for(String str : val.split(",")) {
			if((str.contains("\"")||str.contains("\'"))){
				valid = valid && (str.charAt(str.length()-1)==str.charAt(0));
			} else {
				valid = valid && !val.matches("[a-z]+");
			}
		}
		return valid;
	}

}
