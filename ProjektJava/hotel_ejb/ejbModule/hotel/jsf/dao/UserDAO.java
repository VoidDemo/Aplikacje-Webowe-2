package hotel.jsf.dao;

import hotel.jsf.entity.Uzytkownicy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
}
