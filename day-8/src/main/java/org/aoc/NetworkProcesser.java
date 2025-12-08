package org.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class NetworkProcesser {

    private static int MAX_CONNECTIONS = 1000; // 10 for test-input and 1000 for puzzle-input

    // idea is simple -- make all-to-all connections, sort them, leaving only MAX_CONNECTIONS
    // number, and then make circuits out of remaining connections
    public long makeCircuits(List<Vertex> vertices) {

        List<Connection> connections = new ArrayList<>();

        // i exclude double counting here
        for (int v1 = 0; v1 < vertices.size(); v1++) {
            for (int v2 = v1 + 1; v2 < vertices.size(); v2++) {
                Connection connection = new Connection(List.of(vertices.get(v1), vertices.get(v2)),
                    distance(vertices.get(v1), vertices.get(v2)));
                connections.add(connection);
            }
        }

        // sort and leave only MAX_CONNECTIONS with min distance
        Comparator<Connection> compareDistance = Comparator.comparingDouble(Connection::getDistance);
        connections = connections.stream().sorted(compareDistance).limit(MAX_CONNECTIONS).toList();
//        System.out.println("The connections distances are: ");
//        for(Connection connection : connections) {
//            System.out.println(connection.getDistance());
//        }

        // construct circuits from the remaining connections
        List<Circuit> circuits = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        for (int conn1 = 0; conn1 < connections.size() - 1; conn1++) {

            Connection connection1 = connections.get(conn1);
            List<Vertex> conn1Vertices = connection1.getVertices();
            if(!visited.isEmpty() && visited.contains(conn1Vertices.get(0)) || visited.contains(conn1Vertices.get(1))) {
                continue;
            } else {

                visited.addAll(connection1.getVertices());

                Set<Vertex> connectedVertices = new HashSet<>(conn1Vertices);
                int oldSize = connectedVertices.size();
                int newSize = -1;

                while (oldSize != newSize) {
                    oldSize = connectedVertices.size();
                    for(int conn2 = 0; conn2 <  connections.size(); conn2++) {
                        Connection connection2 = connections.get(conn2);
                        List<Vertex> conn2Vertices = connection2.getVertices();
                        if(connectedVertices.contains(conn2Vertices.get(0))
                            || connectedVertices.contains(conn2Vertices.get(1))) {
                            connectedVertices.addAll(conn2Vertices);
                        }
                    }
                    newSize = connectedVertices.size();
                }

                Circuit circuit = new Circuit(connectedVertices.stream().toList());
                circuits.add(circuit);
            }

        }
        circuits = circuits.stream().sorted((a,b) ->b.getVertices().size() - a.getVertices().size()).toList();
        System.out.println("List number of vertices in each circuit: ");
        for (Circuit circuit : circuits) {
            System.out.println(circuit.getVertices().size());
        }

        // find three largest
        circuits = circuits.stream().limit(3).toList();
        System.out.println("Number of vertices in three largest circuits: ["
            + circuits.getFirst().getVertices().size() + ", " + circuits.get(1).getVertices().size()
            + ", " + circuits.getLast().getVertices().size() + "]");

        long product = 1;
        for (Circuit circuit : circuits) {
            product *= circuit.getVertices().size(); // for N vertices we need N-1 connections
        }

        return product;
    }

    private double distance(Vertex v1, Vertex v2) {
        int deltaX = Math.abs(v1.getX() - v2.getX());
        int deltaY = Math.abs(v1.getY() - v2.getY());
        int deltaZ = Math.abs(v1.getZ() - v2.getZ());
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
    }

    @Data
    @AllArgsConstructor
    private class Connection {
        private List<Vertex> vertices;
        private Double distance;
    }

    @Data
    @AllArgsConstructor
    private class Circuit {
        private List<Vertex> vertices;
    }
}
