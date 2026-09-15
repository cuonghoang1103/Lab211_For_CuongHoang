package controller;

import constants.Message;
import dto.DoctorRequestDTO;
import dto.DoctorResponseDTO;
import java.util.LinkedHashMap;
import repository.DoctorRepository;
import view.DoctorView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class DoctorController {

    // Where the doctors are stored; each controller owns its repository.
    private DoctorRepository doctorRepository;
    // Where the results are printed.
    private DoctorView doctorView;

    // Creates the controller together with its repository and view.
    public DoctorController() {
        doctorRepository = new DoctorRepository();
        doctorView = new DoctorView();
    }

    // Function 1 (addDoctor): adds a new doctor.
    public void addDoctor(DoctorRequestDTO requestDTO) throws Exception {
        // the brief: null data cannot be added
        if (requestDTO == null) {
            throw new Exception(Message.DATA_NOT_EXIST_ADD);
        }
        // the brief: a code may appear only once in the database
        if (doctorRepository.isExistDoctor(requestDTO.getCode())) {
            throw new Exception(String.format(Message.DUPLICATE_CODE,
                    requestDTO.getCode()));
        }
        doctorRepository.addDoctor(requestDTO);
        doctorView.showMessage(Message.ADD_SUCCESS);
    }

    // Checks that the code of an update/delete request exists.
    public void checkExistDoctor(DoctorRequestDTO requestDTO) throws Exception {
        // the brief: null data cannot be updated or deleted
        if (requestDTO == null) {
            throw new Exception(Message.DATA_NOT_EXIST);
        }
        // the brief: the code must exist in the database
        if (!doctorRepository.isExistDoctor(requestDTO.getCode())) {
            throw new Exception(Message.CODE_NOT_EXIST);
        }
    }

    // Function 2 (updateDoctor): changes an existing doctor; blank fields keep their old
    // value.
    public void updateDoctor(DoctorRequestDTO requestDTO) throws Exception {
        checkExistDoctor(requestDTO);
        doctorRepository.updateDoctor(requestDTO);
        doctorView.showMessage(Message.UPDATE_SUCCESS);
    }

    // Function 3 (deleteDoctor): removes the doctor with the given code.
    public void deleteDoctor(DoctorRequestDTO requestDTO) throws Exception {
        checkExistDoctor(requestDTO);
        doctorRepository.deleteDoctor(requestDTO);
        doctorView.showMessage(Message.DELETE_SUCCESS);
    }

    // Function 4 (searchDoctor): finds doctors whose code, name or specialization
    // contains the text, then lets the view print them.
    public void searchDoctor(DoctorRequestDTO requestDTO) throws Exception {
        LinkedHashMap<String, DoctorResponseDTO> result
                = doctorRepository.searchDoctor(requestDTO);
        doctorView.setDoctorMap(result);
        doctorView.display();
    }
}
