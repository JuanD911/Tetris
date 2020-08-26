package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    // El tablero contiene un conjunto de formas
    Figura.Tetromino[] tablero;
    // Tamano del tablero
    final int ancho = 10;
    final int altura = 22;

    // lineas_completas mantiene el contador de las lineas que hemos limpiado
    int lineas_completas = 0;
    JLabel barra;

    // pieza actual
    Figura pieza;
    // curX y curY determinan la posicion actual de la pieza que esta cayendo
    int curX = 0;
    int curY = 0;

    // pieza_caer determina si la pieza ha terminado de caer
    // para asi saber si tenemos que generar una nueva
    boolean pieza_caer = false;
    boolean empezar = false;
    boolean pausar = false;
    Timer timer;

    /* Constructor */
    public Board(Tetris parent) {

       // Llamamos explicitamente al metodo setFocusable() con true
       // para que desde ahora tenga el foco y el imput del teclado
       setFocusable(true);

       // Generamos una nueva pieza
       pieza = new Figura();

       // El timer lanza eventos cada cierto tiempo indicado por el delay.
       // En nuestro caso el timer llama a actionPerformed() cada 400 ms
       timer = new Timer(500, this);
       timer.start();

       // Asignamos la barra de estado
       barra =  parent.getBarra();

       // Iniciamos el tablero con piezas vacias hasta el ancho y alto indicados
       tablero = new Figura.Tetromino[ancho * altura];

       addKeyListener(new TAdapter());
       limpiartablero();
    }

    /* El metodo actionPerformed() comprueba si la caida de la pieza ha finalizado. En ese caso genera una nueva pieza con newPieze(). En caso contrario mueve una linea abajo con oneLineDown() */
    public void actionPerformed(ActionEvent e) {
        if (pieza_caer) {
            pieza_caer = false;
            nuevapieza();
        } else {
            oneLineDown();
        }
    }

    // Metodos auxiliares
    int squareWidth() { return (int) getSize().getWidth() / ancho; }
    int squareHeight() { return (int) getSize().getHeight() / altura; }
    Figura.Tetromino shapeAt(int x, int y) { return tablero[(y * ancho) + x]; }


    // Inicializa una partida nueva
    public void start(){
        if (pausar)
            return;

        empezar = true;
        pieza_caer = false;
        lineas_completas = 0;
        limpiartablero();

        nuevapieza();
        timer.start();
    }

    // Pausa o despausa la partida
    private void pause(){
        if (!empezar)
            return;

        pausar = !pausar;
        if (pausar) {
            timer.stop();
            barra.setText("Pausa");
        } else {
            timer.start();
            barra.setText(String.valueOf(lineas_completas));
        }
        repaint();
    }

    /* Este metodo dibuja todos los objetos en el tablero.
     * El proceso tiene 2 pasos:
     * 1. Se pintan todas las figuras que ya se habian colocado en el tablero.
     * 2. Pintamos la figura que esta cayendo actualmente. */
    public void paint(Graphics g){
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - altura * squareHeight();

        /* 1. Se pintan todas las figuras que ya han tocado la parte baja del tablero. Todos los cuadrados estan guardados en el array de tablero y podemos acceder a el usando el metodo shapeAt() */
        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < ancho; ++j) {
                Figura.Tetromino figura = shapeAt(j, altura - i - 1);
                if (figura != Figura.Tetromino.NoFigura)
                    drawSquare(g, 0 + j * squareWidth(),
                               boardTop + i * squareHeight(), figura);
            }
        }

        /* 2. Pintamos la figura que esta cayendo actualmente. */
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

    /* Metodo que hace caer la pieza actual de forma rapida si pulsamos la tecla espacio. El proceso que realiza consiste en bajar la pieza una linea hasta que ya no pueda bajar mas, sea porque haya llegado al final del tablero o porque haya chocado con otra pieza.
	Para implementar el metodo se utilizan dos metodos auxiliares:
    - tryMove para saber si se puede mover la pieza a ese nuevo lugar
    - y pieceDropped que una vez que la pieza tiene ya su posicion definitiva la guarda en el array del tablero */
    private void bajar(){
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(pieza, curX, newY - 1))
                break;
            --newY;
        }
        caida_pieza();
    }

    /* Este metodo mueve la pieza una linea abajo si es posible.
     Para implementar el metodo se utilizan dos metodos auxiliares:
     - tryMove para saber si se puede mover la pieza a ese nuevo lugar
     - y pieceDropped que una vez que la pieza tiene ya su posicion definitiva la guarda en el array del tablero */
    private void oneLineDown(){
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

    /* Este metodo crea una nueva pieza que cae y la asigna a curPiece. Lo asigna con una forma aleatoria usando el metodo setRandomShape. Entonces inicializamos su posicion actual curX y curY a la parte superior. Posteriormente vemos si la pieza se puede mover a esa posicion inicial que hemos asignado, utilizando el metodo tryMove.
     Si no se puede mover es porque ya esta todo el tablero lleno y hemos perdido y por lo tanto, debemos hacer varias cosas:
     - asignar a la p ieza actual curPiece la figura NoShape
     - parar el timer
     - cambiar el booleano de comienzo isStarted a falso
     - asignar a la barra de estado statusbar el texto "game over" */
    private void nuevapieza(){
        pieza.setRandomShape();
        curX = ancho / 2 + 1;
        curY = altura - 1 + pieza.minY();

        if (!tryMove(pieza, curX, curY)) {
            pieza.setFigura(Figura.Tetromino.NoFigura);
            timer.stop();
            empezar = false;
            barra.setText("game over");
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

    /* Este metodo se lanza despues de colocar una pieza y su objetivo es eliminar todas las lineas completas (filas) que pueda haber en el tablero.
     Las lineas se borran cuando estan todas rellena de piezas y no hay huecos, y por cada linea que borramos aumentamos los puntos en el juego.
     Para implementar este metodo, primero tenemos que mirar cuantas lineas (filas) completas tenemos actualmente (puede haber varias). Recorremos todo el tablero linea a linea desde abajo (BoardHeight) hacia arriba (0).
     Por cada linea hacemos lo siguiente:
     - Miramos toda la fila preguntando para cada casilla si alli hay o una pieza o un hueco. Para eso usamos shapeAt y los tipos de figuras, como NoShape.
     - Si en toda esa fila hay figuras distintas a NoShape entonces tendremos una linea completa que deberemos eliminar.
     El proceso para eliminar la linea es el siguiente:
     - Recorremos el tablero linea a linea, desde la linea (fila) que tenemos que borrar hacia arriba (BoardHeight).
     - Por cada linea tenemos que recorrerla completamente de izquierda a derecha y bajar todas sus piezas una casilla, asignando a la casilla correspondiente del array board lo que hay justo encima, con shapeAt.
     Hay que recordar, que en esta implementacion de Tetris no existe gravedad entre filas. Es decir, la fila superior no cae rellenando los huecos que pueda haber en filas inferiores, sino que el efecto es como si se moviera la fila completa tal cual esta, manteniendo sus piezas y tambien sus huecos exactamente como estaban.
     Despues de realizar este proceso de borrado de lineas, si hemos borrado alguna:
     - se actualiza la puntuacion
     - se marca el booleano de finalizacion de la caida de pieza a true
     - se asigna la pieza actual a NoShape
     - se repinta todo
     */
    private void quitar_lineas(){
        int numFullLines = 0;

        for (int i = altura - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < ancho; ++j) {
                if (shapeAt(j, i) == Figura.Tetromino.NoFigura) {
                	lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < altura - 1; ++k) {
                    for (int j = 0; j < ancho; ++j)
                         tablero[(k * ancho) + j] = shapeAt(j, k + 1);
                }
            }
        }


        if (numFullLines > 0) {
            lineas_completas += numFullLines;
            barra.setText(String.valueOf(lineas_completas));
            pieza_caer = true;
            pieza.setFigura(Figura.Tetromino.NoFigura);
            repaint();
        }
     }

     /* Este metodo dibuja cada uno de los 4 cuadrados que componen una pieza.
      Asigna para cada tipo de pieza un color distinto.
      Y anade a los bordes izquierdo y superior de cada cuadrado un poco de brillo, y al derecho e inferior un poco de sombra, para dar un efecto 3d. */
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

    /* Implementacion de los controles por teclado.*/
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
                 oneLineDown();
                 break;
             case 'D':
                 oneLineDown();
                 break;
             }

         }
     }
}