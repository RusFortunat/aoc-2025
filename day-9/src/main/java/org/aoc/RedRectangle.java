package org.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class RedRectangle {

    private int[][] field;
    List<Vertex> vertices;

    public RedRectangle(List<String> input) {
        // every input is a X,Y coordinate; what we maybe can do, is to find maxX and maxY first

        this.vertices = new ArrayList<>();

        for (String line : input) {
            String[] coordinate = line.split(",");
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            Vertex vertex = new Vertex(x, y);
            this.vertices.add(vertex);
        }
    }


    public long findLargestArea() {

        List<Connection> connections = new ArrayList<>();
        // i exclude double counting here
        for (int v1 = 0; v1 < vertices.size() - 1; v1++) {
            for (int v2 = v1 + 1; v2 < vertices.size(); v2++) {
                Connection connection = new Connection(Set.of(vertices.get(v1), vertices.get(v2)),
                    distance(vertices.get(v1), vertices.get(v2)));
                connections.add(connection);
            }
        }

        // sort, to find the largest
        Comparator<Connection> comparator = Comparator.comparingDouble(Connection::getDistance);
        connections = connections.stream()
            .sorted(comparator.reversed()).toList();

        long largestArea = 0;
        for (Connection connection : connections) {
            List<Vertex> points = connection.getJoinedVertices().stream().toList();
            int x1 = points.getFirst().getX();
            int y1 = points.getFirst().getY();
            int x2 = points.getLast().getX();
            int y2 = points.getLast().getY();
            // +1 comes from the fact that the range is fully inclusive [a,b]
            long area = (long) (Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1);
            if(area >  largestArea){
                largestArea = area;
            }
        }

        return largestArea;
    }

    public long partTwo() throws Exception{

        // the connections need to be formed differently -- they must form a straight angle
        List<Connection> perpConnections = new ArrayList<>();
        for (int v1 = 0; v1 < vertices.size() - 1; v1++) {
            Vertex vertex =  vertices.get(v1);
            System.out.println("Vertex: " + vertex.getX() + ", " + vertex.getY());
            Set<Vertex> threeVertices = vertices.stream().filter(v ->
                (v.getX() == vertex.getX() && v.getY() != vertex.getY()) ||
                (v.getX() != vertex.getX() && v.getY() == vertex.getY())).collect(Collectors.toSet());
            if(threeVertices.size() > 2){
                System.out.println("Vertices: ");
                for(Vertex vertex2 : threeVertices){
                    System.out.println(vertex2.getX() + "," + vertex2.getY());
                }
                throw new Exception("The approach wouldn't work!\n" +
                    "There are more than two vertices that lay on a straight line that connects them!\n"
                + "Set size: " + threeVertices.size());
            }
        }

        return 0L;
    }

    private double distance(Vertex v1, Vertex v2) {
        long deltaX = Math.abs(v1.getX() - v2.getX());
        long deltaY = Math.abs(v1.getY() - v2.getY());
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Data
    @AllArgsConstructor
    private class Vertex {
        private int x, y;
    }

    @Data
    @AllArgsConstructor
    private class Connection {
        private Set<Vertex> joinedVertices;
        Double distance;
    }
}
