package ec.ups.edu.controlador;

import ec.ups.edu.modelo.Persona;
import static ec.ups.edu.utils.UtilsJPA.getEntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author santi
 */
public class ControladorPersona extends AbstractControlador<Persona> {

    public Persona buscar(String cedula) {

        EntityManager em = getEntityManager();

        try {

            String jpql = "Select p from Persona p where p.cedula='" + cedula + "'";
            Persona p = (Persona) em.createQuery(jpql).getSingleResult();

            return p;

        } catch (NoResultException e) {

            System.out.println("Error " + e);

        }

        return null;

    }

}
