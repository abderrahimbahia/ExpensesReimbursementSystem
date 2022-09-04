package data;

import entity.Ticket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class TicketDaoImpl implements ITicketDao {

    Connection connection;

    public TicketDaoImpl()
    {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public Ticket insert(Ticket ticket) {
        String query = "insert into tickets values(default, ?, ?, ?, ?, ?);";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ticket.getStatus());
            preparedStatement.setString(2, ticket.getType());
            preparedStatement.setDouble(3, ticket.getAmount());
            preparedStatement.setString(4, ticket.getDescription());
            preparedStatement.setInt(5, ticket.getEmployeeID());

            int count = preparedStatement.executeUpdate();
            if(count == 1)
            {
                System.out.println("Ticket inserted successfully!");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int generatedID = resultSet.getInt(1);
                ticket.setTicketID(generatedID);
                return ticket;
            }
            else {
                System.out.println("Something went wrong with insert!");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("something went wrong preparing the statement!");
        }
        return null;
    }

    @Override
    public Ticket getByTicketID(int ticketID) {
        String query = "select * from tickets where ticketid = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int     ticketid     = resultSet.getInt("ticketid");
                String  status       = resultSet.getString("status");
                String  type         = resultSet.getString("type");
                double  amount       = resultSet.getDouble("amount");
                String  description  = resultSet.getString("description");
                int     employeeid   = resultSet.getInt("employeeid");

                return new Ticket(ticketid, employeeid, status, type, amount, description);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong while retrieving data!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getByEmployeeID(int id) {
        List<Ticket>    tickets  = new ArrayList<>();
        String query = "select * from tickets where employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                int    ticketid    = resultSet.getInt("ticketid");
                String status      = resultSet.getString("status");
                String type        = resultSet.getString("type");
                double amount      = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                int    employeeid  = resultSet.getInt("employeeid");

                tickets.add(new Ticket(ticketid,employeeid, status, type, amount, description));
            }

        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong trying to retrieve data!");
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getByStatus(int employeeID, String ticketStatus) {
        List<Ticket>    tickets  = new ArrayList<>();
        String query = "select * from tickets where status = ? and employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, ticketStatus);
            preparedStatement.setInt(2, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                int    ticketid    = resultSet.getInt("ticketid");
                String status      = resultSet.getString("status");
                String type        = resultSet.getString("type");
                double amount      = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                int    employeeid  = resultSet.getInt("employeeid");

                tickets.add(new Ticket(ticketid,employeeid, status, type, amount, description));
            }

        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong trying to retrieve data!");
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getByType(int employeeID, String ticketType) {
        List<Ticket>    tickets  = new ArrayList<>();
        String query = "select * from tickets where type = ? and employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, ticketType);
            preparedStatement.setInt(2, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                int    ticketid    = resultSet.getInt("ticketid");
                String status      = resultSet.getString("status");
                String type        = resultSet.getString("type");
                double amount      = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                int    employeeid  = resultSet.getInt("employeeid");

                tickets.add(new Ticket(ticketid, employeeid, status, type, amount, description));
            }

        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong trying to retrieve data!");
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket>    tickets  = new ArrayList<>();
        String query = "select * from tickets;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next())
            {
                int    ticketid    = resultSet.getInt("ticketid");
                String status      = resultSet.getString("status");
                String type        = resultSet.getString("type");
                double amount      = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                int    employeeid  = resultSet.getInt("employeeid");

                tickets.add(new Ticket(ticketid, employeeid, status, type, amount, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while retrieving data!");
        }

        return tickets;
    }

    @Override
    public void update(Ticket ticket)
    {
        String query = "update tickets set status = ?, type = ?, amount = ?, description = ?where ticketid = ? and employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, ticket.getStatus());
            preparedStatement.setString(2, ticket.getType());
            preparedStatement.setDouble(3, ticket.getAmount());
            preparedStatement.setString(4, ticket.getDescription());
            preparedStatement.setInt(5, ticket.getTicketID());
            preparedStatement.setInt(6, ticket.getEmployeeID());

            int count = preparedStatement.executeUpdate();
            if(count == 1)
            {
                System.out.println("Ticket Updated successful!");
            }
            else
            {
                System.out.println("Something went wrong with the update!");
                if(count == 0)
                {
                    System.out.println("Ticket was not affected!");
                }
                else
                {
                    System.out.println("Ticket was affected!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }

    @Override
    public void updateStatus(int id, String status) {
        String query = "update tickets set status = ? where ticketid = ?;";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);

            int count = preparedStatement.executeUpdate();
            if(count == 1)
            {
                System.out.println("Ticket Status updated successfully!");
            }
            else
            {
                System.out.println("Something Went Wrong");
                if(count == 0)
                {
                    System.out.println("Status was not affected");
                }
                else
                {
                    System.out.println("Status was affected!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went Wrong!");
        }
    }

    @Override
    public void addReceiptImage(int id, String address) {
        String query = "update tickets set picture = ? where ticketid = ?;";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            FileInputStream fileInputStream = new FileInputStream(address);

            preparedStatement.setBinaryStream(1, fileInputStream, fileInputStream.available());
            preparedStatement.setInt(2, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1)
                System.out.println("Added Receipt picture successful!");
            else
                System.out.println("Something went wrong with the update!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went Wrong while inserting the picture!");
        }
    }

    @Override
    public void delete(int id)
    {
        String query = "delete from tickets where ticketid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int count = preparedStatement.executeUpdate();
            if(count == 1)
            {
                System.out.println("Deletion completed successfully!");
            }
            else
            {
                System.out.println("Something went wrong with the delete!");
                if(count == 0)
                {
                    System.out.println("No rows were affected!");
                }
                else
                {
                    System.out.println("one row was affected!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong while deleting the ticket");
        }
    }
}
