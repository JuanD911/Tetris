package Tetris;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import Tetris.Persistencia.*;

public class IniciarSesion extends JFrame implements ActionListener {

    private String userName, password;

    Lectura lector;

    public IniciarSesion() {

        lector = new Lectura("userData");
        this.setSize(400, 500);
        this.setVisible(true);

        ArrayList<String> contenido = lector.readAll();

        for(String content : contenido) {
            System.out.println(content);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}