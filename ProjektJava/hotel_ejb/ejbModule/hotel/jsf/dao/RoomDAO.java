package hotel.jsf.dao;


import hotel.jsf.entity.Pokoje;
import hotel.jsf.entity.Rezerwacje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


import java.util.ArrayList;
import java.util.HashMap;
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
        em.remove(em.merge(room));
    }
    
    public Pokoje getRoom(Object id) {
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
    
    public List<Pokoje> getSortedRooms(String sortCriteria) {
        String queryStr = "SELECT p FROM Pokoje p";
        if ("highest".equals(sortCriteria)) {
            queryStr += " ORDER BY p.cenaZaDobe DESC";
        } else if ("lowest".equals(sortCriteria)) {
            queryStr += " ORDER BY p.cenaZaDobe ASC";
        } // Domyślne sortowanie można dodać tutaj
        
        Query query = em.createQuery(queryStr);
        return query.getResultList();
    }
    
   
    
    public List<Pokoje> getRoomsByModeratorId(int userId) {
    	List<Pokoje> list = null;
        list = em.createQuery("SELECT p FROM Pokoje p WHERE p.uzytkownicy.idUzytkownika = :userId", Pokoje.class)
                 .setParameter("userId", userId)
                 .getResultList();
        return list;
    }
    
    public List<Pokoje> findRoomsPaginated(int first, int pageSize, String sortField, String sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokoje> cq = cb.createQuery(Pokoje.class);
        Root<Pokoje> root = cq.from(Pokoje.class);

     // Dodanie sortowania, jeśli zostało określone
        if (sortField != null && sortOrder != null) {
            Order order = sortOrder.equals("ASC") ? cb.asc(root.get(sortField)) : cb.desc(root.get(sortField));
            cq.orderBy(order);
        }

        TypedQuery<Pokoje> query = em.createQuery(cq)
                                     .setFirstResult(first)
                                     .setMaxResults(pageSize);
        return query.getResultList();
    }

    public int countFilteredRooms() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Pokoje> root = query.from(Pokoje.class);
        
        query.select(cb.count(root));
        return em.createQuery(query).getSingleResult().intValue();
    }
	

}
