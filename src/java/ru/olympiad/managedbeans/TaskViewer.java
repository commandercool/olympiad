/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.managedbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.olympiad.ejb.OlympiadEJB;
import ru.olympiad.entities.Task;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class TaskViewer {
    @EJB
    private OlympiadEJB olympiadEJB;
    private List<Task> taskList;
    private Integer season;
    private Integer tour;
    
    @PostConstruct
    private void fetchTaskList(){
        taskList = olympiadEJB.getTasks();
    }
    
    public void filter() {
        taskList = olympiadEJB.getTasks(season, tour);
    }
    
    public List<Task> getTaskList() {
        return taskList;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getTour() {
        return tour;
    }

    public void setTour(Integer tour) {
        this.tour = tour;
    }

}
