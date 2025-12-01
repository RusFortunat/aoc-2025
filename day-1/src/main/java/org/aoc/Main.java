package org.aoc;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome! Let's try to get the North Pole password!");

        PasswordDecoder pwdDecoder = new PasswordDecoder();
        String puzzleInputFileName = "puzzle-input.txt";
        pwdDecoder.decode(puzzleInputFileName);

        System.out.println("Number of Zero hits: " + pwdDecoder.getZeroHits());
    }
}
