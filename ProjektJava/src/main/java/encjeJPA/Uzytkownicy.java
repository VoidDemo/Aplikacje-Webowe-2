package encjeJPA;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the uzytkownicy database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownicy.findAll", query="SELECT u FROM Uzytkownicy u")
public class Uzytkownicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_uzytkownika")
	private int idUzytkownika;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nadania_roli")
	private Date dataNadaniaRoli;

	@Temporal(TemporalType.DATE)
	@Column(name="data_odebrania_roli")
	private Date dataOdebraniaRoli;

	@Temporal(TemporalType.DATE)
	@Column(name="data_utworzenia")
	private Date dataUtworzenia;

	private String email;

	private String haslo;

	private String imie;

	private String nazwisko;

	private int telefon;

	//bi-directional many-to-one association to Pokoje
	@OneToMany(mappedBy="uzytkownicy")
	private List<Pokoje> pokojes;

	//bi-directional many-to-one association to Rezerwacje
	@OneToMany(mappedBy="uzytkownicy")
	private List<Rezerwacje> rezerwacjes;

	//bi-directional many-to-one association to Rola
	@ManyToOne
	private Rola rola;

	public Uzytkownicy() {
	}

	public int getIdUzytkownika() {
		return this.idUzytkownika;
	}

	public void setIdUzytkownika(int idUzytkownika) {
		this.idUzytkownika = idUzytkownika;
	}

	public Date getDataNadaniaRoli() {
		return this.dataNadaniaRoli;
	}

	public void setDataNadaniaRoli(Date dataNadaniaRoli) {
		this.dataNadaniaRoli = dataNadaniaRoli;
	}

	public Date getDataOdebraniaRoli() {
		return this.dataOdebraniaRoli;
	}

	public void setDataOdebraniaRoli(Date dataOdebraniaRoli) {
		this.dataOdebraniaRoli = dataOdebraniaRoli;
	}

	public Date getDataUtworzenia() {
		return this.dataUtworzenia;
	}

	public void setDataUtworzenia(Date dataUtworzenia) {
		this.dataUtworzenia = dataUtworzenia;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public int getTelefon() {
		return this.telefon;
	}

	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}

	public List<Pokoje> getPokojes() {
		return this.pokojes;
	}

	public void setPokojes(List<Pokoje> pokojes) {
		this.pokojes = pokojes;
	}

	public Pokoje addPokoje(Pokoje pokoje) {
		getPokojes().add(pokoje);
		pokoje.setUzytkownicy(this);

		return pokoje;
	}

	public Pokoje removePokoje(Pokoje pokoje) {
		getPokojes().remove(pokoje);
		pokoje.setUzytkownicy(null);

		return pokoje;
	}

	public List<Rezerwacje> getRezerwacjes() {
		return this.rezerwacjes;
	}

	public void setRezerwacjes(List<Rezerwacje> rezerwacjes) {
		this.rezerwacjes = rezerwacjes;
	}

	public Rezerwacje addRezerwacje(Rezerwacje rezerwacje) {
		getRezerwacjes().add(rezerwacje);
		rezerwacje.setUzytkownicy(this);

		return rezerwacje;
	}

	public Rezerwacje removeRezerwacje(Rezerwacje rezerwacje) {
		getRezerwacjes().remove(rezerwacje);
		rezerwacje.setUzytkownicy(null);

		return rezerwacje;
	}

	public Rola getRola() {
		return this.rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

}