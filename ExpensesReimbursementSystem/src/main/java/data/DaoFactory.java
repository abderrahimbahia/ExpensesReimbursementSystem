package data;

public class DaoFactory {
    private static IEmployeeDao employeeDao;
    private static ITicketDao ticketDap;

    private DaoFactory(){}

    public static IEmployeeDao getEmployeeDao()
    {
        if(employeeDao == null)
        {
            employeeDao = new EmployeeDaoImpl();
        }
        return employeeDao;
    }

    public static ITicketDao getTicketDao()
    {
        if( ticketDap == null)
        {
            ticketDap = new TicketDaoImpl();
        }
        return ticketDap;
    }
}
