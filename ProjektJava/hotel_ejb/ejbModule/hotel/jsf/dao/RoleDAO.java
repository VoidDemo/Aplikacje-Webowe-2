package hotel.jsf.dao;

import hotel.jsf.entity.Rola;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Stateless
public class RoleDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(Rola role) {
        em.persist(role);
    }
    
    public void update(Rola role) {
        em.merge(role);
    }
    
    public void delete(Rola role) {
        em.remove(role);
    }
    
    public Rola get(Object id) {
        return em.find(Rola.class, id);
    }
    
    public List<Rola> getFullList() {
        List<Rola> list = null;

        Query query = em.createQuery("SELECT r FROM Rola r");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Rola> getList(Map<String, Object> searchParams) {
        List<Rola> list = null;

        // 1. Build query string with parameters
        String select = "SELECT r ";
        String from = "FROM Rola r ";
        String where = "";
        String orderby = "ORDER BY r.idRoli ASC";

        // Example search for role name
        String nazwa = (String) searchParams.get("nazwa");
        if (nazwa != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "r.nazwa LIKE :nazwa ";
        }

        // Add other parameters based on search criteria
        // e.g., "r.uzytkownicies.size > :minUserCount"

        // 2. Create query object
        Query query = em.createQuery(select + from + where + orderby);

        // 3. Set configured parameters
        if (nazwa != null) {
            query.setParameter("nazwa", nazwa + "%");
        }

        // Set other parameters based on search criteria

        // 4. Execute query and retrieve list of Rola objects
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}
