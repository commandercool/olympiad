/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "SUMMARY_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SummaryResult.findAll", query = "SELECT s FROM SummaryResult s"),
    @NamedQuery(name = "SummaryResult.findByLogin", query = "SELECT s FROM SummaryResult s WHERE s.login = :login"),
    @NamedQuery(name = "SummaryResult.findByFirstTour", query = "SELECT s FROM SummaryResult s WHERE s.firstTour = :firstTour"),
    @NamedQuery(name = "SummaryResult.findBySecondTour", query = "SELECT s FROM SummaryResult s WHERE s.secondTour = :secondTour"),
    @NamedQuery(name = "SummaryResult.findBySummary", query = "SELECT s FROM SummaryResult s WHERE s.summary = :summary")})
public class SummaryResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "FIRST_TOUR")
    private Integer firstTour;
    @Column(name = "SECOND_TOUR")
    private Integer secondTour;
    @Column(name = "SUMMARY")
    private Integer summary;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public SummaryResult() {
    }

    public SummaryResult(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getFirstTour() {
        return firstTour;
    }

    public void setFirstTour(Integer firstTour) {
        this.firstTour = firstTour;
    }

    public Integer getSecondTour() {
        return secondTour;
    }

    public void setSecondTour(Integer secondTour) {
        this.secondTour = secondTour;
    }

    public Integer getSummary() {
        return summary;
    }

    public void setSummary(Integer summary) {
        this.summary = summary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SummaryResult)) {
            return false;
        }
        SummaryResult other = (SummaryResult) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.olympiad.entities.SummaryResult[ login=" + login + " ]";
    }
    
}
