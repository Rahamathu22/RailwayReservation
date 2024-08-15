public class TicketCanceling {
    private int pnr;
    private int seats;
    private TicketSystem ticketSystem;
    TicketCanceling(int pnr,int seats){
        this.pnr=pnr;
        this.seats=seats;
        this.ticketSystem=TicketSystem.getInstance();
    }

    private void cancelTicket(){
        Ticket ticket=ticketSystem.getTicket(pnr);
        if(ticket!=null){
            int bookedSeats=ticket.getSource();
            char source=ticket.getSource(),destination=ticket.getDestination();
            if(bookedSeats>seats){
                ticket.setSeats(bookedSeats-seats);
                ticketSystem.storePartiallyCanceledSeats(pnr, seats);
                System.out.println("Partially cancelled Pnr:"+pnr);
            }
            else{
                ticketSystem.processCancellation(pnr, ticket);
                System.out.println("Cancelled Ticket pnr:"+pnr);
            }
            ticketSystem.increaseSeatAvailability(source, destination, seats);
           WaitingListManager waitingListManager=new WaitingListManager();
           waitingListManager.processWaitingList();
        }
        else{
            System.out.println("Ticket with pnr "+pnr+" not found");
        }
    }

    protected void execute(){
        this.cancelTicket();
    }
}
