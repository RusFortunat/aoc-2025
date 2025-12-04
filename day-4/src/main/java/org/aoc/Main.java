package org.aoc;

import org.aoc.graphics.AnimationMaker;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("May the 4th be with you!");

        try(InputStream inputStream = Main.class.getClassLoader()
            .getResourceAsStream("puzzle-input.txt")) {

            // load and parse input
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> input = bufferedReader.lines().toList();
            int[][] floorWithRolls = new int[input.size()][];
            for (int row = 0; row < input.size(); row++) {
                String line = input.get(row);
                int[] rollsRow = new int[line.length()];
                for(int i = 0; i < rollsRow.length; i++) {
                    rollsRow[i] = line.charAt(i) == '@' ? 1 : 0;
                }
                floorWithRolls[row] = rollsRow;
            }

            int rolls = FloorWithRolls.countAccessibleRolls(floorWithRolls);

            System.out.println("Number of accessible rolls: " + rolls);

            // create a GIF animation
            ImageOutputStream output = new FileImageOutputStream(new File("animation.gif"));

            // delay = 200ms per frame, loop = true
            AnimationMaker writer = new AnimationMaker(
                output,
                BufferedImage.TYPE_INT_RGB,
                200,
                true
            );

            int totalIterations = FloorWithRolls.getIterations();
            for(int frame = 0; frame < totalIterations; frame++) {
                String imageName = "snapshots/snapshot_" + frame + ".png";
                BufferedImage image = ImageIO.read(new File(imageName));
                writer.writeToSequence(image);
            }
            writer.close();

        } catch (IOException _ioex) {
            System.out.println("IOException: " + _ioex.getMessage());
        } catch(NullPointerException _nullex) {
            System.out.println("NullPointerException: " + _nullex.getMessage());
        }
    }
}