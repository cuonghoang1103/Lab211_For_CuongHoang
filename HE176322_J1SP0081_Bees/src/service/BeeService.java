package service;

import constants.BeeType;
import constants.Constants;
import constants.Message;
import dto.BeeResponseDTO;
import dto.ColonyResponseDTO;
import java.util.ArrayList;
import java.util.Random;
import model.Bee;
import repository.BeeRepository;

/**
 * SERVICE: the business of the simulation - building a fresh colony and attacking it with
 * random damage - plus the Alive / Dead counts. Called only by the controller; no print,
 * no keyboard.
 *
 * @author HE176322
 */
public class BeeService {

    // Keeps the colony between menu choices (Service -> Repository -> Model).
    private BeeRepository beeRepository;

    // Creates the right subclass for each BeeType.
    private BeeFactory beeFactory;

    // Source of the random damage; one object reused for every bee.
    private Random random;

    // Creates the service with its repository, factory and random source.
    public BeeService() {
        beeRepository = new BeeRepository();
        beeFactory = new BeeFactory();
        random = new Random();
    }

    // Function 1: clears the colony and builds a new one - 10 Workers, then 10 Queens,
    // then 10 Drones, all at 100% health.
    public ColonyResponseDTO createBees() {
        ColonyResponseDTO responseDTO = new ColonyResponseDTO();
        ArrayList<BeeResponseDTO> rowList = new ArrayList<>();
        ArrayList<Bee> beeList = null;

        // the brief: "clear the current bee list"
        beeRepository.clearBees();

        // one group of bees per type, in the enum's order
        for (BeeType type : BeeType.values()) {
            // BEES_PER_TYPE bees of this type
            for (int i = 0; i < Constants.BEES_PER_TYPE; i++) {
                beeRepository.addBee(beeFactory.createBee(type));
            }
        }

        // read the new colony back from the repository
        beeList = beeRepository.getBeeList();

        // one row per bee, numbered from 1
        for (int i = 0; i < beeList.size(); i++) {
            rowList.add(createRow(i + 1, beeList.get(i)));
        }

        // the line of Function 1, then the rows and the Alive / Dead summary
        responseDTO.setMessage(String.format(Message.CREATED, Constants.BEES_PER_TYPE,
                Constants.BEES_PER_TYPE, Constants.BEES_PER_TYPE));
        responseDTO.setAttack(false);
        fillColony(responseDTO, rowList);
        return responseDTO;
    }

    // Function 2: attacks the current colony - a new random damage in [0, 80] for every
    // bee, applied through damage() (the brief's Damage()).
    public ColonyResponseDTO attackBees() throws Exception {
        ColonyResponseDTO responseDTO = new ColonyResponseDTO();
        ArrayList<BeeResponseDTO> rowList = new ArrayList<>();
        ArrayList<Bee> beeList = null;

        // the brief attacks the CURRENT list, so one must exist
        if (beeRepository.isEmpty()) {
            throw new Exception(Message.NO_BEE_LIST);
        }

        // read the current colony from the repository
        beeList = beeRepository.getBeeList();

        // roll, apply and record the damage of every bee
        for (int i = 0; i < beeList.size(); i++) {
            Bee bee = beeList.get(i);
            int damage = random.nextInt(Constants.MAX_DAMAGE + 1);
            BeeResponseDTO row = null;

            // a dead bee keeps its health, without error (the brief)
            bee.damage(damage);
            row = createRow(i + 1, bee);
            row.setDamage(damage);
            rowList.add(row);
        }

        // the line of Function 2, then the rows and the Alive / Dead summary
        responseDTO.setMessage(String.format(Message.ATTACKING, Constants.MIN_PERCENT,
                Constants.MAX_DAMAGE));
        responseDTO.setAttack(true);
        fillColony(responseDTO, rowList);
        return responseDTO;
    }

    // Copies one bee into the row the view may see (the view never sees the model).
    private BeeResponseDTO createRow(int no, Bee bee) {
        BeeResponseDTO row = new BeeResponseDTO();

        // number, type, health and status of the bee
        row.setNo(no);
        row.setType(bee.getType());
        row.setHealth(bee.getHealth());
        row.setDead(bee.isDead());
        return row;
    }

    // Puts the rows into the answer, with the Alive / Dead counts of the summary line.
    private void fillColony(ColonyResponseDTO responseDTO, ArrayList<BeeResponseDTO> rowList) {
        int aliveCount = 0;

        // count the rows whose bee is still alive
        for (BeeResponseDTO row : rowList) {
            // a living bee adds one to the alive count
            if (!row.isDead()) {
                aliveCount++;
            }
        }

        // the rows and the two counts travel to the view inside the answer
        responseDTO.setRowList(rowList);
        responseDTO.setAliveCount(aliveCount);
        responseDTO.setDeadCount(rowList.size() - aliveCount);
    }
}
