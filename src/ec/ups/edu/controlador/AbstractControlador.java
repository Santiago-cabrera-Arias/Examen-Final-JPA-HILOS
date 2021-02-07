package ec.ups.edu.controlador;

import ec.ups.edu.utils.UtilsJPA;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author santi
 */
public abstract class AbstractControlador<T> {

    private List<T> lista;
    private Class<T> clase;
    private EntityManager em;

    public AbstractControlador(EntityManager em) {
        lista = new ArrayList<>();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clase = (Class) pt.getActualTypeArguments()[0];
        this.em = em;
    }

    public AbstractControlador() {
        lista = new ArrayList<>();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clase = (Class) pt.getActualTypeArguments()[0];
        this.em = UtilsJPA.getEntityManager();
    }

    public boolean create(T objeto) {
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        lista.add(objeto);
        return true;
    }

    public T read(Object id) {
        return (T) em.find(clase, id);
    }

    public boolean update(T objeto) {
        em.getTransaction().begin();
        objeto = em.merge(objeto);
        em.getTransaction().commit();
        lista = findAll();
        return true;
    }

    public boolean delete(T objeto) {
        em.getTransaction().begin();
        em.remove(em.merge(objeto));
        em.getTransaction().commit();
        lista.remove(objeto);
        return true;
    }

    public List<T> findAll() {
        return em.createQuery("Select t from " + clase.getSimpleName() + " t").getResultList();
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    public Class<T> getClase() {
        return clase;
    }

    public void setClase(Class<T> clase) {
        this.clase = clase;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
