package Tetris;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Tetris.Persistencia.*;

public class Registrarse extends JFrame implements ActionListener {

    private String userName, password;

    private JButton registrarse;
    private JTextField nombre, contrasenna;

    private Escritura escritor;

    private Tetris tetris = new Tetris();

    public Registrarse() {

        escritor = new Escritura("userData");

        this.setSize(450, 325);
        this.setTitle("Registro");

        registrarse = new JButton("Registrarse");
        nombre = new JTextField("Nombre");
        contrasenna = new JTextField("Contraseña");

        registrarse.setBounds(215, 300, 100, 25);
        nombre.setBounds(215, 150, 100, 25);
        contrasenna.setBounds(215, 250, 100, 25);

        registrarse.addActionListener(this);

        this.add(registrarse);
        this.add(nombre);
        this.add(contrasenna);


        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrarse) {
            this.userName = nombre.getText();
            this.password = contrasenna.getText();

            escritor.addContent("Juan David es una Perra");
            escritor.addContent("Cierto que si!!!!");

            this.tetris.setVisible(true);
        }
    }
    
}