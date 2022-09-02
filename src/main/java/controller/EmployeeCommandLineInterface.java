package controller;

import entity.Employee;
import service.EmployeeService;


import java.util.List;
import java.util.Scanner;

public class EmployeeCommandLineInterface {
    public static void Main(Employee currentLoggedEmployee)
    {
        EmployeeService employeeService = new EmployeeService();

        //Create a scanner object
        Scanner intScanner    = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);

        try {

            while(currentLoggedEmployee != null)
            {
                HomeMenu();

                int choice2 = intScanner.nextInt();
                switch(choice2)
                {
                    case 1 ->
                    {
                        boolean displayProfileMenu = true;
                        while(displayProfileMenu)
                        {
                            ProfileMenu();

                            int choice3 = intScanner.nextInt();
                            switch(choice3)
                            {
                                case 1 ->
                                {
                                    if(currentLoggedEmployee.getPosition().equalsIgnoreCase("manager")) {

                                        boolean displayManagerProfileMenu = true;
                                        while (displayManagerProfileMenu) {

                                            ManagerProfileMenu();

                                            int choice4 = intScanner.nextInt();
                                            switch (choice4) {
                                                case 1 -> System.out.println(currentLoggedEmployee);
                                                case 2 -> {
                                                    List<Employee> currentEmployees = employeeService.getAllEmployees();
                                                    for (Employee employee : currentEmployees) {
                                                        System.out.println(employee.toString());
                                                    }
                                                }
                                                case 3 -> displayManagerProfileMenu = false;
                                                default ->
                                                        System.out.println("Invalid choice, please try again!");
                                            }
                                        }
                                    }
                                    else if(currentLoggedEmployee.getPosition().equalsIgnoreCase("employee"))
                                    {
                                        System.out.println(currentLoggedEmployee);
                                    }
                                    else
                                    {
                                        System.out.println("Invalid position, please enter either 'employee' or 'manager'!");
                                    }
                                }
                                case 2 ->
                                {
                                    if(currentLoggedEmployee.getPosition().equalsIgnoreCase("manager")) {

                                        boolean displayManagerEditProfileMenu = true;
                                        while (displayManagerEditProfileMenu) {

                                            ManagerEditProfileMenu();

                                            int choice5 = intScanner.nextInt();
                                            switch (choice5) {
                                                case 1 -> {
                                                    Employee updatedEmployee = EmployeeInformation(stringScanner, intScanner);
                                                    employeeService.update(updatedEmployee);
                                                }
                                                case 2 -> {
                                                    boolean displayManagerEditEmployeeProfileMenu = true;
                                                    while (displayManagerEditEmployeeProfileMenu) {
                                                        ManagerEditEmployeeProfileMenu();
                                                        int choice6 = intScanner.nextInt();
                                                        switch (choice6) {
                                                            case 1 ->
                                                            {
                                                                System.out.println("Employee ID: ");
                                                                int id1 = intScanner.nextInt();

                                                                Employee employeeNewData = EmployeeInformation(stringScanner, intScanner);
                                                                employeeNewData.setEmployeeID(id1);
                                                                employeeService.update(employeeNewData);
                                                            }
                                                            case 2 ->
                                                            {
                                                                System.out.println("Employee ID: ");
                                                                int id2 = intScanner.nextInt();

                                                                System.out.println("Employee New Position: ");
                                                                String position = stringScanner.nextLine();

                                                                Employee employeeToUpdate2 = employeeService.getById(id2);

                                                                if (employeeToUpdate2 != null)
                                                                    employeeService.updatePosition(id2, position);
                                                            }
                                                            case 3 ->
                                                            {
                                                                System.out.println("Employee ID: ");
                                                                int employeeToDeleteID = intScanner.nextInt();

                                                                employeeService.delete(employeeToDeleteID);
                                                            }
                                                            case 4 -> displayManagerEditEmployeeProfileMenu = false;
                                                            default -> System.out.println("Invalid choice, please try again!");
                                                        }
                                                    }
                                                }
                                                default ->
                                                        System.out.println("Invalid choice, please try again!");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        Employee updatedEmployee = EmployeeInformation(stringScanner, intScanner);
                                        updatedEmployee.setEmployeeID(currentLoggedEmployee.getEmployeeID());
                                        employeeService.update(updatedEmployee);
                                    }
                                }
                                case 3 ->
                                {
                                    System.out.println("Profile Picture Address: ");
                                    String address = stringScanner.nextLine();

                                    employeeService.addProfilePicture(currentLoggedEmployee.getEmployeeID(), address);
                                }
                                case 4 -> displayProfileMenu = false;
                                default -> System.out.println("Invalid choice, please try again!");
                            }
                        }
                    }
                    case 2 ->
                    {
                        currentLoggedEmployee = null;
                    }
                    default -> System.out.println("Invalid choice, please try again!");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong!");
            System.out.println(e.getMessage());
        }
    }

    public static void HomeMenu()
    {
        System.out.println("1 - Profile");
        System.out.println("2 - Previous Page");
    }

    public static void ProfileMenu()
    {
        System.out.println("1 - View Profile");
        System.out.println("2 - Edit Profile");
        System.out.println("3 - Add Profile Picture");
        System.out.println("4 - Previous Page");
    }

    public static void ManagerProfileMenu()
    {
        System.out.println("1 - Your Profile");
        System.out.println("2 - Employees Profile");
        System.out.println("3 - Previous Menu");
    }

    public static void ManagerEditProfileMenu()
    {
        System.out.println("1 - Edit Your Profile");
        System.out.println("2 - Edit Employees Profile");
        System.out.println("3 - Previous Menu");
    }

    public static void ManagerEditEmployeeProfileMenu()
    {
        System.out.println("1 - Edit All Employee Credentials");
        System.out.println("2 - Change Employee Position");
        System.out.println("3 - Delete Employee");
        System.out.println("4 - Previous Menu");
    }

    public static Employee EmployeeInformation(Scanner stringScanner, Scanner intScanner)
    {

        System.out.println("First Name: ");
        String firstName = stringScanner.nextLine();

        System.out.println("Last Name: ");
        String lastName = stringScanner.nextLine();

        System.out.println("Username: ");
        String username = stringScanner.nextLine();

        System.out.println("Password: ");
        String password = stringScanner.nextLine();

        System.out.println("E-mail: ");
        String email = stringScanner.nextLine();

        System.out.println("Phone: ");
        String phone = stringScanner.nextLine();

        System.out.println("Street: ");
        String street = stringScanner.nextLine();

        System.out.println("City: ");
        String city = stringScanner.nextLine();

        System.out.println("State: ");
        String state = stringScanner.nextLine();

        System.out.println("Zipcode: ");
        String zipcode = stringScanner.nextLine();

        System.out.println("Manager Id: ");
        int managerID = intScanner.nextInt();

        //Create an employee object
        return new Employee(firstName, lastName, username, password, email, phone, "employee", street, city, state, zipcode, managerID);
    }
}
