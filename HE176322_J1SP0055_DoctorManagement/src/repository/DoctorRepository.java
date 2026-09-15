package repository;

import constants.Message;
import dto.DoctorRequestDTO;
import dto.DoctorResponseDTO;
import java.util.LinkedHashMap;
import model.Doctor;

/**
 * REPOSITORY: holds the doctors and performs simple CRUD on them.
 *
 * @author HE176322
 */
public class DoctorRepository {

    // The "database": doctor code -> doctor.
    private LinkedHashMap<String, Doctor> doctorMap = new LinkedHashMap<>();

    // Creates an empty repository.
    public DoctorRepository() {
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
        checkDatabase();
        return doctorMap.containsKey(code);
    }

    // Stores a new doctor built from the request.
    public boolean addDoctor(DoctorRequestDTO requestDTO) throws Exception {
        checkDatabase();
        Doctor doctor = new Doctor(requestDTO.getCode(), requestDTO.getName(),
                requestDTO.getSpecialization(), requestDTO.getAvailability());
        doctorMap.put(doctor.getCode(), doctor);
        return true;
    }

    // Updates an existing doctor.
    public boolean updateDoctor(DoctorRequestDTO requestDTO) throws Exception {
        checkDatabase();
        Doctor doctor = doctorMap.get(requestDTO.getCode());
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

    // Removes the doctor stored under the request's code.
    public boolean deleteDoctor(DoctorRequestDTO requestDTO) throws Exception {
        checkDatabase();
        return doctorMap.remove(requestDTO.getCode()) != null;
    }

    // Finds every doctor whose code, name or specialization contains the text, ignoring
    // upper/lower case (the brief's own data mixes "Orthopedics" and "orthodontic").
    public LinkedHashMap<String, DoctorResponseDTO> searchDoctor(DoctorRequestDTO requestDTO)
            throws Exception {
        checkDatabase();
        String text = requestDTO.getSearchText().trim().toLowerCase();
        LinkedHashMap<String, DoctorResponseDTO> result = new LinkedHashMap<>();
        // look at every stored doctor once
        for (Doctor doctor : doctorMap.values()) {
            // keep the doctor when any of the three fields contains the text
            if (contains(doctor.getCode(), text) || contains(doctor.getName(), text)
                    || contains(doctor.getSpecialization(), text)) {
                result.put(doctor.getCode(), toResponse(doctor));
            }
        }
        return result;
    }

    // Case-insensitive "contains" used by the search; private because only this class
    // needs it.
    private boolean contains(String field, String text) {
        return field != null && field.toLowerCase().contains(text);
    }

    // Copies a model object into the DTO the view is allowed to see.
    private DoctorResponseDTO toResponse(Doctor doctor) {
        return new DoctorResponseDTO(doctor.getCode(), doctor.getName(),
                doctor.getSpecialization(), doctor.getAvailability());
    }
}
