package view;

import constants.Message;
import dto.StudentDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: prints the sorted students, one block each. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class StudentView {

    // The sorted students to print, handed over by the controller.
    private StudentResponseDTO responseDTO;

    // Creates the view.
    public StudentView() {
    }

    // Receives the students the next display() will print.
    public void setResponseDTO(StudentResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // The brief's Function 2 method: prints every student under a numbered title, in the
    // order of the list. The list comes from the attribute, not from a parameter.
    // brief: void display(List<Student> students)
    public void display() {
        ArrayList<StudentDTO> studentList = responseDTO.getStudentList();
        StudentDTO studentDTO = null;

        // one block per student; the number shown starts at 1
        for (int i = 0; i < studentList.size(); i++) {
            studentDTO = studentList.get(i);
            System.out.println(String.format(Message.TITLE_STUDENT, i + 1));
            System.out.println(String.format(Message.LABEL_NAME, studentDTO.getName()));
            System.out.println(String.format(Message.LABEL_CLASSES, studentDTO.getClasses()));
            System.out.println(String.format(Message.LABEL_MARK, studentDTO.getMark()));
        }
    }
}
