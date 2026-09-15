package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: role MA - the person the manager's program is written for.
 *
 * @author HE176322
 */
public class Manager extends Person {

    // JavaBean constructor.
    public Manager() {
    }

    // Creates a manager with every field filled in.
    public Manager(String employeeID, String name, String birthdate, String sex,
            String password) {
        super(employeeID, name, birthdate, sex, password);
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
