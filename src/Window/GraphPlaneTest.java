package Window;

import Graph.SimpleGraph;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphPlaneTest {

    final static int INF=10_000_000;

    @Test
    public void result() {
        GraphPlane graph=new GraphPlane();
        int[][] matr={
                {0,0,1,0,0},
                {2,0,0,0,0},
                {1,2,0,0,0},
                {0,0,3,0,0},
                {1,0,5,6,0},
        };
        int[][] expected={
                { 0,    3,    1,    INF,  INF},
                {2,    0,    3,    INF,  INF,},
                {1,    2,    0,    INF,  INF},
                {4,    5,    3,    0,    INF },
                {1,    4,    2,    6,    0 },
        };
        graph.addFromKlav(matr);
        graph.start_alg();
        Assert.assertEquals(expected,graph.result());
    }
}