package repository;

import constants.Constants;
import java.util.ArrayList;
import model.Identifiable;
import utils.FileUtils;

/**
 * REPOSITORY (Template Method): the file handling written ONCE for the four .dat files.
 * A subclass only says how one line becomes an object (parse) and back (format).
 *
 * @param <T> the record of the file: Asset, Person, Request or Borrow
 * @author HE176322
 */
public abstract class FileRepository<T extends Identifiable> {

    // Name of the file, next to build.xml.
    private String fileName;
    // The rows, in file order.
    private ArrayList<T> items = new ArrayList<>();

    // Creates a store for the given file.
    protected FileRepository(String fileName) {
        this.fileName = fileName;
    }

    // Template Method step: one line (already split and trimmed) -> one object.
    protected abstract T parse(String[] parts) throws Exception;

    // Template Method step: one object -> the columns of its line.
    protected abstract String format(T item);

    // Reads the file; a missing file is an empty store, a damaged line is skipped.
    public final void load() throws Exception {
        items.clear();
        // first run: nothing was stored yet
        if (!FileUtils.exists(fileName)) {
            return;
        }
        // one line = one record
        for (String line : FileUtils.readLines(fileName)) {
            // an empty line is not a record
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(Constants.DATA_SEPARATOR, -1);
            // "A001, Samsung projector" -> "A001" and "Samsung projector"
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }
            // a damaged line costs one line, not the whole file
            try {
                items.add(parse(parts));
            } catch (Exception e) {
                // wrong column count or a number that is not a number: skip it
                continue;
            }
        }
    }

    // Writes every row back (the file is replaced, not appended).
    public final void save() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // one line per row, in order
        for (T item : items) {
            lines.add(format(item));
        }
        FileUtils.writeLines(fileName, lines);
    }

    // Finds a row by id, ignoring case (a001 = A001).
    public T findById(String id) {
        // look at every row once
        for (T item : items) {
            // same id, whatever the case
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // Returns a copy of the rows, so a caller cannot add or remove behind the store.
    public ArrayList<T> findAll() {
        return new ArrayList<>(items);
    }

    // Tells whether the file has no row.
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Adds a row and writes the file at once (closing the window loses nothing).
    public void add(T item) throws Exception {
        items.add(item);
        save();
    }

    // Puts the changed row back in its place and writes the file.
    public void update(T item) throws Exception {
        // walk by index so the row keeps its position
        for (int i = 0; i < items.size(); i++) {
            // the stored row with the same id
            if (items.get(i).getId().equalsIgnoreCase(item.getId())) {
                items.set(i, item);
            }
        }
        save();
    }

    // Removes a row and writes the file.
    public void remove(T item) throws Exception {
        items.remove(item);
        save();
    }

    // The next free id: the HIGHEST number used plus one (R001, R002, R007 -> R008), not
    // size + 1, which would give an id that already exists.
    public String nextId(String prefix) {
        int max = 0;
        // read the number of every id
        for (T item : items) {
            String id = item.getId();
            // only ids made of the prefix and digits count (B001 yes, X9 no)
            if (id.startsWith(prefix)
                    && id.substring(prefix.length()).matches(Constants.DIGITS_PATTERN)) {
                max = Math.max(max, Integer.parseInt(id.substring(prefix.length())));
            }
        }
        return String.format(Constants.ID_FORMAT, prefix, max + 1);
    }
}
