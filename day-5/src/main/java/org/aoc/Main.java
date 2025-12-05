package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day #5, let's focking go! (pronouncing with shitty irish accent)");

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("puzzle-input.txt");) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // the input is a file with two lists, that are separated by a line
            List<Stick> ranges = new ArrayList<>();
            String line;
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                Stick stick = new Stick(line);
                ranges.add(stick);
            }

            List<Long> ids = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                ids.add(Long.parseLong(line));
            }

            FruitSortingMachine fruitSortingMachine = new FruitSortingMachine();
            final var results = fruitSortingMachine.findFreshFruits(ranges, ids);
            System.out.println("Fresh fruits: " + results.getFreshFruits());
            System.out.println("Total range of fresh fruits IDs: " + results.getTotalIDsRange());

        } catch (IOException _ioException) {
            System.out.println("IOException: " + _ioException.getMessage());
        } catch (Exception _exception) {
            System.out.println("Exception: " + _exception.getMessage());
        }
    }
}