package services;

import java.util.List;

import exceptions.CustomDateTimeParseException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;

public interface Dao<T> {

    List<T> getAll() throws CustomDateTimeParseException;

    T get(String id) throws EntityNotFoundException, CustomDateTimeParseException;

    Boolean create(T t) throws EntityAlreadyExistsException, CustomDateTimeParseException;

    Boolean update(T t) throws EntityNotFoundException;

    Boolean delete(String id) throws EntityNotFoundException;
}
