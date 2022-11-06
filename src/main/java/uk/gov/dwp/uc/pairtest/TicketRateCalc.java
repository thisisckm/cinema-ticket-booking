package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketRateCalc {
    
    private final Float ADULT_RATE = 20.0f;
    private final Float CHILD_RATE = 10.0f;
    private final Float INFANT_RATE = 0.0f;
    

    public Float calculateTicketRate(TicketTypeRequest ...request) {
        Float totalPrice = 0.0f;
        for (TicketTypeRequest ticketTypeRequest: request) {
            if (Type.ADULT.equals(ticketTypeRequest.getTicketType())) {
                totalPrice += ADULT_RATE * ticketTypeRequest.getNoOfTickets();
            }
            else if (Type.CHILD.equals(ticketTypeRequest.getTicketType())) {
                totalPrice += CHILD_RATE * ticketTypeRequest.getNoOfTickets();
            }
            else if (Type.INFANT.equals(ticketTypeRequest.getTicketType())) {
                totalPrice += INFANT_RATE * ticketTypeRequest.getNoOfTickets();
            }
        }
        return totalPrice;
    }
}
