
package Entities;




import Entities.dao.AssignmentDao;
import Entities.dao.GenericDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * Assignment represents the program's assignment
 */
public class Assignment {
    
    private int id;
    private String title;
    private String description;
    private LocalDate subDateTime;
    private int oralMark;
    private int totalMark;
    
    static AssignmentDao data = new AssignmentDao();                              //This is the program's database.
    
    public Assignment(String title, String description, LocalDate subDateTime, int oralMark, int totalMark){
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;}

    public Assignment(int id, String title, String description, LocalDate subDateTime, int oralMark, int totalMark) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
//  title's Getter and Setter
    public void setTitle(String title){
        this.title = title;}
    
    public String getTitle(){
        return this.title;}
    
    
    
//  description's Getter and Setter
    public void setDescription(String description){
        this.description = description;}
    
    public String getDescription(){
        return this.description;}
    
    
    
//  subDateTime's Getter and Setter
    public void setSubDateTime(LocalDate subDateTime){
        this.subDateTime = subDateTime;}
    
    public LocalDate getSubDateTime(){
        return this.subDateTime;}
    
    
    
//  oralMark's Getter and Setter
    public void setOralMark(int oralMark){
        this.oralMark = oralMark;}
    
    public int getOralMark(){
        return this.oralMark;}
    
    
    
//  totalMark's Getter and Setter
    public void setTotalMark(int totalMark){
        this.totalMark = totalMark;}
    
    public int getTotalMark(){
        return this.totalMark;}
    
    
    
    /**
     * returns the deadline's week's number of the year
     * @return int
     */
    public int getWeekOfYearDeadlint(){
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
        int weekNumber = this.getSubDateTime().get(woy);
        return weekNumber;
    }
    
    
    
    /**
     * Returns an ArrayList with all Assignments
     * @return ArrayList with Assignments
     */
    public static ArrayList<Assignment> getAllAssignments(){
        
        return data.getAll();
    }
    
    
    /**
     * Returns an Assignment that has the given id
     * @param id
     * @return Assignment
     */
    public static Assignment getAssignmentById(int id){
        
        return data.getById(id);
    }
    
    
    /**
     * Returns an ArrayList with all Assignments in the given week
     * @param weekNumber
     * @return ArrayList with Assignments
     */
    public static ArrayList<Assignment> getAllAssignmentsInWeek(int weekNumber){
        
        return data.getAssignmentsForWeek(weekNumber);
    }
    
    
    
    /**
     * Saves the assignment into the database
     * @param assignment
     */
    public static void saveAssignment(Assignment assignment){
        data.create(assignment);
    }
    
    
    
    /**
     * Checks if the given assignment exists
     * @param assignment
     * @return
     */
    public static boolean exists(Assignment assignment){
        return data.exists(assignment);
    }
    
    
    
    /**
     * Updates the assignment in Database. 
     * The assignment's parametrs, must be updated first by the program
     */
    public void update(){
        data.update(this);
    }
    
    /**
     * Deletes the given assignment
     * @param student 
     */
    public static void delete(Assignment assignment){
        data.delete(assignment);
    }
    
    
    /**
     * Adds course in this assignment
     * @param course
     */
    public void addCourse(Course course){
        data.addCourseInAssignment(this, course);
    }
    
    
    
    /**
     * Adds courses in this assignment
     * @param ArrayList course
     */
    public void addCourses(ArrayList<Course> courses){
        for (Course course : courses) {
            data.addCourseInAssignment(this, course);
        }
    }
    
    
    
    
    /**
     * remove course from this assignment
     * @param course
     */
    public void removeCourse(Course course){
        data.removeCourseFromAssignment(this, course);
    }
    
    
    
    /**
     * Returns all the assignment's courses
     * @return ArrayList with courses
     */
    public ArrayList<Course> getCourses(){
        return data.getCoursesForAssignment(this);
    }
    
    
    /**
     * Returns all the courses which are not registered in this assignment
     * @return ArrayList with courses
     */
    public ArrayList<Course> getUnregisteredCourses(){
        return data.getCoursesNotInAssignment(this);
    }
    
    
    
    
//  When we try to print an Assignment object, we return formatted the String we want to print. 
    @Override
    public String toString(){
        String format = "%1$-30s %2$-55s %3$-15s %4$-5s %5$-5s %6$-5s %7$-5s";
        return String.format(format, this.title, this.description, this.subDateTime.format(DateTimeFormatter.ofPattern(GenericDao.daTiFormat)),
                "O.M.", this.oralMark, "T.M.", this.totalMark);}
    
    
}
