/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author alex
 */
@Embeddable
public class TaskResultPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TASK_ID")
    private int taskId;

    public TaskResultPK() {
    }

    public TaskResultPK(String login, int taskId) {
        this.login = login;
        this.taskId = taskId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        hash += (int) taskId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskResultPK)) {
            return false;
        }
        TaskResultPK other = (TaskResultPK) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        if (this.taskId != other.taskId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.olympiad.entities.TaskResultPK[ login=" + login + ", taskId=" + taskId + " ]";
    }
    
}
