package eg.edu.alexu.csd.oop.db.cs55;

public interface IValidator {
	/**
	 * boolean function return true when the sqlLine
	 * is valid.
	 * @param sqlLine
	 * @return
	 */
	public boolean IsValid(String sqlLine);
	public String getSQL();
}
