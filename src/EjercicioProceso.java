import javax.imageio.IIOException;
import java.io.File;

public class EjercicioProceso {

    public void ejecutarPB (String comando) {
        ProcessBuilder pb;
        Process process;

        try {
            //pb = new ProcessBuilder(comando);
            pb=new ProcessBuilder("bash", "-c", "ps");

            File log = new File("salida.log");
            pb.redirectOutput(log);
            pb.redirectError(log);

            process = pb.start();
            int retorno = process.waitFor();

            System.out.println("La ejecución de" + pb.command() + "devuelve" + retorno);
            System.out.println("Las variables de entorno del proceso son:" + pb.environment());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ejecutarRun (String[] comando) {
        Runtime runtime;
        Process process;

        try {
            runtime = Runtime.getRuntime();
            process= runtime.exec(comando);

            synchronized (process){
                process.wait(2000);
            }

            process.destroy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
    String comando = args[0];/*"gnome-terminal";*/
    EjercicioProceso ep = new EjercicioProceso();
    ep.ejecutarPB(comando);/*para ejecutar*/

  // ep.ejecutarRun(args); /*para dest*/


    }

}
