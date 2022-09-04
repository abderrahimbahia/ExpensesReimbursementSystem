package controller;

import entity.Employee;
import entity.Ticket;
import service.EmployeeService;
import service.TicketService;

import java.util.List;
import java.util.Scanner;

public class TicketCommandLineInterface {

    public static void Main(Employee currentEmployee)
    {
        TicketService   ticketService   = new TicketService();
        EmployeeService employeeService = new EmployeeService();

        //Create a scanner object
        Scanner intScanner    = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);

        try
        {
            while(currentEmployee != null)
            {
                boolean displayTicketMenu = true;
                while(displayTicketMenu)
                {
                    TicktsMenu();
                    int choice = intScanner.nextInt();
                    switch(choice)
                    {
                        case 1 -> {
                            if(currentEmployee.getPosition().equalsIgnoreCase("manager"))
                            {
                                boolean displayManagerDisplayMenu = true;
                                while(displayManagerDisplayMenu)
                                {
                                    ManagerDisplayMenu();
                                    int choice1 = intScanner.nextInt();
                                    switch (choice1)
                                    {
                                        case 1 -> {

                                            System.out.println("TicketID");
                                            int id2 = intScanner.nextInt();

                                            Ticket ticket2 = ticketService.getByTicketID(id2);
                                            if(ticket2 != null)
                                                System.out.println(ticket2.toString());
                                        }
                                        case 2 -> {
                                            System.out.println("Employee id: ");
                                            int id3 = intScanner.nextInt();

                                            List<Ticket> allEmployeeTickets = ticketService.displayByOwnerID(id3);

                                            if(allEmployeeTickets != null)
                                            {
                                                for(Ticket ticket : allEmployeeTickets)
                                                {
                                                    System.out.println(ticket.toString());
                                                }
                                            }
                                        }
                                        case 3 -> {
                                            List<Ticket> allTickets = ticketService.displayAll();
                                            if(allTickets != null)
                                            {
                                                for(Ticket ticket : allTickets)
                                                {
                                                    System.out.println(ticket.toString());
                                                }
                                            }
                                        }
                                        case 4 -> displayManagerDisplayMenu = false;
                                        default -> System.out.println("Invalid choice, please try again!");
                                    }
                                }
                            }
                            else
                            {
                                boolean displayEmployeeDisplayMenu = true;
                                while(displayEmployeeDisplayMenu)
                                {
                                    EmployeeDisplayMenu();
                                    int choice2 = intScanner.nextInt();

                                    switch (choice2)
                                    {
                                        case 1 -> {
                                            System.out.println("TicketID");
                                            int ticketID = intScanner.nextInt();

                                            Ticket ticket1 = ticketService.getByTicketID(ticketID);
                                            if(ticket1 != null)
                                                System.out.println(ticket1.toString());
                                        }
                                        case 2 -> {
                                            System.out.println("Ticket Status: ");
                                            String status = stringScanner.nextLine();

                                            List<Ticket> ticketsWithSameStatus = ticketService.displayByStatus(currentEmployee.getEmployeeID(), status);
                                            if(ticketsWithSameStatus != null)
                                            {
                                                for(Ticket ticket : ticketsWithSameStatus)
                                                {
                                                    System.out.println(ticket.toString());
                                                }
                                            }
                                        }
                                        case 3 -> {
                                            System.out.println("Ticket Type: ");
                                            String type = stringScanner.nextLine();

                                            List<Ticket> ticketsWithSameType = ticketService.displayByType(currentEmployee.getEmployeeID(), type);
                                            if(ticketsWithSameType != null)
                                            {
                                                for(Ticket ticket : ticketsWithSameType)
                                                {
                                                    System.out.println(ticket.toString());
                                                }
                                            }
                                        }
                                        case 4 -> {
                                            List<Ticket> allEmployeeTickets = ticketService.displayByOwnerID(currentEmployee.getEmployeeID());
                                            if(allEmployeeTickets != null)
                                            {
                                                for(Ticket ticket : allEmployeeTickets)
                                                {
                                                    System.out.println(ticket.toString());
                                                }
                                            }
                                        }
                                        case 5 -> displayEmployeeDisplayMenu = false;
                                        default -> System.out.println("Invalid choice, please try again!");
                                    }
                                }
                            }
                        }
                        case 2 -> {
                            if(currentEmployee.getPosition().equalsIgnoreCase("manager"))
                            {
                                boolean displayManagerEditMenu = true;
                                while(displayManagerEditMenu)
                                {
                                    ManagerEditMenu();
                                    int choice1 = intScanner.nextInt();
                                    switch (choice1)
                                    {
                                        case 1 -> {

                                            System.out.println("Ticket ID: ");
                                            int ticketID = intScanner.nextInt();

                                            System.out.println("New Status: ");
                                            String newStatus = stringScanner.nextLine();

                                            ticketService.updateStatus(ticketID, newStatus);
                                        }
                                        case 2 -> {
                                            System.out.println("Ticket ID: ");
                                            int ticketID1 = intScanner.nextInt();

                                            ticketService.delete(ticketID1);
                                        }
                                        case 3 -> displayManagerEditMenu = false;
                                        default -> System.out.println("invalid choice, please try again!");
                                    }
                                }
                            }
                            else
                            {
                                boolean displayEmployeeEditMenu = true;
                                while(displayEmployeeEditMenu)
                                {
                                    EmployeeEditMenu();
                                    int choice2 = intScanner.nextInt();

                                    switch (choice2)
                                    {
                                        case 1 -> {
                                            System.out.println("Ticket type: ");
                                            String type = stringScanner.nextLine();

                                            System.out.println("ticket amount: ");
                                            double amount = intScanner.nextDouble();

                                            System.out.println("Ticket Description: ");
                                            String description = stringScanner.nextLine();

                                            Ticket ticket = new Ticket(type, amount, description);
                                            ticketService.add(currentEmployee.getEmployeeID(), ticket);
                                        }
                                        case 2 ->
                                        {
                                            System.out.println("Ticket ID: ");
                                            int ticketID = intScanner.nextInt();

                                            System.out.println("Ticket type: ");
                                            String newType = stringScanner.nextLine();

                                            System.out.println("ticket amount: ");
                                            double newAmount = intScanner.nextDouble();

                                            System.out.println("Ticket Description: ");
                                            String NewDescription = stringScanner.nextLine();

                                            Ticket updatedTicket = new Ticket(ticketID, currentEmployee.getEmployeeID(), newType, newAmount, NewDescription);
                                            ticketService.update(currentEmployee.getEmployeeID(), updatedTicket);
                                        }
                                        case 3 ->
                                        {
                                            System.out.println("Ticket ID: ");
                                            int ticketID = intScanner.nextInt();

                                            System.out.println("Receipt Image Address: ");
                                            String address = stringScanner.nextLine();

                                            ticketService.addReceiptImage(ticketID, address);

                                        }
                                        case 4 -> {
                                            System.out.println("Ticket ID: ");
                                            int ticketID1 = intScanner.nextInt();

                                            ticketService.delete(ticketID1);
                                        }
                                        case 5 -> displayEmployeeEditMenu = false;
                                        default -> System.out.println("invalid choice, please try again!");
                                    }
                                }
                            }
                        }
                        case 3 -> {
                            currentEmployee = null;
                            displayTicketMenu = false;
                        }
                        default -> System.out.println("Invalid choice, please try again!");
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Something went Wrong!");
        }
    }


    public static void TicktsMenu()
    {
        System.out.println("1 - Display tickets");
        System.out.println("2 - Edit tickets");
        System.out.println("3 - Sign out");
    }

    public static void ManagerDisplayMenu()
    {
        System.out.println("1 - ticket by ticket id");
        System.out.println("2 - tickets by employee id");
        System.out.println("3 - All employees tickets");
        System.out.println("4 - Previous page");
    }


    public static void EmployeeDisplayMenu()
    {
        System.out.println("1 - ticket by id");
        System.out.println("2 - tickets by status");
        System.out.println("3 - tickets by type");
        System.out.println("4 - all employee tickets");
        System.out.println("5 - Previous page");
    }

    public static void ManagerEditMenu()
    {
        System.out.println("1 - Process tickets");
        System.out.println("2 - Delete Ticket");
        System.out.println("3 - Previous Page");
    }

    public static void EmployeeEditMenu()
    {
        System.out.println("1 - Add a ticket");
        System.out.println("2 - Edit a ticket");
        System.out.println("3 - Add a ticket picture");
        System.out.println("4 - Delete a ticket");
        System.out.println("5 - Previous Page");
    }
}
