/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "TASKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findById", query = "SELECT t FROM Task t WHERE t.id = :id"),
    @NamedQuery(name = "Task.findBySerial", query = "SELECT t FROM Task t WHERE t.serial = :serial"),
    @NamedQuery(name = "Task.findByAnswer", query = "SELECT t FROM Task t WHERE t.answer = :answer"),
    @NamedQuery(name = "Task.findBySeason", query = "SELECT t FROM Task t WHERE t.season = :season"),
    @NamedQuery(name = "Task.findByTour", query = "SELECT t FROM Task t WHERE t.tour = :tour"),
    @NamedQuery(name = "Task.findUniqTask", query = "SELECT t FROM Task t WHERE t.tour = :tour AND t.season = :season AND t.serial = :serial"),
    @NamedQuery(name = "Task.findAllTourTasks", query = "SELECT t FROM Task t WHERE t.tour = :tour AND t.season = :season"),
    @NamedQuery(name = "Task.findTourTasks", query = "SELECT t FROM Task t WHERE t.tour = :tour AND t.season = :season AND t.serial >= :serial")})
public class Task implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<TaskResult> taskResultList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SERIAL")
    private Integer serial;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 32700)
    @Column(name = "TEXT")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANSWER")
    private int answer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEASON")
    private int season;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOUR")
    private int tour;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private List<Resource> resourceList;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Task(Integer id, String text, int answer, int season, int tour) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.season = season;
        this.tour = tour;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    @XmlTransient
    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.olympiad.entities.Task[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TaskResult> getTaskResultList() {
        return taskResultList;
    }

    public void setTaskResultList(List<TaskResult> taskResultList) {
        this.taskResultList = taskResultList;
    }
    
}
