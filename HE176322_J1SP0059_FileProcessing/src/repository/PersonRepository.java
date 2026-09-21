package repository;

import constants.Constants;
import dto.FileRequestDTO;
import java.util.ArrayList;
import java.util.HashMap;
import model.Person;

/**
 * REPOSITORY: holds the persons of every person file main has read, each list under the
 * path of its file, and only simple CRUD on them. No filter, no sort, no print, no file
 * reading - main reads the file and hands the lines over in the request.
 *
 * @author HE176322
 */
public class PersonRepository {

    // Path of a person file -> the persons of that file, in file order.
    private HashMap<String, ArrayList<Person>> personMap;

    // Creates an empty store.
    public PersonRepository() {
        personMap = new HashMap<>();
    }

    // Create: turns the lines main read into persons (the model) and keeps them under the
    // path of their file; reading the same path again replaces the old list.
    public void loadData(FileRequestDTO requestDTO) {
        ArrayList<Person> personList = new ArrayList<>();

        // one line of the file = one person
        for (String line : requestDTO.getLineList()) {
            // blank lines are not persons
            if (!line.trim().isEmpty()) {
                personList.add(toPerson(line));
            }
        }

        personMap.put(requestDTO.getPath(), personList);
    }

    // Read: the persons of the file at this path (an empty list for a path never loaded).
    public ArrayList<Person> getPersonList(String path) {
        return personMap.getOrDefault(path, new ArrayList<>());
    }

    // Turns one line "name;address;salary" into a Person.
    private Person toPerson(String line) {
        // -1 keeps empty fields: "Lan;Hue;" gives 3 parts, the last empty
        String[] partArray = line.split(Constants.SEPARATOR, Constants.KEEP_EMPTY_FIELDS);
        Person person = new Person();

        person.setName(partArray[Constants.INDEX_NAME].trim());
        person.setAddress(getPart(partArray, Constants.INDEX_ADDRESS));
        person.setSalary(toSalary(getPart(partArray, Constants.INDEX_SALARY)));
        return person;
    }

    // Returns one field of a split line, or an empty text when the line is too short to
    // have it.
    private String getPart(String[] partArray, int index) {
        // the line stops before this field
        if (index >= partArray.length) {
            return "";
        }

        return partArray[index].trim();
    }

    // The brief: "If the salary of some person is in wrong format (not a number, not
    // inputted) to set it to default value zero", and "the amount not less than 0".
    private double toSalary(String text) {
        double salary = Constants.DEFAULT_SALARY;

        // a wrong format must not stop the reading of the other lines
        try {
            salary = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            // "abc" or "": wrong format -> default 0
            return Constants.DEFAULT_SALARY;
        }

        // negative, NaN or infinite: not a legal amount -> default 0
        if (Double.isNaN(salary) || Double.isInfinite(salary) || (salary < Constants.MIN_MONEY)) {
            return Constants.DEFAULT_SALARY;
        }

        return salary;
    }
}
