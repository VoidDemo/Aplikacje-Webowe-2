package hotel.jsf.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the pokoje database table.
 * 
 */
@Entity
@NamedQuery(name="Pokoje.findAll", query="SELECT p FROM Pokoje p")
public class Pokoje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pokoju")
	private int idPokoju;

	@Column(name="cena_za_dobe")
	private int cenaZaDobe;

	private String klimatyzacja;

	private String kuchnia;

	@Column(name="liczba_lazienek")
	private int liczbaLazienek;

	@Column(name="liczba_osob")
	private int liczbaOsob;

	private String telewizor;

	//bi-directional many-to-one association to TypPokoju
	@ManyToOne
	@JoinColumn(name="typ_pokoju_id_typu")
	private TypPokoju typPokoju;

	//bi-directional many-to-one association to Uzytkownicy
	@ManyToOne
	@JoinColumn(name="użytkownicy_id_użytkownika")
	private Uzytkownicy uzytkownicy;

	//bi-directional many-to-one association to Rezerwacje
	@OneToMany(mappedBy="pokoje")
	private List<Rezerwacje> rezerwacjes;

	public Pokoje() {
	}

	public int getIdPokoju() {
		return this.idPokoju;
	}

	public void setIdPokoju(int idPokoju) {
		this.idPokoju = idPokoju;
	}

	public int getCenaZaDobe() {
		return this.cenaZaDobe;
	}

	public void setCenaZaDobe(int cenaZaDobe) {
		this.cenaZaDobe = cenaZaDobe;
	}

	public String getKlimatyzacja() {
		return this.klimatyzacja;
	}

	public void setKlimatyzacja(String klimatyzacja) {
		this.klimatyzacja = klimatyzacja;
	}

	public String getKuchnia() {
		return this.kuchnia;
	}

	public void setKuchnia(String kuchnia) {
		this.kuchnia = kuchnia;
	}

	public int getLiczbaLazienek() {
		return this.liczbaLazienek;
	}

	public void setLiczbaLazienek(int liczbaLazienek) {
		this.liczbaLazienek = liczbaLazienek;
	}

	public int getLiczbaOsob() {
		return this.liczbaOsob;
	}

	public void setLiczbaOsob(int liczbaOsob) {
		this.liczbaOsob = liczbaOsob;
	}

	public String getTelewizor() {
		return this.telewizor;
	}

	public void setTelewizor(String telewizor) {
		this.telewizor = telewizor;
	}

	public TypPokoju getTypPokoju() {
		return this.typPokoju;
	}

	public void setTypPokoju(TypPokoju typPokoju) {
		this.typPokoju = typPokoju;
	}

	public Uzytkownicy getUzytkownicy() {
		return this.uzytkownicy;
	}

	public void setUzytkownicy(Uzytkownicy uzytkownicy) {
		this.uzytkownicy = uzytkownicy;
	}

	public List<Rezerwacje> getRezerwacjes() {
		return this.rezerwacjes;
	}

	public void setRezerwacjes(List<Rezerwacje> rezerwacjes) {
		this.rezerwacjes = rezerwacjes;
	}

	public Rezerwacje addRezerwacje(Rezerwacje rezerwacje) {
		getRezerwacjes().add(rezerwacje);
		rezerwacje.setPokoje(this);

		return rezerwacje;
	}

	public Rezerwacje removeRezerwacje(Rezerwacje rezerwacje) {
		getRezerwacjes().remove(rezerwacje);
		rezerwacje.setPokoje(null);

		return rezerwacje;
	}

}