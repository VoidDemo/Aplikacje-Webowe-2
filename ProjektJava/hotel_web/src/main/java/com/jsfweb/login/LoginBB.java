package com.jsfweb.login;

import java.util.List;

import hotel.jsf.dao.UserDAO;
import hotel.jsf.entity.Uzytkownicy;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBB {
    private static final String PAGE_MAIN = "/pages/index";
    private static final String PAGE_LOGIN = "/pages/login";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_USER="/pages/user/account";
    private static final String PAGE_MOD="/pages/mod/modPage";
    private static final String PAGE_ADMIN="/pages/admin/adminPage";
    
    
    private String login;
    private String pass;

    @EJB
    UserDAO userDAO;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String doLogin() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        // 1. verify login and password - get Uzytkownicy from "database"
        Uzytkownicy user = userDAO.getUserFromDatabase(login, pass);

        // 2. if bad login or password - stay with error info
        if (user == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Niepoprawny login lub hasło", null));
            return PAGE_STAY_AT_THE_SAME;
        }

        // 3. if logged in: get Uzytkownicy roles, save in RemoteClient and store it in session
        RemoteClient<Uzytkownicy> client = new RemoteClient<>(); //create new RemoteClient
        client.setDetails(user);
        
        List<String> roles = userDAO.getUserRolesFromDatabase(user); //get Uzytkownicy roles
        
        if (roles != null) { //save roles in RemoteClient
            for (String role : roles) {
                client.getRoles().add(role);
            }
        }
        
        //store RemoteClient with request info in session (needed for SecurityFilter)
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        client.store(request);

        // and enter the system (now SecurityFilter will pass the request)
        return PAGE_MAIN;
    }
    
    
    public String getAccountPage() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        
        if (client != null && !client.getRoles().isEmpty()) {
            // Zakładając, że role są zapisane jako String, sprawdzamy przynależność do ról
        	if (client.isInRole("admin")) {
                return "/pages/admin/adminPage?faces-redirect=true";
            } else if (client.isInRole("moderator")) {
                return "/pages/mod/modPage?faces-redirect=true";
            } else if (client.isInRole("uzytkownik")) {
                return "/pages/user/account?faces-redirect=true";
            }
        }
        return PAGE_LOGIN + "?faces-redirect=true"; // Przekierowanie na stronę logowania
    }
    
    public String doLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        // Invalidate session
        session.invalidate();
        return PAGE_LOGIN;
    }
    
    public String getZalogowanyUzytkownikImieNazwisko() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        RemoteClient<Uzytkownicy> client = RemoteClient.load(session);
        
        if (client != null && client.getDetails() != null) {
            Uzytkownicy user = client.getDetails();
            return user.getImie() + " " + user.getNazwisko();
        }
        return "Gość";
    }
    
    
}
