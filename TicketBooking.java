public class TicketBooking {
    private char source;
    private char destination;
    private int seats;
    private TicketSystem ticketSystem;
    TicketBooking(char source,char destination,int seats){
        this.source=source;
        this.destination=destination;
        this.seats=seats;
        this.ticketSystem=TicketSystem.getInstance();
    }

    private void bookTicket(){
        if(ticketSystem.checkSeatsAvailability(source, destination, seats)){
            Ticket ticket=new Ticket(source, destination, seats, TicketStatus.Booked);
            int newPnr=ticket.getPnrNumber();
            ticketSystem.addToBookedTickets(newPnr, ticket);
            System.out.println("Ticket Booked! Your PNR Number is:"+newPnr);
            ticketSystem.decreaseSeatAvailability(source, destination, seats);
        }
        else{
            if(ticketSystem.seatsBooked+seats>2){
                System.out.println("No Tickets Available");
            }
            else{
                WaitingListManager waitingListManager=new WaitingListManager();
                waitingListManager.waitingListEntry(source,destination,seats);
            }
        }
    }

    protected void execute(){
        this.bookTicket();
    }
}
