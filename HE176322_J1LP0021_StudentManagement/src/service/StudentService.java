package service;

import constants.Constants;
import constants.Course;
import constants.Message;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import model.ReportItem;
import model.Student;
import repository.IStudentRepository;

/**
 * SERVICE and Strategy CONTEXT: every rule about students, plus the search, the sort and
 * the report. It takes the data from the repository and gives the controller only text -
 * never a model.
 *
 * @author HE176322
 */
public class StudentService {

    // Where the students are stored.
    private IStudentRepository studentRepository;

    // Order of the Find and Sort result (the Strategy): by name.
    private Comparator<Student> sortStrategy;

    // Order of the report lines: by name, then course.
    private Comparator<ReportItem> reportStrategy;

    // Creates the service with its store and the order it must sort with.
    public StudentService(IStudentRepository studentRepository,
            Comparator<Student> sortStrategy) {
        this.studentRepository = studentRepository;
        this.sortStrategy = sortStrategy;
        this.reportStrategy = new ReportComparator();
    }

    // Create, read only: how many students the list already holds (the brief's "at least
    // 10 students" counts the whole list).
    public int countStudents() {
        return studentRepository.countStudents();
    }

    // Create, check only: the student main is typing must keep every rule of the brief -
    // an id not used yet (neither in the list nor earlier in this Create), a name, a
    // semester above 0 and one of the three courses. Nothing is stored.
    public void checkStudent(StudentRequestDTO requestDTO) throws Exception {
        String id = requestDTO.getId();

        // the id is the key of every later search: it may not be blank
        if (id.isEmpty()) {
            throw new Exception(Message.ID_EMPTY);
        }

        // one id belongs to one student only
        if ((studentRepository.findById(id) != null) || isTypedBefore(requestDTO)) {
            throw new Exception(String.format(Message.ID_EXIST, id));
        }

        // a student needs a name
        if (requestDTO.getStudentName().isEmpty()) {
            throw new Exception(Message.NAME_EMPTY);
        }

        // a semester above 0, then one of the three courses (each throws when broken)
        checkSemester(requestDTO.getSemester());
        parseCourse(requestDTO.getCourseName());
    }

    // Function 1: stores every student main kept, in the typed order, and returns the line
    // "Student [id] has been added." of each one.
    public ArrayList<String> createStudents(StudentRequestDTO requestDTO) throws Exception {
        ArrayList<String> messageList = new ArrayList<>();

        // each kept student is checked once more against the list, then stored
        for (StudentRequestDTO studentDTO : requestDTO.getStudentList()) {
            Student student = null;

            // a second student with the same id would be refused here
            checkStudent(studentDTO);
            student = new Student(studentDTO.getId(), studentDTO.getStudentName(),
                    studentDTO.getSemester(), parseCourse(studentDTO.getCourseName()));
            studentRepository.addStudent(student);
            messageList.add(String.format(Message.ADD_SUCCESS, student.getId()));
        }

        return messageList;
    }

    // Function 2: finds the students whose name contains the text (ignoring case), sorts
    // them with Collections.sort and the strategy, and returns their rows (name, semester,
    // course).
    public ArrayList<String> findAndSort(StudentRequestDTO requestDTO) throws Exception {
        String searchText = requestDTO.getSearchText().toLowerCase();
        ArrayList<Student> foundList = new ArrayList<>();
        ArrayList<String> rowList = new ArrayList<>();

        // nothing created yet: nothing to find
        checkNotEmpty();

        // a blank text would match everybody: refuse it
        if (searchText.isEmpty()) {
            throw new Exception(Message.KEYWORD_EMPTY);
        }

        // keep every student whose name contains the text
        for (Student student : studentRepository.findAll()) {
            // the brief: "student name or a part of student name"
            if (student.isNameContains(searchText)) {
                foundList.add(student);
            }
        }

        // the brief's Guidelines: Collections.sort() with the compare() of a Comparator
        Collections.sort(foundList, sortStrategy);

        // the text of each row comes from the model's toString()
        for (Student student : foundList) {
            rowList.add(student.toString());
        }

        return rowList;
    }

    // Function 3, check only: the id main read must belong to a student - the brief finds
    // the student by id BEFORE its question U or D. Nothing is changed.
    public void checkExistStudent(StudentRequestDTO requestDTO) throws Exception {
        requireStudent(requestDTO.getId());
    }

