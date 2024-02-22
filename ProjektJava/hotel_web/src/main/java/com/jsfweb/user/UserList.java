package com.jsfweb.user;

import hotel.jsf.dao.UserDAO;
import hotel.jsf.entity.Uzytkownicy;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;


@Named
@ViewScoped
public class UserList  implements Serializable {
	private static final String PAGE_USER_EDIT = "/pages/admin/userEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String surname;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
		
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	

	public List<Uzytkownicy> getFullList(){
		return userDAO.getFullList();
	}

	public List<Uzytkownicy> getList(){
		List<Uzytkownicy> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0){
			searchParams.put("surname", surname);
		}
		
		//2. Get list
		list = userDAO.getList(searchParams);
		
		return list;
	}
	
	public void resetSearch() {
	    this.surname = null; 
	}


	public String editUser(Uzytkownicy uz){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("person", uz);
		
		return PAGE_USER_EDIT;
	}

	public String deleteUser(int id){
		userDAO.delete(id);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	
	
}
