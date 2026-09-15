package view;

import constants.Message;
import dto.StudentResponseDTO;
import java.util.List;

/**
 * VIEW: prints the sorted students, one block each.
 *
 * @author HE176322
 */
public class StudentView {

    // Creates the view.
    public StudentView() {
    }

    // The brief's Function 2 method: prints every student under a numbered title, in the
    // order of the list.
    // brief: void display(List<Student> students) - List kept, Student -> DTO
    public void display(List<StudentResponseDTO> students) {
        // one block per student; the number shown starts at 1
        for (int i = 0; i < students.size(); i++) {
            StudentResponseDTO student = students.get(i);
            System.out.println(String.format(Message.TITLE_STUDENT, i + 1));
            System.out.println(Message.LABEL_NAME + student.getName());
            System.out.println(Message.LABEL_CLASSES + student.getClasses());
            System.out.println(Message.LABEL_MARK + student.getMark());
        }
    }
}
