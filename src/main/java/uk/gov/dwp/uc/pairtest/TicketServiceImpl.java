package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    private final Regulatory regulatory;
    private final TicketRateCalc rateCalc = new TicketRateCalc();
    private final TicketPaymentServiceImpl paymentService = new TicketPaymentServiceImpl();
    private final SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

    private CinemaShow cinemaShow;

    public TicketServiceImpl(CinemaShow cinemaShow) {
        this.cinemaShow = cinemaShow;
        this.regulatory = new Regulatory(this.cinemaShow);
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        if (accountId == null || accountId <= 0 || ticketTypeRequests.length == 0) {
            throw new InvalidPurchaseException("Invalid Parameter");
        }
        regulatory.validate(ticketTypeRequests);
        int noOfTickets = regulatory.applyInfantSeatAllocationRule(ticketTypeRequests);
        int totalAmountToPay = Math.round(rateCalc.calculateTicketRate(ticketTypeRequests)); 

        paymentService.makePayment(accountId, totalAmountToPay);
        seatReservationService.reserveSeat(accountId, noOfTickets );

    }

}