    // Function 3, answer U: changes the fields the user typed (blank keeps the old value)
    // only when all of them are legal, then returns "Student [id] has been updated.".
    public String updateStudent(StudentRequestDTO requestDTO) throws Exception {
        Student oldStudent = requireStudent(requestDTO.getId());
        Student newStudent = new Student(oldStudent.getId(), oldStudent.getStudentName(),
                oldStudent.getSemester(), oldStudent.getCourseName());

        // a new semester was typed: it must be greater than 0
        if (requestDTO.getSemester() != null) {
            checkSemester(requestDTO.getSemester());
            newStudent.setSemester(requestDTO.getSemester());
        }

        // a new course was typed: it must be one of the three
        if (!requestDTO.getCourseName().isEmpty()) {
            newStudent.setCourseName(parseCourse(requestDTO.getCourseName()));
        }

        // a new name was typed: it replaces the old one
        if (!requestDTO.getStudentName().isEmpty()) {
            newStudent.setStudentName(requestDTO.getStudentName());
        }

        // every value is legal: the new version takes the place of the old one
        studentRepository.updateStudent(newStudent);
        return String.format(Message.UPDATE_SUCCESS, newStudent.getId());
    }

    // Function 3, answer D: removes the student, then returns "Student [id] has been
    // deleted.".
    public String deleteStudent(StudentRequestDTO requestDTO) throws Exception {
        Student student = requireStudent(requestDTO.getId());

        // the stored id (not the typed one, which may differ in case) goes in the answer
        studentRepository.deleteStudent(student.getId());
        return String.format(Message.DELETE_SUCCESS, student.getId());
    }

    // Function 4: groups the students by (name, course), counts each group, and returns one
    // line per group - the brief's "Student name | Course | Total of Course".
    public ArrayList<String> report() throws Exception {
        LinkedHashMap<String, ReportItem> reportMap = new LinkedHashMap<>();
        ArrayList<ReportItem> reportItemList = new ArrayList<>();
        ArrayList<String> rowList = new ArrayList<>();

        // nothing created yet: no report
        checkNotEmpty();

        // put every student into the group of its name and course
        for (Student student : studentRepository.findAll()) {
            String key = String.format(Constants.REPORT_KEY_FORMAT,
                    student.getStudentName().toLowerCase(), student.getCourseName().name());
            ReportItem reportItem = reportMap.get(key);

            // first student of this group: open a new line with a total of 1
            if (reportItem == null) {
                reportMap.put(key, new ReportItem(student.getStudentName(),
                        student.getCourseName()));
            } else {
                // the group exists: one more course for this student
                reportItem.increaseTotal();
            }
        }

        // by name, then course, so all lines of one student sit together
        reportItemList.addAll(reportMap.values());
        Collections.sort(reportItemList, reportStrategy);

        // the text of each line comes from the model's toString()
        for (ReportItem item : reportItemList) {
            rowList.add(item.toString());
        }

        return rowList;
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
        Student student = null;

        // nothing created yet
        checkNotEmpty();

        // a blank id cannot name a student
        if (id.isEmpty()) {
            throw new Exception(Message.ID_EMPTY);
        }

        // look the id up in the list
        student = studentRepository.findById(id);

        // nobody has this id
        if (student == null) {
            throw new Exception(String.format(Message.ID_NOT_EXIST, id));
        }

        return student;
    }

    // Tells whether the id of the student being typed was typed earlier in this Create
    // (those students are not stored yet, so the repository cannot see them).
    private boolean isTypedBefore(StudentRequestDTO requestDTO) {
        // compare with every student kept so far in this Create
        for (StudentRequestDTO studentDTO : requestDTO.getStudentList()) {
            // same id, whatever the case
            if (studentDTO.getId().equalsIgnoreCase(requestDTO.getId())) {
                return true;
            }
        }

        return false;
    }

    // Checks the semester is greater than 0.
    private void checkSemester(int semester) throws Exception {
        // 0 or negative is not a semester
        if (semester < Constants.MIN_SEMESTER) {
            throw new Exception(Message.INVALID_SEMESTER);
        }
    }

    // Turns the typed course into one of the three courses, or throws.
    private Course parseCourse(String courseName) throws Exception {
        Course course = Course.findByLabel(courseName);

        // the brief: "There are only three courses"
        if (course == null) {
            throw new Exception(String.format(Message.INVALID_COURSE, Course.joinLabels()));
        }

        return course;
    }
}
