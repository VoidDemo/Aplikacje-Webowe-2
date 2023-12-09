package encjeJPA;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the recenzje database table.
 * 
 */
@Entity
@NamedQuery(name="Recenzje.findAll", query="SELECT r FROM Recenzje r")
public class Recenzje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_oceny")
	private int idOceny;

	private String komentarz;

	private int ocena;

	//bi-directional many-to-one association to Rezerwacje
	@ManyToOne
	private Rezerwacje rezerwacje;

	public Recenzje() {
	}

	public int getIdOceny() {
		return this.idOceny;
	}

	public void setIdOceny(int idOceny) {
		this.idOceny = idOceny;
	}

	public String getKomentarz() {
		return this.komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}

	public int getOcena() {
		return this.ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public Rezerwacje getRezerwacje() {
		return this.rezerwacje;
	}

	public void setRezerwacje(Rezerwacje rezerwacje) {
		this.rezerwacje = rezerwacje;
	}

}