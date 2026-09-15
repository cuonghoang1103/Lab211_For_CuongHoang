package view;

import constants.Constants;
import constants.Message;
import dto.BeeResponseDTO;
import dto.ColonyResponseDTO;
import java.util.Locale;

/**
 * VIEW: prints the two bee tables of the brief - the new colony and the attack report.
 *
 * @author HE176322
 */
public class BeeView {

    // The table to display, handed over by the controller.
    private ColonyResponseDTO colony;

    // Receives the table the next display call will print.
    public void setColony(ColonyResponseDTO colony) {
        this.colony = colony;
    }

    // Function 1's screen: "New bee list created: ...", then the table No / Type / Health
    // / Status and the summary.
    public void displayNewColony() {
        System.out.println(String.format(Message.CREATED, Constants.BEES_PER_TYPE,
                Constants.BEES_PER_TYPE, Constants.BEES_PER_TYPE));
        System.out.println(String.format(Constants.COLONY_FORMAT, Message.LABEL_NO,
                Message.LABEL_TYPE, Message.LABEL_HEALTH, Message.LABEL_STATUS));
        System.out.println(Message.LINE_COLONY);
        // one line per bee, in colony order
        for (BeeResponseDTO bee : colony.getBees()) {
            System.out.println(String.format(Constants.COLONY_FORMAT, bee.getNo(),
                    bee.getType(), formatHealth(bee), formatStatus(bee)));
        }
        System.out.println(Message.LINE_COLONY);
        displaySummary();
    }

    // Function 2's screen: "Attacking all bees ...", then the table No / Type / Dmg /
    // Health / Status and the summary.
    public void displayAttack() {
        System.out.println(String.format(Message.ATTACKING, Constants.MIN_PERCENT,
                Constants.MAX_DAMAGE));
        System.out.println(String.format(Constants.ATTACK_FORMAT, Message.LABEL_NO,
                Message.LABEL_TYPE, Message.LABEL_DAMAGE, Message.LABEL_HEALTH,
                Message.LABEL_STATUS));
        System.out.println(Message.LINE_ATTACK);
        // one line per bee, with the damage it received
        for (BeeResponseDTO bee : colony.getBees()) {
            System.out.println(String.format(Constants.ATTACK_FORMAT, bee.getNo(),
                    bee.getType(), bee.getDamage(), formatHealth(bee), formatStatus(bee)));
        }
        System.out.println(Message.LINE_ATTACK);
        displaySummary();
    }

    // Prints "Alive: 20 Dead: 10".
    private void displaySummary() {
        System.out.println(String.format(Message.SUMMARY, colony.getAliveCount(),
                colony.getDeadCount()));
    }

    // Formats the health with two decimals, pinned to Locale.US so a Vietnamese machine
    // prints "88.00 %" and not "88,00 %".
    private String formatHealth(BeeResponseDTO bee) {
        return String.format(Locale.US, Constants.HEALTH_FORMAT, bee.getHealth());
    }

    // Turns the dead flag into the word the table shows.
    private String formatStatus(BeeResponseDTO bee) {
        // a dead bee reads "Dead", any other bee "Alive"
        if (bee.isDead()) {
            return Message.STATUS_DEAD;
        }
        return Message.STATUS_ALIVE;
    }
}
