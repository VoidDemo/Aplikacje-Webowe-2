package com.jsfweb.room;

import hotel.jsf.dao.RoomDAO;
import hotel.jsf.dao.RoomTypeDAO;
import hotel.jsf.entity.Pokoje;
import hotel.jsf.entity.Uzytkownicy;
import hotel.jsf.entity.TypPokoju;
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
import java.io.Serializable;
import java.util.HashMap;

@Named
@ViewScoped
public class RoomEdit implements Serializable{
	private static final long serialVersionUID = 1L;

	private Pokoje room;
	private static final String PAGE_MOD = "/pages/mod/modPage?faces-redirect=true";
	private int roomTypeId;
	private List<TypPokoju> roomtypes;
    
    @EJB
    private RoomDAO roomDAO;
    @EJB
    private RoomTypeDAO roomtypeDAO;

    @Inject
    private Flash flash;

    @PostConstruct
    public void init() {
        room = (Pokoje) flash.get("room");
        roomtypes = roomtypeDAO.getFullList();
        if (room == null) {
            room = new Pokoje();
        }
        
    }
    
 // Getters and Setters
    public Pokoje getRoom() {
        return room;
    }

    public void setRoom(Pokoje room) {
        this.room = room;
    }
    
    public List<TypPokoju> getRoomTypes(){
    	return roomtypeDAO.getFullList();
    }
    
    public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
    
    
    public String save() {
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> remoteClient = RemoteClient.load(session);
        Uzytkownicy currentUser = remoteClient.getDetails();
        
        // Ustawienie użytkownika dla pokoju
        room.setUzytkownicy(currentUser);
        // Ustawienie typu pokoju dla pokoju
    	TypPokoju newtype = roomtypeDAO.get(roomTypeId);
        room.setTypPokoju(newtype);
        
        if (room.getIdPokoju() == 0) {
            roomDAO.create(room);
        } else {
            roomDAO.update(room);
        }
        return PAGE_MOD; 
    }

	

    
}
