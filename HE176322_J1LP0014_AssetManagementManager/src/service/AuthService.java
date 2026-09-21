package service;

import constants.Message;
import dto.AssetRequestDTO;
import dto.PersonDTO;
import model.Person;
import repository.EmployeeRepository;

/**
 * SERVICE: Function 1 and the session - who is logged in, and may he manage?
 *
 * @author HE176322
 */
public class AuthService {

    // employee.dat.
    private EmployeeRepository employeeRepository;

    // The person logged in, or null.
    private Person currentUser;

    // Creates the service on employee.dat (constructor injection).
    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Start-up: the lines main read from employee.dat become people.
    public void loadData(AssetRequestDTO requestDTO) {
        employeeRepository.loadData(requestDTO.getEmployeeLineList());
    }

    // Function 1: the stored hash must equal the MD5 main made of the typed password.
    public PersonDTO login(AssetRequestDTO requestDTO) throws Exception {
        Person person = employeeRepository.findById(requestDTO.getEmployeeId());
        PersonDTO personDTO = new PersonDTO();

        // one message for a wrong id AND a wrong password: never tell which ids exist
        if ((person == null) ||
                !person.getPassword().equalsIgnoreCase(requestDTO.getPassword())) {
            currentUser = null;
            throw new Exception(Message.LOGIN_FAILED);
        }

        // remember who logged in, and copy what the screen shows
        currentUser = person;
        personDTO.setName(person.getName());
        personDTO.setTitle(person.getTitle());
        return personDTO;
    }

    // The guard of Functions 3-6: logged in, and allowed to manage.
    public void checkManager() throws Exception {
        // nobody logged in
        if (currentUser == null) {
            throw new Exception(Message.LOGIN_FIRST);
        }

        // logged in, but canManage() answers false (polymorphism: Employee)
        if (!currentUser.canManage()) {
            throw new Exception(String.format(Message.NOT_MANAGER, currentUser.getName()));
        }
    }
}
