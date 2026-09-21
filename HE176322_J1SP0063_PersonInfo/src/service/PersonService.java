package service;

import constants.Constants;
import constants.Message;
import dto.PersonDTO;
import dto.PersonRequestDTO;
import dto.PersonResponseDTO;
import model.Person;
import repository.PersonRepository;

/**
 * Service: builds the persons, keeps them in the repository, sorts them by salary with
 * bubble sort and turns them into rows for the view. Called only by the controller; no
 * print, no keyboard.
 *
 * @author HE176322
 */
public class PersonService {

    // Keeps the array of persons the service works on (Service -> Repository -> Model).
    private PersonRepository personRepository;

    // Creates the service with an empty repository.
    public PersonService() {
        personRepository = new PersonRepository();
    }

    // Turns the typed persons into Person objects, keeps them in the repository, sorts
    // them by salary and copies them into rows for the view.
    public PersonResponseDTO sortPersons(PersonRequestDTO requestDTO) throws Exception {
        PersonDTO[] typedArray = requestDTO.getPersonArray();
        Person[] personArray = new Person[typedArray.length];
        PersonDTO[] rowArray = new PersonDTO[typedArray.length];
        PersonResponseDTO responseDTO = new PersonResponseDTO();

        // build one Person per typed row, in the order they were typed
        for (int i = 0; i < typedArray.length; i++) {
            personArray[i] = inputPersonInfo(typedArray[i]);
        }

        // the repository keeps the array; the brief's sortBySalary works on what it holds
        personRepository.savePersonArray(personArray);
        personArray = sortBySalary(personRepository.getPersonArray());

        // copy each sorted person into a row the view is allowed to see
        for (int i = 0; i < personArray.length; i++) {
            rowArray[i] = new PersonDTO(personArray[i].getName(),
                    personArray[i].getAddress(), personArray[i].getSalary());
        }

        // the rows go to the view inside the response
        responseDTO.setPersonArray(rowArray);
        return responseDTO;
    }

    // brief: Person inputPersonInfo(String name, String address, String sSalary) - check
    // the salary and build the Person; the three values arrive in one DTO
    private Person inputPersonInfo(PersonDTO personDTO) throws Exception {
        // the brief: a salary must be greater than zero
        if (personDTO.getSalary() <= Constants.MIN_SALARY) {
            throw new Exception(Message.SALARY_NOT_POSITIVE);
        }

        return new Person(personDTO.getName(), personDTO.getAddress(),
                personDTO.getSalary());
    }

    // brief: Person[] sortBySalary(Person[] person) - bubble sort by salary ascending; a
    // pass without any swap ends the sort
    private Person[] sortBySalary(Person[] personArray) throws Exception {
        int size = 0;

        // the brief: "Can't Sort Person" when there is an error
        if (personArray == null) {
            throw new Exception(Message.CANNOT_SORT);
        }

        // a missing person makes the sort impossible
        for (Person person : personArray) {
            // one hole in the array would crash the comparison
            if (person == null) {
                throw new Exception(Message.CANNOT_SORT);
            }
        }

        // every person is there: the passes work on the whole array
        size = personArray.length;

        // pass i moves the biggest remaining salary to position size - 1 - i
        for (int i = 0; i < (size - 1); i++) {
            boolean swapped = false;

            // the last i persons are already in place, so stop before them
            for (int j = 0; j < (size - 1 - i); j++) {
                // wrong order: swap the two WHOLE persons, not only their salaries
                if (personArray[j].getSalary() > personArray[j + 1].getSalary()) {
                    Person temp = personArray[j];

                    personArray[j] = personArray[j + 1];
                    personArray[j + 1] = temp;
                    swapped = true;
                }
            }

            // no swap in a whole pass: the array is already sorted
            if (!swapped) {
                return personArray;
            }
        }

        return personArray;
    }
}
