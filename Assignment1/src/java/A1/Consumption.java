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

/**
 *
 * @author apurv
 */
@Entity
@Table(name = "CONSUMPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumption.findAll", query = "SELECT c FROM Consumption c")
    , @NamedQuery(name = "Consumption.findByConsumptionid", query = "SELECT c FROM Consumption c WHERE c.consumptionid = :consumptionid")
    , @NamedQuery(name = "Consumption.findByUserid", query = "SELECT c FROM Consumption c WHERE c.userid.userid = :userid")
    , @NamedQuery(name = "Consumption.findByFoodid", query = "SELECT c FROM Consumption c WHERE c.foodid.foodid = :foodid")
    , @NamedQuery(name = "Consumption.findByDateAndFat", query = "SELECT s FROM Consumption s WHERE s.date = :date AND s.foodid.fat = :fat")
    , @NamedQuery(name = "Consumption.findByDate", query = "SELECT c FROM Consumption c WHERE c.date = :date")
    , @NamedQuery(name = "Consumption.findByFoodserving", query = "SELECT c FROM Consumption c WHERE c.foodserving = :foodserving")})
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUMPTIONID")
    private Integer consumptionid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FOODSERVING")
    private int foodserving;
    @JoinColumn(name = "FOODID", referencedColumnName = "FOODID")
    @ManyToOne(optional = false)
    private Food foodid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public Consumption() {
    }

    public Consumption(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public Consumption(Integer consumptionid, Date date, int foodserving) {
        this.consumptionid = consumptionid;
        this.date = date;
        this.foodserving = foodserving;
    }

    public Integer getConsumptionid() {
        return consumptionid;
    }

    public void setConsumptionid(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFoodserving() {
        return foodserving;
    }

    public void setFoodserving(int foodserving) {
        this.foodserving = foodserving;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumptionid != null ? consumptionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.consumptionid == null && other.consumptionid != null) || (this.consumptionid != null && !this.consumptionid.equals(other.consumptionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "A1.Consumption[ consumptionid=" + consumptionid + " ]";
    }
    
}
