package service;

import constants.Constants;
import constants.Course;
import constants.Message;
import dto.ReportResponseDTO;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import model.ReportItem;
import model.Student;
import repository.IStudentRepository;

/**
 * SERVICE and Strategy CONTEXT: every rule about students, plus the search, the sort and
 * the report.
 *
 * @author HE176322
 */
public class StudentService {

    // Where the students are stored.
    private IStudentRepository studentRepository;
    // Order of the Find and Sort result (the Strategy): by name.
    private Comparator<Student> sortStrategy;
    // Order of the report lines: by name, then course.
    private Comparator<ReportItem> reportStrategy = new ReportComparator();

    // Creates the service with its store and the order it must sort with.
    public StudentService(IStudentRepository studentRepository,
            Comparator<Student> sortStrategy) {
        this.studentRepository = studentRepository;
        this.sortStrategy = sortStrategy;
    }

    // Function 1: checks a new student and stores it.
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO)
            throws Exception {
        String id = requestDTO.getId();
        // the id is the key of every later search: it may not be blank
        if (id.isEmpty()) {
            throw new Exception(Message.ID_EMPTY);
        }
        // one id belongs to one student only
        if (studentRepository.findById(id) != null) {
            throw new Exception(String.format(Message.ID_EXIST, id));
        }
        // a student needs a name
        if (requestDTO.getStudentName().isEmpty()) {
            throw new Exception(Message.NAME_EMPTY);
        }
        checkSemester(requestDTO.getSemester());
        Course course = parseCourse(requestDTO.getCourseName());
        Student student = new Student(id, requestDTO.getStudentName(),
                requestDTO.getSemester(), course);
        studentRepository.addStudent(student);
        return toResponse(student);
    }

    // Counts the stored students (the Create screen needs at least 10).
    public int countStudents() {
        return studentRepository.countStudents();
    }

    // Function 2: finds the students whose name contains the text (ignoring case), then
    // sorts them with Collections.sort and the strategy.
    public ArrayList<StudentResponseDTO> findAndSort(StudentRequestDTO requestDTO)
            throws Exception {
        checkNotEmpty();
        String text = requestDTO.getSearchText();
        // a blank text would match everybody: refuse it
        if (text.isEmpty()) {
            throw new Exception(Message.KEYWORD_EMPTY);
        }
        String lower = text.toLowerCase();
        ArrayList<Student> found = new ArrayList<>();
        // keep every student whose name contains the text
        for (Student student : studentRepository.findAll()) {
            // the brief: "student name or a part of student name"
            if (student.isNameContains(lower)) {
                found.add(student);
            }
        }
        Collections.sort(found, sortStrategy);
        ArrayList<StudentResponseDTO> result = new ArrayList<>();
        // copy each match into the DTO the view is allowed to see
        for (Student student : found) {
            result.add(toResponse(student));
        }
        return result;
    }

    // Function 3, first step: finds a student by id.
    public StudentResponseDTO findStudent(StudentRequestDTO requestDTO)
            throws Exception {
        return toResponse(requireStudent(requestDTO.getId()));
    }

    // Function 3, choice U: changes the given fields; blank keeps the old value.
    public StudentResponseDTO updateStudent(StudentRequestDTO requestDTO)
            throws Exception {
        Student old = requireStudent(requestDTO.getId());
        Student updated = new Student(old.getId(), old.getStudentName(),
                old.getSemester(), old.getCourseName());
        // a new semester was typed: it must be legal
        if (requestDTO.getSemester() != null) {
            checkSemester(requestDTO.getSemester());
            updated.setSemester(requestDTO.getSemester());
        }
        // a new course was typed: it must be one of the three
        if (!requestDTO.getCourseName().isEmpty()) {
            updated.setCourseName(parseCourse(requestDTO.getCourseName()));
        }
        // a new name was typed: replace the old one
        if (!requestDTO.getStudentName().isEmpty()) {
            updated.setStudentName(requestDTO.getStudentName());
        }
        studentRepository.updateStudent(updated);
        return toResponse(updated);
    }

    // Function 3, choice D: removes the student.
    public StudentResponseDTO deleteStudent(StudentRequestDTO requestDTO)
            throws Exception {
        Student student = requireStudent(requestDTO.getId());
        studentRepository.deleteStudent(student.getId());
        return toResponse(student);
    }

    // Function 4: groups the students by (name, course) and counts each group - the
    // brief's "Student name | Course | Total of Course".
    public ArrayList<ReportResponseDTO> report() throws Exception {
        checkNotEmpty();
        LinkedHashMap<String, ReportItem> groups = new LinkedHashMap<>();
        // put every student into the group of its name and course
        for (Student student : studentRepository.findAll()) {
            String key = student.getStudentName().toLowerCase()
                    + Constants.KEY_SEPARATOR + student.getCourseName().name();
            ReportItem item = groups.get(key);
            // first student of this group: open a new line
            if (item == null) {
                groups.put(key, new ReportItem(student.getStudentName(),
                        student.getCourseName()));
            } else {
                // the group exists: one more course for this student
                item.increaseTotal();
            }
        }
        ArrayList<ReportItem> items = new ArrayList<>(groups.values());
        Collections.sort(items, reportStrategy);
        ArrayList<ReportResponseDTO> result = new ArrayList<>();
        // copy each line into the DTO the view is allowed to see
        for (ReportItem item : items) {
            result.add(new ReportResponseDTO(item.getStudentName(),
                    item.getCourse().getLabel(), item.getTotal()));
        }
        return result;
    }

    // Throws when there is no student yet (find, update, delete, report).
    private void checkNotEmpty() throws Exception {
        // nothing created yet
        if (studentRepository.countStudents() == 0) {
            throw new Exception(Message.LIST_EMPTY);
        }
    }

    // Finds a student by id or explains why not.
    private Student requireStudent(String id) throws Exception {
        checkNotEmpty();
        // a blank id cannot name a student
        if (id.isEmpty()) {
            throw new Exception(Message.ID_EMPTY);
        }
        Student student = studentRepository.findById(id);
        // nobody has this id
        if (student == null) {
            throw new Exception(String.format(Message.ID_NOT_EXIST, id));
        }
        return student;
    }

    // Checks the semester is greater than 0.
    private void checkSemester(int semester) throws Exception {
        // 0 or negative is not a semester
        if (semester < Constants.MIN_SEMESTER) {
            throw new Exception(Message.INVALID_SEMESTER);
        }
    }

    // Turns the typed course into one of the three courses.
    private Course parseCourse(String courseName) throws Exception {
        Course course = Course.fromLabel(courseName);
        // the brief: "There are only three courses"
        if (course == null) {
            throw new Exception(String.format(Message.INVALID_COURSE, Course.labels()));
        }
        return course;
    }

    // Copies a model object into the DTO the view may see.
    private StudentResponseDTO toResponse(Student student) {
        return new StudentResponseDTO(student.getId(), student.getStudentName(),
                student.getSemester(), student.getCourseName().getLabel());
    }
}
