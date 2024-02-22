package com.jsfweb.reservation;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import hotel.jsf.dao.ReservationDAO;
import hotel.jsf.entity.Rezerwacje;
import hotel.jsf.entity.Uzytkownicy;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpSession;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LazyReservationDataModel extends LazyDataModel<Rezerwacje> {
	private static final long serialVersionUID = 1L;

    @EJB
    private ReservationDAO reservationDAO;

    private int userId;

    public LazyReservationDataModel(ReservationDAO reservationDAO, int userId) {
        this.reservationDAO = reservationDAO;
        this.userId = userId;
    }
    
    @Override
    public List<Rezerwacje> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        // Pobieranie ID użytkownika z sesji
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        if (client != null && client.getDetails() != null) {
            userId = client.getDetails().getIdUzytkownika();
        }
        // Ładowanie rezerwacji dla użytkownika z wykorzystaniem DAO
        if (userId > 0) {
            List<Rezerwacje> reservations = reservationDAO.findReservationsLazy(userId, first, pageSize, null, null, null);
            // Ustawienie całkowitej liczby wyników
            int rowCount = reservationDAO.getReservationsCount(userId);
            this.setRowCount(rowCount);
            return reservations;
        } else {
            // Brak wyników, jeśli nie ma zalogowanego użytkownika
            this.setRowCount(0);
            return new ArrayList<>();
        }
    }
    
    @Override
    public int count(Map<String, FilterMeta> filters) {
        return reservationDAO.getReservationsCount(userId);
    }


	
}
