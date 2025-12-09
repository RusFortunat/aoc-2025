package org.aoc;

import lombok.Data;

import java.util.Objects;

@Data
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
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y && z == vertex.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vertex{" +
            "x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }
}
