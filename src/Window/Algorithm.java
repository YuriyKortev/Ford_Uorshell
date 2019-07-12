package Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static Window.Windows_par.*;

public class Algorithm {
    GraphPlane graph;
    int[][]adjMatr;
    int i=0;
    ArrayList<Alg_har> history=new ArrayList<>();

    Algorithm(GraphPlane graph,int[][]matr){
        this.graph=graph;
        this.adjMatr=matr;
        while(!graph.points.containsKey(i))i++;
        graph.points.get(i).color=CIRCLE_BORDERLINE_COLOR_IN_ALG;
        graph.repaint();
    }

    public boolean step(){
        if(i==adjMatr.length) return false;
        history.add(new Alg_har(this.graph.points,this.adjMatr));
        for (int v = 0; v < adjMatr.length; v++) {
            for (int u = 0; u < adjMatr.length; u++) {
                adjMatr[v][u] = Math.min(adjMatr[v][u], adjMatr[v][i] + adjMatr[i][u]);
            }
        }
        graph.points.get(i).color=CIRCLE_BORDERLINE_COLOR_BASE;
        i++;
        while(!graph.points.containsKey(i)&&i!=adjMatr.length)i++;
        if(graph.points.containsKey(i))graph.points.get(i).color=CIRCLE_BORDERLINE_COLOR_IN_ALG;
        graph.repaint();
        return true;
    }

    public void step_back(){
        if(i==0){
            JOptionPane.showMessageDialog(graph,
                    "Ошибка: начало алгоритма",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
        }
        else{
            Alg_har back=history.get(history.size()-1);
            history.remove(history.size()-1);
            this.adjMatr=back.adjMatr;
            for(int i=0;i<graph.countV;i++)
                if(graph.points.containsKey(i))graph.points.get(i).color=back.points.get(i);
            graph.repaint();
            i--;
        }
    }

    public void go_to_start(){//откал в начало
        if(history.isEmpty()){
            JOptionPane.showMessageDialog(graph,
                    "Ошибка: уже начало алгоритма",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Alg_har back=history.get(0);
        for(int i=0;i<graph.countV;i++) if(graph.points.containsKey(i))graph.points.get(i).color=back.points.get(i);
        history.clear();

    }

    public int[][] result(){
        while(step());
        return adjMatr;
    }

    public class Alg_har{
        public HashMap<Integer, Color> points;
        public int[][]adjMatr;
        Alg_har(HashMap<Integer,VertexViz>points,int[][]adjMatr){
            this.points=new HashMap<>();
            for(int i=0;i<graph.countV;i++)
                if(points.containsKey(i))this.points.put(i,points.get(i).color);
            this.adjMatr=adjMatr.clone();
        }
    }

}
