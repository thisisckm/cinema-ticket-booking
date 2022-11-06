# Cinema Ticket Booking Service(CTBS)

Cinema Ticket Booking Service's primary task is to book Cinema tickets. 

This CTBS is Java Library Service which can be used in any Java application.

### Limitation
1. Rules and regulations are hard coded
2. Ticket pricing is hard coded
3. No data like cinema, cinema show, or account are not stored in a file or database

## Quick Start Guide
### Prerequisite
1. git - Install the latest version of git
2. Java JDK 17 - Install the latest build
3. Install Java Maven - Install the latest version.

### Build the code
1. Clone the code from GitHub
```
$ git clone https://github.com/thisisckm/cinema-ticket-booking-service
```
2. Switch to the cinema-ticket-booking-service folder
```
$ cd cinema-ticket-booking-service
```
3. Build the jar file.
```
$ mvn package
```
You can find the jar file under the target folder as cinema-ticket-booking-service-1.0.0-jar-with-dependencies.jar

### Test the code
1. Run the test
```
$ mvn test
```

### Example
To purchase tickets for a family of 2 adults, one child and one infant for the movie Black Adam at the cinema Odeon Greenwich(iMax).
```
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
```
