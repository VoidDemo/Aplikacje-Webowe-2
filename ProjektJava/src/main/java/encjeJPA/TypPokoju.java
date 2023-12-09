package encjeJPA;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the typ_pokoju database table.
 * 
 */
@Entity
@Table(name="typ_pokoju")
@NamedQuery(name="TypPokoju.findAll", query="SELECT t FROM TypPokoju t")
public class TypPokoju implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_typu")
	private int idTypu;

	private String nazwa;

	//bi-directional many-to-one association to Pokoje
	@OneToMany(mappedBy="typPokoju")
	private List<Pokoje> pokojes;

	public TypPokoju() {
	}

	public int getIdTypu() {
		return this.idTypu;
	}

	public void setIdTypu(int idTypu) {
		this.idTypu = idTypu;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Pokoje> getPokojes() {
		return this.pokojes;
	}

	public void setPokojes(List<Pokoje> pokojes) {
		this.pokojes = pokojes;
	}

	public Pokoje addPokoje(Pokoje pokoje) {
		getPokojes().add(pokoje);
		pokoje.setTypPokoju(this);

		return pokoje;
	}

	public Pokoje removePokoje(Pokoje pokoje) {
		getPokojes().remove(pokoje);
		pokoje.setTypPokoju(null);

		return pokoje;
	}

}