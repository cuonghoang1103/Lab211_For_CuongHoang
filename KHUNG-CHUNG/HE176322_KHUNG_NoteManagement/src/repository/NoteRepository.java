package repository;

import constants.Constants;
import constants.Message;
import dto.NoteRequestDTO;
import java.util.ArrayList;
import model.Note;

/**
 * REPOSITORY: holds the notes and performs simple CRUD on them. No check of what was typed
 * (Main did it), no print.
 *
 * @author HE176322
 */
public class NoteRepository {

    // The "database" of notes, in the order they were added.
    private ArrayList<Note> noteList;

    // Creates an empty repository.
    public NoteRepository() {
        noteList = new ArrayList<>();
    }

    // Stores the note Main has already checked, with ID = last ID + 1, and returns that ID.
    public int addNote(NoteRequestDTO requestDTO) {
        Note note = new Note(generateNextId(), requestDTO.getContent());

        // keep it after the others
        noteList.add(note);
        return note.getId();
    }

    // Removes the note with the request's ID; an unknown ID is refused.
    public void deleteNote(NoteRequestDTO requestDTO) throws Exception {
        // look at every note until the ID matches
        for (int i = 0; i < noteList.size(); i++) {
            // found it: remove by position and stop
            if (noteList.get(i).getId() == requestDTO.getId()) {
                noteList.remove(i);
                return;
            }
        }

        // no note has this ID
        throw new Exception(String.format(Message.NOTE_NOT_EXIST, requestDTO.getId()));
    }

    // Every note as one row of the table, in the order they were added.
    public ArrayList<String> getDataNotes() {
        ArrayList<String> rowList = new ArrayList<>();

        // one row per stored note, text built by the model's toString()
        for (Note note : noteList) {
            rowList.add(note.toString());
        }

        return rowList;
    }

    // The ID rule: the first note gets ID 1, every next one the last ID + 1.
    private int generateNextId() {
        // empty list: the first note gets ID 1
        if (noteList.isEmpty()) {
            return Constants.FIRST_ID;
        }

        return noteList.get(noteList.size() - 1).getId() + Constants.ID_STEP;
    }
}
