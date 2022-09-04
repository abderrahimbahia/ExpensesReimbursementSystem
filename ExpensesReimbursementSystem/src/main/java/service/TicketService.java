package service;

import data.DaoFactory;
import data.IEmployeeDao;
import data.ITicketDao;
import entity.Employee;
import entity.Ticket;

import java.util.List;

public class TicketService {

    public Ticket add(int employeeID, Ticket ticket)
    {
        ITicketDao   ticketDao      = DaoFactory.getTicketDao();
        IEmployeeDao employeeDao    = DaoFactory.getEmployeeDao();

        Employee employee           = employeeDao.getByID(employeeID);

        if(employee.getPosition().equalsIgnoreCase("employee"))
        {
            ticket.setEmployeeID(employeeID);
            return ticketDao.insert(ticket);
        }
        else
        {
            System.out.println("Sorry, Managers can't add tickets!");
            return null;
        }
    }

    public Ticket getByTicketID(int ticketID)
    {
        ITicketDao ticketDao        = DaoFactory.getTicketDao();
        Ticket   ticket             = ticketDao.getByTicketID(ticketID);

        if(ticket == null)
        {
            System.out.println("no ticket with " + ticketID + " id!");
            return null;
        }
        else
            return ticket;
    }

    public List<Ticket> displayByOwnerID(int employeeID)
    {
        ITicketDao   ticketDao      = DaoFactory.getTicketDao();
        IEmployeeDao employeeDao    = DaoFactory.getEmployeeDao();

        Employee      employee        = employeeDao.getByID(employeeID);

        if(employee != null && (employee.getPosition().equalsIgnoreCase("manager") || employee.getEmployeeID() == employeeID))
        {
            List<Ticket> tickets = ticketDao.getByEmployeeID(employeeID);
            if(!tickets.isEmpty())
                return tickets;
            else
            {
                System.out.println("Employee with id " + employeeID + " doesn't have any tickets!");
                return null;
            }
        }
        else
        {
            System.out.println("Employee doesn't exist, or you need the right privileges to access the request data!");
            return null;
        }

    }

    public List<Ticket> displayByStatus(int employeeID, String ticketStatus)
    {
        ITicketDao      ticketDao      = DaoFactory.getTicketDao();
        IEmployeeDao    employeeDao    = DaoFactory.getEmployeeDao();

        if(employeeDao.getByID(employeeID) != null && employeeDao.getByID(employeeID).getPosition().equalsIgnoreCase("employee"))
        {
            List<Ticket> tickets = ticketDao.getByStatus(employeeID, ticketStatus);
            if(!tickets.isEmpty())
                    return tickets;
            else
            {
                System.out.println("Employee with id " + employeeID + " doesn't have any " + ticketStatus + " tickets!");
                return null;
            }
        }else
        {
            System.out.println("Employee with id " + employeeID + " doesn't exist  or position is not employee");
            return null;
        }
    }

    public List<Ticket> displayByType(int employeeID, String ticketType)
    {
        ITicketDao      ticketDao      = DaoFactory.getTicketDao();
        IEmployeeDao    employeeDao    = DaoFactory.getEmployeeDao();

        if(employeeDao.getByID(employeeID) != null && employeeDao.getByID(employeeID).getPosition().equalsIgnoreCase("employee"))
        {
            List<Ticket> tickets = ticketDao.getByType(employeeID, ticketType);
            if(!tickets.isEmpty())
                return tickets;
            else
            {
                System.out.println("Employee with id " + employeeID + " doesn't have any " + ticketType + " tickets!");
                return null;
            }
        }else
        {
            System.out.println("Employee with id " + employeeID + " doesn't exist  or position is not employee");
            return null;
        }
    }

    public List<Ticket> displayAll()
    {
        ITicketDao ticketDao = DaoFactory.getTicketDao();
        List<Ticket> tickets = ticketDao.getAllTickets();

        if(!tickets.isEmpty())
            return tickets;
        else
        {
            System.out.println("No tickets submitted yet!");
            return null;
        }
    }

    public void update(int employeeID, Ticket ticket)
    {
        ITicketDao      ticketDao   = DaoFactory.getTicketDao();
        IEmployeeDao    employeeDao = DaoFactory.getEmployeeDao();

        if (employeeDao.getByID(employeeID) != null)
            if(ticketDao.getByTicketID(ticket.getTicketID()) != null && ticketDao.getByTicketID(ticket.getTicketID()).getStatus().equalsIgnoreCase("pending"))
                ticketDao.update(ticket);
            else
                System.out.println("Ticket doesn't exist, or ticket is already processed!");
        else
            System.out.println("Invalid employee id");
    }

    public void updateStatus(int ticketID, String status)
    {
        ITicketDao      ticketDao   = DaoFactory.getTicketDao();
        Ticket ticket = ticketDao.getByTicketID(ticketID);

        if(ticket != null && ticket.getStatus().equalsIgnoreCase("pending"))
            if(status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("approved"))
            {
                ticketDao.updateStatus(ticketID, status);
                System.out.println("Ticket status updated successfully!");
            }
            else
                System.out.println("Invalid status, tickets can be approved or denied");
        else
            System.out.println("Ticket doesn't exist, or ticket is already processed!");
    }

    public void addReceiptImage(int ticketID, String imageAddress)
    {
        ITicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket      ticket   = ticketDao.getByTicketID(ticketID);

        if(ticket != null && ticket.getStatus().equalsIgnoreCase("pending"))
        {
            ticketDao.addReceiptImage(ticketID, imageAddress);
        }
        else
            System.out.println("Ticket doesn't exist, or ticket is already processed!");
    }

    public void delete(int ticketID)
    {
        ITicketDao ticketDao        = DaoFactory.getTicketDao();

        if(ticketDao.getByTicketID(ticketID) != null &&
                ticketDao.getByTicketID(ticketID).getStatus().equalsIgnoreCase("pending"))
            ticketDao.delete(ticketID);
        else
            System.out.println("Ticket doesn't exist, or ticket is already processed!");
    }
}
