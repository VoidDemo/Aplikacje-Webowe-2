package hotel.jsf.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the rola database table.
 * 
 */
@Entity
@NamedQuery(name="Rola.findAll", query="SELECT r FROM Rola r")
public class Rola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_roli")
	private int idRoli;

	private String nazwa;

	//bi-directional many-to-one association to Uzytkownicy
	@OneToMany(mappedBy="rola")
	private List<Uzytkownicy> uzytkownicies;

	public Rola() {
	}

	public int getIdRoli() {
		return this.idRoli;
	}

	public void setIdRoli(int idRoli) {
		this.idRoli = idRoli;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Uzytkownicy> getUzytkownicies() {
		return this.uzytkownicies;
	}

	public void setUzytkownicies(List<Uzytkownicy> uzytkownicies) {
		this.uzytkownicies = uzytkownicies;
	}

	public Uzytkownicy addUzytkownicy(Uzytkownicy uzytkownicy) {
		getUzytkownicies().add(uzytkownicy);
		uzytkownicy.setRola(this);

		return uzytkownicy;
	}

	public Uzytkownicy removeUzytkownicy(Uzytkownicy uzytkownicy) {
		getUzytkownicies().remove(uzytkownicy);
		uzytkownicy.setRola(null);

		return uzytkownicy;
	}

}