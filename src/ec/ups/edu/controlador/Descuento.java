
package ec.ups.edu.controlador;

/**
 *
 * @author santi
 */
public class Descuento {

    private int dinero;

    public Descuento() {
        setDinero(0);
    }

    public synchronized void setAumento(long dinero) {
        this.dinero += dinero;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero += dinero;
    }

}
