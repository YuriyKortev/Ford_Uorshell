package Window;

import Graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.math.*;

import static Window.Windows_par.*;

public class GraphPlane extends JPanel {
    final static int INF=10_000_000;
    int countV=0;
    int countE=0;
    HashMap<Integer,VertexViz> points=new HashMap<>();
    HashMap<Integer,Graph.Edge> edges=new HashMap<>();
    Graph graph=new SimpleGraph();
    Algorithm floyd_warshell=null;

    GraphPlane(){
        setLayout(null);
        setPreferredSize( SIZE_OF_GRAPH_FIELD );    //Размер рамки
        setBackground( BACKGROUND );
/*
        int[][] matr={
                {0,0,1,0,0},
                {2,0,0,0,0},
                {1,2,0,0,0},
                {0,0,3,0,0},
                {1,0,5,6,0},
        };
        addFromKlav(matr);
        start_alg();
        floyd_warshell.step();
        int[][]result=floyd_warshell.result();
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result.length;j++){
                if(result[i][j]==INF)
                    System.out.print("INF  ");
                else
                    System.out.print(result[i][j]+"    ");
            }
            System.out.print('\n');
        }
        */

    }

    private int[][] list_in_matrix(){
        int[][] matr=new int[countV][countV];
        for(int i=0;i<countV;i++){
            for(int j=0;j<countV;j++){
                if(i!=j) matr[i][j]=INF;
                else matr[i][j]=0;
            }
        }

        for(int i=0;i<countE;i++){
            if(edges.containsKey(i))matr[edges.get(i).v1][edges.get(i).v2]=edges.get(i).weight;
        }
        return matr;
    }

    public void start_alg(){    //начать алгоритм(кнопка)
        this.floyd_warshell=new Algorithm(this,list_in_matrix());
    }

    public void step() { //кнопка шага вперед
        if (floyd_warshell == null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм еще не запущен",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!floyd_warshell.step()) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм уже закончил работу",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
        }
    }       //шаг вперед(кнопка)

