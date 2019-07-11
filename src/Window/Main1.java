package Window;

import javax.swing.*;
import java.awt.*;

import static Window.Windows_par.*;

public class Main1 extends JFrame{

    public void main1() {new Main1(); }

    public Main1(){
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("Алгоритм Форда-Уоршела");      //Имя окна
        setSize( WINDOW_SIZE );                            //Размер окна

        setResizable(false);

       add(new GraphPlane());


        setVisible(true);
    }
}
