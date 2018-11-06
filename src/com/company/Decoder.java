package com.company;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

class Decoder {
    // strings
    public static final String integers = "0123456789";
    public static final String alphabetLow = "abc defghijklmnopqrstuvwxyz";
    public static final String alphabetUpper = alphabetLow.toUpperCase();
    public static final String chars = "()<>,./?[]{}!@#$%^&*_+-=`~";
    public static final String allKeys = alphabetUpper + alphabetLow + integers + chars;

    // keys
    private static final char[] INTEGERS = integers.toCharArray();
    private static final char[] ALPHABET_LOW = alphabetLow.toCharArray();
    private static final char[] ALPHABET_UPPER = alphabetUpper.toCharArray();
    private static final char[] CHARS = chars.toCharArray();

    private static final int INTERVAL = 3;
    private char[] KEYS;

    Decoder() {
        this(allKeys);
    }

    Decoder(String keySet) {
        Set<Character> characterSet = new LinkedHashSet<>();
        int i = 0;
        for (char c : keySet.toCharArray()) {
            characterSet.add(c);
        }
        char[] localKeys = new char[characterSet.size()];
        for (Character character : characterSet) {
            localKeys[i] = character;
            i++;
        }
        this.KEYS = localKeys;
    }

    void printKeySet() {
        for (char key : KEYS) {
            System.out.println(key);
        }
    }

    public String encode(String text) {
        int f = text.charAt(0) - 48;
        int l = text.charAt(text.length() - 1) - 48;

        String first = text.substring(1, f + 1);
        String last = text.substring(text.length() - l - 1, text.length() - 1);

        int ten = Integer.parseInt(convertToText(17, first)) * 10;
        int one = Integer.parseInt(convertToText(19, last));
        return convertText(ten + one, text.substring(f + 1, text.length() - l - 1));
    }

    public String decode(String text) {
        int BASE = getBase(text);
        String first = convertText(17, BASE / 10 + "");
        String last = convertText(19, BASE % 10 + "");
        return first.length() + first + convertToText(BASE, text) + last + last.length();
    }

    private String convert(int from, int to, String expression) {
        if (from < 2 || to < 2) return null;
        char[] number = expression.toCharArray();
        char[] localKeys = Arrays.copyOf(KEYS, from);
        long base10 = 0;
        // convert to base 10
        for (int j = 0; j < number.length; ++j) {
            for (int i = 0; i < localKeys.length; ++i) {
                if (number[number.length - j - 1] == localKeys[i]) {
                    base10 += i * Math.pow(from, j);
                }
            }
        }
        if (to == 10) {
            return base10 + "";
        }
        // convert to base {to}
        StringBuilder result = new StringBuilder();
        while (base10 > 0) {
            result.append(KEYS[(int) (base10 % to)]);
            base10 /= to;
        }

        return String.valueOf(new StringBuilder(result.toString()).reverse());
    }

    private String convertText(int to, String text) {
        String[] lines = text.split("\n");
        StringBuilder res = new StringBuilder();
        for (int i = 0, linesLength = lines.length; i < linesLength; i++) {
            String line = lines[i];
            String[] words = equalParts(line);
            for (String word : words) {
                if (word == null) continue;
                res.append(convert(KEYS.length, to, word));
            }
            if (i < lines.length - 1)
                res.append("\n");
        }
        return res.toString();
    }

    private String convertToText(int from, String text) {
        String[] lines = text.split("\n");
        StringBuilder res = new StringBuilder();
        for (int i = 0, linesLength = lines.length; i < linesLength; i++) {
            String line = lines[i];
            String[] words = equalParts(line);
            for (String word : words) {
                if (word == null) continue;
                res.append(convert(from, KEYS.length, word));
            }
            if (i < lines.length - 1)
                res.append("\n");
        }
        return res.toString();
    }

    private String[] equalParts(String text) {
        String[] result = new String[text.length() / INTERVAL + 1];
        int start = 0, iter = 0;
        for (int i = 0; i < result.length; ++i) {
            if (start + INTERVAL < text.length())
                result[i] = text.substring(start, start + INTERVAL);
            else
                result[i] = text.substring(start);
            start += INTERVAL;
        }
        return result;
    }

    private int getBase(String text) {
        int iter = 0;
        char[] temp = text.toCharArray();
        for (char c : temp) {
            for (int i = 0; i < KEYS.length; i++) {
                if (c == KEYS[i]) {
                    if (iter < i) {
                        iter = i;
                    }
                }
            }
        }
        return iter + 1;
    }
}
