package org.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Day 7 sounds interesting! Let's dig into it!");

        try (InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> input = bufferedReader.lines().toList();

            BeamSplitter beamSplitter = new BeamSplitter(input.size(), input);
            int beamSplitsCount = beamSplitter.countBeamSplits();
            System.out.println("Beam split count: " + beamSplitsCount);

            // part two
            long timelinesCount = beamSplitter.countTimelines();
            System.out.println("Timelines count: " + timelinesCount);

        } catch (IOException _ioEx) {
            System.out.println("Can't load input! IOException: " + _ioEx.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        }
    }
}