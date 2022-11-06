package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import uk.gov.dwp.uc.pairtest.domain.Cinema;
import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.Movie;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.Movie.Rating;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketServiceImplTest {
    
    private TicketServiceImpl ticketServiceImpl;

    @BeforeAll
    public void beforeTest() {

        Cinema cinema = new Cinema("Odeon Greenwich(iMax) - Screen 9");
        Movie blackAdam = new Movie(Rating.R_12A, "Black Adam");
        ticketServiceImpl = new TicketServiceImpl(new CinemaShow(LocalDateTime.of(2022, 11, 11, 12, 30, 0), cinema, blackAdam));
        
    }
    
    @Test
    public void regulatoryTest_InvalidParameter_InvalidAccoutIDNull() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 1);
            ticketServiceImpl.purchaseTickets(null, request1);
        });
    
        String expectedMessage = "Invalid Parameter";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void regulatoryTest_InvalidParameter_InvalidAccoutID() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 1);
            ticketServiceImpl.purchaseTickets(0L, request1);
        });
    
        String expectedMessage = "Invalid Parameter";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
