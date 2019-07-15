package Window;

import Graph.SimpleGraph;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphPlaneTest {

    final static int INF=10_000_000;

    @Test
    public void result() {
        GraphPlane graph1=new GraphPlane();
        int[][] matr1={
                {0,0,1,0,0},
                {2,0,0,0,0},
                {1,2,0,0,0},
                {0,0,3,0,0},
                {1,0,5,6,0},
        };
        int[][] expected1={
                { 0,    3,    1,    INF,  INF},
                {2,    0,    3,    INF,  INF},
                {1,    2,    0,    INF,  INF},
                {4,    5,    3,    0,    INF },
                {1,    4,    2,    6,    0 },
        };
        graph1.addFromKlav(matr1);
        graph1.start_alg();

        GraphPlane graph2=new GraphPlane();
        int[][] matr2={
                {0    ,0    ,1    ,0    ,0    ,},
                {2    ,0    ,0    ,4    ,0    ,},
                {1    ,2    ,0    ,0    ,0    ,},
                {0    ,0    ,3    ,0    ,0    ,},
                {1    ,0    ,0    ,0    ,0    ,},
        };
        int[][] expected2={
                {0    ,3    ,1    ,7    ,INF  ,},
                {2    ,0    ,3    ,4    ,INF  ,},
                {1    ,2    ,0    ,6    ,INF  ,},
                {4    ,5    ,3    ,0    ,INF  ,},
                {1    ,4    ,2    ,8    ,0    ,},
        };
        graph2.addFromKlav(matr2);
        graph2.start_alg();
        GraphPlane graph3=new GraphPlane();
        int[][] matr3={
                {0,2},
                {2,0},
        };
        int[][] expected3={
                {0,2},
                {2,0},
        };
        graph3.addFromKlav(matr3);
        graph3.start_alg();

        GraphPlane graph4=new GraphPlane();
        int[][] matr4={
                {0    ,0    ,0    ,0    ,},
                {2    ,0    ,4    ,6    ,},
                {1    ,0    ,0    ,0    ,},
                {0    ,0    ,1    ,0    ,},
        };
        int[][] expected4={
                {0    ,INF  ,INF  ,INF  ,},
                {2    ,0    ,4    ,6    ,},
                {1    ,INF  ,0    ,INF  ,},
                {2    ,INF  ,1    ,0    ,},
        };
        graph4.addFromKlav(matr4);
        graph4.start_alg();

        GraphPlane graph5=new GraphPlane();
        int[][] matr5={
                {0    ,0    ,1    ,0    ,0    ,0    ,},
                {2    ,0    ,0    ,4    ,0    ,0    ,},
                {1    ,2    ,0    ,0    ,0    ,2    ,},
                {0    ,0    ,3    ,0    ,0    ,3    ,},
                {0    ,0    ,3    ,0    ,0    ,7    ,},
                {1    ,2    ,5    ,6    ,3    ,0    ,},
        };
        int[][] expected5={
                {0    ,3    ,1    ,7    ,6    ,3    ,},
                {2    ,0    ,3    ,4    ,8    ,5    ,},
                {1    ,2    ,0    ,6    ,5    ,2    ,},
                {4    ,5    ,3    ,0    ,6    ,3    ,},
                {4    ,5    ,3    ,9    ,0    ,5    ,},
                {1    ,2    ,2    ,6    ,3    ,0    ,},
        };
        graph5.addFromKlav(matr5);
        graph5.start_alg();
        Assert.assertEquals(expected5,graph5.result());
        Assert.assertEquals(expected4,graph4.result());
        Assert.assertEquals(expected3,graph3.result());
        Assert.assertEquals(expected1,graph1.result());
        Assert.assertEquals(expected2,graph2.result());
    }
}