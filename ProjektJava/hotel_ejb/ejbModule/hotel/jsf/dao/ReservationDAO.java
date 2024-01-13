package hotel.jsf.dao;

import hotel.jsf.entity.Rezerwacje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Stateless
public class ReservationDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(Rezerwacje reservation) {
        em.persist(reservation);
    }
    
    public void update(Rezerwacje reservation) {
        em.merge(reservation);
    }
    
    public void delete(Rezerwacje reservation) {
        em.remove(reservation);
    }
    
    public Rezerwacje get(Object id) {
        return em.find(Rezerwacje.class, id);
    }
    
    public List<Rezerwacje> getFullList() {
        List<Rezerwacje> list = null;

        Query query = em.createQuery("SELECT r FROM Rezerwacje r");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Rezerwacje> getList(Map<String, Object> searchParams) {
        List<Rezerwacje> list = null;

        // 1. Build query string with parameters
        String select = "SELECT r ";
        String from = "FROM Rezerwacje r ";
        String where = "";
        String orderby = "ORDER BY r.dataRozpoczecia ASC";

        // Example search for start date
        Date dataRozpoczecia = (Date) searchParams.get("dataRozpoczecia");
        if (dataRozpoczecia != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "r.dataRozpoczecia >= :dataRozpoczecia ";
        }

        // Add other parameters based on search criteria
        // e.g., "r.koszt <= :koszt" or "r.uzytkownicy.id = :userId"

        // 2. Create query object
        Query query = em.createQuery(select + from + where + orderby);

        // 3. Set configured parameters
        if (dataRozpoczecia != null) {
            query.setParameter("dataRozpoczecia", dataRozpoczecia);
        }

        // Set other parameters based on search criteria

        // 4. Execute query and retrieve list of Rezerwacje objects
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}
