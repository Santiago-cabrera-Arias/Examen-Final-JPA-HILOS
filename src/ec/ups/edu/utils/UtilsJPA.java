
package ec.ups.edu.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author santi
 */
public class UtilsJPA {
    
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AplicacionFinIntercicloExamenPU");
    
    public static EntityManager getEntityManager(){
        
        return emf.createEntityManager();
        
    }
    
}
