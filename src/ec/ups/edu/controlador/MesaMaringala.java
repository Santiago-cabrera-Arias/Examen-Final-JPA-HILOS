
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
    public class MesaMaringala extends Thread {

        List<JuegoMaringala> lista;
        Random numeroAleatorio;
        JTextField txtMesa;
        boolean bandera;

        public MesaMaringala(List<JuegoMaringala> lista, JTextField txtMesa) {

            this.lista = lista;
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

                for (JuegoMaringala juegoMaringala : lista) {

                    if (juegoMaringala != null) {

                        juegoMaringala.setiNumeroRuleta(numeroAzar);

                        Thread hilo1 = new Thread(juegoMaringala);

                        hilo1.start();

                        System.out.println("--");

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
