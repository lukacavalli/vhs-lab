package hr.north.vhs.models;

import jakarta.persistence.*;

@Entity
public class VHS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long vhsId;

    private String title;

    private String genre;

    private int releaseYear;

    private int durationInMinutes;

    public long getId() {
        return vhsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
