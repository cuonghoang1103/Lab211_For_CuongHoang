package controller;

import constants.Message;
import dto.NoteRequestDTO;
import dto.NoteResponseDTO;
import repository.NoteRepository;
import view.NoteView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * to do the work, and hands the result to the view - once per flow. No Scanner, no print,
 * no model.
 *
 * @author HE176322
 */
public class NoteController {

    // Where the notes are stored.
    private NoteRepository noteRepository;

    // Where the results are printed.
    private NoteView noteView;

    // Creates the controller together with its repository and view.
    public NoteController() {
        noteRepository = new NoteRepository();
        noteView = new NoteView();
    }

    // Option 1: stores the note Main has checked; the view shows its new ID.
    public void addNote(NoteRequestDTO requestDTO) {
        NoteResponseDTO responseDTO = new NoteResponseDTO();
        int noteId = noteRepository.addNote(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.ADD_SUCCESS, noteId));
        noteView.setResponseDTO(responseDTO);
        noteView.display();
    }

    // Option 2: removes the note (or throws "Note [9] does not exist."); the view shows
    // which ID was deleted.
    public void deleteNote(NoteRequestDTO requestDTO) throws Exception {
        NoteResponseDTO responseDTO = new NoteResponseDTO();

        // an unknown ID stops the flow here: main prints the message
        noteRepository.deleteNote(requestDTO);

        // deleted: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.DELETE_SUCCESS, requestDTO.getId()));
        noteView.setResponseDTO(responseDTO);
        noteView.display();
    }

    // Option 3: every note; the view shows the table.
    public void displayNotes() {
        NoteResponseDTO responseDTO = new NoteResponseDTO();

        // hand the rows to the view, then render them - once for the whole flow
        responseDTO.setRowList(noteRepository.getDataNotes());
        noteView.setResponseDTO(responseDTO);
        noteView.display();
    }
}
