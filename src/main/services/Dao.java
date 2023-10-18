package main.services;

import java.util.List;

import main.exceptions.CustomDateTimeParseException;
import main.exceptions.EntityAlreadyExistsException;
import main.exceptions.EntityNotFoundException;

public interface Dao<T> {

    List<T> getAll() throws CustomDateTimeParseException;

    T get(String id) throws EntityNotFoundException, CustomDateTimeParseException;

    Boolean create(T t) throws EntityAlreadyExistsException, CustomDateTimeParseException;

    Boolean update(T t) throws EntityNotFoundException;

    Boolean delete(String id) throws EntityNotFoundException;
}
