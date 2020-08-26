package Persistencia;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Lectura {

    private String ruta;
    private String fileName;

    public Lectura(String name) {
        this.ruta = "./Files/";
        this.fileName = name;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> readAll() {
        FileReader lector;
        ArrayList<String> cadenas = new ArrayList<>();
        try {
            lector = new FileReader(ruta + fileName + ".txt");
            BufferedReader lectorRapido = new BufferedReader(lector);
            String linea = "";

            do{
               linea = lectorRapido.readLine();
               cadenas.add(linea);

            }while(linea != null);

            lectorRapido.close();

        } catch (FileNotFoundException a) {
            System.out.println(a.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        return cadenas;
        
    }
    
}