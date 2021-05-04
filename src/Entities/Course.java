
package Entities;



import Entities.dao.CourseDao;
import Entities.dao.GenericDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;





/*
 *
 * @author Tsepetzidis Nikos
 * Course represents the program's course
 */
public class Course{
    private int id;
    private String title;
    private String stream;
    private String type;
    private LocalDate start_date;
    private LocalDate end_date;
    
    static CourseDao data = new CourseDao();                                          //This is the program's database.
    
    public Course(String title,String stream, String type, LocalDate start_date,LocalDate end_date){
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Course(int id, String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.id = id;
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }
//  id's Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
//  title's Getter and Setter
    public String getTitle(){
        return this.title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    
    
//  stream's Getter and Setter  
    public String getStream(){
        return this.stream;
    }
    public void setStream(String stream){
        this.stream = stream;
    }
    
    
    
//  type's Getter and Setter     
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    
    
//  start_date's Getter and Setter    
    public LocalDate getStart_date(){
        return this.start_date;
    }
    public void setStart_date(LocalDate start_date){
        this.start_date = start_date;
    }
  
    
    
//  end_date's Getter and Setter     
    public LocalDate getEnd_date(){
        return this.end_date;
    }
    public void setEnd_date(LocalDate end_date){
        this.end_date = end_date;
    }

    
    /**
     * Returns an ArrayList with all Courses
     * @return ArrayList with Courses
     */
    public static ArrayList<Course> getAllCourses(){
        
        return data.getAll();
    }
    
    /**
     * Returns an ArrayList with all courses that ar active in given date
     * @param date
     * @return ArrayList with Courses
     */
    public static ArrayList<Course> getAllCoursesAreActiveIn(LocalDate date){
        return data.getCoursesAreActiveIn(date);
    }
    
    
    
    /**
     * Checks if the given course exists
     * @param course
     * @return
     */
    public static boolean exists(Course course){
        return data.exists(course);
    }
    
    
    
    /**
     * Saves the Course into database
     * @param Course
     */
    public static void saveCourse(Course course){
        data.create(course);
    }
    
    
    
    /**
     * Updates the course in Database. 
     * The course's parametrs, must be updated first by the program
     */
    public void update(){
        data.update(this);
    }
    
    /**
     * Deletes the given course
     * @param course 
     */
    public static void delete(Course course){
        data.delete(course);
    }
    
    
    /**
     * Returs all course's assignments
     * @return ArrayList of Assignments
     */
    public ArrayList<Assignment> getAssignments(){
        return data.getAssignmentsForCourse(this);
    }
    
    
    /**
     * Returs all the assignments which are not registered in that course, and
     * are active in course's dates.
     * @return ArrayList of assignments
     */
    public ArrayList<Assignment> getUnregisteredAssignments(){
        return data.getAssignmentsNotInCourse(this);
    }
    
    /**
     * Returs all course's students
     * @return ArrayList of Students
     */
    public ArrayList<Student> getStudents(){
        return data.getStudentsForCourse(this);
    }
    
    
    
    /**
     * Returs all the students who are unregistered in that course
     * @return ArrayList of trainers
     */
    public ArrayList<Student> getUnregisteredStudents(){
        return data.getStudentsNotInCourse(this);
    }
    
    
    
    
    /**
     * Returs all course's trainers
     * @return ArrayList of trainers
     */
    public ArrayList<Trainer> getTrainers(){
        return data.getTrainersForCourse(this);
    }
    
    
    
    /**
     * Returs all the trainers who are unregistered in that course
     * @return ArrayList of trainers
     */
    public ArrayList<Trainer> getUnregisteredTrainers(){
        return data.getTrainersNotInCourse(this);
    }
    
    
    
    
    /**
     * Adds a student in this course
     * @param student
     */
    public void addStudent(Student student){
        data.addStudentInCourse(this, student);
    }
    
    
    
    /**
     * Adds students in this course
     * @param ArrayList of students
     */
    public void addStudents(ArrayList<Student> students){
        for (Student student : students) {
            data.addStudentInCourse(this, student);
        }
        
    }
    
    
    
    /**
     * removes a student from this course
     * @param student
     */
    public void removeStudent(Student student){
        data.removeStudentFromCourse(this, student);
    }
    
    
    
    /**
     * Adds a trainer in this course
     * @param trainer
     */
    public void addTrainer(Trainer trainer){
        data.addTrainerInCourse(this, trainer);
    }
    
    
    
    /**
     * Adds trainers in this course
     * @param ArrayList of trainers
     */
    public void addTrainers(ArrayList<Trainer> trainers){
        for (Trainer trainer : trainers) {
            data.addTrainerInCourse(this, trainer);
        }
        
    }
    
    
    
    /**
     * removes a trainer from this course
     * @param Trainer
     */
    public void removeTrainer(Trainer trainer){
        data.removeTrainerFromCourse(this, trainer);
    }
    
    
    
    
    
//  When we try to print a Course object, we return formatted the String we want to print. 
    @Override
    public String toString(){
        String format = "%1$-30s %2$-20s %3$-20s %4$-12s %5$-3s %6$-12s";
        return String.format(format, this.title, this.stream, this.type,
                this.start_date.format(DateTimeFormatter.ofPattern(GenericDao.daTiFormat)), "-", this.end_date.format(DateTimeFormatter.ofPattern(GenericDao.daTiFormat)));
    }
}
