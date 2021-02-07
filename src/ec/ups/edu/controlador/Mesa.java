
package ec.ups.edu.controlador;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author santi
 */
public class Mesa extends Thread {

    //Sirve para resivir los hilos.
    List<JuegoClasico> listaJuegoClasico;
    Random numeroApuesta;
    JTextField txtNumeroMesa;
    boolean bandera = true;

    public Mesa(List<JuegoClasico> lista, JTextField txtNumeroMesa) {
        //resive los hilos.
        this.listaJuegoClasico = lista;
        this.txtNumeroMesa = txtNumeroMesa;
        //a esta clase has hilo viejo meco
        Thread hilo = new Thread(this);

        hilo.start();

        System.out.println("hola");

    }

    @Override
    public void run() {

        while (bandera) {

            int numeroAzar = ((int) (Math.random() * 36));

            txtNumeroMesa.setText(numeroAzar + "");

            for (JuegoClasico juegoClasico : listaJuegoClasico) {

                if (juegoClasico != null) {

                    juegoClasico.setNumeroAzar(numeroAzar);

                    Thread hilo1 = new Thread(juegoClasico);

                    hilo1.start();

                    System.out.println("esta mamada ke");

                }

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

}
