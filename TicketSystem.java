import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class TicketSystem {
    protected  Map<Integer,Ticket>ticketsBooked=new HashMap<>();
    protected Map<Integer,Ticket>ticketsCanceled=new HashMap<>();
    protected ConcurrentHashMap<Integer,Ticket>waitingList=new ConcurrentHashMap<>();

    protected int[] seatsAvailable=new int[5];

    protected Map<Integer,Integer>partiallyCanceled=new HashMap<>();

    protected int seatsBooked=0;
    private static TicketSystem instance=null;

    private TicketSystem(){
        Arrays.fill(seatsAvailable,8);
    }

    public static TicketSystem getInstance(){
        if(instance==null){
            instance=new TicketSystem();
        }
        return instance;
    }

    protected int getSeatsBooked(){
        return seatsBooked;
    }

    protected void setSeatsBooked(int seatsBooked){
        this.seatsBooked=seatsBooked;
    }

    public void addToBookedTickets(int newPnr,Ticket ticket){
        ticketsBooked.put(newPnr,ticket);
    }

    protected boolean checkSeatsAvailability(char source,char destination,int seats){
        for(int i=source-'A';i<destination-'A';i++){
            if(seatsAvailable[i]<seats){
                return false;
            }
        }
        return true;
    }

    protected Ticket getTicket(int pnr){
        return ticketsBooked.get(pnr);
    }

    protected void increaseSeatAvailability(char source,char destination,int seats){
        for(int i=source-'A';i<destination-'A';i++){
            seatsAvailable[i]+=seats;
        }
    }

    protected void decreaseSeatAvailability(char source,char destination,int seats){
        for(int i=source-'A';i<destination-'A';i++){
            seatsAvailable[i]-=seats;
        }
    }

    protected void storePartiallyCanceledSeats(int pnr,int seats){
        partiallyCanceled.merge(pnr, seats, Integer::sum);
    }

    protected void processCancellation(int pnr,Ticket ticket){
        Integer getSeats=partiallyCanceled.get(pnr);
        int seatsToAdd = getSeats != null ? getSeats:0;
        ticket.setSeats(ticket.getSeats()+seatsToAdd);
        addToCanceledQueue(pnr,ticket);
    }

    protected void addToCanceledQueue(int pnr,Ticket t1){
        t1.setTicketStatus(TicketStatus.Canceled);
        ticketsCanceled.put(pnr,t1);
        removeFromBookedQueue(pnr);
    }

    protected void removeFromBookedQueue(int pnr){
        ticketsBooked.remove(pnr);
    }

    public void printChart(){
        System.out.println("\n Tickets Booked:");
        ticketsBooked.values().forEach(System.out::println);
        System.out.println("\n Tickets Cancelled:");
        ticketsCanceled.values().forEach(System.out::println);
        System.out.println("\n Tickets in waiting list:");
        waitingList.values().forEach(System.out::println);
        System.out.println("\n Seat Availability:"+ Arrays.toString(seatsAvailable));
        System.out.println("\n\t\t Seats Booked:");
        System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8");
        for(char c='A';c<='E';c++){
            System.out.print(c);
            int seatsBooked=8-seatsAvailable[c-'A'];
            for(int i=0;i<seatsBooked;i++){
                System.out.print("\t*");
            }
            System.out.println();
        }
        System.out.println();
    }
}
