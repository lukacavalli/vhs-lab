package hr.north.vhs.models;

import jakarta.persistence.*;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rentalId;

    private String title;

    private String author;
}
