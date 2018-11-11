package com.company;

import com.company.Algorithms.Decoder;

public class Main {
    public static void main(String[] args) {
        Decoder decoder = new Decoder();
        String text = "0123";
        String decode = decoder.decode(text);
        System.out.println(decode);
        System.out.println("=================");
        System.out.println(decoder.encode(decode));
    }
}
