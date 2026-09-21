package service;

import constants.Constants;
import dto.ReportRequestDTO;
import dto.ReportResponseDTO;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.HashMap;
import java.util.List;
import model.Student;
import model.StudentBuilder;
import repository.StudentRepository;

/**
 * SERVICE: the brief's "Mark Calculation" - creates the students, keeps them in the
 * repository, classifies them and computes the statistics (createStudent, averageStudent,
 * getPercentTypeStudent). Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class StudentService {

    // The rule that turns an average into a type (the Strategy).
    private IClassificationStrategy classificationStrategy;

    // Keeps the students the service works on (Service -> Repository -> Model).
    private StudentRepository studentRepository;

    // Creates the service with an empty repository and the classification rule it must use.
    public StudentService(IClassificationStrategy classificationStrategy) {
        studentRepository = new StudentRepository();
        this.classificationStrategy = classificationStrategy;
    }

    // The whole of Function 2: creates and stores every student, classifies them, counts
    // the types, and packs everything the screen shows into one DTO.
    public ReportResponseDTO makeReport(ReportRequestDTO requestDTO) {
        // brief: averageStudent returns List<Student>, so the classified list keeps that type
        List<Student> classifiedList = null;
        ReportResponseDTO responseDTO = new ReportResponseDTO();

        // turn every typed student into a model object, kept by the repository
        for (StudentRequestDTO studentDTO : requestDTO.getStudentList()) {
            studentRepository.addStudent(createStudent(studentDTO));
        }

        // the brief's averageStudent works on the list the repository holds
        classifiedList = averageStudent(studentRepository.getStudentList());

        // copy every classified student into the shape the view displays
        for (Student student : classifiedList) {
            responseDTO.getStudentList().add(convertToResponse(student));
        }

        // the statistics of the brief, by the keys A, B, C, D
        responseDTO.setPercentMap(getPercentTypeStudent(classifiedList));
        return responseDTO;
    }

    // The brief's createStudent: builds a student from the typed values with the Builder;
    // the five values travel in one DTO (lecturer: no method with 3 parameters).
    // brief: Student createStudent(String name, String classes, double maths,
    //        double chemistry, double physics)
    private Student createStudent(StudentRequestDTO studentDTO) {
        return new StudentBuilder()
                .setName(studentDTO.getName())
                .setClasses(studentDTO.getClasses())
                .setMaths(studentDTO.getMaths())
                .setChemistry(studentDTO.getChemistry())
                .setPhysics(studentDTO.getPhysics())
                .build();
    }

    // The brief's averageStudent: sets the average and the type of every student.
    // brief: List<Student> averageStudent(List<Student> students)
    private List<Student> averageStudent(List<Student> studentList) {
        double sum = 0;
        double average = 0;

        // classify each student in turn: the average is rounded BEFORE it is classified
        for (Student student : studentList) {
            sum = student.getMaths() + student.getChemistry() + student.getPhysics();
            average = convertToOneDecimal(sum / Constants.NUMBER_OF_SUBJECTS);
            student.setAverage(average);
            student.setType(classificationStrategy.classify(average));
        }

        return studentList;
    }

    // The brief's getPercentTypeStudent: the percentage of each type, one decimal.
    // brief: HashMap<String, Double> getPercentTypeStudent(List<Student> students)
    private HashMap<String, Double> getPercentTypeStudent(List<Student> studentList) {
        HashMap<String, Double> percentMap = new HashMap<>();
        double percent = 0;

        // start every type at zero
        for (String type : Constants.TYPE_ARRAY) {
            percentMap.put(type, 0.0);
        }

        // nobody to count: four zeros, no division by zero
        if ((studentList == null) || studentList.isEmpty()) {
            return percentMap;
        }

        // count the students of each type
        for (Student student : studentList) {
            percentMap.put(student.getType(), percentMap.get(student.getType()) + 1);
        }

        // turn each count into a percentage of all students
        for (String type : Constants.TYPE_ARRAY) {
            percent = (percentMap.get(type) * Constants.PERCENT) / studentList.size();
            percentMap.put(type, convertToOneDecimal(percent));
        }

        return percentMap;
    }

    // Copies one student into the DTO the view is allowed to see.
    private StudentResponseDTO convertToResponse(Student student) {
        StudentResponseDTO studentDTO = new StudentResponseDTO();

        // the four lines of a "Student Info" block
        studentDTO.setName(student.getName());
        studentDTO.setClasses(student.getClasses());
        studentDTO.setAverage(student.getAverage());
        studentDTO.setType(student.getType());
        return studentDTO;
    }

    // Rounds a value to one decimal: 7.533 becomes 7.5 (times 10, round, divided by 10.0).
    private double convertToOneDecimal(double value) {
        return Math.round(value * Constants.ROUND_FACTOR) / Constants.ROUND_FACTOR;
    }
}
