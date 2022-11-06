package uk.gov.dwp.uc.pairtest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class Regulatory {
    
    private CinemaShow cinemaShow;

    public Regulatory(CinemaShow cinemaShow) {
        this.cinemaShow = cinemaShow;
    }

    public Boolean validate(TicketTypeRequest ...requests) throws InvalidPurchaseException {
        
        boolean adultAccommodation = false;
        int noOfTickets = 0;
        for (TicketTypeRequest ticketTypeRequest: requests) {
            if (ticketTypeRequest == null) {
                throw new InvalidPurchaseException("Invalid Parameter");
            }
            else if (ticketTypeRequest.getNoOfTickets() <= 0) {
                throw new InvalidPurchaseException("No of tickets should be minimum 1");
            }
            else if (ticketTypeRequest.getTicketType() == null) {
                throw new InvalidPurchaseException("Ticket type is invalid");
            }

            if (Type.ADULT.equals(ticketTypeRequest.getTicketType())) {
                adultAccommodation = true;
            }

            
            noOfTickets += ticketTypeRequest.getNoOfTickets();
        }

        if (!adultAccommodation) {
            throw new InvalidPurchaseException("Child or Infant tickets can't be purchased without purchasing an Adult ticket");
        }

        if (noOfTickets > 20) {
            throw new InvalidPurchaseException("Only a maximum of 20 tickets that can be purchased at a time");
        }
        

        return true;
    }

    public int applyInfantSeatAllocationRule(TicketTypeRequest ...requests) {

        List<TicketTypeRequest> requestsAsList = Arrays.asList(requests);
        
        return requestsAsList.stream()
        .filter(Predicate.not(elm -> Type.INFANT.equals(elm.getTicketType())))
        .mapToInt(TicketTypeRequest::getNoOfTickets).sum();

    }
}
