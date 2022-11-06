package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import uk.gov.dwp.uc.pairtest.domain.Cinema;
import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.Movie;
import uk.gov.dwp.uc.pairtest.domain.Movie.Rating;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegulatoryTest {
    
    private Regulatory regulatory; 

    @BeforeAll
    public void beforeTest() {

        Cinema cinema = new Cinema("Odeon Greenwich(iMax) - Screen 9");
        Movie blackAdam = new Movie(Rating.R_12A, "Black Adam");
        regulatory = new Regulatory(new CinemaShow(LocalDateTime.of(2022, 11, 11, 12, 30, 0), cinema, blackAdam));

    }

    @Test
    public void regulatoryTest_InvalidParameter_TicketTypeRequestIsNull() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = null;
            regulatory.validate(request1);
        });
    
        String expectedMessage = "Invalid Parameter";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void regulatoryTest_InvalidParameter_TicketTypeRequest_InvalidNoOfTickets() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 0);
            regulatory.validate(request1);
        });
    
        String expectedMessage = "No of tickets should be minimum 1";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void regulatoryTest_InvalidParameter_TicketTypeRequest_InvalidTicketType() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(null, 1);
            regulatory.validate(request1);
        });
    
        String expectedMessage = "Ticket type is invalid";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void regulatoryTest_NoAdultRequest() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.CHILD, 3);
            regulatory.validate(request1);
        });
    
        String expectedMessage = "Child or Infant tickets can't be purchased without purchasing an Adult ticket";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void regulatoryTest_MaxTicketPerPurchase() {
        Exception exception = assertThrows(InvalidPurchaseException.class, () -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.INFANT, 3);
            TicketTypeRequest request2 = new TicketTypeRequest(Type.CHILD, 14);
            TicketTypeRequest request3 = new TicketTypeRequest(Type.ADULT, 6);
            
            regulatory.validate(request1, request2, request3);
        });
    
        String expectedMessage = "Only a maximum of 20 tickets that can be purchased at a time";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void regulatoryTest_ApplyInfantSeatAllocationRule() {
        TicketTypeRequest request1 = new TicketTypeRequest(Type.INFANT, 3);
        TicketTypeRequest request2 = new TicketTypeRequest(Type.CHILD, 14);
        TicketTypeRequest request3 = new TicketTypeRequest(Type.ADULT, 6);
        
        int actual = regulatory.applyInfantSeatAllocationRule(request1, request2, request3);
    
        int expected = 20;
        
        assertEquals(expected, actual, "Unexpected result after apply the Infant Sear Allocation Regulatory");
    }

    @Test
    public void regulatoryTest_ValidProcess() {
        assertDoesNotThrow(() -> {
            TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 1);
            regulatory.validate(request1);
        }, "Unexpected exception throws");
    }
}
