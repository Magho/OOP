package eg.edu.alexu.csd.oop.db.cs55;

public class ValidationFactory {
	static public IValidator getValidator(String sqlLine){
		String sql = sqlLine.trim();
		String command = sql.substring(0, sql.indexOf(" "));
		if(command.equalsIgnoreCase("select")){
			return new SelectionValidator();
		}
		if(command.equalsIgnoreCase("insert")){
			return new InsertValidator();
		}
		if(command.equalsIgnoreCase("drop")){
			return new DropValidator();
		}

		if(command.equalsIgnoreCase("delete")){
			return new DeleteValidator();
		}
		if(command.equalsIgnoreCase("create")){
			return new CreationValidator();
		}
		return null;
	}
}
