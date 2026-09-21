package service;

import constants.Constants;
import constants.Message;
import dto.FileRequestDTO;
import dto.FileResponseDTO;
import dto.PersonResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import model.Person;
import repository.PersonRepository;
import repository.TextRepository;

/**
 * SERVICE and Strategy CONTEXT: the business work of the two functions - keeping the
 * persons who earn enough, sorting them and naming the richest and the poorest; and
 * finding every single word of a text. The data comes from the repositories.
 *
 * @author HE176322
 */
public class FileService {

    // The order of the result list (SalaryComparator: poorest first).
    private Comparator<Person> personOrder;

    // The persons of each person file main read (Service -> Repository -> Model).
    private PersonRepository personRepository;

    // The lines of each text file of "Copy text".
    private TextRepository textRepository;

    // Creates the service with the order the result must be sorted in, and its two stores.
    public FileService(Comparator<Person> personOrder) {
        this.personOrder = personOrder;
        personRepository = new PersonRepository();
        textRepository = new TextRepository();
    }

    // The brief's getPerson: every person of the file at path whose salary is greater than
    // or equal to money, sorted so the person with the least money is the head of the list
    // and the one with the most is the last. The persons come from the repository (main
    // read the file), so the path is the key of that file there.
    // brief: "public List<Person> getPerson(String path, double money) throws Exception"
    public List<Person> getPerson(String path, double money) throws Exception {
        ArrayList<Person> foundList = new ArrayList<>();

        // look at every person the repository keeps for that file
        for (Person person : personRepository.getPersonList(path)) {
            // the brief: keep those with salary >= the money entered
            if (person.getSalary() >= money) {
                foundList.add(person);
            }
        }

        // Strategy: sort() does the sorting, personOrder decides the order;
        // the sort is stable, so equal salaries keep their file order
        Collections.sort(foundList, personOrder);
        return foundList;
    }

    // The brief's copyWordOneTimes: finds every single word of the source file, each word
    // counted only once, and writes them into the new file, one word per line, in the
    // order they first appear. The lines of the source come from the repository.
    // brief: "public static boolean copyWordOneTimes(String source, String destination)" -
    // no static here: static is kept for utils/constants/main (see HUONG-DAN)
    public boolean copyWordOneTimes(String source, String destination) throws Exception {
        LinkedHashSet<String> wordSet = new LinkedHashSet<>();

        // look at every line of the source file
        for (String line : textRepository.getLineList(source)) {
            // split the line at spaces/tabs; add() ignores repeated words
            for (String word : line.trim().split(Constants.WORD_SEPARATOR)) {
                // a blank line splits into one empty "word": skip it
                if (!word.isEmpty()) {
                    wordSet.add(word);
                }
            }
        }

        // one word per line in the new file ("Can't write file" comes from here)
        textRepository.saveLineList(destination, new ArrayList<>(wordSet));
        return true;
    }

    // Function 1 for the controller: keeps the persons of the file main read, runs getPerson
    // and turns its list into the answer the view prints (rows + Max + Min).
    public FileResponseDTO findPerson(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();
        ArrayList<PersonResponseDTO> rowList = new ArrayList<>();

        personRepository.loadData(requestDTO);

        // copy each person getPerson found into the DTO the view is allowed to see
        for (Person person : getPerson(requestDTO.getPath(), requestDTO.getMoney())) {
            rowList.add(new PersonResponseDTO(person.getName(), person.getAddress(),
                    person.getSalary()));
        }

        responseDTO.setPersonList(rowList);

        // the rows keep the sorted order: the head earns the least, the last the most
        if (!rowList.isEmpty()) {
            responseDTO.setMinName(rowList.get(0).getName());
            responseDTO.setMaxName(rowList.get(rowList.size() - 1).getName());
        }

        return responseDTO;
    }

    // Function 2 for the controller: keeps the lines main read from the source, runs
    // copyWordOneTimes and answers with "Copy done..." when the new file is written.
    public FileResponseDTO copyText(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();

        textRepository.loadData(requestDTO);

        // the brief's copy status: true means the new file is written
        if (copyWordOneTimes(requestDTO.getSource(), requestDTO.getDestination())) {
            responseDTO.setMessage(Message.COPY_DONE);
        }

        return responseDTO;
    }
}
