package Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleGraph extends Graph{
    HashMap<Integer,Vertex> adjacenyList=new HashMap<>();


    @Override
    public boolean addV(int v) {
        if (adjacenyList.containsKey(v)) throw new RuntimeException("Такая вершина уже есть");

        adjacenyList.put(v, new Vertex(v));
        kolV++;
        return true;
    }

    @Override
    public boolean addE(Edge e) {

        if ( !adjacenyList.containsKey(e.v1) ) throw new RuntimeException("Вершина "+e.v1+" не существует");
        if ( !adjacenyList.containsKey(e.v2) ) throw new RuntimeException("Вершина "+e.v2+" не существует");
        if ( adjacenyList.get(e.v1).way.containsKey(e.v2)) throw new RuntimeException("Данное ребро уже существует");

        adjacenyList.get(e.v1).way.put(e.v2, e.weight);
        adjacenyList.get(e.v2).way.put(e.v1, e.weight);

        kolE++;
        return true;
    }

    @Override
    public boolean removeV(Vertex v) {
        if (!adjacenyList.containsKey(v.v)) throw new RuntimeException("Такой вершины нет");

        adjacenyList.remove(v.v);
        kolV--;
        return true;
    }

    @Override
    public boolean removeE(Edge e) {
        if ( !adjacenyList.containsKey(e.v1) ) throw new RuntimeException("Вершина "+e.v1+" не существует");
        if ( !adjacenyList.containsKey(e.v2) ) throw new RuntimeException("Вершина "+e.v2+" не существует");
        if ( !adjacenyList.get(e.v1).way.containsKey(e.v2)) throw new RuntimeException("Данное ребро не существует");

        adjacenyList.get(e.v1).way.remove(e.v2);
        adjacenyList.get(e.v2).way.remove(e.v1);

        kolE--;
        return true;
    }

    @Override
    public Vertex childrenV(int v) {
        return adjacenyList.get(v);
    }

    @Override
    public Edge checkE(int v1, int v2) {
        if (childrenV(v1)!=null && childrenV(v2)!=null) {
            Integer i = adjacenyList.get(v1).way.get(v2);

            return i==null ? null : new Edge(v1,v2,i.intValue());
        }
        return null;
    }

    @Override
    public int countChildren(int v) {
        if (!adjacenyList.containsKey(v)) return -1;
        return adjacenyList.get(v).way.size();
    }

    @Override
    public boolean connectivity() {
        return false;
    }

    @Override
    public void clear() {
        adjacenyList = new HashMap<Integer, Vertex>();
        kolE = 0;
        kolV = 0;
    }

    @Override
    public ArrayList<Integer> getVertexes() {
        return null;
    }
}
