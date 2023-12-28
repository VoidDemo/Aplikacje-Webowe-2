package hotel.jsf.dao;

import hotel.jsf.entity.Pokoje;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
}
