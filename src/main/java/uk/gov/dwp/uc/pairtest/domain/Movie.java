package uk.gov.dwp.uc.pairtest.domain;

public class Movie {
    
    private String name;

    public String getName() {
        return this.name;
    }

    private Rating rating;

    public Rating getRating() {
        return rating;
    }

    public Movie(Rating rating, String name) {
        this.rating = rating;
        this.name = name;
    }

    public enum Rating {
        R_U, R_PG, R_12A, R_12, R_15, R_18
    }
}
