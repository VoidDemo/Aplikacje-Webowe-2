package hotel.jsf.dao;


import hotel.jsf.entity.Pokoje;
import hotel.jsf.entity.Rezerwacje;
import hotel.jsf.entity.TypPokoju;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
    
    
    public List<Pokoje> getRoomsByModeratorId(int userId) {
    	List<Pokoje> list = null;
        list = em.createQuery("SELECT p FROM Pokoje p WHERE p.uzytkownicy.idUzytkownika = :userId", Pokoje.class)
                 .setParameter("userId", userId)
                 .getResultList();
        return list;
    }
    
    public List<Pokoje> findRoomsPaginated(int first, int pageSize, String sortOrder, String typPokoju, Integer liczbaOsob, String kuchnia, String klimatyzacja, String telewizor) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokoje> cq = cb.createQuery(Pokoje.class);
        Root<Pokoje> root = cq.from(Pokoje.class);
        
        // Lista na predykaty filtrujące
        List<Predicate> predicates = new ArrayList<>();

        // Dodawanie warunków filtrowania, jeśli są dostępne
        if (typPokoju != null) {
            Join<Pokoje, TypPokoju> typPokojuJoin = root.join("typPokoju");
            predicates.add(cb.equal(typPokojuJoin.get("nazwa"), typPokoju));
        }
        if (liczbaOsob != null) {
        	if(liczbaOsob==1) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 1));
        	}else if(liczbaOsob==2) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 2));
        	}else if(liczbaOsob==3) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 3));
        	}else if(liczbaOsob==4) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 4));
        	}
        }
        if (kuchnia != null) {
            predicates.add(cb.equal(root.get("kuchnia"), kuchnia ));
        }
        if (klimatyzacja != null) {
            predicates.add(cb.equal(root.get("klimatyzacja"), klimatyzacja ));
        }
        if (telewizor != null) {
            predicates.add(cb.equal(root.get("telewizor"), telewizor ));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Sortowanie po cenie za dobę
        if (sortOrder != null) {
            Path<Integer> cenaZaDobe = root.get("cenaZaDobe");
            Order order = sortOrder.equals("ASC") ? cb.asc(cenaZaDobe) : cb.desc(cenaZaDobe);
            cq.orderBy(order);
        }

        TypedQuery<Pokoje> query = em.createQuery(cq)
                                     .setFirstResult(first)
                                     .setMaxResults(pageSize);
        return query.getResultList();
    }

    public int countFilteredRooms(String typPokoju, Integer liczbaOsob, String kuchnia, String klimatyzacja, String telewizor) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Pokoje> root = query.from(Pokoje.class);
        
     // Lista na predykaty filtrujące
        List<Predicate> predicates = new ArrayList<>();

        // Dodawanie warunków filtrowania, jeśli są dostępne
        if (typPokoju != null) {
            Join<Pokoje, TypPokoju> typPokojuJoin = root.join("typPokoju");
            predicates.add(cb.equal(typPokojuJoin.get("nazwa"), typPokoju));
        }
        if (liczbaOsob != null) {
        	if(liczbaOsob==1) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 1));
        	}else if(liczbaOsob==2) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 2));
        	}else if(liczbaOsob==3) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 3));
        	}else if(liczbaOsob==4) {
        		predicates.add(cb.equal(root.get("liczbaOsob"), 4));
        	}
            
        }
        if (kuchnia != null) {
            predicates.add(cb.equal(root.get("kuchnia"), kuchnia ));
        }
        if (klimatyzacja != null) {
            predicates.add(cb.equal(root.get("klimatyzacja"), klimatyzacja ));
        }
        if (telewizor != null) {
            predicates.add(cb.equal(root.get("telewizor"), telewizor ));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        
        query.select(cb.count(root));
        return em.createQuery(query).getSingleResult().intValue();
    }
	

}
