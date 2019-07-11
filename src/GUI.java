import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Window.*;
import Graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;

import static Window.Windows_par.*;
import static Window.Windows_par.BACKGROUND;
import static Window.Windows_par.SIZE_OF_GRAPH_FIELD;


public class GUI extends JFrame {
    private JButton in_from_file = new JButton("Ввод с файла");
    private JButton in_from_key = new JButton("Ввод с клавиатуры");
    private JButton in_from_graphic = new JButton("Ввод графически");
    private JButton start_algorithm = new JButton("Запустить алгоритм");
    private JButton OK = new JButton("OK");
    private GraphPlane graph= new GraphPlane();

    public GUI () {
        super("MainWindow");
       this.setBounds(250, 150, 1000, 500); //само окно
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрытие окна

        setPreferredSize( SIZE_OF_GRAPH_FIELD );    //Размер рамки
        setBackground( BACKGROUND );
        JPanel panel=new JPanel();
        panel.setLayout(null);

        in_from_file.addActionListener(new InFromFileEvent ());//вызов чего то при нажатии
        panel.add(in_from_file);
        in_from_file.setBounds(100,10,200,100);

        in_from_key.addActionListener(new InFromKeyEvent());
        in_from_key.setBounds(300,10,200,100);
        panel.add(in_from_key);

        in_from_graphic.addActionListener(new InFromGraphicEvent());
        in_from_graphic.setBounds(500,10,200,100);
        panel.add(in_from_graphic);

        start_algorithm.addActionListener(new Algorithm());
        start_algorithm.setBounds(700,10,200,100);
        panel.add(start_algorithm);
        graph.setBackground(GRAPH_FIELD_BACKGROUND);
        //graph.setBounds(250,110,500,600);
        panel.add(graph);
        getContentPane().add(panel); //форма
       // setPreferredSize(new Dimension(285,145));

    }

    class InFromFileEvent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = "Здесь будет ввод с файла.";
            JOptionPane.showMessageDialog(null, s, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class InFromKeyEvent implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String s = "Здесь будет ввод с клавиатуры.";
            JOptionPane.showMessageDialog(null, s, "Output", JOptionPane.PLAIN_MESSAGE);        }
    }

    class InFromGraphicEvent implements ActionListener {
        public void actionPerformed(ActionEvent e){
            add(new Main1());
        }
    }

    class Algorithm implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String s = "Тут откроется окно с работой алгоритма";
            JOptionPane.showMessageDialog(null, s, "Output", JOptionPane.PLAIN_MESSAGE);        }
    }
}
