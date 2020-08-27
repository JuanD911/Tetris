package Tetris;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {

    JLabel barra_estado;

    public Tetris() {

    // Creamos una barra de estado
        barra_estado = new JLabel("---0---");
        add(barra_estado, BorderLayout.NORTH);

    // Cremos el tablero de juego
        Board tablero = new Board(this);
        add(tablero);
        tablero.start(); // este metodo arranca el juego
        setSize(200, 400);
        setTitle("Tetris");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(false);
   }

   public JLabel getBarra() {
       return barra_estado;
   }
}