package com.jcalvopinam.api.measures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcalvopinam.api.utils.Commons;
import com.jcalvopinam.api.utils.Valor;

/**
 * @author Juan Calvopina M. <juan.calvopina@gmail.com>
 *
 */

public class Disco {

    private static final Logger logDisco = LoggerFactory.getLogger(Disco.class);

    Commons common = new Commons();

    private Valor tiempoEscrituraDisco;
    private Valor tiempoLecturaDisco;

    public void ejecutarRendimiento() {

        String filePath = System.getProperty("user.dir") + File.separator;
        String fileName = "pruebaGuardarArchivo.txt";
        String resultEscritura = "";
        String resultLectura = "";
        String errorMessage = "";

        int mb = 5;
        char[] chars = new char[1024];
        Arrays.fill(chars, 'A');
        String longLine = new String(chars);

        PrintWriter pw = null;
        long startTimeWrite = System.nanoTime();

        try {
            File file = new File(filePath, fileName);
            // File file = File.createTempFile("pruebaGuardarArchivo", ".txt");
            file.deleteOnExit();
            logDisco.info("Directorio del archivo generado: " + file.getCanonicalPath());

            pw = new PrintWriter(new FileWriter(file));

            for (int i = 0; i < mb * 1024; i++) {
                pw.println(longLine);
            }

            pw.close();

            long stopTimeWrite = System.nanoTime() - startTimeWrite;

            logDisco.info(
                    String.format("Tardó %.3f segundos en escibir %d MB", stopTimeWrite / 1e9, file.length() >> 20));

            resultEscritura = common.formatearResultado(stopTimeWrite);

            long startTimeRead = System.nanoTime();

            BufferedReader br = new BufferedReader(new FileReader(file));

            for (String line; (line = br.readLine()) != null;) {
                logDisco.debug(line);
            }

            br.close();

            long stopTimeRead = System.nanoTime() - startTimeRead;
            logDisco.info(String.format("Tardó %.3f segundos en leer %d MB", stopTimeRead / 1e9, file.length() >> 20));
            file.delete();

            resultLectura = common.formatearResultado(stopTimeRead);

        } catch (IOException e) {
            logDisco.error("Ha ocurrido un error inesperado: " + e.getMessage());
            errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            if (pw != null)
                pw.close();
        }

        this.setTiempoEscrituraDisco(new Valor(resultEscritura, errorMessage));
        this.setTiempoLecturaDisco(new Valor(resultLectura, errorMessage));

    }

    public Valor getTiempoEscrituraDisco() {
        return tiempoEscrituraDisco;
    }

    public void setTiempoEscrituraDisco(Valor tiempoEscrituraDisco) {
        this.tiempoEscrituraDisco = tiempoEscrituraDisco;
    }

    public Valor getTiempoLecturaDisco() {
        return tiempoLecturaDisco;
    }

    public void setTiempoLecturaDisco(Valor tiempoLecturaDisco) {
        this.tiempoLecturaDisco = tiempoLecturaDisco;
    }

}
