package hr.north.vhs.models;

import jakarta.persistence.*;

@Entity
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;
    private String userName;

    private String password;

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
