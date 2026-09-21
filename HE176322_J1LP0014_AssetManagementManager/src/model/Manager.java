package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: role MA - the manager. In the manager's program he uses every function; in the
 * employee's program he can log in, but he approves requests, he does not borrow.
 *
 * @author HE176322
 */
public class Manager extends Person {

    // JavaBean constructor.
    public Manager() {
    }

    // Creates a manager with every field filled in.
    public Manager(String employeeId, String name, String birthdate, String sex,
            String password) {
        super(employeeId, name, birthdate, sex, password);
    }

    // A manager is written as MA.
    @Override
    public String getRole() {
        return Constants.ROLE_MANAGER;
    }

    // Shown as "Manager".
    @Override
    public String getTitle() {
        return Message.TITLE_MANAGER;
    }

    // A manager may use every function.
    @Override
    public boolean canManage() {
        return true;
    }
}
