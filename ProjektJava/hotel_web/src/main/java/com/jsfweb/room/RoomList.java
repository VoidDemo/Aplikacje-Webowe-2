package com.jsfweb.room;

import hotel.jsf.dao.RoomDAO;
import hotel.jsf.entity.Pokoje;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;



import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RoomList {
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	RoomDAO roomnDAO;
	
	public List<Pokoje> getFullList(){
		return roomnDAO.getFullList();
	}
	
}
