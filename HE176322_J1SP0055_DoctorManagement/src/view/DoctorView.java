package view;

import constants.Constants;
import constants.Message;
import dto.DoctorResponseDTO;
import java.util.LinkedHashMap;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class DoctorView {

    // The rows to display, handed over by the controller.
    private LinkedHashMap<String, DoctorResponseDTO> doctorMap;

    // Receives the rows the next display() call will print.
    public void setDoctorMap(LinkedHashMap<String, DoctorResponseDTO> doctorMap) {
        this.doctorMap = doctorMap;
    }

    // Prints the search result: a title, then either "No doctor found." or a header and
    // one line per doctor.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        // nobody matched the search text
        if (doctorMap == null || doctorMap.isEmpty()) {
            System.out.println(Message.NOT_FOUND);
            return;
        }
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_CODE,
                Message.LABEL_NAME, Message.LABEL_SPECIALIZATION,
                Message.LABEL_AVAILABILITY));
        // one line per doctor; toString() of the DTO is already padded
        for (DoctorResponseDTO doctor : doctorMap.values()) {
            System.out.println(doctor);
        }
    }

    // Prints a one-line result such as "Add doctor successfully.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
