package com.example.calin.task_manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    private static Pattern emailPattern;
    private static Pattern alphaNumericPattern;

    private static Matcher matcher;

    public static Boolean emailValidate(String email) {
        emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean alphaNumericValidate(String string) {
        alphaNumericPattern = Pattern.compile(ALPHA_NUMERIC_REGEX, Pattern.CASE_INSENSITIVE);
        System.out.println(string);
        if (string != null) {
            matcher = alphaNumericPattern.matcher(string);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
