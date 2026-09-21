package repository;

import constants.Message;
import dto.DoctorRequestDTO;
import java.util.LinkedHashMap;
import model.Doctor;

/**
 * REPOSITORY: holds the doctors and performs simple CRUD on them - the brief's DoctorHash.
 * No rule of the screen, no print.
 *
 * @author HE176322
 */
public class DoctorRepository {

    // The "database": doctor code -> doctor, in the order they were added.
    private LinkedHashMap<String, Doctor> doctorMap;

    // Creates an empty repository.
    public DoctorRepository() {
        doctorMap = new LinkedHashMap<>();
    }

    // Throws when the database does not exist, as the brief asks every function to do
    // ("Database does not exist" when the HashMap is null).
    private void checkDatabase() throws Exception {
        // the brief's first rule for all four functions
        if (doctorMap == null) {
            throw new Exception(Message.DATABASE_NOT_EXIST);
        }
    }

    // Tells whether a doctor with this code is stored.
    public boolean isExistDoctor(String code) throws Exception {
        // the brief: "Database does not exist" when the HashMap is null
        checkDatabase();
        return doctorMap.containsKey(code);
    }

    // Stores a new doctor built from the request (the brief's "Put function").
    public boolean addDoctor(DoctorRequestDTO requestDTO) throws Exception {
        Doctor doctor = new Doctor(requestDTO.getCode(), requestDTO.getName(),
                requestDTO.getSpecialization(), requestDTO.getAvailability());

        // the brief: "Database does not exist" when the HashMap is null
        checkDatabase();

        // the code is the key; true = the doctor is added
        doctorMap.put(doctor.getCode(), doctor);
        return true;
    }

    // Updates an existing doctor; a blank field keeps the old value.
    public boolean updateDoctor(DoctorRequestDTO requestDTO) throws Exception {
        Doctor doctor = null;

        // the brief: "Database does not exist" when the HashMap is null
        checkDatabase();
        doctor = doctorMap.get(requestDTO.getCode());

        // only a non-blank name replaces the old one
        if (!requestDTO.getName().isEmpty()) {
            doctor.setName(requestDTO.getName());
        }

        // only a non-blank specialization replaces the old one
        if (!requestDTO.getSpecialization().isEmpty()) {
            doctor.setSpecialization(requestDTO.getSpecialization());
        }

        // null availability means the user left it blank
        if (requestDTO.getAvailability() != null) {
            doctor.setAvailability(requestDTO.getAvailability());
        }

        return true;
    }

    // Removes the doctor stored under the request's code (the brief's "delete function").
    public boolean deleteDoctor(DoctorRequestDTO requestDTO) throws Exception {
        // the brief: "Database does not exist" when the HashMap is null
        checkDatabase();
        return doctorMap.remove(requestDTO.getCode()) != null;
    }

    // Finds every doctor whose code, name or specialization contains the text, ignoring
    // upper/lower case (the brief's own data mixes "Orthopedics" and "orthodontic").
    // Each doctor found is handed out as the table row its toString() gives.
    public LinkedHashMap<String, String> searchDoctor(DoctorRequestDTO requestDTO)
            throws Exception {
        String text = requestDTO.getSearchText().trim().toLowerCase();
        LinkedHashMap<String, String> foundMap = new LinkedHashMap<>();

        // the brief: "Database does not exist" when the HashMap is null
        checkDatabase();

        // the brief: the values of the HashMap, one by one, with "contains"
        for (Doctor doctor : doctorMap.values()) {
            // keep the doctor when any of the three fields contains the text
            if (contains(doctor.getCode(), text) || contains(doctor.getName(), text) ||
                    contains(doctor.getSpecialization(), text)) {
                foundMap.put(doctor.getCode(), doctor.toString());
            }
        }

        return foundMap;
    }

    // Case-insensitive "contains" used by the search; private because only this class
    // needs it.
    private boolean contains(String field, String text) {
        return (field != null) && field.toLowerCase().contains(text);
    }
}
