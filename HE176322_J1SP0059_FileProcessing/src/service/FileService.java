package service;

import constants.Constants;
import dto.FileRequestDTO;
import dto.PersonResponseDTO;
import dto.ReportResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Person;
import utils.FileUtils;

/**
 * SERVICE and Strategy CONTEXT: the business work of the two functions - turning the
 * lines of the file into persons, keeping those who earn enough, sorting them and naming
 * the richest and the poorest; and the word copy.
 *
 * @author HE176322
 */
public class FileService {

    // The order of the result list (SalaryComparator: poorest first).
    private Comparator<Person> personOrder;

    // Creates the service with the order the result must be sorted in.
    public FileService(Comparator<Person> personOrder) {
        this.personOrder = personOrder;
    }

    // The brief's getPerson: every person of the file whose salary is greater than or
    // equal to money, sorted so the person with the least money is the head of the list
    // and the one with the most is the last.
    // brief: "public List<Person> getPerson(String path, double money)"
    public List<Person> getPerson(String path, double money) throws Exception {
        ArrayList<String> lines = FileUtils.readLines(path);
        ArrayList<Person> persons = new ArrayList<>();
        // one line of the file = one person
        for (String line : lines) {
            // blank lines are not persons
            if (line.trim().isEmpty()) {
                continue;
            }
            Person person = toPerson(line);
            // the brief: keep those with salary >= the money entered
            if (person.getSalary() >= money) {
                persons.add(person);
            }
        }
        // Strategy: sort() does the sorting, personOrder decides the order;
        // the sort is stable, so equal salaries keep their file order
        Collections.sort(persons, personOrder);
        return persons;
    }

    // Function 1 for the controller: runs getPerson and turns its list into the report
    // the view prints (rows + max + min).
    public ReportResponseDTO findPerson(FileRequestDTO requestDTO) throws Exception {
        // brief: getPerson returns a List
        List<Person> persons = getPerson(requestDTO.getPath(), requestDTO.getMoney());
        ArrayList<PersonResponseDTO> rows = new ArrayList<>();
        // copy each model object into the DTO the view is allowed to see
        for (Person person : persons) {
            rows.add(new PersonResponseDTO(person.getName(), person.getAddress(),
                    person.getSalary()));
        }
        ReportResponseDTO report = new ReportResponseDTO();
        report.setPersons(rows);
        // the list is sorted: the head earns the least, the last the most
        if (!persons.isEmpty()) {
            report.setMinName(persons.get(0).getName());
            report.setMaxName(persons.get(persons.size() - 1).getName());
        }
        return report;
    }

    // Function 2 for the controller: copies every single word of the source into the
    // destination through the brief's copyWordOneTimes.
    public boolean copyText(FileRequestDTO requestDTO) throws Exception {
        return FileUtils.copyWordOneTimes(requestDTO.getSource(),
                requestDTO.getDestination());
    }

    // Turns one line "name;address;salary" into a Person.
    private Person toPerson(String line) {
        // -1 keeps empty fields: "Lan;Hue;" gives 3 parts, the last empty
        String[] parts = line.split(Constants.SEPARATOR, Constants.KEEP_EMPTY_FIELDS);
        Person person = new Person();
        person.setName(parts[Constants.INDEX_NAME].trim());
        person.setAddress(getPart(parts, Constants.INDEX_ADDRESS));
        person.setSalary(toSalary(getPart(parts, Constants.INDEX_SALARY)));
        return person;
    }

    // Returns one field of a split line, or an empty text when the line is too short to
    // have it.
    private String getPart(String[] parts, int index) {
        // the line stops before this field
        if (index >= parts.length) {
            return "";
        }
        return parts[index].trim();
    }

    // The brief: "If the salary of some person is in wrong format (not a number, not
    // inputted) to set it to default value zero", and "the amount not less than 0".
    private double toSalary(String text) {
        double salary;
        // a wrong format must not stop the reading of the other lines
        try {
            salary = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            // "abc" or "": wrong format -> default 0
            return Constants.DEFAULT_SALARY;
        }
        // negative, NaN or infinite: not a legal amount -> default 0
        if (Double.isNaN(salary) || Double.isInfinite(salary)
                || salary < Constants.MIN_MONEY) {
            return Constants.DEFAULT_SALARY;
        }
        return salary;
    }
}
