package com.jsfweb.reservation;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import hotel.jsf.dao.ReservationDAO;
import hotel.jsf.dao.RoomDAO;
import hotel.jsf.entity.Pokoje;
import hotel.jsf.entity.Rezerwacje;
import hotel.jsf.entity.Uzytkownicy;

@Named
@ViewScoped
public class ReserveRoom implements Serializable {

	private Date dateStart;
    private Date dateEnd;
    private Pokoje selectedRoom;

    @EJB
    private ReservationDAO reservationDAO;
    @EJB
    private RoomDAO roomDAO; 
    
    @Inject
    Flash flash;
    
    @PostConstruct
    public void init() {
    	selectedRoom = (Pokoje) flash.get("room");
    }
    
    public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public Pokoje getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Pokoje selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
	
    public String saveBooking() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        if (!reservationDAO.checkReservationAvailability(selectedRoom.getIdPokoju(), dateStart, dateEnd)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Pokój nie jest dostępny w wybranym terminie.", null));
            return null; // Pozostanie na tej samej stronie
        }
        long diff = dateEnd.getTime() - dateStart.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int totalCost = selectedRoom.getCenaZaDobe() * (int) days;

        Rezerwacje reservation = new Rezerwacje();
        reservation.setDataRozpoczecia(dateStart);
        reservation.setDataZakonczenia(dateEnd);
        reservation.setPokoje(selectedRoom);
        reservation.setUzytkownicy(client.getDetails());
        reservation.setKoszt(totalCost);
        reservationDAO.createReservation(reservation);

        return "/pages/index?faces-redirect=true"; // Powrót do strony głównej po zapisie
    }
	
}
