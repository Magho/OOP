package eg.edu.alexu.csd.oop.db.cs55;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {


    public static void main(String []args){
        String processedSQLCommand = "update ts set name = 'labib' where id = 3";
        String regex = "update\\s([a-z][a-z0-9_-]*)(\\sset\\s([a-z][a-z0-9]*)\\s?[<>=]=?\\s?[\"]?[\']?[a-z0-9]+[\"]?[,]?)(\\swhere\\s([a-z][a-z0-9]*)\\s?[<>=]=?\\s?[\"]?[a-z0-9]+[\"]?)?";
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher m = re.matcher(processedSQLCommand);
            System.out.println(m.find());
     }
}	