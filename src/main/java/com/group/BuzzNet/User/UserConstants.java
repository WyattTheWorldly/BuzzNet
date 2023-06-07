package com.group.BuzzNet.User;

import java.util.regex.Pattern;

public final class UserConstants {
    public static final int NAME_LENGTH = 35;
    public static final int USERNAME_LENGTH = 20;
    public static final int PASSWORD_LENGTH = 20;
    public static final int EMAIL_LENGTH = 50;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    private UserConstants(){

    }
}
