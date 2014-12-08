/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "TASK_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaskResult.findAll", query = "SELECT t FROM TaskResult t"),
    @NamedQuery(name = "TaskResult.findByLogin", query = "SELECT t FROM TaskResult t WHERE t.taskResultPK.login = :login"),
    @NamedQuery(name = "TaskResult.findByTaskId", query = "SELECT t FROM TaskResult t WHERE t.taskResultPK.taskId = :taskId"),
    @NamedQuery(name = "TaskResult.findByTaskResult", query = "SELECT t FROM TaskResult t WHERE t.taskResult = :taskResult")})
public class TaskResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TaskResultPK taskResultPK;
    @Column(name = "TASK_RESULT")
    private Integer taskResult;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Task task;

    public TaskResult() {
    }

    public TaskResult(TaskResultPK taskResultPK) {
        this.taskResultPK = taskResultPK;
    }

    public TaskResult(String login, int taskId) {
        this.taskResultPK = new TaskResultPK(login, taskId);
    }

    public TaskResultPK getTaskResultPK() {
        return taskResultPK;
    }

    public void setTaskResultPK(TaskResultPK taskResultPK) {
        this.taskResultPK = taskResultPK;
    }

    public Integer getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Integer taskResult) {
        this.taskResult = taskResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskResultPK != null ? taskResultPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskResult)) {
            return false;
        }
        TaskResult other = (TaskResult) object;
        if ((this.taskResultPK == null && other.taskResultPK != null) || (this.taskResultPK != null && !this.taskResultPK.equals(other.taskResultPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.olympiad.entities.TaskResult[ taskResultPK=" + taskResultPK + " ]";
    }
    
}
