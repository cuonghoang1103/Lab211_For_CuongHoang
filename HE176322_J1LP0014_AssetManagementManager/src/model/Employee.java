package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: role EM - a member of staff. In the employee's program he borrows and returns
 * assets; in the manager's program every manager function turns him away.
 *
 * @author HE176322
 */
public class Employee extends Person {

    // JavaBean constructor.
    public Employee() {
    }

    // Creates an employee with every field filled in.
    public Employee(String employeeId, String name, String birthdate, String sex,
            String password) {
        super(employeeId, name, birthdate, sex, password);
    }

    // An employee is written as EM.
    @Override
    public String getRole() {
        return Constants.ROLE_EMPLOYEE;
    }

    // Shown as "Employee".
    @Override
    public String getTitle() {
        return Message.TITLE_EMPLOYEE;
    }

    // An employee may not use the manager's functions.
    @Override
    public boolean canManage() {
        return false;
    }
}
