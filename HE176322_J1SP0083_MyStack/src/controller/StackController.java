package controller;

import dto.StackRequestDTO;
import dto.StackResponseDTO;
import repository.StackRepository;
import view.StackView;

/**
 * CONTROLLER: receives the request from main, asks the repository to do the work, and
 * hands the result to the view - once per menu option. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class StackController {

    // Keeps the stack between menu choices.
    private StackRepository stackRepository;

    // Prints the results.
    private StackView stackView;

    // Creates the controller together with its repository and view.
    public StackController() {
        stackRepository = new StackRepository();
        stackView = new StackView();
    }

    // Option 1: pushes the value, then shows it with the new stack.
    public void push(StackRequestDTO requestDTO) {
        StackResponseDTO responseDTO = stackRepository.push(requestDTO);

        // hand the result to the view, then render it - once for this flow
        stackView.setResponseDTO(responseDTO);
        stackView.display();
    }

    // Option 2: pops the top value, then shows it with the stack left.
    public void pop() throws Exception {
        StackResponseDTO responseDTO = stackRepository.pop();

        // hand the result to the view, then render it - once for this flow
        stackView.setResponseDTO(responseDTO);
        stackView.display();
    }

    // Option 3: shows the top value without removing it.
    public void get() throws Exception {
        StackResponseDTO responseDTO = stackRepository.get();

        // hand the result to the view, then render it - once for this flow
        stackView.setResponseDTO(responseDTO);
        stackView.display();
    }

    // Option 4: shows the whole stack, top first ("[]" when empty).
    public void displayStack() {
        StackResponseDTO responseDTO = stackRepository.getStack();

        // hand the result to the view, then render it - once for this flow
        stackView.setResponseDTO(responseDTO);
        stackView.display();
    }
}
