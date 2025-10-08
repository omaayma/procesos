package psp.procesos.sumaFicheros;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SumaUnFichero {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Se necesita el nombre del fichero");
            System.exit(1);
        }

        String fileName = args[0];
        long total = 0;

        try (BufferedReader entrada = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                total += Long.parseLong(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(total);
    }
}
