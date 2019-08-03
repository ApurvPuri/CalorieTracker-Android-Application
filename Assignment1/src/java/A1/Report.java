/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author apurv
 */
@Entity
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportid", query = "SELECT r FROM Report r WHERE r.reportid = :reportid")
    , @NamedQuery(name = "Report.findByDate", query = "SELECT r FROM Report r WHERE r.date = :date")
    , @NamedQuery(name = "Report.findByTotcalconsumed", query = "SELECT r FROM Report r WHERE r.totcalconsumed = :totcalconsumed")
    , @NamedQuery(name = "Report.findByStepstaken", query = "SELECT r FROM Report r WHERE r.stepstaken = :stepstaken")
    , @NamedQuery(name = "Report.findByUserName", query = "SELECT r FROM Report r WHERE r.userid.name = :username")
    , @NamedQuery(name = "Report.findByUserid", query = "SELECT r FROM Report r WHERE r.userid.userid = :userid")
    , @NamedQuery(name = "Report.findByCalburned", query = "SELECT r FROM Report r WHERE r.calburned = :calburned")
    , @NamedQuery(name = "Report.findByCaloriegoal", query = "SELECT r FROM Report r WHERE r.caloriegoal = :caloriegoal")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORTID")
    private Integer reportid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTCALCONSUMED")
    private int totcalconsumed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALBURNED")
    private int calburned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STEPSTAKEN")
    private int stepstaken;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIEGOAL")
    private int caloriegoal;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Report() {
    }

    public Report(Integer reportid) {
        this.reportid = reportid;
    }

    public Report(Integer reportid, Date date, int totcalconsumed, int stepstaken, int caloriegoal, int calburned) {
        this.reportid = reportid;
        this.date = date;
        this.totcalconsumed = totcalconsumed;
        this.stepstaken = stepstaken;
        this.caloriegoal = caloriegoal;
        this.calburned = calburned;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotcalconsumed() {
        return totcalconsumed;
    }

    public void setTotcalconsumed(int totcalconsumed) {
        this.totcalconsumed = totcalconsumed;
    }

    public int getCalburned() {
        return calburned;
    }

    public void setCalburned(int calburned) {
        this.calburned = calburned;
    }

    public int getStepstaken() {
        return stepstaken;
    }

    public void setStepstaken(int stepstaken) {
        this.stepstaken = stepstaken;
    }

    public int getCaloriegoal() {
        return caloriegoal;
    }

    public void setCaloriegoal(int caloriegoal) {
        this.caloriegoal = caloriegoal;
    }

    //@XmlTransient
    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportid != null ? reportid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportid == null && other.reportid != null) || (this.reportid != null && !this.reportid.equals(other.reportid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "A1.Report[ reportid=" + reportid + " ]";
    }

}
