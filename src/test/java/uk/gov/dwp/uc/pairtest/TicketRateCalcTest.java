package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketRateCalcTest {

    private TicketRateCalc ticketRateCalc; 

    @BeforeAll
    public void beforeTest() {
        ticketRateCalc =  new TicketRateCalc();
    }


    @Test
    public void ticketRateCalc_AdultValidProcess() {
        TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 2);
        Float expectedRate = 40.0f;
        Float actualRate = ticketRateCalc.calculateTicketRate(request1);
        assertEquals(expectedRate, actualRate, String.format("Expected rate %f but %f returned", expectedRate, actualRate));
    }

    @Test
    public void ticketRateCalc_ChildValidProcess() {
        TicketTypeRequest request1 = new TicketTypeRequest(Type.CHILD, 2);
        Float expectedRate = 20.0f;
        Float actualRate = ticketRateCalc.calculateTicketRate(request1);
        assertEquals(expectedRate, actualRate, String.format("Expected rate %f but %f returned", expectedRate, actualRate));
    }

    @Test
    public void ticketRateCalc_InfantValidProcess() {
        TicketTypeRequest request1 = new TicketTypeRequest(Type.INFANT, 10);
        Float expectedRate = 0.0f;
        Float actualRate = ticketRateCalc.calculateTicketRate(request1);
        assertEquals(expectedRate, actualRate, String.format("Expected rate %f but %f returned", expectedRate, actualRate));
    }

    @Test
    public void ticketRateCalc_AdultInfantValidProcess() {
        
        TicketTypeRequest request1 = new TicketTypeRequest(Type.INFANT, 2);
        TicketTypeRequest request2 = new TicketTypeRequest(Type.ADULT, 4);
        
        Float expectedRate = 80.0f;
        Float actualRate = ticketRateCalc.calculateTicketRate(request1, request2);
        assertEquals(expectedRate, actualRate, String.format("Expected rate %f but %f returned", expectedRate, actualRate));
    }

    @Test
    public void ticketRateCalc_AdultChildValidProcess() {
        
        TicketTypeRequest request1 = new TicketTypeRequest(Type.CHILD, 8);
        TicketTypeRequest request2 = new TicketTypeRequest(Type.ADULT, 4);
        
        Float expectedRate = 160.0f;
        Float actualRate = ticketRateCalc.calculateTicketRate(request1, request2);
        assertEquals(expectedRate, actualRate, String.format("Expected rate %f but %f returned", expectedRate, actualRate));
    }
    
}
