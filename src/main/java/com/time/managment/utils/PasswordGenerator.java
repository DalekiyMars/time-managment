package com.time.managment.utils;

import com.time.managment.constants.Constants;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder password = new StringBuilder(Constants.PasswordValues.DEFAULT_LENGTH);
        for (int i = 0; i < Constants.PasswordValues.DEFAULT_LENGTH; i++) {
            int index = random.nextInt(Constants.PasswordValues.ALL.length());
            password.append(Constants.PasswordValues.ALL.charAt(index));
        }
        return password.toString();
    }
}
