package psp.procesos.sumaFicheros;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LanzaSumaFicheros {

    private static final String[] fileNames = {"cuenta1.txt", "cuenta2.txt", "cuenta3.txt", "cuenta4.txt", "cuenta5.txt", "cuenta6.txt",
            "cuenta7.txt", "cuenta8.txt", "cuenta9.txt", "cuenta10.txt"};
    private static final String packetPath = "psp.procesos.sumaFicheros";

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = null;
        File file;

        List<Process> listaFicherosGenerados = new ArrayList<>();
        for (String fileName : fileNames) {
            file = new File(fileName);
            if (!file.exists()) {
                pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), packetPath + ".GenerarFicherosConNumeros", fileName);
                listaFicherosGenerados.add(pb.start());
            }
        }
        while (!listaFicherosGenerados.isEmpty()) {
            Iterator<Process> iterator = listaFicherosGenerados.iterator();
            while (iterator.hasNext()) {
                Process processFichero = iterator.next();
                if (!processFichero.isAlive()) {
                    iterator.remove();
                }
            }
        }
        LanzaSumaFicheros lsf = new LanzaSumaFicheros();
        lsf.sumaSerie();
        lsf.sumaParalelo();
    }

    public void sumaParalelo() throws IOException, InterruptedException {
        long total = 0;
        long tIni = System.currentTimeMillis();

        Process[] procesos = new Process[fileNames.length];
        InputStream[] entradas = new InputStream[fileNames.length];

        ProcessBuilder pb = null;
        for (int pos = 0; pos < fileNames.length; pos++) {
            pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), packetPath + ".SumaUnFichero", fileNames[pos]);
            procesos[pos] = pb.start();
            entradas[pos] = procesos[pos].getInputStream();
        }
        boolean algunVivo = true;
        while (algunVivo) {
            algunVivo = false;

            for (int pos = 0; pos < fileNames.length; pos++) {
                if(procesos[pos] ==null) continue;
                if(entradas[pos].available()>0) {
                    String linea = new String(entradas[pos].readAllBytes()).trim();
                    long valor = Long.parseLong(linea);

                    procesos[pos]=null;
                    entradas[pos].close();
                    total +=valor;

                }
                else if(procesos[pos].isAlive()){
                    algunVivo= true;
                }

            }
            Thread.sleep(10);
        }
        long tFin = System.currentTimeMillis();

        System.out.printf("El total es:%20d\n", total);
        System.out.printf("El tiempo empleado: %d milisegundos.\n", (tFin-tIni));




    }
    public void sumaSerie() throws IOException,InterruptedException {
        long total =0;
        Process proceso;
        InputStream entrada;
        ProcessBuilder pb = null;

        long tIni = System.currentTimeMillis();
        for (String fileName : fileNames) {
                pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), packetPath + ".SumaUnFichero", fileName);
                proceso= pb.start();
                proceso.waitFor();
                entrada = proceso.getInputStream();

                String linea = new String(entrada.readAllBytes()).trim();
                long valor = Long.parseLong(linea);

                entrada.close();
                total +=valor;
        }
        long tFin = System.currentTimeMillis();

        System.out.printf("El total es:%20d\n", total);
        System.out.printf("El tiempo empleado: %d milisegundos.\n", (tFin-tIni));
    }

}