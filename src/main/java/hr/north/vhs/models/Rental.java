package hr.north.vhs.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rentalId;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private long personId;

    private long vhsId;
    @Transient
    private String vhsTitle;
    @Transient
    private String userName;

    public Date getCreationDate() {
        return creationDate;
    }

    public long getRentalId() {
        return rentalId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setRentalId(long rentalId) {
        this.rentalId = rentalId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getVhsId() {
        return vhsId;
    }

    public void setVhsId(long vhsId) {
        this.vhsId = vhsId;
    }

    public String getUserName() {
        return userName;
    }

    public String getVhsTitle() {
        return vhsTitle;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setVhsTitle(String vhsTitle) {
        this.vhsTitle = vhsTitle;
    }
}
