package repository;

import constants.Constants;
import model.Employee;
import model.Manager;
import model.Person;

/**
 * REPOSITORY: employee.dat - staff and manager in one file; the role column decides the
 * class (the only place a role code is compared).
 *
 * @author HE176322
 */
public class EmployeeRepository extends FileRepository<Person> {

    // Creates the store of employee.dat.
    public EmployeeRepository() {
        super(Constants.EMPLOYEE_FILE);
    }

    // Six columns: id, name, birthdate, role, sex, password; MA -> Manager, else Employee.
    @Override
    protected Person parse(String[] parts) throws Exception {
        // a line with too few or too many columns
        if (parts.length != Constants.EMPLOYEE_COLUMNS) {
            throw new Exception();
        }
        String id = parts[Constants.EMPLOYEE_ID];
        String name = parts[Constants.EMPLOYEE_NAME];
        String birthdate = parts[Constants.EMPLOYEE_BIRTHDATE];
        String sex = parts[Constants.EMPLOYEE_SEX];
        String password = parts[Constants.EMPLOYEE_PASSWORD];
        // the role column chooses the class
        if (parts[Constants.EMPLOYEE_ROLE].equalsIgnoreCase(Constants.ROLE_MANAGER)) {
            return new Manager(id, name, birthdate, sex, password);
        }
        return new Employee(id, name, birthdate, sex, password);
    }

    // The same six columns; the role is asked of the object (it cannot disagree with it).
    @Override
    protected String format(Person person) {
        return String.join(Constants.DATA_JOINER, person.getEmployeeID(), person.getName(),
                person.getBirthdate(), person.getRole(), person.getSex(),
                person.getPassword());
    }
}
