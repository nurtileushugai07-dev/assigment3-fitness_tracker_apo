package repository.interfaces;

import exception.DatabaseOperationException;
import java.util.List;

// Generic interface - T is any type
// This is the DIP: service depends on interface, not concrete class
public interface CrudRepository<T> {

    void create(T entity) throws DatabaseOperationException;

    List<T> getAll() throws DatabaseOperationException;

    T getById(int id) throws DatabaseOperationException;

    void update(int id, T entity) throws DatabaseOperationException;

    void delete(int id) throws DatabaseOperationException;
}