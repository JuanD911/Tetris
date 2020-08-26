package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
    Figura.Tetromino[] tablero;
    private final int ancho = 10;
    private final int altura = 22;
    JButton restart = new JButton("Volver a jugar");

    int lineas_removidas = 0;
    JLabel barra;

    Figura pieza;
    
    private int curX = 0;
    private int curY = 0;

    // pieza_caer determina si la pieza ha terminado de caer
    // para asi saber si tenemos que generar una nueva
    private boolean pieza_caer = false;
    private boolean empezar = false;
    private boolean pausar = false;
    Timer timer;

    /* Constructor */
    public Board(Tetris parent) {

       // Llamamos explicitamente al metodo setFocusable() con true
       // para que desde ahora tenga el foco y el imput del teclado
       setFocusable(true);

       pieza = new Figura();
       timer = new Timer(550, this);
       timer.start();

       barra =  parent.getBarra();

       tablero = new Figura.Tetromino[ancho * altura];

       addKeyListener(new TAdapter());
       limpiartablero();
       
       this.add(restart);
       restart.setVisible(false);
       restart.setBounds(165, 183, 144, 50);
    }

    public void actionPerformed(ActionEvent e) {
        if (pieza_caer) {
            pieza_caer = false;
            nuevapieza();
        } else {
            bajar_unalinea();
        }
    }

    int squareWidth() { return (int) getSize().getWidth() / ancho; }
    int squareHeight() { return (int) getSize().getHeight() / altura; }
    Figura.Tetromino shapeAt(int x, int y) { return tablero[(y * ancho) + x]; }

    public void start(){
        if (pausar)
            return;

        empezar = true;
        pieza_caer = false;
        lineas_removidas = 0;
        limpiartablero();

        nuevapieza();
        timer.start();
    }

    private void pause(){
        if (!empezar)
            return;

        pausar = !pausar;
        if (pausar) {
            timer.stop();
            barra.setText("---Pausa---");
        } else {
            timer.start();
            barra.setText(String.valueOf("---"+lineas_removidas+"---"));
        }
        repaint();
    }

    public void paint(Graphics g){
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - altura * squareHeight();

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < ancho; ++j) {
                Figura.Tetromino figura = shapeAt(j, altura - i - 1);
                if (figura != Figura.Tetromino.NoFigura)
                    drawSquare(g, 0 + j * squareWidth(),
                               boardTop + i * squareHeight(), figura);
            }
        }
        
        if (pieza.getFigura() != Figura.Tetromino.NoFigura) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + pieza.x(i);
                int y = curY - pieza.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                           boardTop + (altura - y - 1) * squareHeight(),
                           pieza.getFigura());
            }
        }
    }

    private void bajar(){
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(pieza, curX, newY - 1))
                break;
            --newY;
        }
        caida_pieza();
    }

    private void bajar_unalinea(){
        if (!tryMove(pieza, curX, curY - 1))
            caida_pieza();
    }


    /* Este metodo limpia el array del tablero (board). Para ello, asigna a cada una de sus casillas una figura vacia (Tetrominoes NoShape). */
    private void limpiartablero(){
        for (int i = 0; i < altura * ancho; ++i)
            tablero[i] = Figura.Tetromino.NoFigura;
    }

    /* Este metodo anade la pieza que esta cayendo al array del tablero (board). Se llamara cuando la pieza ya haya terminado de caer, asi que debemos comprobar si ha hecho una linea que hay que borrar o no, llamando para ello al metodo removeFullLines(). Por ultimo, intentamos crear una nueva pieza para seguir jugando. */
    private void caida_pieza(){
        for (int i = 0; i < 4; ++i) {
            int x = curX + pieza.x(i);
            int y = curY - pieza.y(i);
            tablero[(y * ancho) + x] = pieza.getFigura();
        }

        quitar_lineas();

        if (!pieza_caer)
            nuevapieza();
    }

    private void nuevapieza(){
        pieza.setFigura_random();
        curX = ancho / 2 + 1;
        curY = altura - 1 + pieza.minY();

        if (!tryMove(pieza, curX, curY)) {
            pieza.setFigura(Figura.Tetromino.NoFigura);
            timer.stop();
            empezar = false;
            barra.setText("game over");
            restart.setVisible(true);
            restart.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    				start();
    				barra.setText(String.valueOf(lineas_removidas));
    				restart.hide();
    			}
    		});
        }
    }

    /* Este metodo intenta mover una pieza a una posicion x y que pasamos como argumentos. El metodo devuelve false si no ha sido posible moverla a esa posicion. Esto puede pasar por dos motivos:
     1. que queramos salir de los limites del tablero.
     2. que haya tocado otra pieza
     Si no ocurre ninguno de estos casos, la pieza se puede mover, por lo que actualizamos su posicion, repintamos y devolvemos verdadero. */
    private boolean tryMove(Figura newPiece, int newX, int newY){
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= ancho || y < 0 || y >= altura)
                return false;
            if (shapeAt(x, y) != Figura.Tetromino.NoFigura)
                return false;
        }

        pieza = newPiece;
        curX = newX;
        curY = newY;
        repaint();
        return true;
    }

    private void quitar_lineas(){
        int lineas_completas = 0;

        for (int i = altura - 1; i >= 0; --i) {
            boolean linea_Full = true;

            for (int j = 0; j < ancho; ++j) {
                if (shapeAt(j, i) == Figura.Tetromino.NoFigura) {
                	linea_Full = false;
                    break;
                }
            }

            if (linea_Full) {
                ++lineas_completas;
                for (int k = i; k < altura - 1; ++k) {
                    for (int j = 0; j < ancho; ++j)
                         tablero[(k * ancho) + j] = shapeAt(j, k + 1);
                }
            }
        }


        if (lineas_completas > 0) {
            lineas_removidas += lineas_completas;
            barra.setText(String.valueOf(lineas_removidas));
            pieza_caer = true;
            pieza.setFigura(Figura.Tetromino.NoFigura);
            repaint();
        }
     }

    private void drawSquare(Graphics g, int x, int y, Figura.Tetromino figura){
        Color colors[] = { new Color(103, 103, 103), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[figura.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + 1);
    }

    class TAdapter extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!empezar || pieza.getFigura() == Figura.Tetromino.NoFigura) {
                 return;
             }

             int keycode = e.getKeyCode();

             if (keycode == 'p' || keycode == 'P') {
                 pause();
                 return;
             }

             if (pausar)
                 return;

             switch (keycode) {
             case KeyEvent.VK_LEFT:
                 tryMove(pieza, curX - 1, curY);
                 break;
             case KeyEvent.VK_RIGHT:
                 tryMove(pieza, curX + 1, curY);
                 break;
             case KeyEvent.VK_DOWN:
                 tryMove(pieza.rotarderecha(), curX, curY);
                 break;
             case KeyEvent.VK_UP:
                 tryMove(pieza.rotarizquierda(), curX, curY);
                 break;
             case KeyEvent.VK_SPACE:
                 bajar();
                 break;
             case 'd':
                 bajar_unalinea();
                 break;
             case 'D':
                 bajar_unalinea();
                 break;
             }

         }
     }
}