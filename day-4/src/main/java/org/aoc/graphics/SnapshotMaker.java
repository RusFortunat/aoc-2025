package org.aoc.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SnapshotMaker {

    private static final int SCALE = 5;

    public static void createSnapshot(int[][] floorWithRolls, int iteration) {

        int height = floorWithRolls.length;
        int width = floorWithRolls[0].length;

        BufferedImage image = new BufferedImage(
            width * SCALE,
            height * SCALE,
            BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = image.createGraphics();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = floorWithRolls[y][x];
                Color color = value == 0 ? Color.WHITE : Color.BLACK;

                g2d.setColor(color);
                g2d.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }

        g2d.dispose();

        try {
            File dir = new File("snapshots");
            if (!dir.exists()) {
                dir.mkdirs(); // create folder if missing
            }

            File outputFile = new File(dir, "snapshot_" + iteration + ".png");
            if(!outputFile.exists()) {
                ImageIO.write(image, "png", outputFile);
            }
        } catch (IOException _ioEx) {
            System.out.println("Failed to save snapshot to resource folder! " +
                "IOException: " + _ioEx.getMessage());
        }
    }
}
