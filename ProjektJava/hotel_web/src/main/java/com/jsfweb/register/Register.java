package com.jsfweb.register;

import hotel.jsf.dao.UserDAO;
import hotel.jsf.entity.Rola;
import hotel.jsf.entity.Uzytkownicy;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;



import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

@Named
@ViewScoped
public class Register  implements Serializable {

	private String name;
    private String surname;
    private String email;
    private String phone;
    private String pass1;
    private String pass2;

    @EJB
    private UserDAO userDAO;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPass1() {
		return pass1;
	}

	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}	
	
	
    public String register() {
        if (!pass1.equals(pass2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hasła nie są identyczne"));
            return null;
        }

        Uzytkownicy newUser = new Uzytkownicy();
        newUser.setImie(name);
        newUser.setNazwisko(surname);
        newUser.setEmail(email);
        newUser.setTelefon(Integer.parseInt(phone));
        newUser.setHaslo(pass1);
        
        Rola userRole = new Rola();
        userRole.setIdRoli(3); // Ustawienie domyślnej roli na "uzytkownik"
        newUser.setRola(userRole);
        
        LocalDate currentDate = LocalDate.now();
        newUser.setDataUtworzenia(java.sql.Date.valueOf(currentDate));
        newUser.setDataNadaniaRoli(java.sql.Date.valueOf(currentDate));

        userDAO.create(newUser);
        return "login?faces-redirect=true"; // Przekierowanie do strony logowania po rejestracji
    }



	
}
