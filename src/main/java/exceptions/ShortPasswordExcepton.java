package exceptions;//Program Name: ExpensesReimbursementSystem
//Programmer  : Abderrahim Bahia
//Last Update : 08/10/2022

/**
 * the exception is thrown when the user sets a password of less than 16 characters
 */
public class ShortPasswordExcepton extends Exception{

    public ShortPasswordExcepton(String message)
    {
        super(message);
    }
}
