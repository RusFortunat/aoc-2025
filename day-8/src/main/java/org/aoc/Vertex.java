package org.aoc;

import lombok.Getter;

@Getter
public class Vertex {

    private final int x;
    private final int y;
    private final int z;

    public Vertex(String input) {
        String[] coordinates = input.split(",");
        this.x = Integer.parseInt(coordinates[0]);
        this.y = Integer.parseInt(coordinates[1]);
        this.z = Integer.parseInt(coordinates[2]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y && z == vertex.z;
    }
}
