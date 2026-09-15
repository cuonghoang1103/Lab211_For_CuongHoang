package controller;

import dto.StackRequestDTO;
import dto.StackResponseDTO;
import repository.StackRepository;
import view.StackView;

/**
 * CONTROLLER: receives the request from main, asks the repository to do the work, and
 * hands the result to the view.
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
        StackResponseDTO response = stackRepository.push(requestDTO);
        stackView.setResponse(response);
        stackView.displayPush();
    }

    // Option 2: pops the top value, then shows it with the stack left.
    public void pop() throws Exception {
        StackResponseDTO response = stackRepository.pop();
        stackView.setResponse(response);
        stackView.displayPop();
    }

    // Option 3: shows the top value without removing it.
    public void get() throws Exception {
        StackResponseDTO response = stackRepository.get();
        stackView.setResponse(response);
        stackView.displayTop();
    }

    // Option 4: shows the whole stack, top first ("[]" when empty).
    public void display() {
        StackResponseDTO response = stackRepository.getStack();
        stackView.setResponse(response);
        stackView.display();
    }
}
