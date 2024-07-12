package hr.north.vhs.models;

import jakarta.persistence.*;

@Entity
public class VHS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@Column(nullable = false, unique = true)
    private String title;

    //@Column(nullable = false)
    private String author;
}
