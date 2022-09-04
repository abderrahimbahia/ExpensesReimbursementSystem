package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Employee;
import entity.Ticket;
import service.EmployeeService;
import service.TicketService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

public class TicketServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        EmployeeService employeeService = new EmployeeService();
        TicketService ticketService = new TicketService();

        String pathInfo = request.getQueryString();
        List<Ticket> tickets;

        int currentEmployeeID;

        try
        {
            currentEmployeeID = (Integer) request.getSession().getAttribute("id");

        }
        catch(Exception e)
        {
            response.sendError(401, "Must Login first!");
            return;
        }

        if (pathInfo == null)
        {
            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager"))
            {
                tickets = ticketService.displayAll();
                if (tickets == null)
                    out.println("No tickets available yet!");
                for(Ticket ticket: tickets)
                {
                    out.println(ticket);
                }
            }
            else {
                tickets = ticketService.displayByOwnerID(currentEmployeeID);
                if (tickets == null)
                    out.println("Employee doesn't have any tickets!");
                else
                {
                    for(Ticket ticket: tickets)
                    {
                        out.println(ticket);
                    }
                }
            }
        }
        else if(pathInfo.contains("ticketid"))
        {
            int ticketID   = Integer.parseInt(request.getParameter("ticketid"));
            int employeeID = Integer.parseInt(request.getParameter("employeeid"));

            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager") || currentEmployeeID == employeeID)
            {
                Ticket ticket = ticketService.getByTicketID(ticketID);
                out.println(ticket);
            }
            else
                out.println("only managers can see other employee's tickets!");
        }
        else if(pathInfo.contains("status"))
        {
            String status = request.getParameter("status");
            int employeeID = Integer.parseInt(request.getParameter("employeeid"));

            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager") || currentEmployeeID == employeeID)
            {
                tickets = ticketService.displayByStatus(employeeID, status);
                if (tickets == null)
                    out.println("Employee doesn't have any " + status + " tickets!");
                else
                {
                    for (Ticket ticket : tickets) {
                        out.println(ticket);
                    }
                }
            }
            else
                out.println("only managers can see other employee's tickets!");

        }
        else if(pathInfo.contains("type"))
        {
            String type = request.getParameter("type");
            int employeeID = Integer.parseInt(request.getParameter("employeeid"));

            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager") || currentEmployeeID == employeeID)
            {
                tickets = ticketService.displayByType(employeeID, type);
                if (tickets == null)
                    out.println("Employee doesn't have any " + type + " tickets!");
                else
                {
                    for (Ticket ticket : tickets) {
                        out.println(ticket);
                    }
                }
            }
            else
                out.println("only managers can see other employee's tickets!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter                 out = response.getWriter();
        EmployeeService employeeService = new EmployeeService();
        TicketService   ticketService   = new TicketService();

        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee;
        Ticket   ticket;

        int currentEmployeeID;
        try
        {
            currentEmployeeID = (Integer) request.getSession().getAttribute("id");

        }catch(Exception e)
        {
            response.sendError(401, "Must login first!");
            return;
        }

        try
        {
            ticket = objectMapper.readValue(request.getReader(), Ticket.class);
        }
        catch(Exception e)
        {
            e.getStackTrace();
            response.sendError(400, "Invalid Ticket Format!");
            return;
        }

        try
        {
            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager"))
                out.println("Only Employees can add Tickets");
            else
            {
                if( ticket.getAmount() > 0.0 && ticket.getDescription() != null)
                {
                    String type = ticket.getType();
                    if(type.equalsIgnoreCase("travel") || type.equalsIgnoreCase("lodging") || type.equalsIgnoreCase("food") || type.equalsIgnoreCase("other"))
                    {
                        ticketService.add(currentEmployeeID, ticket);
                        out.println("Ticket was inserted successfully!");
                    }else
                        out.println("Invalid type, type can be either travel, lodging, food, or other!");
                }
                else
                    out.println("Must include both a valid price and description!");
            }
        }
        catch(Exception e)
        {
            e.getStackTrace();
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter             out = response.getWriter();
        TicketService ticketService = new TicketService();
        EmployeeService employeeService = new EmployeeService();

        int currentEmployeeID, ticketID; // employeeID is the id of employee we're trying to update
        Ticket ticket;
        String status, address;
        String pathInfo = request.getQueryString();

        try
        {
            currentEmployeeID = (Integer) request.getSession().getAttribute("id");
        }
        catch(Exception e)
        {
            response.sendError(401, "Must Login first!");
            return;
        }

        try
        {
            ticketID = Integer.parseInt(request.getParameter("ticketid"));
            ticket = ticketService.getByTicketID(ticketID);
        }
        catch(Exception e)
        {
            response.sendError(400, "Must include ticket id!");
            return;
        }

        try
        {
            if(pathInfo.contains("address"))
            {
                address = request.getParameter("address");
                if(!employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager")) {
                    if(ticket.getEmployeeID() == currentEmployeeID)
                    {
                        if(ticket.getStatus().equalsIgnoreCase("pending"))
                        {
                            ticketService.addReceiptImage(ticketID, address);
                        }
                        else
                            out.print("Ticket is already processed!");
                    }
                    else
                        out.println("You can't edit other employee's tickets");
                }
                else
                    out.println("Only employees can add pictures to their tickets!");
            }
            else
            {
                status  = request.getParameter("status");
                if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager")) {
                    if(!status.isEmpty())
                    {
                        if(ticket.getStatus().equalsIgnoreCase("pending"))
                            if(status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("approved"))
                            {
                                ticketService.updateStatus(ticketID, status);
                                out.println("Ticket status updated successfully!");
                            }
                            else
                                out.println("Invalid status, tickets can be approved or denied");
                        else
                            out.print("Ticket is already processed!");
                    }
                }
                else
                    out.println("Only managers can update ticket status!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendError(400, "Make sure you included ticket new status!");
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        TicketService ticketService     = new TicketService();
        EmployeeService employeeService = new EmployeeService();

        int currentEmployeeID, ticketID;
        Ticket ticket;

        try
        {
            currentEmployeeID = (Integer) request.getSession().getAttribute("id");
        }
        catch(Exception e)
        {
            response.sendError(401, "Must Login first!");
            return;
        }

        try
        {
            ticketID = Integer.parseInt(request.getParameter("ticketid"));
            ticket = ticketService.getByTicketID(ticketID);
        }
        catch(Exception e)
        {
            response.sendError(400, "Must include Ticket id");
            return;
        }

        try
        {
            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager") || ticket.getEmployeeID() == currentEmployeeID) {
                if(ticket != null && ticket.getStatus().equalsIgnoreCase("pending"))
                {
                    out.println("Ticket with id " + ticketID + " was deleted successfully!");
                    ticketService.delete(ticketID);
                }
                else
                    out.print("Ticket doesn't exist, or ticket is already processed!");
            }
            else
                out.println("Only manager or ticket owner can delete tickets");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendError(400, "Make sure Ticket with id " + ticketID + " exist!");
            return;
        }
    }

}
