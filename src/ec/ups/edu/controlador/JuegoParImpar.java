package ec.ups.edu.controlador;

import ec.ups.edu.modelo.Banco;
import ec.ups.edu.modelo.Persona;
import java.util.List;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author santi
 */
public class JuegoParImpar implements Runnable {

    private Banca banca;
    private int numeroAzar;
    private long saldoInicial;
    private boolean esPar;
    String texto = "";
    JTextArea txtArea;
    Descuento descuento;
    ControladorPersona controladorPesona;
    Persona persona;
    JTextField txtCasa;

    public JuegoParImpar(Banca banca, Descuento descuento, Persona persona, JTextArea txtArea, JTextField txtCasa) {

        this.banca = banca;
        this.saldoInicial = persona.getCuenta();
        this.descuento = descuento;
        this.txtArea = txtArea;
        this.persona = persona;
        this.txtCasa = txtCasa;
        controladorPesona = new ControladorPersona();

    }

    public JuegoParImpar(int numeroAzar, int saldoInicial) {

        this.numeroAzar = numeroAzar;
        this.saldoInicial = saldoInicial;

    }

    @Override
    public void run() {

        try {

            if (tieneDinero()) {

                setEsPar(apostar());

                if ((getNumeroAzar() % 2 == 0) && isEsPar()) {

                    numeroGanado();
                    System.out.println(Thread.currentThread().getName() + " Usted gano, Ahora tiene " + getSaldoInicial());
                    texto = Thread.currentThread().getName() + " Usted gano, Ahora tiene " + getSaldoInicial() + "\n";
                    txtArea.append(texto);

                } else {

                    numeroPerdido();
                    System.out.println(Thread.currentThread().getName() + " Usted perdio, Ahora tiene " + getSaldoInicial());
                    texto = Thread.currentThread().getName() + " Usted perdio, Ahora tiene " + getSaldoInicial() + "\n";
                    txtArea.append(texto);

                }

            } else {

                System.out.println("Ya no cuenta con dinero");
                texto = "Ya no cuenta con dinero \n";
                txtArea.append(texto);

            }

        } catch (InstantiationException | IllegalAccessException e) {

            e.printStackTrace();

        }

    }

    public boolean tieneDinero() {

        if (saldoInicial > 0) {

            return true;

        } else {
            return false;

        }

    }

    public boolean apostar() throws InstantiationException, IllegalAccessException {

        boolean numeroPar = Random.class.newInstance().nextBoolean();
        disminuirDinero(10);
        List<Banco> lista = persona.getListaBanco();
        lista.add(new Banco(numeroAzar, 10, "retiro", "juego par impar", persona));
        long var = persona.getCuenta() - 10;
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banca.ingresoDinero(" juego par Impar ");
        controladorPesona.update(persona);
        //banca.setDineroBanco(banca.getDineroBanco() + 10);
        return numeroPar;

    }

    public void aumentarDinero(long dinero) {

        long nuevoValor = saldoInicial + dinero;
        setSaldoInicial(nuevoValor);

    }

    public void disminuirDinero(long dinero) {

        long nuevoValor = saldoInicial - dinero;
        setSaldoInicial(nuevoValor);

    }

    public void numeroGanado() {

        int numeroGana = banca.getDineroGanado(Thread.currentThread());
        List<Banco> lista = persona.getListaBanco();
        lista.add(new Banco(numeroAzar, numeroGana, "ingreso", "juego par Impar", persona));
        long var = persona.getCuenta() + numeroGana;
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banca.ingresoDinero("juego par Impar");
        controladorPesona.update(persona);
        //le aumentamos el dinero al hilo ganador
        aumentarDinero(numeroGana);
        //le disminuimos el dinero al banco.
        banca.setDineroBanco(banca.getDineroBanco() - numeroGana);
        descuento.setAumento(numeroGana);

    }

    private void numeroPerdido() {
        descuento.setAumento(-10);
    }//mvNunmeroPerdido()

    public Banca getBanca() {
        return banca;
    }

    public void setBanca(Banca banca) {
        this.banca = banca;
    }

    public int getNumeroAzar() {
        return numeroAzar;
    }

    public void setNumeroAzar(int numeroAzar) {
        this.numeroAzar = numeroAzar;
    }

    public long getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(long saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isEsPar() {
        return esPar;
    }

    public void setEsPar(boolean esPar) {
        this.esPar = esPar;
    }

}
