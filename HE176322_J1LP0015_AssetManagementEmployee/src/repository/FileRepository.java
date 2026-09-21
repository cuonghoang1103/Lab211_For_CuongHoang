package repository;

import constants.Constants;
import java.util.ArrayList;
import model.IRecord;
import utils.FileUtils;

/**
 * REPOSITORY (Template Method): the data of one .dat file and its simple CRUD, written
 * ONCE for the four files. A subclass only says how one line becomes an object (parse)
 * and back (format). It never reads the file itself: main reads the lines (FileUtils)
 * and hands them over; writing goes through utils/FileUtils.
 *
 * @param <T> the record of the file: Asset, Person, Request or Borrow
 * @author HE176322
 */
public abstract class FileRepository<T extends IRecord> {

    // Name of the file, next to build.xml.
    private String fileName;

    // Number of columns of one line of the file.
    private int columnCount;

    // The rows, in file order.
    private ArrayList<T> itemList = new ArrayList<>();

    // Creates a store for the given file and its number of columns.
    protected FileRepository(String fileName, int columnCount) {
        this.fileName = fileName;
        this.columnCount = columnCount;
    }

    // Template Method step: one line (already split, trimmed, right column count) -> one
    // object.
    protected abstract T parse(String[] partArray);

    // Template Method step: one object -> its line.
    protected abstract String format(T item);

    // Start-up: turns the lines main read from the file into rows. A blank or damaged line
    // costs that line only, not the whole file.
    public final void loadData(ArrayList<String> lineList) {
        String[] partArray = null;

        // start from an empty store
        itemList.clear();

        // one line = one row
        for (String line : lineList) {
            partArray = line.split(Constants.DATA_SEPARATOR, -1);

            // "A001, Samsung projector" -> "A001" and "Samsung projector"
            for (int i = 0; i < partArray.length; i++) {
                partArray[i] = partArray[i].trim();
            }

            // a blank line or a wrong number of columns is not a row
            if (partArray.length != columnCount) {
                continue;
            }

            // a number that is not a number costs this line only
            try {
                itemList.add(parse(partArray));
            } catch (NumberFormatException e) {
                // skip the damaged line
                continue;
            }
        }
    }

    // Finds a row by id, ignoring case (a001 = A001); null when no row has it.
    public T findById(String id) {
        // look at every row once
        for (T item : itemList) {
            // same id, whatever the case
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }

        return null;
    }

    // Returns a copy of the rows, so a caller cannot add or remove behind the store.
    public ArrayList<T> findAll() {
        return new ArrayList<>(itemList);
    }

    // Tells whether the file has no row.
    public boolean isEmpty() {
        return itemList.isEmpty();
    }

    // Adds a row and writes the file at once (closing the window loses nothing).
    public void add(T item) throws Exception {
        itemList.add(item);
        saveData();
    }

    // Puts the changed row back in its place and writes the file.
    public void update(T item) throws Exception {
        // walk by index so the row keeps its position
        for (int i = 0; i < itemList.size(); i++) {
            // the stored row with the same id
            if (itemList.get(i).getId().equalsIgnoreCase(item.getId())) {
                itemList.set(i, item);
            }
        }

        saveData();
    }

    // Removes a row and writes the file.
    public void remove(T item) throws Exception {
        itemList.remove(item);
        saveData();
    }

    // The next free id: the HIGHEST number used plus one (R001, R002, R007 -> R008), not
    // size + 1, which would give an id that already exists.
    public String getNextId(String prefix) {
        int max = 0;

        // read the number of every id
        for (T item : itemList) {
            String id = item.getId();

            // only ids made of the prefix and digits count (B001 yes, X9 no)
            if (id.startsWith(prefix) &&
                    id.substring(prefix.length()).matches(Constants.DIGITS_PATTERN)) {
                max = Math.max(max, Integer.parseInt(id.substring(prefix.length())));
            }
        }

        return String.format(Constants.ID_FORMAT, prefix, max + 1);
    }

    // Writes every row back through utils/FileUtils (the file is replaced, not appended).
    private void saveData() throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // one line per row, in order
        for (T item : itemList) {
            lineList.add(format(item));
        }

        FileUtils.writeLines(fileName, lineList);
    }
}
