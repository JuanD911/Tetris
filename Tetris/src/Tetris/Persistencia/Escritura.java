package Tetris.Persistencia;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Escritura {

    private String ruta;
    private String fileName;

    public Escritura(String name) {
        this.ruta = "./Files/";
        this.fileName = name;
        this.createDir(ruta);
    }

    public void createDir(String name) {
        File directorio = new File(name);
        if (!directorio.exists()) {
            if (directorio.mkdir()) {

            } else {
                System.err.println("Error al crear la capeta");
            }
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void initFile() {
        File archivo = new File(ruta + fileName + ".txt");

        if (archivo.exists()) {
            archivo.delete();
        }

    }

    public void addContent(String texto) {

        try {
            FileWriter f = new FileWriter(ruta + fileName + ".txt", true);
            BufferedWriter bw = new BufferedWriter(f);
            bw.write(texto);
            bw.write('\n');
            bw.close();

        } catch (FileNotFoundException a) {
            System.out.println(a.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
