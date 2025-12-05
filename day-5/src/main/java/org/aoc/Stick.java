package org.aoc;

import lombok.Getter;

@Getter
public class Stick {

    private final long position;
    private final long length;

    public Stick(long position, long length) {
        this.position = position;
        this.length = length;
    }

    public Stick(String line) {
        String[] split = line.split("-");
        this.position = Long.parseLong(split[0]);

        long end = Long.parseLong(split[1]);
        this.length = end - this.position;
    }

    @Override
    public String toString() {
        return "Stick{" +
            "position=" + position +
            ", length=" + length +
            '}';
    }
}