    public int[][] result(){    //алгоритм сразу
        if(floyd_warshell==null){
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм еще не запущен",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int[][] res=floyd_warshell.result();
        floyd_warshell=null;
        for(int i=0;i<countV;i++)
            if(points.containsKey(i))points.get(i).color=CIRCLE_BORDERLINE_COLOR_BASE;
        return res;
    }   //алгоритм сразу(кнопка)

    public void addFromKlav(int [][] adjMatr){
        graph=new SimpleGraph();
        points=new HashMap<>();
        edges=new HashMap<>();
        for(int i=0;i<adjMatr.length;i++) addV();

        for(int i=0;i<adjMatr.length;i++){
            for(int j=0;j<adjMatr.length;j++){
                if(i==j)continue;
                if(adjMatr[i][j]!=0)
                    addE(new Graph.Edge(i,j,adjMatr[i][j]));
            }

        }
    }//сюда матрицу(кнопка ввести с клавиатуры)

    @Override
    public void paint(Graphics g) {

        g.setColor( GRAPH_FIELD_BACKGROUND );
        g.fillRect(0,0,600,500);

        drawGraph(g);

        g.setColor( GRAPH_FIELD_BORDER );                // Цвет рамки
        ((Graphics2D)g).setStroke(new BasicStroke(4));  // Толщина рамки
        g.drawRect( 0, 0,
                SIZE_OF_GRAPH_FIELD.width,
                SIZE_OF_GRAPH_FIELD.height);        // Нарисовать рамку



    }



    public void addV(){
        int i=0;
        for(;i<=countV;i++)
            if(!points.containsKey(i))break;
        points.put(i, new VertexViz(this, i));
        add(points.get(i));
        graph.addV(i);
        if(i==countV) countV++;
    }//кнопка добавить вершину

    public void addE(Graph.Edge edge){
        try{
            graph.addE(edge);
        }
        catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + exception.getLocalizedMessage(),
                    "Ошибка добавления ребра",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        edges.put(countE,edge);
        countE++;
        repaint();
    }//добавить ребро

    public void remV(int v) {
        if(v==countV-1)countV--;
        graph.removeV(new Graph.Vertex(v));
        for (int i = 0; i < countE; i++){
            if (edges.get(i).v1 == v || edges.get(i).v2 == v) edges.remove(i);
        }
        remove(points.get(v));
        points.remove(v);
        repaint();
    }//удалить вершину

    public void remE(Graph.Edge edge){
        graph.removeE(edge);
        for(int i=0;i<countE;i++) if(edges.containsKey(i) && edges.get(i).v1==edge.v1 && edges.get(i).v2==edge.v2)edges.remove(i);
    }//удалить ребро

    private void drawVertex(Graphics g, int v,Color color) {
        drawCircle(g, points.get(v).point.x,  points.get(v).point.y, VERTEX_R, color);
        drawInt(g, points.get(v).point.x, points.get(v).point.y, v);
    }

    // Пишет text в точку (x,y)
    private void drawInt(Graphics g, int x, int y, int text) {
        g.setColor(TEXT_COLOR);
        Font font = new Font("Default", Font.PLAIN, TEXT_SIZE);  //Шрифт

        g.setFont(font);

        FontMetrics fm = g.getFontMetrics(font);

        g.drawString(Integer.toString(text),
                x-fm.stringWidth(Integer.toString(text))/2,
                y+fm.getAscent()/2);
    }

    private void drawCircle(Graphics g, int cX, int cY, int rad,Color color) {
        g.fillOval(cX-rad, cY-rad, rad*2, rad*2);

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor( color );
        g.drawOval(cX-rad, cY-rad, rad*2, rad*2);
    }

    private void drawEdge(Graphics g, Graph.Edge edge, Color color){

        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);

        ((Graphics2D)g).setStroke( EDGE_LINE_THIKNESS );  // Устанавливаем толщину ребра

        g.setColor( EDGE_LINE_COLOR );
        g.drawLine(v1.x, v1.y, v2.x, v2.y);


        int x = (v1.x+v2.x)/2;
        int y = (v1.y+v2.y)/2;

        if(!graph.childrenV(edge.v2).way.containsKey(edge.v1))
            drawArrow(g,new Point((v1.x+v2.x)/2,(v1.y+v2.y)/2),v1);
        g.setColor(color);
        g.fillOval(x-14, y-14, EDJE_CIRKLE_R, EDJE_CIRKLE_R);

        ((Graphics2D)g).setStroke( EDGE_CIRKLE_LINE_THKNESS);
        g.setColor( EDGE_CIRKLE_LINE_COLOR );
        g.drawOval(x-14, y-14,  EDJE_CIRKLE_R, EDJE_CIRKLE_R);


        drawInt(g, x, y, edge.weight);
    }

    private void drawArrow(Graphics g,Point v1,Point v2){
        g.setColor(new Color(83, 83, 117));
        double dx=v1.x-v2.x;
        double dy=v1.y-v2.y;

        double t=Math.atan2(dy,dx);
        double p=Math.toRadians(40);
        int b=30;

        double x,y,r=t+p;
        for(int i=0;i<2;i++){
            x=v1.x-b*Math.cos(r);
            y=v1.y-b*Math.sin(r);
            g.drawLine(v1.x,v1.y,(int)x,(int)y);
            r=t-p;
        }
    }

    private void drawGraph(Graphics g){
        for(int i=0;i<countE;i++){
            if(edges.containsKey(i)) drawEdge(g,edges.get(i),edges.get(i).color);
        }
        for(int i=0;i<countV;i++){
            g.setColor(BASE_VERTEX_COLOR);
            if(points.containsKey(i)) drawVertex(g,i,points.get(i).color);
        }


    }
}
