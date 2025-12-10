package org.aoc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Day 9! (Actually it is 10.12, but let's not talk about it)");

        try (InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> input = bufferedReader.lines().toList();

            RedRectangle redRectangle = new RedRectangle(input);
            long largestArea = redRectangle.findLargestArea();

            System.out.println("Largest area: " + largestArea);

            // part 2
            long part2Anwer = redRectangle.partTwo();
            System.out.println("Part 2 answer: " + part2Anwer);

        } catch (IOException _ioException) {
            System.out.println("IOException caught when reading the input: " + _ioException.getMessage());
        } catch (Exception _exception) {
            System.out.println("Exception caught when reading the input: " + _exception.getMessage());
        }




    }
}