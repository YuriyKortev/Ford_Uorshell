package Window;

import Graph.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.math.*;
import java.util.Iterator;

import static Window.Windows_par.*;

public class GraphPlane extends JPanel {
    final static int INF=10_000_000;
    int countV=0;
    int countE=0;
    HashMap<Integer,VertexViz> points=new HashMap<>();
    HashMap<Integer,Graph.Edge> edges=new HashMap<>();
    Graph graph=new SimpleGraph();
    Algorithm floyd_warshell=null;

    GraphPlane() {
        setLayout(null);
        setPreferredSize(SIZE_OF_GRAPH_FIELD);    //Размер рамки
        setBackground(BACKGROUND);
/*
        try {
            FileReader reader = new FileReader("file.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
           // fromFile((JSONObject)jsonObject);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

*/


        int[][] matr={
                {0,0,1,0,0},
                {2,0,0,0,0},
                {1,2,0,0,0},
                {0,0,3,0,0},
                {1,0,5,6,0},
        };
        addFromKlav(matr);

        start_alg();

        step();
        step();
        go_to_start();

/*
        int[][]result=floyd_warshell.result();
        print_matr(list_in_matrix(false));
        print_matr(result);
*/

    }

    private void print_matr(int[][] matr){
        for(int i=0;i<matr.length;i++){
            for(int j=0;j<matr.length;j++){
                if(matr[i][j]==INF)
                    System.out.print("INF  ");
                else
                    System.out.print(matr[i][j]+"    ");
            }
            System.out.print('\n');
        }
        System.out.println();
    }

    private int[][] list_in_matrix(boolean is_alg){
        int[][] matr=new int[countV][countV];
        for(int i=0;i<countV;i++){
            for(int j=0;j<countV;j++){
                if(i!=j) matr[i][j]=is_alg ? INF : 0;
                else {
                    if(points.containsKey(i)) matr[i][j]=0;
                    else matr[i][j]=INF;
                }
            }
        }

        for(int i=0;i<countE;i++){
            if(edges.containsKey(i))matr[edges.get(i).v1][edges.get(i).v2]=edges.get(i).weight;
        }
        return matr;
    }

    public int[][] get_matr(){  //возвращает массив
        return list_in_matrix(false);
    }

    public void start_alg(){    //начать алгоритм(кнопка)
        this.floyd_warshell=new Algorithm(this,list_in_matrix(true));
    }

    public void go_to_start(){//кнопка в начало алг
        if (floyd_warshell == null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм еще не запущен",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        floyd_warshell.go_to_start();
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

    public void step_back(){
        if (floyd_warshell == null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм еще не запущен",
                    "Ошибка алгоритма",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        floyd_warshell.step_back();
    }   //шаг назад

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
        countV=0;
        countE=0;
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
        if (floyd_warshell != null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм запущен",
                    "Ошибка добавления вершины",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int i=0;
        for(;i<=countV;i++)
            if(!points.containsKey(i))break;
        points.put(i, new VertexViz(this, i));
        add(points.get(i));
        graph.addV(i);
        if(i==countV) countV++;
        repaint();
    }//кнопка добавить вершину

    public void addE(Graph.Edge edge){
        if (floyd_warshell != null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм запущен",
                    "Ошибка добавления ребра",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
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
        if (floyd_warshell != null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм запущен",
                    "Ошибка удаления вершины",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(v==countV-1)countV--;
        graph.removeV(new Graph.Vertex(v));
        for (int i = 0; i < countE; i++){
            if(edges.containsKey(i)) if (edges.get(i).v1 == v || edges.get(i).v2 == v) edges.remove(i);
        }
        remove(points.get(v));
        points.remove(v);
        repaint();
    }//удалить вершину

    public void remE(Graph.Edge edge){
        if (floyd_warshell != null) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: алгоритм запущен",
                    "Ошибка удаления ребра",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        graph.removeE(edge);
        for(int i=0;i<countE;i++) if(edges.containsKey(i) && edges.get(i).v1==edge.v1 && edges.get(i).v2==edge.v2)edges.remove(i);
        repaint();
    }//удалить ребро

    public void fromFile(JSONObject file){
        points=new HashMap<>();
        edges=new HashMap<>();
        graph=new SimpleGraph();
        countV=0;
        countE=0;
        int vertxs=(int)(long)file.get("count_vert");
        for(int i=0;i<vertxs;i++)addV();

        JSONArray edges=(JSONArray)file.get("edges");
        Iterator iter= edges.iterator();
        while(iter.hasNext()){
            JSONObject jnode=(JSONObject)iter.next();
            addE(new Graph.Edge((int)(long)jnode.get("v1"),(int)(long)jnode.get("v2"),(int)(long)jnode.get("weight")));
        }
    }

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
