package Tetris;

import java.util.Random;
import java.lang.Math;

public class Figura {
	
	enum Tetromino { NoFigura, FiguraZ, FiguraS, FiguraLinea,
               FiguraT, FiguraCuadrado, FiguraL, FiguraLinvertida };

// Cada pieza tiene un tipo de este enumerado
    private Tetromino pieza;
// y unas coordenadas para la forma de la pieza (de 4x2)
    private int coords[][];
// CoordsTable almacena todas las coordenadas de los diferentes tipos de piezas
    private int[][][] coordsTable;


/* El constructuor inicializa:
- el array de coordenadas de la pieza a un array de 4x2
- el tipo de pieza a NoShape */
    public Figura() {
    	coords = new int[4][2];
        setFigura(Tetromino.NoFigura);
    }

    public void setFigura(Tetromino shape) {

/* Cada fila de coordsTable representa un tipo de pieza y estan en el orden en el que aparecen en el enumerado. Por ejemplo, los numeros { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 }, representan la pieza S-shape rotada.*/
        coordsTable = new int[][][] {
        { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
        { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
        { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
        { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
        { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
        { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
        { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

/* Aqui es donde se copia la fila de la pieza que nos interesa de la coordsTable a las coordenadas de la pieza (coords). Para saber que fila de la tabla coger debemos saber que posicion ocupa esa pieza (shape) dentro del enumerado. Esto lo hacemos utilizando el metodo ordinal() que nos devuelve un entero con la posicion que ocupa. */
        for (int i = 0; i < 4 ; i++) {
        	for (int j = 0; j < 2; ++j) {
                coords[i][j] = coordsTable[shape.ordinal()][i][j];
        	}
        }
		pieza = shape;
    }

    private void setX(int index, int x) { coords[index][0] = x; }
    private void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetromino getFigura()  { return pieza; }


/* Metodo que genera una figura aleatoriamente */
    public void setRandomShape(){
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetromino[] values = Tetromino.values();
        setFigura(values[x]);
    }


/* Mira las coordenadas de la pieza y buscan la posicion minima en x e y */
    public int minX(){
        int m = coords[0][0];
        for (int i=0; i < 4; i++) {
                m = Math.min(m, coords[i][0]);
        }
        return m;
    }

    public int minY(){
        int m = coords[0][1];
        for (int i=0; i < 4; i++) {
                m = Math.min(m, coords[i][1]);
        }
        return m;
    }

/* Rota la pieza a la izquierda o derecha y devuelve la nueva forma.
        - Primero comprueba que la forma no sea el cuadrado, ya que si lo es, como no hay que rotarlo lo devuelve directamente.
        - En caso contrario crea una nueva forma (Shape) donde va guardando la forma rotada.
        - Para ello, recorre los 4 elementos de la forma, del tipo {int, int} y va asignandole los nuevos valores.
        - Utiliza los metodos get y(int index) x(int index) y los metodos set setX(int index, int x) y setY(int index, int y) */
    public Figura rotarizquierda(){
        if (pieza == Tetromino.FiguraCuadrado)
                return this;

        Figura resultado = new Figura();
        resultado.pieza = pieza;

        for (int i = 0; i < 4; ++i) {
                resultado.setX(i, y(i));
                resultado.setY(i, -x(i));
        }
        return resultado;
    }

    public Figura rotarderecha(){
        if (pieza == Tetromino.FiguraCuadrado)
                return this;

        Figura resultado = new Figura();
        resultado.pieza = pieza;

        for (int i = 0; i < 4; ++i) {
                resultado.setX(i, -y(i));
                resultado.setY(i, x(i));
        }
        return resultado;
    }
}
