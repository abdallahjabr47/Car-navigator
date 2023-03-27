package jabr1181768;

import java.util.LinkedList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
	public int x;
	public int y;
	public String name;
	public boolean known;
	public double distance;
	public Vertex previous;
	public List<Edge> adjacentEdges;

	public Vertex(String cityName, int x, int y) {
		this.name = cityName;
		this.x = x;
		this.y = y;
		adjacentEdges = new LinkedList<Edge>();
		previous = null;
	}
	
	public void addEdge(Edge edge) {
		adjacentEdges.add(edge);
	}

	@Override
	public int compareTo(Vertex vertex) {
		if (this.distance > vertex.distance)
			return 1;
		else if (this.distance < vertex.distance)
			return -1;
		else
			return 0;
	}
}