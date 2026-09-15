package service;

import constants.Constants;
import constants.Message;
import dto.PersonRequestDTO;
import dto.PersonResponseDTO;
import model.Person;

/**
 * Service: builds the persons, sorts them by salary with bubble sort and turns them into
 * rows for the view.
 *
 * @author HE176322
 */
public class PersonService {

    // turn the typed persons into sorted rows for the view
    public PersonResponseDTO[] sortPersons(PersonRequestDTO[] requests) throws Exception {
        Person[] persons = new Person[requests.length];
        // build one Person per request, in the order they were typed
        for (int i = 0; i < requests.length; i++) {
            persons[i] = inputPersonInfo(requests[i]);
        }
        sortBySalary(persons);
        PersonResponseDTO[] responses = new PersonResponseDTO[persons.length];
        // copy each sorted person into a DTO the view is allowed to see
        for (int i = 0; i < persons.length; i++) {
            responses[i] = new PersonResponseDTO(persons[i].getName(),
                    persons[i].getAddress(), persons[i].getSalary());
        }
        return responses;
    }

    // brief: inputPersonInfo - check the salary and build the Person
    private Person inputPersonInfo(PersonRequestDTO requestDTO) throws Exception {
        // the brief: a salary must be greater than zero
        if (requestDTO.getSalary() <= Constants.MIN_SALARY) {
            throw new Exception(Message.SALARY_NOT_POSITIVE);
        }
        return new Person(requestDTO.getName(), requestDTO.getAddress(),
                requestDTO.getSalary());
    }

    // brief: Person[] sortBySalary(Person[]) - bubble sort by salary ascending
    private Person[] sortBySalary(Person[] person) throws Exception {
        // the brief: "Can't Sort Person" when there is an error
        if (person == null) {
            throw new Exception(Message.CANNOT_SORT);
        }
        // a missing person makes the sort impossible
        for (Person p : person) {
            // one hole in the array would crash the comparison
            if (p == null) {
                throw new Exception(Message.CANNOT_SORT);
            }
        }
        int size = person.length;
        // pass i moves the biggest remaining salary to position size-1-i
        for (int i = 0; i < size - 1; i++) {
            // the last i persons are already in place
            for (int j = 0; j < size - 1 - i; j++) {
                // wrong order: swap the two neighbours
                if (person[j].getSalary() > person[j + 1].getSalary()) {
                    Person temp = person[j];
                    person[j] = person[j + 1];
                    person[j + 1] = temp;
                }
            }
        }
        return person;
    }
}
