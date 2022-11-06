package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.CinemaShow;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * This is the primary class where the Ticker Purchase Process is implements.
 * 
 * This class is depend on the class {@literal {@link uk.gov.dwp.uc.pairtest.domain.CinemaShow}}
 *  
 * Example:
 * <pre>
 *  <code>
 *      // Cinema configuration
 *      Cinema cinema = new Cinema("Odeon Greenwich(iMax) - Screen 9");
 *      Movie blackAdam = new Movie(Rating.R_12A, "Black Adam");
 *      CinemaShow blackAdamCinemaShow = new CinemaShow(LocalDateTime.of(2022, 11, 11, 12, 30, 0), cinema, blackAdam);
 *      TicketService ticketServiceImpl = new TicketServiceImpl(blackAdamCinemaShow);
 *
 *      // Ticket Request
 *      TicketTypeRequest request1 = new TicketTypeRequest(Type.ADULT, 2);
 *      TicketTypeRequest request2 = new TicketTypeRequest(Type.CHILD, 1);
 *      TicketTypeRequest request3 = new TicketTypeRequest(Type.INFANT, 1);
 *
 *      // Purchase process
 *      ticketServiceImpl.purchaseTickets(100L, request1, request2, request3);
 *      System.out.println("Tickets purchased successfully");
 *  </code>
 * </pre>
 * 
 * @author Chandrakumar Murugesan
 * @version 1.0
 */
public class TicketServiceImpl implements TicketService {
    
    private final Regulatory regulatory;
    private final TicketRateCalc rateCalc = new TicketRateCalc();
    private final TicketPaymentServiceImpl paymentService = new TicketPaymentServiceImpl();
    private final SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

    private CinemaShow cinemaShow;

    public TicketServiceImpl(CinemaShow cinemaShow) {
        this.cinemaShow = cinemaShow;
        this.regulatory = new Regulatory(this.cinemaShow);
    }
    
    /**
     * This function purchaseTickets is the core function of this Cinema Ticket Booking service.
     * 
     * This function returns nothing, it means that the purchase is done successfully. If any exception, regulatory issue,
     * or insufficient parameter then InvalidPurchaseException is thrown. 
     *  
     * @param accountId - Customer account ID
     * @param ticketTypeRequests - Purchase requests
     * @throws InvalidPurchaseException - If any exception, regulatory issue, or insufficient parameter then this exception is thrown. 
     * 
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        // Parameter validation
        if (accountId == null || accountId <= 0 || ticketTypeRequests.length == 0) {
            throw new InvalidPurchaseException("Invalid Parameter");
        }

        // Regulatory process
        regulatory.validate(ticketTypeRequests);
        int noOfTickets = regulatory.applyInfantSeatAllocationRule(ticketTypeRequests);
        
        // Purchase process
        int totalAmountToPay = Math.round(rateCalc.calculateTicketRate(ticketTypeRequests));
        paymentService.makePayment(accountId, totalAmountToPay);
        seatReservationService.reserveSeat(accountId, noOfTickets );

    }

}
