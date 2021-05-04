
package Entities;


import Entities.dao.TrainerDao;
import java.util.ArrayList;


/*
 * @author Tsepetzidis Nikos
 * 
 * Trainer represents the school's teachers.
 */
public class Trainer {
    private int id;
    private String firstName;
    private String lastName;
    private String subject;
    
    static TrainerDao data = new TrainerDao();

    
    
    public Trainer(String firstName,String lastName,String subject){
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;

        }

    public Trainer(int id, String firstName, String lastName, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
    
    
//  Getter and Setter for id
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
    
//  Getter και Setter for the Trainer's firstName   
    public String getFirstName(){
        return this.firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    
    
//  Getter και Setter for the Trainer's lasttName     
    public String getLastName(){
        return this.lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    
    
//  Getter και Setter for the Trainer's subject     
    public String getSubject(){
        return this.subject;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    
    
    
    
    /**
     * Returns an ArrayList with all Courses
     * @return ArrayList with Courses
     */
    public static ArrayList<Trainer> getAllTrainers(){
        
        return data.getAll();
    }
    
    
    
    /**
     * Saves the Trainer into database
     * @param trainer
     */
    public static void saveTrainer(Trainer trainer){
        data.create(trainer);
    }
    
    /**
     * Checks if the given trainer exists
     * @param trainer
     * @return
     */
    public static boolean exists(Trainer trainer){
        return data.exists(trainer);
    }
    
    
    /**
     * Updates the trainer in Database. 
     * The trainer's parameters, must be updated first by the program
     */
    public void update(){
        data.update(this);
    }
    
    
    /**
     * Returns all the trainer's courses
     * @return ArrayList with courses
     */
    public ArrayList<Course> getCourses(){
        return data.getCoursesForTrainer(this);
    }
    
    
    /**
     * Returns the courses that ara available for registering, for the trainer
     * @return ArrayList of courses
     */
    public ArrayList<Course> getAvaliableCourses(){
        return data.getCoursesAvailableForTrainer(this);
    }
    
    
    
    /**
     * Deletes the given trainer
     * @param student 
     */
    public static void delete(Trainer trainer){
        data.delete(trainer);
    }
}
