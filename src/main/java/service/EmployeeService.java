package service;

import controller.VerificationCodeFactory;
import data.DaoFactory;
import data.EmployeeDaoImpl;
import data.IEmployeeDao;
import data.ITicketDao;
import entity.Employee;
import entity.Ticket;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    public Employee register(Employee employee)
    {
        IEmployeeDao    employeeDao = DaoFactory.getEmployeeDao();
        List<Employee>  employees   = employeeDao.getAllEmployees();

        if(employees.contains(employee))
        {
            System.out.println("Employee already exist, please check with HR");
            return null;
        }
        else
        {
            if(employeeDao.getByUsername(employee.getUsername()) != null) {
                System.out.println("Username already used!");
                return null;
            }
            else
            {
                Employee newEmployee = employeeDao.insert(employee);
                System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " is registered successfully!");
                return newEmployee;
            }
        }
    }

    public Employee login(String username, String password)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee newEmployee = employeeDao.getByUsername(username);

        if(newEmployee == null)
        {
            System.out.println("Invalid username, please try again!");
            return null;
        }
        else
        {
            if(!password.equals(newEmployee.getPassword()))
            {
                System.out.println("Invalid password, please try again!");
                return null;
            }
            else
            {
                System.out.println("logged in successfully!");
                return newEmployee;
            }
        }
    }

    public void update(Employee employee)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        if(employeeDao.getByID(employee.getEmployeeID()) != null)
        {
            employeeDao.update(employee);
            System.out.println("Employee is updated successfully!");
        }
        else
        {
            System.out.println("Employee doesn't exist, please try again");
        }
    }

    public void updatePosition(int id, String position)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getByID(id);

        if(employee == null)
            System.out.println("Employee with " + id + " doesn't exist, Please try again!");
        else
            if(!position.equalsIgnoreCase("manager") && !position.equalsIgnoreCase("employee"))
                System.out.println("Invalid Employee Position, Please select either 'Employee' or 'Manager'");
            else{
                employeeDao.updatePosition(id, position);
                System.out.println("Employee position is updated successfully!");
            }
    }

    public void updatePassword(int id, String password)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        employeeDao.changePassword(id, password);
    }

    public Employee getById(int id)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getByID(id);

        if(employee == null){
            System.out.println("Employee with " + id + " doesn't exist, Please try again!");
            return null;
        }
        else
            return employee;
    }

    public Employee getByEmail(String email)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee        = employeeDao.getByEmail(email);
        return employee;
    }

    public List<Employee> getAllEmployees()
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.getAllEmployees();
    }

    public void addProfilePicture(int employeeID, String imageAddress)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee     employee    = employeeDao.getByID(employeeID);

        if(employee != null)
        {
            employeeDao.addProfileImage(employeeID, imageAddress);
        }
        else
            System.out.println("Employee doesn't exist");
    }

    public void delete(int id)
    {
        IEmployeeDao employeeDao = DaoFactory.getEmployeeDao();

        if(employeeDao.getByID(id) != null)
        {
            employeeDao.delete(id);
        }
        else
            System.out.println("Employee with " + id + " doesn't exist, Please try again!");
    }
}
