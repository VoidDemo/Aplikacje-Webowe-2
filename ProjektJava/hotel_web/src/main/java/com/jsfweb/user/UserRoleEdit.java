package com.jsfweb.user;

import hotel.jsf.dao.RoleDAO;
import hotel.jsf.dao.UserDAO;
import hotel.jsf.entity.Rola;
import hotel.jsf.entity.Uzytkownicy;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class UserRoleEdit implements Serializable {
    private Uzytkownicy user;
    private List<Rola> availableRoles;
    private int selectedRoleId;
    private String adminPassword; // Hasło do zatwierdzenia roli admina

    @EJB
    private UserDAO userDAO;
    @EJB
    private RoleDAO roleDAO;
    
    @Inject
    private Flash flash;

    @PostConstruct
    public void init() {
        
        availableRoles = roleDAO.getFullList();
        user = (Uzytkownicy) flash.get("person");
        
    }
    
    public Uzytkownicy getUser() {
		return user;
	}

	public void setUser(Uzytkownicy user) {
		this.user = user;
	}

	public List<Rola> getAvailableRoles() {
		return roleDAO.getFullList();
	}

	public int getSelectedRoleId() {
		return selectedRoleId;
	}

	public void setSelectedRoleId(int selectedRoleId) {
		this.selectedRoleId = selectedRoleId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

    public String saveRole() {
        // Sprawdź, czy zmiana na admina i czy hasło się zgadza
        if (selectedRoleId == getIdForAdminRole() && !isAdminPasswordCorrect(adminPassword)) {
            // Informuj o błędzie hasła
            return null;
        }

        Rola newRole = roleDAO.get(selectedRoleId);
        Rola previousRole = user.getRola();

        // Ustaw nową rolę i aktualizuj daty
        user.setRola(newRole);
        user.setDataNadaniaRoli(new Date());
        if (previousRole != null) {
            user.setDataOdebraniaRoli(new Date());
        }

        userDAO.update(user);

        // Jeśli użytkownik stał się adminem, zaktualizuj obecnego admina
        if (newRole.getIdRoli() == getIdForAdminRole()) {
            updateCurrentAdminRole();
        }

        return "/pages/admin/adminPage?faces-redirect=true";
    }

    private void updateCurrentAdminRole() {
        
    }


    private boolean isAdminPasswordCorrect(String password) {
        
        return true; 
    }

    private int getIdForAdminRole() {
        
        return 1; 
    }

	

    
    
}