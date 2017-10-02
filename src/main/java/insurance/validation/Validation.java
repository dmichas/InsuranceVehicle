package insurance.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean validateUserInput(String input, String pattern){
        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(input);
        return matcher.matches();
    }
}
