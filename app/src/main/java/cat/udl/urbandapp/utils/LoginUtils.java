package cat.udl.urbandapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUtils {

    public boolean isValidEmailAddress(String email){
        final String MAIL_PATTERN ="^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(MAIL_PATTERN);
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN ="^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
