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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apurv
 */
@Entity
@Table(name = "CREDENTIALS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credentials.findAll", query = "SELECT c FROM Credentials c")
    , @NamedQuery(name = "Credentials.findByUserid", query = "SELECT c FROM Credentials c WHERE c.userid = :userid")
    , @NamedQuery(name = "Credentials.findByUsername", query = "SELECT c FROM Credentials c WHERE c.username = :username")
    , @NamedQuery(name = "Credentials.findBySignupdate", query = "SELECT c FROM Credentials c WHERE c.signupdate = :signupdate")
    , @NamedQuery(name = "Credentials.findByPasswordhash", query = "SELECT c FROM Credentials c WHERE c.passwordhash = :passwordhash")})
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERID")
    private Integer userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIGNUPDATE")
    @Temporal(TemporalType.DATE)
    private Date signupdate;
    @Size(max = 32)
    @Column(name = "PASSWORDHASH")
    private String passwordhash;

    public Credentials() {
    }

    public Credentials(Integer userid) {
        this.userid = userid;
    }

    public Credentials(Integer userid, String username, Date signupdate) {
        this.userid = userid;
        this.username = username;
        this.signupdate = signupdate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credentials)) {
            return false;
        }
        Credentials other = (Credentials) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "A1.Credentials[ userid=" + userid + " ]";
    }
    
}
