package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Third day of the 2025 advent, uhu!!");

        // Once again, load the input first
        try(InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = bufferedReader.lines().toList();

            long joltageSum = 0;
            for (String line : lines) {
                joltageSum += JoltageCounter.findHighestJoltage(line);
                System.out.println("Current joltage is " + joltageSum + "\n");
            }

            System.out.println("Joltage sum: " + joltageSum);

        } catch (IOException _ioException) {
            System.out.println("Failed to load puzzle-input.txt. IOException message: " + _ioException.getMessage());
        } catch (NullPointerException _nullPointerException) {
            System.out.println("Failed to load puzzle-input.txt. NullPointer message: " + _nullPointerException.getMessage());
        }


    }
}