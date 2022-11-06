package uk.gov.dwp.uc.pairtest.domain;

import java.time.LocalDateTime;

public class CinemaShow {

    private LocalDateTime showTime;

    public LocalDateTime getShowTime() {
        return showTime;
    }

    private Cinema cinema;

    public Cinema getCinema() {
        return cinema;
    }

    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public CinemaShow(LocalDateTime showTime, Cinema cinema, Movie movie) {
        this.showTime = showTime;
        this.cinema = cinema;
        this.movie = movie;
    }
    
}
