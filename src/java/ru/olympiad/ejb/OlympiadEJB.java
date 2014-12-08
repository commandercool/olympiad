/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.ejb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ru.olympiad.entities.Role;
import ru.olympiad.entities.SummaryResult;
import ru.olympiad.entities.Task;
import ru.olympiad.entities.TaskResult;
import ru.olympiad.entities.TaskResultPK;
import ru.olympiad.entities.User;

/**
 *
 * @author alex
 */
@Stateless
@DeclareRoles({"Admin", "Writer", "Participant"})
@RolesAllowed({"Admin", "Writer"})
public class OlympiadEJB {
    @PersistenceContext(unitName = "OlympiadPU")
    private EntityManager em;
    private int tour = 1;
    private int season = 13;
    
    @RolesAllowed("Participant")
    public List<Task> getTourTasks(){
        Query query = em.createNamedQuery("Task.findAllTourTasks", Task.class);
        query.setParameter("tour", tour);
        query.setParameter("season", season);
        return query.getResultList();
    }
    
    @RolesAllowed("Participant")
    public void submitTourResults(Map<Task, Integer> results){
        User user = getUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        int summary = 0;
        for (Entry<Task, Integer> result : results.entrySet()) {
            TaskResult taskResult = new TaskResult();
            Task task = result.getKey();
            taskResult.setTask(task);
            taskResult.setUser(user);
            taskResult.setTaskResult(result.getValue());
            taskResult.setTaskResultPK(new TaskResultPK(user.getLogin(), 
                    task.getId()));
            if (result.getValue() != null &&
                    result.getValue() == result.getKey().getAnswer()) {
                summary++;
            }
            em.persist(taskResult);
        }
        SummaryResult sr = em.find(SummaryResult.class, user.getLogin());
        if (sr != null) {
            switch (tour) {
                case 1:
                    sr.setFirstTour(summary);
                    break;
                case 2:
                    sr.setSecondTour(summary);
                    break;
            }
            em.merge(sr);
        } else {
            
        }
        em.flush();
    }
    
    @RolesAllowed("Participant")
    public boolean isTourComplited(String login) {
        SummaryResult sr = em.find(SummaryResult.class, login);
        if (sr != null) {
            switch (tour) {
                case 1:
                    return sr.getFirstTour() != null;
                case 2:
                    return sr.getSecondTour() != null;
            }
        }
        return false;
    }
    
    public List<Task> getTasks(){
        Query query = em.createNamedQuery("Task.findAll", Task.class);
        return query.getResultList();
    }
    
    public List<Task> getTasks(Integer season, Integer tour){
        Query query = null;
        if (season == null && tour == null) {
            query = em.createNamedQuery("Task.findAll", Task.class);
        } else if (season != null && tour != null) {
            query = em.createNamedQuery("Task.findAllTourTasks", Task.class);
            query.setParameter("season", season);
            query.setParameter("tour", tour);
        } else if(season != null) {
            query = em.createNamedQuery("Task.findBySeason", Task.class);
            query.setParameter("season", season);
        } else if (tour != null) {
            query = em.createNamedQuery("Task.findByTour", Task.class);
            query.setParameter("tour", tour);
        }
        return query.getResultList();
    }
    
    public Task getTask(int id){
        Query query = em.createNamedQuery("Task.findById", Task.class);
        query.setParameter("id", id);
        List<Task> tasks = query.getResultList();
        if (!tasks.isEmpty()){
            return tasks.get(0);
        } else {
            return null;
        }
    }
    
    public void saveTask(Task task, boolean newTask){
        Query query = em.createNamedQuery("Task.findUniqTask", Task.class);
        query.setParameter("season", task.getSeason());
        query.setParameter("tour", task.getTour());
        query.setParameter("serial", task.getSerial());
        if (!query.getResultList().isEmpty()){
            query = em.createNamedQuery("Task.findTourTasks", Task.class);
            query.setParameter("season", task.getSeason());
            query.setParameter("tour", task.getTour());
            query.setParameter("serial", task.getSerial());
            List<Task> tasks = query.getResultList();
            for (Task tsk : tasks){
                tsk.setSerial(tsk.getSerial() + 1);
                em.persist(tsk);
            }
        }
        if (newTask) {
            em.persist(task);
        } else {
            em.merge(task);
        }
        em.flush();
    }
    
    @PermitAll
    public User getUser(String login){
        Query query = em.createNamedQuery("User.findByLogin", User.class);
        query.setParameter("login", login);
        List<User> users = query.getResultList();
        if (!users.isEmpty()){
            return users.get(0);
        } else {
            return null;
        }
    }
    
    @PermitAll
    public void register(User user){
        SummaryResult summaryResult = new SummaryResult();
        summaryResult.setLogin(user.getLogin());
        summaryResult.setUser(user);
        em.persist(summaryResult);
        em.persist(user);
        em.flush();
    }
    
    @PermitAll
    public Role getRole(String name) {
        return em.find(Role.class, name);
    }
    
    @PermitAll
    public boolean checkUserAvailability(String property, String value) {
        Query query = em.createNamedQuery("User.findBy".concat(property), User.class);
        query.setParameter(property.toLowerCase(), value);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
}
