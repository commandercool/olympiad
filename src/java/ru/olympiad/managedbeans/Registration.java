/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.managedbeans;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import ru.olympiad.ejb.OlympiadEJB;
import ru.olympiad.entities.Role;
import ru.olympiad.entities.User;
import ru.olympiad.entities.UserRole;

/**
 *
 * @author alex
 */
@ManagedBean
@RequestScoped
public class Registration {
    @EJB private OlympiadEJB olympiad;
    private String passConfirm;
    private Map<String, String> errMap;
    
    private User user;
    /**
     * Creates a new instance of Registration
     */
    public Registration() {
        user = new User();
        errMap = new HashMap<>();
        errMap.put("Login", "Пользователь с таким логином уже зарегистрирован");
        errMap.put("Email", "Пользователь с таким адресом электронной почты уже зарегистрирован");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String param = component.getClientId();
        if (!olympiad.checkUserAvailability(param, (String)value)){
            throw new ValidatorException(new FacesMessage(errMap.get(param)));
        }
    }
    
    public String register(){
        if (passConfirm.equals(user.getPassword())) {
            Role role = olympiad.getRole("Participant");
            UserRole userRole = new UserRole();
            userRole.setLogin(user);
            userRole.setRoleName(role);
            user.setUserRoleList(Arrays.asList(userRole));
            olympiad.register(user);
            return "registersuccess";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Введеные пароли не совпадают"));
            return null;
        }
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }
    
}
