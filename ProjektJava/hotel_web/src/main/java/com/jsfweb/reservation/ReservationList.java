package com.jsfweb.reservation;

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
import java.util.ArrayList;
import java.util.HashMap;

import hotel.jsf.dao.ReservationDAO;
import hotel.jsf.entity.Rezerwacje;
import hotel.jsf.entity.Uzytkownicy;

@Named
@ViewScoped
public class ReservationList implements Serializable {
	
	 @EJB
	 private ReservationDAO reservationDAO;

	 private List<Rezerwacje> userReservations = new ArrayList<>();
	 
	 private LazyDataModel<Rezerwacje> lazyModel;
	 
	 public List<Rezerwacje> getUserReservations() {
	        return userReservations;
	 }
    @PostConstruct
    public void init() {
        loadUserReservations();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        if (client != null && client.getDetails() != null) {
            int userId = client.getDetails().getIdUzytkownika();
            lazyModel = new LazyReservationDataModel(reservationDAO, userId);
        }
    }

    private void loadUserReservations() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        if (client != null && client.getDetails() != null) {
            int userId = client.getDetails().getIdUzytkownika();
            userReservations = reservationDAO.getReservationsForUser(userId);
        }
    }
    
    public LazyDataModel<Rezerwacje> getLazyModel() {
        return lazyModel;
    }

    
		
}
