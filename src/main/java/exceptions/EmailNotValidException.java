package exceptions;//Program Name: ExpensesReimbursementSystem
//Programmer  : Abderrahim Bahia
//Last Update : 08/10/2022

public class EmailNotValidException extends Exception{

    /**
     * Handles invalid email exception
     * ensure email address includes '@' and ".",
     */
    public EmailNotValidException(String message)
    {
        super(message);
    }
}
