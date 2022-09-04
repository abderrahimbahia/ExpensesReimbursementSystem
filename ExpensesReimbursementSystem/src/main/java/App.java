import controller.EmployeeCommandLineInterface;
import controller.TicketCommandLineInterface;
import controller.VerificationCodeFactory;
import entity.Employee;
import service.EmployeeService;
import service.TicketService;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        System.out.println("Welcome to Revature Expenses Reimbursement System!");

        EmployeeService employeeService = new EmployeeService();
        TicketService   ticketService   = new TicketService();

        //Create a scanner object
        Scanner intScanner    = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);

        try
        {
            boolean displayLoginPage = true;
            while(displayLoginPage)
            {
                LoginMenu();

                int choice1 = intScanner.nextInt();
                switch(choice1) {
                    case 1 -> {

                        //prompt for credentials
                        System.out.println("username: ");
                        String username = stringScanner.nextLine();

                        System.out.println("Password: ");
                        String password = stringScanner.nextLine();

                        employeeService = new EmployeeService();
                        Employee currentLoggedEmployee = employeeService.login(username, password);

                        if (currentLoggedEmployee != null) {

                            boolean displayHomePage = true;
                            while (displayHomePage) {
                                HomePageMenu();
                                int choice2 = intScanner.nextInt();
                                switch (choice2) {
                                    case 1 -> EmployeeCommandLineInterface.Main(currentLoggedEmployee);
                                    case 2 -> TicketCommandLineInterface.Main(currentLoggedEmployee);
                                    case 3 ->
                                    {
                                        System.out.println("see you later!");
                                        currentLoggedEmployee = null;
                                        displayHomePage = false;
                                    }
                                    default -> System.out.println("Invalid choice, please try again!");
                                }
                            }
                        }
                    }
                    case 2 -> {
                        Employee newEmployee = EmployeeInformation(stringScanner, intScanner);
                        employeeService.register(newEmployee);
                    }
                    case 3 -> {
                        System.out.println("Enter your email address: ");
                        String email = stringScanner.nextLine();

                        Employee employee = employeeService.getByEmail(email);

                        if(employee != null)
                        {
                            int code = VerificationCodeFactory.sendEmail(email);
                            System.out.println("A verification code sent to your email");

                            System.out.println("Verification Code");
                            int verificationCode = intScanner.nextInt();

                            if(verificationCode == code)
                            {
                                System.out.println("Enter new password : ");
                                String password = stringScanner.nextLine();

                                employeeService.updatePassword(employee.getEmployeeID(), password);
                            }
                            else
                                System.out.println("Invalid verification code");
                        }
                        else
                            System.out.println("Email provided doesn't exist!");
                    }
                    default -> System.out.println("Invalid choice, please try again!");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void LoginMenu()
    {
        System.out.println("1 - Login");
        System.out.println("2 - Sign Up");
        System.out.println("3 - Reset Password");
    }

    public static void HomePageMenu()
    {
        System.out.println("1 - Employee");
        System.out.println("2 - Ticket");
        System.out.println("3 - Sign Out");
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
        return new Employee(firstName, lastName, username, password, email, phone, street, city, state, zipcode, managerID);
    }
}


