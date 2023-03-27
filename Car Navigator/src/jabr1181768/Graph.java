package jabr1181768;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, Vertex> namesOfVertices;
    final double Max_value = Double.MAX_VALUE;

    public Graph() {
        namesOfVertices = new HashMap<String, Vertex>();
    }
     
    public void addVertex(Vertex v) {
        if (namesOfVertices.containsKey(v.name))
            throw new IllegalArgumentException("the city is already exist, please try again!");
        namesOfVertices.put(v.name, v);
    }

    public void addEdge(String sCity, String tCity, double cost) {
        if (!namesOfVertices.containsKey(sCity))
            throw new IllegalArgumentException(sCity + "it doesn't exist, you can't create this edge, please try again!.");
        if (!namesOfVertices.containsKey(tCity))
            throw new IllegalArgumentException(tCity + "it doesn't exist, you can't create this edge, please try again!.");
        Vertex sourceVertex = namesOfVertices.get(sCity);
        Vertex targetVertex = namesOfVertices.get(tCity);
        Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
        sourceVertex.addEdge(newEdge);
    }

    public void addUndirectedEdge(String sCity, String tCity, double d) {
        addEdge(sCity, tCity, d);
        addEdge(tCity, sCity, d);
    }
    
    public double calculateHeuristic(Vertex sCity, Vertex tCity) {
        double theScaleFactor = 0.224;
        return (Math.sqrt(Math.pow((sCity.x - tCity.x), 2) + Math.pow((sCity.y - tCity.y), 2))) * theScaleFactor;
    }

    public void aStarAlgorithm(String s, String t) {
        for (String str : namesOfVertices.keySet()) {
            Vertex node = namesOfVertices.get(str);
            node.previous = null;
            node.known = false;
            node.distance = Max_value;
        }

        namesOfVertices.get(s).distance = 0;
        PriorityQueue<Vertex> minHeap = new PriorityQueue<Vertex>();

        for (Vertex ver : namesOfVertices.values()) {
            minHeap.add(ver);
        }

        while (true) {
            if (minHeap.size() == 0)
                break;

            Vertex v = minHeap.poll();

            if (v.name.equals(t))
                break;

            v.known = true;

            List<Edge> adjacencyList = v.adjacentEdges;

            for (Edge e : adjacencyList) {
                if (!e.targetCity.known) {
                    double distanceCost = e.distance;
                    if (v.distance + distanceCost < e.targetCity.distance) {
                        e.targetCity.distance = v.distance + distanceCost + calculateHeuristic(v, e.targetCity);
                        e.targetCity.previous = v;
                        minHeap.remove(e.targetCity);
                        minHeap.add(e.targetCity);
                    }
                }
            }
        }
    }

    public List<Edge> getThePath(String sCity, String tCity) {
        Vertex temp = null;
    	aStarAlgorithm(sCity, tCity);
        Vertex ver = namesOfVertices.get(tCity);
        LinkedList<Edge> shortestPathEdges = new LinkedList<>();

        while (ver.previous != null) {
            List<Edge> adjacencyList = ver.adjacentEdges;
            for (Edge e : adjacencyList) {
                if (e.targetCity.equals(ver.previous)) {
                    shortestPathEdges.addFirst(e);
                }
            }
            temp = ver;
            ver = ver.previous;
        }
        return shortestPathEdges;
    }
}