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
	private String typPokoju;
    private Integer liczbaOsob;
    private String kuchnia;
    private String klimatyzacja;
    private String telewizor;
	
    private LazyDataModel<Pokoje> lazyModel;
    
    public String getTypPokoju() {
        return typPokoju;
    }

    public void setTypPokoju(String typPokoju) {
        this.typPokoju = typPokoju;
    }
    
    public Integer getLiczbaOsob() {
		return liczbaOsob;
	}

	public void setLiczbaOsob(Integer liczbaOsob) {
		this.liczbaOsob = liczbaOsob;
	}
	
	public String getKuchnia() {
		return kuchnia;
	}

	public void setKuchnia(String kuchnia) {
		this.kuchnia = kuchnia;
	}

	public String getKlimatyzacja() {
		return klimatyzacja;
	}

	public void setKlimatyzacja(String klimatyzacja) {
		this.klimatyzacja = klimatyzacja;
	}

	public String getTelewizor() {
		return telewizor;
	}

	public void setTelewizor(String telewizor) {
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
		lazyModel = new LazyRoomDataModel(roomDAO,selectedSortOption, this);
	       
	}
	
	public void getFullList(){
		rooms = roomDAO.getFullList();
	}
	
	 public List<Pokoje> getRooms() {
		 return rooms;
	 }
	
	    
	 public void onConfirmSort() {
	        lazyModel = new LazyRoomDataModel(roomDAO, selectedSortOption, this);
	    }
	 
	 public void filterRooms() {
	        lazyModel = new LazyRoomDataModel(roomDAO, selectedSortOption, this);
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
	
	 public String resetFilters() {
	        this.typPokoju = null; 
	        this.liczbaOsob = null; 
	        this.kuchnia = null; 
	        this.klimatyzacja = null; 
	        this.telewizor = null; 

	        return null; 
	    }
		
	 public LazyDataModel<Pokoje> getLazyModel() { 
		 return lazyModel; 
	}
		 
}
