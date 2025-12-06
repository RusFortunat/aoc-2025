package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Day six sounds easy!");

        try (InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> input = bufferedReader.lines().toList();
//            long answer = WeirdCalculator.doComputation(input);
//            System.out.println("Answer is: " + answer);
            WeirdCalculator weirdCalculator = new WeirdCalculator();
            long answer2 = weirdCalculator.doComputationPart2(input);
            System.out.println("Answer to part 2: " + answer2);

        } catch (IOException _ioEx){
            System.out.println("IOException: " +  _ioEx.getMessage());
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }



    }
}