package com.jsfweb.room;

import hotel.jsf.dao.RoomDAO;
import hotel.jsf.entity.Pokoje;
import hotel.jsf.entity.Uzytkownicy;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

import java.io.Serializable;
import java.util.HashMap;

@Named
@ViewScoped
public class RoomList implements Serializable {
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	RoomDAO roomDAO;
	
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_EDIT = "/pages/mod/roomEdit?faces-redirect=true";
	private static final String PAGE_BOOKING ="/pages/user/reservation?faces-redirect=true";
	private List<Pokoje> rooms;
	private List<Pokoje> roomsByModerator;
	private String sortCriteria;
	private Integer typPokojuId;
    private Integer liczbaOsob;
    private Boolean kuchnia;
    private Boolean klimatyzacja;
    private Boolean telewizor;
	
    private LazyDataModel<Pokoje> lazyModel;
    
    public Integer getTypPokojuId() {
        return typPokojuId;
    }

    public void setTypPokojuId(Integer typPokojuId) {
        this.typPokojuId = typPokojuId;
    }
    
    public Integer getLiczbaOsob() {
		return liczbaOsob;
	}

	public void setLiczbaOsob(Integer liczbaOsob) {
		this.liczbaOsob = liczbaOsob;
	}
	
	public Boolean getKuchnia() {
		return kuchnia;
	}

	public void setKuchnia(Boolean kuchnia) {
		this.kuchnia = kuchnia;
	}

	public Boolean getKlimatyzacja() {
		return klimatyzacja;
	}

	public void setKlimatyzacja(Boolean klimatyzacja) {
		this.klimatyzacja = klimatyzacja;
	}

	public Boolean getTelewizor() {
		return telewizor;
	}

	public void setTelewizor(Boolean telewizor) {
		this.telewizor = telewizor;
	}
    
	private String selectedSortOption;

    // Gettery i settery
    public String getSelectedSortOption() {
        return selectedSortOption;
    }

    public void setSelectedSortOption(String selectedSortOption) {
        this.selectedSortOption = selectedSortOption;
    }

	
	@PostConstruct
	public void init() {
		getFullList();
		getRoomsByModerator();
		lazyModel = new LazyRoomDataModel(roomDAO,selectedSortOption);
	       
	}
	
	public void getFullList(){
		rooms = roomDAO.getFullList();
	}
	
	 public List<Pokoje> getRooms() {
		 return rooms;
	 }
	
		/*
		 * public String getSortCriteria() { return sortCriteria; }
		 * 
		 * public void setSortCriteria(String sortCriteria) { this.sortCriteria =
		 * sortCriteria; }
		 * 
		 * public void sortRooms() { rooms = roomDAO.getSortedRooms(sortCriteria); }
		 */
	    
	 public void onConfirmSort() {
	        lazyModel = new LazyRoomDataModel(roomDAO, selectedSortOption);
	    }

	 public List<Pokoje> getRoomsByModerator() {
		    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		    RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
		    if (client != null && client.getDetails() != null) {
		        int userId = client.getDetails().getIdUzytkownika();
		        roomsByModerator = roomDAO.getRoomsByModeratorId(userId);
		    }
		    return roomsByModerator;
	}
	 public String reserveRoom(Pokoje room) {
		    flash.put("room", room); // Przekazanie ID pokoju do Flash scope
		    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
	        if (client == null || client.getDetails() == null) {
	            return "/pages/login?faces-redirect=true"; // Przekierowanie na logowanie
	        }else {
	        	 return PAGE_BOOKING;// Przekierowanie do formularza rezerwacji
	        }
		    
	}
	 
	 public String deleteRoom(Pokoje room){
			roomDAO.delete(room);
			return PAGE_STAY_AT_THE_SAME;
		}
	 
	 public String editRoom(Pokoje room) {
		    flash.put("room", room);
		    return PAGE_EDIT;
		}
	
	 public String addRoom() {
		 	Pokoje room = new Pokoje();
		    flash.put("room", room);
		    return PAGE_EDIT;
		}
		
		
	 public LazyDataModel<Pokoje> getLazyModel() { 
		 return lazyModel; 
	}
		 
}
