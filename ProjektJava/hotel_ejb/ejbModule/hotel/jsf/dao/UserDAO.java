package hotel.jsf.dao;

import hotel.jsf.entity.Uzytkownicy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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
	
	public void delete(Uzytkownicy user) {
		em.remove(user);
	}
	
	public Uzytkownicy get(Object id) {
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
	    String nazwisko = (String) searchParams.get("nazwisko");
	    if (nazwisko != null) {
	        if (where.isEmpty()) {
	            where = "WHERE ";
	        } else {
	            where += "AND ";
	        }
	        where += "u.nazwisko LIKE :nazwisko ";
	    }

	    // Add other parameters based on search criteria
	    // e.g., "u.email LIKE :email" or "u.telefon = :telefon"

	    // 2. Create query object
	    Query query = em.createQuery(select + from + where + orderby);

	    // 3. Set configured parameters
	    if (nazwisko != null) {
	        query.setParameter("nazwisko", nazwisko + "%");
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
		
		Uzytkownicy user = null;
		
		if (login.equals(user.getEmail()) && pass.equals(user.getHaslo())) {
			user.setEmail(login);
			user.setHaslo(pass);
		}

		return user;
	}
	
	public List<String> getUserRolesFromDatabase(Uzytkownicy user) {
		
		ArrayList<String> roles = new ArrayList<String>();
		
		if (user.getRola().equals(1)) {
			roles.add("admin");
		}
		if (user.getRola().equals(2)) {
			roles.add("manager");
		}
		if (user.getRola().equals(3)) {
			roles.add("admin");
		}
		
		return roles;
	}
	
}
