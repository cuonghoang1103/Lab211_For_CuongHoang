package service;

import constants.Message;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import model.Person;
import repository.EmployeeRepository;
import utils.MD5Utils;

/**
 * SERVICE: Function 1 and the session - who is logged in, and may he borrow?
 *
 * @author HE176322
 */
public class AuthService {

    // employee.dat.
    private EmployeeRepository employeeRepository;
    // The person logged in, or null.
    private Person currentUser;

    // Creates the service on employee.dat.
    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Reads employee.dat.
    public void loadEmployees() throws Exception {
        employeeRepository.load();
    }

    // Function 1: the MD5 of the typed password must equal the stored hash.
    public LoginResponseDTO login(LoginRequestDTO requestDTO) throws Exception {
        Person person = employeeRepository.findById(requestDTO.getEmployeeID());
        // one message for a wrong id AND a wrong password: never tell which ids exist
        if (person == null || !person.getPassword().equalsIgnoreCase(
                MD5Utils.hash(requestDTO.getPassword()))) {
            currentUser = null;
            throw new Exception(Message.LOGIN_FAILED);
        }
        currentUser = person;
        LoginResponseDTO response = new LoginResponseDTO();
        response.setName(person.getName());
        response.setTitle(person.getTitle());
        return response;
    }

    // The guard of Functions 3-5: logged in, and an employee (the manager approves, he
    // does not borrow).
    public void checkEmployee() throws Exception {
        // nobody logged in
        if (currentUser == null) {
            throw new Exception(Message.LOGIN_FIRST);
        }
        // canManage() answers true only for a Manager (polymorphism)
        if (currentUser.canManage()) {
            throw new Exception(Message.MANAGER_CANNOT_BORROW);
        }
    }

    // The id of the person logged in (called after checkEmployee).
    public String getCurrentEmployeeID() {
        return currentUser.getEmployeeID();
    }
}
