/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.managedbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ru.olympiad.ejb.OlympiadEJB;
import ru.olympiad.entities.Task;

/**
 *
 * @author alex
 */
@ManagedBean
@SessionScoped
public class TasksThrough {
    @EJB
    private OlympiadEJB olympiadEJB;
    private List<Task> tasks;
    private Map<Task, Integer> answerMap;
    private int current = 0;
    private boolean compiled;
    private boolean hasNext;
    private boolean hasPrev;
    
    @PostConstruct
    private void fetchTasks(){
        String login = FacesContext.getCurrentInstance().getExternalContext()
                .getRemoteUser();
        if (!olympiadEJB.isTourComplited(login)){
            tasks = olympiadEJB.getTourTasks();
            answerMap = new HashMap<>();
            for (Task task : tasks){
                answerMap.put(task, null);
            }
            hasNext = !tasks.isEmpty();
        } else {
            compiled = true;
        }
    }
    
    public Task getCurrentTask(){
        return tasks.get(current);
    }
    
    public void submitResults(){
        olympiadEJB.submitTourResults(answerMap);
        compiled = true;
    }
    
    public void nextTask() {
        if (hasNext) {
            current++;
            checkCurrent();
        }
    }

    public void prevTask() {
        if (hasPrev) {
            current--;
            checkCurrent();
        }
    }
    
    private void checkCurrent() {
        if (current == tasks.size() - 1) {
            hasNext = false;
        } else {
            hasNext = true;
        }
        if (current == 0) {
            hasPrev = false;
        } else {
            hasPrev = true;
        }
    }
    
    public Integer getAnswer(){
        return answerMap.get(getCurrentTask());
    }
    
    public void setAnswer(Integer answer){
        answerMap.put(getCurrentTask(), answer);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public int getCurrent() {
        return current + 1;
    }

    public List<Entry<Task, Integer>> getAnswerMap() {
        return new ArrayList<>(answerMap.entrySet());
    }

    public boolean isCompiled() {
        return compiled;
    }
    
}
