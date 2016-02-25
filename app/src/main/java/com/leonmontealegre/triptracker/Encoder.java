package com.leonmontealegre.triptracker;

public class Encoder {

    private Encoder() {}

    public static String encodePassword(String password) {
        String encodedPassword = "";
        long totalObfuscated = 0;
        for (int i = 0; i < password.length(); i++) {
            int asciiVal = (int)password.charAt(i);
            totalObfuscated += (((long)Math.pow(asciiVal, 3) * (long)Math.pow(password.length(), 2) * Integer.bitCount(asciiVal*password.length())));
            String obfuscatedVal = "" + totalObfuscated;
            while (obfuscatedVal.length() > 3) {
                int val = Integer.valueOf(obfuscatedVal.substring(0, 3)) + 0x0021;
                encodedPassword += (!Character.isWhitespace((char)val) ? (char)val : "");
                obfuscatedVal = obfuscatedVal.substring(3);
            }
            if (obfuscatedVal.length() > 0)
                encodedPassword += (char)Integer.valueOf(obfuscatedVal).intValue();
        }
        return encodedPassword;
    }

}
