
package Entities;



import Entities.dao.GenericDao;
import Entities.dao.StudentDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



/**
 *
 * @author Tsepetzidis Nikos
 * Student represents the program's student.
 */
public class Student {
    private int id;
    private String firstName ;
    private String lastName ;
    private LocalDate dateOfBirth ;
    private double tuitionFees ;
    
    static StudentDao data = new StudentDao();                                          //This is the program's database.

    
    public Student(String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public Student(int id, String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }
    
    
    
    
    
//  firstName's Getter and Setter 
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    
    
//  lastName's Getter and Setter 
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    
    
//  dateofBirth's Getter and Setter 
    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    public LocalDate getDateOfBirth(){
        return this.dateOfBirth;
    }
    
    
    
//  tuitionFees's Getter and Setter 
    public void setTuitionFees(double tuitionFees){
        this.tuitionFees = tuitionFees;
    }
    
    public double getTuitionFees(){
        return this.tuitionFees;
    }

//  id's Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    
    /**
     * Returns an ArrayList with all Students
     * @return ArrayList with Students
     */
    public static ArrayList<Student> getAllStudents(){
        
        return data.getAll();
    }
    
    /**
     * Saves the student into the database
     * @param student
     */
    public static void saveStudent(Student student){
        data.create(student);
    }
    
    
    /**
     * Checks if the given student exists
     * @param student
     * @return boolean
     */
    public static boolean exists(Student student){
        return data.exists(student);
    }
    
    
    /**
     * Updates the student in Database. 
     * The student's parametrs, must be updated first by the program
     */
    public void update() {
        data.update(this);
    }
    
    
    
    /**
     * Returns all the student's courses
     * @return ArrayList with courses
     */
    public ArrayList<Course> getCourses(){
        return data.getCoursesForStudent(this);
    }
    
    /**
     * Returns the courses that ara available for registering, for the student
     * @return ArrayList of courses
     */
    public ArrayList<Course> getAvaliableCourses(){
        return data.getAvaliableCoursesFor(this);
        
    }
    
    
    /**
     * Returns all the student's assignments
     * @return ArrayList with assignments
     */
    public ArrayList<Assignment> getAssignments(){
        return data.getAssignmentsForStudent(this);
    }
    
    
    /**
     * Return student's rated assignments from the given course
     * @param course
     * @return ArrayList of Assignments
     */
    public ArrayList<Assignment> getRatedAssignmentsFromCourse(Course course){
        return data.getRatedAssignmentsFromStudentCourse(this,course);
    }
    
    
    
    
    /**
     * Return student's unrated assignments from the given course
     * @param course
     * @return ArrayList of Assignments
     */
    public ArrayList<Assignment> getUnratedAssignmentsFromCourse(Course course){
        return data.getUnratedAssignmentsFromStudentCourse(this,course);
    }
    
    
    
    /**
     * Return student's all assignments and grades from the given course, in 
     * GradesForTables objects,which we need them for the front-end.
     * @param course
     * @return ArrayList of GradesForTables
     */
    public ArrayList<GradesForTables> getRatedAssignmentsForFrontENd(Course course){
        return data.getAssignmentsForTableView(this,course);
    }
    
    
    
    /**
     * Returns the student's that are register in more assignment
     * than the given number
     * @return ArrayList of Students
     */
    public static ArrayList<Student> getStudentsWhenLessonsMoreThan(int num){
        
        return data.getStudentsWhenLessonsMoreThan(num);
    }
    
    
    
    
    /**
     * Deletes the given student
     * @param student 
     */
    public static void delete(Student student){
        data.delete(student);
    }
    
    
  
    
//  When we try to print a Student object, we return formatted the String we want to print. 
    @Override
    public String toString(){
        String format = "%1$-15s %2$-20s %3$-15s %4$-18s";
        return String.format(format, this.firstName, this.lastName, this.dateOfBirth.format(DateTimeFormatter.ofPattern(GenericDao.daTiFormat)),
                            "  -  " + this.tuitionFees +"€");
    }
}
