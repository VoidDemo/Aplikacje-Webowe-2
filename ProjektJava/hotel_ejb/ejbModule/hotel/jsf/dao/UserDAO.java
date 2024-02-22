package hotel.jsf.dao;

import hotel.jsf.entity.Uzytkownicy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class UserDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(Uzytkownicy user) {
		em.persist(user);
	}
	
	public void update(Uzytkownicy user) {
		em.merge(user);
	}
	
	public void delete(int id) {
		Uzytkownicy user = em.find(Uzytkownicy.class, id);
		if (user != null) {
            em.remove(user);
        } 
	}
	
	public Uzytkownicy get(int id) {
		return em.find(Uzytkownicy.class, id);
	}
	
	public List<Uzytkownicy> getFullList() {
	    List<Uzytkownicy> list = null;

	    Query query = em.createQuery("SELECT u FROM Uzytkownicy u");

	    try {
	        list = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public List<Uzytkownicy> getList(Map<String, Object> searchParams) {
	    List<Uzytkownicy> list = null;

	    // 1. Build query string with parameters
	    String select = "SELECT u ";
	    String from = "FROM Uzytkownicy u ";
	    String where = "";
	    String orderby = "ORDER BY u.idUzytkownika ASC";

	    // Example search for surname
	    String nazwisko = (String) searchParams.get("surname");
	    if (nazwisko != null) {
	        if (where.isEmpty()) {
	            where = "WHERE ";
	        } else {
	            where += "AND ";
	        }
	        where += "u.nazwisko LIKE :surname ";
	    }

	    // 2. Create query object
	    Query query = em.createQuery(select + from + where + orderby);

	    // 3. Set configured parameters
	    if (nazwisko != null) {
	        query.setParameter("surname", nazwisko + "%");
	    }

	    // Set other parameters based on search criteria

	    // 4. Execute query and retrieve list of Uzytkownicy objects
	    try {
	        list = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public Uzytkownicy getUserFromDatabase(String login, String pass) {
		
		try {
	        TypedQuery<Uzytkownicy> query = em.createQuery(
	                "SELECT u FROM Uzytkownicy u WHERE u.email = :email AND u.haslo = :haslo", Uzytkownicy.class);
	        query.setParameter("email", login);
	        query.setParameter("haslo", pass);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
	
	public List<String> getUserRolesFromDatabase(Uzytkownicy user) {
	    List<String> roles = new ArrayList<>();

	    if (user.getRola() != null) { 
	        int roleId = user.getRola().getIdRoli(); 

	        if (roleId == 1) {
	            roles.add("admin");
	        } else if (roleId == 2) {
	            roles.add("moderator");
	        } else if (roleId == 3) {
	            roles.add("uzytkownik");
	        }
	    }    
	    return roles;
	    
	}
	
	
	
}
