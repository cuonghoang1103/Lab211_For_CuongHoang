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
 * random damage - plus the Alive / Dead counts.
 *
 * @author HE176322
 */
public class BeeService {

    // Keeps the colony between menu choices.
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
        beeRepository.clearBees();
        // one group of bees per type, in the enum's order
        for (BeeType type : BeeType.values()) {
            // BEES_PER_TYPE bees of this type
            for (int i = 0; i < Constants.BEES_PER_TYPE; i++) {
                beeRepository.addBee(beeFactory.createBee(type));
            }
        }
        ArrayList<Bee> bees = beeRepository.getBees();
        ArrayList<BeeResponseDTO> rows = new ArrayList<>();
        // one row per bee, numbered from 1
        for (int i = 0; i < bees.size(); i++) {
            rows.add(toRow(i + 1, bees.get(i)));
        }
        return toColony(rows);
    }

    // Function 2: attacks the current colony.
    public ColonyResponseDTO attackBees() throws Exception {
        // the brief attacks the CURRENT list, so one must exist
        if (beeRepository.isEmpty()) {
            throw new Exception(Message.NO_BEE_LIST);
        }
        ArrayList<Bee> bees = beeRepository.getBees();
        ArrayList<BeeResponseDTO> rows = new ArrayList<>();
        // roll, apply and record the damage of every bee
        for (int i = 0; i < bees.size(); i++) {
            Bee bee = bees.get(i);
            int damage = random.nextInt(Constants.MAX_DAMAGE + 1);
            bee.Damage(damage);
            BeeResponseDTO row = toRow(i + 1, bee);
            row.setDamage(damage);
            rows.add(row);
        }
        return toColony(rows);
    }

    // Copies one bee into the row the view may see.
    private BeeResponseDTO toRow(int no, Bee bee) {
        BeeResponseDTO row = new BeeResponseDTO();
        row.setNo(no);
        row.setType(bee.getType());
        row.setHealth(bee.getHealth());
        row.setDead(bee.isDead());
        return row;
    }

    // Wraps the rows with the Alive / Dead summary.
    private ColonyResponseDTO toColony(ArrayList<BeeResponseDTO> rows) {
        int alive = 0;
        // count the rows whose bee is still alive
        for (BeeResponseDTO row : rows) {
            // a living bee adds one to the alive count
            if (!row.isDead()) {
                alive++;
            }
        }
        ColonyResponseDTO colony = new ColonyResponseDTO();
        colony.setBees(rows);
        colony.setAliveCount(alive);
        colony.setDeadCount(rows.size() - alive);
        return colony;
    }
}
