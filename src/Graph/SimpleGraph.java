package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleGraph extends Graph{
    HashMap<Integer,Vertex> adjacenyList=new HashMap<>();
    ArrayList<Edge> edges=new ArrayList<Edge>();

    @Override
    public boolean addV(int v) {
        if (adjacenyList.containsKey(v)) throw new RuntimeException("Такая вершина уже есть");

        adjacenyList.put(v, new Vertex(v));
        if(v==kolV)kolV++;
        return true;
    }

    @Override
    public boolean addE(Edge e) {

        if ( !adjacenyList.containsKey(e.v1) ) throw new RuntimeException("Вершина "+e.v1+" не существует");
        if ( !adjacenyList.containsKey(e.v2) ) throw new RuntimeException("Вершина "+e.v2+" не существует");
        if ( adjacenyList.get(e.v1).way.containsKey(e.v2)) throw new RuntimeException("Данное ребро уже существует");

        adjacenyList.get(e.v1).way.put(e.v2, e.weight);
      //  adjacenyList.get(e.v2).way.put(e.v1, e.weight);
        edges.add(e);
        kolE++;
        return true;
    }

    @Override
    public boolean removeV(Vertex v) {
        if (!adjacenyList.containsKey(v.v)) throw new RuntimeException("Такой вершины нет");
        for(int i=0;i<kolV;i++){
            if(i==v.v)continue;

            if(adjacenyList.containsKey(i)&& adjacenyList.get(i).way.containsKey(v.v)) adjacenyList.get(i).way.remove(v.v);
        }
        adjacenyList.remove(v.v);
        if(v.v==kolV-1)kolV--;
        return true;
    }

    @Override
    public boolean removeE(Edge e) {
        if ( !adjacenyList.containsKey(e.v1) ) throw new RuntimeException("Вершина "+e.v1+" не существует");
        if ( !adjacenyList.containsKey(e.v2) ) throw new RuntimeException("Вершина "+e.v2+" не существует");
        if ( !adjacenyList.get(e.v1).way.containsKey(e.v2)) throw new RuntimeException("Данное ребро не существует");

        adjacenyList.get(e.v1).way.remove(e.v2);
        //adjacenyList.get(e.v2).way.remove(e.v1);
        edges.remove(e);
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
    public Vertex checkV(int v) {
        return adjacenyList.get(v);
    }

    @Override
    public int countChildren(int v) {
        if (!adjacenyList.containsKey(v)) return -1;
        return adjacenyList.get(v).way.size();
    }

    @Override
    public boolean connectivity() {
        ArrayList<Integer> stek = new ArrayList<Integer>();
        ArrayList<Integer> baseV = this.getVertexes();
        HashMap<Integer,Vertex> list=not_orient_list();
        stek.add(baseV.get(0));
        int n = 0;
        int k = 1;

        while (n < k){
            Graph.Vertex v_i = list.get(stek.get(n));
            for(Map.Entry<Integer,Integer> j: v_i.way.entrySet()) {
                if (!stek.contains(j.getKey().intValue())) {                // И путь меньше уже найденного
                    stek.add(j.getKey().intValue());
                    k++;
                }
            }
            n++;
        }
        return k==adjacenyList.size();
    }

    @Override
    public void clear() {
        adjacenyList = new HashMap<Integer, Vertex>();
        kolE = 0;
        kolV = 0;
    }

    @Override
    public ArrayList<Integer> getVertexes() {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        for(Map.Entry<Integer, Vertex> v: adjacenyList.entrySet()) {
            ret.add(v.getKey());
        }
        return ret;
    }

    private HashMap<Integer,Vertex> not_orient_list(){
        HashMap<Integer,Vertex>ret=new HashMap<>();
        ret=(HashMap)this.adjacenyList.clone();

        for(int i=0;i<edges.size();i++){
            if(!ret.get(edges.get(i).v2).way.containsKey(edges.get(i).v1))ret.get(edges.get(i).v2).way.put(edges.get(i).v1,edges.get(i).weight);
        }
        return ret;
    }
}
