package data;

import java.util.List;

import entity.Employee;

//DAO: data access object
public interface IEmployeeDao {

    //Create: inserting data into data base
    Employee insert(Employee employee);

    //Read - getting/reading data from a database
    Employee getByID(int id);

    Employee getByEmail(String email);
    Employee getByUsername(String username);

    List<Employee> getAllEmployees();

    //Update - update data that is already in the database
    Employee update(Employee employee);

    void addProfileImage(int id,  String address);

    void changePassword(int id, String password);

    void updatePosition(int id, String position);


    //Delete - delete an item from the database
    boolean delete(int id);
}
