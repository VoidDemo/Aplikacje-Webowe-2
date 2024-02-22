package hotel.jsf.dao;

import hotel.jsf.entity.Rezerwacje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.SortOrder;

@Stateless
public class ReservationDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void createReservation(Rezerwacje reservation) {
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
    
    
    public List<Rezerwacje> getReservationsForUser(int userId) {
    	List<Rezerwacje> list = null;
        list = em.createQuery("SELECT r FROM Rezerwacje r WHERE r.uzytkownicy.id = :userId", Rezerwacje.class)
                 .setParameter("userId", userId)
                 .getResultList();
        return list;
    }
    
    public boolean checkReservationAvailability(int roomId, Date startDate, Date endDate) {
        List<Rezerwacje> existingReservations = em.createQuery("SELECT r FROM Rezerwacje r WHERE r.pokoje.id = :roomId AND NOT (r.dataZakonczenia < :startDate OR r.dataRozpoczecia > :endDate)", Rezerwacje.class)
                .setParameter("roomId", roomId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return existingReservations.isEmpty();
    }
    
    public List<Rezerwacje> findReservationsLazy(int userId, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rezerwacje> cq = cb.createQuery(Rezerwacje.class);
        Root<Rezerwacje> reservation = cq.from(Rezerwacje.class);
        cq.where(cb.equal(reservation.get("uzytkownicy").get("id"), userId));
        if (sortField != null) {
            Path<Object> sortPath = reservation.get(sortField);
            cq.orderBy(sortOrder == SortOrder.DESCENDING ? cb.desc(sortPath) : cb.asc(sortPath));
        }
        TypedQuery<Rezerwacje> query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int getReservationsCount(int userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Rezerwacje> reservation = cq.from(Rezerwacje.class);
        cq.select(cb.count(reservation)).where(cb.equal(reservation.get("uzytkownicy").get("id"), userId));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    
}
