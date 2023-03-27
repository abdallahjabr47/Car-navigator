package jabr1181768;

public class Edge {

  public Vertex sourceCity;
  public Vertex targetCity;
  public double distance;

  public Edge(Vertex vertex1, Vertex vertex2, double d) {
    sourceCity = vertex1;
    targetCity = vertex2;
    this.distance = d;
  }
}