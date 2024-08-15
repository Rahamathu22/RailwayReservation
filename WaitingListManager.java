public class WaitingListManager {
    private TicketSystem ticketSystem;
    WaitingListManager(){
        this.ticketSystem=TicketSystem.getInstance();
    }
    protected void processWaitingList(){
        for(Ticket waiting : ticketSystem.waitingList.values()){
            boolean isValid;
            char source=waiting.getSource(),destination=waiting.getDestination();
            int seats=waiting.getSeats();
            isValid=ticketSystem.checkSeatsAvailability(source, destination, seats);
            if(isValid){
                ticketSystem.decreaseSeatAvailability(source, destination, seats);
                ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked()-seats);
                updateTicketToBookingList(waiting);
            }
        }
    }
    private void updateTicketToBookingList(Ticket waiting){
        int pnrNumber=waiting.getPnrNumber();
        waiting.setTicketStatus(TicketStatus.Booked);
        ticketSystem.ticketsBooked.put(pnrNumber, waiting);
        ticketSystem.waitingList.remove(pnrNumber);
        System.out.println("Booking confrimed for pnr Number:"+pnrNumber);
    }

    protected void waitingListEntry(char source,char destination,int seats){
        WaitingList wl=new WaitingList(source, destination, seats);
        wl.execute();
    }
}
