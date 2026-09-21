package controller;

import dto.PersonRequestDTO;
import dto.PersonResponseDTO;
import service.PersonService;
import view.PersonView;

/**
 * Controller: sends the typed persons to the service and the sorted result to the view
 * once (no Scanner, no printing, no static, no model).
 *
 * @author HE176322
 */
public class PersonController {

    // builds, keeps and sorts the persons (Controller -> Service -> Repository -> Model)
    private PersonService personService;

    // prints the persons
    private PersonView personView;

    // creates the controller with its service and view
    public PersonController() {
        personService = new PersonService();
        personView = new PersonView();
    }

    // the only workflow: sort by salary, then display
    public void displaySortedPersons(PersonRequestDTO requestDTO) throws Exception {
        PersonResponseDTO responseDTO = personService.sortPersons(requestDTO);

        // hand the sorted persons to the view, then render them - once for the whole flow
        personView.setResponseDTO(responseDTO);
        personView.display();
    }
}
