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

    // Creates the store of employee.dat: six columns.
    public EmployeeRepository() {
        super(Constants.EMPLOYEE_FILE, Constants.EMPLOYEE_COLUMNS);
    }

    // Six columns: id, name, birthdate, role, sex, password; MA -> Manager, else Employee.
    @Override
    protected Person parse(String[] partArray) {
        String employeeId = partArray[Constants.EMPLOYEE_ID];
        String name = partArray[Constants.EMPLOYEE_NAME];
        String birthdate = partArray[Constants.EMPLOYEE_BIRTHDATE];
        String sex = partArray[Constants.EMPLOYEE_SEX];
        String password = partArray[Constants.EMPLOYEE_PASSWORD];

        // the role column chooses the class
        if (partArray[Constants.EMPLOYEE_ROLE].equalsIgnoreCase(Constants.ROLE_MANAGER)) {
            return new Manager(employeeId, name, birthdate, sex, password);
        }

        return new Employee(employeeId, name, birthdate, sex, password);
    }

    // The same six columns; the role is asked of the object (it cannot disagree with it).
    @Override
    protected String format(Person person) {
        return String.join(Constants.DATA_JOINER, person.getEmployeeId(), person.getName(),
                person.getBirthdate(), person.getRole(), person.getSex(),
                person.getPassword());
    }
}
