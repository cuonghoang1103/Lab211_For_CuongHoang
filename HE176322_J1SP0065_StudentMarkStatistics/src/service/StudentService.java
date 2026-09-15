package service;

import constants.Constants;
import dto.ReportResponseDTO;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Student;
import model.StudentBuilder;

/**
 * SERVICE: the brief's "Mark Calculation" - creates the students, classifies them and
 * computes the statistics (createStudent, averageStudent, getPercentTypeStudent).
 *
 * @author HE176322
 */
public class StudentService {

    // The rule that turns an average into a type.
    private ClassificationStrategy classificationStrategy;

    // Creates the service with the classification rule it must use.
    public StudentService(ClassificationStrategy classificationStrategy) {
        this.classificationStrategy = classificationStrategy;
    }

    // The whole of Function 2: creates every student, classifies them, counts the types,
    // and packs everything the screen shows into one DTO.
    public ReportResponseDTO makeReport(ArrayList<StudentRequestDTO> requestList) {
        ArrayList<Student> students = new ArrayList<>();
        // turn every typed student into a model object
        for (StudentRequestDTO request : requestList) {
            students.add(createStudent(request));
        }
        averageStudent(students);
        ReportResponseDTO report = new ReportResponseDTO();
        // copy every classified student into the shape the view displays
        for (Student student : students) {
            report.getStudentList().add(toResponse(student));
        }
        report.setPercentMap(getPercentTypeStudent(students));
        return report;
    }

    // The brief's createStudent: builds a student from the typed values with the Builder.
    public Student createStudent(StudentRequestDTO request) {
        return new StudentBuilder()
                .withName(request.getName())
                .withClasses(request.getClasses())
                .withMaths(request.getMaths())
                .withChemistry(request.getChemistry())
                .withPhysics(request.getPhysics())
                .build();
    }

    // The brief's averageStudent: sets the average and the type of every student.
    // brief: List<Student> averageStudent(List<Student> students)
    public List<Student> averageStudent(List<Student> students) {
        // classify each student in turn
        for (Student student : students) {
            double sum = student.getMaths() + student.getChemistry()
                    + student.getPhysics();
            double average = round(sum / Constants.NUMBER_OF_SUBJECTS);
            student.setAverage(average);
            student.setType(classificationStrategy.classify(average));
        }
        return students;
    }

    // The brief's getPercentTypeStudent: the percentage of each type, one decimal.
    // brief: HashMap<String, Double> getPercentTypeStudent(List<Student> students)
    public HashMap<String, Double> getPercentTypeStudent(List<Student> students) {
        HashMap<String, Double> percentMap = new HashMap<>();
        // start every type at zero
        for (String type : Constants.TYPES) {
            percentMap.put(type, 0.0);
        }
        // nobody to count: four zeros, no division by zero
        if (students == null || students.isEmpty()) {
            return percentMap;
        }
        // count the students of each type
        for (Student student : students) {
            percentMap.put(student.getType(), percentMap.get(student.getType()) + 1);
        }
        // turn each count into a percentage of all students
        for (String type : Constants.TYPES) {
            percentMap.put(type, round(percentMap.get(type) * Constants.PERCENT
                    / students.size()));
        }
        return percentMap;
    }

    // Copies one student into the DTO the view is allowed to see.
    private StudentResponseDTO toResponse(Student student) {
        StudentResponseDTO response = new StudentResponseDTO();
        response.setName(student.getName());
        response.setClasses(student.getClasses());
        response.setAverage(student.getAverage());
        response.setType(student.getType());
        return response;
    }

    // Rounds to one decimal.
    private double round(double value) {
        return Math.round(value * Constants.ROUND_FACTOR) / Constants.ROUND_FACTOR;
    }
}
