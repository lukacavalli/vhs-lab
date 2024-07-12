package hr.north.vhs.models;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personId;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    public long getId() {
        return personId;
    }

    public String getUserName() {
        return userName;
    }
}
