/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.ups.edu.controlador;

import ec.ups.edu.modelo.Banco;
import ec.ups.edu.modelo.Persona;
import java.util.List;

/**
 *
 * @author santi
 */
public class Banca extends AbstractControlador<Banco>{

    public long dineroBanco;
    ControladorPersona controladorPersona;
    Persona persona;

    public Banca() {

        setDineroBanco(50000);
        controladorPersona = new ControladorPersona();
        buscar();

    }

    public synchronized int getDineroGanado(Thread tread) {

        if (tread.getName().contains(" numeroConcreto ")) {

            if (getDineroBanco() > 360) {

                return 360;
            } else {
                return 0;
            }
        } else if (tread.getName().contains(" ParImpar ")) {

            if (getDineroBanco() > 20) {

                return 20;

            } else {

                return 0;

            }

        }
        return 0;
    }

    public int getDineroGanado(Thread currentTread, int dineroApuesto) {

        if (getDineroBanco() > dineroApuesto) {

            return dineroApuesto * 36;

        } else {

            return 0;

        }

    }

    public synchronized void retirarDinero(int valor, String modalidad) {

        List<Banco> listaB = persona.getListaBanco();
        listaB.add(new Banco(0, valor, "retiro", modalidad, persona));
        persona.setListaBanco(listaB);
        long var = persona.getCuenta() - valor;
        persona.setCuenta(var);
        controladorPersona.update(persona);

    }

    public synchronized void ingresoDinero(String modalidad) {

        System.out.println(modalidad);
        List<Banco> listaB = persona.getListaBanco();
        listaB.add(new Banco(0, 10, "ingreso", modalidad, persona));
        persona.setListaBanco(listaB);
        dineroBanco = persona.getCuenta() + 10;
        persona.setCuenta(dineroBanco);
        controladorPersona.update(persona);
        

    }
    
     public void buscar() {

        Persona persona = controladorPersona.buscar("123");
        this.persona = persona;
        dineroBanco = persona.getCuenta();

    }

    public synchronized int dineroGanado(Thread tread) {

        if (tread.getName().contains("juegoClasico")) {

            if (getDineroBanco() > 360) {

                retirarDinero(360, "juego clasico");
                return 360;

            } else {

                return 0;

            }

        }
        return 0;
    }

    public synchronized long getDineroBanco() {

        return dineroBanco;

    }

    public synchronized void setDineroBanco(long dineroBanco) {

        this.dineroBanco = dineroBanco;

    }

}
