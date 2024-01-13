package hotel.jsf.dao;

import hotel.jsf.entity.Pokoje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Stateless
public class RoomDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(Pokoje room) {
        em.persist(room);
    }
    
    public void update(Pokoje room) {
        em.merge(room);
    }
    
    public void delete(Pokoje room) {
        em.remove(room);
    }
    
    public Pokoje get(Object id) {
        return em.find(Pokoje.class, id);
    }
    
    public List<Pokoje> getFullList() {
        List<Pokoje> list = null;

        Query query = em.createQuery("SELECT p FROM Pokoje p");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Pokoje> getList(Map<String, Object> searchParams) {
        List<Pokoje> list = null;

        // 1. Build query string with parameters
        String select = "SELECT p ";
        String from = "FROM Pokoje p ";
        String where = "";
        String orderby = "ORDER BY p.idPokoju ASC";

        // Example search for room type
        String typPokoju = (String) searchParams.get("typPokoju");
        if (typPokoju != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "p.typPokoju.nazwaTypu LIKE :typPokoju ";
        }

        // Add other parameters based on search criteria
        // e.g., "p.cenaZaDobe = :cenaZaDobe" or "p.liczbaOsob = :liczbaOsob"

        // 2. Create query object
        Query query = em.createQuery(select + from + where + orderby);

        // 3. Set configured parameters
        if (typPokoju != null) {
            query.setParameter("typPokoju", typPokoju + "%");
        }

        // Set other parameters based on search criteria

        // 4. Execute query and retrieve list of Pokoje objects
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}
