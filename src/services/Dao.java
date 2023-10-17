package services;

import java.util.List;

import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;

public interface Dao<T> {

    List<T> getAll();

    T get(String id) throws EntityNotFoundException;

    Boolean create(T t) throws EntityAlreadyExistsException;

    Boolean update(T t) throws EntityNotFoundException;

    Boolean delete(T t) throws EntityNotFoundException;
}
