//Program Name: ExpensesReimbursementSystem
//Programmer  : Abderrahim Bahia
//Last Update : 08/10/2022

package entity;

import exceptions.EmailNotValidException;
import exceptions.InvalidPhoneNumberException;
import exceptions.ShortPasswordExcepton;

public class Employee
{
    //Employee Attributes
    private int       employeeID;
    private String    firstName;
    private String    lastName;
    private String    username;
    private String    password;
    private String    email;
    private String    phone;
    private String    position = "employee";
    private String    street;
    private String    city;
    private String    state;
    private String zipCode;
    private int       managerID;

    private byte []   profileImage = null;


    /**
     * Default Constructor
     */

    public Employee()
    {
    }

    public Employee(String firstName, String lastName, String username, String password, String email, String phone, String street, String city, String state, String zipCode, int managerID, byte [] profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerID = managerID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.profileImage = profileImage;
    }

    public Employee(String firstName, String lastName, String username, String password, String email, String phone, String street, String city, String state, String zipCode, int managerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerID = managerID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Employee(String firstName, String lastName, String username, String password, String email, String phone, String position, String street, String city, String state, String zipCode, int managerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerID = managerID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }


    //fully parameterized constructor
    public Employee(int employeeID, String firstName, String lastName, String username, String password, String email, String phone, String position, String street, String city, String state, String zipCode, int managerID) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.managerID = managerID;
    }

    public Employee(int employeeID, String firstName, String lastName, String username, String password, String email, String phone, String position, String street, String city, String state, String zipCode, int managerID, byte [] profileeImage) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.managerID = managerID;
        this.profileImage = profileImage;
    }


    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ShortPasswordExcepton {
      if(password.length() < 16)
            throw new ShortPasswordExcepton("Invalid Password, Please try again!");
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailNotValidException {
        if(email.isEmpty())
            throw new EmailNotValidException("E-mail address is required to sign up");
        else if(!email.contains("@") || !email.contains("."))
            throw new EmailNotValidException("Invalid E-mail, Please try again!");
        else
            this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws InvalidPhoneNumberException {
        boolean onlyIntegers = true;
        for(int i = 0; i < phone.length(); i++)
        {
            if(phone.charAt(i) < '0' || phone.charAt(i) > '9')
                onlyIntegers = false;
        }

        if(!onlyIntegers || phone.length() != 10)
            throw new InvalidPhoneNumberException("Invalid Phone Number, Please try again!");

        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position)
    {
            if (position.equalsIgnoreCase("manager"))
                this.position = "Manager";
            else
                System.out.println("The position is set to \"Employee\" by default!");
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setAddress(String street, String city, String state, String zipCode)
    {
        this.street     = street;
        this.city       = city;
        this.state      = state;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Employee: " +
                "EmployeeID='" + employeeID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode=" + zipCode +
                ", ManagerID=" + managerID +
                '}';
    }
}
