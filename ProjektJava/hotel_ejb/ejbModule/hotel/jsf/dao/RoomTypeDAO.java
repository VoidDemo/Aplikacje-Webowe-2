package hotel.jsf.dao;

import hotel.jsf.entity.TypPokoju;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Stateless
public class RoomTypeDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(TypPokoju roomType) {
        em.persist(roomType);
    }
    
    public void update(TypPokoju roomType) {
        em.merge(roomType);
    }
    
    public void delete(TypPokoju roomType) {
        em.remove(roomType);
    }
    
    public TypPokoju get(Object id) {
        return em.find(TypPokoju.class, id);
    }
    
    public List<TypPokoju> getFullList() {
        List<TypPokoju> list = null;

        Query query = em.createQuery("SELECT t FROM TypPokoju t");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<TypPokoju> getList(Map<String, Object> searchParams) {
        List<TypPokoju> list = null;

        // 1. Build query string with parameters
        String select = "SELECT t ";
        String from = "FROM TypPokoju t ";
        String where = "";
        String orderby = "ORDER BY t.idTypu ASC";

        // Example search for room type name
        String nazwa = (String) searchParams.get("nazwa");
        if (nazwa != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "t.nazwa LIKE :nazwa ";
        }

        // 2. Create query object
        Query query = em.createQuery(select + from + where + orderby);

        // 3. Set configured parameters
        if (nazwa != null) {
            query.setParameter("nazwa", nazwa + "%");
        }

        // 4. Execute query and retrieve list of TypPokoju objects
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}
