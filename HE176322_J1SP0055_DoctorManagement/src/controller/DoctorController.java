package controller;

import constants.Message;
import dto.DoctorRequestDTO;
import dto.DoctorResponseDTO;
import repository.DoctorRepository;
import view.DoctorView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the answer to the view - one render per menu option. No Scanner, no print, no
 * model; a broken rule is thrown as an Exception(Message.X) for main to print.
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

    // Function 1 (addDoctor): adds a new doctor, then the view prints "Add doctor
    // successfully." - once.
    public void addDoctor(DoctorRequestDTO requestDTO) throws Exception {
        DoctorResponseDTO responseDTO = new DoctorResponseDTO();

        // the brief: null data cannot be added
        if (requestDTO == null) {
            throw new Exception(Message.DATA_NOT_EXIST_ADD);
        }

        // the brief: a code may appear only once in the database
        if (doctorRepository.isExistDoctor(requestDTO.getCode())) {
            throw new Exception(String.format(Message.DUPLICATE_CODE, requestDTO.getCode()));
        }

        // stored: hand the answer to the view, then render it - once for the whole flow
        doctorRepository.addDoctor(requestDTO);
        responseDTO.setMessage(Message.ADD_SUCCESS);
        doctorView.setResponseDTO(responseDTO);
        doctorView.display();
    }

    // Check only (no render): the code of an update/delete request must exist. Main calls
    // it right after the code of an update, because the brief stops there when the code
    // is unknown.
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

    // Function 2 (updateDoctor): changes an existing doctor (blank fields keep their old
    // value), then the view prints "Update doctor successfully." - once.
    public void updateDoctor(DoctorRequestDTO requestDTO) throws Exception {
        DoctorResponseDTO responseDTO = new DoctorResponseDTO();

        // the same rules as the check main already made: the controller never trusts that
        checkExistDoctor(requestDTO);

        // changed: hand the answer to the view, then render it - once for the whole flow
        doctorRepository.updateDoctor(requestDTO);
        responseDTO.setMessage(Message.UPDATE_SUCCESS);
        doctorView.setResponseDTO(responseDTO);
        doctorView.display();
    }

    // Function 3 (deleteDoctor): removes the doctor with the given code, then the view
    // prints "Delete doctor successfully." - once.
    public void deleteDoctor(DoctorRequestDTO requestDTO) throws Exception {
        DoctorResponseDTO responseDTO = new DoctorResponseDTO();

        // null data or an unknown code stops the delete
        checkExistDoctor(requestDTO);

        // removed: hand the answer to the view, then render it - once for the whole flow
        doctorRepository.deleteDoctor(requestDTO);
        responseDTO.setMessage(Message.DELETE_SUCCESS);
        doctorView.setResponseDTO(responseDTO);
        doctorView.display();
    }

    // Function 4 (searchDoctor): finds doctors whose code, name or specialization
    // contains the text, then the view prints them - once.
    public void searchDoctor(DoctorRequestDTO requestDTO) throws Exception {
        DoctorResponseDTO responseDTO = new DoctorResponseDTO();

        // the rows of the doctors found, keyed by code (empty when nobody matched)
        responseDTO.setDoctorMap(doctorRepository.searchDoctor(requestDTO));
        doctorView.setResponseDTO(responseDTO);
        doctorView.display();
    }
}
