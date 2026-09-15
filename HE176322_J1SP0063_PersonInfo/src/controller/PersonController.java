package controller;

import dto.PersonRequestDTO;
import dto.PersonResponseDTO;
import service.PersonService;
import view.PersonView;

/**
 * Controller: sends the typed persons to the service and the sorted result to the view
 * (no Scanner, no printing, no static).
 *
 * @author HE176322
 */
public class PersonController {

    // builds and sorts the persons
    private PersonService personService;
    // prints the persons
    private PersonView personView;

    // creates the controller with its service and view
    public PersonController() {
        personService = new PersonService();
        personView = new PersonView();
    }

    // the only workflow: sort by salary, then display
    public void displaySortedPersons(PersonRequestDTO[] requests) throws Exception {
        PersonResponseDTO[] sorted = personService.sortPersons(requests);
        personView.setPersons(sorted);
        personView.display();
    }
}
