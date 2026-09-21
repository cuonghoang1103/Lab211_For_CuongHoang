package view;

import constants.Constants;
import constants.Message;
import dto.BeeResponseDTO;
import dto.ColonyResponseDTO;
import java.util.Locale;

/**
 * VIEW: prints the two bee tables of the brief - the new colony and the attack report. It
 * receives the data through its attribute (the ResponseDTO), never through the parameters
 * of display().
 *
 * @author HE176322
 */
public class BeeView {

    // The table to print, handed over by the controller.
    private ColonyResponseDTO responseDTO;

    // Receives the table the next display() call will print.
    public void setResponseDTO(ColonyResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the line of the function, its table, then the
    // summary "Alive: 20   Dead: 10".
    public void display() {
        // "New bee list created: ..." or "Attacking all bees (...)..."
        System.out.println(responseDTO.getMessage());

        // Function 2's table has the Dmg column, Function 1's table does not
        if (responseDTO.isAttack()) {
            displayAttackTable();
        } else {
            // Function 1: the new colony
            displayColonyTable();
        }

        // the summary under both tables
        System.out.println(String.format(Message.SUMMARY, responseDTO.getAliveCount(),
                responseDTO.getDeadCount()));
    }

    // Function 1's table: No / Type / Health / Status between two rulers.
    private void displayColonyTable() {
        System.out.println(String.format(Constants.COLONY_FORMAT, Message.LABEL_NO,
                Message.LABEL_TYPE, Message.LABEL_HEALTH, Message.LABEL_STATUS));
        System.out.println(Message.LINE_COLONY);

        // one line per bee, in colony order
        for (BeeResponseDTO row : responseDTO.getRowList()) {
            System.out.println(String.format(Constants.COLONY_FORMAT, row.getNo(),
                    row.getType(), formatHealth(row), formatStatus(row)));
        }

        // the closing ruler
        System.out.println(Message.LINE_COLONY);
    }

    // Function 2's table: No / Type / Dmg / Health / Status between two rulers.
    private void displayAttackTable() {
        System.out.println(String.format(Constants.ATTACK_FORMAT, Message.LABEL_NO,
                Message.LABEL_TYPE, Message.LABEL_DAMAGE, Message.LABEL_HEALTH,
                Message.LABEL_STATUS));
        System.out.println(Message.LINE_ATTACK);

        // one line per bee, with the damage it received
        for (BeeResponseDTO row : responseDTO.getRowList()) {
            System.out.println(String.format(Constants.ATTACK_FORMAT, row.getNo(),
                    row.getType(), row.getDamage(), formatHealth(row), formatStatus(row)));
        }

        // the closing ruler
        System.out.println(Message.LINE_ATTACK);
    }

    // Formats the health with two decimals, pinned to Locale.US so a Vietnamese machine
    // prints "88.00 %" and not "88,00 %".
    private String formatHealth(BeeResponseDTO row) {
        return String.format(Locale.US, Constants.HEALTH_FORMAT, row.getHealth());
    }

    // Turns the dead flag into the word the table shows.
    private String formatStatus(BeeResponseDTO row) {
        // a dead bee reads "Dead", any other bee "Alive"
        if (row.isDead()) {
            return Message.STATUS_DEAD;
        }

        return Message.STATUS_ALIVE;
    }
}
