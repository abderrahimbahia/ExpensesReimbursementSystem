package data;

import entity.Employee;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements IEmployeeDao {

    Connection connection;
    public EmployeeDaoImpl()
    {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public Employee insert(Employee employee) {
        String query = "insert into employees(employeeID, firstName, lastName, userName, password, email, phone, position, street, city, state, zipcode, managerID) values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try
        {
            PreparedStatement   preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getUsername());
            preparedStatement.setString(4, employee.getPassword());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setString(7, employee.getPosition());
            preparedStatement.setString(8, employee.getStreet());
            preparedStatement.setString(9, employee.getCity());
            preparedStatement.setString(10, employee.getState());
            preparedStatement.setString(11, employee.getZipCode());
            preparedStatement.setInt(12, employee.getManagerID());

            // we use the executeUpdate method whenever we do DML operations (insert, update, delete):
            // going to return the number of records that were updated (or in this case inserted)
            int count = preparedStatement.executeUpdate();

            if(count == 1)
            {
                System.out.println("Employee inserted successfully!");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int generatedID = resultSet.getInt(1);
                employee.setEmployeeID(generatedID);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Something went wrong when preparing the statement!");
        }
        return employee;
    }

    @Override
    public Employee getByID(int id) {
        String query = "select * from employees where employeeid = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            //stores the return value of the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int    employeeID         = resultSet.getInt("employeeid");
                String firstName          = resultSet.getString("firstname");
                String lastName           = resultSet.getString("lastname");
                String username           = resultSet.getString("username");
                String password           = resultSet.getString("password");
                String email              = resultSet.getString("email");
                String phone              = resultSet.getString("phone");
                String position           = resultSet.getString("position");
                String street             = resultSet.getString("street");
                String city               = resultSet.getString("city");
                String state              = resultSet.getString("state");
                String zipcode            = resultSet.getString("zipcode");
                int    managerID          = resultSet.getInt("managerid");

                return new Employee(employeeID, firstName, lastName, username, password, email, phone, position, street, city, state, zipcode, managerID);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong while retrieving data!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getByEmail(String employeeEmail) {
        String query = "select * from employees where email = ?;";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeEmail);

            //stores the return value of the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("employeeID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String position = resultSet.getString("position");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zipcode = resultSet.getString("zipcode");
                int managerID = resultSet.getInt("managerid");

                return new Employee(id, firstName, lastName, userName, password, email, phone, position, street, city, state, zipcode, managerID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong when trying to retrieve data!");
        }
        return null;
    }

    @Override
    public Employee getByUsername(String username) {
        String query = "select * from employees where username = ?;";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            //stores the return value of the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("employeeID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String position = resultSet.getString("position");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zipcode = resultSet.getString("zipcode");
                int managerID = resultSet.getInt("managerid");

                return new Employee(id, firstName, lastName, userName, password, email, phone, position, street, city, state, zipcode, managerID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong when trying to retrieve data!");
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        //List to store employee objects
        List<Employee> employees = new ArrayList<>();

        //query to execute
        String query = "select * from employees;";
        try
        {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            //get employees from our database
            while(resultSet.next())
            {
                int id           = resultSet.getInt("employeeID");
                String firstName = resultSet.getString("firstName");
                String lastName  = resultSet.getString("lastName");
                String userName  = resultSet.getString("userName");
                String password  = resultSet.getString("password");
                String email     = resultSet.getString("email");
                String position  = resultSet.getString("position");
                String street    = resultSet.getString("street");
                String city      = resultSet.getString("city");
                String state     = resultSet.getString("state");
                String phone     = resultSet.getString("phone");
                String zipcode   = resultSet.getString("zipcode");
                int managerID    = resultSet.getInt("managerid");

                //create employee object
                Employee employee = new Employee(id, firstName, lastName, userName, password, email, position, street, city, state, phone, zipcode, managerID);

                //add an employee
                employees.add(employee);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong when trying to retrieve data!");
        }
        return employees;
    }

    @Override
    public Employee update(Employee employee) {
        String query ="update employees set " +
                "firstname = ?, lastname = ?, username = ?, password = ?, email = ?, phone = ?, position = ?, street = ?, city = ?, state = ?, zipcode = ?, managerid = ?" +
                "where employeeid = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getUsername());
            preparedStatement.setString(4, employee.getPassword());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setString(7, employee.getPosition());
            preparedStatement.setString(8, employee.getStreet());
            preparedStatement.setString(9, employee.getCity());
            preparedStatement.setString(10, employee.getState());
            preparedStatement.setString(11, employee.getZipCode());
            preparedStatement.setInt(12, employee.getManagerID());
            preparedStatement.setInt(13, employee.getEmployeeID());

            int count = preparedStatement.executeUpdate();
            if(count == 1)
            {
                System.out.println("Update employee successful!");
                return employee;
            }
            else
            {
                System.out.println("Something went wrong with the update!");
                if(count == 0)
                {
                    System.out.println("No rows were affected!");
                }
                else
                {
                    System.out.println("Many rows were affected!");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
        return  null;
    }

    @Override
    public void addProfileImage(int id, String address) {
        String query = "update employees set picture = ? where employeeid = ?;";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            FileInputStream fileInputStream = new FileInputStream(address);

            preparedStatement.setBinaryStream(1, fileInputStream, fileInputStream.available());
            preparedStatement.setInt(2, id);
            
            int count = preparedStatement.executeUpdate();

            if(count == 1)
                System.out.println("Added profile picture successful!");
            else
                System.out.println("Something went wrong with the update!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went Wrong while inserting the picture!");
        }
    }

    //to be edited
    @Override
    public void changePassword(int id, String newPassword)
    {
        String query = "update employees set password = ? where employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1)
                System.out.println("Updated employee password successfully!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }

    @Override
    public void updatePosition(int id, String position)
    {
        String query = "update employees set position = ? where employeeid = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, position);
            preparedStatement.setInt(2, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1)
                System.out.println("Updated employee position successfully!");
        }
    catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "delete from employees where employeeid = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Deletion completed successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while deleting the Employee");
        }
        return false;
    }
}
