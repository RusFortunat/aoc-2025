package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        System.out.println("Second day of AoC 2025! Exciting!");

        long invalidIDsSum = 0;

        try(InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> input = Arrays.stream(bufferedReader.readLine().split(",")).toList();
            System.out.println("input entries: " + input);
            for (String numbers : input ) {
                invalidIDsSum += IDChecker.countInvalidIDs(numbers);
            }

            System.out.println("Sum of invalid IDs found: " + invalidIDsSum);
        } catch (IOException _ioex) {
            System.out.println("Unable to load puzzle-input.txt!\n" + _ioex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception caught during execution! Message: " + ex.getMessage());
        }

    }
}
