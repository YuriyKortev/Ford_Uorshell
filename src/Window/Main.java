package Window;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static Window.Windows_par.*;

public class Main extends JFrame{

    public static void main(String[] args){new Main(); }

    Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("Алгоритм Форда-Уоршела");      //Имя окна
        setSize( WINDOW_SIZE );                            //Размер окна

        setResizable(false);

        add(new GraphPlane());


        setVisible(true);
    }
}
