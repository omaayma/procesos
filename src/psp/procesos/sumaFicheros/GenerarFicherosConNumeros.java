package psp.procesos.sumaFicheros;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerarFicherosConNumeros {
    final static int NUM_LINEAS = 100000000;
    final static int PESO = Integer.MAX_VALUE / 100;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Se necesita el nombre del fichero");
            System.exit(1);
        }

        try (PrintWriter salida = new PrintWriter(new FileWriter(args[0]))) {
            for (int num = 1; num <= NUM_LINEAS; num++) {
                long aleatorio = (long) (Math.random() * PESO);
                salida.println(aleatorio);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
