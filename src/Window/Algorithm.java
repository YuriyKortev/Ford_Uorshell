package Window;

import javax.swing.*;
import java.util.HashMap;

import static Window.Windows_par.*;

public class Algorithm {
    GraphPlane graph;
    int[][]adjMatr;
    int i=0;

    Algorithm(GraphPlane graph,int[][]matr){
        this.graph=graph;
        this.adjMatr=matr;
        while(!graph.points.containsKey(i))i++;
        graph.points.get(i).color=CIRCLE_BORDERLINE_COLOR_IN_ALG;
        graph.repaint();
    }
    public boolean step(){
        if(i==adjMatr.length) return false;
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

    public int[][] result(){
        while(step());
        return adjMatr;
    }

}
