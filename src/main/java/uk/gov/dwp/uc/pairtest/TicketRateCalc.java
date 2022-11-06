package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

/**
 * This class used to find the total price of the ticket requests.
 * 
 * @author Chandrakumar Murugesan
 * @version 1.0
 */
public class TicketRateCalc {
    
    private final Float ADULT_RATE = 20.0f;
    private final Float CHILD_RATE = 10.0f;
    private final Float INFANT_RATE = 0.0f;
    
    /** 
     * This function calculate the ticket rate againts the ticket count.
     * 
     * @param ...request - Purchase request
     * @return Float 
     */
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
