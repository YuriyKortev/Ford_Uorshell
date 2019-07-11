package Window;

import Graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static Window.Windows_par.*;

public class GraphPlane extends JPanel {
    int countV=0;
    int countE=0;
    HashMap<Integer,VertexViz> points=new HashMap<>();
    HashMap<Integer,Graph.Edge> edges=new HashMap<>();
    Graph graph=new SimpleGraph();

    public GraphPlane(){
        setLayout(null);
        this.setBounds(250,110,600,500);
       // setPreferredSize( SIZE_OF_GRAPH_FIELD );    //Размер рамки
        setBackground( BACKGROUND );

        addV();
        addV();
        addV();
        addE(new Graph.Edge(0,1,5));
    }

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
        points.put(countV, new VertexViz(this, countV));
        add(points.get(countV));
        graph.addV(countV);
        countV++;
    }

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
    }

    private void drawVertex(Graphics g, int v) {
        drawCircle(g, points.get(v).point.x,  points.get(v).point.y, VERTEX_R);
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

    private void drawCircle(Graphics g, int cX, int cY, int rad) {
        g.fillOval(cX-rad, cY-rad, rad*2, rad*2);

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor( CIRCLE_BORDERLINE_COLOR );
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

        g.setColor(color);
        g.fillOval(x-14, y-14, EDJE_CIRKLE_R, EDJE_CIRKLE_R);

        ((Graphics2D)g).setStroke( EDGE_CIRKLE_LINE_THKNESS);
        g.setColor( EDGE_CIRKLE_LINE_COLOR );
        g.drawOval(x-14, y-14,  EDJE_CIRKLE_R, EDJE_CIRKLE_R);

        drawInt(g, x, y, edge.weight);
    }

    private void drawGraph(Graphics g){
        for(int i=0;i<edges.size();i++){
            drawEdge(g,edges.get(i),BASE_EDGE_COLOR);
        }
        for(int i=0;i<points.size();i++){
            g.setColor(BASE_VERTEX_COLOR);
            drawVertex(g,i);
        }


    }
}
