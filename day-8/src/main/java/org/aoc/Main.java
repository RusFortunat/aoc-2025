package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Day 8! Connecting the dots here");

        try (InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<Vertex> vertices = bufferedReader.lines().map(Vertex::new).toList();

            NetworkProcesser networkProcesser = new NetworkProcesser();
            long answer = networkProcesser.makeCircuits(vertices);
            System.out.println("The answer is: " + answer);

        } catch (IOException _ioException) {
            System.out.println("IOException caught during input reading: " + _ioException.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        }

    }
}