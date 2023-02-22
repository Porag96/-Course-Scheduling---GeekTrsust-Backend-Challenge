package com.geektrust.backend.helper;

import java.util.regex.Pattern;

public class EmailValidator {

    public static boolean isValidEmail(String email) {
        String emailValidatorRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailValidatorRegex).matcher(email).matches();

    }
}
