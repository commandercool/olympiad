/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.managedbeans;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import ru.olympiad.ejb.OlympiadEJB;
import ru.olympiad.entities.User;

/**
 *
 * @author alex
 */
@ManagedBean
@SessionScoped
public class OlimpiadContext {
    @EJB
    private OlympiadEJB olympiadEJB;
    private User user;
    private String fullName;
    private String role;
    
    @PostConstruct
    private void fetchUser(){
        user = olympiadEJB.getUser(FacesContext.getCurrentInstance().
                getExternalContext().getRemoteUser());
        if (user != null){
            fullName = user.getFname() + " " +user.getLname() + " " +
                    user.getPatronymic();
        }
        
    }
    
    public String logout(){
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession)ectx.getSession(true)).invalidate();
        try {
            ((HttpServletRequest)ectx.getRequest()).logout();
        } catch (ServletException ex) {
            Logger.getLogger(OlimpiadContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }
    
}
