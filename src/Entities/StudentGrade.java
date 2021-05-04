
package Entities;


import Entities.dao.StudentGradeDao;

/**
 *
 * @author Tsepetzidis Nikos
 */
public class StudentGrade {
    
    
    private Student student;
    private Assignment assignment;
    private Course course;
    private int oralMark;
    private int totalMark;
    
    static StudentGradeDao data = new StudentGradeDao();

    
    public StudentGrade() {
    }


    public StudentGrade(Student student, Assignment assignment, Course course, int oralMark, int totalMark) {
        this.student = student;
        this.assignment = assignment;
        this.course = course;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

//  Setters and Getters

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getOralMark() {
        return oralMark;
    }

    public void setOralMark(int oralMark) {
        this.oralMark = oralMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }
    
    
    
    /**
     * Returns a StudentGrade
     * @param student
     * @param course
     * @param assignment
     * @return StudentGrade
     */
    public static StudentGrade getStudentGrade(Student student, Course course, Assignment assignment){
        return data.getStudentGrade(student,course,assignment);
    }
    
    
    /**
     * Saves the given StudentGrade
     * @param studentGrade
     */
    public static void saveStudentGrade(StudentGrade studentGrade) {
        data.create(studentGrade);
    }
    
    
    
    /**
     * Checks if the StudentGrade exists
     * @param Student
     * @param Course
     * @param Assignment
     * @return boolean
     */
    public static boolean exists(Student student, Course course, Assignment assignment){
        return data.exists(student, course, assignment);
    }
    
    
    
    /**
     * Updates the studentGrade in Database. 
     * The studentGrade's parameters, must be updated first by the program
     */
    public void update(){
        data.update(this);
    }
}
