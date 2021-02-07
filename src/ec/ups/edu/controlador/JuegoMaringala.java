/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class JuegoMaringala implements Runnable {

    private Descuento descuento;
    private Banca banca;
    private int numeroAzar;
    private long saldoInicial;
    private long dineroApuesto;
    Persona persona;
    String texto = "";
    JTextArea txtArea;
    ControladorPersona controladorPersona;
    JTextField txtCasa;

    public JuegoMaringala(Banca banco, Descuento descuento, JTextArea txtArea, Persona persona, JTextField txtCasa) {
        this.banca = banco;
        this.descuento = descuento;
        this.saldoInicial = persona.getCuenta();
        this.dineroApuesto = 10;
        controladorPersona = new ControladorPersona();
        this.txtArea = txtArea;
        this.txtCasa = txtCasa;
        this.persona = persona;
                
       

    }

    @Override
    public void run() {
        if (bTieneDinero()) {
            int iNumeroHilo = apostar();
            txtCasa.setText(iNumeroHilo + "");
            if (getiNumeroRuleta() == iNumeroHilo) {
                miNumeroGanado();
                System.out.println(Thread.currentThread().getName() + " ha ganado! Ahora tiene " + getiSaldoInicial());
                texto = Thread.currentThread().getName() + " ha ganado! Ahora tiene " + getiSaldoInicial()+"\n";
                txtArea.append(texto);

            } else {
                numeroPerdido();
                System.out.println(Thread.currentThread().getName() + " ha perdido! Ahora tiene " + getiSaldoInicial());
                texto = Thread.currentThread().getName() + " ha perdido! Ahora tiene " + getiSaldoInicial()+"\n";
                txtArea.append(texto);
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " se ha quedado sin dinero para apostar");
            texto = Thread.currentThread().getName() + " se ha quedado sin dinero para apostar"+"\n";
            txtArea.append(texto);

        }
    }

    private boolean bTieneDinero() {
        if (getiSaldoInicial() < 10) {
            return false;
        } else {
            return true;
        }
    }

    private int getiNumeroRuleta() {
        return this.numeroAzar;
    }

    public void setiNumeroRuleta(int iiNumeroRuleta) {
        this.numeroAzar = iiNumeroRuleta;
    }

    public int apostar() {

        System.out.println(persona);
        int numero = (int) (Math.random() * 36 + 1);
        disminuirDinero(getiDineroApuesta());
        List<Banco> lista = persona.getListaBanco();
        lista.add(new Banco(numero, (int) getiDineroApuesta(), "retiro", "juego Martingala", persona));
        long var = persona.getCuenta() - getiDineroApuesta();
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banca.ingresoDinero("juego Martingala");
        controladorPersona.update(persona);
        //banca.setDineroBanco(banca.getDineroBanco() + getiDineroApuesta());
        return numero;
    }

    public void miNumeroGanado() {

        int dineroDisponibleBanco = banca.getDineroGanado(Thread.currentThread(), (int)getiDineroApuesta());
        List<Banco> lista = persona.getListaBanco();
        lista.add(new Banco(numeroAzar, dineroDisponibleBanco, "ingreso", "juego Martingala", persona));
        long var = persona.getCuenta() + dineroDisponibleBanco;
        persona.setListaBanco(lista);
        persona.setCuenta(var);
        banca.ingresoDinero("juego Martingala");
        controladorPersona.update(persona);
        aumetnarDinero(dineroDisponibleBanco);
        banca.setDineroBanco(banca.getDineroBanco() - dineroDisponibleBanco);
        descuento.setAumento(dineroDisponibleBanco);
        setiDineroApuesta(10);
    }

    public void aumetnarDinero(long iDinero) {
        long iNuevoDinero = getiSaldoInicial() + iDinero;
        setiDineroApuesta(iNuevoDinero);
    }

    public void disminuirDinero(long iDinero) {
        long iNuevoDinero = getiSaldoInicial() - iDinero;
        setSaldoInicial(iNuevoDinero);
    }

    public long getiSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(long dinero) {
        this.saldoInicial = dinero;
    }

    private void numeroPerdido() {
        descuento.setAumento(getiDineroApuesta());
        setiDineroApuesta(getiDineroApuesta() * 2);
    }

    public long getiDineroApuesta() {
        return dineroApuesto;
    }

    public void setiDineroApuesta(long iDineroApuesta) {
        this.dineroApuesto = iDineroApuesta;
    }

}
