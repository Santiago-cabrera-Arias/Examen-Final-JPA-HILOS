/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.ups.edu.controlador;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author santi
 */
public class MesaParImpar extends Thread{
    
    List<JuegoParImpar> listaJuegoParImpar;
    Random numeroAleatorio;
    JTextField txtMesa;
    boolean bandera;
    

    public MesaParImpar(List<JuegoParImpar> lista,JTextField txtMesa) {
        
        this.listaJuegoParImpar = lista;
        this.txtMesa = txtMesa;
        //a esta clase has hilo 
        Thread hilo = new Thread(this);

        hilo.start();

        System.out.println("hola");
   
    }

      @Override
    public void run() {

        bandera = true;
        
        while (bandera) {

            int numeroAzar = ((int) (Math.random() * 36));

            txtMesa.setText(numeroAzar + "");

            for (JuegoParImpar juegoParImpar : listaJuegoParImpar) {
            
                if (juegoParImpar != null) {

                    juegoParImpar.setNumeroAzar(numeroAzar);

                    Thread hilo1 = new Thread(juegoParImpar);

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
