package hotel.jsf.dao;

import hotel.jsf.entity.Recenzje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Stateless
public class ReviewDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(Recenzje review) {
        em.persist(review);
    }
    
    public void update(Recenzje review) {
        em.merge(review);
    }
    
    public void delete(Recenzje review) {
        em.remove(review);
    }
    
    public Recenzje get(Object id) {
        return em.find(Recenzje.class, id);
    }
    
    public List<Recenzje> getFullList() {
        List<Recenzje> list = null;

        Query query = em.createQuery("SELECT r FROM Recenzje r");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Recenzje> getList(Map<String, Object> searchParams) {
        List<Recenzje> list = null;

        // 1. Build query string with parameters
        String select = "SELECT r ";
        String from = "FROM Recenzje r ";
        String where = "";
        String orderby = "ORDER BY r.ocena DESC";

        // Example search for specific review score
        Integer ocena = (Integer) searchParams.get("ocena");
        if (ocena != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "r.ocena = :ocena ";
        }

        // Add other parameters based on search criteria
        // e.g., "r.rezerwacje.id = :reservationId" or "r.komentarz LIKE :komentarz"

        // 2. Create query object
        Query query = em.createQuery(select + from + where + orderby);

        // 3. Set configured parameters
        if (ocena != null) {
            query.setParameter("ocena", ocena);
        }

        // Set other parameters based on search criteria

        // 4. Execute query and retrieve list of Recenzje objects
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}
