package com.company.Algorithms;

import java.util.Arrays;

public class Decoder {

    // strings
    private static final String integers = "0123456789";
    private static final String alphabetLow = "abcdefghijklmnopqrstuvwxyz";
    private static final String alphabetUpper = alphabetLow.toUpperCase();
    private static final String chars = "()<>,./?[]{ }!@#$:;'%^&*_+-=`~";
    private static final String allKeys = integers + alphabetUpper + alphabetLow + chars;
    private static final int INTERVAL = 3;
    // keys
    private final int BASE_ONE = 3, BASE_TWO = 5;
    private char[] KEYS = allKeys.toCharArray();

    public void printKeySet() {
        for (char key : KEYS) {
            System.out.println(key);
        }
    }

    public String encode(String text) {
        int f = text.charAt(0) - 48;
        int l = text.charAt(text.length() - 1) - 48;
        String first = text.substring(1, f + 1);
        String last = text.substring(text.length() - l - 1, text.length() - 1);
        int ten = 0, one = 0;
        if (!first.equals("")) ten = Integer.parseInt(convertToText(BASE_ONE, first)) * 10;
        if (!last.equals("")) one = Integer.parseInt(convertToText(BASE_TWO, last));
        String substring = text.substring(f + 1, text.length() - l - 1);
        return convertText(ten + one, substring); // convertText
    }

    /**
     * used convertToText to convert the text, while convertText for the base
     */
    public String decode(String text) {
        int BASE = getBase(text);
        String first = convertText(BASE_ONE, BASE / 10 + "");
        String last = convertText(BASE_TWO, BASE % 10 + "");
        String convert = convertToText(BASE, text); // convertToText
        String toBeDecoded = first.length() + first + convert + last + last.length();
        return toBeDecoded;
    }

    private String convertText(int to, String text) {
        String[] lines = text.split("\n");
        StringBuilder res = new StringBuilder();
        for (int i = 0, linesLength = lines.length; i < linesLength; i++) {
            String line = lines[i];
            if (getBase(line) > 10 && to > 10) { // checks if contains only numbers
                String[] words = equalParts(line);
                for (String word : words) {
                    if (word == null) continue;
                    res.append(convert(KEYS.length, to, word));
                }
            } else {
                String convert = convert(KEYS.length, to, line);
                res.append(convert);
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
            if (getBase(line) > 10) { // checks if contains only numbers
                String[] words = equalParts(line);
                for (String word : words) {
                    if (word == null) continue;
                    String convert = convert(from, KEYS.length, word);
                    res.append(convert);
                }
            } else {
                String convert = convert(from, KEYS.length, line);
                res.append(convert);
            }
            if (i < lines.length - 1)
                res.append("\n");
        }
        return res.toString();
    }

    private String[] equalParts(String text) {
        String[] result = new String[text.length() / INTERVAL + 1];
        int start = 0;
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
}
