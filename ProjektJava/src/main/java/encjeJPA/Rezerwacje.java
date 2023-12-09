package encjeJPA;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rezerwacje database table.
 * 
 */
@Entity
@NamedQuery(name="Rezerwacje.findAll", query="SELECT r FROM Rezerwacje r")
public class Rezerwacje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="data_rozpoczecia")
	private Date dataRozpoczecia;

	@Temporal(TemporalType.DATE)
	@Column(name="data_zakonczenia")
	private Date dataZakonczenia;

	private int koszt;

	//bi-directional many-to-one association to Recenzje
	@OneToMany(mappedBy="rezerwacje")
	private List<Recenzje> recenzjes;

	//bi-directional many-to-one association to Pokoje
	@ManyToOne
	private Pokoje pokoje;

	//bi-directional many-to-one association to Uzytkownicy
	@ManyToOne
	@JoinColumn(name="użytkownicy_id_użytkownika")
	private Uzytkownicy uzytkownicy;

	public Rezerwacje() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataRozpoczecia() {
		return this.dataRozpoczecia;
	}

	public void setDataRozpoczecia(Date dataRozpoczecia) {
		this.dataRozpoczecia = dataRozpoczecia;
	}

	public Date getDataZakonczenia() {
		return this.dataZakonczenia;
	}

	public void setDataZakonczenia(Date dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}

	public int getKoszt() {
		return this.koszt;
	}

	public void setKoszt(int koszt) {
		this.koszt = koszt;
	}

	public List<Recenzje> getRecenzjes() {
		return this.recenzjes;
	}

	public void setRecenzjes(List<Recenzje> recenzjes) {
		this.recenzjes = recenzjes;
	}

	public Recenzje addRecenzje(Recenzje recenzje) {
		getRecenzjes().add(recenzje);
		recenzje.setRezerwacje(this);

		return recenzje;
	}

	public Recenzje removeRecenzje(Recenzje recenzje) {
		getRecenzjes().remove(recenzje);
		recenzje.setRezerwacje(null);

		return recenzje;
	}

	public Pokoje getPokoje() {
		return this.pokoje;
	}

	public void setPokoje(Pokoje pokoje) {
		this.pokoje = pokoje;
	}

	public Uzytkownicy getUzytkownicy() {
		return this.uzytkownicy;
	}

	public void setUzytkownicy(Uzytkownicy uzytkownicy) {
		this.uzytkownicy = uzytkownicy;
	}

}