
package Entities.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Entities.Course;
import Entities.Trainer;

/**
 *
 * @author Tsepetzidis Nikos
 */
public class TrainerDao implements CrudInterface<Trainer>{

    private static final String FINDBYID = "SELECT * FROM trainers WHERE id = ?";
    private static final String FINDBYMAXID = "SELECT * FROM trainers WHERE id = (SELECT max(id) FROM trainers)";
    private static final String EXISTS = "SELECT * FROM trainers WHERE first_name = ? and last_name = ? and subject = ?";
    private static final String FINDALL = "SELECT * FROM trainers";
    private static final String INSERT = "insert into trainers (first_name, last_name, subject) values (?, ?, ?)";
    private static final String UPDATE = " UPDATE trainers SET first_name = ?, last_name = ?, subject = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM trainers WHERE id = ?";
    private static final String REMOVE_TRAINER_FROM_COURSES = "DELETE FROM courses_trainers WHERE trainer_id = ?";
    private static final String GET_COURSES_FOR_TRAINER = "SELECT * FROM courses C INNER JOIN courses_trainers R ON C.id =R.course_id " +
                                                          "INNER JOIN trainers T ON R.trainer_id = T.id WHERE T.id = ?";
    private static final String GET_COURSES_AVALIABLE_FOR_TRAINERS = "SELECT * FROM courses C WHERE  C.id NOT IN (SELECT course_id " +
                                                                     "FROM courses_trainers R WHERE R.trainer_id = ?)";
    
    GenericDao database = GenericDao.getInstance();
    
    
    /**
     * Returns an ArrayList with Trainers
     * @return ArrayList with Trainers
     */
    @Override
    public ArrayList<Trainer> getAll() {
        ArrayList<Trainer> allTrainers = new ArrayList();
        String statement = FINDALL;
        ResultSet rs = database.makeStatement(statement);
        try {
            while(rs.next()){
                Trainer trainer = new Trainer(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getString("subject"));
                allTrainers.add(trainer);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.getAll()");
        }
        finally{
            database.closeConnections(database.result,database.statement,database.conn);
        }
        return allTrainers;
    }
    
    

    /**
     * Returns the trainer by his id
     * @param id
     * @return Trainer
     */
    @Override
    public Trainer getById(int id) {
        Trainer result = null;
        String statement = FINDBYID;
        ResultSet rs = database.makePreparedStatement(statement,new Object[]{(Object)id});
        try {
            while(rs.next()){
                result = new Trainer(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getString("subject"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.getById()");
        }finally{
            database.closeConnections(database.ps, database.conn);
        }
        return result;
    }

    
    /**
     * Returns the created course 
     * @return Trainer
     */
    @Override
    public Trainer getByMaxId() {
        Trainer result = null;
        String statement = FINDBYMAXID;
        ResultSet rs = database.makeStatement(statement);
        try {
            while(rs.next()){
                result = new Trainer(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getString("subject"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.getByMaxId()");
        }
        finally{
            database.closeConnections(database.result,database.statement,database.conn);
        }
        return result;
    }
    
    
    
    /**
     * Saves the trainer into the dataBase
     * @param student
     */
    @Override
    public void create(Trainer t) {
        String statement = INSERT;
        database.makeUpdateStatement(statement,new Object[]{(Object)t.getFirstName(), (Object)t.getLastName(), (Object)t.getSubject()});
        database.closeConnections(database.ps, database.conn);
        
    }

    
    
    /**
     * Updates the trainer's parameters in database.
     * User must first edit the trainer.
     * @param updatedTrainer
     */
    @Override
    public void update(Trainer t) {
        String statement = UPDATE;
        database.makeUpdateStatement(statement,new Object[]{(Object)t.getFirstName(), (Object)t.getLastName(), (Object)t.getSubject(),(Object)t.getId()});
        Trainer lastCreatedTrainer = getByMaxId();                              //after the trainer creation, we get back the last trainer with the id
        t.setId(lastCreatedTrainer.getId());                                    //and set the id to the given trainer
        
        database.closeConnections(database.ps, database.conn);
        
    }

    
    
    /**
     * Deletes the given trainer from the database
     * @param trainer
     */
    @Override
    public void delete(Trainer t) {
        
        String statement = REMOVE_TRAINER_FROM_COURSES;
        database.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        database.closeConnections(database.ps, database.conn);
        
        statement = DELETE;
        database.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        database.closeConnections(database.ps, database.conn);
        
        
        
    }

    
    
    
    /**
     * Checks if the given trainer exists
     * @param trainer
     * @return
     */
    @Override
    public boolean exists(Trainer t) {
        boolean exists = true;
        
        String statement = EXISTS;
        ResultSet rs = database.makePreparedStatement(statement,new Object[]{(Object)t.getFirstName(),(Object)t.getLastName(),(Object)t.getSubject()});
        try {
            if (rs.next() == false) {
                exists = false;
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.exists()");
        }finally{
            database.closeConnections(database.ps, database.conn);
        }

        return exists;
    }
    
    
    /**
     * Get all the trainer's courses
     * @param trainer
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCoursesForTrainer(Trainer trainer){
        ArrayList<Course> result = new ArrayList();
        
       
        // the mysql insert statement
        String statement = GET_COURSES_FOR_TRAINER;
      
        // execute the preparedstatement
        ResultSet rs = database.makePreparedStatement(statement,new Object[]{(Object)trainer.getId()});
        try {
            while(rs.next()){
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                result.add(course);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.getCoursesForTrainer()");
        }finally{
            database.closeConnections(database.ps, database.conn);
        }
        
        return result;
    }
    
    
    /**
     * Get all the trainer's available for register, courses
     * @param trainer
     * @return ArrayList of courses
     * @throws SQLException 
     */
    public ArrayList<Course> getCoursesAvailableForTrainer(Trainer trainer){
        ArrayList<Course> result = new ArrayList();
        // the mysql insert statement
        String statement =  GET_COURSES_AVALIABLE_FOR_TRAINERS;

        // execute the preparedstatement
        ResultSet rs = database.makePreparedStatement(statement,new Object[]{(Object)trainer.getId()});
        try {
            while(rs.next()){
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                result.add(course);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with TrainerDao.getCoursesAvailableForTrainer()");
        }finally{
            database.closeConnections(database.ps, database.conn);
        }
        return result;
    }
    
}
