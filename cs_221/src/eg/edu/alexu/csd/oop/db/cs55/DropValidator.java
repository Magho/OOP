package eg.edu.alexu.csd.oop.db.cs55;

public class DropValidator extends Validation {
	public DropValidator(){
		super();
	}
	@Override
	public boolean IsValid(String sqlLine){
		boolean valid = false;
		String sql = removeUnusedSpaces(sqlLine);
		sql = sql.toLowerCase();
		String regex = "(drop\\stable\\s|drop\\sdatabase\\s){1}[a-z][a-z0-9]+;";
		valid = sql.matches(regex);
		if(valid){
			setSql(removeUnusedSpaces(sqlLine));
		}else{
			setSql(null);
		}
		return valid;
	}
}
