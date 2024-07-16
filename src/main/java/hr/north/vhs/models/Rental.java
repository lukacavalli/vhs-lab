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

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private long personId;

    private long vhsId;

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public long getRentalId() {
        return rentalId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

}
