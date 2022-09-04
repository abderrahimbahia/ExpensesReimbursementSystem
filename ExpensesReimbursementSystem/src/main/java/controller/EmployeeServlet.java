package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Employee;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        EmployeeService employeeService = new EmployeeService();

        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee;

        try
        {
            employee = objectMapper.readValue(request.getReader(), Employee.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendError(400, "Invalid Employee Format!");
            return;
        }

        String authType = request.getParameter("auth");
        if(authType.equals("login"))
        {
            employee = employeeService.login(employee.getUsername(), employee.getPassword());
            if(employee == null) {
                out.println("Invalid user name or password!");
                return;
            }
            else
                out.println(employee.getFirstName() + " " + employee.getLastName() + " Logged in successfully!");

        }else if(authType.equals("register"))
        {
            Employee newEmployee = employeeService.register(employee);
            if(newEmployee != null)
                out.println(employee.getFirstName() + " " + employee.getLastName() + " Registered in successfully!");
            else
                out.println("Unable to register employee!");
        }

        if(employee == null)
        {
            response.sendError(400, "Invalid Username or Password!");
            return;
        }

        request.getSession().setAttribute("id", employee.getEmployeeID());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EmployeeService employeeService = new EmployeeService();

        int currentEmployeeID;
        try
        {
            currentEmployeeID = (Integer) request.getSession().getAttribute("id");

        }catch(Exception e)
        {
            response.sendError(401, "Must login first!");
            return;
        }

        Employee current = employeeService.getById(currentEmployeeID);

        String pathInfo = request.getQueryString();
        if (pathInfo == null)
        {
            if(current.getPosition().equalsIgnoreCase("manager"))
            {
                List<Employee> employees = employeeService.getAllEmployees();
                for(Employee employee: employees)
                {
                    out.println(employee);
                }
            }
            else
                out.println("Only managers can see all employees data!");
        }else
        {
            int employeeID = Integer.parseInt((request.getParameter("id"))); // employeeID is the id of employee we're trying to update
            Employee employee   = employeeService.getById(employeeID);

            if(current.getPosition().equalsIgnoreCase("manager") || currentEmployeeID == employeeID)
                out.println(employee);
            else
                out.println("Only managers can see other employees data!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EmployeeService employeeService = new EmployeeService();

        int currentEmployeeID, employeeID; // employeeID is the id of employee we're trying to update
        String position = null, image = null, pathInfo = request.getQueryString(), str = "";

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
            employeeID = Integer.parseInt(request.getParameter("id"));
            if(pathInfo.contains("image"))
            {
                image = request.getParameter("image");
                str = "profile image";
            }
            else
            {
                position   = request.getParameter("position");
                str = "position";
            }
        }
        catch(Exception e)
        {
            response.sendError(400, "Must include both employee id and employee " + str);
            return;
        }

        try
        {
            if(pathInfo.contains("image"))
            {
                if(currentEmployeeID == employeeID) {
                    employeeService.addProfilePicture(currentEmployeeID, image);
                    out.println("Profile image updated successfully");
                }
                else
                    out.println("Can only update your own profile picture!");
            }
            else
            {
                if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager")) {
                    employeeService.updatePosition(employeeID, position);
                    out.println("Employee " + employeeService.getById(employeeID).getFirstName() + " " + employeeService.getById(employeeID).getLastName() +
                            " position is updated successfully!");
                }
                else
                    out.println("Only managers can update other employee's position!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendError(400, "Make sure Employee with id " + employeeID + " exist!");
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EmployeeService employeeService = new EmployeeService();

        int currentEmployeeID, employeeID; // employeeID is the id of employee we're trying to update

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
            employeeID = Integer.parseInt(request.getParameter("id"));
        }
        catch(Exception e)
        {
            response.sendError(400, "Must include employee id");
            return;
        }

        try
        {
            if(employeeService.getById(currentEmployeeID).getPosition().equalsIgnoreCase("manager")) {
                out.println("Employee " + employeeService.getById(employeeID).getFirstName() + " " + employeeService.getById(employeeID).getLastName() +
                        " was deleted successfully!");
                employeeService.delete(employeeID);
            }
            else
                out.println("Only managers can update other employee's position!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.sendError(400, "Make sure Employee with id " + employeeID + " exist!");
            return;
        }
    }
}
