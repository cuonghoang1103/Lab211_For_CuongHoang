package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: role EM. He can log in, but every manager function turns him away.
 *
 * @author HE176322
 */
public class Employee extends Person {

    // JavaBean constructor.
    public Employee() {
    }

    // Creates an employee with every field filled in.
    public Employee(String employeeID, String name, String birthdate, String sex,
            String password) {
        super(employeeID, name, birthdate, sex, password);
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
