package uk.gov.dwp.uc.example;

import java.time.LocalDateTime;

import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.Cinema;
import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.Movie;
import uk.gov.dwp.uc.pairtest.domain.Movie.Rating;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;


/**
 * To purchase tickets for a family of 2 adults, one child and one infant for the movie Black Adam at the cinema Odeon Greenwich(iMax).
 * 
 * @author Chandrakumar Murugesan
 * @version 1.0
 */
public class ExampleOne {

    
    /** 
     * The main function for the ExampleOne.
     * 
     * @param agrv[] - Parameter from cli
     */
    public static void main(String agrv[]) {

        // Cinema configuration
        Cinema cinema = new Cinema("Odeon Greenwich(iMax) - Screen 9");
        Movie blackAdam = new Movie(Rating.R_12A, "Black Adam");
        CinemaShow blackAdamCinemaShow = new CinemaShow(LocalDateTime.of(2022, 11, 11, 12, 30, 0), cinema, blackAdam);
        TicketService ticketServiceImpl = new TicketServiceImpl(blackAdamCinemaShow);

        // Ticket Request
        TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 2);
        TicketTypeRequest request2 = new TicketTypeRequest(Type.CHILD, 1);
        TicketTypeRequest request3 = new TicketTypeRequest(Type.INFANT, 1);

        // Purchase process
        ticketServiceImpl.purchaseTickets(100L, request1, request2, request3);
        System.out.println("Tickets purchased successfully");

    }

}
