package exceptions;//Program Name: ExpensesReimbursementSystem
//Programmer  : Abderrahim Bahia
//Last Update : 08/10/2022

public class InvalidPhoneNumberException extends Exception{

    /**
     * Handles invalid phone numbers
     * checks the phone number length and characters
     */

    public InvalidPhoneNumberException(String message)
    {
        super(message);
    }
}
