package data;

import java.util.List;

import entity.Ticket;

public interface ITicketDao {

    //Create: inserting data into data base
    Ticket insert(Ticket ticket);

    Ticket getByTicketID(int ticketID);
    //Read - getting/reading data from a database
    List<Ticket> getByEmployeeID(int employeeID);

    List<Ticket> getByStatus(int employeeID, String status);

    List<Ticket> getByType(int employeeID, String type);

    List<Ticket> getAllTickets();

    //Update - update data that is already in the database
    void update(Ticket ticket);

    void updateStatus(int id, String status);

    void addReceiptImage(int id,  String address);

    //Delete - delete an item from the database
    void delete(int id);
}
