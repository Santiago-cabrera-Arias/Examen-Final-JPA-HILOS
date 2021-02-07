package ec.ups.edu.controlador;

import ec.ups.edu.modelo.Banco;
import ec.ups.edu.modelo.Persona;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author santi
 */
public class JuegoClasico implements Runnable {

    private Banca banco;
    private int numeroAzar;
    private long saldoInicial;
    String texto = "";
    JTextArea txtArea;
    Descuento descuento;
    ControladorPersona controladorPesona;
    Persona persona;
    JTextField txtCasa;
    
    

    public JuegoClasico(Banca banca, Descuento descuento, JTextArea txtArea,Persona persona,JTextField txtCasa) {
        this.banco = banca;
        this.saldoInicial = persona.getCuenta();
        this.descuento = descuento;
        this.txtArea = txtArea;
        this.persona = persona;
        this.txtCasa = txtCasa;
        controladorPesona = new ControladorPersona();
        

    }

    public JuegoClasico(JTextArea txtArea) {
        this.txtArea = txtArea;
    }

    public boolean tieneDinero() {

        if (getSaldoInicial() > 0) {

            return true;

        } else {

            return false;

        }

    }

    public int apostar() {

        int numero = (int) (Math.random() * 36 + 1);
        quitarDinero(numero);
        List<Banco> lista = persona.getListaBanco(); 
        lista.add(new Banco(numero, 10, "retiro", "juego clasico", persona));
        long var = persona.getCuenta()-10;
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banco.ingresoDinero(" juego clasico ");
        controladorPesona.update(persona);
        return numero;

    }

    public void numeroGanado() {

        //le quitamos el dinero al hilo
        int dineroDisponibleBanco = banco.getDineroGanado(Thread.currentThread());
        List<Banco> lista = persona.getListaBanco();
        lista.add(new Banco(numeroAzar, dineroDisponibleBanco,"ingreso" , "juego clasico", persona));
        long var = persona.getCuenta() + dineroDisponibleBanco;
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banco.ingresoDinero("juego clasico");
        controladorPesona.update(persona);
        //le aumentamos el dinero al hilo ganador
        aumentarDinero(dineroDisponibleBanco);
        //le disminuimos el dinero al banco.
        banco.setDineroBanco(banco.getDineroBanco() - dineroDisponibleBanco);
        descuento.setAumento(dineroDisponibleBanco);

    }

    public void numeroPerdido() {

        descuento.setAumento(-10);

    }

    public void aumentoDinero(long dinero) {

        long nuevoDinero = getSaldoInicial() + dinero;
        setSaldoInicial(nuevoDinero);

    }

    public void quitarDinero(long dinero) {

        long dineroPe = getSaldoInicial() - dinero;
        setSaldoInicial(dineroPe);

    }

    public void aumentarDinero(long dinero) {

        long dineroAu = getSaldoInicial() - dinero;
        setSaldoInicial(dineroAu);

    }

    @Override
    public void run() {

        if (tieneDinero()) {

            int numeroAleatorio = apostar();
            
            txtCasa.setText(numeroAleatorio+"");

            if (getNumeroAzar() == numeroAleatorio) {

                numeroGanado();
                System.out.println(Thread.currentThread().getName() + " usted gano, saldo actual " + getSaldoInicial());
                texto = Thread.currentThread().getName() + " usted gano, saldo actual " + getSaldoInicial() + "\n";
                txtArea.append(texto);

            } else {

                numeroPerdido();
                texto = Thread.currentThread().getName() + " usted perdio, saldo actual " + getSaldoInicial() + "\n";
                txtArea.append(texto);
                System.out.println(Thread.currentThread().getName() + " usted perdio, saldo actual " + getSaldoInicial());

            }

        } else {

            System.out.println("No cuenta con dinero suficiente");
            texto = "No cuenta con dinero suficiente \n";
            txtArea.append(texto);
            
        }

    }

    public Banca getBanco() {
        return banco;
    }

    public void setBanco(Banca banco) {
        this.banco = banco;
    }

    public int getNumeroAzar() {
        return this.numeroAzar;
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

}
