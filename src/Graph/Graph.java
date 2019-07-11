package Graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Абстрактный граф
 */
public abstract class Graph {

    int kolV = 0;
    int kolE = 0;

    abstract public boolean addV(int v);

    abstract public boolean addE(Edge e);

    abstract public boolean removeV(Vertex v);

    abstract public boolean removeE(Edge e);

    abstract public Vertex childrenV(int v);

    abstract public Edge checkE(int v1, int v2);

    abstract public int countChildren(int v);

    abstract public boolean connectivity();

    public int getKolE() {
        return kolE;
    }
    public int getKolV() {
        return kolV;
    }




    abstract public void clear();

    abstract public ArrayList<Integer> getVertexes();


    public static class Edge {
        public Edge(int v1, int v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        public int v1;
        public int v2;
        public int weight;
    }
    public static class Vertex {
        public int v;
        public HashMap<Integer,Integer> way = new HashMap<Integer,Integer>();

        public Vertex(int v) {
            this.v = v;
        }
    }

}