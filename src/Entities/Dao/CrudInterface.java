
package Entities.dao;

import java.util.ArrayList;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * CrudInterface is an interface for main Entities
 */
public interface CrudInterface<T> {
    ArrayList<T> getAll();
    T getById(int id);
    T getByMaxId();
    void create(T t);
    void update(T t);
    void delete(T t);
    boolean exists(T t);
}
