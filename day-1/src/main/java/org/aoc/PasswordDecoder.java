package org.aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordDecoder {

    private int zeroHits;
    private List<String> rotations;

    public PasswordDecoder() {
        this.zeroHits = 0;
        this.rotations = new ArrayList<>();
    }

    public void decode(String inputFileName) {
        // setup, grab all dial rotations input and instantiate a Dial
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFileName)) {
            if (inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                this.rotations = br.lines().toList();
            } else {
                throw new IllegalArgumentException("File not found");
            }
        } catch (IOException _ioException) {
            System.out.println("Error opening file: " + inputFileName);
        }

        System.out.println("Rotations list size: " + this.rotations.size());

        // dial has periodic boundary conditions
        int initialPosition = 50;
        int dialLength = 100;
        Dial northPoleSafe = new Dial(initialPosition, dialLength);

        if(!rotations.isEmpty()) {
            for(String rotation: rotations) {
                int number = Integer.parseInt(rotation.substring(1));
                int delta = rotation.startsWith("L") ? -1 * number : number;
                int zeroCrossCount = northPoleSafe.setPosition(delta);

                this.zeroHits += zeroCrossCount;
                if(northPoleSafe.getPosition() == 0){
                    this.zeroHits++;
                }
            }
        }
    }

    public int getZeroHits() {
        return this.zeroHits;
    }
}
