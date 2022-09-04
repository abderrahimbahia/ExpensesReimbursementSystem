//Program Name: ExpensesReimbursementSystem
//Programmer  : Abderrahim Bahia
//Last Update : 08/13/2022

package entity;

import java.util.Arrays;

public class Ticket {

    private int     ticketID;
    private int     employeeID;
    private String  status = "pending";
    private String  type;
    private double  amount;
    private String  description;

    private byte [] picture;


    public Ticket()
    {}

    public Ticket(int ticketID, int employeeID, String status, String type, double amount, String description, byte [] picture) {
        this.ticketID       = ticketID;
        this.employeeID     = employeeID;
        this.status         = status;
        this.type           = type;
        this.amount         = amount;
        this.description    = description;
        this.picture        = picture;
    }

    public Ticket(int ticketID, int employeeID, String status, String type, double amount, String description) {
        this.ticketID       = ticketID;
        this.employeeID     = employeeID;
        this.status         = status;
        this.type           = type;
        this.amount         = amount;
        this.description    = description;
    }

    public Ticket(int ticketID, int employeeID, String type, double amount, String description) {
        this.ticketID       = ticketID;
        this.employeeID     = employeeID;
        this.type           = type;
        this.amount         = amount;
        this.description    = description;
    }

    public Ticket(int employeeID,String type, double amount, String description) {
        this.employeeID = employeeID;
        this.type           = type;
        this.amount         = amount;
        this.description    = description;
    }

    public Ticket(String type, double amount, String description) {
        this.type           = type;
        this.amount         = amount;
        this.description    = description;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID)
    {
        this.ticketID = ticketID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status)
    {
        if(this.status.equalsIgnoreCase("approved") || this.status.equalsIgnoreCase("denied"))
        {
            System.out.println("Ticket status can't be changed, It's already processed");
        }
        else
        {
            if(!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("denied"))
            {
                System.out.println("Invalid Ticket Status, Please enter approved or denied!");
            }
            else
            {
                this.status = status;
            }
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type)
    {
        String [] types = {"travel", "lodging", "food", "other"};
        if(Arrays.stream(types).noneMatch(type::contains))
        {
            System.out.println("Invalid type, please set it to either Travel, Lodging, Food, or other");
        }
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount)
    {
        if(amount < 0)
        {
            System.out.println("Ticket amount can't be negative!");
            return;
        }

        this.amount = (double)amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", status='" + status + '\'' +
                ", type= '" + type + '\'' +
                ", amount=" + amount +
                ", Description='" + this.description + '\'' +
                '}';
    }
}
