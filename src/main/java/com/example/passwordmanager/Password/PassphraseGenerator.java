package com.example.passwordmanager.Password;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Currently generates a passphrase where each "word" is made out of 2 concatenated words
 * This was done to ensure a stronger password, if in the case an attacker knew what the complete word list is.
 * Can be changed in the future.
 */

public class PassphraseGenerator {      // could use decorator pattern to include design pattern


    /** Retrieves a random word from the words.txt file **/
    private static String getRandomWord() throws FileNotFoundException {
        String randomString = null;
        try {
            RandomAccessFile raf = new RandomAccessFile("src/main/resources/com/example/passwordmanager/Other/words.txt", "r");
            raf.seek(getRandomFromRange((int) (raf.length() + 1)));
            raf.readLine();
            randomString = raf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomString;
    }

    private static String getSinglePassphraseWord() {
        String result;
        try {
            String s1 = getRandomWord();
            result = s1 + getRandomWord();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String getBasePassphrase(int length) {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<length; i++) {
            result.append(getSinglePassphraseWord());
            if (i<length-1) {
                result.append("-");  // Word separator
            }
        }
        return result.toString();
    }

    // Replaces a random letter in a string with its uppercase variant
    private static String includeUppercase(String s) {
        StringBuilder result = new StringBuilder(s);
        while (true) {
            int rndIndex = getRandomFromRange(s.length()-1);
            String rndChar = String.valueOf(s.charAt(rndIndex));
            if (!rndChar.equals("-")) {
                rndChar = rndChar.toUpperCase();
                result.setCharAt(rndIndex, rndChar.charAt(0));  // Spagetti?
                break;
            }
        }
        return result.toString();
    }

    // Inserts a random number in a string
    private static String includeNumber(String s) {
        StringBuilder result = new StringBuilder(s);
        ArrayList<String> numbers = new ArrayList<>(List.of(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
        String rndChoice = numbers.get(getRandomFromRange(numbers.size()-1));
        while (true) {
            int rndIndex = getRandomFromRange(s.length()-1);
            String rndChar = String.valueOf(s.charAt(rndIndex));
            if (!rndChar.equals("-")) {
                result.insert(rndIndex, rndChoice);
                break;
            }
        }
        return result.toString();
    }

    // Inserts a random special character in a string
    private static String includeSpecial(String s) {
        StringBuilder result = new StringBuilder(s);
        ArrayList<String> special = new ArrayList<>(List.of(new String[]{"!", "@", "#", "$", "%", "&", "^", "*"}));
        String rndChoice = special.get(getRandomFromRange(special.size()-1));
        while (true) {
            int rndIndex = getRandomFromRange(special.size()-1);
            String rndChar = String.valueOf(s.charAt(rndIndex));
            if (!rndChar.equals("-")) {
                result.insert(rndIndex, rndChoice);
                break;
            }
        }
        return result.toString();
    }

    // Generates a random passphrase based on the input parameters
    public static String generatePassphrase(int length, boolean includeUppercase, boolean includeNumber, boolean includeSpecial) {
        String base = getBasePassphrase(length);
        if (includeUppercase) {
            base = includeUppercase(base);
        } if (includeNumber) {
            base = includeNumber(base);
        } if (includeSpecial) {
            base = includeSpecial(base);
        }
        return base;
    }

    // Returns a random number between 0 and (including) max
    private static int getRandomFromRange(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }
}
