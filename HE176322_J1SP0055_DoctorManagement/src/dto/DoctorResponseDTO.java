package dto;

import java.util.LinkedHashMap;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean (private fields, public no-argument constructor, getters/setters). Add, update
 * and delete fill the message; search fills the doctor map.
 *
 * @author HE176322
 */
public class DoctorResponseDTO {

    // The one-line result, e.g. "Add doctor successfully."; null for a search.
    private String message;

    // The doctors a search found: code -> table row (the text of Doctor.toString()); null
    // when the answer is a message, empty when nobody matched.
    private LinkedHashMap<String, String> doctorMap;

    // JavaBean constructor: an empty answer, filled through the setters.
    public DoctorResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the doctors found.
    public LinkedHashMap<String, String> getDoctorMap() {
        return doctorMap;
    }

    // Sets the doctors found.
    public void setDoctorMap(LinkedHashMap<String, String> doctorMap) {
        this.doctorMap = doctorMap;
    }
}
