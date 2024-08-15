import java.util.Map;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        while ((true)) {
            System.out.println("1.Book Ticket\n 2.Cancel Ticket\n 3.Print Chart\n Choose any one from the given option:");
            Scanner sc=new Scanner(System.in);
            int option=sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the source:");
                    char source=sc.next().charAt(0);
                    System.out.println("Enter the destination:");
                    char destination=sc.next().charAt(0);
                    System.out.println("Enter the number of seats to Book:");
                    int seats=sc.nextInt();
                    if(source!=destination){
                        TicketBooking booking=new TicketBooking(source, destination, seats);
                        booking.execute();
                    }
                    else{
                        System.out.println("I handeled this scenario.Try Again!");
                    }
                    break;
                case 2:
                    System.out.println("Enter the pnr Number:");
                    int pnr=sc.nextInt();
                    System.out.println("Enter the number of seats you want to cancel:");
                    int Seats=sc.nextInt();
                    TicketCanceling canceling=new TicketCanceling(pnr, Seats);
                    canceling.execute();
                    break;
                case 3:
                    TicketSystem.getInstance().printChart();
                    break;    
                default:
                    System.out.println("Unfortunately Stopped!!");
                    break;
            }
        }
    }
}
